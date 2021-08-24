package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.NivelRepository;
import br.com.nhw.std.artes.service.NivelService;
import br.com.nhw.std.artes.service.dto.NivelDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.Nivel}.
 */
@RestController
@RequestMapping("/api")
public class NivelResource {

    private final Logger log = LoggerFactory.getLogger(NivelResource.class);

    private static final String ENTITY_NAME = "nivel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NivelService nivelService;

    private final NivelRepository nivelRepository;

    public NivelResource(NivelService nivelService, NivelRepository nivelRepository) {
        this.nivelService = nivelService;
        this.nivelRepository = nivelRepository;
    }

    /**
     * {@code POST  /nivels} : Create a new nivel.
     *
     * @param nivelDTO the nivelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nivelDTO, or with status {@code 400 (Bad Request)} if the nivel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nivels")
    public ResponseEntity<NivelDTO> createNivel(@Valid @RequestBody NivelDTO nivelDTO) throws URISyntaxException {
        log.debug("REST request to save Nivel : {}", nivelDTO);
        if (nivelDTO.getId() != null) {
            throw new BadRequestAlertException("A new nivel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NivelDTO result = nivelService.save(nivelDTO);
        return ResponseEntity
            .created(new URI("/api/nivels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nivels/:id} : Updates an existing nivel.
     *
     * @param id the id of the nivelDTO to save.
     * @param nivelDTO the nivelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivelDTO,
     * or with status {@code 400 (Bad Request)} if the nivelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nivelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nivels/{id}")
    public ResponseEntity<NivelDTO> updateNivel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NivelDTO nivelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Nivel : {}, {}", id, nivelDTO);
        if (nivelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nivelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nivelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NivelDTO result = nivelService.save(nivelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nivels/:id} : Partial updates given fields of an existing nivel, field will ignore if it is null
     *
     * @param id the id of the nivelDTO to save.
     * @param nivelDTO the nivelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivelDTO,
     * or with status {@code 400 (Bad Request)} if the nivelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nivelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nivelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nivels/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NivelDTO> partialUpdateNivel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NivelDTO nivelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nivel partially : {}, {}", id, nivelDTO);
        if (nivelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nivelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nivelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NivelDTO> result = nivelService.partialUpdate(nivelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nivels} : get all the nivels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nivels in body.
     */
    @GetMapping("/nivels")
    public ResponseEntity<List<NivelDTO>> getAllNivels(Pageable pageable) {
        log.debug("REST request to get a page of Nivels");
        Page<NivelDTO> page = nivelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nivels/:id} : get the "id" nivel.
     *
     * @param id the id of the nivelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nivelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nivels/{id}")
    public ResponseEntity<NivelDTO> getNivel(@PathVariable Long id) {
        log.debug("REST request to get Nivel : {}", id);
        Optional<NivelDTO> nivelDTO = nivelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nivelDTO);
    }

    /**
     * {@code DELETE  /nivels/:id} : delete the "id" nivel.
     *
     * @param id the id of the nivelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nivels/{id}")
    public ResponseEntity<Void> deleteNivel(@PathVariable Long id) {
        log.debug("REST request to delete Nivel : {}", id);
        nivelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
