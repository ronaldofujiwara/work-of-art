package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.AreaDeptoRepository;
import br.com.nhw.std.artes.service.AreaDeptoQueryService;
import br.com.nhw.std.artes.service.AreaDeptoService;
import br.com.nhw.std.artes.service.criteria.AreaDeptoCriteria;
import br.com.nhw.std.artes.service.dto.AreaDeptoDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
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
     * @param areaDeptoDTO the areaDeptoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaDeptoDTO, or with status {@code 400 (Bad Request)} if the areaDepto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/area-deptos")
    public ResponseEntity<AreaDeptoDTO> createAreaDepto(@Valid @RequestBody AreaDeptoDTO areaDeptoDTO) throws URISyntaxException {
        log.debug("REST request to save AreaDepto : {}", areaDeptoDTO);
        if (areaDeptoDTO.getId() != null) {
            throw new BadRequestAlertException("A new areaDepto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AreaDeptoDTO result = areaDeptoService.save(areaDeptoDTO);
        return ResponseEntity
            .created(new URI("/api/area-deptos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /area-deptos/:id} : Updates an existing areaDepto.
     *
     * @param id the id of the areaDeptoDTO to save.
     * @param areaDeptoDTO the areaDeptoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaDeptoDTO,
     * or with status {@code 400 (Bad Request)} if the areaDeptoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaDeptoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/area-deptos/{id}")
    public ResponseEntity<AreaDeptoDTO> updateAreaDepto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AreaDeptoDTO areaDeptoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AreaDepto : {}, {}", id, areaDeptoDTO);
        if (areaDeptoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaDeptoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaDeptoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AreaDeptoDTO result = areaDeptoService.save(areaDeptoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaDeptoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /area-deptos/:id} : Partial updates given fields of an existing areaDepto, field will ignore if it is null
     *
     * @param id the id of the areaDeptoDTO to save.
     * @param areaDeptoDTO the areaDeptoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaDeptoDTO,
     * or with status {@code 400 (Bad Request)} if the areaDeptoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the areaDeptoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaDeptoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/area-deptos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AreaDeptoDTO> partialUpdateAreaDepto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AreaDeptoDTO areaDeptoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AreaDepto partially : {}, {}", id, areaDeptoDTO);
        if (areaDeptoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaDeptoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaDeptoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaDeptoDTO> result = areaDeptoService.partialUpdate(areaDeptoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaDeptoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /area-deptos} : get all the areaDeptos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaDeptos in body.
     */
    @GetMapping("/area-deptos")
    public ResponseEntity<List<AreaDeptoDTO>> getAllAreaDeptos(AreaDeptoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AreaDeptos by criteria: {}", criteria);
        Page<AreaDeptoDTO> page = areaDeptoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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
     * @param id the id of the areaDeptoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaDeptoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/area-deptos/{id}")
    public ResponseEntity<AreaDeptoDTO> getAreaDepto(@PathVariable Long id) {
        log.debug("REST request to get AreaDepto : {}", id);
        Optional<AreaDeptoDTO> areaDeptoDTO = areaDeptoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaDeptoDTO);
    }

    /**
     * {@code DELETE  /area-deptos/:id} : delete the "id" areaDepto.
     *
     * @param id the id of the areaDeptoDTO to delete.
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
