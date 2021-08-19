package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.AreaDepto;
import br.com.nhw.std.artes.domain.Contato;
import br.com.nhw.std.artes.repository.AreaDeptoRepository;
import br.com.nhw.std.artes.service.criteria.AreaDeptoCriteria;
import br.com.nhw.std.artes.service.dto.AreaDeptoDTO;
import br.com.nhw.std.artes.service.mapper.AreaDeptoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AreaDeptoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AreaDeptoResourceIT {

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String ENTITY_API_URL = "/api/area-deptos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AreaDeptoRepository areaDeptoRepository;

    @Autowired
    private AreaDeptoMapper areaDeptoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaDeptoMockMvc;

    private AreaDepto areaDepto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaDepto createEntity(EntityManager em) {
        AreaDepto areaDepto = new AreaDepto().area(DEFAULT_AREA).ativo(DEFAULT_ATIVO);
        return areaDepto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaDepto createUpdatedEntity(EntityManager em) {
        AreaDepto areaDepto = new AreaDepto().area(UPDATED_AREA).ativo(UPDATED_ATIVO);
        return areaDepto;
    }

    @BeforeEach
    public void initTest() {
        areaDepto = createEntity(em);
    }

    @Test
    @Transactional
    void createAreaDepto() throws Exception {
        int databaseSizeBeforeCreate = areaDeptoRepository.findAll().size();
        // Create the AreaDepto
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(areaDepto);
        restAreaDeptoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO)))
            .andExpect(status().isCreated());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeCreate + 1);
        AreaDepto testAreaDepto = areaDeptoList.get(areaDeptoList.size() - 1);
        assertThat(testAreaDepto.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testAreaDepto.getAtivo()).isEqualTo(DEFAULT_ATIVO);
    }

    @Test
    @Transactional
    void createAreaDeptoWithExistingId() throws Exception {
        // Create the AreaDepto with an existing ID
        areaDepto.setId(1L);
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(areaDepto);

        int databaseSizeBeforeCreate = areaDeptoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaDeptoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = areaDeptoRepository.findAll().size();
        // set the field null
        areaDepto.setArea(null);

        // Create the AreaDepto, which fails.
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(areaDepto);

        restAreaDeptoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO)))
            .andExpect(status().isBadRequest());

        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAreaDeptos() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList
        restAreaDeptoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaDepto.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getAreaDepto() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get the areaDepto
        restAreaDeptoMockMvc
            .perform(get(ENTITY_API_URL_ID, areaDepto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(areaDepto.getId().intValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getAreaDeptosByIdFiltering() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        Long id = areaDepto.getId();

        defaultAreaDeptoShouldBeFound("id.equals=" + id);
        defaultAreaDeptoShouldNotBeFound("id.notEquals=" + id);

        defaultAreaDeptoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAreaDeptoShouldNotBeFound("id.greaterThan=" + id);

        defaultAreaDeptoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAreaDeptoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where area equals to DEFAULT_AREA
        defaultAreaDeptoShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the areaDeptoList where area equals to UPDATED_AREA
        defaultAreaDeptoShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where area not equals to DEFAULT_AREA
        defaultAreaDeptoShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the areaDeptoList where area not equals to UPDATED_AREA
        defaultAreaDeptoShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where area in DEFAULT_AREA or UPDATED_AREA
        defaultAreaDeptoShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the areaDeptoList where area equals to UPDATED_AREA
        defaultAreaDeptoShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where area is not null
        defaultAreaDeptoShouldBeFound("area.specified=true");

        // Get all the areaDeptoList where area is null
        defaultAreaDeptoShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAreaContainsSomething() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where area contains DEFAULT_AREA
        defaultAreaDeptoShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the areaDeptoList where area contains UPDATED_AREA
        defaultAreaDeptoShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where area does not contain DEFAULT_AREA
        defaultAreaDeptoShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the areaDeptoList where area does not contain UPDATED_AREA
        defaultAreaDeptoShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAtivoIsEqualToSomething() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where ativo equals to DEFAULT_ATIVO
        defaultAreaDeptoShouldBeFound("ativo.equals=" + DEFAULT_ATIVO);

        // Get all the areaDeptoList where ativo equals to UPDATED_ATIVO
        defaultAreaDeptoShouldNotBeFound("ativo.equals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAtivoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where ativo not equals to DEFAULT_ATIVO
        defaultAreaDeptoShouldNotBeFound("ativo.notEquals=" + DEFAULT_ATIVO);

        // Get all the areaDeptoList where ativo not equals to UPDATED_ATIVO
        defaultAreaDeptoShouldBeFound("ativo.notEquals=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAtivoIsInShouldWork() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where ativo in DEFAULT_ATIVO or UPDATED_ATIVO
        defaultAreaDeptoShouldBeFound("ativo.in=" + DEFAULT_ATIVO + "," + UPDATED_ATIVO);

        // Get all the areaDeptoList where ativo equals to UPDATED_ATIVO
        defaultAreaDeptoShouldNotBeFound("ativo.in=" + UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void getAllAreaDeptosByAtivoIsNullOrNotNull() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        // Get all the areaDeptoList where ativo is not null
        defaultAreaDeptoShouldBeFound("ativo.specified=true");

        // Get all the areaDeptoList where ativo is null
        defaultAreaDeptoShouldNotBeFound("ativo.specified=false");
    }

    @Test
    @Transactional
    void getAllAreaDeptosByContatoIsEqualToSomething() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);
        Contato contato = ContatoResourceIT.createEntity(em);
        em.persist(contato);
        em.flush();
        areaDepto.addContato(contato);
        areaDeptoRepository.saveAndFlush(areaDepto);
        Long contatoId = contato.getId();

        // Get all the areaDeptoList where contato equals to contatoId
        defaultAreaDeptoShouldBeFound("contatoId.equals=" + contatoId);

        // Get all the areaDeptoList where contato equals to (contatoId + 1)
        defaultAreaDeptoShouldNotBeFound("contatoId.equals=" + (contatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAreaDeptoShouldBeFound(String filter) throws Exception {
        restAreaDeptoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaDepto.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));

        // Check, that the count call also returns 1
        restAreaDeptoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAreaDeptoShouldNotBeFound(String filter) throws Exception {
        restAreaDeptoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAreaDeptoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAreaDepto() throws Exception {
        // Get the areaDepto
        restAreaDeptoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAreaDepto() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        int databaseSizeBeforeUpdate = areaDeptoRepository.findAll().size();

        // Update the areaDepto
        AreaDepto updatedAreaDepto = areaDeptoRepository.findById(areaDepto.getId()).get();
        // Disconnect from session so that the updates on updatedAreaDepto are not directly saved in db
        em.detach(updatedAreaDepto);
        updatedAreaDepto.area(UPDATED_AREA).ativo(UPDATED_ATIVO);
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(updatedAreaDepto);

        restAreaDeptoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaDeptoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO))
            )
            .andExpect(status().isOk());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeUpdate);
        AreaDepto testAreaDepto = areaDeptoList.get(areaDeptoList.size() - 1);
        assertThat(testAreaDepto.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testAreaDepto.getAtivo()).isEqualTo(UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void putNonExistingAreaDepto() throws Exception {
        int databaseSizeBeforeUpdate = areaDeptoRepository.findAll().size();
        areaDepto.setId(count.incrementAndGet());

        // Create the AreaDepto
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(areaDepto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaDeptoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaDeptoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAreaDepto() throws Exception {
        int databaseSizeBeforeUpdate = areaDeptoRepository.findAll().size();
        areaDepto.setId(count.incrementAndGet());

        // Create the AreaDepto
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(areaDepto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaDeptoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAreaDepto() throws Exception {
        int databaseSizeBeforeUpdate = areaDeptoRepository.findAll().size();
        areaDepto.setId(count.incrementAndGet());

        // Create the AreaDepto
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(areaDepto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaDeptoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAreaDeptoWithPatch() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        int databaseSizeBeforeUpdate = areaDeptoRepository.findAll().size();

        // Update the areaDepto using partial update
        AreaDepto partialUpdatedAreaDepto = new AreaDepto();
        partialUpdatedAreaDepto.setId(areaDepto.getId());

        partialUpdatedAreaDepto.area(UPDATED_AREA).ativo(UPDATED_ATIVO);

        restAreaDeptoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaDepto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAreaDepto))
            )
            .andExpect(status().isOk());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeUpdate);
        AreaDepto testAreaDepto = areaDeptoList.get(areaDeptoList.size() - 1);
        assertThat(testAreaDepto.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testAreaDepto.getAtivo()).isEqualTo(UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void fullUpdateAreaDeptoWithPatch() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        int databaseSizeBeforeUpdate = areaDeptoRepository.findAll().size();

        // Update the areaDepto using partial update
        AreaDepto partialUpdatedAreaDepto = new AreaDepto();
        partialUpdatedAreaDepto.setId(areaDepto.getId());

        partialUpdatedAreaDepto.area(UPDATED_AREA).ativo(UPDATED_ATIVO);

        restAreaDeptoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaDepto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAreaDepto))
            )
            .andExpect(status().isOk());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeUpdate);
        AreaDepto testAreaDepto = areaDeptoList.get(areaDeptoList.size() - 1);
        assertThat(testAreaDepto.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testAreaDepto.getAtivo()).isEqualTo(UPDATED_ATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingAreaDepto() throws Exception {
        int databaseSizeBeforeUpdate = areaDeptoRepository.findAll().size();
        areaDepto.setId(count.incrementAndGet());

        // Create the AreaDepto
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(areaDepto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaDeptoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, areaDeptoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAreaDepto() throws Exception {
        int databaseSizeBeforeUpdate = areaDeptoRepository.findAll().size();
        areaDepto.setId(count.incrementAndGet());

        // Create the AreaDepto
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(areaDepto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaDeptoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAreaDepto() throws Exception {
        int databaseSizeBeforeUpdate = areaDeptoRepository.findAll().size();
        areaDepto.setId(count.incrementAndGet());

        // Create the AreaDepto
        AreaDeptoDTO areaDeptoDTO = areaDeptoMapper.toDto(areaDepto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaDeptoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(areaDeptoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaDepto in the database
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAreaDepto() throws Exception {
        // Initialize the database
        areaDeptoRepository.saveAndFlush(areaDepto);

        int databaseSizeBeforeDelete = areaDeptoRepository.findAll().size();

        // Delete the areaDepto
        restAreaDeptoMockMvc
            .perform(delete(ENTITY_API_URL_ID, areaDepto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AreaDepto> areaDeptoList = areaDeptoRepository.findAll();
        assertThat(areaDeptoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
