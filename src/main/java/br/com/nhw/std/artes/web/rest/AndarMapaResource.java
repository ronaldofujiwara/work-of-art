package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.AndarMapaRepository;
import br.com.nhw.std.artes.service.AndarMapaService;
import br.com.nhw.std.artes.service.dto.AndarMapaDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.AndarMapa}.
 */
@RestController
@RequestMapping("/api")
public class AndarMapaResource {

    private final Logger log = LoggerFactory.getLogger(AndarMapaResource.class);

    private static final String ENTITY_NAME = "andarMapa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AndarMapaService andarMapaService;

    private final AndarMapaRepository andarMapaRepository;

    public AndarMapaResource(AndarMapaService andarMapaService, AndarMapaRepository andarMapaRepository) {
        this.andarMapaService = andarMapaService;
        this.andarMapaRepository = andarMapaRepository;
    }

    /**
     * {@code POST  /andar-mapas} : Create a new andarMapa.
     *
     * @param andarMapaDTO the andarMapaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new andarMapaDTO, or with status {@code 400 (Bad Request)} if the andarMapa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/andar-mapas")
    public ResponseEntity<AndarMapaDTO> createAndarMapa(@Valid @RequestBody AndarMapaDTO andarMapaDTO) throws URISyntaxException {
        log.debug("REST request to save AndarMapa : {}", andarMapaDTO);
        if (andarMapaDTO.getId() != null) {
            throw new BadRequestAlertException("A new andarMapa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AndarMapaDTO result = andarMapaService.save(andarMapaDTO);
        return ResponseEntity
            .created(new URI("/api/andar-mapas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /andar-mapas/:id} : Updates an existing andarMapa.
     *
     * @param id the id of the andarMapaDTO to save.
     * @param andarMapaDTO the andarMapaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated andarMapaDTO,
     * or with status {@code 400 (Bad Request)} if the andarMapaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the andarMapaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/andar-mapas/{id}")
    public ResponseEntity<AndarMapaDTO> updateAndarMapa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AndarMapaDTO andarMapaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AndarMapa : {}, {}", id, andarMapaDTO);
        if (andarMapaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, andarMapaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!andarMapaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AndarMapaDTO result = andarMapaService.save(andarMapaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, andarMapaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /andar-mapas/:id} : Partial updates given fields of an existing andarMapa, field will ignore if it is null
     *
     * @param id the id of the andarMapaDTO to save.
     * @param andarMapaDTO the andarMapaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated andarMapaDTO,
     * or with status {@code 400 (Bad Request)} if the andarMapaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the andarMapaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the andarMapaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/andar-mapas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AndarMapaDTO> partialUpdateAndarMapa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AndarMapaDTO andarMapaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AndarMapa partially : {}, {}", id, andarMapaDTO);
        if (andarMapaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, andarMapaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!andarMapaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AndarMapaDTO> result = andarMapaService.partialUpdate(andarMapaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, andarMapaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /andar-mapas} : get all the andarMapas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of andarMapas in body.
     */
    @GetMapping("/andar-mapas")
    public ResponseEntity<List<AndarMapaDTO>> getAllAndarMapas(Pageable pageable) {
        log.debug("REST request to get a page of AndarMapas");
        Page<AndarMapaDTO> page = andarMapaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /andar-mapas/:id} : get the "id" andarMapa.
     *
     * @param id the id of the andarMapaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the andarMapaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/andar-mapas/{id}")
    public ResponseEntity<AndarMapaDTO> getAndarMapa(@PathVariable Long id) {
        log.debug("REST request to get AndarMapa : {}", id);
        Optional<AndarMapaDTO> andarMapaDTO = andarMapaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(andarMapaDTO);
    }

    /**
     * {@code DELETE  /andar-mapas/:id} : delete the "id" andarMapa.
     *
     * @param id the id of the andarMapaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/andar-mapas/{id}")
    public ResponseEntity<Void> deleteAndarMapa(@PathVariable Long id) {
        log.debug("REST request to delete AndarMapa : {}", id);
        andarMapaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
