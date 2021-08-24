package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.MoedaRepository;
import br.com.nhw.std.artes.service.MoedaService;
import br.com.nhw.std.artes.service.dto.MoedaDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.Moeda}.
 */
@RestController
@RequestMapping("/api")
public class MoedaResource {

    private final Logger log = LoggerFactory.getLogger(MoedaResource.class);

    private static final String ENTITY_NAME = "moeda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoedaService moedaService;

    private final MoedaRepository moedaRepository;

    public MoedaResource(MoedaService moedaService, MoedaRepository moedaRepository) {
        this.moedaService = moedaService;
        this.moedaRepository = moedaRepository;
    }

    /**
     * {@code POST  /moedas} : Create a new moeda.
     *
     * @param moedaDTO the moedaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moedaDTO, or with status {@code 400 (Bad Request)} if the moeda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moedas")
    public ResponseEntity<MoedaDTO> createMoeda(@Valid @RequestBody MoedaDTO moedaDTO) throws URISyntaxException {
        log.debug("REST request to save Moeda : {}", moedaDTO);
        if (moedaDTO.getId() != null) {
            throw new BadRequestAlertException("A new moeda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MoedaDTO result = moedaService.save(moedaDTO);
        return ResponseEntity
            .created(new URI("/api/moedas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moedas/:id} : Updates an existing moeda.
     *
     * @param id the id of the moedaDTO to save.
     * @param moedaDTO the moedaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moedaDTO,
     * or with status {@code 400 (Bad Request)} if the moedaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moedaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moedas/{id}")
    public ResponseEntity<MoedaDTO> updateMoeda(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MoedaDTO moedaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Moeda : {}, {}", id, moedaDTO);
        if (moedaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moedaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moedaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MoedaDTO result = moedaService.save(moedaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moedaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /moedas/:id} : Partial updates given fields of an existing moeda, field will ignore if it is null
     *
     * @param id the id of the moedaDTO to save.
     * @param moedaDTO the moedaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moedaDTO,
     * or with status {@code 400 (Bad Request)} if the moedaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moedaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moedaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/moedas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MoedaDTO> partialUpdateMoeda(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MoedaDTO moedaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Moeda partially : {}, {}", id, moedaDTO);
        if (moedaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moedaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moedaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MoedaDTO> result = moedaService.partialUpdate(moedaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moedaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /moedas} : get all the moedas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moedas in body.
     */
    @GetMapping("/moedas")
    public ResponseEntity<List<MoedaDTO>> getAllMoedas(Pageable pageable) {
        log.debug("REST request to get a page of Moedas");
        Page<MoedaDTO> page = moedaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /moedas/:id} : get the "id" moeda.
     *
     * @param id the id of the moedaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moedaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moedas/{id}")
    public ResponseEntity<MoedaDTO> getMoeda(@PathVariable Long id) {
        log.debug("REST request to get Moeda : {}", id);
        Optional<MoedaDTO> moedaDTO = moedaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moedaDTO);
    }

    /**
     * {@code DELETE  /moedas/:id} : delete the "id" moeda.
     *
     * @param id the id of the moedaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moedas/{id}")
    public ResponseEntity<Void> deleteMoeda(@PathVariable Long id) {
        log.debug("REST request to delete Moeda : {}", id);
        moedaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
