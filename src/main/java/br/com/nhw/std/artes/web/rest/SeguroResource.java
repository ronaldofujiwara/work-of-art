package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.SeguroRepository;
import br.com.nhw.std.artes.service.SeguroService;
import br.com.nhw.std.artes.service.dto.SeguroDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.Seguro}.
 */
@RestController
@RequestMapping("/api")
public class SeguroResource {

    private final Logger log = LoggerFactory.getLogger(SeguroResource.class);

    private static final String ENTITY_NAME = "seguro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SeguroService seguroService;

    private final SeguroRepository seguroRepository;

    public SeguroResource(SeguroService seguroService, SeguroRepository seguroRepository) {
        this.seguroService = seguroService;
        this.seguroRepository = seguroRepository;
    }

    /**
     * {@code POST  /seguros} : Create a new seguro.
     *
     * @param seguroDTO the seguroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new seguroDTO, or with status {@code 400 (Bad Request)} if the seguro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/seguros")
    public ResponseEntity<SeguroDTO> createSeguro(@Valid @RequestBody SeguroDTO seguroDTO) throws URISyntaxException {
        log.debug("REST request to save Seguro : {}", seguroDTO);
        if (seguroDTO.getId() != null) {
            throw new BadRequestAlertException("A new seguro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SeguroDTO result = seguroService.save(seguroDTO);
        return ResponseEntity
            .created(new URI("/api/seguros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /seguros/:id} : Updates an existing seguro.
     *
     * @param id the id of the seguroDTO to save.
     * @param seguroDTO the seguroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seguroDTO,
     * or with status {@code 400 (Bad Request)} if the seguroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the seguroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/seguros/{id}")
    public ResponseEntity<SeguroDTO> updateSeguro(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SeguroDTO seguroDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Seguro : {}, {}", id, seguroDTO);
        if (seguroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seguroDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seguroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SeguroDTO result = seguroService.save(seguroDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seguroDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /seguros/:id} : Partial updates given fields of an existing seguro, field will ignore if it is null
     *
     * @param id the id of the seguroDTO to save.
     * @param seguroDTO the seguroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated seguroDTO,
     * or with status {@code 400 (Bad Request)} if the seguroDTO is not valid,
     * or with status {@code 404 (Not Found)} if the seguroDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the seguroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/seguros/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SeguroDTO> partialUpdateSeguro(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SeguroDTO seguroDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Seguro partially : {}, {}", id, seguroDTO);
        if (seguroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, seguroDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!seguroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SeguroDTO> result = seguroService.partialUpdate(seguroDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, seguroDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /seguros} : get all the seguros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of seguros in body.
     */
    @GetMapping("/seguros")
    public ResponseEntity<List<SeguroDTO>> getAllSeguros(Pageable pageable) {
        log.debug("REST request to get a page of Seguros");
        Page<SeguroDTO> page = seguroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /seguros/:id} : get the "id" seguro.
     *
     * @param id the id of the seguroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the seguroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/seguros/{id}")
    public ResponseEntity<SeguroDTO> getSeguro(@PathVariable Long id) {
        log.debug("REST request to get Seguro : {}", id);
        Optional<SeguroDTO> seguroDTO = seguroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(seguroDTO);
    }

    /**
     * {@code DELETE  /seguros/:id} : delete the "id" seguro.
     *
     * @param id the id of the seguroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/seguros/{id}")
    public ResponseEntity<Void> deleteSeguro(@PathVariable Long id) {
        log.debug("REST request to delete Seguro : {}", id);
        seguroService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
