package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.FuncaoArtistaRepository;
import br.com.nhw.std.artes.service.FuncaoArtistaService;
import br.com.nhw.std.artes.service.dto.FuncaoArtistaDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.FuncaoArtista}.
 */
@RestController
@RequestMapping("/api")
public class FuncaoArtistaResource {

    private final Logger log = LoggerFactory.getLogger(FuncaoArtistaResource.class);

    private static final String ENTITY_NAME = "funcaoArtista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncaoArtistaService funcaoArtistaService;

    private final FuncaoArtistaRepository funcaoArtistaRepository;

    public FuncaoArtistaResource(FuncaoArtistaService funcaoArtistaService, FuncaoArtistaRepository funcaoArtistaRepository) {
        this.funcaoArtistaService = funcaoArtistaService;
        this.funcaoArtistaRepository = funcaoArtistaRepository;
    }

    /**
     * {@code POST  /funcao-artistas} : Create a new funcaoArtista.
     *
     * @param funcaoArtistaDTO the funcaoArtistaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcaoArtistaDTO, or with status {@code 400 (Bad Request)} if the funcaoArtista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funcao-artistas")
    public ResponseEntity<FuncaoArtistaDTO> createFuncaoArtista(@Valid @RequestBody FuncaoArtistaDTO funcaoArtistaDTO)
        throws URISyntaxException {
        log.debug("REST request to save FuncaoArtista : {}", funcaoArtistaDTO);
        if (funcaoArtistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new funcaoArtista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuncaoArtistaDTO result = funcaoArtistaService.save(funcaoArtistaDTO);
        return ResponseEntity
            .created(new URI("/api/funcao-artistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funcao-artistas/:id} : Updates an existing funcaoArtista.
     *
     * @param id the id of the funcaoArtistaDTO to save.
     * @param funcaoArtistaDTO the funcaoArtistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcaoArtistaDTO,
     * or with status {@code 400 (Bad Request)} if the funcaoArtistaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcaoArtistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funcao-artistas/{id}")
    public ResponseEntity<FuncaoArtistaDTO> updateFuncaoArtista(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FuncaoArtistaDTO funcaoArtistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FuncaoArtista : {}, {}", id, funcaoArtistaDTO);
        if (funcaoArtistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcaoArtistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcaoArtistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuncaoArtistaDTO result = funcaoArtistaService.save(funcaoArtistaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcaoArtistaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /funcao-artistas/:id} : Partial updates given fields of an existing funcaoArtista, field will ignore if it is null
     *
     * @param id the id of the funcaoArtistaDTO to save.
     * @param funcaoArtistaDTO the funcaoArtistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcaoArtistaDTO,
     * or with status {@code 400 (Bad Request)} if the funcaoArtistaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the funcaoArtistaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the funcaoArtistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/funcao-artistas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FuncaoArtistaDTO> partialUpdateFuncaoArtista(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FuncaoArtistaDTO funcaoArtistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuncaoArtista partially : {}, {}", id, funcaoArtistaDTO);
        if (funcaoArtistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcaoArtistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcaoArtistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuncaoArtistaDTO> result = funcaoArtistaService.partialUpdate(funcaoArtistaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcaoArtistaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /funcao-artistas} : get all the funcaoArtistas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcaoArtistas in body.
     */
    @GetMapping("/funcao-artistas")
    public ResponseEntity<List<FuncaoArtistaDTO>> getAllFuncaoArtistas(Pageable pageable) {
        log.debug("REST request to get a page of FuncaoArtistas");
        Page<FuncaoArtistaDTO> page = funcaoArtistaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /funcao-artistas/:id} : get the "id" funcaoArtista.
     *
     * @param id the id of the funcaoArtistaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcaoArtistaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funcao-artistas/{id}")
    public ResponseEntity<FuncaoArtistaDTO> getFuncaoArtista(@PathVariable Long id) {
        log.debug("REST request to get FuncaoArtista : {}", id);
        Optional<FuncaoArtistaDTO> funcaoArtistaDTO = funcaoArtistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funcaoArtistaDTO);
    }

    /**
     * {@code DELETE  /funcao-artistas/:id} : delete the "id" funcaoArtista.
     *
     * @param id the id of the funcaoArtistaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funcao-artistas/{id}")
    public ResponseEntity<Void> deleteFuncaoArtista(@PathVariable Long id) {
        log.debug("REST request to delete FuncaoArtista : {}", id);
        funcaoArtistaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
