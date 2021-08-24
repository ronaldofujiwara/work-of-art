package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.AreaDepto;
import br.com.nhw.std.artes.domain.Artista;
import br.com.nhw.std.artes.domain.Cidade;
import br.com.nhw.std.artes.domain.Contato;
import br.com.nhw.std.artes.domain.Obra;
import br.com.nhw.std.artes.domain.Seguro;
import br.com.nhw.std.artes.repository.ContatoRepository;
import br.com.nhw.std.artes.service.criteria.ContatoCriteria;
import br.com.nhw.std.artes.service.dto.ContatoDTO;
import br.com.nhw.std.artes.service.mapper.ContatoMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ContatoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContatoResourceIT {

    private static final String DEFAULT_NOME_COMP = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COMP = "BBBBBBBBBB";

    private static final String DEFAULT_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCAO = "AAAAAAAAAA";
    private static final String UPDATED_FUNCAO = "BBBBBBBBBB";

    private static final String DEFAULT_RG = "AAAAAAAAAA";
    private static final String UPDATED_RG = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_INFO_CONTATO = "AAAAAAAAAA";
    private static final String UPDATED_INFO_CONTATO = "BBBBBBBBBB";

    private static final String DEFAULT_END_RUA = "AAAAAAAAAA";
    private static final String UPDATED_END_RUA = "BBBBBBBBBB";

    private static final String DEFAULT_END_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_END_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_END_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_END_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_END_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_END_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_END_CEP = "AAAAAAAAAA";
    private static final String UPDATED_END_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACOES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACOES = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_ATUALIZACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATUALIZACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/contatoes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ContatoMapper contatoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContatoMockMvc;

    private Contato contato;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contato createEntity(EntityManager em) {
        Contato contato = new Contato()
            .nomeComp(DEFAULT_NOME_COMP)
            .empresa(DEFAULT_EMPRESA)
            .funcao(DEFAULT_FUNCAO)
            .rg(DEFAULT_RG)
            .cpf(DEFAULT_CPF)
            .infoContato(DEFAULT_INFO_CONTATO)
            .endRua(DEFAULT_END_RUA)
            .endNumero(DEFAULT_END_NUMERO)
            .endBairro(DEFAULT_END_BAIRRO)
            .endComplemento(DEFAULT_END_COMPLEMENTO)
            .endCep(DEFAULT_END_CEP)
            .telefone(DEFAULT_TELEFONE)
            .fax(DEFAULT_FAX)
            .celular(DEFAULT_CELULAR)
            .email(DEFAULT_EMAIL)
            .site(DEFAULT_SITE)
            .observacoes(DEFAULT_OBSERVACOES)
            .dataAtualizacao(DEFAULT_DATA_ATUALIZACAO)
            .inativo(DEFAULT_INATIVO);
        return contato;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contato createUpdatedEntity(EntityManager em) {
        Contato contato = new Contato()
            .nomeComp(UPDATED_NOME_COMP)
            .empresa(UPDATED_EMPRESA)
            .funcao(UPDATED_FUNCAO)
            .rg(UPDATED_RG)
            .cpf(UPDATED_CPF)
            .infoContato(UPDATED_INFO_CONTATO)
            .endRua(UPDATED_END_RUA)
            .endNumero(UPDATED_END_NUMERO)
            .endBairro(UPDATED_END_BAIRRO)
            .endComplemento(UPDATED_END_COMPLEMENTO)
            .endCep(UPDATED_END_CEP)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .email(UPDATED_EMAIL)
            .site(UPDATED_SITE)
            .observacoes(UPDATED_OBSERVACOES)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO)
            .inativo(UPDATED_INATIVO);
        return contato;
    }

    @BeforeEach
    public void initTest() {
        contato = createEntity(em);
    }

    @Test
    @Transactional
    void createContato() throws Exception {
        int databaseSizeBeforeCreate = contatoRepository.findAll().size();
        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);
        restContatoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isCreated());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeCreate + 1);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getNomeComp()).isEqualTo(DEFAULT_NOME_COMP);
        assertThat(testContato.getEmpresa()).isEqualTo(DEFAULT_EMPRESA);
        assertThat(testContato.getFuncao()).isEqualTo(DEFAULT_FUNCAO);
        assertThat(testContato.getRg()).isEqualTo(DEFAULT_RG);
        assertThat(testContato.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testContato.getInfoContato()).isEqualTo(DEFAULT_INFO_CONTATO);
        assertThat(testContato.getEndRua()).isEqualTo(DEFAULT_END_RUA);
        assertThat(testContato.getEndNumero()).isEqualTo(DEFAULT_END_NUMERO);
        assertThat(testContato.getEndBairro()).isEqualTo(DEFAULT_END_BAIRRO);
        assertThat(testContato.getEndComplemento()).isEqualTo(DEFAULT_END_COMPLEMENTO);
        assertThat(testContato.getEndCep()).isEqualTo(DEFAULT_END_CEP);
        assertThat(testContato.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testContato.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testContato.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testContato.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContato.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testContato.getObservacoes()).isEqualTo(DEFAULT_OBSERVACOES);
        assertThat(testContato.getDataAtualizacao()).isEqualTo(DEFAULT_DATA_ATUALIZACAO);
        assertThat(testContato.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createContatoWithExistingId() throws Exception {
        // Create the Contato with an existing ID
        contato.setId(1L);
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        int databaseSizeBeforeCreate = contatoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContatoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeCompIsRequired() throws Exception {
        int databaseSizeBeforeTest = contatoRepository.findAll().size();
        // set the field null
        contato.setNomeComp(null);

        // Create the Contato, which fails.
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        restContatoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isBadRequest());

        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContatoes() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList
        restContatoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contato.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeComp").value(hasItem(DEFAULT_NOME_COMP)))
            .andExpect(jsonPath("$.[*].empresa").value(hasItem(DEFAULT_EMPRESA)))
            .andExpect(jsonPath("$.[*].funcao").value(hasItem(DEFAULT_FUNCAO)))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].infoContato").value(hasItem(DEFAULT_INFO_CONTATO)))
            .andExpect(jsonPath("$.[*].endRua").value(hasItem(DEFAULT_END_RUA)))
            .andExpect(jsonPath("$.[*].endNumero").value(hasItem(DEFAULT_END_NUMERO)))
            .andExpect(jsonPath("$.[*].endBairro").value(hasItem(DEFAULT_END_BAIRRO)))
            .andExpect(jsonPath("$.[*].endComplemento").value(hasItem(DEFAULT_END_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].endCep").value(hasItem(DEFAULT_END_CEP)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(DEFAULT_DATA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get the contato
        restContatoMockMvc
            .perform(get(ENTITY_API_URL_ID, contato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contato.getId().intValue()))
            .andExpect(jsonPath("$.nomeComp").value(DEFAULT_NOME_COMP))
            .andExpect(jsonPath("$.empresa").value(DEFAULT_EMPRESA))
            .andExpect(jsonPath("$.funcao").value(DEFAULT_FUNCAO))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.infoContato").value(DEFAULT_INFO_CONTATO))
            .andExpect(jsonPath("$.endRua").value(DEFAULT_END_RUA))
            .andExpect(jsonPath("$.endNumero").value(DEFAULT_END_NUMERO))
            .andExpect(jsonPath("$.endBairro").value(DEFAULT_END_BAIRRO))
            .andExpect(jsonPath("$.endComplemento").value(DEFAULT_END_COMPLEMENTO))
            .andExpect(jsonPath("$.endCep").value(DEFAULT_END_CEP))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE))
            .andExpect(jsonPath("$.observacoes").value(DEFAULT_OBSERVACOES))
            .andExpect(jsonPath("$.dataAtualizacao").value(DEFAULT_DATA_ATUALIZACAO.toString()))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getContatoesByIdFiltering() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        Long id = contato.getId();

        defaultContatoShouldBeFound("id.equals=" + id);
        defaultContatoShouldNotBeFound("id.notEquals=" + id);

        defaultContatoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContatoShouldNotBeFound("id.greaterThan=" + id);

        defaultContatoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContatoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContatoesByNomeCompIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nomeComp equals to DEFAULT_NOME_COMP
        defaultContatoShouldBeFound("nomeComp.equals=" + DEFAULT_NOME_COMP);

        // Get all the contatoList where nomeComp equals to UPDATED_NOME_COMP
        defaultContatoShouldNotBeFound("nomeComp.equals=" + UPDATED_NOME_COMP);
    }

    @Test
    @Transactional
    void getAllContatoesByNomeCompIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nomeComp not equals to DEFAULT_NOME_COMP
        defaultContatoShouldNotBeFound("nomeComp.notEquals=" + DEFAULT_NOME_COMP);

        // Get all the contatoList where nomeComp not equals to UPDATED_NOME_COMP
        defaultContatoShouldBeFound("nomeComp.notEquals=" + UPDATED_NOME_COMP);
    }

    @Test
    @Transactional
    void getAllContatoesByNomeCompIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nomeComp in DEFAULT_NOME_COMP or UPDATED_NOME_COMP
        defaultContatoShouldBeFound("nomeComp.in=" + DEFAULT_NOME_COMP + "," + UPDATED_NOME_COMP);

        // Get all the contatoList where nomeComp equals to UPDATED_NOME_COMP
        defaultContatoShouldNotBeFound("nomeComp.in=" + UPDATED_NOME_COMP);
    }

    @Test
    @Transactional
    void getAllContatoesByNomeCompIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nomeComp is not null
        defaultContatoShouldBeFound("nomeComp.specified=true");

        // Get all the contatoList where nomeComp is null
        defaultContatoShouldNotBeFound("nomeComp.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByNomeCompContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nomeComp contains DEFAULT_NOME_COMP
        defaultContatoShouldBeFound("nomeComp.contains=" + DEFAULT_NOME_COMP);

        // Get all the contatoList where nomeComp contains UPDATED_NOME_COMP
        defaultContatoShouldNotBeFound("nomeComp.contains=" + UPDATED_NOME_COMP);
    }

    @Test
    @Transactional
    void getAllContatoesByNomeCompNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where nomeComp does not contain DEFAULT_NOME_COMP
        defaultContatoShouldNotBeFound("nomeComp.doesNotContain=" + DEFAULT_NOME_COMP);

        // Get all the contatoList where nomeComp does not contain UPDATED_NOME_COMP
        defaultContatoShouldBeFound("nomeComp.doesNotContain=" + UPDATED_NOME_COMP);
    }

    @Test
    @Transactional
    void getAllContatoesByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where empresa equals to DEFAULT_EMPRESA
        defaultContatoShouldBeFound("empresa.equals=" + DEFAULT_EMPRESA);

        // Get all the contatoList where empresa equals to UPDATED_EMPRESA
        defaultContatoShouldNotBeFound("empresa.equals=" + UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    void getAllContatoesByEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where empresa not equals to DEFAULT_EMPRESA
        defaultContatoShouldNotBeFound("empresa.notEquals=" + DEFAULT_EMPRESA);

        // Get all the contatoList where empresa not equals to UPDATED_EMPRESA
        defaultContatoShouldBeFound("empresa.notEquals=" + UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    void getAllContatoesByEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where empresa in DEFAULT_EMPRESA or UPDATED_EMPRESA
        defaultContatoShouldBeFound("empresa.in=" + DEFAULT_EMPRESA + "," + UPDATED_EMPRESA);

        // Get all the contatoList where empresa equals to UPDATED_EMPRESA
        defaultContatoShouldNotBeFound("empresa.in=" + UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    void getAllContatoesByEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where empresa is not null
        defaultContatoShouldBeFound("empresa.specified=true");

        // Get all the contatoList where empresa is null
        defaultContatoShouldNotBeFound("empresa.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByEmpresaContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where empresa contains DEFAULT_EMPRESA
        defaultContatoShouldBeFound("empresa.contains=" + DEFAULT_EMPRESA);

        // Get all the contatoList where empresa contains UPDATED_EMPRESA
        defaultContatoShouldNotBeFound("empresa.contains=" + UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    void getAllContatoesByEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where empresa does not contain DEFAULT_EMPRESA
        defaultContatoShouldNotBeFound("empresa.doesNotContain=" + DEFAULT_EMPRESA);

        // Get all the contatoList where empresa does not contain UPDATED_EMPRESA
        defaultContatoShouldBeFound("empresa.doesNotContain=" + UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    void getAllContatoesByFuncaoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where funcao equals to DEFAULT_FUNCAO
        defaultContatoShouldBeFound("funcao.equals=" + DEFAULT_FUNCAO);

        // Get all the contatoList where funcao equals to UPDATED_FUNCAO
        defaultContatoShouldNotBeFound("funcao.equals=" + UPDATED_FUNCAO);
    }

    @Test
    @Transactional
    void getAllContatoesByFuncaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where funcao not equals to DEFAULT_FUNCAO
        defaultContatoShouldNotBeFound("funcao.notEquals=" + DEFAULT_FUNCAO);

        // Get all the contatoList where funcao not equals to UPDATED_FUNCAO
        defaultContatoShouldBeFound("funcao.notEquals=" + UPDATED_FUNCAO);
    }

    @Test
    @Transactional
    void getAllContatoesByFuncaoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where funcao in DEFAULT_FUNCAO or UPDATED_FUNCAO
        defaultContatoShouldBeFound("funcao.in=" + DEFAULT_FUNCAO + "," + UPDATED_FUNCAO);

        // Get all the contatoList where funcao equals to UPDATED_FUNCAO
        defaultContatoShouldNotBeFound("funcao.in=" + UPDATED_FUNCAO);
    }

    @Test
    @Transactional
    void getAllContatoesByFuncaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where funcao is not null
        defaultContatoShouldBeFound("funcao.specified=true");

        // Get all the contatoList where funcao is null
        defaultContatoShouldNotBeFound("funcao.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByFuncaoContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where funcao contains DEFAULT_FUNCAO
        defaultContatoShouldBeFound("funcao.contains=" + DEFAULT_FUNCAO);

        // Get all the contatoList where funcao contains UPDATED_FUNCAO
        defaultContatoShouldNotBeFound("funcao.contains=" + UPDATED_FUNCAO);
    }

    @Test
    @Transactional
    void getAllContatoesByFuncaoNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where funcao does not contain DEFAULT_FUNCAO
        defaultContatoShouldNotBeFound("funcao.doesNotContain=" + DEFAULT_FUNCAO);

        // Get all the contatoList where funcao does not contain UPDATED_FUNCAO
        defaultContatoShouldBeFound("funcao.doesNotContain=" + UPDATED_FUNCAO);
    }

    @Test
    @Transactional
    void getAllContatoesByRgIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where rg equals to DEFAULT_RG
        defaultContatoShouldBeFound("rg.equals=" + DEFAULT_RG);

        // Get all the contatoList where rg equals to UPDATED_RG
        defaultContatoShouldNotBeFound("rg.equals=" + UPDATED_RG);
    }

    @Test
    @Transactional
    void getAllContatoesByRgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where rg not equals to DEFAULT_RG
        defaultContatoShouldNotBeFound("rg.notEquals=" + DEFAULT_RG);

        // Get all the contatoList where rg not equals to UPDATED_RG
        defaultContatoShouldBeFound("rg.notEquals=" + UPDATED_RG);
    }

    @Test
    @Transactional
    void getAllContatoesByRgIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where rg in DEFAULT_RG or UPDATED_RG
        defaultContatoShouldBeFound("rg.in=" + DEFAULT_RG + "," + UPDATED_RG);

        // Get all the contatoList where rg equals to UPDATED_RG
        defaultContatoShouldNotBeFound("rg.in=" + UPDATED_RG);
    }

    @Test
    @Transactional
    void getAllContatoesByRgIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where rg is not null
        defaultContatoShouldBeFound("rg.specified=true");

        // Get all the contatoList where rg is null
        defaultContatoShouldNotBeFound("rg.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByRgContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where rg contains DEFAULT_RG
        defaultContatoShouldBeFound("rg.contains=" + DEFAULT_RG);

        // Get all the contatoList where rg contains UPDATED_RG
        defaultContatoShouldNotBeFound("rg.contains=" + UPDATED_RG);
    }

    @Test
    @Transactional
    void getAllContatoesByRgNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where rg does not contain DEFAULT_RG
        defaultContatoShouldNotBeFound("rg.doesNotContain=" + DEFAULT_RG);

        // Get all the contatoList where rg does not contain UPDATED_RG
        defaultContatoShouldBeFound("rg.doesNotContain=" + UPDATED_RG);
    }

    @Test
    @Transactional
    void getAllContatoesByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where cpf equals to DEFAULT_CPF
        defaultContatoShouldBeFound("cpf.equals=" + DEFAULT_CPF);

        // Get all the contatoList where cpf equals to UPDATED_CPF
        defaultContatoShouldNotBeFound("cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllContatoesByCpfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where cpf not equals to DEFAULT_CPF
        defaultContatoShouldNotBeFound("cpf.notEquals=" + DEFAULT_CPF);

        // Get all the contatoList where cpf not equals to UPDATED_CPF
        defaultContatoShouldBeFound("cpf.notEquals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllContatoesByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where cpf in DEFAULT_CPF or UPDATED_CPF
        defaultContatoShouldBeFound("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF);

        // Get all the contatoList where cpf equals to UPDATED_CPF
        defaultContatoShouldNotBeFound("cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllContatoesByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where cpf is not null
        defaultContatoShouldBeFound("cpf.specified=true");

        // Get all the contatoList where cpf is null
        defaultContatoShouldNotBeFound("cpf.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByCpfContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where cpf contains DEFAULT_CPF
        defaultContatoShouldBeFound("cpf.contains=" + DEFAULT_CPF);

        // Get all the contatoList where cpf contains UPDATED_CPF
        defaultContatoShouldNotBeFound("cpf.contains=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllContatoesByCpfNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where cpf does not contain DEFAULT_CPF
        defaultContatoShouldNotBeFound("cpf.doesNotContain=" + DEFAULT_CPF);

        // Get all the contatoList where cpf does not contain UPDATED_CPF
        defaultContatoShouldBeFound("cpf.doesNotContain=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllContatoesByInfoContatoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where infoContato equals to DEFAULT_INFO_CONTATO
        defaultContatoShouldBeFound("infoContato.equals=" + DEFAULT_INFO_CONTATO);

        // Get all the contatoList where infoContato equals to UPDATED_INFO_CONTATO
        defaultContatoShouldNotBeFound("infoContato.equals=" + UPDATED_INFO_CONTATO);
    }

    @Test
    @Transactional
    void getAllContatoesByInfoContatoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where infoContato not equals to DEFAULT_INFO_CONTATO
        defaultContatoShouldNotBeFound("infoContato.notEquals=" + DEFAULT_INFO_CONTATO);

        // Get all the contatoList where infoContato not equals to UPDATED_INFO_CONTATO
        defaultContatoShouldBeFound("infoContato.notEquals=" + UPDATED_INFO_CONTATO);
    }

    @Test
    @Transactional
    void getAllContatoesByInfoContatoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where infoContato in DEFAULT_INFO_CONTATO or UPDATED_INFO_CONTATO
        defaultContatoShouldBeFound("infoContato.in=" + DEFAULT_INFO_CONTATO + "," + UPDATED_INFO_CONTATO);

        // Get all the contatoList where infoContato equals to UPDATED_INFO_CONTATO
        defaultContatoShouldNotBeFound("infoContato.in=" + UPDATED_INFO_CONTATO);
    }

    @Test
    @Transactional
    void getAllContatoesByInfoContatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where infoContato is not null
        defaultContatoShouldBeFound("infoContato.specified=true");

        // Get all the contatoList where infoContato is null
        defaultContatoShouldNotBeFound("infoContato.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByInfoContatoContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where infoContato contains DEFAULT_INFO_CONTATO
        defaultContatoShouldBeFound("infoContato.contains=" + DEFAULT_INFO_CONTATO);

        // Get all the contatoList where infoContato contains UPDATED_INFO_CONTATO
        defaultContatoShouldNotBeFound("infoContato.contains=" + UPDATED_INFO_CONTATO);
    }

    @Test
    @Transactional
    void getAllContatoesByInfoContatoNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where infoContato does not contain DEFAULT_INFO_CONTATO
        defaultContatoShouldNotBeFound("infoContato.doesNotContain=" + DEFAULT_INFO_CONTATO);

        // Get all the contatoList where infoContato does not contain UPDATED_INFO_CONTATO
        defaultContatoShouldBeFound("infoContato.doesNotContain=" + UPDATED_INFO_CONTATO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndRuaIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endRua equals to DEFAULT_END_RUA
        defaultContatoShouldBeFound("endRua.equals=" + DEFAULT_END_RUA);

        // Get all the contatoList where endRua equals to UPDATED_END_RUA
        defaultContatoShouldNotBeFound("endRua.equals=" + UPDATED_END_RUA);
    }

    @Test
    @Transactional
    void getAllContatoesByEndRuaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endRua not equals to DEFAULT_END_RUA
        defaultContatoShouldNotBeFound("endRua.notEquals=" + DEFAULT_END_RUA);

        // Get all the contatoList where endRua not equals to UPDATED_END_RUA
        defaultContatoShouldBeFound("endRua.notEquals=" + UPDATED_END_RUA);
    }

    @Test
    @Transactional
    void getAllContatoesByEndRuaIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endRua in DEFAULT_END_RUA or UPDATED_END_RUA
        defaultContatoShouldBeFound("endRua.in=" + DEFAULT_END_RUA + "," + UPDATED_END_RUA);

        // Get all the contatoList where endRua equals to UPDATED_END_RUA
        defaultContatoShouldNotBeFound("endRua.in=" + UPDATED_END_RUA);
    }

    @Test
    @Transactional
    void getAllContatoesByEndRuaIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endRua is not null
        defaultContatoShouldBeFound("endRua.specified=true");

        // Get all the contatoList where endRua is null
        defaultContatoShouldNotBeFound("endRua.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByEndRuaContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endRua contains DEFAULT_END_RUA
        defaultContatoShouldBeFound("endRua.contains=" + DEFAULT_END_RUA);

        // Get all the contatoList where endRua contains UPDATED_END_RUA
        defaultContatoShouldNotBeFound("endRua.contains=" + UPDATED_END_RUA);
    }

    @Test
    @Transactional
    void getAllContatoesByEndRuaNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endRua does not contain DEFAULT_END_RUA
        defaultContatoShouldNotBeFound("endRua.doesNotContain=" + DEFAULT_END_RUA);

        // Get all the contatoList where endRua does not contain UPDATED_END_RUA
        defaultContatoShouldBeFound("endRua.doesNotContain=" + UPDATED_END_RUA);
    }

    @Test
    @Transactional
    void getAllContatoesByEndNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endNumero equals to DEFAULT_END_NUMERO
        defaultContatoShouldBeFound("endNumero.equals=" + DEFAULT_END_NUMERO);

        // Get all the contatoList where endNumero equals to UPDATED_END_NUMERO
        defaultContatoShouldNotBeFound("endNumero.equals=" + UPDATED_END_NUMERO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndNumeroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endNumero not equals to DEFAULT_END_NUMERO
        defaultContatoShouldNotBeFound("endNumero.notEquals=" + DEFAULT_END_NUMERO);

        // Get all the contatoList where endNumero not equals to UPDATED_END_NUMERO
        defaultContatoShouldBeFound("endNumero.notEquals=" + UPDATED_END_NUMERO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endNumero in DEFAULT_END_NUMERO or UPDATED_END_NUMERO
        defaultContatoShouldBeFound("endNumero.in=" + DEFAULT_END_NUMERO + "," + UPDATED_END_NUMERO);

        // Get all the contatoList where endNumero equals to UPDATED_END_NUMERO
        defaultContatoShouldNotBeFound("endNumero.in=" + UPDATED_END_NUMERO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endNumero is not null
        defaultContatoShouldBeFound("endNumero.specified=true");

        // Get all the contatoList where endNumero is null
        defaultContatoShouldNotBeFound("endNumero.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByEndNumeroContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endNumero contains DEFAULT_END_NUMERO
        defaultContatoShouldBeFound("endNumero.contains=" + DEFAULT_END_NUMERO);

        // Get all the contatoList where endNumero contains UPDATED_END_NUMERO
        defaultContatoShouldNotBeFound("endNumero.contains=" + UPDATED_END_NUMERO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endNumero does not contain DEFAULT_END_NUMERO
        defaultContatoShouldNotBeFound("endNumero.doesNotContain=" + DEFAULT_END_NUMERO);

        // Get all the contatoList where endNumero does not contain UPDATED_END_NUMERO
        defaultContatoShouldBeFound("endNumero.doesNotContain=" + UPDATED_END_NUMERO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndBairroIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endBairro equals to DEFAULT_END_BAIRRO
        defaultContatoShouldBeFound("endBairro.equals=" + DEFAULT_END_BAIRRO);

        // Get all the contatoList where endBairro equals to UPDATED_END_BAIRRO
        defaultContatoShouldNotBeFound("endBairro.equals=" + UPDATED_END_BAIRRO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndBairroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endBairro not equals to DEFAULT_END_BAIRRO
        defaultContatoShouldNotBeFound("endBairro.notEquals=" + DEFAULT_END_BAIRRO);

        // Get all the contatoList where endBairro not equals to UPDATED_END_BAIRRO
        defaultContatoShouldBeFound("endBairro.notEquals=" + UPDATED_END_BAIRRO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndBairroIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endBairro in DEFAULT_END_BAIRRO or UPDATED_END_BAIRRO
        defaultContatoShouldBeFound("endBairro.in=" + DEFAULT_END_BAIRRO + "," + UPDATED_END_BAIRRO);

        // Get all the contatoList where endBairro equals to UPDATED_END_BAIRRO
        defaultContatoShouldNotBeFound("endBairro.in=" + UPDATED_END_BAIRRO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndBairroIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endBairro is not null
        defaultContatoShouldBeFound("endBairro.specified=true");

        // Get all the contatoList where endBairro is null
        defaultContatoShouldNotBeFound("endBairro.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByEndBairroContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endBairro contains DEFAULT_END_BAIRRO
        defaultContatoShouldBeFound("endBairro.contains=" + DEFAULT_END_BAIRRO);

        // Get all the contatoList where endBairro contains UPDATED_END_BAIRRO
        defaultContatoShouldNotBeFound("endBairro.contains=" + UPDATED_END_BAIRRO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndBairroNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endBairro does not contain DEFAULT_END_BAIRRO
        defaultContatoShouldNotBeFound("endBairro.doesNotContain=" + DEFAULT_END_BAIRRO);

        // Get all the contatoList where endBairro does not contain UPDATED_END_BAIRRO
        defaultContatoShouldBeFound("endBairro.doesNotContain=" + UPDATED_END_BAIRRO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndComplementoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endComplemento equals to DEFAULT_END_COMPLEMENTO
        defaultContatoShouldBeFound("endComplemento.equals=" + DEFAULT_END_COMPLEMENTO);

        // Get all the contatoList where endComplemento equals to UPDATED_END_COMPLEMENTO
        defaultContatoShouldNotBeFound("endComplemento.equals=" + UPDATED_END_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndComplementoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endComplemento not equals to DEFAULT_END_COMPLEMENTO
        defaultContatoShouldNotBeFound("endComplemento.notEquals=" + DEFAULT_END_COMPLEMENTO);

        // Get all the contatoList where endComplemento not equals to UPDATED_END_COMPLEMENTO
        defaultContatoShouldBeFound("endComplemento.notEquals=" + UPDATED_END_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndComplementoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endComplemento in DEFAULT_END_COMPLEMENTO or UPDATED_END_COMPLEMENTO
        defaultContatoShouldBeFound("endComplemento.in=" + DEFAULT_END_COMPLEMENTO + "," + UPDATED_END_COMPLEMENTO);

        // Get all the contatoList where endComplemento equals to UPDATED_END_COMPLEMENTO
        defaultContatoShouldNotBeFound("endComplemento.in=" + UPDATED_END_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndComplementoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endComplemento is not null
        defaultContatoShouldBeFound("endComplemento.specified=true");

        // Get all the contatoList where endComplemento is null
        defaultContatoShouldNotBeFound("endComplemento.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByEndComplementoContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endComplemento contains DEFAULT_END_COMPLEMENTO
        defaultContatoShouldBeFound("endComplemento.contains=" + DEFAULT_END_COMPLEMENTO);

        // Get all the contatoList where endComplemento contains UPDATED_END_COMPLEMENTO
        defaultContatoShouldNotBeFound("endComplemento.contains=" + UPDATED_END_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndComplementoNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endComplemento does not contain DEFAULT_END_COMPLEMENTO
        defaultContatoShouldNotBeFound("endComplemento.doesNotContain=" + DEFAULT_END_COMPLEMENTO);

        // Get all the contatoList where endComplemento does not contain UPDATED_END_COMPLEMENTO
        defaultContatoShouldBeFound("endComplemento.doesNotContain=" + UPDATED_END_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllContatoesByEndCepIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endCep equals to DEFAULT_END_CEP
        defaultContatoShouldBeFound("endCep.equals=" + DEFAULT_END_CEP);

        // Get all the contatoList where endCep equals to UPDATED_END_CEP
        defaultContatoShouldNotBeFound("endCep.equals=" + UPDATED_END_CEP);
    }

    @Test
    @Transactional
    void getAllContatoesByEndCepIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endCep not equals to DEFAULT_END_CEP
        defaultContatoShouldNotBeFound("endCep.notEquals=" + DEFAULT_END_CEP);

        // Get all the contatoList where endCep not equals to UPDATED_END_CEP
        defaultContatoShouldBeFound("endCep.notEquals=" + UPDATED_END_CEP);
    }

    @Test
    @Transactional
    void getAllContatoesByEndCepIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endCep in DEFAULT_END_CEP or UPDATED_END_CEP
        defaultContatoShouldBeFound("endCep.in=" + DEFAULT_END_CEP + "," + UPDATED_END_CEP);

        // Get all the contatoList where endCep equals to UPDATED_END_CEP
        defaultContatoShouldNotBeFound("endCep.in=" + UPDATED_END_CEP);
    }

    @Test
    @Transactional
    void getAllContatoesByEndCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endCep is not null
        defaultContatoShouldBeFound("endCep.specified=true");

        // Get all the contatoList where endCep is null
        defaultContatoShouldNotBeFound("endCep.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByEndCepContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endCep contains DEFAULT_END_CEP
        defaultContatoShouldBeFound("endCep.contains=" + DEFAULT_END_CEP);

        // Get all the contatoList where endCep contains UPDATED_END_CEP
        defaultContatoShouldNotBeFound("endCep.contains=" + UPDATED_END_CEP);
    }

    @Test
    @Transactional
    void getAllContatoesByEndCepNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where endCep does not contain DEFAULT_END_CEP
        defaultContatoShouldNotBeFound("endCep.doesNotContain=" + DEFAULT_END_CEP);

        // Get all the contatoList where endCep does not contain UPDATED_END_CEP
        defaultContatoShouldBeFound("endCep.doesNotContain=" + UPDATED_END_CEP);
    }

    @Test
    @Transactional
    void getAllContatoesByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where telefone equals to DEFAULT_TELEFONE
        defaultContatoShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the contatoList where telefone equals to UPDATED_TELEFONE
        defaultContatoShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllContatoesByTelefoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where telefone not equals to DEFAULT_TELEFONE
        defaultContatoShouldNotBeFound("telefone.notEquals=" + DEFAULT_TELEFONE);

        // Get all the contatoList where telefone not equals to UPDATED_TELEFONE
        defaultContatoShouldBeFound("telefone.notEquals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllContatoesByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultContatoShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the contatoList where telefone equals to UPDATED_TELEFONE
        defaultContatoShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllContatoesByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where telefone is not null
        defaultContatoShouldBeFound("telefone.specified=true");

        // Get all the contatoList where telefone is null
        defaultContatoShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByTelefoneContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where telefone contains DEFAULT_TELEFONE
        defaultContatoShouldBeFound("telefone.contains=" + DEFAULT_TELEFONE);

        // Get all the contatoList where telefone contains UPDATED_TELEFONE
        defaultContatoShouldNotBeFound("telefone.contains=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllContatoesByTelefoneNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where telefone does not contain DEFAULT_TELEFONE
        defaultContatoShouldNotBeFound("telefone.doesNotContain=" + DEFAULT_TELEFONE);

        // Get all the contatoList where telefone does not contain UPDATED_TELEFONE
        defaultContatoShouldBeFound("telefone.doesNotContain=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void getAllContatoesByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where fax equals to DEFAULT_FAX
        defaultContatoShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the contatoList where fax equals to UPDATED_FAX
        defaultContatoShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllContatoesByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where fax not equals to DEFAULT_FAX
        defaultContatoShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the contatoList where fax not equals to UPDATED_FAX
        defaultContatoShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllContatoesByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultContatoShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the contatoList where fax equals to UPDATED_FAX
        defaultContatoShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllContatoesByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where fax is not null
        defaultContatoShouldBeFound("fax.specified=true");

        // Get all the contatoList where fax is null
        defaultContatoShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByFaxContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where fax contains DEFAULT_FAX
        defaultContatoShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the contatoList where fax contains UPDATED_FAX
        defaultContatoShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllContatoesByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where fax does not contain DEFAULT_FAX
        defaultContatoShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the contatoList where fax does not contain UPDATED_FAX
        defaultContatoShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllContatoesByCelularIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular equals to DEFAULT_CELULAR
        defaultContatoShouldBeFound("celular.equals=" + DEFAULT_CELULAR);

        // Get all the contatoList where celular equals to UPDATED_CELULAR
        defaultContatoShouldNotBeFound("celular.equals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    void getAllContatoesByCelularIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular not equals to DEFAULT_CELULAR
        defaultContatoShouldNotBeFound("celular.notEquals=" + DEFAULT_CELULAR);

        // Get all the contatoList where celular not equals to UPDATED_CELULAR
        defaultContatoShouldBeFound("celular.notEquals=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    void getAllContatoesByCelularIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular in DEFAULT_CELULAR or UPDATED_CELULAR
        defaultContatoShouldBeFound("celular.in=" + DEFAULT_CELULAR + "," + UPDATED_CELULAR);

        // Get all the contatoList where celular equals to UPDATED_CELULAR
        defaultContatoShouldNotBeFound("celular.in=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    void getAllContatoesByCelularIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular is not null
        defaultContatoShouldBeFound("celular.specified=true");

        // Get all the contatoList where celular is null
        defaultContatoShouldNotBeFound("celular.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByCelularContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular contains DEFAULT_CELULAR
        defaultContatoShouldBeFound("celular.contains=" + DEFAULT_CELULAR);

        // Get all the contatoList where celular contains UPDATED_CELULAR
        defaultContatoShouldNotBeFound("celular.contains=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    void getAllContatoesByCelularNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where celular does not contain DEFAULT_CELULAR
        defaultContatoShouldNotBeFound("celular.doesNotContain=" + DEFAULT_CELULAR);

        // Get all the contatoList where celular does not contain UPDATED_CELULAR
        defaultContatoShouldBeFound("celular.doesNotContain=" + UPDATED_CELULAR);
    }

    @Test
    @Transactional
    void getAllContatoesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email equals to DEFAULT_EMAIL
        defaultContatoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contatoList where email equals to UPDATED_EMAIL
        defaultContatoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContatoesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email not equals to DEFAULT_EMAIL
        defaultContatoShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the contatoList where email not equals to UPDATED_EMAIL
        defaultContatoShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContatoesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContatoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contatoList where email equals to UPDATED_EMAIL
        defaultContatoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContatoesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email is not null
        defaultContatoShouldBeFound("email.specified=true");

        // Get all the contatoList where email is null
        defaultContatoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByEmailContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email contains DEFAULT_EMAIL
        defaultContatoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the contatoList where email contains UPDATED_EMAIL
        defaultContatoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContatoesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where email does not contain DEFAULT_EMAIL
        defaultContatoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the contatoList where email does not contain UPDATED_EMAIL
        defaultContatoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContatoesBySiteIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where site equals to DEFAULT_SITE
        defaultContatoShouldBeFound("site.equals=" + DEFAULT_SITE);

        // Get all the contatoList where site equals to UPDATED_SITE
        defaultContatoShouldNotBeFound("site.equals=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    void getAllContatoesBySiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where site not equals to DEFAULT_SITE
        defaultContatoShouldNotBeFound("site.notEquals=" + DEFAULT_SITE);

        // Get all the contatoList where site not equals to UPDATED_SITE
        defaultContatoShouldBeFound("site.notEquals=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    void getAllContatoesBySiteIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where site in DEFAULT_SITE or UPDATED_SITE
        defaultContatoShouldBeFound("site.in=" + DEFAULT_SITE + "," + UPDATED_SITE);

        // Get all the contatoList where site equals to UPDATED_SITE
        defaultContatoShouldNotBeFound("site.in=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    void getAllContatoesBySiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where site is not null
        defaultContatoShouldBeFound("site.specified=true");

        // Get all the contatoList where site is null
        defaultContatoShouldNotBeFound("site.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesBySiteContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where site contains DEFAULT_SITE
        defaultContatoShouldBeFound("site.contains=" + DEFAULT_SITE);

        // Get all the contatoList where site contains UPDATED_SITE
        defaultContatoShouldNotBeFound("site.contains=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    void getAllContatoesBySiteNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where site does not contain DEFAULT_SITE
        defaultContatoShouldNotBeFound("site.doesNotContain=" + DEFAULT_SITE);

        // Get all the contatoList where site does not contain UPDATED_SITE
        defaultContatoShouldBeFound("site.doesNotContain=" + UPDATED_SITE);
    }

    @Test
    @Transactional
    void getAllContatoesByObservacoesIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where observacoes equals to DEFAULT_OBSERVACOES
        defaultContatoShouldBeFound("observacoes.equals=" + DEFAULT_OBSERVACOES);

        // Get all the contatoList where observacoes equals to UPDATED_OBSERVACOES
        defaultContatoShouldNotBeFound("observacoes.equals=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllContatoesByObservacoesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where observacoes not equals to DEFAULT_OBSERVACOES
        defaultContatoShouldNotBeFound("observacoes.notEquals=" + DEFAULT_OBSERVACOES);

        // Get all the contatoList where observacoes not equals to UPDATED_OBSERVACOES
        defaultContatoShouldBeFound("observacoes.notEquals=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllContatoesByObservacoesIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where observacoes in DEFAULT_OBSERVACOES or UPDATED_OBSERVACOES
        defaultContatoShouldBeFound("observacoes.in=" + DEFAULT_OBSERVACOES + "," + UPDATED_OBSERVACOES);

        // Get all the contatoList where observacoes equals to UPDATED_OBSERVACOES
        defaultContatoShouldNotBeFound("observacoes.in=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllContatoesByObservacoesIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where observacoes is not null
        defaultContatoShouldBeFound("observacoes.specified=true");

        // Get all the contatoList where observacoes is null
        defaultContatoShouldNotBeFound("observacoes.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByObservacoesContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where observacoes contains DEFAULT_OBSERVACOES
        defaultContatoShouldBeFound("observacoes.contains=" + DEFAULT_OBSERVACOES);

        // Get all the contatoList where observacoes contains UPDATED_OBSERVACOES
        defaultContatoShouldNotBeFound("observacoes.contains=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllContatoesByObservacoesNotContainsSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where observacoes does not contain DEFAULT_OBSERVACOES
        defaultContatoShouldNotBeFound("observacoes.doesNotContain=" + DEFAULT_OBSERVACOES);

        // Get all the contatoList where observacoes does not contain UPDATED_OBSERVACOES
        defaultContatoShouldBeFound("observacoes.doesNotContain=" + UPDATED_OBSERVACOES);
    }

    @Test
    @Transactional
    void getAllContatoesByDataAtualizacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where dataAtualizacao equals to DEFAULT_DATA_ATUALIZACAO
        defaultContatoShouldBeFound("dataAtualizacao.equals=" + DEFAULT_DATA_ATUALIZACAO);

        // Get all the contatoList where dataAtualizacao equals to UPDATED_DATA_ATUALIZACAO
        defaultContatoShouldNotBeFound("dataAtualizacao.equals=" + UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllContatoesByDataAtualizacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where dataAtualizacao not equals to DEFAULT_DATA_ATUALIZACAO
        defaultContatoShouldNotBeFound("dataAtualizacao.notEquals=" + DEFAULT_DATA_ATUALIZACAO);

        // Get all the contatoList where dataAtualizacao not equals to UPDATED_DATA_ATUALIZACAO
        defaultContatoShouldBeFound("dataAtualizacao.notEquals=" + UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllContatoesByDataAtualizacaoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where dataAtualizacao in DEFAULT_DATA_ATUALIZACAO or UPDATED_DATA_ATUALIZACAO
        defaultContatoShouldBeFound("dataAtualizacao.in=" + DEFAULT_DATA_ATUALIZACAO + "," + UPDATED_DATA_ATUALIZACAO);

        // Get all the contatoList where dataAtualizacao equals to UPDATED_DATA_ATUALIZACAO
        defaultContatoShouldNotBeFound("dataAtualizacao.in=" + UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    void getAllContatoesByDataAtualizacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where dataAtualizacao is not null
        defaultContatoShouldBeFound("dataAtualizacao.specified=true");

        // Get all the contatoList where dataAtualizacao is null
        defaultContatoShouldNotBeFound("dataAtualizacao.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByInativoIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where inativo equals to DEFAULT_INATIVO
        defaultContatoShouldBeFound("inativo.equals=" + DEFAULT_INATIVO);

        // Get all the contatoList where inativo equals to UPDATED_INATIVO
        defaultContatoShouldNotBeFound("inativo.equals=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllContatoesByInativoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where inativo not equals to DEFAULT_INATIVO
        defaultContatoShouldNotBeFound("inativo.notEquals=" + DEFAULT_INATIVO);

        // Get all the contatoList where inativo not equals to UPDATED_INATIVO
        defaultContatoShouldBeFound("inativo.notEquals=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllContatoesByInativoIsInShouldWork() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where inativo in DEFAULT_INATIVO or UPDATED_INATIVO
        defaultContatoShouldBeFound("inativo.in=" + DEFAULT_INATIVO + "," + UPDATED_INATIVO);

        // Get all the contatoList where inativo equals to UPDATED_INATIVO
        defaultContatoShouldNotBeFound("inativo.in=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllContatoesByInativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        // Get all the contatoList where inativo is not null
        defaultContatoShouldBeFound("inativo.specified=true");

        // Get all the contatoList where inativo is null
        defaultContatoShouldNotBeFound("inativo.specified=false");
    }

    @Test
    @Transactional
    void getAllContatoesByObraIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);
        Obra obra = ObraResourceIT.createEntity(em);
        em.persist(obra);
        em.flush();
        contato.addObra(obra);
        contatoRepository.saveAndFlush(contato);
        Long obraId = obra.getId();

        // Get all the contatoList where obra equals to obraId
        defaultContatoShouldBeFound("obraId.equals=" + obraId);

        // Get all the contatoList where obra equals to (obraId + 1)
        defaultContatoShouldNotBeFound("obraId.equals=" + (obraId + 1));
    }

    @Test
    @Transactional
    void getAllContatoesBySeguroSegIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);
        Seguro seguroSeg = SeguroResourceIT.createEntity(em);
        em.persist(seguroSeg);
        em.flush();
        contato.addSeguroSeg(seguroSeg);
        contatoRepository.saveAndFlush(contato);
        Long seguroSegId = seguroSeg.getId();

        // Get all the contatoList where seguroSeg equals to seguroSegId
        defaultContatoShouldBeFound("seguroSegId.equals=" + seguroSegId);

        // Get all the contatoList where seguroSeg equals to (seguroSegId + 1)
        defaultContatoShouldNotBeFound("seguroSegId.equals=" + (seguroSegId + 1));
    }

    @Test
    @Transactional
    void getAllContatoesBySeguroCorIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);
        Seguro seguroCor = SeguroResourceIT.createEntity(em);
        em.persist(seguroCor);
        em.flush();
        contato.addSeguroCor(seguroCor);
        contatoRepository.saveAndFlush(contato);
        Long seguroCorId = seguroCor.getId();

        // Get all the contatoList where seguroCor equals to seguroCorId
        defaultContatoShouldBeFound("seguroCorId.equals=" + seguroCorId);

        // Get all the contatoList where seguroCor equals to (seguroCorId + 1)
        defaultContatoShouldNotBeFound("seguroCorId.equals=" + (seguroCorId + 1));
    }

    @Test
    @Transactional
    void getAllContatoesByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);
        AreaDepto area = AreaDeptoResourceIT.createEntity(em);
        em.persist(area);
        em.flush();
        contato.setArea(area);
        contatoRepository.saveAndFlush(contato);
        Long areaId = area.getId();

        // Get all the contatoList where area equals to areaId
        defaultContatoShouldBeFound("areaId.equals=" + areaId);

        // Get all the contatoList where area equals to (areaId + 1)
        defaultContatoShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }

    @Test
    @Transactional
    void getAllContatoesByCidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);
        Cidade cidade = CidadeResourceIT.createEntity(em);
        em.persist(cidade);
        em.flush();
        contato.setCidade(cidade);
        contatoRepository.saveAndFlush(contato);
        Long cidadeId = cidade.getId();

        // Get all the contatoList where cidade equals to cidadeId
        defaultContatoShouldBeFound("cidadeId.equals=" + cidadeId);

        // Get all the contatoList where cidade equals to (cidadeId + 1)
        defaultContatoShouldNotBeFound("cidadeId.equals=" + (cidadeId + 1));
    }

    @Test
    @Transactional
    void getAllContatoesByArtistaIsEqualToSomething() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);
        Artista artista = ArtistaResourceIT.createEntity(em);
        em.persist(artista);
        em.flush();
        contato.addArtista(artista);
        contatoRepository.saveAndFlush(contato);
        Long artistaId = artista.getId();

        // Get all the contatoList where artista equals to artistaId
        defaultContatoShouldBeFound("artistaId.equals=" + artistaId);

        // Get all the contatoList where artista equals to (artistaId + 1)
        defaultContatoShouldNotBeFound("artistaId.equals=" + (artistaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContatoShouldBeFound(String filter) throws Exception {
        restContatoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contato.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeComp").value(hasItem(DEFAULT_NOME_COMP)))
            .andExpect(jsonPath("$.[*].empresa").value(hasItem(DEFAULT_EMPRESA)))
            .andExpect(jsonPath("$.[*].funcao").value(hasItem(DEFAULT_FUNCAO)))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].infoContato").value(hasItem(DEFAULT_INFO_CONTATO)))
            .andExpect(jsonPath("$.[*].endRua").value(hasItem(DEFAULT_END_RUA)))
            .andExpect(jsonPath("$.[*].endNumero").value(hasItem(DEFAULT_END_NUMERO)))
            .andExpect(jsonPath("$.[*].endBairro").value(hasItem(DEFAULT_END_BAIRRO)))
            .andExpect(jsonPath("$.[*].endComplemento").value(hasItem(DEFAULT_END_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].endCep").value(hasItem(DEFAULT_END_CEP)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].observacoes").value(hasItem(DEFAULT_OBSERVACOES)))
            .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(DEFAULT_DATA_ATUALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));

        // Check, that the count call also returns 1
        restContatoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContatoShouldNotBeFound(String filter) throws Exception {
        restContatoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContatoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContato() throws Exception {
        // Get the contato
        restContatoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();

        // Update the contato
        Contato updatedContato = contatoRepository.findById(contato.getId()).get();
        // Disconnect from session so that the updates on updatedContato are not directly saved in db
        em.detach(updatedContato);
        updatedContato
            .nomeComp(UPDATED_NOME_COMP)
            .empresa(UPDATED_EMPRESA)
            .funcao(UPDATED_FUNCAO)
            .rg(UPDATED_RG)
            .cpf(UPDATED_CPF)
            .infoContato(UPDATED_INFO_CONTATO)
            .endRua(UPDATED_END_RUA)
            .endNumero(UPDATED_END_NUMERO)
            .endBairro(UPDATED_END_BAIRRO)
            .endComplemento(UPDATED_END_COMPLEMENTO)
            .endCep(UPDATED_END_CEP)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .email(UPDATED_EMAIL)
            .site(UPDATED_SITE)
            .observacoes(UPDATED_OBSERVACOES)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO)
            .inativo(UPDATED_INATIVO);
        ContatoDTO contatoDTO = contatoMapper.toDto(updatedContato);

        restContatoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contatoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contatoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getNomeComp()).isEqualTo(UPDATED_NOME_COMP);
        assertThat(testContato.getEmpresa()).isEqualTo(UPDATED_EMPRESA);
        assertThat(testContato.getFuncao()).isEqualTo(UPDATED_FUNCAO);
        assertThat(testContato.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testContato.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testContato.getInfoContato()).isEqualTo(UPDATED_INFO_CONTATO);
        assertThat(testContato.getEndRua()).isEqualTo(UPDATED_END_RUA);
        assertThat(testContato.getEndNumero()).isEqualTo(UPDATED_END_NUMERO);
        assertThat(testContato.getEndBairro()).isEqualTo(UPDATED_END_BAIRRO);
        assertThat(testContato.getEndComplemento()).isEqualTo(UPDATED_END_COMPLEMENTO);
        assertThat(testContato.getEndCep()).isEqualTo(UPDATED_END_CEP);
        assertThat(testContato.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testContato.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testContato.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testContato.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContato.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testContato.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testContato.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
        assertThat(testContato.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingContato() throws Exception {
        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();
        contato.setId(count.incrementAndGet());

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContatoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contatoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contatoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContato() throws Exception {
        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();
        contato.setId(count.incrementAndGet());

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContatoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contatoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContato() throws Exception {
        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();
        contato.setId(count.incrementAndGet());

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContatoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contatoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContatoWithPatch() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();

        // Update the contato using partial update
        Contato partialUpdatedContato = new Contato();
        partialUpdatedContato.setId(contato.getId());

        partialUpdatedContato
            .nomeComp(UPDATED_NOME_COMP)
            .funcao(UPDATED_FUNCAO)
            .rg(UPDATED_RG)
            .cpf(UPDATED_CPF)
            .infoContato(UPDATED_INFO_CONTATO)
            .endRua(UPDATED_END_RUA)
            .endBairro(UPDATED_END_BAIRRO)
            .endComplemento(UPDATED_END_COMPLEMENTO)
            .endCep(UPDATED_END_CEP)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .email(UPDATED_EMAIL)
            .site(UPDATED_SITE)
            .observacoes(UPDATED_OBSERVACOES)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO)
            .inativo(UPDATED_INATIVO);

        restContatoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContato.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContato))
            )
            .andExpect(status().isOk());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getNomeComp()).isEqualTo(UPDATED_NOME_COMP);
        assertThat(testContato.getEmpresa()).isEqualTo(DEFAULT_EMPRESA);
        assertThat(testContato.getFuncao()).isEqualTo(UPDATED_FUNCAO);
        assertThat(testContato.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testContato.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testContato.getInfoContato()).isEqualTo(UPDATED_INFO_CONTATO);
        assertThat(testContato.getEndRua()).isEqualTo(UPDATED_END_RUA);
        assertThat(testContato.getEndNumero()).isEqualTo(DEFAULT_END_NUMERO);
        assertThat(testContato.getEndBairro()).isEqualTo(UPDATED_END_BAIRRO);
        assertThat(testContato.getEndComplemento()).isEqualTo(UPDATED_END_COMPLEMENTO);
        assertThat(testContato.getEndCep()).isEqualTo(UPDATED_END_CEP);
        assertThat(testContato.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testContato.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testContato.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testContato.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContato.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testContato.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testContato.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
        assertThat(testContato.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateContatoWithPatch() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();

        // Update the contato using partial update
        Contato partialUpdatedContato = new Contato();
        partialUpdatedContato.setId(contato.getId());

        partialUpdatedContato
            .nomeComp(UPDATED_NOME_COMP)
            .empresa(UPDATED_EMPRESA)
            .funcao(UPDATED_FUNCAO)
            .rg(UPDATED_RG)
            .cpf(UPDATED_CPF)
            .infoContato(UPDATED_INFO_CONTATO)
            .endRua(UPDATED_END_RUA)
            .endNumero(UPDATED_END_NUMERO)
            .endBairro(UPDATED_END_BAIRRO)
            .endComplemento(UPDATED_END_COMPLEMENTO)
            .endCep(UPDATED_END_CEP)
            .telefone(UPDATED_TELEFONE)
            .fax(UPDATED_FAX)
            .celular(UPDATED_CELULAR)
            .email(UPDATED_EMAIL)
            .site(UPDATED_SITE)
            .observacoes(UPDATED_OBSERVACOES)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO)
            .inativo(UPDATED_INATIVO);

        restContatoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContato.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContato))
            )
            .andExpect(status().isOk());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
        Contato testContato = contatoList.get(contatoList.size() - 1);
        assertThat(testContato.getNomeComp()).isEqualTo(UPDATED_NOME_COMP);
        assertThat(testContato.getEmpresa()).isEqualTo(UPDATED_EMPRESA);
        assertThat(testContato.getFuncao()).isEqualTo(UPDATED_FUNCAO);
        assertThat(testContato.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testContato.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testContato.getInfoContato()).isEqualTo(UPDATED_INFO_CONTATO);
        assertThat(testContato.getEndRua()).isEqualTo(UPDATED_END_RUA);
        assertThat(testContato.getEndNumero()).isEqualTo(UPDATED_END_NUMERO);
        assertThat(testContato.getEndBairro()).isEqualTo(UPDATED_END_BAIRRO);
        assertThat(testContato.getEndComplemento()).isEqualTo(UPDATED_END_COMPLEMENTO);
        assertThat(testContato.getEndCep()).isEqualTo(UPDATED_END_CEP);
        assertThat(testContato.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testContato.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testContato.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testContato.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContato.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testContato.getObservacoes()).isEqualTo(UPDATED_OBSERVACOES);
        assertThat(testContato.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
        assertThat(testContato.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingContato() throws Exception {
        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();
        contato.setId(count.incrementAndGet());

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContatoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contatoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contatoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContato() throws Exception {
        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();
        contato.setId(count.incrementAndGet());

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContatoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contatoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContato() throws Exception {
        int databaseSizeBeforeUpdate = contatoRepository.findAll().size();
        contato.setId(count.incrementAndGet());

        // Create the Contato
        ContatoDTO contatoDTO = contatoMapper.toDto(contato);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContatoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contatoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contato in the database
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContato() throws Exception {
        // Initialize the database
        contatoRepository.saveAndFlush(contato);

        int databaseSizeBeforeDelete = contatoRepository.findAll().size();

        // Delete the contato
        restContatoMockMvc
            .perform(delete(ENTITY_API_URL_ID, contato.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contato> contatoList = contatoRepository.findAll();
        assertThat(contatoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
