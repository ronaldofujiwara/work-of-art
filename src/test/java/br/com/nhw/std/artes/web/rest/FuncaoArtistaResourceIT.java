package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.FuncaoArtista;
import br.com.nhw.std.artes.repository.FuncaoArtistaRepository;
import br.com.nhw.std.artes.service.dto.FuncaoArtistaDTO;
import br.com.nhw.std.artes.service.mapper.FuncaoArtistaMapper;
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
 * Integration tests for the {@link FuncaoArtistaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuncaoArtistaResourceIT {

    private static final String DEFAULT_FUNCAO_ARTISTA = "AAAAAAAAAA";
    private static final String UPDATED_FUNCAO_ARTISTA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/funcao-artistas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuncaoArtistaRepository funcaoArtistaRepository;

    @Autowired
    private FuncaoArtistaMapper funcaoArtistaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncaoArtistaMockMvc;

    private FuncaoArtista funcaoArtista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuncaoArtista createEntity(EntityManager em) {
        FuncaoArtista funcaoArtista = new FuncaoArtista().funcaoArtista(DEFAULT_FUNCAO_ARTISTA).inativo(DEFAULT_INATIVO);
        return funcaoArtista;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuncaoArtista createUpdatedEntity(EntityManager em) {
        FuncaoArtista funcaoArtista = new FuncaoArtista().funcaoArtista(UPDATED_FUNCAO_ARTISTA).inativo(UPDATED_INATIVO);
        return funcaoArtista;
    }

    @BeforeEach
    public void initTest() {
        funcaoArtista = createEntity(em);
    }

    @Test
    @Transactional
    void createFuncaoArtista() throws Exception {
        int databaseSizeBeforeCreate = funcaoArtistaRepository.findAll().size();
        // Create the FuncaoArtista
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(funcaoArtista);
        restFuncaoArtistaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeCreate + 1);
        FuncaoArtista testFuncaoArtista = funcaoArtistaList.get(funcaoArtistaList.size() - 1);
        assertThat(testFuncaoArtista.getFuncaoArtista()).isEqualTo(DEFAULT_FUNCAO_ARTISTA);
        assertThat(testFuncaoArtista.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createFuncaoArtistaWithExistingId() throws Exception {
        // Create the FuncaoArtista with an existing ID
        funcaoArtista.setId(1L);
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(funcaoArtista);

        int databaseSizeBeforeCreate = funcaoArtistaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncaoArtistaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFuncaoArtistaIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcaoArtistaRepository.findAll().size();
        // set the field null
        funcaoArtista.setFuncaoArtista(null);

        // Create the FuncaoArtista, which fails.
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(funcaoArtista);

        restFuncaoArtistaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isBadRequest());

        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFuncaoArtistas() throws Exception {
        // Initialize the database
        funcaoArtistaRepository.saveAndFlush(funcaoArtista);

        // Get all the funcaoArtistaList
        restFuncaoArtistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcaoArtista.getId().intValue())))
            .andExpect(jsonPath("$.[*].funcaoArtista").value(hasItem(DEFAULT_FUNCAO_ARTISTA)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getFuncaoArtista() throws Exception {
        // Initialize the database
        funcaoArtistaRepository.saveAndFlush(funcaoArtista);

        // Get the funcaoArtista
        restFuncaoArtistaMockMvc
            .perform(get(ENTITY_API_URL_ID, funcaoArtista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcaoArtista.getId().intValue()))
            .andExpect(jsonPath("$.funcaoArtista").value(DEFAULT_FUNCAO_ARTISTA))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingFuncaoArtista() throws Exception {
        // Get the funcaoArtista
        restFuncaoArtistaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFuncaoArtista() throws Exception {
        // Initialize the database
        funcaoArtistaRepository.saveAndFlush(funcaoArtista);

        int databaseSizeBeforeUpdate = funcaoArtistaRepository.findAll().size();

        // Update the funcaoArtista
        FuncaoArtista updatedFuncaoArtista = funcaoArtistaRepository.findById(funcaoArtista.getId()).get();
        // Disconnect from session so that the updates on updatedFuncaoArtista are not directly saved in db
        em.detach(updatedFuncaoArtista);
        updatedFuncaoArtista.funcaoArtista(UPDATED_FUNCAO_ARTISTA).inativo(UPDATED_INATIVO);
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(updatedFuncaoArtista);

        restFuncaoArtistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcaoArtistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isOk());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeUpdate);
        FuncaoArtista testFuncaoArtista = funcaoArtistaList.get(funcaoArtistaList.size() - 1);
        assertThat(testFuncaoArtista.getFuncaoArtista()).isEqualTo(UPDATED_FUNCAO_ARTISTA);
        assertThat(testFuncaoArtista.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingFuncaoArtista() throws Exception {
        int databaseSizeBeforeUpdate = funcaoArtistaRepository.findAll().size();
        funcaoArtista.setId(count.incrementAndGet());

        // Create the FuncaoArtista
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(funcaoArtista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncaoArtistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcaoArtistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncaoArtista() throws Exception {
        int databaseSizeBeforeUpdate = funcaoArtistaRepository.findAll().size();
        funcaoArtista.setId(count.incrementAndGet());

        // Create the FuncaoArtista
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(funcaoArtista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncaoArtistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncaoArtista() throws Exception {
        int databaseSizeBeforeUpdate = funcaoArtistaRepository.findAll().size();
        funcaoArtista.setId(count.incrementAndGet());

        // Create the FuncaoArtista
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(funcaoArtista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncaoArtistaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncaoArtistaWithPatch() throws Exception {
        // Initialize the database
        funcaoArtistaRepository.saveAndFlush(funcaoArtista);

        int databaseSizeBeforeUpdate = funcaoArtistaRepository.findAll().size();

        // Update the funcaoArtista using partial update
        FuncaoArtista partialUpdatedFuncaoArtista = new FuncaoArtista();
        partialUpdatedFuncaoArtista.setId(funcaoArtista.getId());

        restFuncaoArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncaoArtista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncaoArtista))
            )
            .andExpect(status().isOk());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeUpdate);
        FuncaoArtista testFuncaoArtista = funcaoArtistaList.get(funcaoArtistaList.size() - 1);
        assertThat(testFuncaoArtista.getFuncaoArtista()).isEqualTo(DEFAULT_FUNCAO_ARTISTA);
        assertThat(testFuncaoArtista.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateFuncaoArtistaWithPatch() throws Exception {
        // Initialize the database
        funcaoArtistaRepository.saveAndFlush(funcaoArtista);

        int databaseSizeBeforeUpdate = funcaoArtistaRepository.findAll().size();

        // Update the funcaoArtista using partial update
        FuncaoArtista partialUpdatedFuncaoArtista = new FuncaoArtista();
        partialUpdatedFuncaoArtista.setId(funcaoArtista.getId());

        partialUpdatedFuncaoArtista.funcaoArtista(UPDATED_FUNCAO_ARTISTA).inativo(UPDATED_INATIVO);

        restFuncaoArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncaoArtista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncaoArtista))
            )
            .andExpect(status().isOk());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeUpdate);
        FuncaoArtista testFuncaoArtista = funcaoArtistaList.get(funcaoArtistaList.size() - 1);
        assertThat(testFuncaoArtista.getFuncaoArtista()).isEqualTo(UPDATED_FUNCAO_ARTISTA);
        assertThat(testFuncaoArtista.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingFuncaoArtista() throws Exception {
        int databaseSizeBeforeUpdate = funcaoArtistaRepository.findAll().size();
        funcaoArtista.setId(count.incrementAndGet());

        // Create the FuncaoArtista
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(funcaoArtista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncaoArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcaoArtistaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncaoArtista() throws Exception {
        int databaseSizeBeforeUpdate = funcaoArtistaRepository.findAll().size();
        funcaoArtista.setId(count.incrementAndGet());

        // Create the FuncaoArtista
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(funcaoArtista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncaoArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncaoArtista() throws Exception {
        int databaseSizeBeforeUpdate = funcaoArtistaRepository.findAll().size();
        funcaoArtista.setId(count.incrementAndGet());

        // Create the FuncaoArtista
        FuncaoArtistaDTO funcaoArtistaDTO = funcaoArtistaMapper.toDto(funcaoArtista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncaoArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcaoArtistaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuncaoArtista in the database
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncaoArtista() throws Exception {
        // Initialize the database
        funcaoArtistaRepository.saveAndFlush(funcaoArtista);

        int databaseSizeBeforeDelete = funcaoArtistaRepository.findAll().size();

        // Delete the funcaoArtista
        restFuncaoArtistaMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcaoArtista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuncaoArtista> funcaoArtistaList = funcaoArtistaRepository.findAll();
        assertThat(funcaoArtistaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
