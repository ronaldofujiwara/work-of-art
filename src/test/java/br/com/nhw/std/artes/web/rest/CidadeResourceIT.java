package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Artista;
import br.com.nhw.std.artes.domain.Cidade;
import br.com.nhw.std.artes.domain.Contato;
import br.com.nhw.std.artes.domain.Empresa;
import br.com.nhw.std.artes.repository.CidadeRepository;
import br.com.nhw.std.artes.service.criteria.CidadeCriteria;
import br.com.nhw.std.artes.service.dto.CidadeDTO;
import br.com.nhw.std.artes.service.mapper.CidadeMapper;
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
 * Integration tests for the {@link CidadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CidadeResourceIT {

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE_UF_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE_UF_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_PAIS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String ENTITY_API_URL = "/api/cidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCidadeMockMvc;

    private Cidade cidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cidade createEntity(EntityManager em) {
        Cidade cidade = new Cidade()
            .cidade(DEFAULT_CIDADE)
            .estado(DEFAULT_ESTADO)
            .pais(DEFAULT_PAIS)
            .cidadeUFPais(DEFAULT_CIDADE_UF_PAIS)
            .estadoPais(DEFAULT_ESTADO_PAIS)
            .inativo(DEFAULT_INATIVO);
        return cidade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cidade createUpdatedEntity(EntityManager em) {
        Cidade cidade = new Cidade()
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS)
            .cidadeUFPais(UPDATED_CIDADE_UF_PAIS)
            .estadoPais(UPDATED_ESTADO_PAIS)
            .inativo(UPDATED_INATIVO);
        return cidade;
    }

    @BeforeEach
    public void initTest() {
        cidade = createEntity(em);
    }

    @Test
    @Transactional
    void createCidade() throws Exception {
        int databaseSizeBeforeCreate = cidadeRepository.findAll().size();
        // Create the Cidade
        CidadeDTO cidadeDTO = cidadeMapper.toDto(cidade);
        restCidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCidade.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCidade.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testCidade.getCidadeUFPais()).isEqualTo(DEFAULT_CIDADE_UF_PAIS);
        assertThat(testCidade.getEstadoPais()).isEqualTo(DEFAULT_ESTADO_PAIS);
        assertThat(testCidade.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void createCidadeWithExistingId() throws Exception {
        // Create the Cidade with an existing ID
        cidade.setId(1L);
        CidadeDTO cidadeDTO = cidadeMapper.toDto(cidade);

        int databaseSizeBeforeCreate = cidadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cidadeRepository.findAll().size();
        // set the field null
        cidade.setCidade(null);

        // Create the Cidade, which fails.
        CidadeDTO cidadeDTO = cidadeMapper.toDto(cidade);

        restCidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cidadeDTO)))
            .andExpect(status().isBadRequest());

        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCidades() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].cidadeUFPais").value(hasItem(DEFAULT_CIDADE_UF_PAIS)))
            .andExpect(jsonPath("$.[*].estadoPais").value(hasItem(DEFAULT_ESTADO_PAIS)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get the cidade
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, cidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cidade.getId().intValue()))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.cidadeUFPais").value(DEFAULT_CIDADE_UF_PAIS))
            .andExpect(jsonPath("$.estadoPais").value(DEFAULT_ESTADO_PAIS))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getCidadesByIdFiltering() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        Long id = cidade.getId();

        defaultCidadeShouldBeFound("id.equals=" + id);
        defaultCidadeShouldNotBeFound("id.notEquals=" + id);

        defaultCidadeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCidadeShouldNotBeFound("id.greaterThan=" + id);

        defaultCidadeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCidadeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidade equals to DEFAULT_CIDADE
        defaultCidadeShouldBeFound("cidade.equals=" + DEFAULT_CIDADE);

        // Get all the cidadeList where cidade equals to UPDATED_CIDADE
        defaultCidadeShouldNotBeFound("cidade.equals=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidade not equals to DEFAULT_CIDADE
        defaultCidadeShouldNotBeFound("cidade.notEquals=" + DEFAULT_CIDADE);

        // Get all the cidadeList where cidade not equals to UPDATED_CIDADE
        defaultCidadeShouldBeFound("cidade.notEquals=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeIsInShouldWork() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidade in DEFAULT_CIDADE or UPDATED_CIDADE
        defaultCidadeShouldBeFound("cidade.in=" + DEFAULT_CIDADE + "," + UPDATED_CIDADE);

        // Get all the cidadeList where cidade equals to UPDATED_CIDADE
        defaultCidadeShouldNotBeFound("cidade.in=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidade is not null
        defaultCidadeShouldBeFound("cidade.specified=true");

        // Get all the cidadeList where cidade is null
        defaultCidadeShouldNotBeFound("cidade.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidade contains DEFAULT_CIDADE
        defaultCidadeShouldBeFound("cidade.contains=" + DEFAULT_CIDADE);

        // Get all the cidadeList where cidade contains UPDATED_CIDADE
        defaultCidadeShouldNotBeFound("cidade.contains=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeNotContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidade does not contain DEFAULT_CIDADE
        defaultCidadeShouldNotBeFound("cidade.doesNotContain=" + DEFAULT_CIDADE);

        // Get all the cidadeList where cidade does not contain UPDATED_CIDADE
        defaultCidadeShouldBeFound("cidade.doesNotContain=" + UPDATED_CIDADE);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estado equals to DEFAULT_ESTADO
        defaultCidadeShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the cidadeList where estado equals to UPDATED_ESTADO
        defaultCidadeShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estado not equals to DEFAULT_ESTADO
        defaultCidadeShouldNotBeFound("estado.notEquals=" + DEFAULT_ESTADO);

        // Get all the cidadeList where estado not equals to UPDATED_ESTADO
        defaultCidadeShouldBeFound("estado.notEquals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultCidadeShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the cidadeList where estado equals to UPDATED_ESTADO
        defaultCidadeShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estado is not null
        defaultCidadeShouldBeFound("estado.specified=true");

        // Get all the cidadeList where estado is null
        defaultCidadeShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estado contains DEFAULT_ESTADO
        defaultCidadeShouldBeFound("estado.contains=" + DEFAULT_ESTADO);

        // Get all the cidadeList where estado contains UPDATED_ESTADO
        defaultCidadeShouldNotBeFound("estado.contains=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoNotContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estado does not contain DEFAULT_ESTADO
        defaultCidadeShouldNotBeFound("estado.doesNotContain=" + DEFAULT_ESTADO);

        // Get all the cidadeList where estado does not contain UPDATED_ESTADO
        defaultCidadeShouldBeFound("estado.doesNotContain=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void getAllCidadesByPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where pais equals to DEFAULT_PAIS
        defaultCidadeShouldBeFound("pais.equals=" + DEFAULT_PAIS);

        // Get all the cidadeList where pais equals to UPDATED_PAIS
        defaultCidadeShouldNotBeFound("pais.equals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByPaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where pais not equals to DEFAULT_PAIS
        defaultCidadeShouldNotBeFound("pais.notEquals=" + DEFAULT_PAIS);

        // Get all the cidadeList where pais not equals to UPDATED_PAIS
        defaultCidadeShouldBeFound("pais.notEquals=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByPaisIsInShouldWork() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where pais in DEFAULT_PAIS or UPDATED_PAIS
        defaultCidadeShouldBeFound("pais.in=" + DEFAULT_PAIS + "," + UPDATED_PAIS);

        // Get all the cidadeList where pais equals to UPDATED_PAIS
        defaultCidadeShouldNotBeFound("pais.in=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByPaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where pais is not null
        defaultCidadeShouldBeFound("pais.specified=true");

        // Get all the cidadeList where pais is null
        defaultCidadeShouldNotBeFound("pais.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByPaisContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where pais contains DEFAULT_PAIS
        defaultCidadeShouldBeFound("pais.contains=" + DEFAULT_PAIS);

        // Get all the cidadeList where pais contains UPDATED_PAIS
        defaultCidadeShouldNotBeFound("pais.contains=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByPaisNotContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where pais does not contain DEFAULT_PAIS
        defaultCidadeShouldNotBeFound("pais.doesNotContain=" + DEFAULT_PAIS);

        // Get all the cidadeList where pais does not contain UPDATED_PAIS
        defaultCidadeShouldBeFound("pais.doesNotContain=" + UPDATED_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeUFPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidadeUFPais equals to DEFAULT_CIDADE_UF_PAIS
        defaultCidadeShouldBeFound("cidadeUFPais.equals=" + DEFAULT_CIDADE_UF_PAIS);

        // Get all the cidadeList where cidadeUFPais equals to UPDATED_CIDADE_UF_PAIS
        defaultCidadeShouldNotBeFound("cidadeUFPais.equals=" + UPDATED_CIDADE_UF_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeUFPaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidadeUFPais not equals to DEFAULT_CIDADE_UF_PAIS
        defaultCidadeShouldNotBeFound("cidadeUFPais.notEquals=" + DEFAULT_CIDADE_UF_PAIS);

        // Get all the cidadeList where cidadeUFPais not equals to UPDATED_CIDADE_UF_PAIS
        defaultCidadeShouldBeFound("cidadeUFPais.notEquals=" + UPDATED_CIDADE_UF_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeUFPaisIsInShouldWork() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidadeUFPais in DEFAULT_CIDADE_UF_PAIS or UPDATED_CIDADE_UF_PAIS
        defaultCidadeShouldBeFound("cidadeUFPais.in=" + DEFAULT_CIDADE_UF_PAIS + "," + UPDATED_CIDADE_UF_PAIS);

        // Get all the cidadeList where cidadeUFPais equals to UPDATED_CIDADE_UF_PAIS
        defaultCidadeShouldNotBeFound("cidadeUFPais.in=" + UPDATED_CIDADE_UF_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeUFPaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidadeUFPais is not null
        defaultCidadeShouldBeFound("cidadeUFPais.specified=true");

        // Get all the cidadeList where cidadeUFPais is null
        defaultCidadeShouldNotBeFound("cidadeUFPais.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeUFPaisContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidadeUFPais contains DEFAULT_CIDADE_UF_PAIS
        defaultCidadeShouldBeFound("cidadeUFPais.contains=" + DEFAULT_CIDADE_UF_PAIS);

        // Get all the cidadeList where cidadeUFPais contains UPDATED_CIDADE_UF_PAIS
        defaultCidadeShouldNotBeFound("cidadeUFPais.contains=" + UPDATED_CIDADE_UF_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByCidadeUFPaisNotContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where cidadeUFPais does not contain DEFAULT_CIDADE_UF_PAIS
        defaultCidadeShouldNotBeFound("cidadeUFPais.doesNotContain=" + DEFAULT_CIDADE_UF_PAIS);

        // Get all the cidadeList where cidadeUFPais does not contain UPDATED_CIDADE_UF_PAIS
        defaultCidadeShouldBeFound("cidadeUFPais.doesNotContain=" + UPDATED_CIDADE_UF_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoPaisIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estadoPais equals to DEFAULT_ESTADO_PAIS
        defaultCidadeShouldBeFound("estadoPais.equals=" + DEFAULT_ESTADO_PAIS);

        // Get all the cidadeList where estadoPais equals to UPDATED_ESTADO_PAIS
        defaultCidadeShouldNotBeFound("estadoPais.equals=" + UPDATED_ESTADO_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoPaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estadoPais not equals to DEFAULT_ESTADO_PAIS
        defaultCidadeShouldNotBeFound("estadoPais.notEquals=" + DEFAULT_ESTADO_PAIS);

        // Get all the cidadeList where estadoPais not equals to UPDATED_ESTADO_PAIS
        defaultCidadeShouldBeFound("estadoPais.notEquals=" + UPDATED_ESTADO_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoPaisIsInShouldWork() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estadoPais in DEFAULT_ESTADO_PAIS or UPDATED_ESTADO_PAIS
        defaultCidadeShouldBeFound("estadoPais.in=" + DEFAULT_ESTADO_PAIS + "," + UPDATED_ESTADO_PAIS);

        // Get all the cidadeList where estadoPais equals to UPDATED_ESTADO_PAIS
        defaultCidadeShouldNotBeFound("estadoPais.in=" + UPDATED_ESTADO_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoPaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estadoPais is not null
        defaultCidadeShouldBeFound("estadoPais.specified=true");

        // Get all the cidadeList where estadoPais is null
        defaultCidadeShouldNotBeFound("estadoPais.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoPaisContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estadoPais contains DEFAULT_ESTADO_PAIS
        defaultCidadeShouldBeFound("estadoPais.contains=" + DEFAULT_ESTADO_PAIS);

        // Get all the cidadeList where estadoPais contains UPDATED_ESTADO_PAIS
        defaultCidadeShouldNotBeFound("estadoPais.contains=" + UPDATED_ESTADO_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByEstadoPaisNotContainsSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where estadoPais does not contain DEFAULT_ESTADO_PAIS
        defaultCidadeShouldNotBeFound("estadoPais.doesNotContain=" + DEFAULT_ESTADO_PAIS);

        // Get all the cidadeList where estadoPais does not contain UPDATED_ESTADO_PAIS
        defaultCidadeShouldBeFound("estadoPais.doesNotContain=" + UPDATED_ESTADO_PAIS);
    }

    @Test
    @Transactional
    void getAllCidadesByInativoIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where inativo equals to DEFAULT_INATIVO
        defaultCidadeShouldBeFound("inativo.equals=" + DEFAULT_INATIVO);

        // Get all the cidadeList where inativo equals to UPDATED_INATIVO
        defaultCidadeShouldNotBeFound("inativo.equals=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllCidadesByInativoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where inativo not equals to DEFAULT_INATIVO
        defaultCidadeShouldNotBeFound("inativo.notEquals=" + DEFAULT_INATIVO);

        // Get all the cidadeList where inativo not equals to UPDATED_INATIVO
        defaultCidadeShouldBeFound("inativo.notEquals=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllCidadesByInativoIsInShouldWork() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where inativo in DEFAULT_INATIVO or UPDATED_INATIVO
        defaultCidadeShouldBeFound("inativo.in=" + DEFAULT_INATIVO + "," + UPDATED_INATIVO);

        // Get all the cidadeList where inativo equals to UPDATED_INATIVO
        defaultCidadeShouldNotBeFound("inativo.in=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllCidadesByInativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList where inativo is not null
        defaultCidadeShouldBeFound("inativo.specified=true");

        // Get all the cidadeList where inativo is null
        defaultCidadeShouldNotBeFound("inativo.specified=false");
    }

    @Test
    @Transactional
    void getAllCidadesByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        cidade.addEmpresa(empresa);
        cidadeRepository.saveAndFlush(cidade);
        Long empresaId = empresa.getId();

        // Get all the cidadeList where empresa equals to empresaId
        defaultCidadeShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the cidadeList where empresa equals to (empresaId + 1)
        defaultCidadeShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllCidadesByArtistaNascIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);
        Artista artistaNasc = ArtistaResourceIT.createEntity(em);
        em.persist(artistaNasc);
        em.flush();
        cidade.addArtistaNasc(artistaNasc);
        cidadeRepository.saveAndFlush(cidade);
        Long artistaNascId = artistaNasc.getId();

        // Get all the cidadeList where artistaNasc equals to artistaNascId
        defaultCidadeShouldBeFound("artistaNascId.equals=" + artistaNascId);

        // Get all the cidadeList where artistaNasc equals to (artistaNascId + 1)
        defaultCidadeShouldNotBeFound("artistaNascId.equals=" + (artistaNascId + 1));
    }

    @Test
    @Transactional
    void getAllCidadesByArtistaFalescIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);
        Artista artistaFalesc = ArtistaResourceIT.createEntity(em);
        em.persist(artistaFalesc);
        em.flush();
        cidade.addArtistaFalesc(artistaFalesc);
        cidadeRepository.saveAndFlush(cidade);
        Long artistaFalescId = artistaFalesc.getId();

        // Get all the cidadeList where artistaFalesc equals to artistaFalescId
        defaultCidadeShouldBeFound("artistaFalescId.equals=" + artistaFalescId);

        // Get all the cidadeList where artistaFalesc equals to (artistaFalescId + 1)
        defaultCidadeShouldNotBeFound("artistaFalescId.equals=" + (artistaFalescId + 1));
    }

    @Test
    @Transactional
    void getAllCidadesByContatoIsEqualToSomething() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);
        Contato contato = ContatoResourceIT.createEntity(em);
        em.persist(contato);
        em.flush();
        cidade.addContato(contato);
        cidadeRepository.saveAndFlush(cidade);
        Long contatoId = contato.getId();

        // Get all the cidadeList where contato equals to contatoId
        defaultCidadeShouldBeFound("contatoId.equals=" + contatoId);

        // Get all the cidadeList where contato equals to (contatoId + 1)
        defaultCidadeShouldNotBeFound("contatoId.equals=" + (contatoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCidadeShouldBeFound(String filter) throws Exception {
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].cidadeUFPais").value(hasItem(DEFAULT_CIDADE_UF_PAIS)))
            .andExpect(jsonPath("$.[*].estadoPais").value(hasItem(DEFAULT_ESTADO_PAIS)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())));

        // Check, that the count call also returns 1
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCidadeShouldNotBeFound(String filter) throws Exception {
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCidadeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCidade() throws Exception {
        // Get the cidade
        restCidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Update the cidade
        Cidade updatedCidade = cidadeRepository.findById(cidade.getId()).get();
        // Disconnect from session so that the updates on updatedCidade are not directly saved in db
        em.detach(updatedCidade);
        updatedCidade
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS)
            .cidadeUFPais(UPDATED_CIDADE_UF_PAIS)
            .estadoPais(UPDATED_ESTADO_PAIS)
            .inativo(UPDATED_INATIVO);
        CidadeDTO cidadeDTO = cidadeMapper.toDto(updatedCidade);

        restCidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cidadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCidade.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCidade.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testCidade.getCidadeUFPais()).isEqualTo(UPDATED_CIDADE_UF_PAIS);
        assertThat(testCidade.getEstadoPais()).isEqualTo(UPDATED_ESTADO_PAIS);
        assertThat(testCidade.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void putNonExistingCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(count.incrementAndGet());

        // Create the Cidade
        CidadeDTO cidadeDTO = cidadeMapper.toDto(cidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cidadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(count.incrementAndGet());

        // Create the Cidade
        CidadeDTO cidadeDTO = cidadeMapper.toDto(cidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(count.incrementAndGet());

        // Create the Cidade
        CidadeDTO cidadeDTO = cidadeMapper.toDto(cidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cidadeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCidadeWithPatch() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Update the cidade using partial update
        Cidade partialUpdatedCidade = new Cidade();
        partialUpdatedCidade.setId(cidade.getId());

        partialUpdatedCidade.estado(UPDATED_ESTADO).pais(UPDATED_PAIS).cidadeUFPais(UPDATED_CIDADE_UF_PAIS);

        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCidade))
            )
            .andExpect(status().isOk());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCidade.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCidade.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testCidade.getCidadeUFPais()).isEqualTo(UPDATED_CIDADE_UF_PAIS);
        assertThat(testCidade.getEstadoPais()).isEqualTo(DEFAULT_ESTADO_PAIS);
        assertThat(testCidade.getInativo()).isEqualTo(DEFAULT_INATIVO);
    }

    @Test
    @Transactional
    void fullUpdateCidadeWithPatch() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Update the cidade using partial update
        Cidade partialUpdatedCidade = new Cidade();
        partialUpdatedCidade.setId(cidade.getId());

        partialUpdatedCidade
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS)
            .cidadeUFPais(UPDATED_CIDADE_UF_PAIS)
            .estadoPais(UPDATED_ESTADO_PAIS)
            .inativo(UPDATED_INATIVO);

        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCidade))
            )
            .andExpect(status().isOk());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCidade.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCidade.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testCidade.getCidadeUFPais()).isEqualTo(UPDATED_CIDADE_UF_PAIS);
        assertThat(testCidade.getEstadoPais()).isEqualTo(UPDATED_ESTADO_PAIS);
        assertThat(testCidade.getInativo()).isEqualTo(UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void patchNonExistingCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(count.incrementAndGet());

        // Create the Cidade
        CidadeDTO cidadeDTO = cidadeMapper.toDto(cidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cidadeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(count.incrementAndGet());

        // Create the Cidade
        CidadeDTO cidadeDTO = cidadeMapper.toDto(cidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();
        cidade.setId(count.incrementAndGet());

        // Create the Cidade
        CidadeDTO cidadeDTO = cidadeMapper.toDto(cidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCidadeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cidadeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        int databaseSizeBeforeDelete = cidadeRepository.findAll().size();

        // Delete the cidade
        restCidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
