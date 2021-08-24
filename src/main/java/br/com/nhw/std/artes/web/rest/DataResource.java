package br.com.nhw.std.artes.web.rest;

import br.com.nhw.std.artes.repository.DataRepository;
import br.com.nhw.std.artes.service.DataService;
import br.com.nhw.std.artes.service.dto.DataDTO;
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
 * REST controller for managing {@link br.com.nhw.std.artes.domain.Data}.
 */
@RestController
@RequestMapping("/api")
public class DataResource {

    private final Logger log = LoggerFactory.getLogger(DataResource.class);

    private static final String ENTITY_NAME = "data";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataService dataService;

    private final DataRepository dataRepository;

    public DataResource(DataService dataService, DataRepository dataRepository) {
        this.dataService = dataService;
        this.dataRepository = dataRepository;
    }

    /**
     * {@code POST  /data} : Create a new data.
     *
     * @param dataDTO the dataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataDTO, or with status {@code 400 (Bad Request)} if the data has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data")
    public ResponseEntity<DataDTO> createData(@Valid @RequestBody DataDTO dataDTO) throws URISyntaxException {
        log.debug("REST request to save Data : {}", dataDTO);
        if (dataDTO.getId() != null) {
            throw new BadRequestAlertException("A new data cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataDTO result = dataService.save(dataDTO);
        return ResponseEntity
            .created(new URI("/api/data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data/:id} : Updates an existing data.
     *
     * @param id the id of the dataDTO to save.
     * @param dataDTO the dataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataDTO,
     * or with status {@code 400 (Bad Request)} if the dataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data/{id}")
    public ResponseEntity<DataDTO> updateData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DataDTO dataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Data : {}, {}", id, dataDTO);
        if (dataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DataDTO result = dataService.save(dataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /data/:id} : Partial updates given fields of an existing data, field will ignore if it is null
     *
     * @param id the id of the dataDTO to save.
     * @param dataDTO the dataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataDTO,
     * or with status {@code 400 (Bad Request)} if the dataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DataDTO> partialUpdateData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DataDTO dataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Data partially : {}, {}", id, dataDTO);
        if (dataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DataDTO> result = dataService.partialUpdate(dataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /data} : get all the data.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of data in body.
     */
    @GetMapping("/data")
    public ResponseEntity<List<DataDTO>> getAllData(Pageable pageable) {
        log.debug("REST request to get a page of Data");
        Page<DataDTO> page = dataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /data/:id} : get the "id" data.
     *
     * @param id the id of the dataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data/{id}")
    public ResponseEntity<DataDTO> getData(@PathVariable Long id) {
        log.debug("REST request to get Data : {}", id);
        Optional<DataDTO> dataDTO = dataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataDTO);
    }

    /**
     * {@code DELETE  /data/:id} : delete the "id" data.
     *
     * @param id the id of the dataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Long id) {
        log.debug("REST request to delete Data : {}", id);
        dataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
