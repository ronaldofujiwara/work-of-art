package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.domain.Contato;
import br.com.nhw.std.artes.repository.ContatoRepository;
import br.com.nhw.std.artes.service.ContatoQueryService;
import br.com.nhw.std.artes.service.ContatoService;
import br.com.nhw.std.artes.service.criteria.ContatoCriteria;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.nhw.std.artes.domain.Contato}.
 */
@RestController
@RequestMapping("/api")
public class ContatoResource {

    private final Logger log = LoggerFactory.getLogger(ContatoResource.class);

    private static final String ENTITY_NAME = "contato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContatoService contatoService;

    private final ContatoRepository contatoRepository;

    private final ContatoQueryService contatoQueryService;

    public ContatoResource(ContatoService contatoService, ContatoRepository contatoRepository, ContatoQueryService contatoQueryService) {
        this.contatoService = contatoService;
        this.contatoRepository = contatoRepository;
        this.contatoQueryService = contatoQueryService;
    }

    /**
     * {@code POST  /contatoes} : Create a new contato.
     *
     * @param contato the contato to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contato, or with status {@code 400 (Bad Request)} if the contato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contatoes")
    public ResponseEntity<Contato> createContato(@Valid @RequestBody Contato contato) throws URISyntaxException {
        log.debug("REST request to save Contato : {}", contato);
        if (contato.getId() != null) {
            throw new BadRequestAlertException("A new contato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contato result = contatoService.save(contato);
        return ResponseEntity
            .created(new URI("/api/contatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contatoes/:id} : Updates an existing contato.
     *
     * @param id the id of the contato to save.
     * @param contato the contato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contato,
     * or with status {@code 400 (Bad Request)} if the contato is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contatoes/{id}")
    public ResponseEntity<Contato> updateContato(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Contato contato
    ) throws URISyntaxException {
        log.debug("REST request to update Contato : {}, {}", id, contato);
        if (contato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contato.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contatoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Contato result = contatoService.save(contato);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contato.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contatoes/:id} : Partial updates given fields of an existing contato, field will ignore if it is null
     *
     * @param id the id of the contato to save.
     * @param contato the contato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contato,
     * or with status {@code 400 (Bad Request)} if the contato is not valid,
     * or with status {@code 404 (Not Found)} if the contato is not found,
     * or with status {@code 500 (Internal Server Error)} if the contato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contatoes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Contato> partialUpdateContato(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Contato contato
    ) throws URISyntaxException {
        log.debug("REST request to partial update Contato partially : {}, {}", id, contato);
        if (contato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contato.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contatoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Contato> result = contatoService.partialUpdate(contato);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contato.getId().toString())
        );
    }

    /**
     * {@code GET  /contatoes} : get all the contatoes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contatoes in body.
     */
    @GetMapping("/contatoes")
    public ResponseEntity<List<Contato>> getAllContatoes(ContatoCriteria criteria) {
        log.debug("REST request to get Contatoes by criteria: {}", criteria);
        List<Contato> entityList = contatoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /contatoes/count} : count all the contatoes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contatoes/count")
    public ResponseEntity<Long> countContatoes(ContatoCriteria criteria) {
        log.debug("REST request to count Contatoes by criteria: {}", criteria);
        return ResponseEntity.ok().body(contatoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contatoes/:id} : get the "id" contato.
     *
     * @param id the id of the contato to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contato, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contatoes/{id}")
    public ResponseEntity<Contato> getContato(@PathVariable Long id) {
        log.debug("REST request to get Contato : {}", id);
        Optional<Contato> contato = contatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contato);
    }

    /**
     * {@code DELETE  /contatoes/:id} : delete the "id" contato.
     *
     * @param id the id of the contato to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contatoes/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        log.debug("REST request to delete Contato : {}", id);
        contatoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
