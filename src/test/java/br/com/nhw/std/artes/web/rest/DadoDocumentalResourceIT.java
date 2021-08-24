package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.DadoDocumental;
import br.com.nhw.std.artes.repository.DadoDocumentalRepository;
import br.com.nhw.std.artes.service.dto.DadoDocumentalDTO;
import br.com.nhw.std.artes.service.mapper.DadoDocumentalMapper;
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
 * Integration tests for the {@link DadoDocumentalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DadoDocumentalResourceIT {

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_EMISSOR = "AAAAAAAAAA";
    private static final String UPDATED_EMISSOR = "BBBBBBBBBB";

    private static final String DEFAULT_RECEPTOR = "AAAAAAAAAA";
    private static final String UPDATED_RECEPTOR = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_TRANSCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FINALIZADO = false;
    private static final Boolean UPDATED_FINALIZADO = true;

    private static final Integer DEFAULT_GEN_TRANSCRICAO = 1;
    private static final Integer UPDATED_GEN_TRANSCRICAO = 2;

    private static final String ENTITY_API_URL = "/api/dado-documentals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DadoDocumentalRepository dadoDocumentalRepository;

    @Autowired
    private DadoDocumentalMapper dadoDocumentalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDadoDocumentalMockMvc;

    private DadoDocumental dadoDocumental;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadoDocumental createEntity(EntityManager em) {
        DadoDocumental dadoDocumental = new DadoDocumental()
            .data(DEFAULT_DATA)
            .emissor(DEFAULT_EMISSOR)
            .receptor(DEFAULT_RECEPTOR)
            .obs(DEFAULT_OBS)
            .transcricao(DEFAULT_TRANSCRICAO)
            .finalizado(DEFAULT_FINALIZADO)
            .genTranscricao(DEFAULT_GEN_TRANSCRICAO);
        return dadoDocumental;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadoDocumental createUpdatedEntity(EntityManager em) {
        DadoDocumental dadoDocumental = new DadoDocumental()
            .data(UPDATED_DATA)
            .emissor(UPDATED_EMISSOR)
            .receptor(UPDATED_RECEPTOR)
            .obs(UPDATED_OBS)
            .transcricao(UPDATED_TRANSCRICAO)
            .finalizado(UPDATED_FINALIZADO)
            .genTranscricao(UPDATED_GEN_TRANSCRICAO);
        return dadoDocumental;
    }

    @BeforeEach
    public void initTest() {
        dadoDocumental = createEntity(em);
    }

    @Test
    @Transactional
    void createDadoDocumental() throws Exception {
        int databaseSizeBeforeCreate = dadoDocumentalRepository.findAll().size();
        // Create the DadoDocumental
        DadoDocumentalDTO dadoDocumentalDTO = dadoDocumentalMapper.toDto(dadoDocumental);
        restDadoDocumentalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadoDocumentalDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeCreate + 1);
        DadoDocumental testDadoDocumental = dadoDocumentalList.get(dadoDocumentalList.size() - 1);
        assertThat(testDadoDocumental.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDadoDocumental.getEmissor()).isEqualTo(DEFAULT_EMISSOR);
        assertThat(testDadoDocumental.getReceptor()).isEqualTo(DEFAULT_RECEPTOR);
        assertThat(testDadoDocumental.getObs()).isEqualTo(DEFAULT_OBS);
        assertThat(testDadoDocumental.getTranscricao()).isEqualTo(DEFAULT_TRANSCRICAO);
        assertThat(testDadoDocumental.getFinalizado()).isEqualTo(DEFAULT_FINALIZADO);
        assertThat(testDadoDocumental.getGenTranscricao()).isEqualTo(DEFAULT_GEN_TRANSCRICAO);
    }

    @Test
    @Transactional
    void createDadoDocumentalWithExistingId() throws Exception {
        // Create the DadoDocumental with an existing ID
        dadoDocumental.setId(1L);
        DadoDocumentalDTO dadoDocumentalDTO = dadoDocumentalMapper.toDto(dadoDocumental);

        int databaseSizeBeforeCreate = dadoDocumentalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDadoDocumentalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadoDocumentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDadoDocumentals() throws Exception {
        // Initialize the database
        dadoDocumentalRepository.saveAndFlush(dadoDocumental);

        // Get all the dadoDocumentalList
        restDadoDocumentalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dadoDocumental.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].emissor").value(hasItem(DEFAULT_EMISSOR)))
            .andExpect(jsonPath("$.[*].receptor").value(hasItem(DEFAULT_RECEPTOR)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)))
            .andExpect(jsonPath("$.[*].transcricao").value(hasItem(DEFAULT_TRANSCRICAO)))
            .andExpect(jsonPath("$.[*].finalizado").value(hasItem(DEFAULT_FINALIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].genTranscricao").value(hasItem(DEFAULT_GEN_TRANSCRICAO)));
    }

    @Test
    @Transactional
    void getDadoDocumental() throws Exception {
        // Initialize the database
        dadoDocumentalRepository.saveAndFlush(dadoDocumental);

        // Get the dadoDocumental
        restDadoDocumentalMockMvc
            .perform(get(ENTITY_API_URL_ID, dadoDocumental.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dadoDocumental.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.emissor").value(DEFAULT_EMISSOR))
            .andExpect(jsonPath("$.receptor").value(DEFAULT_RECEPTOR))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS))
            .andExpect(jsonPath("$.transcricao").value(DEFAULT_TRANSCRICAO))
            .andExpect(jsonPath("$.finalizado").value(DEFAULT_FINALIZADO.booleanValue()))
            .andExpect(jsonPath("$.genTranscricao").value(DEFAULT_GEN_TRANSCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingDadoDocumental() throws Exception {
        // Get the dadoDocumental
        restDadoDocumentalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDadoDocumental() throws Exception {
        // Initialize the database
        dadoDocumentalRepository.saveAndFlush(dadoDocumental);

        int databaseSizeBeforeUpdate = dadoDocumentalRepository.findAll().size();

        // Update the dadoDocumental
        DadoDocumental updatedDadoDocumental = dadoDocumentalRepository.findById(dadoDocumental.getId()).get();
        // Disconnect from session so that the updates on updatedDadoDocumental are not directly saved in db
        em.detach(updatedDadoDocumental);
        updatedDadoDocumental
            .data(UPDATED_DATA)
            .emissor(UPDATED_EMISSOR)
            .receptor(UPDATED_RECEPTOR)
            .obs(UPDATED_OBS)
            .transcricao(UPDATED_TRANSCRICAO)
            .finalizado(UPDATED_FINALIZADO)
            .genTranscricao(UPDATED_GEN_TRANSCRICAO);
        DadoDocumentalDTO dadoDocumentalDTO = dadoDocumentalMapper.toDto(updatedDadoDocumental);

        restDadoDocumentalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dadoDocumentalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dadoDocumentalDTO))
            )
            .andExpect(status().isOk());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeUpdate);
        DadoDocumental testDadoDocumental = dadoDocumentalList.get(dadoDocumentalList.size() - 1);
        assertThat(testDadoDocumental.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDadoDocumental.getEmissor()).isEqualTo(UPDATED_EMISSOR);
        assertThat(testDadoDocumental.getReceptor()).isEqualTo(UPDATED_RECEPTOR);
        assertThat(testDadoDocumental.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testDadoDocumental.getTranscricao()).isEqualTo(UPDATED_TRANSCRICAO);
        assertThat(testDadoDocumental.getFinalizado()).isEqualTo(UPDATED_FINALIZADO);
        assertThat(testDadoDocumental.getGenTranscricao()).isEqualTo(UPDATED_GEN_TRANSCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingDadoDocumental() throws Exception {
        int databaseSizeBeforeUpdate = dadoDocumentalRepository.findAll().size();
        dadoDocumental.setId(count.incrementAndGet());

        // Create the DadoDocumental
        DadoDocumentalDTO dadoDocumentalDTO = dadoDocumentalMapper.toDto(dadoDocumental);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadoDocumentalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dadoDocumentalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dadoDocumentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDadoDocumental() throws Exception {
        int databaseSizeBeforeUpdate = dadoDocumentalRepository.findAll().size();
        dadoDocumental.setId(count.incrementAndGet());

        // Create the DadoDocumental
        DadoDocumentalDTO dadoDocumentalDTO = dadoDocumentalMapper.toDto(dadoDocumental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadoDocumentalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dadoDocumentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDadoDocumental() throws Exception {
        int databaseSizeBeforeUpdate = dadoDocumentalRepository.findAll().size();
        dadoDocumental.setId(count.incrementAndGet());

        // Create the DadoDocumental
        DadoDocumentalDTO dadoDocumentalDTO = dadoDocumentalMapper.toDto(dadoDocumental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadoDocumentalMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dadoDocumentalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDadoDocumentalWithPatch() throws Exception {
        // Initialize the database
        dadoDocumentalRepository.saveAndFlush(dadoDocumental);

        int databaseSizeBeforeUpdate = dadoDocumentalRepository.findAll().size();

        // Update the dadoDocumental using partial update
        DadoDocumental partialUpdatedDadoDocumental = new DadoDocumental();
        partialUpdatedDadoDocumental.setId(dadoDocumental.getId());

        partialUpdatedDadoDocumental.data(UPDATED_DATA).finalizado(UPDATED_FINALIZADO);

        restDadoDocumentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDadoDocumental.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDadoDocumental))
            )
            .andExpect(status().isOk());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeUpdate);
        DadoDocumental testDadoDocumental = dadoDocumentalList.get(dadoDocumentalList.size() - 1);
        assertThat(testDadoDocumental.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDadoDocumental.getEmissor()).isEqualTo(DEFAULT_EMISSOR);
        assertThat(testDadoDocumental.getReceptor()).isEqualTo(DEFAULT_RECEPTOR);
        assertThat(testDadoDocumental.getObs()).isEqualTo(DEFAULT_OBS);
        assertThat(testDadoDocumental.getTranscricao()).isEqualTo(DEFAULT_TRANSCRICAO);
        assertThat(testDadoDocumental.getFinalizado()).isEqualTo(UPDATED_FINALIZADO);
        assertThat(testDadoDocumental.getGenTranscricao()).isEqualTo(DEFAULT_GEN_TRANSCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateDadoDocumentalWithPatch() throws Exception {
        // Initialize the database
        dadoDocumentalRepository.saveAndFlush(dadoDocumental);

        int databaseSizeBeforeUpdate = dadoDocumentalRepository.findAll().size();

        // Update the dadoDocumental using partial update
        DadoDocumental partialUpdatedDadoDocumental = new DadoDocumental();
        partialUpdatedDadoDocumental.setId(dadoDocumental.getId());

        partialUpdatedDadoDocumental
            .data(UPDATED_DATA)
            .emissor(UPDATED_EMISSOR)
            .receptor(UPDATED_RECEPTOR)
            .obs(UPDATED_OBS)
            .transcricao(UPDATED_TRANSCRICAO)
            .finalizado(UPDATED_FINALIZADO)
            .genTranscricao(UPDATED_GEN_TRANSCRICAO);

        restDadoDocumentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDadoDocumental.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDadoDocumental))
            )
            .andExpect(status().isOk());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeUpdate);
        DadoDocumental testDadoDocumental = dadoDocumentalList.get(dadoDocumentalList.size() - 1);
        assertThat(testDadoDocumental.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDadoDocumental.getEmissor()).isEqualTo(UPDATED_EMISSOR);
        assertThat(testDadoDocumental.getReceptor()).isEqualTo(UPDATED_RECEPTOR);
        assertThat(testDadoDocumental.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testDadoDocumental.getTranscricao()).isEqualTo(UPDATED_TRANSCRICAO);
        assertThat(testDadoDocumental.getFinalizado()).isEqualTo(UPDATED_FINALIZADO);
        assertThat(testDadoDocumental.getGenTranscricao()).isEqualTo(UPDATED_GEN_TRANSCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingDadoDocumental() throws Exception {
        int databaseSizeBeforeUpdate = dadoDocumentalRepository.findAll().size();
        dadoDocumental.setId(count.incrementAndGet());

        // Create the DadoDocumental
        DadoDocumentalDTO dadoDocumentalDTO = dadoDocumentalMapper.toDto(dadoDocumental);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadoDocumentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dadoDocumentalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dadoDocumentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDadoDocumental() throws Exception {
        int databaseSizeBeforeUpdate = dadoDocumentalRepository.findAll().size();
        dadoDocumental.setId(count.incrementAndGet());

        // Create the DadoDocumental
        DadoDocumentalDTO dadoDocumentalDTO = dadoDocumentalMapper.toDto(dadoDocumental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadoDocumentalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dadoDocumentalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDadoDocumental() throws Exception {
        int databaseSizeBeforeUpdate = dadoDocumentalRepository.findAll().size();
        dadoDocumental.setId(count.incrementAndGet());

        // Create the DadoDocumental
        DadoDocumentalDTO dadoDocumentalDTO = dadoDocumentalMapper.toDto(dadoDocumental);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDadoDocumentalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dadoDocumentalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DadoDocumental in the database
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDadoDocumental() throws Exception {
        // Initialize the database
        dadoDocumentalRepository.saveAndFlush(dadoDocumental);

        int databaseSizeBeforeDelete = dadoDocumentalRepository.findAll().size();

        // Delete the dadoDocumental
        restDadoDocumentalMockMvc
            .perform(delete(ENTITY_API_URL_ID, dadoDocumental.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DadoDocumental> dadoDocumentalList = dadoDocumentalRepository.findAll();
        assertThat(dadoDocumentalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
