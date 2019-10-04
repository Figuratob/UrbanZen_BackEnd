package ee.urbanzen.backoffice.web.rest;

import ee.urbanzen.backoffice.domain.Studio;
import ee.urbanzen.backoffice.repository.StudioRepository;
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
 * REST controller for managing {@link ee.urbanzen.backoffice.domain.Studio}.
 */
@RestController
@RequestMapping("/api")
public class StudioResource {

    private final Logger log = LoggerFactory.getLogger(StudioResource.class);

    private static final String ENTITY_NAME = "studio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudioRepository studioRepository;

    public StudioResource(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    /**
     * {@code POST  /studios} : Create a new studio.
     *
     * @param studio the studio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studio, or with status {@code 400 (Bad Request)} if the studio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/studios")
    public ResponseEntity<Studio> createStudio(@Valid @RequestBody Studio studio) throws URISyntaxException {
        log.debug("REST request to save Studio : {}", studio);
        if (studio.getId() != null) {
            throw new BadRequestAlertException("A new studio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Studio result = studioRepository.save(studio);
        return ResponseEntity.created(new URI("/api/studios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /studios} : Updates an existing studio.
     *
     * @param studio the studio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studio,
     * or with status {@code 400 (Bad Request)} if the studio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/studios")
    public ResponseEntity<Studio> updateStudio(@Valid @RequestBody Studio studio) throws URISyntaxException {
        log.debug("REST request to update Studio : {}", studio);
        if (studio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Studio result = studioRepository.save(studio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /studios} : get all the studios.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studios in body.
     */
    @GetMapping("/studios")
    public List<Studio> getAllStudios() {
        log.debug("REST request to get all Studios");
        return studioRepository.findAll();
    }

    /**
     * {@code GET  /studios/:id} : get the "id" studio.
     *
     * @param id the id of the studio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/studios/{id}")
    public ResponseEntity<Studio> getStudio(@PathVariable Long id) {
        log.debug("REST request to get Studio : {}", id);
        Optional<Studio> studio = studioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studio);
    }

    /**
     * {@code DELETE  /studios/:id} : delete the "id" studio.
     *
     * @param id the id of the studio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/studios/{id}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long id) {
        log.debug("REST request to delete Studio : {}", id);
        studioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
