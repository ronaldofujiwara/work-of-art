package br.com.nhw.std.artes.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.Artista;
import br.com.nhw.std.artes.domain.Cidade;
import br.com.nhw.std.artes.domain.Contato;
import br.com.nhw.std.artes.domain.FuncaoArtista;
import br.com.nhw.std.artes.domain.Obra;
import br.com.nhw.std.artes.domain.Responsavel;
import br.com.nhw.std.artes.repository.ArtistaRepository;
import br.com.nhw.std.artes.service.ArtistaService;
import br.com.nhw.std.artes.service.criteria.ArtistaCriteria;
import br.com.nhw.std.artes.service.dto.ArtistaDTO;
import br.com.nhw.std.artes.service.mapper.ArtistaMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ArtistaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArtistaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_NASC = "AAAAAAAAAA";
    private static final String UPDATED_DATA_NASC = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_FALEC = "AAAAAAAAAA";
    private static final String UPDATED_DATA_FALEC = "BBBBBBBBBB";

    private static final String DEFAULT_BIOGRAFIA = "AAAAAAAAAA";
    private static final String UPDATED_BIOGRAFIA = "BBBBBBBBBB";

    private static final String DEFAULT_VERBETE = "AAAAAAAAAA";
    private static final String UPDATED_VERBETE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_ATUAL_BIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ATUAL_BIO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_ATUAL_BIO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_ATUAL_VERB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ATUAL_VERB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_ATUAL_VERB = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_POSSUI_BIO = false;
    private static final Boolean UPDATED_POSSUI_BIO = true;

    private static final Boolean DEFAULT_POSSUI_VERB = false;
    private static final Boolean UPDATED_POSSUI_VERB = true;

    private static final String DEFAULT_FONTE_BIO = "AAAAAAAAAA";
    private static final String UPDATED_FONTE_BIO = "BBBBBBBBBB";

    private static final String DEFAULT_OBRAS_NO_ACERVO = "AAAAAAAAAA";
    private static final String UPDATED_OBRAS_NO_ACERVO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INATIVO = false;
    private static final Boolean UPDATED_INATIVO = true;

    private static final String DEFAULT_AGRADECIMENTOS = "AAAAAAAAAA";
    private static final String UPDATED_AGRADECIMENTOS = "BBBBBBBBBB";

    private static final String DEFAULT_COPYRIGHT = "AAAAAAAAAA";
    private static final String UPDATED_COPYRIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_OBS_USO = "AAAAAAAAAA";
    private static final String UPDATED_OBS_USO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artistas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtistaRepository artistaRepository;

    @Mock
    private ArtistaRepository artistaRepositoryMock;

    @Autowired
    private ArtistaMapper artistaMapper;

    @Mock
    private ArtistaService artistaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtistaMockMvc;

    private Artista artista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artista createEntity(EntityManager em) {
        Artista artista = new Artista()
            .nome(DEFAULT_NOME)
            .dataNasc(DEFAULT_DATA_NASC)
            .dataFalec(DEFAULT_DATA_FALEC)
            .biografia(DEFAULT_BIOGRAFIA)
            .verbete(DEFAULT_VERBETE)
            .dataAtualBio(DEFAULT_DATA_ATUAL_BIO)
            .dataAtualVerb(DEFAULT_DATA_ATUAL_VERB)
            .possuiBio(DEFAULT_POSSUI_BIO)
            .possuiVerb(DEFAULT_POSSUI_VERB)
            .fonteBio(DEFAULT_FONTE_BIO)
            .obrasNoAcervo(DEFAULT_OBRAS_NO_ACERVO)
            .inativo(DEFAULT_INATIVO)
            .agradecimentos(DEFAULT_AGRADECIMENTOS)
            .copyright(DEFAULT_COPYRIGHT)
            .obsUso(DEFAULT_OBS_USO);
        return artista;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artista createUpdatedEntity(EntityManager em) {
        Artista artista = new Artista()
            .nome(UPDATED_NOME)
            .dataNasc(UPDATED_DATA_NASC)
            .dataFalec(UPDATED_DATA_FALEC)
            .biografia(UPDATED_BIOGRAFIA)
            .verbete(UPDATED_VERBETE)
            .dataAtualBio(UPDATED_DATA_ATUAL_BIO)
            .dataAtualVerb(UPDATED_DATA_ATUAL_VERB)
            .possuiBio(UPDATED_POSSUI_BIO)
            .possuiVerb(UPDATED_POSSUI_VERB)
            .fonteBio(UPDATED_FONTE_BIO)
            .obrasNoAcervo(UPDATED_OBRAS_NO_ACERVO)
            .inativo(UPDATED_INATIVO)
            .agradecimentos(UPDATED_AGRADECIMENTOS)
            .copyright(UPDATED_COPYRIGHT)
            .obsUso(UPDATED_OBS_USO);
        return artista;
    }

    @BeforeEach
    public void initTest() {
        artista = createEntity(em);
    }

    @Test
    @Transactional
    void createArtista() throws Exception {
        int databaseSizeBeforeCreate = artistaRepository.findAll().size();
        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);
        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isCreated());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeCreate + 1);
        Artista testArtista = artistaList.get(artistaList.size() - 1);
        assertThat(testArtista.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testArtista.getDataNasc()).isEqualTo(DEFAULT_DATA_NASC);
        assertThat(testArtista.getDataFalec()).isEqualTo(DEFAULT_DATA_FALEC);
        assertThat(testArtista.getBiografia()).isEqualTo(DEFAULT_BIOGRAFIA);
        assertThat(testArtista.getVerbete()).isEqualTo(DEFAULT_VERBETE);
        assertThat(testArtista.getDataAtualBio()).isEqualTo(DEFAULT_DATA_ATUAL_BIO);
        assertThat(testArtista.getDataAtualVerb()).isEqualTo(DEFAULT_DATA_ATUAL_VERB);
        assertThat(testArtista.getPossuiBio()).isEqualTo(DEFAULT_POSSUI_BIO);
        assertThat(testArtista.getPossuiVerb()).isEqualTo(DEFAULT_POSSUI_VERB);
        assertThat(testArtista.getFonteBio()).isEqualTo(DEFAULT_FONTE_BIO);
        assertThat(testArtista.getObrasNoAcervo()).isEqualTo(DEFAULT_OBRAS_NO_ACERVO);
        assertThat(testArtista.getInativo()).isEqualTo(DEFAULT_INATIVO);
        assertThat(testArtista.getAgradecimentos()).isEqualTo(DEFAULT_AGRADECIMENTOS);
        assertThat(testArtista.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testArtista.getObsUso()).isEqualTo(DEFAULT_OBS_USO);
    }

    @Test
    @Transactional
    void createArtistaWithExistingId() throws Exception {
        // Create the Artista with an existing ID
        artista.setId(1L);
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        int databaseSizeBeforeCreate = artistaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistaRepository.findAll().size();
        // set the field null
        artista.setNome(null);

        // Create the Artista, which fails.
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isBadRequest());

        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArtistas() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNasc").value(hasItem(DEFAULT_DATA_NASC)))
            .andExpect(jsonPath("$.[*].dataFalec").value(hasItem(DEFAULT_DATA_FALEC)))
            .andExpect(jsonPath("$.[*].biografia").value(hasItem(DEFAULT_BIOGRAFIA)))
            .andExpect(jsonPath("$.[*].verbete").value(hasItem(DEFAULT_VERBETE)))
            .andExpect(jsonPath("$.[*].dataAtualBio").value(hasItem(DEFAULT_DATA_ATUAL_BIO.toString())))
            .andExpect(jsonPath("$.[*].dataAtualVerb").value(hasItem(DEFAULT_DATA_ATUAL_VERB.toString())))
            .andExpect(jsonPath("$.[*].possuiBio").value(hasItem(DEFAULT_POSSUI_BIO.booleanValue())))
            .andExpect(jsonPath("$.[*].possuiVerb").value(hasItem(DEFAULT_POSSUI_VERB.booleanValue())))
            .andExpect(jsonPath("$.[*].fonteBio").value(hasItem(DEFAULT_FONTE_BIO)))
            .andExpect(jsonPath("$.[*].obrasNoAcervo").value(hasItem(DEFAULT_OBRAS_NO_ACERVO)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].agradecimentos").value(hasItem(DEFAULT_AGRADECIMENTOS)))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT)))
            .andExpect(jsonPath("$.[*].obsUso").value(hasItem(DEFAULT_OBS_USO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArtistasWithEagerRelationshipsIsEnabled() throws Exception {
        when(artistaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArtistaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(artistaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArtistasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(artistaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArtistaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(artistaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getArtista() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get the artista
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL_ID, artista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artista.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataNasc").value(DEFAULT_DATA_NASC))
            .andExpect(jsonPath("$.dataFalec").value(DEFAULT_DATA_FALEC))
            .andExpect(jsonPath("$.biografia").value(DEFAULT_BIOGRAFIA))
            .andExpect(jsonPath("$.verbete").value(DEFAULT_VERBETE))
            .andExpect(jsonPath("$.dataAtualBio").value(DEFAULT_DATA_ATUAL_BIO.toString()))
            .andExpect(jsonPath("$.dataAtualVerb").value(DEFAULT_DATA_ATUAL_VERB.toString()))
            .andExpect(jsonPath("$.possuiBio").value(DEFAULT_POSSUI_BIO.booleanValue()))
            .andExpect(jsonPath("$.possuiVerb").value(DEFAULT_POSSUI_VERB.booleanValue()))
            .andExpect(jsonPath("$.fonteBio").value(DEFAULT_FONTE_BIO))
            .andExpect(jsonPath("$.obrasNoAcervo").value(DEFAULT_OBRAS_NO_ACERVO))
            .andExpect(jsonPath("$.inativo").value(DEFAULT_INATIVO.booleanValue()))
            .andExpect(jsonPath("$.agradecimentos").value(DEFAULT_AGRADECIMENTOS))
            .andExpect(jsonPath("$.copyright").value(DEFAULT_COPYRIGHT))
            .andExpect(jsonPath("$.obsUso").value(DEFAULT_OBS_USO));
    }

    @Test
    @Transactional
    void getArtistasByIdFiltering() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        Long id = artista.getId();

        defaultArtistaShouldBeFound("id.equals=" + id);
        defaultArtistaShouldNotBeFound("id.notEquals=" + id);

        defaultArtistaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultArtistaShouldNotBeFound("id.greaterThan=" + id);

        defaultArtistaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultArtistaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllArtistasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nome equals to DEFAULT_NOME
        defaultArtistaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the artistaList where nome equals to UPDATED_NOME
        defaultArtistaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllArtistasByNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nome not equals to DEFAULT_NOME
        defaultArtistaShouldNotBeFound("nome.notEquals=" + DEFAULT_NOME);

        // Get all the artistaList where nome not equals to UPDATED_NOME
        defaultArtistaShouldBeFound("nome.notEquals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllArtistasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultArtistaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the artistaList where nome equals to UPDATED_NOME
        defaultArtistaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllArtistasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nome is not null
        defaultArtistaShouldBeFound("nome.specified=true");

        // Get all the artistaList where nome is null
        defaultArtistaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByNomeContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nome contains DEFAULT_NOME
        defaultArtistaShouldBeFound("nome.contains=" + DEFAULT_NOME);

        // Get all the artistaList where nome contains UPDATED_NOME
        defaultArtistaShouldNotBeFound("nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllArtistasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nome does not contain DEFAULT_NOME
        defaultArtistaShouldNotBeFound("nome.doesNotContain=" + DEFAULT_NOME);

        // Get all the artistaList where nome does not contain UPDATED_NOME
        defaultArtistaShouldBeFound("nome.doesNotContain=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllArtistasByDataNascIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataNasc equals to DEFAULT_DATA_NASC
        defaultArtistaShouldBeFound("dataNasc.equals=" + DEFAULT_DATA_NASC);

        // Get all the artistaList where dataNasc equals to UPDATED_DATA_NASC
        defaultArtistaShouldNotBeFound("dataNasc.equals=" + UPDATED_DATA_NASC);
    }

    @Test
    @Transactional
    void getAllArtistasByDataNascIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataNasc not equals to DEFAULT_DATA_NASC
        defaultArtistaShouldNotBeFound("dataNasc.notEquals=" + DEFAULT_DATA_NASC);

        // Get all the artistaList where dataNasc not equals to UPDATED_DATA_NASC
        defaultArtistaShouldBeFound("dataNasc.notEquals=" + UPDATED_DATA_NASC);
    }

    @Test
    @Transactional
    void getAllArtistasByDataNascIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataNasc in DEFAULT_DATA_NASC or UPDATED_DATA_NASC
        defaultArtistaShouldBeFound("dataNasc.in=" + DEFAULT_DATA_NASC + "," + UPDATED_DATA_NASC);

        // Get all the artistaList where dataNasc equals to UPDATED_DATA_NASC
        defaultArtistaShouldNotBeFound("dataNasc.in=" + UPDATED_DATA_NASC);
    }

    @Test
    @Transactional
    void getAllArtistasByDataNascIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataNasc is not null
        defaultArtistaShouldBeFound("dataNasc.specified=true");

        // Get all the artistaList where dataNasc is null
        defaultArtistaShouldNotBeFound("dataNasc.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByDataNascContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataNasc contains DEFAULT_DATA_NASC
        defaultArtistaShouldBeFound("dataNasc.contains=" + DEFAULT_DATA_NASC);

        // Get all the artistaList where dataNasc contains UPDATED_DATA_NASC
        defaultArtistaShouldNotBeFound("dataNasc.contains=" + UPDATED_DATA_NASC);
    }

    @Test
    @Transactional
    void getAllArtistasByDataNascNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataNasc does not contain DEFAULT_DATA_NASC
        defaultArtistaShouldNotBeFound("dataNasc.doesNotContain=" + DEFAULT_DATA_NASC);

        // Get all the artistaList where dataNasc does not contain UPDATED_DATA_NASC
        defaultArtistaShouldBeFound("dataNasc.doesNotContain=" + UPDATED_DATA_NASC);
    }

    @Test
    @Transactional
    void getAllArtistasByDataFalecIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataFalec equals to DEFAULT_DATA_FALEC
        defaultArtistaShouldBeFound("dataFalec.equals=" + DEFAULT_DATA_FALEC);

        // Get all the artistaList where dataFalec equals to UPDATED_DATA_FALEC
        defaultArtistaShouldNotBeFound("dataFalec.equals=" + UPDATED_DATA_FALEC);
    }

    @Test
    @Transactional
    void getAllArtistasByDataFalecIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataFalec not equals to DEFAULT_DATA_FALEC
        defaultArtistaShouldNotBeFound("dataFalec.notEquals=" + DEFAULT_DATA_FALEC);

        // Get all the artistaList where dataFalec not equals to UPDATED_DATA_FALEC
        defaultArtistaShouldBeFound("dataFalec.notEquals=" + UPDATED_DATA_FALEC);
    }

    @Test
    @Transactional
    void getAllArtistasByDataFalecIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataFalec in DEFAULT_DATA_FALEC or UPDATED_DATA_FALEC
        defaultArtistaShouldBeFound("dataFalec.in=" + DEFAULT_DATA_FALEC + "," + UPDATED_DATA_FALEC);

        // Get all the artistaList where dataFalec equals to UPDATED_DATA_FALEC
        defaultArtistaShouldNotBeFound("dataFalec.in=" + UPDATED_DATA_FALEC);
    }

    @Test
    @Transactional
    void getAllArtistasByDataFalecIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataFalec is not null
        defaultArtistaShouldBeFound("dataFalec.specified=true");

        // Get all the artistaList where dataFalec is null
        defaultArtistaShouldNotBeFound("dataFalec.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByDataFalecContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataFalec contains DEFAULT_DATA_FALEC
        defaultArtistaShouldBeFound("dataFalec.contains=" + DEFAULT_DATA_FALEC);

        // Get all the artistaList where dataFalec contains UPDATED_DATA_FALEC
        defaultArtistaShouldNotBeFound("dataFalec.contains=" + UPDATED_DATA_FALEC);
    }

    @Test
    @Transactional
    void getAllArtistasByDataFalecNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataFalec does not contain DEFAULT_DATA_FALEC
        defaultArtistaShouldNotBeFound("dataFalec.doesNotContain=" + DEFAULT_DATA_FALEC);

        // Get all the artistaList where dataFalec does not contain UPDATED_DATA_FALEC
        defaultArtistaShouldBeFound("dataFalec.doesNotContain=" + UPDATED_DATA_FALEC);
    }

    @Test
    @Transactional
    void getAllArtistasByBiografiaIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where biografia equals to DEFAULT_BIOGRAFIA
        defaultArtistaShouldBeFound("biografia.equals=" + DEFAULT_BIOGRAFIA);

        // Get all the artistaList where biografia equals to UPDATED_BIOGRAFIA
        defaultArtistaShouldNotBeFound("biografia.equals=" + UPDATED_BIOGRAFIA);
    }

    @Test
    @Transactional
    void getAllArtistasByBiografiaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where biografia not equals to DEFAULT_BIOGRAFIA
        defaultArtistaShouldNotBeFound("biografia.notEquals=" + DEFAULT_BIOGRAFIA);

        // Get all the artistaList where biografia not equals to UPDATED_BIOGRAFIA
        defaultArtistaShouldBeFound("biografia.notEquals=" + UPDATED_BIOGRAFIA);
    }

    @Test
    @Transactional
    void getAllArtistasByBiografiaIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where biografia in DEFAULT_BIOGRAFIA or UPDATED_BIOGRAFIA
        defaultArtistaShouldBeFound("biografia.in=" + DEFAULT_BIOGRAFIA + "," + UPDATED_BIOGRAFIA);

        // Get all the artistaList where biografia equals to UPDATED_BIOGRAFIA
        defaultArtistaShouldNotBeFound("biografia.in=" + UPDATED_BIOGRAFIA);
    }

    @Test
    @Transactional
    void getAllArtistasByBiografiaIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where biografia is not null
        defaultArtistaShouldBeFound("biografia.specified=true");

        // Get all the artistaList where biografia is null
        defaultArtistaShouldNotBeFound("biografia.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByBiografiaContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where biografia contains DEFAULT_BIOGRAFIA
        defaultArtistaShouldBeFound("biografia.contains=" + DEFAULT_BIOGRAFIA);

        // Get all the artistaList where biografia contains UPDATED_BIOGRAFIA
        defaultArtistaShouldNotBeFound("biografia.contains=" + UPDATED_BIOGRAFIA);
    }

    @Test
    @Transactional
    void getAllArtistasByBiografiaNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where biografia does not contain DEFAULT_BIOGRAFIA
        defaultArtistaShouldNotBeFound("biografia.doesNotContain=" + DEFAULT_BIOGRAFIA);

        // Get all the artistaList where biografia does not contain UPDATED_BIOGRAFIA
        defaultArtistaShouldBeFound("biografia.doesNotContain=" + UPDATED_BIOGRAFIA);
    }

    @Test
    @Transactional
    void getAllArtistasByVerbeteIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where verbete equals to DEFAULT_VERBETE
        defaultArtistaShouldBeFound("verbete.equals=" + DEFAULT_VERBETE);

        // Get all the artistaList where verbete equals to UPDATED_VERBETE
        defaultArtistaShouldNotBeFound("verbete.equals=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllArtistasByVerbeteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where verbete not equals to DEFAULT_VERBETE
        defaultArtistaShouldNotBeFound("verbete.notEquals=" + DEFAULT_VERBETE);

        // Get all the artistaList where verbete not equals to UPDATED_VERBETE
        defaultArtistaShouldBeFound("verbete.notEquals=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllArtistasByVerbeteIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where verbete in DEFAULT_VERBETE or UPDATED_VERBETE
        defaultArtistaShouldBeFound("verbete.in=" + DEFAULT_VERBETE + "," + UPDATED_VERBETE);

        // Get all the artistaList where verbete equals to UPDATED_VERBETE
        defaultArtistaShouldNotBeFound("verbete.in=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllArtistasByVerbeteIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where verbete is not null
        defaultArtistaShouldBeFound("verbete.specified=true");

        // Get all the artistaList where verbete is null
        defaultArtistaShouldNotBeFound("verbete.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByVerbeteContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where verbete contains DEFAULT_VERBETE
        defaultArtistaShouldBeFound("verbete.contains=" + DEFAULT_VERBETE);

        // Get all the artistaList where verbete contains UPDATED_VERBETE
        defaultArtistaShouldNotBeFound("verbete.contains=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllArtistasByVerbeteNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where verbete does not contain DEFAULT_VERBETE
        defaultArtistaShouldNotBeFound("verbete.doesNotContain=" + DEFAULT_VERBETE);

        // Get all the artistaList where verbete does not contain UPDATED_VERBETE
        defaultArtistaShouldBeFound("verbete.doesNotContain=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualBioIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualBio equals to DEFAULT_DATA_ATUAL_BIO
        defaultArtistaShouldBeFound("dataAtualBio.equals=" + DEFAULT_DATA_ATUAL_BIO);

        // Get all the artistaList where dataAtualBio equals to UPDATED_DATA_ATUAL_BIO
        defaultArtistaShouldNotBeFound("dataAtualBio.equals=" + UPDATED_DATA_ATUAL_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualBioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualBio not equals to DEFAULT_DATA_ATUAL_BIO
        defaultArtistaShouldNotBeFound("dataAtualBio.notEquals=" + DEFAULT_DATA_ATUAL_BIO);

        // Get all the artistaList where dataAtualBio not equals to UPDATED_DATA_ATUAL_BIO
        defaultArtistaShouldBeFound("dataAtualBio.notEquals=" + UPDATED_DATA_ATUAL_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualBioIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualBio in DEFAULT_DATA_ATUAL_BIO or UPDATED_DATA_ATUAL_BIO
        defaultArtistaShouldBeFound("dataAtualBio.in=" + DEFAULT_DATA_ATUAL_BIO + "," + UPDATED_DATA_ATUAL_BIO);

        // Get all the artistaList where dataAtualBio equals to UPDATED_DATA_ATUAL_BIO
        defaultArtistaShouldNotBeFound("dataAtualBio.in=" + UPDATED_DATA_ATUAL_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualBioIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualBio is not null
        defaultArtistaShouldBeFound("dataAtualBio.specified=true");

        // Get all the artistaList where dataAtualBio is null
        defaultArtistaShouldNotBeFound("dataAtualBio.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualBioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualBio is greater than or equal to DEFAULT_DATA_ATUAL_BIO
        defaultArtistaShouldBeFound("dataAtualBio.greaterThanOrEqual=" + DEFAULT_DATA_ATUAL_BIO);

        // Get all the artistaList where dataAtualBio is greater than or equal to UPDATED_DATA_ATUAL_BIO
        defaultArtistaShouldNotBeFound("dataAtualBio.greaterThanOrEqual=" + UPDATED_DATA_ATUAL_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualBioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualBio is less than or equal to DEFAULT_DATA_ATUAL_BIO
        defaultArtistaShouldBeFound("dataAtualBio.lessThanOrEqual=" + DEFAULT_DATA_ATUAL_BIO);

        // Get all the artistaList where dataAtualBio is less than or equal to SMALLER_DATA_ATUAL_BIO
        defaultArtistaShouldNotBeFound("dataAtualBio.lessThanOrEqual=" + SMALLER_DATA_ATUAL_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualBioIsLessThanSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualBio is less than DEFAULT_DATA_ATUAL_BIO
        defaultArtistaShouldNotBeFound("dataAtualBio.lessThan=" + DEFAULT_DATA_ATUAL_BIO);

        // Get all the artistaList where dataAtualBio is less than UPDATED_DATA_ATUAL_BIO
        defaultArtistaShouldBeFound("dataAtualBio.lessThan=" + UPDATED_DATA_ATUAL_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualBioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualBio is greater than DEFAULT_DATA_ATUAL_BIO
        defaultArtistaShouldNotBeFound("dataAtualBio.greaterThan=" + DEFAULT_DATA_ATUAL_BIO);

        // Get all the artistaList where dataAtualBio is greater than SMALLER_DATA_ATUAL_BIO
        defaultArtistaShouldBeFound("dataAtualBio.greaterThan=" + SMALLER_DATA_ATUAL_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualVerbIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualVerb equals to DEFAULT_DATA_ATUAL_VERB
        defaultArtistaShouldBeFound("dataAtualVerb.equals=" + DEFAULT_DATA_ATUAL_VERB);

        // Get all the artistaList where dataAtualVerb equals to UPDATED_DATA_ATUAL_VERB
        defaultArtistaShouldNotBeFound("dataAtualVerb.equals=" + UPDATED_DATA_ATUAL_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualVerbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualVerb not equals to DEFAULT_DATA_ATUAL_VERB
        defaultArtistaShouldNotBeFound("dataAtualVerb.notEquals=" + DEFAULT_DATA_ATUAL_VERB);

        // Get all the artistaList where dataAtualVerb not equals to UPDATED_DATA_ATUAL_VERB
        defaultArtistaShouldBeFound("dataAtualVerb.notEquals=" + UPDATED_DATA_ATUAL_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualVerbIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualVerb in DEFAULT_DATA_ATUAL_VERB or UPDATED_DATA_ATUAL_VERB
        defaultArtistaShouldBeFound("dataAtualVerb.in=" + DEFAULT_DATA_ATUAL_VERB + "," + UPDATED_DATA_ATUAL_VERB);

        // Get all the artistaList where dataAtualVerb equals to UPDATED_DATA_ATUAL_VERB
        defaultArtistaShouldNotBeFound("dataAtualVerb.in=" + UPDATED_DATA_ATUAL_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualVerbIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualVerb is not null
        defaultArtistaShouldBeFound("dataAtualVerb.specified=true");

        // Get all the artistaList where dataAtualVerb is null
        defaultArtistaShouldNotBeFound("dataAtualVerb.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualVerbIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualVerb is greater than or equal to DEFAULT_DATA_ATUAL_VERB
        defaultArtistaShouldBeFound("dataAtualVerb.greaterThanOrEqual=" + DEFAULT_DATA_ATUAL_VERB);

        // Get all the artistaList where dataAtualVerb is greater than or equal to UPDATED_DATA_ATUAL_VERB
        defaultArtistaShouldNotBeFound("dataAtualVerb.greaterThanOrEqual=" + UPDATED_DATA_ATUAL_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualVerbIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualVerb is less than or equal to DEFAULT_DATA_ATUAL_VERB
        defaultArtistaShouldBeFound("dataAtualVerb.lessThanOrEqual=" + DEFAULT_DATA_ATUAL_VERB);

        // Get all the artistaList where dataAtualVerb is less than or equal to SMALLER_DATA_ATUAL_VERB
        defaultArtistaShouldNotBeFound("dataAtualVerb.lessThanOrEqual=" + SMALLER_DATA_ATUAL_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualVerbIsLessThanSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualVerb is less than DEFAULT_DATA_ATUAL_VERB
        defaultArtistaShouldNotBeFound("dataAtualVerb.lessThan=" + DEFAULT_DATA_ATUAL_VERB);

        // Get all the artistaList where dataAtualVerb is less than UPDATED_DATA_ATUAL_VERB
        defaultArtistaShouldBeFound("dataAtualVerb.lessThan=" + UPDATED_DATA_ATUAL_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByDataAtualVerbIsGreaterThanSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where dataAtualVerb is greater than DEFAULT_DATA_ATUAL_VERB
        defaultArtistaShouldNotBeFound("dataAtualVerb.greaterThan=" + DEFAULT_DATA_ATUAL_VERB);

        // Get all the artistaList where dataAtualVerb is greater than SMALLER_DATA_ATUAL_VERB
        defaultArtistaShouldBeFound("dataAtualVerb.greaterThan=" + SMALLER_DATA_ATUAL_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByPossuiBioIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where possuiBio equals to DEFAULT_POSSUI_BIO
        defaultArtistaShouldBeFound("possuiBio.equals=" + DEFAULT_POSSUI_BIO);

        // Get all the artistaList where possuiBio equals to UPDATED_POSSUI_BIO
        defaultArtistaShouldNotBeFound("possuiBio.equals=" + UPDATED_POSSUI_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByPossuiBioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where possuiBio not equals to DEFAULT_POSSUI_BIO
        defaultArtistaShouldNotBeFound("possuiBio.notEquals=" + DEFAULT_POSSUI_BIO);

        // Get all the artistaList where possuiBio not equals to UPDATED_POSSUI_BIO
        defaultArtistaShouldBeFound("possuiBio.notEquals=" + UPDATED_POSSUI_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByPossuiBioIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where possuiBio in DEFAULT_POSSUI_BIO or UPDATED_POSSUI_BIO
        defaultArtistaShouldBeFound("possuiBio.in=" + DEFAULT_POSSUI_BIO + "," + UPDATED_POSSUI_BIO);

        // Get all the artistaList where possuiBio equals to UPDATED_POSSUI_BIO
        defaultArtistaShouldNotBeFound("possuiBio.in=" + UPDATED_POSSUI_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByPossuiBioIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where possuiBio is not null
        defaultArtistaShouldBeFound("possuiBio.specified=true");

        // Get all the artistaList where possuiBio is null
        defaultArtistaShouldNotBeFound("possuiBio.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByPossuiVerbIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where possuiVerb equals to DEFAULT_POSSUI_VERB
        defaultArtistaShouldBeFound("possuiVerb.equals=" + DEFAULT_POSSUI_VERB);

        // Get all the artistaList where possuiVerb equals to UPDATED_POSSUI_VERB
        defaultArtistaShouldNotBeFound("possuiVerb.equals=" + UPDATED_POSSUI_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByPossuiVerbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where possuiVerb not equals to DEFAULT_POSSUI_VERB
        defaultArtistaShouldNotBeFound("possuiVerb.notEquals=" + DEFAULT_POSSUI_VERB);

        // Get all the artistaList where possuiVerb not equals to UPDATED_POSSUI_VERB
        defaultArtistaShouldBeFound("possuiVerb.notEquals=" + UPDATED_POSSUI_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByPossuiVerbIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where possuiVerb in DEFAULT_POSSUI_VERB or UPDATED_POSSUI_VERB
        defaultArtistaShouldBeFound("possuiVerb.in=" + DEFAULT_POSSUI_VERB + "," + UPDATED_POSSUI_VERB);

        // Get all the artistaList where possuiVerb equals to UPDATED_POSSUI_VERB
        defaultArtistaShouldNotBeFound("possuiVerb.in=" + UPDATED_POSSUI_VERB);
    }

    @Test
    @Transactional
    void getAllArtistasByPossuiVerbIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where possuiVerb is not null
        defaultArtistaShouldBeFound("possuiVerb.specified=true");

        // Get all the artistaList where possuiVerb is null
        defaultArtistaShouldNotBeFound("possuiVerb.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByFonteBioIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fonteBio equals to DEFAULT_FONTE_BIO
        defaultArtistaShouldBeFound("fonteBio.equals=" + DEFAULT_FONTE_BIO);

        // Get all the artistaList where fonteBio equals to UPDATED_FONTE_BIO
        defaultArtistaShouldNotBeFound("fonteBio.equals=" + UPDATED_FONTE_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByFonteBioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fonteBio not equals to DEFAULT_FONTE_BIO
        defaultArtistaShouldNotBeFound("fonteBio.notEquals=" + DEFAULT_FONTE_BIO);

        // Get all the artistaList where fonteBio not equals to UPDATED_FONTE_BIO
        defaultArtistaShouldBeFound("fonteBio.notEquals=" + UPDATED_FONTE_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByFonteBioIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fonteBio in DEFAULT_FONTE_BIO or UPDATED_FONTE_BIO
        defaultArtistaShouldBeFound("fonteBio.in=" + DEFAULT_FONTE_BIO + "," + UPDATED_FONTE_BIO);

        // Get all the artistaList where fonteBio equals to UPDATED_FONTE_BIO
        defaultArtistaShouldNotBeFound("fonteBio.in=" + UPDATED_FONTE_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByFonteBioIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fonteBio is not null
        defaultArtistaShouldBeFound("fonteBio.specified=true");

        // Get all the artistaList where fonteBio is null
        defaultArtistaShouldNotBeFound("fonteBio.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByFonteBioContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fonteBio contains DEFAULT_FONTE_BIO
        defaultArtistaShouldBeFound("fonteBio.contains=" + DEFAULT_FONTE_BIO);

        // Get all the artistaList where fonteBio contains UPDATED_FONTE_BIO
        defaultArtistaShouldNotBeFound("fonteBio.contains=" + UPDATED_FONTE_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByFonteBioNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fonteBio does not contain DEFAULT_FONTE_BIO
        defaultArtistaShouldNotBeFound("fonteBio.doesNotContain=" + DEFAULT_FONTE_BIO);

        // Get all the artistaList where fonteBio does not contain UPDATED_FONTE_BIO
        defaultArtistaShouldBeFound("fonteBio.doesNotContain=" + UPDATED_FONTE_BIO);
    }

    @Test
    @Transactional
    void getAllArtistasByObrasNoAcervoIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obrasNoAcervo equals to DEFAULT_OBRAS_NO_ACERVO
        defaultArtistaShouldBeFound("obrasNoAcervo.equals=" + DEFAULT_OBRAS_NO_ACERVO);

        // Get all the artistaList where obrasNoAcervo equals to UPDATED_OBRAS_NO_ACERVO
        defaultArtistaShouldNotBeFound("obrasNoAcervo.equals=" + UPDATED_OBRAS_NO_ACERVO);
    }

    @Test
    @Transactional
    void getAllArtistasByObrasNoAcervoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obrasNoAcervo not equals to DEFAULT_OBRAS_NO_ACERVO
        defaultArtistaShouldNotBeFound("obrasNoAcervo.notEquals=" + DEFAULT_OBRAS_NO_ACERVO);

        // Get all the artistaList where obrasNoAcervo not equals to UPDATED_OBRAS_NO_ACERVO
        defaultArtistaShouldBeFound("obrasNoAcervo.notEquals=" + UPDATED_OBRAS_NO_ACERVO);
    }

    @Test
    @Transactional
    void getAllArtistasByObrasNoAcervoIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obrasNoAcervo in DEFAULT_OBRAS_NO_ACERVO or UPDATED_OBRAS_NO_ACERVO
        defaultArtistaShouldBeFound("obrasNoAcervo.in=" + DEFAULT_OBRAS_NO_ACERVO + "," + UPDATED_OBRAS_NO_ACERVO);

        // Get all the artistaList where obrasNoAcervo equals to UPDATED_OBRAS_NO_ACERVO
        defaultArtistaShouldNotBeFound("obrasNoAcervo.in=" + UPDATED_OBRAS_NO_ACERVO);
    }

    @Test
    @Transactional
    void getAllArtistasByObrasNoAcervoIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obrasNoAcervo is not null
        defaultArtistaShouldBeFound("obrasNoAcervo.specified=true");

        // Get all the artistaList where obrasNoAcervo is null
        defaultArtistaShouldNotBeFound("obrasNoAcervo.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByObrasNoAcervoContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obrasNoAcervo contains DEFAULT_OBRAS_NO_ACERVO
        defaultArtistaShouldBeFound("obrasNoAcervo.contains=" + DEFAULT_OBRAS_NO_ACERVO);

        // Get all the artistaList where obrasNoAcervo contains UPDATED_OBRAS_NO_ACERVO
        defaultArtistaShouldNotBeFound("obrasNoAcervo.contains=" + UPDATED_OBRAS_NO_ACERVO);
    }

    @Test
    @Transactional
    void getAllArtistasByObrasNoAcervoNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obrasNoAcervo does not contain DEFAULT_OBRAS_NO_ACERVO
        defaultArtistaShouldNotBeFound("obrasNoAcervo.doesNotContain=" + DEFAULT_OBRAS_NO_ACERVO);

        // Get all the artistaList where obrasNoAcervo does not contain UPDATED_OBRAS_NO_ACERVO
        defaultArtistaShouldBeFound("obrasNoAcervo.doesNotContain=" + UPDATED_OBRAS_NO_ACERVO);
    }

    @Test
    @Transactional
    void getAllArtistasByInativoIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where inativo equals to DEFAULT_INATIVO
        defaultArtistaShouldBeFound("inativo.equals=" + DEFAULT_INATIVO);

        // Get all the artistaList where inativo equals to UPDATED_INATIVO
        defaultArtistaShouldNotBeFound("inativo.equals=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllArtistasByInativoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where inativo not equals to DEFAULT_INATIVO
        defaultArtistaShouldNotBeFound("inativo.notEquals=" + DEFAULT_INATIVO);

        // Get all the artistaList where inativo not equals to UPDATED_INATIVO
        defaultArtistaShouldBeFound("inativo.notEquals=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllArtistasByInativoIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where inativo in DEFAULT_INATIVO or UPDATED_INATIVO
        defaultArtistaShouldBeFound("inativo.in=" + DEFAULT_INATIVO + "," + UPDATED_INATIVO);

        // Get all the artistaList where inativo equals to UPDATED_INATIVO
        defaultArtistaShouldNotBeFound("inativo.in=" + UPDATED_INATIVO);
    }

    @Test
    @Transactional
    void getAllArtistasByInativoIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where inativo is not null
        defaultArtistaShouldBeFound("inativo.specified=true");

        // Get all the artistaList where inativo is null
        defaultArtistaShouldNotBeFound("inativo.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByAgradecimentosIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where agradecimentos equals to DEFAULT_AGRADECIMENTOS
        defaultArtistaShouldBeFound("agradecimentos.equals=" + DEFAULT_AGRADECIMENTOS);

        // Get all the artistaList where agradecimentos equals to UPDATED_AGRADECIMENTOS
        defaultArtistaShouldNotBeFound("agradecimentos.equals=" + UPDATED_AGRADECIMENTOS);
    }

    @Test
    @Transactional
    void getAllArtistasByAgradecimentosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where agradecimentos not equals to DEFAULT_AGRADECIMENTOS
        defaultArtistaShouldNotBeFound("agradecimentos.notEquals=" + DEFAULT_AGRADECIMENTOS);

        // Get all the artistaList where agradecimentos not equals to UPDATED_AGRADECIMENTOS
        defaultArtistaShouldBeFound("agradecimentos.notEquals=" + UPDATED_AGRADECIMENTOS);
    }

    @Test
    @Transactional
    void getAllArtistasByAgradecimentosIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where agradecimentos in DEFAULT_AGRADECIMENTOS or UPDATED_AGRADECIMENTOS
        defaultArtistaShouldBeFound("agradecimentos.in=" + DEFAULT_AGRADECIMENTOS + "," + UPDATED_AGRADECIMENTOS);

        // Get all the artistaList where agradecimentos equals to UPDATED_AGRADECIMENTOS
        defaultArtistaShouldNotBeFound("agradecimentos.in=" + UPDATED_AGRADECIMENTOS);
    }

    @Test
    @Transactional
    void getAllArtistasByAgradecimentosIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where agradecimentos is not null
        defaultArtistaShouldBeFound("agradecimentos.specified=true");

        // Get all the artistaList where agradecimentos is null
        defaultArtistaShouldNotBeFound("agradecimentos.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByAgradecimentosContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where agradecimentos contains DEFAULT_AGRADECIMENTOS
        defaultArtistaShouldBeFound("agradecimentos.contains=" + DEFAULT_AGRADECIMENTOS);

        // Get all the artistaList where agradecimentos contains UPDATED_AGRADECIMENTOS
        defaultArtistaShouldNotBeFound("agradecimentos.contains=" + UPDATED_AGRADECIMENTOS);
    }

    @Test
    @Transactional
    void getAllArtistasByAgradecimentosNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where agradecimentos does not contain DEFAULT_AGRADECIMENTOS
        defaultArtistaShouldNotBeFound("agradecimentos.doesNotContain=" + DEFAULT_AGRADECIMENTOS);

        // Get all the artistaList where agradecimentos does not contain UPDATED_AGRADECIMENTOS
        defaultArtistaShouldBeFound("agradecimentos.doesNotContain=" + UPDATED_AGRADECIMENTOS);
    }

    @Test
    @Transactional
    void getAllArtistasByCopyrightIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where copyright equals to DEFAULT_COPYRIGHT
        defaultArtistaShouldBeFound("copyright.equals=" + DEFAULT_COPYRIGHT);

        // Get all the artistaList where copyright equals to UPDATED_COPYRIGHT
        defaultArtistaShouldNotBeFound("copyright.equals=" + UPDATED_COPYRIGHT);
    }

    @Test
    @Transactional
    void getAllArtistasByCopyrightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where copyright not equals to DEFAULT_COPYRIGHT
        defaultArtistaShouldNotBeFound("copyright.notEquals=" + DEFAULT_COPYRIGHT);

        // Get all the artistaList where copyright not equals to UPDATED_COPYRIGHT
        defaultArtistaShouldBeFound("copyright.notEquals=" + UPDATED_COPYRIGHT);
    }

    @Test
    @Transactional
    void getAllArtistasByCopyrightIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where copyright in DEFAULT_COPYRIGHT or UPDATED_COPYRIGHT
        defaultArtistaShouldBeFound("copyright.in=" + DEFAULT_COPYRIGHT + "," + UPDATED_COPYRIGHT);

        // Get all the artistaList where copyright equals to UPDATED_COPYRIGHT
        defaultArtistaShouldNotBeFound("copyright.in=" + UPDATED_COPYRIGHT);
    }

    @Test
    @Transactional
    void getAllArtistasByCopyrightIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where copyright is not null
        defaultArtistaShouldBeFound("copyright.specified=true");

        // Get all the artistaList where copyright is null
        defaultArtistaShouldNotBeFound("copyright.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByCopyrightContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where copyright contains DEFAULT_COPYRIGHT
        defaultArtistaShouldBeFound("copyright.contains=" + DEFAULT_COPYRIGHT);

        // Get all the artistaList where copyright contains UPDATED_COPYRIGHT
        defaultArtistaShouldNotBeFound("copyright.contains=" + UPDATED_COPYRIGHT);
    }

    @Test
    @Transactional
    void getAllArtistasByCopyrightNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where copyright does not contain DEFAULT_COPYRIGHT
        defaultArtistaShouldNotBeFound("copyright.doesNotContain=" + DEFAULT_COPYRIGHT);

        // Get all the artistaList where copyright does not contain UPDATED_COPYRIGHT
        defaultArtistaShouldBeFound("copyright.doesNotContain=" + UPDATED_COPYRIGHT);
    }

    @Test
    @Transactional
    void getAllArtistasByObsUsoIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obsUso equals to DEFAULT_OBS_USO
        defaultArtistaShouldBeFound("obsUso.equals=" + DEFAULT_OBS_USO);

        // Get all the artistaList where obsUso equals to UPDATED_OBS_USO
        defaultArtistaShouldNotBeFound("obsUso.equals=" + UPDATED_OBS_USO);
    }

    @Test
    @Transactional
    void getAllArtistasByObsUsoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obsUso not equals to DEFAULT_OBS_USO
        defaultArtistaShouldNotBeFound("obsUso.notEquals=" + DEFAULT_OBS_USO);

        // Get all the artistaList where obsUso not equals to UPDATED_OBS_USO
        defaultArtistaShouldBeFound("obsUso.notEquals=" + UPDATED_OBS_USO);
    }

    @Test
    @Transactional
    void getAllArtistasByObsUsoIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obsUso in DEFAULT_OBS_USO or UPDATED_OBS_USO
        defaultArtistaShouldBeFound("obsUso.in=" + DEFAULT_OBS_USO + "," + UPDATED_OBS_USO);

        // Get all the artistaList where obsUso equals to UPDATED_OBS_USO
        defaultArtistaShouldNotBeFound("obsUso.in=" + UPDATED_OBS_USO);
    }

    @Test
    @Transactional
    void getAllArtistasByObsUsoIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obsUso is not null
        defaultArtistaShouldBeFound("obsUso.specified=true");

        // Get all the artistaList where obsUso is null
        defaultArtistaShouldNotBeFound("obsUso.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByObsUsoContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obsUso contains DEFAULT_OBS_USO
        defaultArtistaShouldBeFound("obsUso.contains=" + DEFAULT_OBS_USO);

        // Get all the artistaList where obsUso contains UPDATED_OBS_USO
        defaultArtistaShouldNotBeFound("obsUso.contains=" + UPDATED_OBS_USO);
    }

    @Test
    @Transactional
    void getAllArtistasByObsUsoNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where obsUso does not contain DEFAULT_OBS_USO
        defaultArtistaShouldNotBeFound("obsUso.doesNotContain=" + DEFAULT_OBS_USO);

        // Get all the artistaList where obsUso does not contain UPDATED_OBS_USO
        defaultArtistaShouldBeFound("obsUso.doesNotContain=" + UPDATED_OBS_USO);
    }

    @Test
    @Transactional
    void getAllArtistasByObraIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);
        Obra obra = ObraResourceIT.createEntity(em);
        em.persist(obra);
        em.flush();
        artista.addObra(obra);
        artistaRepository.saveAndFlush(artista);
        Long obraId = obra.getId();

        // Get all the artistaList where obra equals to obraId
        defaultArtistaShouldBeFound("obraId.equals=" + obraId);

        // Get all the artistaList where obra equals to (obraId + 1)
        defaultArtistaShouldNotBeFound("obraId.equals=" + (obraId + 1));
    }

    @Test
    @Transactional
    void getAllArtistasByContatoIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);
        Contato contato = ContatoResourceIT.createEntity(em);
        em.persist(contato);
        em.flush();
        artista.addContato(contato);
        artistaRepository.saveAndFlush(artista);
        Long contatoId = contato.getId();

        // Get all the artistaList where contato equals to contatoId
        defaultArtistaShouldBeFound("contatoId.equals=" + contatoId);

        // Get all the artistaList where contato equals to (contatoId + 1)
        defaultArtistaShouldNotBeFound("contatoId.equals=" + (contatoId + 1));
    }

    @Test
    @Transactional
    void getAllArtistasByCidadeNascIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);
        Cidade cidadeNasc = CidadeResourceIT.createEntity(em);
        em.persist(cidadeNasc);
        em.flush();
        artista.setCidadeNasc(cidadeNasc);
        artistaRepository.saveAndFlush(artista);
        Long cidadeNascId = cidadeNasc.getId();

        // Get all the artistaList where cidadeNasc equals to cidadeNascId
        defaultArtistaShouldBeFound("cidadeNascId.equals=" + cidadeNascId);

        // Get all the artistaList where cidadeNasc equals to (cidadeNascId + 1)
        defaultArtistaShouldNotBeFound("cidadeNascId.equals=" + (cidadeNascId + 1));
    }

    @Test
    @Transactional
    void getAllArtistasByCidadeFalescIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);
        Cidade cidadeFalesc = CidadeResourceIT.createEntity(em);
        em.persist(cidadeFalesc);
        em.flush();
        artista.setCidadeFalesc(cidadeFalesc);
        artistaRepository.saveAndFlush(artista);
        Long cidadeFalescId = cidadeFalesc.getId();

        // Get all the artistaList where cidadeFalesc equals to cidadeFalescId
        defaultArtistaShouldBeFound("cidadeFalescId.equals=" + cidadeFalescId);

        // Get all the artistaList where cidadeFalesc equals to (cidadeFalescId + 1)
        defaultArtistaShouldNotBeFound("cidadeFalescId.equals=" + (cidadeFalescId + 1));
    }

    @Test
    @Transactional
    void getAllArtistasByRespVerbeteIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);
        Responsavel respVerbete = ResponsavelResourceIT.createEntity(em);
        em.persist(respVerbete);
        em.flush();
        artista.setRespVerbete(respVerbete);
        artistaRepository.saveAndFlush(artista);
        Long respVerbeteId = respVerbete.getId();

        // Get all the artistaList where respVerbete equals to respVerbeteId
        defaultArtistaShouldBeFound("respVerbeteId.equals=" + respVerbeteId);

        // Get all the artistaList where respVerbete equals to (respVerbeteId + 1)
        defaultArtistaShouldNotBeFound("respVerbeteId.equals=" + (respVerbeteId + 1));
    }

    @Test
    @Transactional
    void getAllArtistasByFuncaoArtistaIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);
        FuncaoArtista funcaoArtista = FuncaoArtistaResourceIT.createEntity(em);
        em.persist(funcaoArtista);
        em.flush();
        artista.setFuncaoArtista(funcaoArtista);
        artistaRepository.saveAndFlush(artista);
        Long funcaoArtistaId = funcaoArtista.getId();

        // Get all the artistaList where funcaoArtista equals to funcaoArtistaId
        defaultArtistaShouldBeFound("funcaoArtistaId.equals=" + funcaoArtistaId);

        // Get all the artistaList where funcaoArtista equals to (funcaoArtistaId + 1)
        defaultArtistaShouldNotBeFound("funcaoArtistaId.equals=" + (funcaoArtistaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArtistaShouldBeFound(String filter) throws Exception {
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNasc").value(hasItem(DEFAULT_DATA_NASC)))
            .andExpect(jsonPath("$.[*].dataFalec").value(hasItem(DEFAULT_DATA_FALEC)))
            .andExpect(jsonPath("$.[*].biografia").value(hasItem(DEFAULT_BIOGRAFIA)))
            .andExpect(jsonPath("$.[*].verbete").value(hasItem(DEFAULT_VERBETE)))
            .andExpect(jsonPath("$.[*].dataAtualBio").value(hasItem(DEFAULT_DATA_ATUAL_BIO.toString())))
            .andExpect(jsonPath("$.[*].dataAtualVerb").value(hasItem(DEFAULT_DATA_ATUAL_VERB.toString())))
            .andExpect(jsonPath("$.[*].possuiBio").value(hasItem(DEFAULT_POSSUI_BIO.booleanValue())))
            .andExpect(jsonPath("$.[*].possuiVerb").value(hasItem(DEFAULT_POSSUI_VERB.booleanValue())))
            .andExpect(jsonPath("$.[*].fonteBio").value(hasItem(DEFAULT_FONTE_BIO)))
            .andExpect(jsonPath("$.[*].obrasNoAcervo").value(hasItem(DEFAULT_OBRAS_NO_ACERVO)))
            .andExpect(jsonPath("$.[*].inativo").value(hasItem(DEFAULT_INATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].agradecimentos").value(hasItem(DEFAULT_AGRADECIMENTOS)))
            .andExpect(jsonPath("$.[*].copyright").value(hasItem(DEFAULT_COPYRIGHT)))
            .andExpect(jsonPath("$.[*].obsUso").value(hasItem(DEFAULT_OBS_USO)));

        // Check, that the count call also returns 1
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArtistaShouldNotBeFound(String filter) throws Exception {
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingArtista() throws Exception {
        // Get the artista
        restArtistaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewArtista() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();

        // Update the artista
        Artista updatedArtista = artistaRepository.findById(artista.getId()).get();
        // Disconnect from session so that the updates on updatedArtista are not directly saved in db
        em.detach(updatedArtista);
        updatedArtista
            .nome(UPDATED_NOME)
            .dataNasc(UPDATED_DATA_NASC)
            .dataFalec(UPDATED_DATA_FALEC)
            .biografia(UPDATED_BIOGRAFIA)
            .verbete(UPDATED_VERBETE)
            .dataAtualBio(UPDATED_DATA_ATUAL_BIO)
            .dataAtualVerb(UPDATED_DATA_ATUAL_VERB)
            .possuiBio(UPDATED_POSSUI_BIO)
            .possuiVerb(UPDATED_POSSUI_VERB)
            .fonteBio(UPDATED_FONTE_BIO)
            .obrasNoAcervo(UPDATED_OBRAS_NO_ACERVO)
            .inativo(UPDATED_INATIVO)
            .agradecimentos(UPDATED_AGRADECIMENTOS)
            .copyright(UPDATED_COPYRIGHT)
            .obsUso(UPDATED_OBS_USO);
        ArtistaDTO artistaDTO = artistaMapper.toDto(updatedArtista);

        restArtistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
        Artista testArtista = artistaList.get(artistaList.size() - 1);
        assertThat(testArtista.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testArtista.getDataNasc()).isEqualTo(UPDATED_DATA_NASC);
        assertThat(testArtista.getDataFalec()).isEqualTo(UPDATED_DATA_FALEC);
        assertThat(testArtista.getBiografia()).isEqualTo(UPDATED_BIOGRAFIA);
        assertThat(testArtista.getVerbete()).isEqualTo(UPDATED_VERBETE);
        assertThat(testArtista.getDataAtualBio()).isEqualTo(UPDATED_DATA_ATUAL_BIO);
        assertThat(testArtista.getDataAtualVerb()).isEqualTo(UPDATED_DATA_ATUAL_VERB);
        assertThat(testArtista.getPossuiBio()).isEqualTo(UPDATED_POSSUI_BIO);
        assertThat(testArtista.getPossuiVerb()).isEqualTo(UPDATED_POSSUI_VERB);
        assertThat(testArtista.getFonteBio()).isEqualTo(UPDATED_FONTE_BIO);
        assertThat(testArtista.getObrasNoAcervo()).isEqualTo(UPDATED_OBRAS_NO_ACERVO);
        assertThat(testArtista.getInativo()).isEqualTo(UPDATED_INATIVO);
        assertThat(testArtista.getAgradecimentos()).isEqualTo(UPDATED_AGRADECIMENTOS);
        assertThat(testArtista.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testArtista.getObsUso()).isEqualTo(UPDATED_OBS_USO);
    }

    @Test
    @Transactional
    void putNonExistingArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtistaWithPatch() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();

        // Update the artista using partial update
        Artista partialUpdatedArtista = new Artista();
        partialUpdatedArtista.setId(artista.getId());

        partialUpdatedArtista
            .nome(UPDATED_NOME)
            .dataNasc(UPDATED_DATA_NASC)
            .dataFalec(UPDATED_DATA_FALEC)
            .verbete(UPDATED_VERBETE)
            .dataAtualBio(UPDATED_DATA_ATUAL_BIO)
            .possuiBio(UPDATED_POSSUI_BIO)
            .possuiVerb(UPDATED_POSSUI_VERB)
            .fonteBio(UPDATED_FONTE_BIO)
            .obsUso(UPDATED_OBS_USO);

        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtista))
            )
            .andExpect(status().isOk());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
        Artista testArtista = artistaList.get(artistaList.size() - 1);
        assertThat(testArtista.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testArtista.getDataNasc()).isEqualTo(UPDATED_DATA_NASC);
        assertThat(testArtista.getDataFalec()).isEqualTo(UPDATED_DATA_FALEC);
        assertThat(testArtista.getBiografia()).isEqualTo(DEFAULT_BIOGRAFIA);
        assertThat(testArtista.getVerbete()).isEqualTo(UPDATED_VERBETE);
        assertThat(testArtista.getDataAtualBio()).isEqualTo(UPDATED_DATA_ATUAL_BIO);
        assertThat(testArtista.getDataAtualVerb()).isEqualTo(DEFAULT_DATA_ATUAL_VERB);
        assertThat(testArtista.getPossuiBio()).isEqualTo(UPDATED_POSSUI_BIO);
        assertThat(testArtista.getPossuiVerb()).isEqualTo(UPDATED_POSSUI_VERB);
        assertThat(testArtista.getFonteBio()).isEqualTo(UPDATED_FONTE_BIO);
        assertThat(testArtista.getObrasNoAcervo()).isEqualTo(DEFAULT_OBRAS_NO_ACERVO);
        assertThat(testArtista.getInativo()).isEqualTo(DEFAULT_INATIVO);
        assertThat(testArtista.getAgradecimentos()).isEqualTo(DEFAULT_AGRADECIMENTOS);
        assertThat(testArtista.getCopyright()).isEqualTo(DEFAULT_COPYRIGHT);
        assertThat(testArtista.getObsUso()).isEqualTo(UPDATED_OBS_USO);
    }

    @Test
    @Transactional
    void fullUpdateArtistaWithPatch() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();

        // Update the artista using partial update
        Artista partialUpdatedArtista = new Artista();
        partialUpdatedArtista.setId(artista.getId());

        partialUpdatedArtista
            .nome(UPDATED_NOME)
            .dataNasc(UPDATED_DATA_NASC)
            .dataFalec(UPDATED_DATA_FALEC)
            .biografia(UPDATED_BIOGRAFIA)
            .verbete(UPDATED_VERBETE)
            .dataAtualBio(UPDATED_DATA_ATUAL_BIO)
            .dataAtualVerb(UPDATED_DATA_ATUAL_VERB)
            .possuiBio(UPDATED_POSSUI_BIO)
            .possuiVerb(UPDATED_POSSUI_VERB)
            .fonteBio(UPDATED_FONTE_BIO)
            .obrasNoAcervo(UPDATED_OBRAS_NO_ACERVO)
            .inativo(UPDATED_INATIVO)
            .agradecimentos(UPDATED_AGRADECIMENTOS)
            .copyright(UPDATED_COPYRIGHT)
            .obsUso(UPDATED_OBS_USO);

        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtista))
            )
            .andExpect(status().isOk());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
        Artista testArtista = artistaList.get(artistaList.size() - 1);
        assertThat(testArtista.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testArtista.getDataNasc()).isEqualTo(UPDATED_DATA_NASC);
        assertThat(testArtista.getDataFalec()).isEqualTo(UPDATED_DATA_FALEC);
        assertThat(testArtista.getBiografia()).isEqualTo(UPDATED_BIOGRAFIA);
        assertThat(testArtista.getVerbete()).isEqualTo(UPDATED_VERBETE);
        assertThat(testArtista.getDataAtualBio()).isEqualTo(UPDATED_DATA_ATUAL_BIO);
        assertThat(testArtista.getDataAtualVerb()).isEqualTo(UPDATED_DATA_ATUAL_VERB);
        assertThat(testArtista.getPossuiBio()).isEqualTo(UPDATED_POSSUI_BIO);
        assertThat(testArtista.getPossuiVerb()).isEqualTo(UPDATED_POSSUI_VERB);
        assertThat(testArtista.getFonteBio()).isEqualTo(UPDATED_FONTE_BIO);
        assertThat(testArtista.getObrasNoAcervo()).isEqualTo(UPDATED_OBRAS_NO_ACERVO);
        assertThat(testArtista.getInativo()).isEqualTo(UPDATED_INATIVO);
        assertThat(testArtista.getAgradecimentos()).isEqualTo(UPDATED_AGRADECIMENTOS);
        assertThat(testArtista.getCopyright()).isEqualTo(UPDATED_COPYRIGHT);
        assertThat(testArtista.getObsUso()).isEqualTo(UPDATED_OBS_USO);
    }

    @Test
    @Transactional
    void patchNonExistingArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artistaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtista() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        int databaseSizeBeforeDelete = artistaRepository.findAll().size();

        // Delete the artista
        restArtistaMockMvc
            .perform(delete(ENTITY_API_URL_ID, artista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
