package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Nivel;
import br.com.nhw.std.artes.repository.NivelRepository;
import br.com.nhw.std.artes.service.dto.NivelDTO;
import br.com.nhw.std.artes.service.mapper.NivelMapper;
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
 * Integration tests for the {@link NivelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NivelResourceIT {

    private static final String DEFAULT_NIVEL = "AAAAAAAAAA";
    private static final String UPDATED_NIVEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/nivels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private NivelMapper nivelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNivelMockMvc;

    private Nivel nivel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nivel createEntity(EntityManager em) {
        Nivel nivel = new Nivel().nivel(DEFAULT_NIVEL).inativo(DEFAULT_INATIVO);
        return nivel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nivel createUpdatedEntity(EntityManager em) {
        Nivel nivel = new Nivel().nivel(UPDATED_NIVEL).inativo(UPDATED_INATIVO);
        return nivel;
    }

    @BeforeEach
    public void initTest() {
        nivel = createEntity(em);
    }

    @Test
    @Transactional
    void createNivel() throws Exception {
        int databaseSizeBeforeCreate = nivelRepository.findAll().size();
        // Create the Nivel
        NivelDTO nivelDTO = nivelMapper.toDto(nivel);
        restNivelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelDTO)))
            .andExpect(status().isCreated());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeCreate + 1);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testNivel.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createNivelWithExistingId() throws Exception {
        // Create the Nivel with an existing ID
        nivel.setId(1L);
        NivelDTO nivelDTO = nivelMapper.toDto(nivel);

        int databaseSizeBeforeCreate = nivelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNivelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNivelIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelRepository.findAll().size();
        // set the field null
        nivel.setNivel(null);

        // Create the Nivel, which fails.
        NivelDTO nivelDTO = nivelMapper.toDto(nivel);

        restNivelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelDTO)))
            .andExpect(status().isBadRequest());

        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNivels() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        // Get all the nivelList
        restNivelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        // Get the nivel
        restNivelMockMvc
            .perform(get(ENTITY_API_URL_ID, nivel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nivel.getId().intValue()))
            .andExpect(jsonPath("$.nivel").value(DEFAULT_NIVEL))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingNivel() throws Exception {
        // Get the nivel
        restNivelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();

        // Update the nivel
        Nivel updatedNivel = nivelRepository.findById(nivel.getId()).get();
        // Disconnect from session so that the updates on updatedNivel are not directly saved in db
        em.detach(updatedNivel);
        updatedNivel.nivel(UPDATED_NIVEL).inativo(UPDATED_INATIVO);
        NivelDTO nivelDTO = nivelMapper.toDto(updatedNivel);

        restNivelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nivelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nivelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testNivel.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // Create the Nivel
        NivelDTO nivelDTO = nivelMapper.toDto(nivel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nivelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nivelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // Create the Nivel
        NivelDTO nivelDTO = nivelMapper.toDto(nivel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nivelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // Create the Nivel
        NivelDTO nivelDTO = nivelMapper.toDto(nivel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nivelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNivelWithPatch() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();

        // Update the nivel using partial update
        Nivel partialUpdatedNivel = new Nivel();
        partialUpdatedNivel.setId(nivel.getId());

        partialUpdatedNivel.nivel(UPDATED_NIVEL);

        restNivelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNivel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNivel))
            )
            .andExpect(status().isOk());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testNivel.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateNivelWithPatch() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();

        // Update the nivel using partial update
        Nivel partialUpdatedNivel = new Nivel();
        partialUpdatedNivel.setId(nivel.getId());

        partialUpdatedNivel.nivel(UPDATED_NIVEL).inativo(UPDATED_INATIVO);

        restNivelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNivel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNivel))
            )
            .andExpect(status().isOk());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
        Nivel testNivel = nivelList.get(nivelList.size() - 1);
        assertThat(testNivel.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testNivel.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // Create the Nivel
        NivelDTO nivelDTO = nivelMapper.toDto(nivel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nivelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nivelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // Create the Nivel
        NivelDTO nivelDTO = nivelMapper.toDto(nivel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nivelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNivel() throws Exception {
        int databaseSizeBeforeUpdate = nivelRepository.findAll().size();
        nivel.setId(count.incrementAndGet());

        // Create the Nivel
        NivelDTO nivelDTO = nivelMapper.toDto(nivel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNivelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nivelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nivel in the database
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNivel() throws Exception {
        // Initialize the database
        nivelRepository.saveAndFlush(nivel);

        int databaseSizeBeforeDelete = nivelRepository.findAll().size();

        // Delete the nivel
        restNivelMockMvc
            .perform(delete(ENTITY_API_URL_ID, nivel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nivel> nivelList = nivelRepository.findAll();
        assertThat(nivelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
