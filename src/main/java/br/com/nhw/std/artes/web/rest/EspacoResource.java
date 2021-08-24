package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.EspacoRepository;
import br.com.nhw.std.artes.service.EspacoService;
import br.com.nhw.std.artes.service.dto.EspacoDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.Espaco}.
 */
@RestController
@RequestMapping("/api")
public class EspacoResource {

    private final Logger log = LoggerFactory.getLogger(EspacoResource.class);

    private static final String ENTITY_NAME = "espaco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspacoService espacoService;

    private final EspacoRepository espacoRepository;

    public EspacoResource(EspacoService espacoService, EspacoRepository espacoRepository) {
        this.espacoService = espacoService;
        this.espacoRepository = espacoRepository;
    }

    /**
     * {@code POST  /espacos} : Create a new espaco.
     *
     * @param espacoDTO the espacoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new espacoDTO, or with status {@code 400 (Bad Request)} if the espaco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/espacos")
    public ResponseEntity<EspacoDTO> createEspaco(@Valid @RequestBody EspacoDTO espacoDTO) throws URISyntaxException {
        log.debug("REST request to save Espaco : {}", espacoDTO);
        if (espacoDTO.getId() != null) {
            throw new BadRequestAlertException("A new espaco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EspacoDTO result = espacoService.save(espacoDTO);
        return ResponseEntity
            .created(new URI("/api/espacos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /espacos/:id} : Updates an existing espaco.
     *
     * @param id the id of the espacoDTO to save.
     * @param espacoDTO the espacoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated espacoDTO,
     * or with status {@code 400 (Bad Request)} if the espacoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the espacoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/espacos/{id}")
    public ResponseEntity<EspacoDTO> updateEspaco(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EspacoDTO espacoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Espaco : {}, {}", id, espacoDTO);
        if (espacoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, espacoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!espacoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EspacoDTO result = espacoService.save(espacoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, espacoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /espacos/:id} : Partial updates given fields of an existing espaco, field will ignore if it is null
     *
     * @param id the id of the espacoDTO to save.
     * @param espacoDTO the espacoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated espacoDTO,
     * or with status {@code 400 (Bad Request)} if the espacoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the espacoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the espacoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/espacos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EspacoDTO> partialUpdateEspaco(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EspacoDTO espacoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Espaco partially : {}, {}", id, espacoDTO);
        if (espacoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, espacoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!espacoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EspacoDTO> result = espacoService.partialUpdate(espacoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, espacoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /espacos} : get all the espacos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of espacos in body.
     */
    @GetMapping("/espacos")
    public ResponseEntity<List<EspacoDTO>> getAllEspacos(Pageable pageable) {
        log.debug("REST request to get a page of Espacos");
        Page<EspacoDTO> page = espacoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /espacos/:id} : get the "id" espaco.
     *
     * @param id the id of the espacoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the espacoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/espacos/{id}")
    public ResponseEntity<EspacoDTO> getEspaco(@PathVariable Long id) {
        log.debug("REST request to get Espaco : {}", id);
        Optional<EspacoDTO> espacoDTO = espacoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(espacoDTO);
    }

    /**
     * {@code DELETE  /espacos/:id} : delete the "id" espaco.
     *
     * @param id the id of the espacoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/espacos/{id}")
    public ResponseEntity<Void> deleteEspaco(@PathVariable Long id) {
        log.debug("REST request to delete Espaco : {}", id);
        espacoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
