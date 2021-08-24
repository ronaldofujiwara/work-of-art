package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.TipoDocumentoRepository;
import br.com.nhw.std.artes.service.TipoDocumentoService;
import br.com.nhw.std.artes.service.dto.TipoDocumentoDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.TipoDocumento}.
 */
@RestController
@RequestMapping("/api")
public class TipoDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDocumentoResource.class);

    private static final String ENTITY_NAME = "tipoDocumento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDocumentoService tipoDocumentoService;

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoResource(TipoDocumentoService tipoDocumentoService, TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoService = tipoDocumentoService;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    /**
     * {@code POST  /tipo-documentos} : Create a new tipoDocumento.
     *
     * @param tipoDocumentoDTO the tipoDocumentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDocumentoDTO, or with status {@code 400 (Bad Request)} if the tipoDocumento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-documentos")
    public ResponseEntity<TipoDocumentoDTO> createTipoDocumento(@Valid @RequestBody TipoDocumentoDTO tipoDocumentoDTO)
        throws URISyntaxException {
        log.debug("REST request to save TipoDocumento : {}", tipoDocumentoDTO);
        if (tipoDocumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDocumentoDTO result = tipoDocumentoService.save(tipoDocumentoDTO);
        return ResponseEntity
            .created(new URI("/api/tipo-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-documentos/:id} : Updates an existing tipoDocumento.
     *
     * @param id the id of the tipoDocumentoDTO to save.
     * @param tipoDocumentoDTO the tipoDocumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDocumentoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoDocumentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDocumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-documentos/{id}")
    public ResponseEntity<TipoDocumentoDTO> updateTipoDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoDocumentoDTO tipoDocumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TipoDocumento : {}, {}", id, tipoDocumentoDTO);
        if (tipoDocumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDocumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDocumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoDocumentoDTO result = tipoDocumentoService.save(tipoDocumentoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDocumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-documentos/:id} : Partial updates given fields of an existing tipoDocumento, field will ignore if it is null
     *
     * @param id the id of the tipoDocumentoDTO to save.
     * @param tipoDocumentoDTO the tipoDocumentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDocumentoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoDocumentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoDocumentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoDocumentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-documentos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TipoDocumentoDTO> partialUpdateTipoDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoDocumentoDTO tipoDocumentoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoDocumento partially : {}, {}", id, tipoDocumentoDTO);
        if (tipoDocumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDocumentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDocumentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoDocumentoDTO> result = tipoDocumentoService.partialUpdate(tipoDocumentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDocumentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-documentos} : get all the tipoDocumentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDocumentos in body.
     */
    @GetMapping("/tipo-documentos")
    public ResponseEntity<List<TipoDocumentoDTO>> getAllTipoDocumentos(Pageable pageable) {
        log.debug("REST request to get a page of TipoDocumentos");
        Page<TipoDocumentoDTO> page = tipoDocumentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-documentos/:id} : get the "id" tipoDocumento.
     *
     * @param id the id of the tipoDocumentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDocumentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-documentos/{id}")
    public ResponseEntity<TipoDocumentoDTO> getTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to get TipoDocumento : {}", id);
        Optional<TipoDocumentoDTO> tipoDocumentoDTO = tipoDocumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDocumentoDTO);
    }

    /**
     * {@code DELETE  /tipo-documentos/:id} : delete the "id" tipoDocumento.
     *
     * @param id the id of the tipoDocumentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-documentos/{id}")
    public ResponseEntity<Void> deleteTipoDocumento(@PathVariable Long id) {
        log.debug("REST request to delete TipoDocumento : {}", id);
        tipoDocumentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
