package ee.urbanzen.backoffice.web.rest;

import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.repository.LessonRepository;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
import ee.urbanzen.backoffice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link ee.urbanzen.backoffice.domain.LessonTemplate}.
 */
@RestController
@RequestMapping("/api")
public class LessonTemplateResource {

    private final Logger log = LoggerFactory.getLogger(LessonTemplateResource.class);

    private static final String ENTITY_NAME = "lessonTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LessonTemplateRepository lessonTemplateRepository;

    private final LessonRepository lessonRepository;

    public LessonTemplateResource(LessonTemplateRepository lessonTemplateRepository, LessonRepository lessonRepository) {
        this.lessonTemplateRepository = lessonTemplateRepository;
        this.lessonRepository = lessonRepository;
    }

    /**
     * {@code POST  /lesson-templates} : Create a new lessonTemplate.
     *
     * @param lessonTemplate the lessonTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lessonTemplate, or with status {@code 400 (Bad Request)} if the lessonTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lesson-templates")
    public ResponseEntity<LessonTemplate> createLessonTemplate(@Valid @RequestBody LessonTemplate lessonTemplate) throws URISyntaxException {
        log.debug("REST request to save LessonTemplate : {}", lessonTemplate);
        if (lessonTemplate.getId() != null) {
            throw new BadRequestAlertException("A new lessonTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LessonTemplate result = lessonTemplateRepository.save(lessonTemplate);
        return ResponseEntity.created(new URI("/api/lesson-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /timetable} : return status {@code 201(OK)}.
     *
     * @return
     */

    @GetMapping("/timetable")
    public ResponseEntity<String> createTimetable(@RequestParam String startDate,
                                                  @RequestParam String endDate) throws URISyntaxException {
        log.debug("REST request to save Timetable : {}");

        LocalDate timetableStartDateTimeLocalDate = LocalDate.parse(startDate);
        LocalDate timetableEndDateTimeLocalDate = LocalDate.parse(endDate);

        List<LessonTemplate>lessonTemplatesByDatesWithoutLessons = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateTimeLocalDate, timetableEndDateTimeLocalDate);

        List<Lesson>generatedLessons = generateLessonsFromTemplatesByDates(timetableStartDateTimeLocalDate, timetableEndDateTimeLocalDate,lessonTemplatesByDatesWithoutLessons);

        lessonRepository.saveAll(generatedLessons);
        lessonRepository.flush();

        return ResponseEntity.ok()
            .body("OK");
    }

    public static List<LocalDate> getDatesBetween (
        LocalDate timetableStartDateTimeLocalDate, LocalDate timetableEndDateTimeLocalDate) {

        return timetableStartDateTimeLocalDate.datesUntil(timetableEndDateTimeLocalDate)
            .collect(Collectors.toList());
    }

//     load all templates that match criteria from database
//     for every template generate lessons
//     save lessons to database

    public static List<Lesson> generateLessonsFromTemplatesByDates(LocalDate timetableStartDateTimeLocalDate,
                  LocalDate timetableEndDateTimeLocalDate, List<LessonTemplate> lessonTemplatesByDatesWithoutLessons) {

        List<Lesson> lessons = new ArrayList<>();

        List<LocalDate> dates = getDatesBetween(timetableStartDateTimeLocalDate, timetableEndDateTimeLocalDate);

        for (LocalDate date: dates) {

            for (LessonTemplate lessonTemplate : lessonTemplatesByDatesWithoutLessons) {

                boolean templateIsActive = lessonTemplate.isActiveOnGivenDate(date);
                if (templateIsActive) {

                    if (date.getDayOfWeek().getValue() == lessonTemplate.getDayOfWeek().getValue()) {

                        Instant startDate = date.atTime(lessonTemplate.getStartHour(), lessonTemplate.getStartMinute()).toInstant(ZoneOffset.UTC);
                        Instant endDate = date.atTime(lessonTemplate.getEndHour(), lessonTemplate.getEndMinute()).toInstant(ZoneOffset.UTC);

                        Lesson lesson = new Lesson()
                            .startDate(startDate)
                            .endDate(endDate)
                            .name(lessonTemplate.getName())
                            .description(lessonTemplate.getDescription())
                            .street(lessonTemplate.getStreet())
                            .city(lessonTemplate.getCity())
                            .availableSpaces(lessonTemplate.getAvailableSpaces())
                            .teacher(lessonTemplate.getTeacher())
                            .lessonTemplate(lessonTemplate);
                        lessons.add(lesson);
                    }
                }
            }
        }
        return lessons;
    }


    public static List<Lesson> generateLessonsFromTemplatesForDay(LocalDate startDateTimeLocalDate,
                                                                  List<LessonTemplate> lessonTemplatesByDatesWithoutLessons) {

        List<Lesson> lessons = new ArrayList<>();

        for (LessonTemplate lessonTemplate : lessonTemplatesByDatesWithoutLessons) {

            if (startDateTimeLocalDate.getDayOfWeek().getValue() ==lessonTemplate.getDayOfWeek().getValue() &&
                startDateTimeLocalDate.isAfter(lessonTemplate.getRepeatStartDate()) &&
                startDateTimeLocalDate.isBefore(lessonTemplate.getRepeatUntilDate())) {

                Instant startDate = startDateTimeLocalDate.atTime(lessonTemplate.getStartHour(),lessonTemplate.getStartMinute()).toInstant(ZoneOffset.UTC);
                Instant endDate = startDateTimeLocalDate.atTime(lessonTemplate.getEndHour(),lessonTemplate.getEndMinute()).toInstant(ZoneOffset.UTC);

                Lesson lesson = new Lesson()
                    .startDate(startDate)
                    .endDate(endDate)
                    .name(lessonTemplate.getName())
                    .description(lessonTemplate.getDescription())
                    .street(lessonTemplate.getStreet())
                    .city(lessonTemplate.getCity())
                    .availableSpaces(lessonTemplate.getAvailableSpaces())
                    .teacher(lessonTemplate.getTeacher())
                    .lessonTemplate(lessonTemplate);
                lessons.add(lesson);
            }
        }
        return lessons;
    }

    public static List<Lesson> generateLessonsFromTemplates(LocalDate startDateTimeLocalDate, LocalDate untilDateTimeLocalDate,
                                              List<LessonTemplate> lessonTemplatesByWeekOfDay) {

        List<Lesson> lessons = new ArrayList<>();
        for (LessonTemplate lessonTemplate : lessonTemplatesByWeekOfDay) {

            // MONDAY = 1
            int numberOfDayOfWeek = lessonTemplate.getDayOfWeek().ordinal();
            // MONDAY = 2
            int numberOfStartDateTime = startDateTimeLocalDate.getDayOfWeek().ordinal();
            int days = numberOfDayOfWeek-numberOfStartDateTime;

            LocalDate lessonStartDateTimeLocalDate = startDateTimeLocalDate.plusDays(days);

            Instant startDate = lessonStartDateTimeLocalDate.atTime(lessonTemplate.getStartHour(),lessonTemplate.getStartMinute()).toInstant(ZoneOffset.UTC);
            Instant endDate = lessonStartDateTimeLocalDate.atTime(lessonTemplate.getEndHour(),lessonTemplate.getEndMinute()).toInstant(ZoneOffset.UTC);

                Lesson lesson = new Lesson()
                    .startDate(startDate)
                    .endDate(endDate)
                    .name(lessonTemplate.getName())
                    .description(lessonTemplate.getDescription())
                    .street(lessonTemplate.getStreet())
                    .city(lessonTemplate.getCity())
                    .availableSpaces(lessonTemplate.getAvailableSpaces())
                    .teacher(lessonTemplate.getTeacher());
                lessons.add(lesson);
        }
        return lessons;
    }

    /**
     * {@code PUT  /lesson-templates} : Updates an existing lessonTemplate.
     *
     * @param lessonTemplate the lessonTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lessonTemplate,
     * or with status {@code 400 (Bad Request)} if the lessonTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lessonTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lesson-templates")
    public ResponseEntity<LessonTemplate> updateLessonTemplate(@Valid @RequestBody LessonTemplate lessonTemplate) throws URISyntaxException {
        log.debug("REST request to update LessonTemplate : {}", lessonTemplate);
        if (lessonTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LessonTemplate result = lessonTemplateRepository.save(lessonTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lessonTemplate.getId().toString()))
            .body(result);
    }

//    /**
//     * {@code PUT  /timetable} : Updates an existing timetable.
//     *
//     * @param timetable the timetable to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timetable,
//     * or with status {@code 400 (Bad Request)} if the timetable is not valid,
//     * or with status {@code 500 (Internal Server Error)} if the timetable couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PutMapping("/timetable")
//    public ResponseEntity<Timetable> updateTimetable(@Valid @RequestBody Timetable timetable) throws URISyntaxException {
//        log.debug("REST request to update Timetable : {}", timetable);
//        if (timetable.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        Timetable result = timetableRepository.save(timetable);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timetable.getId().toString()))
//            .body(result);
//    }
//
    /**
     * {@code GET  /lesson-templates} : get all the lessonTemplates.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lessonTemplates in body.
     */
    @GetMapping("/lesson-templates")
    public List<LessonTemplate> getAllLessonTemplates() {
        log.debug("REST request to get all LessonTemplates");
        return lessonTemplateRepository.findAll();
    }

    /**
     * {@code GET  /lesson-templates/:id} : get the "id" lessonTemplate.
     *
     * @param id the id of the lessonTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lessonTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lesson-templates/{id}")
    public ResponseEntity<LessonTemplate> getLessonTemplate(@PathVariable Long id) {
        log.debug("REST request to get LessonTemplate : {}", id);
        Optional<LessonTemplate> lessonTemplate = lessonTemplateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lessonTemplate);
    }

    /**
     * {@code DELETE  /lesson-templates/:id} : delete the "id" lessonTemplate.
     *
     * @param id the id of the lessonTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lesson-templates/{id}")
    public ResponseEntity<Void> deleteLessonTemplate(@PathVariable Long id) {
        log.debug("REST request to delete LessonTemplate : {}", id);
        lessonTemplateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
