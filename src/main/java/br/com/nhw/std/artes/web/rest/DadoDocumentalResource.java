package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.DadoDocumentalRepository;
import br.com.nhw.std.artes.service.DadoDocumentalService;
import br.com.nhw.std.artes.service.dto.DadoDocumentalDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.DadoDocumental}.
 */
@RestController
@RequestMapping("/api")
public class DadoDocumentalResource {

    private final Logger log = LoggerFactory.getLogger(DadoDocumentalResource.class);

    private static final String ENTITY_NAME = "dadoDocumental";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DadoDocumentalService dadoDocumentalService;

    private final DadoDocumentalRepository dadoDocumentalRepository;

    public DadoDocumentalResource(DadoDocumentalService dadoDocumentalService, DadoDocumentalRepository dadoDocumentalRepository) {
        this.dadoDocumentalService = dadoDocumentalService;
        this.dadoDocumentalRepository = dadoDocumentalRepository;
    }

    /**
     * {@code POST  /dado-documentals} : Create a new dadoDocumental.
     *
     * @param dadoDocumentalDTO the dadoDocumentalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dadoDocumentalDTO, or with status {@code 400 (Bad Request)} if the dadoDocumental has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dado-documentals")
    public ResponseEntity<DadoDocumentalDTO> createDadoDocumental(@Valid @RequestBody DadoDocumentalDTO dadoDocumentalDTO)
        throws URISyntaxException {
        log.debug("REST request to save DadoDocumental : {}", dadoDocumentalDTO);
        if (dadoDocumentalDTO.getId() != null) {
            throw new BadRequestAlertException("A new dadoDocumental cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DadoDocumentalDTO result = dadoDocumentalService.save(dadoDocumentalDTO);
        return ResponseEntity
            .created(new URI("/api/dado-documentals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dado-documentals/:id} : Updates an existing dadoDocumental.
     *
     * @param id the id of the dadoDocumentalDTO to save.
     * @param dadoDocumentalDTO the dadoDocumentalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadoDocumentalDTO,
     * or with status {@code 400 (Bad Request)} if the dadoDocumentalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dadoDocumentalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dado-documentals/{id}")
    public ResponseEntity<DadoDocumentalDTO> updateDadoDocumental(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DadoDocumentalDTO dadoDocumentalDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DadoDocumental : {}, {}", id, dadoDocumentalDTO);
        if (dadoDocumentalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dadoDocumentalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dadoDocumentalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DadoDocumentalDTO result = dadoDocumentalService.save(dadoDocumentalDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadoDocumentalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dado-documentals/:id} : Partial updates given fields of an existing dadoDocumental, field will ignore if it is null
     *
     * @param id the id of the dadoDocumentalDTO to save.
     * @param dadoDocumentalDTO the dadoDocumentalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadoDocumentalDTO,
     * or with status {@code 400 (Bad Request)} if the dadoDocumentalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dadoDocumentalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dadoDocumentalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dado-documentals/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DadoDocumentalDTO> partialUpdateDadoDocumental(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DadoDocumentalDTO dadoDocumentalDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DadoDocumental partially : {}, {}", id, dadoDocumentalDTO);
        if (dadoDocumentalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dadoDocumentalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dadoDocumentalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DadoDocumentalDTO> result = dadoDocumentalService.partialUpdate(dadoDocumentalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadoDocumentalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dado-documentals} : get all the dadoDocumentals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dadoDocumentals in body.
     */
    @GetMapping("/dado-documentals")
    public ResponseEntity<List<DadoDocumentalDTO>> getAllDadoDocumentals(Pageable pageable) {
        log.debug("REST request to get a page of DadoDocumentals");
        Page<DadoDocumentalDTO> page = dadoDocumentalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dado-documentals/:id} : get the "id" dadoDocumental.
     *
     * @param id the id of the dadoDocumentalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dadoDocumentalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dado-documentals/{id}")
    public ResponseEntity<DadoDocumentalDTO> getDadoDocumental(@PathVariable Long id) {
        log.debug("REST request to get DadoDocumental : {}", id);
        Optional<DadoDocumentalDTO> dadoDocumentalDTO = dadoDocumentalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dadoDocumentalDTO);
    }

    /**
     * {@code DELETE  /dado-documentals/:id} : delete the "id" dadoDocumental.
     *
     * @param id the id of the dadoDocumentalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dado-documentals/{id}")
    public ResponseEntity<Void> deleteDadoDocumental(@PathVariable Long id) {
        log.debug("REST request to delete DadoDocumental : {}", id);
        dadoDocumentalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
