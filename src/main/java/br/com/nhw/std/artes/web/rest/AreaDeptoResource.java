package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.domain.AreaDepto;
import br.com.nhw.std.artes.repository.AreaDeptoRepository;
import br.com.nhw.std.artes.service.AreaDeptoQueryService;
import br.com.nhw.std.artes.service.AreaDeptoService;
import br.com.nhw.std.artes.service.criteria.AreaDeptoCriteria;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.AreaDepto}.
 */
@RestController
@RequestMapping("/api")
public class AreaDeptoResource {

    private final Logger log = LoggerFactory.getLogger(AreaDeptoResource.class);

    private static final String ENTITY_NAME = "areaDepto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaDeptoService areaDeptoService;

    private final AreaDeptoRepository areaDeptoRepository;

    private final AreaDeptoQueryService areaDeptoQueryService;

    public AreaDeptoResource(
        AreaDeptoService areaDeptoService,
        AreaDeptoRepository areaDeptoRepository,
        AreaDeptoQueryService areaDeptoQueryService
    ) {
        this.areaDeptoService = areaDeptoService;
        this.areaDeptoRepository = areaDeptoRepository;
        this.areaDeptoQueryService = areaDeptoQueryService;
    }

    /**
     * {@code POST  /area-deptos} : Create a new areaDepto.
     *
     * @param areaDepto the areaDepto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaDepto, or with status {@code 400 (Bad Request)} if the areaDepto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/area-deptos")
    public ResponseEntity<AreaDepto> createAreaDepto(@Valid @RequestBody AreaDepto areaDepto) throws URISyntaxException {
        log.debug("REST request to save AreaDepto : {}", areaDepto);
        if (areaDepto.getId() != null) {
            throw new BadRequestAlertException("A new areaDepto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaDepto result = areaDeptoService.save(areaDepto);
        return ResponseEntity
            .created(new URI("/api/area-deptos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /area-deptos/:id} : Updates an existing areaDepto.
     *
     * @param id the id of the areaDepto to save.
     * @param areaDepto the areaDepto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaDepto,
     * or with status {@code 400 (Bad Request)} if the areaDepto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaDepto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/area-deptos/{id}")
    public ResponseEntity<AreaDepto> updateAreaDepto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AreaDepto areaDepto
    ) throws URISyntaxException {
        log.debug("REST request to update AreaDepto : {}, {}", id, areaDepto);
        if (areaDepto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaDepto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaDeptoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AreaDepto result = areaDeptoService.save(areaDepto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaDepto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /area-deptos/:id} : Partial updates given fields of an existing areaDepto, field will ignore if it is null
     *
     * @param id the id of the areaDepto to save.
     * @param areaDepto the areaDepto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaDepto,
     * or with status {@code 400 (Bad Request)} if the areaDepto is not valid,
     * or with status {@code 404 (Not Found)} if the areaDepto is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaDepto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/area-deptos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AreaDepto> partialUpdateAreaDepto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AreaDepto areaDepto
    ) throws URISyntaxException {
        log.debug("REST request to partial update AreaDepto partially : {}, {}", id, areaDepto);
        if (areaDepto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaDepto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaDeptoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaDepto> result = areaDeptoService.partialUpdate(areaDepto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaDepto.getId().toString())
        );
    }

    /**
     * {@code GET  /area-deptos} : get all the areaDeptos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaDeptos in body.
     */
    @GetMapping("/area-deptos")
    public ResponseEntity<List<AreaDepto>> getAllAreaDeptos(AreaDeptoCriteria criteria) {
        log.debug("REST request to get AreaDeptos by criteria: {}", criteria);
        List<AreaDepto> entityList = areaDeptoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /area-deptos/count} : count all the areaDeptos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/area-deptos/count")
    public ResponseEntity<Long> countAreaDeptos(AreaDeptoCriteria criteria) {
        log.debug("REST request to count AreaDeptos by criteria: {}", criteria);
        return ResponseEntity.ok().body(areaDeptoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /area-deptos/:id} : get the "id" areaDepto.
     *
     * @param id the id of the areaDepto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaDepto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/area-deptos/{id}")
    public ResponseEntity<AreaDepto> getAreaDepto(@PathVariable Long id) {
        log.debug("REST request to get AreaDepto : {}", id);
        Optional<AreaDepto> areaDepto = areaDeptoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaDepto);
    }

    /**
     * {@code DELETE  /area-deptos/:id} : delete the "id" areaDepto.
     *
     * @param id the id of the areaDepto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/area-deptos/{id}")
    public ResponseEntity<Void> deleteAreaDepto(@PathVariable Long id) {
        log.debug("REST request to delete AreaDepto : {}", id);
        areaDeptoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
