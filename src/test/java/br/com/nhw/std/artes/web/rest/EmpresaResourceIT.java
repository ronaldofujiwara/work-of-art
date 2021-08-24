package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Empresa;
import br.com.nhw.std.artes.repository.EmpresaRepository;
import br.com.nhw.std.artes.service.dto.EmpresaDTO;
import br.com.nhw.std.artes.service.mapper.EmpresaMapper;
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
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpresaResourceIT {

    private static final String DEFAULT_NOME_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_EMPRESA = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGO_BRAD = 1;
    private static final Integer UPDATED_CODIGO_BRAD = 2;

    private static final String DEFAULT_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCAO = "AAAAAAAAAA";
    private static final String UPDATED_FUNCAO = "BBBBBBBBBB";

    private static final String DEFAULT_C_NPJ = "AAAAAAAAAA";
    private static final String UPDATED_C_NPJ = "BBBBBBBBBB";

    private static final String DEFAULT_INSCRICAO_ESTADUAL = "AAAAAAAAAA";
    private static final String UPDATED_INSCRICAO_ESTADUAL = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String DEFAULT_RUA = "AAAAAAAAAA";
    private static final String UPDATED_RUA = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAA";
    private static final String UPDATED_NUMERO = "BBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_C_EP = "AAAAAAAA";
    private static final String UPDATED_C_EP = "BBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CREDITO = "AAAAAAAAAA";
    private static final String UPDATED_CREDITO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final Integer DEFAULT_GEN_EMAIL = 1;
    private static final Integer UPDATED_GEN_EMAIL = 2;

    private static final String ENTITY_API_URL = "/api/empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nomeEmpresa(DEFAULT_NOME_EMPRESA)
            .codigoBrad(DEFAULT_CODIGO_BRAD)
            .empresa(DEFAULT_EMPRESA)
            .nome(DEFAULT_NOME)
            .funcao(DEFAULT_FUNCAO)
            .cNPJ(DEFAULT_C_NPJ)
            .inscricaoEstadual(DEFAULT_INSCRICAO_ESTADUAL)
            .obs(DEFAULT_OBS)
            .rua(DEFAULT_RUA)
            .numero(DEFAULT_NUMERO)
            .bairro(DEFAULT_BAIRRO)
            .complemento(DEFAULT_COMPLEMENTO)
            .cEP(DEFAULT_C_EP)
            .telefone(DEFAULT_TELEFONE)
            .fax(DEFAULT_FAX)
            .celular(DEFAULT_CELULAR)
            .email(DEFAULT_EMAIL)
            .credito(DEFAULT_CREDITO)
            .inativo(DEFAULT_INATIVO)
            .genEmail(DEFAULT_GEN_EMAIL);
        return empresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .codigoBrad(UPDATED_CODIGO_BRAD)
            .empresa(UPDATED_EMPRESA)
            .nome(UPDATED_NOME)
            .funcao(UPDATED_FUNCAO)
            .cNPJ(UPDATED_C_NPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .obs(UPDATED_OBS)
            .rua(UPDATED_RUA)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .complemento(UPDATED_COMPLEMENTO)
            .cEP(UPDATED_C_EP)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .email(UPDATED_EMAIL)
            .credito(UPDATED_CREDITO)
            .inativo(UPDATED_INATIVO)
            .genEmail(UPDATED_GEN_EMAIL);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();
        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNomeEmpresa()).isEqualTo(DEFAULT_NOME_EMPRESA);
        assertThat(testEmpresa.getCodigoBrad()).isEqualTo(DEFAULT_CODIGO_BRAD);
        assertThat(testEmpresa.getEmpresa()).isEqualTo(DEFAULT_EMPRESA);
        assertThat(testEmpresa.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEmpresa.getFuncao()).isEqualTo(DEFAULT_FUNCAO);
        assertThat(testEmpresa.getcNPJ()).isEqualTo(DEFAULT_C_NPJ);
        assertThat(testEmpresa.getInscricaoEstadual()).isEqualTo(DEFAULT_INSCRICAO_ESTADUAL);
        assertThat(testEmpresa.getObs()).isEqualTo(DEFAULT_OBS);
        assertThat(testEmpresa.getRua()).isEqualTo(DEFAULT_RUA);
        assertThat(testEmpresa.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEmpresa.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEmpresa.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testEmpresa.getcEP()).isEqualTo(DEFAULT_C_EP);
        assertThat(testEmpresa.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testEmpresa.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testEmpresa.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testEmpresa.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmpresa.getCredito()).isEqualTo(DEFAULT_CREDITO);
        assertThat(testEmpresa.getInativo()).isEqualTo(DEFAULT_INATIVO);
        assertThat(testEmpresa.getGenEmail()).isEqualTo(DEFAULT_GEN_EMAIL);
    }

    @Test
    @Transactional
    void createEmpresaWithExistingId() throws Exception {
        // Create the Empresa with an existing ID
        empresa.setId(1L);
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setNomeEmpresa(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeEmpresa").value(hasItem(DEFAULT_NOME_EMPRESA)))
            .andExpect(jsonPath("$.[*].codigoBrad").value(hasItem(DEFAULT_CODIGO_BRAD)))
            .andExpect(jsonPath("$.[*].empresa").value(hasItem(DEFAULT_EMPRESA)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].funcao").value(hasItem(DEFAULT_FUNCAO)))
            .andExpect(jsonPath("$.[*].cNPJ").value(hasItem(DEFAULT_C_NPJ)))
            .andExpect(jsonPath("$.[*].inscricaoEstadual").value(hasItem(DEFAULT_INSCRICAO_ESTADUAL)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].cEP").value(hasItem(DEFAULT_C_EP)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].credito").value(hasItem(DEFAULT_CREDITO)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].genEmail").value(hasItem(DEFAULT_GEN_EMAIL)));
    }

    @Test
    @Transactional
    void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.nomeEmpresa").value(DEFAULT_NOME_EMPRESA))
            .andExpect(jsonPath("$.codigoBrad").value(DEFAULT_CODIGO_BRAD))
            .andExpect(jsonPath("$.empresa").value(DEFAULT_EMPRESA))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.funcao").value(DEFAULT_FUNCAO))
            .andExpect(jsonPath("$.cNPJ").value(DEFAULT_C_NPJ))
            .andExpect(jsonPath("$.inscricaoEstadual").value(DEFAULT_INSCRICAO_ESTADUAL))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS))
            .andExpect(jsonPath("$.rua").value(DEFAULT_RUA))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.cEP").value(DEFAULT_C_EP))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.credito").value(DEFAULT_CREDITO))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()))
            .andExpect(jsonPath("$.genEmail").value(DEFAULT_GEN_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .codigoBrad(UPDATED_CODIGO_BRAD)
            .empresa(UPDATED_EMPRESA)
            .nome(UPDATED_NOME)
            .funcao(UPDATED_FUNCAO)
            .cNPJ(UPDATED_C_NPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .obs(UPDATED_OBS)
            .rua(UPDATED_RUA)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .complemento(UPDATED_COMPLEMENTO)
            .cEP(UPDATED_C_EP)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .email(UPDATED_EMAIL)
            .credito(UPDATED_CREDITO)
            .inativo(UPDATED_INATIVO)
            .genEmail(UPDATED_GEN_EMAIL);
        EmpresaDTO empresaDTO = empresaMapper.toDto(updatedEmpresa);

        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNomeEmpresa()).isEqualTo(UPDATED_NOME_EMPRESA);
        assertThat(testEmpresa.getCodigoBrad()).isEqualTo(UPDATED_CODIGO_BRAD);
        assertThat(testEmpresa.getEmpresa()).isEqualTo(UPDATED_EMPRESA);
        assertThat(testEmpresa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmpresa.getFuncao()).isEqualTo(UPDATED_FUNCAO);
        assertThat(testEmpresa.getcNPJ()).isEqualTo(UPDATED_C_NPJ);
        assertThat(testEmpresa.getInscricaoEstadual()).isEqualTo(UPDATED_INSCRICAO_ESTADUAL);
        assertThat(testEmpresa.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testEmpresa.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testEmpresa.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEmpresa.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEmpresa.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEmpresa.getcEP()).isEqualTo(UPDATED_C_EP);
        assertThat(testEmpresa.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEmpresa.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testEmpresa.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testEmpresa.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmpresa.getCredito()).isEqualTo(UPDATED_CREDITO);
        assertThat(testEmpresa.getInativo()).isEqualTo(UPDATED_INATIVO);
        assertThat(testEmpresa.getGenEmail()).isEqualTo(UPDATED_GEN_EMAIL);
    }

    @Test
    @Transactional
    void putNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .codigoBrad(UPDATED_CODIGO_BRAD)
            .nome(UPDATED_NOME)
            .cNPJ(UPDATED_C_NPJ)
            .obs(UPDATED_OBS)
            .rua(UPDATED_RUA)
            .email(UPDATED_EMAIL)
            .credito(UPDATED_CREDITO)
            .inativo(UPDATED_INATIVO)
            .genEmail(UPDATED_GEN_EMAIL);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNomeEmpresa()).isEqualTo(UPDATED_NOME_EMPRESA);
        assertThat(testEmpresa.getCodigoBrad()).isEqualTo(UPDATED_CODIGO_BRAD);
        assertThat(testEmpresa.getEmpresa()).isEqualTo(DEFAULT_EMPRESA);
        assertThat(testEmpresa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmpresa.getFuncao()).isEqualTo(DEFAULT_FUNCAO);
        assertThat(testEmpresa.getcNPJ()).isEqualTo(UPDATED_C_NPJ);
        assertThat(testEmpresa.getInscricaoEstadual()).isEqualTo(DEFAULT_INSCRICAO_ESTADUAL);
        assertThat(testEmpresa.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testEmpresa.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testEmpresa.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEmpresa.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEmpresa.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testEmpresa.getcEP()).isEqualTo(DEFAULT_C_EP);
        assertThat(testEmpresa.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testEmpresa.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testEmpresa.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testEmpresa.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmpresa.getCredito()).isEqualTo(UPDATED_CREDITO);
        assertThat(testEmpresa.getInativo()).isEqualTo(UPDATED_INATIVO);
        assertThat(testEmpresa.getGenEmail()).isEqualTo(UPDATED_GEN_EMAIL);
    }

    @Test
    @Transactional
    void fullUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .codigoBrad(UPDATED_CODIGO_BRAD)
            .empresa(UPDATED_EMPRESA)
            .nome(UPDATED_NOME)
            .funcao(UPDATED_FUNCAO)
            .cNPJ(UPDATED_C_NPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .obs(UPDATED_OBS)
            .rua(UPDATED_RUA)
            .numero(UPDATED_NUMERO)
            .bairro(UPDATED_BAIRRO)
            .complemento(UPDATED_COMPLEMENTO)
            .cEP(UPDATED_C_EP)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .email(UPDATED_EMAIL)
            .credito(UPDATED_CREDITO)
            .inativo(UPDATED_INATIVO)
            .genEmail(UPDATED_GEN_EMAIL);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getNomeEmpresa()).isEqualTo(UPDATED_NOME_EMPRESA);
        assertThat(testEmpresa.getCodigoBrad()).isEqualTo(UPDATED_CODIGO_BRAD);
        assertThat(testEmpresa.getEmpresa()).isEqualTo(UPDATED_EMPRESA);
        assertThat(testEmpresa.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmpresa.getFuncao()).isEqualTo(UPDATED_FUNCAO);
        assertThat(testEmpresa.getcNPJ()).isEqualTo(UPDATED_C_NPJ);
        assertThat(testEmpresa.getInscricaoEstadual()).isEqualTo(UPDATED_INSCRICAO_ESTADUAL);
        assertThat(testEmpresa.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testEmpresa.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testEmpresa.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEmpresa.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEmpresa.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEmpresa.getcEP()).isEqualTo(UPDATED_C_EP);
        assertThat(testEmpresa.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEmpresa.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testEmpresa.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testEmpresa.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmpresa.getCredito()).isEqualTo(UPDATED_CREDITO);
        assertThat(testEmpresa.getInativo()).isEqualTo(UPDATED_INATIVO);
        assertThat(testEmpresa.getGenEmail()).isEqualTo(UPDATED_GEN_EMAIL);
    }

    @Test
    @Transactional
    void patchNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Delete the empresa
        restEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, empresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
