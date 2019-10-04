package ee.urbanzen.backoffice.web.rest;

import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
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

    private final LessonTemplateRepository lessonTemplateRepository;

    public LessonTemplateResource(LessonTemplateRepository lessonTemplateRepository) {
        this.lessonTemplateRepository = lessonTemplateRepository;
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
