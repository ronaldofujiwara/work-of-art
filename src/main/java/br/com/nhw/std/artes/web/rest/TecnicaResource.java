package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.TecnicaRepository;
import br.com.nhw.std.artes.service.TecnicaService;
import br.com.nhw.std.artes.service.dto.TecnicaDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.Tecnica}.
 */
@RestController
@RequestMapping("/api")
public class TecnicaResource {

    private final Logger log = LoggerFactory.getLogger(TecnicaResource.class);

    private static final String ENTITY_NAME = "tecnica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TecnicaService tecnicaService;

    private final TecnicaRepository tecnicaRepository;

    public TecnicaResource(TecnicaService tecnicaService, TecnicaRepository tecnicaRepository) {
        this.tecnicaService = tecnicaService;
        this.tecnicaRepository = tecnicaRepository;
    }

    /**
     * {@code POST  /tecnicas} : Create a new tecnica.
     *
     * @param tecnicaDTO the tecnicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tecnicaDTO, or with status {@code 400 (Bad Request)} if the tecnica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tecnicas")
    public ResponseEntity<TecnicaDTO> createTecnica(@Valid @RequestBody TecnicaDTO tecnicaDTO) throws URISyntaxException {
        log.debug("REST request to save Tecnica : {}", tecnicaDTO);
        if (tecnicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tecnica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TecnicaDTO result = tecnicaService.save(tecnicaDTO);
        return ResponseEntity
            .created(new URI("/api/tecnicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tecnicas/:id} : Updates an existing tecnica.
     *
     * @param id the id of the tecnicaDTO to save.
     * @param tecnicaDTO the tecnicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tecnicaDTO,
     * or with status {@code 400 (Bad Request)} if the tecnicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tecnicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tecnicas/{id}")
    public ResponseEntity<TecnicaDTO> updateTecnica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TecnicaDTO tecnicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tecnica : {}, {}", id, tecnicaDTO);
        if (tecnicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tecnicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tecnicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TecnicaDTO result = tecnicaService.save(tecnicaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tecnicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tecnicas/:id} : Partial updates given fields of an existing tecnica, field will ignore if it is null
     *
     * @param id the id of the tecnicaDTO to save.
     * @param tecnicaDTO the tecnicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tecnicaDTO,
     * or with status {@code 400 (Bad Request)} if the tecnicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tecnicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tecnicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tecnicas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TecnicaDTO> partialUpdateTecnica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TecnicaDTO tecnicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tecnica partially : {}, {}", id, tecnicaDTO);
        if (tecnicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tecnicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tecnicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TecnicaDTO> result = tecnicaService.partialUpdate(tecnicaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tecnicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tecnicas} : get all the tecnicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tecnicas in body.
     */
    @GetMapping("/tecnicas")
    public ResponseEntity<List<TecnicaDTO>> getAllTecnicas(Pageable pageable) {
        log.debug("REST request to get a page of Tecnicas");
        Page<TecnicaDTO> page = tecnicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tecnicas/:id} : get the "id" tecnica.
     *
     * @param id the id of the tecnicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tecnicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tecnicas/{id}")
    public ResponseEntity<TecnicaDTO> getTecnica(@PathVariable Long id) {
        log.debug("REST request to get Tecnica : {}", id);
        Optional<TecnicaDTO> tecnicaDTO = tecnicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tecnicaDTO);
    }

    /**
     * {@code DELETE  /tecnicas/:id} : delete the "id" tecnica.
     *
     * @param id the id of the tecnicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tecnicas/{id}")
    public ResponseEntity<Void> deleteTecnica(@PathVariable Long id) {
        log.debug("REST request to delete Tecnica : {}", id);
        tecnicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
