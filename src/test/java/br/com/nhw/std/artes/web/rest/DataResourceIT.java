package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Data;
import br.com.nhw.std.artes.repository.DataRepository;
import br.com.nhw.std.artes.service.dto.DataDTO;
import br.com.nhw.std.artes.service.mapper.DataMapper;
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
 * Integration tests for the {@link DataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DataResourceIT {

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataMockMvc;

    private Data data;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Data createEntity(EntityManager em) {
        Data data = new Data().data(DEFAULT_DATA).inativo(DEFAULT_INATIVO);
        return data;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Data createUpdatedEntity(EntityManager em) {
        Data data = new Data().data(UPDATED_DATA).inativo(UPDATED_INATIVO);
        return data;
    }

    @BeforeEach
    public void initTest() {
        data = createEntity(em);
    }

    @Test
    @Transactional
    void createData() throws Exception {
        int databaseSizeBeforeCreate = dataRepository.findAll().size();
        // Create the Data
        DataDTO dataDTO = dataMapper.toDto(data);
        restDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataDTO)))
            .andExpect(status().isCreated());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeCreate + 1);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testData.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createDataWithExistingId() throws Exception {
        // Create the Data with an existing ID
        data.setId(1L);
        DataDTO dataDTO = dataMapper.toDto(data);

        int databaseSizeBeforeCreate = dataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataRepository.findAll().size();
        // set the field null
        data.setData(null);

        // Create the Data, which fails.
        DataDTO dataDTO = dataMapper.toDto(data);

        restDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataDTO)))
            .andExpect(status().isBadRequest());

        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllData() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList
        restDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(data.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getData() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get the data
        restDataMockMvc
            .perform(get(ENTITY_API_URL_ID, data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(data.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingData() throws Exception {
        // Get the data
        restDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewData() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        int databaseSizeBeforeUpdate = dataRepository.findAll().size();

        // Update the data
        Data updatedData = dataRepository.findById(data.getId()).get();
        // Disconnect from session so that the updates on updatedData are not directly saved in db
        em.detach(updatedData);
        updatedData.data(UPDATED_DATA).inativo(UPDATED_INATIVO);
        DataDTO dataDTO = dataMapper.toDto(updatedData);

        restDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataDTO))
            )
            .andExpect(status().isOk());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testData.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingData() throws Exception {
        int databaseSizeBeforeUpdate = dataRepository.findAll().size();
        data.setId(count.incrementAndGet());

        // Create the Data
        DataDTO dataDTO = dataMapper.toDto(data);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchData() throws Exception {
        int databaseSizeBeforeUpdate = dataRepository.findAll().size();
        data.setId(count.incrementAndGet());

        // Create the Data
        DataDTO dataDTO = dataMapper.toDto(data);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamData() throws Exception {
        int databaseSizeBeforeUpdate = dataRepository.findAll().size();
        data.setId(count.incrementAndGet());

        // Create the Data
        DataDTO dataDTO = dataMapper.toDto(data);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataWithPatch() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        int databaseSizeBeforeUpdate = dataRepository.findAll().size();

        // Update the data using partial update
        Data partialUpdatedData = new Data();
        partialUpdatedData.setId(data.getId());

        partialUpdatedData.data(UPDATED_DATA).inativo(UPDATED_INATIVO);

        restDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedData))
            )
            .andExpect(status().isOk());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testData.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateDataWithPatch() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        int databaseSizeBeforeUpdate = dataRepository.findAll().size();

        // Update the data using partial update
        Data partialUpdatedData = new Data();
        partialUpdatedData.setId(data.getId());

        partialUpdatedData.data(UPDATED_DATA).inativo(UPDATED_INATIVO);

        restDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedData))
            )
            .andExpect(status().isOk());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testData.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingData() throws Exception {
        int databaseSizeBeforeUpdate = dataRepository.findAll().size();
        data.setId(count.incrementAndGet());

        // Create the Data
        DataDTO dataDTO = dataMapper.toDto(data);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchData() throws Exception {
        int databaseSizeBeforeUpdate = dataRepository.findAll().size();
        data.setId(count.incrementAndGet());

        // Create the Data
        DataDTO dataDTO = dataMapper.toDto(data);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamData() throws Exception {
        int databaseSizeBeforeUpdate = dataRepository.findAll().size();
        data.setId(count.incrementAndGet());

        // Create the Data
        DataDTO dataDTO = dataMapper.toDto(data);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteData() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        int databaseSizeBeforeDelete = dataRepository.findAll().size();

        // Delete the data
        restDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, data.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
