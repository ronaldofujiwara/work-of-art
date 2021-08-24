package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Tecnica;
import br.com.nhw.std.artes.repository.TecnicaRepository;
import br.com.nhw.std.artes.service.dto.TecnicaDTO;
import br.com.nhw.std.artes.service.mapper.TecnicaMapper;
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
 * Integration tests for the {@link TecnicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TecnicaResourceIT {

    private static final String DEFAULT_TECNICA = "AAAAAAAAAA";
    private static final String UPDATED_TECNICA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/tecnicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TecnicaRepository tecnicaRepository;

    @Autowired
    private TecnicaMapper tecnicaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTecnicaMockMvc;

    private Tecnica tecnica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tecnica createEntity(EntityManager em) {
        Tecnica tecnica = new Tecnica().tecnica(DEFAULT_TECNICA).inativo(DEFAULT_INATIVO);
        return tecnica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tecnica createUpdatedEntity(EntityManager em) {
        Tecnica tecnica = new Tecnica().tecnica(UPDATED_TECNICA).inativo(UPDATED_INATIVO);
        return tecnica;
    }

    @BeforeEach
    public void initTest() {
        tecnica = createEntity(em);
    }

    @Test
    @Transactional
    void createTecnica() throws Exception {
        int databaseSizeBeforeCreate = tecnicaRepository.findAll().size();
        // Create the Tecnica
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(tecnica);
        restTecnicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tecnicaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeCreate + 1);
        Tecnica testTecnica = tecnicaList.get(tecnicaList.size() - 1);
        assertThat(testTecnica.getTecnica()).isEqualTo(DEFAULT_TECNICA);
        assertThat(testTecnica.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createTecnicaWithExistingId() throws Exception {
        // Create the Tecnica with an existing ID
        tecnica.setId(1L);
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(tecnica);

        int databaseSizeBeforeCreate = tecnicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTecnicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tecnicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTecnicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tecnicaRepository.findAll().size();
        // set the field null
        tecnica.setTecnica(null);

        // Create the Tecnica, which fails.
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(tecnica);

        restTecnicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tecnicaDTO)))
            .andExpect(status().isBadRequest());

        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTecnicas() throws Exception {
        // Initialize the database
        tecnicaRepository.saveAndFlush(tecnica);

        // Get all the tecnicaList
        restTecnicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tecnica.getId().intValue())))
            .andExpect(jsonPath("$.[*].tecnica").value(hasItem(DEFAULT_TECNICA)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getTecnica() throws Exception {
        // Initialize the database
        tecnicaRepository.saveAndFlush(tecnica);

        // Get the tecnica
        restTecnicaMockMvc
            .perform(get(ENTITY_API_URL_ID, tecnica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tecnica.getId().intValue()))
            .andExpect(jsonPath("$.tecnica").value(DEFAULT_TECNICA))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTecnica() throws Exception {
        // Get the tecnica
        restTecnicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTecnica() throws Exception {
        // Initialize the database
        tecnicaRepository.saveAndFlush(tecnica);

        int databaseSizeBeforeUpdate = tecnicaRepository.findAll().size();

        // Update the tecnica
        Tecnica updatedTecnica = tecnicaRepository.findById(tecnica.getId()).get();
        // Disconnect from session so that the updates on updatedTecnica are not directly saved in db
        em.detach(updatedTecnica);
        updatedTecnica.tecnica(UPDATED_TECNICA).inativo(UPDATED_INATIVO);
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(updatedTecnica);

        restTecnicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tecnicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tecnicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeUpdate);
        Tecnica testTecnica = tecnicaList.get(tecnicaList.size() - 1);
        assertThat(testTecnica.getTecnica()).isEqualTo(UPDATED_TECNICA);
        assertThat(testTecnica.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingTecnica() throws Exception {
        int databaseSizeBeforeUpdate = tecnicaRepository.findAll().size();
        tecnica.setId(count.incrementAndGet());

        // Create the Tecnica
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(tecnica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTecnicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tecnicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tecnicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTecnica() throws Exception {
        int databaseSizeBeforeUpdate = tecnicaRepository.findAll().size();
        tecnica.setId(count.incrementAndGet());

        // Create the Tecnica
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(tecnica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTecnicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tecnicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTecnica() throws Exception {
        int databaseSizeBeforeUpdate = tecnicaRepository.findAll().size();
        tecnica.setId(count.incrementAndGet());

        // Create the Tecnica
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(tecnica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTecnicaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tecnicaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTecnicaWithPatch() throws Exception {
        // Initialize the database
        tecnicaRepository.saveAndFlush(tecnica);

        int databaseSizeBeforeUpdate = tecnicaRepository.findAll().size();

        // Update the tecnica using partial update
        Tecnica partialUpdatedTecnica = new Tecnica();
        partialUpdatedTecnica.setId(tecnica.getId());

        partialUpdatedTecnica.tecnica(UPDATED_TECNICA);

        restTecnicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTecnica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTecnica))
            )
            .andExpect(status().isOk());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeUpdate);
        Tecnica testTecnica = tecnicaList.get(tecnicaList.size() - 1);
        assertThat(testTecnica.getTecnica()).isEqualTo(UPDATED_TECNICA);
        assertThat(testTecnica.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateTecnicaWithPatch() throws Exception {
        // Initialize the database
        tecnicaRepository.saveAndFlush(tecnica);

        int databaseSizeBeforeUpdate = tecnicaRepository.findAll().size();

        // Update the tecnica using partial update
        Tecnica partialUpdatedTecnica = new Tecnica();
        partialUpdatedTecnica.setId(tecnica.getId());

        partialUpdatedTecnica.tecnica(UPDATED_TECNICA).inativo(UPDATED_INATIVO);

        restTecnicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTecnica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTecnica))
            )
            .andExpect(status().isOk());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeUpdate);
        Tecnica testTecnica = tecnicaList.get(tecnicaList.size() - 1);
        assertThat(testTecnica.getTecnica()).isEqualTo(UPDATED_TECNICA);
        assertThat(testTecnica.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingTecnica() throws Exception {
        int databaseSizeBeforeUpdate = tecnicaRepository.findAll().size();
        tecnica.setId(count.incrementAndGet());

        // Create the Tecnica
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(tecnica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTecnicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tecnicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tecnicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTecnica() throws Exception {
        int databaseSizeBeforeUpdate = tecnicaRepository.findAll().size();
        tecnica.setId(count.incrementAndGet());

        // Create the Tecnica
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(tecnica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTecnicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tecnicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTecnica() throws Exception {
        int databaseSizeBeforeUpdate = tecnicaRepository.findAll().size();
        tecnica.setId(count.incrementAndGet());

        // Create the Tecnica
        TecnicaDTO tecnicaDTO = tecnicaMapper.toDto(tecnica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTecnicaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tecnicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tecnica in the database
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTecnica() throws Exception {
        // Initialize the database
        tecnicaRepository.saveAndFlush(tecnica);

        int databaseSizeBeforeDelete = tecnicaRepository.findAll().size();

        // Delete the tecnica
        restTecnicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tecnica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tecnica> tecnicaList = tecnicaRepository.findAll();
        assertThat(tecnicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
