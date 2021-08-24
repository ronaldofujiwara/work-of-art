package br.com.nhw.std.artes.web.rest;

import static br.com.nhw.std.artes.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Seguro;
import br.com.nhw.std.artes.repository.SeguroRepository;
import br.com.nhw.std.artes.service.dto.SeguroDTO;
import br.com.nhw.std.artes.service.mapper.SeguroMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link SeguroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SeguroResourceIT {

    private static final String DEFAULT_APOLICE = "AAAAAAAAAA";
    private static final String UPDATED_APOLICE = "BBBBBBBBBB";

    private static final String DEFAULT_OBS_SEGURO = "AAAAAAAAAA";
    private static final String UPDATED_OBS_SEGURO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRATO_PROPOSTA = "AAAAAAAAAA";
    private static final String UPDATED_CONTRATO_PROPOSTA = "BBBBBBBBBB";

    private static final String DEFAULT_CIA_SEGURADORA = "AAAAAAAAAA";
    private static final String UPDATED_CIA_SEGURADORA = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ_SEGURADORA = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ_SEGURADORA = "BBBBBBBBBB";

    private static final String DEFAULT_SUSEP_SEGURADORA = "AAAAAAAAAA";
    private static final String UPDATED_SUSEP_SEGURADORA = "BBBBBBBBBB";

    private static final String DEFAULT_CORRETORA = "AAAAAAAAAA";
    private static final String UPDATED_CORRETORA = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ_CORRETORA = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ_CORRETORA = "BBBBBBBBBB";

    private static final String DEFAULT_SUSEP_CORRETORA = "AAAAAAAAAA";
    private static final String UPDATED_SUSEP_CORRETORA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_VENC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENC = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String DEFAULT_PREMIO = "AAAAAAAAAA";
    private static final String UPDATED_PREMIO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TX_CONVERSAO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TX_CONVERSAO = new BigDecimal(2);

    private static final Integer DEFAULT_GEN_STATUS_SEGURO = 1;
    private static final Integer UPDATED_GEN_STATUS_SEGURO = 2;

    private static final LocalDate DEFAULT_DATA_ATUAL_SEGURO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ATUAL_SEGURO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/seguros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private SeguroMapper seguroMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSeguroMockMvc;

    private Seguro seguro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seguro createEntity(EntityManager em) {
        Seguro seguro = new Seguro()
            .apolice(DEFAULT_APOLICE)
            .obsSeguro(DEFAULT_OBS_SEGURO)
            .contratoProposta(DEFAULT_CONTRATO_PROPOSTA)
            .ciaSeguradora(DEFAULT_CIA_SEGURADORA)
            .cnpjSeguradora(DEFAULT_CNPJ_SEGURADORA)
            .susepSeguradora(DEFAULT_SUSEP_SEGURADORA)
            .corretora(DEFAULT_CORRETORA)
            .cnpjCorretora(DEFAULT_CNPJ_CORRETORA)
            .susepCorretora(DEFAULT_SUSEP_CORRETORA)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataVenc(DEFAULT_DATA_VENC)
            .inativo(DEFAULT_INATIVO)
            .premio(DEFAULT_PREMIO)
            .txConversao(DEFAULT_TX_CONVERSAO)
            .genStatusSeguro(DEFAULT_GEN_STATUS_SEGURO)
            .dataAtualSeguro(DEFAULT_DATA_ATUAL_SEGURO);
        return seguro;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seguro createUpdatedEntity(EntityManager em) {
        Seguro seguro = new Seguro()
            .apolice(UPDATED_APOLICE)
            .obsSeguro(UPDATED_OBS_SEGURO)
            .contratoProposta(UPDATED_CONTRATO_PROPOSTA)
            .ciaSeguradora(UPDATED_CIA_SEGURADORA)
            .cnpjSeguradora(UPDATED_CNPJ_SEGURADORA)
            .susepSeguradora(UPDATED_SUSEP_SEGURADORA)
            .corretora(UPDATED_CORRETORA)
            .cnpjCorretora(UPDATED_CNPJ_CORRETORA)
            .susepCorretora(UPDATED_SUSEP_CORRETORA)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataVenc(UPDATED_DATA_VENC)
            .inativo(UPDATED_INATIVO)
            .premio(UPDATED_PREMIO)
            .txConversao(UPDATED_TX_CONVERSAO)
            .genStatusSeguro(UPDATED_GEN_STATUS_SEGURO)
            .dataAtualSeguro(UPDATED_DATA_ATUAL_SEGURO);
        return seguro;
    }

    @BeforeEach
    public void initTest() {
        seguro = createEntity(em);
    }

    @Test
    @Transactional
    void createSeguro() throws Exception {
        int databaseSizeBeforeCreate = seguroRepository.findAll().size();
        // Create the Seguro
        SeguroDTO seguroDTO = seguroMapper.toDto(seguro);
        restSeguroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seguroDTO)))
            .andExpect(status().isCreated());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeCreate + 1);
        Seguro testSeguro = seguroList.get(seguroList.size() - 1);
        assertThat(testSeguro.getApolice()).isEqualTo(DEFAULT_APOLICE);
        assertThat(testSeguro.getObsSeguro()).isEqualTo(DEFAULT_OBS_SEGURO);
        assertThat(testSeguro.getContratoProposta()).isEqualTo(DEFAULT_CONTRATO_PROPOSTA);
        assertThat(testSeguro.getCiaSeguradora()).isEqualTo(DEFAULT_CIA_SEGURADORA);
        assertThat(testSeguro.getCnpjSeguradora()).isEqualTo(DEFAULT_CNPJ_SEGURADORA);
        assertThat(testSeguro.getSusepSeguradora()).isEqualTo(DEFAULT_SUSEP_SEGURADORA);
        assertThat(testSeguro.getCorretora()).isEqualTo(DEFAULT_CORRETORA);
        assertThat(testSeguro.getCnpjCorretora()).isEqualTo(DEFAULT_CNPJ_CORRETORA);
        assertThat(testSeguro.getSusepCorretora()).isEqualTo(DEFAULT_SUSEP_CORRETORA);
        assertThat(testSeguro.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testSeguro.getDataVenc()).isEqualTo(DEFAULT_DATA_VENC);
        assertThat(testSeguro.getInativo()).isEqualTo(DEFAULT_INATIVO);
        assertThat(testSeguro.getPremio()).isEqualTo(DEFAULT_PREMIO);
        assertThat(testSeguro.getTxConversao()).isEqualByComparingTo(DEFAULT_TX_CONVERSAO);
        assertThat(testSeguro.getGenStatusSeguro()).isEqualTo(DEFAULT_GEN_STATUS_SEGURO);
        assertThat(testSeguro.getDataAtualSeguro()).isEqualTo(DEFAULT_DATA_ATUAL_SEGURO);
    }

    @Test
    @Transactional
    void createSeguroWithExistingId() throws Exception {
        // Create the Seguro with an existing ID
        seguro.setId(1L);
        SeguroDTO seguroDTO = seguroMapper.toDto(seguro);

        int databaseSizeBeforeCreate = seguroRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeguroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seguroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSeguros() throws Exception {
        // Initialize the database
        seguroRepository.saveAndFlush(seguro);

        // Get all the seguroList
        restSeguroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seguro.getId().intValue())))
            .andExpect(jsonPath("$.[*].apolice").value(hasItem(DEFAULT_APOLICE)))
            .andExpect(jsonPath("$.[*].obsSeguro").value(hasItem(DEFAULT_OBS_SEGURO)))
            .andExpect(jsonPath("$.[*].contratoProposta").value(hasItem(DEFAULT_CONTRATO_PROPOSTA)))
            .andExpect(jsonPath("$.[*].ciaSeguradora").value(hasItem(DEFAULT_CIA_SEGURADORA)))
            .andExpect(jsonPath("$.[*].cnpjSeguradora").value(hasItem(DEFAULT_CNPJ_SEGURADORA)))
            .andExpect(jsonPath("$.[*].susepSeguradora").value(hasItem(DEFAULT_SUSEP_SEGURADORA)))
            .andExpect(jsonPath("$.[*].corretora").value(hasItem(DEFAULT_CORRETORA)))
            .andExpect(jsonPath("$.[*].cnpjCorretora").value(hasItem(DEFAULT_CNPJ_CORRETORA)))
            .andExpect(jsonPath("$.[*].susepCorretora").value(hasItem(DEFAULT_SUSEP_CORRETORA)))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataVenc").value(hasItem(DEFAULT_DATA_VENC.toString())))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].premio").value(hasItem(DEFAULT_PREMIO)))
            .andExpect(jsonPath("$.[*].txConversao").value(hasItem(sameNumber(DEFAULT_TX_CONVERSAO))))
            .andExpect(jsonPath("$.[*].genStatusSeguro").value(hasItem(DEFAULT_GEN_STATUS_SEGURO)))
            .andExpect(jsonPath("$.[*].dataAtualSeguro").value(hasItem(DEFAULT_DATA_ATUAL_SEGURO.toString())));
    }

    @Test
    @Transactional
    void getSeguro() throws Exception {
        // Initialize the database
        seguroRepository.saveAndFlush(seguro);

        // Get the seguro
        restSeguroMockMvc
            .perform(get(ENTITY_API_URL_ID, seguro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(seguro.getId().intValue()))
            .andExpect(jsonPath("$.apolice").value(DEFAULT_APOLICE))
            .andExpect(jsonPath("$.obsSeguro").value(DEFAULT_OBS_SEGURO))
            .andExpect(jsonPath("$.contratoProposta").value(DEFAULT_CONTRATO_PROPOSTA))
            .andExpect(jsonPath("$.ciaSeguradora").value(DEFAULT_CIA_SEGURADORA))
            .andExpect(jsonPath("$.cnpjSeguradora").value(DEFAULT_CNPJ_SEGURADORA))
            .andExpect(jsonPath("$.susepSeguradora").value(DEFAULT_SUSEP_SEGURADORA))
            .andExpect(jsonPath("$.corretora").value(DEFAULT_CORRETORA))
            .andExpect(jsonPath("$.cnpjCorretora").value(DEFAULT_CNPJ_CORRETORA))
            .andExpect(jsonPath("$.susepCorretora").value(DEFAULT_SUSEP_CORRETORA))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataVenc").value(DEFAULT_DATA_VENC.toString()))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()))
            .andExpect(jsonPath("$.premio").value(DEFAULT_PREMIO))
            .andExpect(jsonPath("$.txConversao").value(sameNumber(DEFAULT_TX_CONVERSAO)))
            .andExpect(jsonPath("$.genStatusSeguro").value(DEFAULT_GEN_STATUS_SEGURO))
            .andExpect(jsonPath("$.dataAtualSeguro").value(DEFAULT_DATA_ATUAL_SEGURO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSeguro() throws Exception {
        // Get the seguro
        restSeguroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSeguro() throws Exception {
        // Initialize the database
        seguroRepository.saveAndFlush(seguro);

        int databaseSizeBeforeUpdate = seguroRepository.findAll().size();

        // Update the seguro
        Seguro updatedSeguro = seguroRepository.findById(seguro.getId()).get();
        // Disconnect from session so that the updates on updatedSeguro are not directly saved in db
        em.detach(updatedSeguro);
        updatedSeguro
            .apolice(UPDATED_APOLICE)
            .obsSeguro(UPDATED_OBS_SEGURO)
            .contratoProposta(UPDATED_CONTRATO_PROPOSTA)
            .ciaSeguradora(UPDATED_CIA_SEGURADORA)
            .cnpjSeguradora(UPDATED_CNPJ_SEGURADORA)
            .susepSeguradora(UPDATED_SUSEP_SEGURADORA)
            .corretora(UPDATED_CORRETORA)
            .cnpjCorretora(UPDATED_CNPJ_CORRETORA)
            .susepCorretora(UPDATED_SUSEP_CORRETORA)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataVenc(UPDATED_DATA_VENC)
            .inativo(UPDATED_INATIVO)
            .premio(UPDATED_PREMIO)
            .txConversao(UPDATED_TX_CONVERSAO)
            .genStatusSeguro(UPDATED_GEN_STATUS_SEGURO)
            .dataAtualSeguro(UPDATED_DATA_ATUAL_SEGURO);
        SeguroDTO seguroDTO = seguroMapper.toDto(updatedSeguro);

        restSeguroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seguroDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seguroDTO))
            )
            .andExpect(status().isOk());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeUpdate);
        Seguro testSeguro = seguroList.get(seguroList.size() - 1);
        assertThat(testSeguro.getApolice()).isEqualTo(UPDATED_APOLICE);
        assertThat(testSeguro.getObsSeguro()).isEqualTo(UPDATED_OBS_SEGURO);
        assertThat(testSeguro.getContratoProposta()).isEqualTo(UPDATED_CONTRATO_PROPOSTA);
        assertThat(testSeguro.getCiaSeguradora()).isEqualTo(UPDATED_CIA_SEGURADORA);
        assertThat(testSeguro.getCnpjSeguradora()).isEqualTo(UPDATED_CNPJ_SEGURADORA);
        assertThat(testSeguro.getSusepSeguradora()).isEqualTo(UPDATED_SUSEP_SEGURADORA);
        assertThat(testSeguro.getCorretora()).isEqualTo(UPDATED_CORRETORA);
        assertThat(testSeguro.getCnpjCorretora()).isEqualTo(UPDATED_CNPJ_CORRETORA);
        assertThat(testSeguro.getSusepCorretora()).isEqualTo(UPDATED_SUSEP_CORRETORA);
        assertThat(testSeguro.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testSeguro.getDataVenc()).isEqualTo(UPDATED_DATA_VENC);
        assertThat(testSeguro.getInativo()).isEqualTo(UPDATED_INATIVO);
        assertThat(testSeguro.getPremio()).isEqualTo(UPDATED_PREMIO);
        assertThat(testSeguro.getTxConversao()).isEqualTo(UPDATED_TX_CONVERSAO);
        assertThat(testSeguro.getGenStatusSeguro()).isEqualTo(UPDATED_GEN_STATUS_SEGURO);
        assertThat(testSeguro.getDataAtualSeguro()).isEqualTo(UPDATED_DATA_ATUAL_SEGURO);
    }

    @Test
    @Transactional
    void putNonExistingSeguro() throws Exception {
        int databaseSizeBeforeUpdate = seguroRepository.findAll().size();
        seguro.setId(count.incrementAndGet());

        // Create the Seguro
        SeguroDTO seguroDTO = seguroMapper.toDto(seguro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeguroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, seguroDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seguroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSeguro() throws Exception {
        int databaseSizeBeforeUpdate = seguroRepository.findAll().size();
        seguro.setId(count.incrementAndGet());

        // Create the Seguro
        SeguroDTO seguroDTO = seguroMapper.toDto(seguro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(seguroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSeguro() throws Exception {
        int databaseSizeBeforeUpdate = seguroRepository.findAll().size();
        seguro.setId(count.incrementAndGet());

        // Create the Seguro
        SeguroDTO seguroDTO = seguroMapper.toDto(seguro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(seguroDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSeguroWithPatch() throws Exception {
        // Initialize the database
        seguroRepository.saveAndFlush(seguro);

        int databaseSizeBeforeUpdate = seguroRepository.findAll().size();

        // Update the seguro using partial update
        Seguro partialUpdatedSeguro = new Seguro();
        partialUpdatedSeguro.setId(seguro.getId());

        partialUpdatedSeguro
            .apolice(UPDATED_APOLICE)
            .obsSeguro(UPDATED_OBS_SEGURO)
            .contratoProposta(UPDATED_CONTRATO_PROPOSTA)
            .ciaSeguradora(UPDATED_CIA_SEGURADORA)
            .cnpjSeguradora(UPDATED_CNPJ_SEGURADORA)
            .susepCorretora(UPDATED_SUSEP_CORRETORA)
            .premio(UPDATED_PREMIO);

        restSeguroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeguro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeguro))
            )
            .andExpect(status().isOk());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeUpdate);
        Seguro testSeguro = seguroList.get(seguroList.size() - 1);
        assertThat(testSeguro.getApolice()).isEqualTo(UPDATED_APOLICE);
        assertThat(testSeguro.getObsSeguro()).isEqualTo(UPDATED_OBS_SEGURO);
        assertThat(testSeguro.getContratoProposta()).isEqualTo(UPDATED_CONTRATO_PROPOSTA);
        assertThat(testSeguro.getCiaSeguradora()).isEqualTo(UPDATED_CIA_SEGURADORA);
        assertThat(testSeguro.getCnpjSeguradora()).isEqualTo(UPDATED_CNPJ_SEGURADORA);
        assertThat(testSeguro.getSusepSeguradora()).isEqualTo(DEFAULT_SUSEP_SEGURADORA);
        assertThat(testSeguro.getCorretora()).isEqualTo(DEFAULT_CORRETORA);
        assertThat(testSeguro.getCnpjCorretora()).isEqualTo(DEFAULT_CNPJ_CORRETORA);
        assertThat(testSeguro.getSusepCorretora()).isEqualTo(UPDATED_SUSEP_CORRETORA);
        assertThat(testSeguro.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testSeguro.getDataVenc()).isEqualTo(DEFAULT_DATA_VENC);
        assertThat(testSeguro.getInativo()).isEqualTo(DEFAULT_INATIVO);
        assertThat(testSeguro.getPremio()).isEqualTo(UPDATED_PREMIO);
        assertThat(testSeguro.getTxConversao()).isEqualByComparingTo(DEFAULT_TX_CONVERSAO);
        assertThat(testSeguro.getGenStatusSeguro()).isEqualTo(DEFAULT_GEN_STATUS_SEGURO);
        assertThat(testSeguro.getDataAtualSeguro()).isEqualTo(DEFAULT_DATA_ATUAL_SEGURO);
    }

    @Test
    @Transactional
    void fullUpdateSeguroWithPatch() throws Exception {
        // Initialize the database
        seguroRepository.saveAndFlush(seguro);

        int databaseSizeBeforeUpdate = seguroRepository.findAll().size();

        // Update the seguro using partial update
        Seguro partialUpdatedSeguro = new Seguro();
        partialUpdatedSeguro.setId(seguro.getId());

        partialUpdatedSeguro
            .apolice(UPDATED_APOLICE)
            .obsSeguro(UPDATED_OBS_SEGURO)
            .contratoProposta(UPDATED_CONTRATO_PROPOSTA)
            .ciaSeguradora(UPDATED_CIA_SEGURADORA)
            .cnpjSeguradora(UPDATED_CNPJ_SEGURADORA)
            .susepSeguradora(UPDATED_SUSEP_SEGURADORA)
            .corretora(UPDATED_CORRETORA)
            .cnpjCorretora(UPDATED_CNPJ_CORRETORA)
            .susepCorretora(UPDATED_SUSEP_CORRETORA)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataVenc(UPDATED_DATA_VENC)
            .inativo(UPDATED_INATIVO)
            .premio(UPDATED_PREMIO)
            .txConversao(UPDATED_TX_CONVERSAO)
            .genStatusSeguro(UPDATED_GEN_STATUS_SEGURO)
            .dataAtualSeguro(UPDATED_DATA_ATUAL_SEGURO);

        restSeguroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSeguro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSeguro))
            )
            .andExpect(status().isOk());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeUpdate);
        Seguro testSeguro = seguroList.get(seguroList.size() - 1);
        assertThat(testSeguro.getApolice()).isEqualTo(UPDATED_APOLICE);
        assertThat(testSeguro.getObsSeguro()).isEqualTo(UPDATED_OBS_SEGURO);
        assertThat(testSeguro.getContratoProposta()).isEqualTo(UPDATED_CONTRATO_PROPOSTA);
        assertThat(testSeguro.getCiaSeguradora()).isEqualTo(UPDATED_CIA_SEGURADORA);
        assertThat(testSeguro.getCnpjSeguradora()).isEqualTo(UPDATED_CNPJ_SEGURADORA);
        assertThat(testSeguro.getSusepSeguradora()).isEqualTo(UPDATED_SUSEP_SEGURADORA);
        assertThat(testSeguro.getCorretora()).isEqualTo(UPDATED_CORRETORA);
        assertThat(testSeguro.getCnpjCorretora()).isEqualTo(UPDATED_CNPJ_CORRETORA);
        assertThat(testSeguro.getSusepCorretora()).isEqualTo(UPDATED_SUSEP_CORRETORA);
        assertThat(testSeguro.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testSeguro.getDataVenc()).isEqualTo(UPDATED_DATA_VENC);
        assertThat(testSeguro.getInativo()).isEqualTo(UPDATED_INATIVO);
        assertThat(testSeguro.getPremio()).isEqualTo(UPDATED_PREMIO);
        assertThat(testSeguro.getTxConversao()).isEqualByComparingTo(UPDATED_TX_CONVERSAO);
        assertThat(testSeguro.getGenStatusSeguro()).isEqualTo(UPDATED_GEN_STATUS_SEGURO);
        assertThat(testSeguro.getDataAtualSeguro()).isEqualTo(UPDATED_DATA_ATUAL_SEGURO);
    }

    @Test
    @Transactional
    void patchNonExistingSeguro() throws Exception {
        int databaseSizeBeforeUpdate = seguroRepository.findAll().size();
        seguro.setId(count.incrementAndGet());

        // Create the Seguro
        SeguroDTO seguroDTO = seguroMapper.toDto(seguro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeguroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, seguroDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seguroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSeguro() throws Exception {
        int databaseSizeBeforeUpdate = seguroRepository.findAll().size();
        seguro.setId(count.incrementAndGet());

        // Create the Seguro
        SeguroDTO seguroDTO = seguroMapper.toDto(seguro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(seguroDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSeguro() throws Exception {
        int databaseSizeBeforeUpdate = seguroRepository.findAll().size();
        seguro.setId(count.incrementAndGet());

        // Create the Seguro
        SeguroDTO seguroDTO = seguroMapper.toDto(seguro);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSeguroMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(seguroDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Seguro in the database
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSeguro() throws Exception {
        // Initialize the database
        seguroRepository.saveAndFlush(seguro);

        int databaseSizeBeforeDelete = seguroRepository.findAll().size();

        // Delete the seguro
        restSeguroMockMvc
            .perform(delete(ENTITY_API_URL_ID, seguro.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Seguro> seguroList = seguroRepository.findAll();
        assertThat(seguroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
