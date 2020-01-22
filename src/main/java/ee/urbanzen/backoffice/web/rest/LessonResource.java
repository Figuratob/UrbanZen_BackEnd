package ee.urbanzen.backoffice.web.rest;

import ee.urbanzen.backoffice.domain.Booking;
import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.repository.LessonRepository;
import ee.urbanzen.backoffice.service.dto.TimetableDTO;
import ee.urbanzen.backoffice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link ee.urbanzen.backoffice.domain.Lesson}.
 */
@RestController
@RequestMapping("/api")
public class LessonResource {

    private final Logger log = LoggerFactory.getLogger(LessonResource.class);

    private static final String ENTITY_NAME = "lesson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LessonRepository lessonRepository;

    public LessonResource(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    /**
     * {@code POST  /lessons} : Create a new lesson.
     *
     * @param lesson the lesson to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lesson, or with status {@code 400 (Bad Request)} if the lesson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lessons")
    public ResponseEntity<Lesson> createLesson(@Valid @RequestBody Lesson lesson) throws URISyntaxException {
        log.debug("REST request to save Lesson : {}", lesson);
        if (lesson.getId() != null) {
            throw new BadRequestAlertException("A new lesson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lesson result = lessonRepository.save(lesson);
        return ResponseEntity.created(new URI("/api/lessons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lessons} : Updates an existing lesson.
     *
     * @param lesson the lesson to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lesson,
     * or with status {@code 400 (Bad Request)} if the lesson is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lesson couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lessons")
    public ResponseEntity<Lesson> updateLesson(@Valid @RequestBody Lesson lesson) throws URISyntaxException {
        log.debug("REST request to update Lesson : {}", lesson);
        if (lesson.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lesson result = lessonRepository.save(lesson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lesson.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lessons} : get all the lessons.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lessons in body.
     */
    @GetMapping("/lessons")
    public List<Lesson> getAllLessons() {
        log.debug("REST request to get all Lessons");
        return lessonRepository.findAll();
    }

    /**
     * {@code GET  /lessons} : get lessons by dates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timetableDTOs in body.
     */
    @GetMapping("/getTimetableByDates")
    public List<TimetableDTO> getTimetableByDates(
        @RequestParam(value = "firstDayOfWeek") LocalDate firstDayOfWeek,
        @RequestParam(value = "lastDayOfWeek") LocalDate lastDayOfWeek) {
        log.debug("REST request to get Timetable by dates");

        //          current week:
//        LocalDate date = LocalDate.now();
//        LocalDate firstDayOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//        LocalDate lastDayOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

//          fixed data:
//        LocalDate firstDayOfWeek = LocalDate.parse("2019-11-04");
//        LocalDate lastDayOfWeek = LocalDate.parse("2019-11-11");

//          or:
//        LocalDate firstDayOfWeek = LocalDate.parse("2019-11-07");
//        LocalDate lastDayOfWeek = LocalDate.parse("2019-11-08");


        List<TimetableDTO> timetables = new ArrayList<>();
        List<LocalDate> days = getDatesBetween(firstDayOfWeek, lastDayOfWeek);
        List<Lesson> lessons = getLessonsByDates(firstDayOfWeek, lastDayOfWeek);

        for (LocalDate day : days) {
            TimetableDTO timetableDTO = new TimetableDTO();
            timetableDTO.setTimetableDay(day.atStartOfDay(ZoneId.systemDefault()).toInstant());
            List<Lesson> timetableLessons = new ArrayList<>();
            for (Lesson lesson : lessons) {
                if (LocalDate.ofInstant(lesson.getStartDate(), ZoneId.systemDefault()).equals(day)) {
                    timetableLessons.add(lesson);
                }
            }
            timetableDTO.setLessons(timetableLessons);
            timetables.add(timetableDTO);
        }
        return timetables;
    }

    private List<Lesson> getLessonsByDates(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        Instant firstDayOfWeekInstant = firstDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant lastDayOfWeekInstant = lastDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return lessonRepository.findAllByDates(firstDayOfWeekInstant, lastDayOfWeekInstant);
    }

    private static List<LocalDate> getDatesBetween(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        return firstDayOfWeek.datesUntil(lastDayOfWeek.plusDays(1))
            .collect(Collectors.toList());
    }

    /**
     * {@code GET  /lessons/:id} : get the "id" lesson.
     *
     * @param id the id of the lesson to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lesson, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lessons/{id}")
    public ResponseEntity<Lesson> getLesson(@PathVariable Long id) {
        log.debug("REST request to get Lesson : {}", id);
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lesson);
    }

    @GetMapping("/lessons/spaces/{id}")
    public int getAvailableSpaces(@PathVariable Long id) {
        log.debug("REST request to get available spaces for lesson id  : {}", id);
        Lesson lesson = lessonRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Lesson not found", ENTITY_NAME, "lessonnotfound"));
        int availableSpaces = 0;
        for (Booking booking : lesson.getBookings()) {
            if (booking.getCancelDate() != null) {
                availableSpaces++;
            }
        }
        return availableSpaces;
    }

    /**
     * {@code DELETE  /lessons/:id} : delete the "id" lesson.
     *
     * @param id the id of the lesson to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lessons/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        log.debug("REST request to delete Lesson : {}", id);
        lessonRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
