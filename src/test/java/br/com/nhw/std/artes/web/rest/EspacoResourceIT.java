package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Espaco;
import br.com.nhw.std.artes.repository.EspacoRepository;
import br.com.nhw.std.artes.service.dto.EspacoDTO;
import br.com.nhw.std.artes.service.mapper.EspacoMapper;
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
 * Integration tests for the {@link EspacoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EspacoResourceIT {

    private static final String DEFAULT_ESPACO = "AAAAAAAAAA";
    private static final String UPDATED_ESPACO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/espacos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EspacoRepository espacoRepository;

    @Autowired
    private EspacoMapper espacoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEspacoMockMvc;

    private Espaco espaco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Espaco createEntity(EntityManager em) {
        Espaco espaco = new Espaco().espaco(DEFAULT_ESPACO).inativo(DEFAULT_INATIVO);
        return espaco;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Espaco createUpdatedEntity(EntityManager em) {
        Espaco espaco = new Espaco().espaco(UPDATED_ESPACO).inativo(UPDATED_INATIVO);
        return espaco;
    }

    @BeforeEach
    public void initTest() {
        espaco = createEntity(em);
    }

    @Test
    @Transactional
    void createEspaco() throws Exception {
        int databaseSizeBeforeCreate = espacoRepository.findAll().size();
        // Create the Espaco
        EspacoDTO espacoDTO = espacoMapper.toDto(espaco);
        restEspacoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(espacoDTO)))
            .andExpect(status().isCreated());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeCreate + 1);
        Espaco testEspaco = espacoList.get(espacoList.size() - 1);
        assertThat(testEspaco.getEspaco()).isEqualTo(DEFAULT_ESPACO);
        assertThat(testEspaco.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createEspacoWithExistingId() throws Exception {
        // Create the Espaco with an existing ID
        espaco.setId(1L);
        EspacoDTO espacoDTO = espacoMapper.toDto(espaco);

        int databaseSizeBeforeCreate = espacoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspacoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(espacoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEspacoIsRequired() throws Exception {
        int databaseSizeBeforeTest = espacoRepository.findAll().size();
        // set the field null
        espaco.setEspaco(null);

        // Create the Espaco, which fails.
        EspacoDTO espacoDTO = espacoMapper.toDto(espaco);

        restEspacoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(espacoDTO)))
            .andExpect(status().isBadRequest());

        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEspacos() throws Exception {
        // Initialize the database
        espacoRepository.saveAndFlush(espaco);

        // Get all the espacoList
        restEspacoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(espaco.getId().intValue())))
            .andExpect(jsonPath("$.[*].espaco").value(hasItem(DEFAULT_ESPACO)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getEspaco() throws Exception {
        // Initialize the database
        espacoRepository.saveAndFlush(espaco);

        // Get the espaco
        restEspacoMockMvc
            .perform(get(ENTITY_API_URL_ID, espaco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(espaco.getId().intValue()))
            .andExpect(jsonPath("$.espaco").value(DEFAULT_ESPACO))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEspaco() throws Exception {
        // Get the espaco
        restEspacoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEspaco() throws Exception {
        // Initialize the database
        espacoRepository.saveAndFlush(espaco);

        int databaseSizeBeforeUpdate = espacoRepository.findAll().size();

        // Update the espaco
        Espaco updatedEspaco = espacoRepository.findById(espaco.getId()).get();
        // Disconnect from session so that the updates on updatedEspaco are not directly saved in db
        em.detach(updatedEspaco);
        updatedEspaco.espaco(UPDATED_ESPACO).inativo(UPDATED_INATIVO);
        EspacoDTO espacoDTO = espacoMapper.toDto(updatedEspaco);

        restEspacoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, espacoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(espacoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeUpdate);
        Espaco testEspaco = espacoList.get(espacoList.size() - 1);
        assertThat(testEspaco.getEspaco()).isEqualTo(UPDATED_ESPACO);
        assertThat(testEspaco.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingEspaco() throws Exception {
        int databaseSizeBeforeUpdate = espacoRepository.findAll().size();
        espaco.setId(count.incrementAndGet());

        // Create the Espaco
        EspacoDTO espacoDTO = espacoMapper.toDto(espaco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspacoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, espacoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(espacoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEspaco() throws Exception {
        int databaseSizeBeforeUpdate = espacoRepository.findAll().size();
        espaco.setId(count.incrementAndGet());

        // Create the Espaco
        EspacoDTO espacoDTO = espacoMapper.toDto(espaco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspacoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(espacoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEspaco() throws Exception {
        int databaseSizeBeforeUpdate = espacoRepository.findAll().size();
        espaco.setId(count.incrementAndGet());

        // Create the Espaco
        EspacoDTO espacoDTO = espacoMapper.toDto(espaco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspacoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(espacoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEspacoWithPatch() throws Exception {
        // Initialize the database
        espacoRepository.saveAndFlush(espaco);

        int databaseSizeBeforeUpdate = espacoRepository.findAll().size();

        // Update the espaco using partial update
        Espaco partialUpdatedEspaco = new Espaco();
        partialUpdatedEspaco.setId(espaco.getId());

        partialUpdatedEspaco.espaco(UPDATED_ESPACO).inativo(UPDATED_INATIVO);

        restEspacoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspaco.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspaco))
            )
            .andExpect(status().isOk());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeUpdate);
        Espaco testEspaco = espacoList.get(espacoList.size() - 1);
        assertThat(testEspaco.getEspaco()).isEqualTo(UPDATED_ESPACO);
        assertThat(testEspaco.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateEspacoWithPatch() throws Exception {
        // Initialize the database
        espacoRepository.saveAndFlush(espaco);

        int databaseSizeBeforeUpdate = espacoRepository.findAll().size();

        // Update the espaco using partial update
        Espaco partialUpdatedEspaco = new Espaco();
        partialUpdatedEspaco.setId(espaco.getId());

        partialUpdatedEspaco.espaco(UPDATED_ESPACO).inativo(UPDATED_INATIVO);

        restEspacoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspaco.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspaco))
            )
            .andExpect(status().isOk());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeUpdate);
        Espaco testEspaco = espacoList.get(espacoList.size() - 1);
        assertThat(testEspaco.getEspaco()).isEqualTo(UPDATED_ESPACO);
        assertThat(testEspaco.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingEspaco() throws Exception {
        int databaseSizeBeforeUpdate = espacoRepository.findAll().size();
        espaco.setId(count.incrementAndGet());

        // Create the Espaco
        EspacoDTO espacoDTO = espacoMapper.toDto(espaco);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspacoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, espacoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(espacoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEspaco() throws Exception {
        int databaseSizeBeforeUpdate = espacoRepository.findAll().size();
        espaco.setId(count.incrementAndGet());

        // Create the Espaco
        EspacoDTO espacoDTO = espacoMapper.toDto(espaco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspacoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(espacoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEspaco() throws Exception {
        int databaseSizeBeforeUpdate = espacoRepository.findAll().size();
        espaco.setId(count.incrementAndGet());

        // Create the Espaco
        EspacoDTO espacoDTO = espacoMapper.toDto(espaco);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspacoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(espacoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Espaco in the database
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEspaco() throws Exception {
        // Initialize the database
        espacoRepository.saveAndFlush(espaco);

        int databaseSizeBeforeDelete = espacoRepository.findAll().size();

        // Delete the espaco
        restEspacoMockMvc
            .perform(delete(ENTITY_API_URL_ID, espaco.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Espaco> espacoList = espacoRepository.findAll();
        assertThat(espacoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
