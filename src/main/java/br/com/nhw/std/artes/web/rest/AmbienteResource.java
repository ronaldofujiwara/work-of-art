package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.domain.Ambiente;
import br.com.nhw.std.artes.repository.AmbienteRepository;
import br.com.nhw.std.artes.service.AmbienteQueryService;
import br.com.nhw.std.artes.service.AmbienteService;
import br.com.nhw.std.artes.service.criteria.AmbienteCriteria;
import br.com.nhw.std.artes.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.nhw.std.artes.domain.Ambiente}.
 */
@RestController
@RequestMapping("/api")
public class AmbienteResource {

    private final Logger log = LoggerFactory.getLogger(AmbienteResource.class);

    private static final String ENTITY_NAME = "ambiente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmbienteService ambienteService;

    private final AmbienteRepository ambienteRepository;

    private final AmbienteQueryService ambienteQueryService;

    public AmbienteResource(
        AmbienteService ambienteService,
        AmbienteRepository ambienteRepository,
        AmbienteQueryService ambienteQueryService
    ) {
        this.ambienteService = ambienteService;
        this.ambienteRepository = ambienteRepository;
        this.ambienteQueryService = ambienteQueryService;
    }

    /**
     * {@code POST  /ambientes} : Create a new ambiente.
     *
     * @param ambiente the ambiente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ambiente, or with status {@code 400 (Bad Request)} if the ambiente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ambientes")
    public ResponseEntity<Ambiente> createAmbiente(@Valid @RequestBody Ambiente ambiente) throws URISyntaxException {
        log.debug("REST request to save Ambiente : {}", ambiente);
        if (ambiente.getId() != null) {
            throw new BadRequestAlertException("A new ambiente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ambiente result = ambienteService.save(ambiente);
        return ResponseEntity
            .created(new URI("/api/ambientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ambientes/:id} : Updates an existing ambiente.
     *
     * @param id the id of the ambiente to save.
     * @param ambiente the ambiente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ambiente,
     * or with status {@code 400 (Bad Request)} if the ambiente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ambiente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ambientes/{id}")
    public ResponseEntity<Ambiente> updateAmbiente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ambiente ambiente
    ) throws URISyntaxException {
        log.debug("REST request to update Ambiente : {}, {}", id, ambiente);
        if (ambiente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ambiente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ambienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ambiente result = ambienteService.save(ambiente);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ambiente.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ambientes/:id} : Partial updates given fields of an existing ambiente, field will ignore if it is null
     *
     * @param id the id of the ambiente to save.
     * @param ambiente the ambiente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ambiente,
     * or with status {@code 400 (Bad Request)} if the ambiente is not valid,
     * or with status {@code 404 (Not Found)} if the ambiente is not found,
     * or with status {@code 500 (Internal Server Error)} if the ambiente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ambientes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Ambiente> partialUpdateAmbiente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ambiente ambiente
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ambiente partially : {}, {}", id, ambiente);
        if (ambiente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ambiente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ambienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ambiente> result = ambienteService.partialUpdate(ambiente);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ambiente.getId().toString())
        );
    }

    /**
     * {@code GET  /ambientes} : get all the ambientes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ambientes in body.
     */
    @GetMapping("/ambientes")
    public ResponseEntity<List<Ambiente>> getAllAmbientes(AmbienteCriteria criteria) {
        log.debug("REST request to get Ambientes by criteria: {}", criteria);
        List<Ambiente> entityList = ambienteQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ambientes/count} : count all the ambientes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ambientes/count")
    public ResponseEntity<Long> countAmbientes(AmbienteCriteria criteria) {
        log.debug("REST request to count Ambientes by criteria: {}", criteria);
        return ResponseEntity.ok().body(ambienteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ambientes/:id} : get the "id" ambiente.
     *
     * @param id the id of the ambiente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ambiente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ambientes/{id}")
    public ResponseEntity<Ambiente> getAmbiente(@PathVariable Long id) {
        log.debug("REST request to get Ambiente : {}", id);
        Optional<Ambiente> ambiente = ambienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ambiente);
    }

    /**
     * {@code DELETE  /ambientes/:id} : delete the "id" ambiente.
     *
     * @param id the id of the ambiente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ambientes/{id}")
    public ResponseEntity<Void> deleteAmbiente(@PathVariable Long id) {
        log.debug("REST request to delete Ambiente : {}", id);
        ambienteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
