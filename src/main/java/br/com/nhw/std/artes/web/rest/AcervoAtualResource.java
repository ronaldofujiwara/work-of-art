package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.AcervoAtualRepository;
import br.com.nhw.std.artes.service.AcervoAtualService;
import br.com.nhw.std.artes.service.dto.AcervoAtualDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.AcervoAtual}.
 */
@RestController
@RequestMapping("/api")
public class AcervoAtualResource {

    private final Logger log = LoggerFactory.getLogger(AcervoAtualResource.class);

    private static final String ENTITY_NAME = "acervoAtual";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcervoAtualService acervoAtualService;

    private final AcervoAtualRepository acervoAtualRepository;

    public AcervoAtualResource(AcervoAtualService acervoAtualService, AcervoAtualRepository acervoAtualRepository) {
        this.acervoAtualService = acervoAtualService;
        this.acervoAtualRepository = acervoAtualRepository;
    }

    /**
     * {@code POST  /acervo-atuals} : Create a new acervoAtual.
     *
     * @param acervoAtualDTO the acervoAtualDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acervoAtualDTO, or with status {@code 400 (Bad Request)} if the acervoAtual has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acervo-atuals")
    public ResponseEntity<AcervoAtualDTO> createAcervoAtual(@Valid @RequestBody AcervoAtualDTO acervoAtualDTO) throws URISyntaxException {
        log.debug("REST request to save AcervoAtual : {}", acervoAtualDTO);
        if (acervoAtualDTO.getId() != null) {
            throw new BadRequestAlertException("A new acervoAtual cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcervoAtualDTO result = acervoAtualService.save(acervoAtualDTO);
        return ResponseEntity
            .created(new URI("/api/acervo-atuals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acervo-atuals/:id} : Updates an existing acervoAtual.
     *
     * @param id the id of the acervoAtualDTO to save.
     * @param acervoAtualDTO the acervoAtualDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acervoAtualDTO,
     * or with status {@code 400 (Bad Request)} if the acervoAtualDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acervoAtualDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acervo-atuals/{id}")
    public ResponseEntity<AcervoAtualDTO> updateAcervoAtual(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AcervoAtualDTO acervoAtualDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AcervoAtual : {}, {}", id, acervoAtualDTO);
        if (acervoAtualDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acervoAtualDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acervoAtualRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AcervoAtualDTO result = acervoAtualService.save(acervoAtualDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, acervoAtualDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /acervo-atuals/:id} : Partial updates given fields of an existing acervoAtual, field will ignore if it is null
     *
     * @param id the id of the acervoAtualDTO to save.
     * @param acervoAtualDTO the acervoAtualDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acervoAtualDTO,
     * or with status {@code 400 (Bad Request)} if the acervoAtualDTO is not valid,
     * or with status {@code 404 (Not Found)} if the acervoAtualDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the acervoAtualDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/acervo-atuals/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AcervoAtualDTO> partialUpdateAcervoAtual(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AcervoAtualDTO acervoAtualDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AcervoAtual partially : {}, {}", id, acervoAtualDTO);
        if (acervoAtualDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acervoAtualDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acervoAtualRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcervoAtualDTO> result = acervoAtualService.partialUpdate(acervoAtualDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, acervoAtualDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /acervo-atuals} : get all the acervoAtuals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acervoAtuals in body.
     */
    @GetMapping("/acervo-atuals")
    public ResponseEntity<List<AcervoAtualDTO>> getAllAcervoAtuals(Pageable pageable) {
        log.debug("REST request to get a page of AcervoAtuals");
        Page<AcervoAtualDTO> page = acervoAtualService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /acervo-atuals/:id} : get the "id" acervoAtual.
     *
     * @param id the id of the acervoAtualDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acervoAtualDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acervo-atuals/{id}")
    public ResponseEntity<AcervoAtualDTO> getAcervoAtual(@PathVariable Long id) {
        log.debug("REST request to get AcervoAtual : {}", id);
        Optional<AcervoAtualDTO> acervoAtualDTO = acervoAtualService.findOne(id);
        return ResponseUtil.wrapOrNotFound(acervoAtualDTO);
    }

    /**
     * {@code DELETE  /acervo-atuals/:id} : delete the "id" acervoAtual.
     *
     * @param id the id of the acervoAtualDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acervo-atuals/{id}")
    public ResponseEntity<Void> deleteAcervoAtual(@PathVariable Long id) {
        log.debug("REST request to delete AcervoAtual : {}", id);
        acervoAtualService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
