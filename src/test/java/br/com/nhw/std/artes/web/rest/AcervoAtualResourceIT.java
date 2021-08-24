package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.AcervoAtual;
import br.com.nhw.std.artes.repository.AcervoAtualRepository;
import br.com.nhw.std.artes.service.dto.AcervoAtualDTO;
import br.com.nhw.std.artes.service.mapper.AcervoAtualMapper;
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
 * Integration tests for the {@link AcervoAtualResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcervoAtualResourceIT {

    private static final String DEFAULT_ACERVO_ATUAL = "AAAAAAAAAA";
    private static final String UPDATED_ACERVO_ATUAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/acervo-atuals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcervoAtualRepository acervoAtualRepository;

    @Autowired
    private AcervoAtualMapper acervoAtualMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcervoAtualMockMvc;

    private AcervoAtual acervoAtual;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcervoAtual createEntity(EntityManager em) {
        AcervoAtual acervoAtual = new AcervoAtual().acervoAtual(DEFAULT_ACERVO_ATUAL).inativo(DEFAULT_INATIVO);
        return acervoAtual;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcervoAtual createUpdatedEntity(EntityManager em) {
        AcervoAtual acervoAtual = new AcervoAtual().acervoAtual(UPDATED_ACERVO_ATUAL).inativo(UPDATED_INATIVO);
        return acervoAtual;
    }

    @BeforeEach
    public void initTest() {
        acervoAtual = createEntity(em);
    }

    @Test
    @Transactional
    void createAcervoAtual() throws Exception {
        int databaseSizeBeforeCreate = acervoAtualRepository.findAll().size();
        // Create the AcervoAtual
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(acervoAtual);
        restAcervoAtualMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeCreate + 1);
        AcervoAtual testAcervoAtual = acervoAtualList.get(acervoAtualList.size() - 1);
        assertThat(testAcervoAtual.getAcervoAtual()).isEqualTo(DEFAULT_ACERVO_ATUAL);
        assertThat(testAcervoAtual.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createAcervoAtualWithExistingId() throws Exception {
        // Create the AcervoAtual with an existing ID
        acervoAtual.setId(1L);
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(acervoAtual);

        int databaseSizeBeforeCreate = acervoAtualRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcervoAtualMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcervoAtualIsRequired() throws Exception {
        int databaseSizeBeforeTest = acervoAtualRepository.findAll().size();
        // set the field null
        acervoAtual.setAcervoAtual(null);

        // Create the AcervoAtual, which fails.
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(acervoAtual);

        restAcervoAtualMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO))
            )
            .andExpect(status().isBadRequest());

        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAcervoAtuals() throws Exception {
        // Initialize the database
        acervoAtualRepository.saveAndFlush(acervoAtual);

        // Get all the acervoAtualList
        restAcervoAtualMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acervoAtual.getId().intValue())))
            .andExpect(jsonPath("$.[*].acervoAtual").value(hasItem(DEFAULT_ACERVO_ATUAL)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getAcervoAtual() throws Exception {
        // Initialize the database
        acervoAtualRepository.saveAndFlush(acervoAtual);

        // Get the acervoAtual
        restAcervoAtualMockMvc
            .perform(get(ENTITY_API_URL_ID, acervoAtual.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acervoAtual.getId().intValue()))
            .andExpect(jsonPath("$.acervoAtual").value(DEFAULT_ACERVO_ATUAL))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAcervoAtual() throws Exception {
        // Get the acervoAtual
        restAcervoAtualMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAcervoAtual() throws Exception {
        // Initialize the database
        acervoAtualRepository.saveAndFlush(acervoAtual);

        int databaseSizeBeforeUpdate = acervoAtualRepository.findAll().size();

        // Update the acervoAtual
        AcervoAtual updatedAcervoAtual = acervoAtualRepository.findById(acervoAtual.getId()).get();
        // Disconnect from session so that the updates on updatedAcervoAtual are not directly saved in db
        em.detach(updatedAcervoAtual);
        updatedAcervoAtual.acervoAtual(UPDATED_ACERVO_ATUAL).inativo(UPDATED_INATIVO);
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(updatedAcervoAtual);

        restAcervoAtualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acervoAtualDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO))
            )
            .andExpect(status().isOk());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeUpdate);
        AcervoAtual testAcervoAtual = acervoAtualList.get(acervoAtualList.size() - 1);
        assertThat(testAcervoAtual.getAcervoAtual()).isEqualTo(UPDATED_ACERVO_ATUAL);
        assertThat(testAcervoAtual.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingAcervoAtual() throws Exception {
        int databaseSizeBeforeUpdate = acervoAtualRepository.findAll().size();
        acervoAtual.setId(count.incrementAndGet());

        // Create the AcervoAtual
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(acervoAtual);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcervoAtualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acervoAtualDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcervoAtual() throws Exception {
        int databaseSizeBeforeUpdate = acervoAtualRepository.findAll().size();
        acervoAtual.setId(count.incrementAndGet());

        // Create the AcervoAtual
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(acervoAtual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcervoAtualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcervoAtual() throws Exception {
        int databaseSizeBeforeUpdate = acervoAtualRepository.findAll().size();
        acervoAtual.setId(count.incrementAndGet());

        // Create the AcervoAtual
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(acervoAtual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcervoAtualMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcervoAtualWithPatch() throws Exception {
        // Initialize the database
        acervoAtualRepository.saveAndFlush(acervoAtual);

        int databaseSizeBeforeUpdate = acervoAtualRepository.findAll().size();

        // Update the acervoAtual using partial update
        AcervoAtual partialUpdatedAcervoAtual = new AcervoAtual();
        partialUpdatedAcervoAtual.setId(acervoAtual.getId());

        partialUpdatedAcervoAtual.acervoAtual(UPDATED_ACERVO_ATUAL);

        restAcervoAtualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcervoAtual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcervoAtual))
            )
            .andExpect(status().isOk());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeUpdate);
        AcervoAtual testAcervoAtual = acervoAtualList.get(acervoAtualList.size() - 1);
        assertThat(testAcervoAtual.getAcervoAtual()).isEqualTo(UPDATED_ACERVO_ATUAL);
        assertThat(testAcervoAtual.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateAcervoAtualWithPatch() throws Exception {
        // Initialize the database
        acervoAtualRepository.saveAndFlush(acervoAtual);

        int databaseSizeBeforeUpdate = acervoAtualRepository.findAll().size();

        // Update the acervoAtual using partial update
        AcervoAtual partialUpdatedAcervoAtual = new AcervoAtual();
        partialUpdatedAcervoAtual.setId(acervoAtual.getId());

        partialUpdatedAcervoAtual.acervoAtual(UPDATED_ACERVO_ATUAL).inativo(UPDATED_INATIVO);

        restAcervoAtualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcervoAtual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcervoAtual))
            )
            .andExpect(status().isOk());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeUpdate);
        AcervoAtual testAcervoAtual = acervoAtualList.get(acervoAtualList.size() - 1);
        assertThat(testAcervoAtual.getAcervoAtual()).isEqualTo(UPDATED_ACERVO_ATUAL);
        assertThat(testAcervoAtual.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingAcervoAtual() throws Exception {
        int databaseSizeBeforeUpdate = acervoAtualRepository.findAll().size();
        acervoAtual.setId(count.incrementAndGet());

        // Create the AcervoAtual
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(acervoAtual);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcervoAtualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, acervoAtualDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcervoAtual() throws Exception {
        int databaseSizeBeforeUpdate = acervoAtualRepository.findAll().size();
        acervoAtual.setId(count.incrementAndGet());

        // Create the AcervoAtual
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(acervoAtual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcervoAtualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcervoAtual() throws Exception {
        int databaseSizeBeforeUpdate = acervoAtualRepository.findAll().size();
        acervoAtual.setId(count.incrementAndGet());

        // Create the AcervoAtual
        AcervoAtualDTO acervoAtualDTO = acervoAtualMapper.toDto(acervoAtual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcervoAtualMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(acervoAtualDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcervoAtual in the database
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcervoAtual() throws Exception {
        // Initialize the database
        acervoAtualRepository.saveAndFlush(acervoAtual);

        int databaseSizeBeforeDelete = acervoAtualRepository.findAll().size();

        // Delete the acervoAtual
        restAcervoAtualMockMvc
            .perform(delete(ENTITY_API_URL_ID, acervoAtual.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcervoAtual> acervoAtualList = acervoAtualRepository.findAll();
        assertThat(acervoAtualList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
