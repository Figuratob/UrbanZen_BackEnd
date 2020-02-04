package ee.urbanzen.backoffice.web.rest;

import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.service.LessonService;
import ee.urbanzen.backoffice.service.LessonTemplateService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    private final LessonService lessonService;

    private final LessonTemplateService lessonTemplateService;

    public LessonTemplateResource(LessonService lessonService, LessonTemplateService lessonTemplateService) {
        this.lessonService = lessonService;
        this.lessonTemplateService = lessonTemplateService;
    }

    /**
     * {@code POST  /lesson-templates} : Create a new lessonTemplate.
     *
     * @param lessonTemplate the lessonTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lessonTemplate,
     * or with status {@code 400 (Bad Request)} if the lessonTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lesson-templates")
    public ResponseEntity<LessonTemplate> createLessonTemplate(@Valid @RequestBody LessonTemplate lessonTemplate)
        throws URISyntaxException {
        log.debug("REST request to save LessonTemplate : {}", lessonTemplate);
        if (lessonTemplate.getId() != null) {
            throw new BadRequestAlertException("A new lessonTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LessonTemplate result = lessonTemplateService.save(lessonTemplate);
        return ResponseEntity.created(new URI("/api/lesson-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
                result.getId().toString()))
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

        List<LessonTemplate>lessonTemplatesByDatesWithoutLessons = lessonTemplateService
            .findAllByDatesWithoutLessons(timetableStartDateTimeLocalDate, timetableEndDateTimeLocalDate);

        List<Lesson>generatedLessons = lessonTemplateService.generateLessonsFromTemplatesByDates(timetableStartDateTimeLocalDate,
            timetableEndDateTimeLocalDate,lessonTemplatesByDatesWithoutLessons);

        lessonService.saveAll(generatedLessons);
        lessonService.flush();

        return ResponseEntity.ok()
            .body("OK");
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
    public ResponseEntity<LessonTemplate> updateLessonTemplate(@Valid @RequestBody LessonTemplate lessonTemplate)
        throws URISyntaxException {
        log.debug("REST request to update LessonTemplate : {}", lessonTemplate);
        if (lessonTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LessonTemplate result = lessonTemplateService.save(lessonTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                lessonTemplate.getId().toString()))
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
        return lessonTemplateService.findAll();
    }

    /**
     * {@code GET  /lesson-templates/:id} : get the "id" lessonTemplate.
     *
     * @param id the id of the lessonTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lessonTemplate, or with status
     * {@code 404 (Not Found)}.
     */
    @GetMapping("/lesson-templates/{id}")
    public ResponseEntity<LessonTemplate> getLessonTemplate(@PathVariable Long id) {
        log.debug("REST request to get LessonTemplate : {}", id);
        Optional<LessonTemplate> lessonTemplate = lessonTemplateService.findById(id);
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
        lessonTemplateService.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName,
            true, ENTITY_NAME, id.toString())).build();
    }
}
