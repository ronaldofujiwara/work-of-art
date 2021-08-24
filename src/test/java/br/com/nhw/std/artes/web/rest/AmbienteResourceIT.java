package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Ambiente;
import br.com.nhw.std.artes.repository.AmbienteRepository;
import br.com.nhw.std.artes.service.criteria.AmbienteCriteria;
import br.com.nhw.std.artes.service.dto.AmbienteDTO;
import br.com.nhw.std.artes.service.mapper.AmbienteMapper;
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
 * Integration tests for the {@link AmbienteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AmbienteResourceIT {

    private static final String DEFAULT_AMBIENTE = "AAAAAAAAAA";
    private static final String UPDATED_AMBIENTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/ambientes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private AmbienteMapper ambienteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmbienteMockMvc;

    private Ambiente ambiente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ambiente createEntity(EntityManager em) {
        Ambiente ambiente = new Ambiente().ambiente(DEFAULT_AMBIENTE).inativo(DEFAULT_INATIVO);
        return ambiente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ambiente createUpdatedEntity(EntityManager em) {
        Ambiente ambiente = new Ambiente().ambiente(UPDATED_AMBIENTE).inativo(UPDATED_INATIVO);
        return ambiente;
    }

    @BeforeEach
    public void initTest() {
        ambiente = createEntity(em);
    }

    @Test
    @Transactional
    void createAmbiente() throws Exception {
        int databaseSizeBeforeCreate = ambienteRepository.findAll().size();
        // Create the Ambiente
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(ambiente);
        restAmbienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ambienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeCreate + 1);
        Ambiente testAmbiente = ambienteList.get(ambienteList.size() - 1);
        assertThat(testAmbiente.getAmbiente()).isEqualTo(DEFAULT_AMBIENTE);
        assertThat(testAmbiente.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createAmbienteWithExistingId() throws Exception {
        // Create the Ambiente with an existing ID
        ambiente.setId(1L);
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(ambiente);

        int databaseSizeBeforeCreate = ambienteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmbienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ambienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmbienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = ambienteRepository.findAll().size();
        // set the field null
        ambiente.setAmbiente(null);

        // Create the Ambiente, which fails.
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(ambiente);

        restAmbienteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ambienteDTO)))
            .andExpect(status().isBadRequest());

        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAmbientes() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList
        restAmbienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ambiente.getId().intValue())))
            .andExpect(jsonPath("$.[*].ambiente").value(hasItem(DEFAULT_AMBIENTE)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getAmbiente() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get the ambiente
        restAmbienteMockMvc
            .perform(get(ENTITY_API_URL_ID, ambiente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ambiente.getId().intValue()))
            .andExpect(jsonPath("$.ambiente").value(DEFAULT_AMBIENTE))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getAmbientesByIdFiltering() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        Long id = ambiente.getId();

        defaultAmbienteShouldBeFound("id.equals=" + id);
        defaultAmbienteShouldNotBeFound("id.notEquals=" + id);

        defaultAmbienteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAmbienteShouldNotBeFound("id.greaterThan=" + id);

        defaultAmbienteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAmbienteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAmbientesByAmbienteIsEqualToSomething() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where ambiente equals to DEFAULT_AMBIENTE
        defaultAmbienteShouldBeFound("ambiente.equals=" + DEFAULT_AMBIENTE);

        // Get all the ambienteList where ambiente equals to UPDATED_AMBIENTE
        defaultAmbienteShouldNotBeFound("ambiente.equals=" + UPDATED_AMBIENTE);
    }

    @Test
    @Transactional
    void getAllAmbientesByAmbienteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where ambiente not equals to DEFAULT_AMBIENTE
        defaultAmbienteShouldNotBeFound("ambiente.notEquals=" + DEFAULT_AMBIENTE);

        // Get all the ambienteList where ambiente not equals to UPDATED_AMBIENTE
        defaultAmbienteShouldBeFound("ambiente.notEquals=" + UPDATED_AMBIENTE);
    }

    @Test
    @Transactional
    void getAllAmbientesByAmbienteIsInShouldWork() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where ambiente in DEFAULT_AMBIENTE or UPDATED_AMBIENTE
        defaultAmbienteShouldBeFound("ambiente.in=" + DEFAULT_AMBIENTE + "," + UPDATED_AMBIENTE);

        // Get all the ambienteList where ambiente equals to UPDATED_AMBIENTE
        defaultAmbienteShouldNotBeFound("ambiente.in=" + UPDATED_AMBIENTE);
    }

    @Test
    @Transactional
    void getAllAmbientesByAmbienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where ambiente is not null
        defaultAmbienteShouldBeFound("ambiente.specified=true");

        // Get all the ambienteList where ambiente is null
        defaultAmbienteShouldNotBeFound("ambiente.specified=false");
    }

    @Test
    @Transactional
    void getAllAmbientesByAmbienteContainsSomething() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where ambiente contains DEFAULT_AMBIENTE
        defaultAmbienteShouldBeFound("ambiente.contains=" + DEFAULT_AMBIENTE);

        // Get all the ambienteList where ambiente contains UPDATED_AMBIENTE
        defaultAmbienteShouldNotBeFound("ambiente.contains=" + UPDATED_AMBIENTE);
    }

    @Test
    @Transactional
    void getAllAmbientesByAmbienteNotContainsSomething() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where ambiente does not contain DEFAULT_AMBIENTE
        defaultAmbienteShouldNotBeFound("ambiente.doesNotContain=" + DEFAULT_AMBIENTE);

        // Get all the ambienteList where ambiente does not contain UPDATED_AMBIENTE
        defaultAmbienteShouldBeFound("ambiente.doesNotContain=" + UPDATED_AMBIENTE);
    }

    @Test
    @Transactional
    void getAllAmbientesByInativoIsEqualToSomething() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where inativo equals to DEFAULT_INATIVO
        defaultAmbienteShouldBeFound("inativo.equals=" + DEFAULT_INATIVO);

        // Get all the ambienteList where inativo equals to UPDATED_INATIVO
        defaultAmbienteShouldNotBeFound("inativo.equals=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllAmbientesByInativoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where inativo not equals to DEFAULT_INATIVO
        defaultAmbienteShouldNotBeFound("inativo.notEquals=" + DEFAULT_INATIVO);

        // Get all the ambienteList where inativo not equals to UPDATED_INATIVO
        defaultAmbienteShouldBeFound("inativo.notEquals=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllAmbientesByInativoIsInShouldWork() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where inativo in DEFAULT_INATIVO or UPDATED_INATIVO
        defaultAmbienteShouldBeFound("inativo.in=" + DEFAULT_INATIVO + "," + UPDATED_INATIVO);

        // Get all the ambienteList where inativo equals to UPDATED_INATIVO
        defaultAmbienteShouldNotBeFound("inativo.in=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllAmbientesByInativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        // Get all the ambienteList where inativo is not null
        defaultAmbienteShouldBeFound("inativo.specified=true");

        // Get all the ambienteList where inativo is null
        defaultAmbienteShouldNotBeFound("inativo.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAmbienteShouldBeFound(String filter) throws Exception {
        restAmbienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ambiente.getId().intValue())))
            .andExpect(jsonPath("$.[*].ambiente").value(hasItem(DEFAULT_AMBIENTE)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));

        // Check, that the count call also returns 1
        restAmbienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAmbienteShouldNotBeFound(String filter) throws Exception {
        restAmbienteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAmbienteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAmbiente() throws Exception {
        // Get the ambiente
        restAmbienteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAmbiente() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();

        // Update the ambiente
        Ambiente updatedAmbiente = ambienteRepository.findById(ambiente.getId()).get();
        // Disconnect from session so that the updates on updatedAmbiente are not directly saved in db
        em.detach(updatedAmbiente);
        updatedAmbiente.ambiente(UPDATED_AMBIENTE).inativo(UPDATED_INATIVO);
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(updatedAmbiente);

        restAmbienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ambienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ambienteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
        Ambiente testAmbiente = ambienteList.get(ambienteList.size() - 1);
        assertThat(testAmbiente.getAmbiente()).isEqualTo(UPDATED_AMBIENTE);
        assertThat(testAmbiente.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingAmbiente() throws Exception {
        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();
        ambiente.setId(count.incrementAndGet());

        // Create the Ambiente
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(ambiente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmbienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ambienteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ambienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAmbiente() throws Exception {
        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();
        ambiente.setId(count.incrementAndGet());

        // Create the Ambiente
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(ambiente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmbienteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ambienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAmbiente() throws Exception {
        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();
        ambiente.setId(count.incrementAndGet());

        // Create the Ambiente
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(ambiente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmbienteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ambienteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAmbienteWithPatch() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();

        // Update the ambiente using partial update
        Ambiente partialUpdatedAmbiente = new Ambiente();
        partialUpdatedAmbiente.setId(ambiente.getId());

        partialUpdatedAmbiente.inativo(UPDATED_INATIVO);

        restAmbienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmbiente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAmbiente))
            )
            .andExpect(status().isOk());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
        Ambiente testAmbiente = ambienteList.get(ambienteList.size() - 1);
        assertThat(testAmbiente.getAmbiente()).isEqualTo(DEFAULT_AMBIENTE);
        assertThat(testAmbiente.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateAmbienteWithPatch() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();

        // Update the ambiente using partial update
        Ambiente partialUpdatedAmbiente = new Ambiente();
        partialUpdatedAmbiente.setId(ambiente.getId());

        partialUpdatedAmbiente.ambiente(UPDATED_AMBIENTE).inativo(UPDATED_INATIVO);

        restAmbienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmbiente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAmbiente))
            )
            .andExpect(status().isOk());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
        Ambiente testAmbiente = ambienteList.get(ambienteList.size() - 1);
        assertThat(testAmbiente.getAmbiente()).isEqualTo(UPDATED_AMBIENTE);
        assertThat(testAmbiente.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingAmbiente() throws Exception {
        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();
        ambiente.setId(count.incrementAndGet());

        // Create the Ambiente
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(ambiente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmbienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ambienteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ambienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAmbiente() throws Exception {
        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();
        ambiente.setId(count.incrementAndGet());

        // Create the Ambiente
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(ambiente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmbienteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ambienteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAmbiente() throws Exception {
        int databaseSizeBeforeUpdate = ambienteRepository.findAll().size();
        ambiente.setId(count.incrementAndGet());

        // Create the Ambiente
        AmbienteDTO ambienteDTO = ambienteMapper.toDto(ambiente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmbienteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ambienteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ambiente in the database
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAmbiente() throws Exception {
        // Initialize the database
        ambienteRepository.saveAndFlush(ambiente);

        int databaseSizeBeforeDelete = ambienteRepository.findAll().size();

        // Delete the ambiente
        restAmbienteMockMvc
            .perform(delete(ENTITY_API_URL_ID, ambiente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ambiente> ambienteList = ambienteRepository.findAll();
        assertThat(ambienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
