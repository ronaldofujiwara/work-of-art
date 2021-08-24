package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.AndarMapa;
import br.com.nhw.std.artes.repository.AndarMapaRepository;
import br.com.nhw.std.artes.service.dto.AndarMapaDTO;
import br.com.nhw.std.artes.service.mapper.AndarMapaMapper;
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
 * Integration tests for the {@link AndarMapaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AndarMapaResourceIT {

    private static final String DEFAULT_IMAGEM_MAPA = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM_MAPA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/andar-mapas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AndarMapaRepository andarMapaRepository;

    @Autowired
    private AndarMapaMapper andarMapaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAndarMapaMockMvc;

    private AndarMapa andarMapa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AndarMapa createEntity(EntityManager em) {
        AndarMapa andarMapa = new AndarMapa().imagemMapa(DEFAULT_IMAGEM_MAPA);
        return andarMapa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AndarMapa createUpdatedEntity(EntityManager em) {
        AndarMapa andarMapa = new AndarMapa().imagemMapa(UPDATED_IMAGEM_MAPA);
        return andarMapa;
    }

    @BeforeEach
    public void initTest() {
        andarMapa = createEntity(em);
    }

    @Test
    @Transactional
    void createAndarMapa() throws Exception {
        int databaseSizeBeforeCreate = andarMapaRepository.findAll().size();
        // Create the AndarMapa
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(andarMapa);
        restAndarMapaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(andarMapaDTO)))
            .andExpect(status().isCreated());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeCreate + 1);
        AndarMapa testAndarMapa = andarMapaList.get(andarMapaList.size() - 1);
        assertThat(testAndarMapa.getImagemMapa()).isEqualTo(DEFAULT_IMAGEM_MAPA);
    }

    @Test
    @Transactional
    void createAndarMapaWithExistingId() throws Exception {
        // Create the AndarMapa with an existing ID
        andarMapa.setId(1L);
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(andarMapa);

        int databaseSizeBeforeCreate = andarMapaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAndarMapaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(andarMapaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkImagemMapaIsRequired() throws Exception {
        int databaseSizeBeforeTest = andarMapaRepository.findAll().size();
        // set the field null
        andarMapa.setImagemMapa(null);

        // Create the AndarMapa, which fails.
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(andarMapa);

        restAndarMapaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(andarMapaDTO)))
            .andExpect(status().isBadRequest());

        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAndarMapas() throws Exception {
        // Initialize the database
        andarMapaRepository.saveAndFlush(andarMapa);

        // Get all the andarMapaList
        restAndarMapaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(andarMapa.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemMapa").value(hasItem(DEFAULT_IMAGEM_MAPA)));
    }

    @Test
    @Transactional
    void getAndarMapa() throws Exception {
        // Initialize the database
        andarMapaRepository.saveAndFlush(andarMapa);

        // Get the andarMapa
        restAndarMapaMockMvc
            .perform(get(ENTITY_API_URL_ID, andarMapa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(andarMapa.getId().intValue()))
            .andExpect(jsonPath("$.imagemMapa").value(DEFAULT_IMAGEM_MAPA));
    }

    @Test
    @Transactional
    void getNonExistingAndarMapa() throws Exception {
        // Get the andarMapa
        restAndarMapaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAndarMapa() throws Exception {
        // Initialize the database
        andarMapaRepository.saveAndFlush(andarMapa);

        int databaseSizeBeforeUpdate = andarMapaRepository.findAll().size();

        // Update the andarMapa
        AndarMapa updatedAndarMapa = andarMapaRepository.findById(andarMapa.getId()).get();
        // Disconnect from session so that the updates on updatedAndarMapa are not directly saved in db
        em.detach(updatedAndarMapa);
        updatedAndarMapa.imagemMapa(UPDATED_IMAGEM_MAPA);
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(updatedAndarMapa);

        restAndarMapaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, andarMapaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(andarMapaDTO))
            )
            .andExpect(status().isOk());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeUpdate);
        AndarMapa testAndarMapa = andarMapaList.get(andarMapaList.size() - 1);
        assertThat(testAndarMapa.getImagemMapa()).isEqualTo(UPDATED_IMAGEM_MAPA);
    }

    @Test
    @Transactional
    void putNonExistingAndarMapa() throws Exception {
        int databaseSizeBeforeUpdate = andarMapaRepository.findAll().size();
        andarMapa.setId(count.incrementAndGet());

        // Create the AndarMapa
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(andarMapa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAndarMapaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, andarMapaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(andarMapaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAndarMapa() throws Exception {
        int databaseSizeBeforeUpdate = andarMapaRepository.findAll().size();
        andarMapa.setId(count.incrementAndGet());

        // Create the AndarMapa
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(andarMapa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAndarMapaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(andarMapaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAndarMapa() throws Exception {
        int databaseSizeBeforeUpdate = andarMapaRepository.findAll().size();
        andarMapa.setId(count.incrementAndGet());

        // Create the AndarMapa
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(andarMapa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAndarMapaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(andarMapaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAndarMapaWithPatch() throws Exception {
        // Initialize the database
        andarMapaRepository.saveAndFlush(andarMapa);

        int databaseSizeBeforeUpdate = andarMapaRepository.findAll().size();

        // Update the andarMapa using partial update
        AndarMapa partialUpdatedAndarMapa = new AndarMapa();
        partialUpdatedAndarMapa.setId(andarMapa.getId());

        partialUpdatedAndarMapa.imagemMapa(UPDATED_IMAGEM_MAPA);

        restAndarMapaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAndarMapa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAndarMapa))
            )
            .andExpect(status().isOk());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeUpdate);
        AndarMapa testAndarMapa = andarMapaList.get(andarMapaList.size() - 1);
        assertThat(testAndarMapa.getImagemMapa()).isEqualTo(UPDATED_IMAGEM_MAPA);
    }

    @Test
    @Transactional
    void fullUpdateAndarMapaWithPatch() throws Exception {
        // Initialize the database
        andarMapaRepository.saveAndFlush(andarMapa);

        int databaseSizeBeforeUpdate = andarMapaRepository.findAll().size();

        // Update the andarMapa using partial update
        AndarMapa partialUpdatedAndarMapa = new AndarMapa();
        partialUpdatedAndarMapa.setId(andarMapa.getId());

        partialUpdatedAndarMapa.imagemMapa(UPDATED_IMAGEM_MAPA);

        restAndarMapaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAndarMapa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAndarMapa))
            )
            .andExpect(status().isOk());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeUpdate);
        AndarMapa testAndarMapa = andarMapaList.get(andarMapaList.size() - 1);
        assertThat(testAndarMapa.getImagemMapa()).isEqualTo(UPDATED_IMAGEM_MAPA);
    }

    @Test
    @Transactional
    void patchNonExistingAndarMapa() throws Exception {
        int databaseSizeBeforeUpdate = andarMapaRepository.findAll().size();
        andarMapa.setId(count.incrementAndGet());

        // Create the AndarMapa
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(andarMapa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAndarMapaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, andarMapaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(andarMapaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAndarMapa() throws Exception {
        int databaseSizeBeforeUpdate = andarMapaRepository.findAll().size();
        andarMapa.setId(count.incrementAndGet());

        // Create the AndarMapa
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(andarMapa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAndarMapaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(andarMapaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAndarMapa() throws Exception {
        int databaseSizeBeforeUpdate = andarMapaRepository.findAll().size();
        andarMapa.setId(count.incrementAndGet());

        // Create the AndarMapa
        AndarMapaDTO andarMapaDTO = andarMapaMapper.toDto(andarMapa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAndarMapaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(andarMapaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AndarMapa in the database
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAndarMapa() throws Exception {
        // Initialize the database
        andarMapaRepository.saveAndFlush(andarMapa);

        int databaseSizeBeforeDelete = andarMapaRepository.findAll().size();

        // Delete the andarMapa
        restAndarMapaMockMvc
            .perform(delete(ENTITY_API_URL_ID, andarMapa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AndarMapa> andarMapaList = andarMapaRepository.findAll();
        assertThat(andarMapaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
