package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Moeda;
import br.com.nhw.std.artes.repository.MoedaRepository;
import br.com.nhw.std.artes.service.dto.MoedaDTO;
import br.com.nhw.std.artes.service.mapper.MoedaMapper;
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
 * Integration tests for the {@link MoedaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MoedaResourceIT {

    private static final String DEFAULT_TIPO_MOEDA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_MOEDA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/moedas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MoedaRepository moedaRepository;

    @Autowired
    private MoedaMapper moedaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMoedaMockMvc;

    private Moeda moeda;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moeda createEntity(EntityManager em) {
        Moeda moeda = new Moeda().tipoMoeda(DEFAULT_TIPO_MOEDA).inativo(DEFAULT_INATIVO);
        return moeda;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Moeda createUpdatedEntity(EntityManager em) {
        Moeda moeda = new Moeda().tipoMoeda(UPDATED_TIPO_MOEDA).inativo(UPDATED_INATIVO);
        return moeda;
    }

    @BeforeEach
    public void initTest() {
        moeda = createEntity(em);
    }

    @Test
    @Transactional
    void createMoeda() throws Exception {
        int databaseSizeBeforeCreate = moedaRepository.findAll().size();
        // Create the Moeda
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);
        restMoedaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isCreated());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeCreate + 1);
        Moeda testMoeda = moedaList.get(moedaList.size() - 1);
        assertThat(testMoeda.getTipoMoeda()).isEqualTo(DEFAULT_TIPO_MOEDA);
        assertThat(testMoeda.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createMoedaWithExistingId() throws Exception {
        // Create the Moeda with an existing ID
        moeda.setId(1L);
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        int databaseSizeBeforeCreate = moedaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMoedaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoMoedaIsRequired() throws Exception {
        int databaseSizeBeforeTest = moedaRepository.findAll().size();
        // set the field null
        moeda.setTipoMoeda(null);

        // Create the Moeda, which fails.
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        restMoedaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isBadRequest());

        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMoedas() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get all the moedaList
        restMoedaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moeda.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoMoeda").value(hasItem(DEFAULT_TIPO_MOEDA)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getMoeda() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        // Get the moeda
        restMoedaMockMvc
            .perform(get(ENTITY_API_URL_ID, moeda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(moeda.getId().intValue()))
            .andExpect(jsonPath("$.tipoMoeda").value(DEFAULT_TIPO_MOEDA))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMoeda() throws Exception {
        // Get the moeda
        restMoedaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMoeda() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();

        // Update the moeda
        Moeda updatedMoeda = moedaRepository.findById(moeda.getId()).get();
        // Disconnect from session so that the updates on updatedMoeda are not directly saved in db
        em.detach(updatedMoeda);
        updatedMoeda.tipoMoeda(UPDATED_TIPO_MOEDA).inativo(UPDATED_INATIVO);
        MoedaDTO moedaDTO = moedaMapper.toDto(updatedMoeda);

        restMoedaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moedaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moedaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
        Moeda testMoeda = moedaList.get(moedaList.size() - 1);
        assertThat(testMoeda.getTipoMoeda()).isEqualTo(UPDATED_TIPO_MOEDA);
        assertThat(testMoeda.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingMoeda() throws Exception {
        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();
        moeda.setId(count.incrementAndGet());

        // Create the Moeda
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoedaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, moedaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moedaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMoeda() throws Exception {
        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();
        moeda.setId(count.incrementAndGet());

        // Create the Moeda
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoedaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(moedaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMoeda() throws Exception {
        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();
        moeda.setId(count.incrementAndGet());

        // Create the Moeda
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoedaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMoedaWithPatch() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();

        // Update the moeda using partial update
        Moeda partialUpdatedMoeda = new Moeda();
        partialUpdatedMoeda.setId(moeda.getId());

        partialUpdatedMoeda.tipoMoeda(UPDATED_TIPO_MOEDA);

        restMoedaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoeda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoeda))
            )
            .andExpect(status().isOk());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
        Moeda testMoeda = moedaList.get(moedaList.size() - 1);
        assertThat(testMoeda.getTipoMoeda()).isEqualTo(UPDATED_TIPO_MOEDA);
        assertThat(testMoeda.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateMoedaWithPatch() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();

        // Update the moeda using partial update
        Moeda partialUpdatedMoeda = new Moeda();
        partialUpdatedMoeda.setId(moeda.getId());

        partialUpdatedMoeda.tipoMoeda(UPDATED_TIPO_MOEDA).inativo(UPDATED_INATIVO);

        restMoedaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMoeda.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMoeda))
            )
            .andExpect(status().isOk());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
        Moeda testMoeda = moedaList.get(moedaList.size() - 1);
        assertThat(testMoeda.getTipoMoeda()).isEqualTo(UPDATED_TIPO_MOEDA);
        assertThat(testMoeda.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingMoeda() throws Exception {
        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();
        moeda.setId(count.incrementAndGet());

        // Create the Moeda
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMoedaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, moedaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moedaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMoeda() throws Exception {
        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();
        moeda.setId(count.incrementAndGet());

        // Create the Moeda
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoedaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(moedaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMoeda() throws Exception {
        int databaseSizeBeforeUpdate = moedaRepository.findAll().size();
        moeda.setId(count.incrementAndGet());

        // Create the Moeda
        MoedaDTO moedaDTO = moedaMapper.toDto(moeda);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMoedaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(moedaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Moeda in the database
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMoeda() throws Exception {
        // Initialize the database
        moedaRepository.saveAndFlush(moeda);

        int databaseSizeBeforeDelete = moedaRepository.findAll().size();

        // Delete the moeda
        restMoedaMockMvc
            .perform(delete(ENTITY_API_URL_ID, moeda.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Moeda> moedaList = moedaRepository.findAll();
        assertThat(moedaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
