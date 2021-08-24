package br.com.nhw.std.artes.web.rest;

import static br.com.nhw.std.artes.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.nhw.std.artes.IntegrationTest;
import br.com.nhw.std.artes.domain.AcervoAtual;
import br.com.nhw.std.artes.domain.AndarMapa;
import br.com.nhw.std.artes.domain.Artista;
import br.com.nhw.std.artes.domain.Categoria;
import br.com.nhw.std.artes.domain.Contato;
import br.com.nhw.std.artes.domain.DadoDocumental;
import br.com.nhw.std.artes.domain.Data;
import br.com.nhw.std.artes.domain.Empresa;
import br.com.nhw.std.artes.domain.Moeda;
import br.com.nhw.std.artes.domain.Nivel;
import br.com.nhw.std.artes.domain.Obra;
import br.com.nhw.std.artes.domain.Responsavel;
import br.com.nhw.std.artes.domain.Seguro;
import br.com.nhw.std.artes.domain.Tecnica;
import br.com.nhw.std.artes.repository.ObraRepository;
import br.com.nhw.std.artes.service.ObraService;
import br.com.nhw.std.artes.service.criteria.ObraCriteria;
import br.com.nhw.std.artes.service.dto.ObraDTO;
import br.com.nhw.std.artes.service.mapper.ObraMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link ObraResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ObraResourceIT {

    private static final String DEFAULT_TOMBO = "AAAAAAAA";
    private static final String UPDATED_TOMBO = "BBBBBBBB";

    private static final String DEFAULT_MULTIPLO = "A";
    private static final String UPDATED_MULTIPLO = "B";

    private static final String DEFAULT_NUMERO_REGISTRO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_REGISTRO = "BBBBBBBBBB";

    private static final String DEFAULT_OUTROS_TITULOS = "AAAAAAAAAA";
    private static final String UPDATED_OUTROS_TITULOS = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_ORIGINAL = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_ORIGINAL = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGEM = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ASSINATURA = false;
    private static final Boolean UPDATED_ASSINATURA = true;

    private static final String DEFAULT_LOCAL_ASSINATURA = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_ASSINATURA = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA_INSCR_OBRA = "AAAAAAAAAA";
    private static final String UPDATED_MARCA_INSCR_OBRA = "BBBBBBBBBB";

    private static final String DEFAULT_MARCA_INSCR_SUPORTE = "AAAAAAAAAA";
    private static final String UPDATED_MARCA_INSCR_SUPORTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MEDIDAS_APROX = false;
    private static final Boolean UPDATED_MEDIDAS_APROX = true;

    private static final BigDecimal DEFAULT_ALTURA_OBRA = new BigDecimal(1);
    private static final BigDecimal UPDATED_ALTURA_OBRA = new BigDecimal(2);
    private static final BigDecimal SMALLER_ALTURA_OBRA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LARG_OBRA = new BigDecimal(1);
    private static final BigDecimal UPDATED_LARG_OBRA = new BigDecimal(2);
    private static final BigDecimal SMALLER_LARG_OBRA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PROF_OBRA = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROF_OBRA = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROF_OBRA = new BigDecimal(1 - 1);

    private static final String DEFAULT_DIAMETR_OBRA = "AAAAAA";
    private static final String UPDATED_DIAMETR_OBRA = "BBBBBB";

    private static final BigDecimal DEFAULT_ALTURA_MOLD = new BigDecimal(1);
    private static final BigDecimal UPDATED_ALTURA_MOLD = new BigDecimal(2);
    private static final BigDecimal SMALLER_ALTURA_MOLD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LARG_MOLD = new BigDecimal(1);
    private static final BigDecimal UPDATED_LARG_MOLD = new BigDecimal(2);
    private static final BigDecimal SMALLER_LARG_MOLD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PROF_MOLD = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROF_MOLD = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROF_MOLD = new BigDecimal(1 - 1);

    private static final String DEFAULT_DIAMETRO_MOLD = "AAAAAAAAAA";
    private static final String UPDATED_DIAMETRO_MOLD = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENS_ADIC = "AAAAAAAAAA";
    private static final String UPDATED_DIMENS_ADIC = "BBBBBBBBBB";

    private static final String DEFAULT_PES_OBRA = "AAAAAAAAAA";
    private static final String UPDATED_PES_OBRA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATRIBUIDO = false;
    private static final Boolean UPDATED_ATRIBUIDO = true;

    private static final String DEFAULT_N_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_N_FOTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONJUNTO = false;
    private static final Boolean UPDATED_CONJUNTO = true;

    private static final String DEFAULT_CONJ_TOMBO = "AAAAAAAAAA";
    private static final String UPDATED_CONJ_TOMBO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR_SEGURO = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SEGURO = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_SEGURO = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_VALOR_SEGURO_REAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_SEGURO_REAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR_SEGURO_REAL = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_DATACONVERSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATACONVERSAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATACONVERSAO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_ALTER_APOLICE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ALTER_APOLICE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_ALTER_APOLICE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_LOCAL_ATUAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_ATUAL = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_ATUAL_NOVO = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_ATUAL_NOVO = "BBBBBBBBBB";

    private static final String DEFAULT_COD_PARAMETRO = "AAAAAAAAAA";
    private static final String UPDATED_COD_PARAMETRO = "BBBBBBBBBB";

    private static final String DEFAULT_OBS = "AAAAAAAAAA";
    private static final String UPDATED_OBS = "BBBBBBBBBB";

    private static final String DEFAULT_TIRAGEM = "AAAAAAAAAA";
    private static final String UPDATED_TIRAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SELO = 1;
    private static final Integer UPDATED_SELO = 2;
    private static final Integer SMALLER_SELO = 1 - 1;

    private static final String DEFAULT_TOMBO_ANTERIO = "AAAAAAAAAA";
    private static final String UPDATED_TOMBO_ANTERIO = "BBBBBBBBBB";

    private static final String DEFAULT_VERBETE = "AAAAAAAAAA";
    private static final String UPDATED_VERBETE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LIVRO = false;
    private static final Boolean UPDATED_LIVRO = true;

    private static final Boolean DEFAULT_IMAGEM_ALTA = false;
    private static final Boolean UPDATED_IMAGEM_ALTA = true;

    private static final Boolean DEFAULT_APABEX = false;
    private static final Boolean UPDATED_APABEX = true;

    private static final Boolean DEFAULT_BUNKYO = false;
    private static final Boolean UPDATED_BUNKYO = true;

    private static final String DEFAULT_FASE_DEPURACAO = "AAAAAAAAAA";
    private static final String UPDATED_FASE_DEPURACAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PARA_AVALIACAO = false;
    private static final Boolean UPDATED_PARA_AVALIACAO = true;

    private static final Boolean DEFAULT_PARA_RESTAURACAO = false;
    private static final Boolean UPDATED_PARA_RESTAURACAO = true;

    private static final Boolean DEFAULT_PARA_MOLDURA = false;
    private static final Boolean UPDATED_PARA_MOLDURA = true;

    private static final Boolean DEFAULT_PARA_LEGENDA = false;
    private static final Boolean UPDATED_PARA_LEGENDA = true;

    private static final Boolean DEFAULT_TEMP_FIELD = false;
    private static final Boolean UPDATED_TEMP_FIELD = true;

    private static final Boolean DEFAULT_SELECAO_LISTA_AVULSA = false;
    private static final Boolean UPDATED_SELECAO_LISTA_AVULSA = true;

    private static final Boolean DEFAULT_DOMINIO_PUBL = false;
    private static final Boolean UPDATED_DOMINIO_PUBL = true;

    private static final LocalDate DEFAULT_DT_VENC_FOTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_VENC_FOTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DT_VENC_FOTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_OBS_USO_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_OBS_USO_FOTO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_FOTO_ALTA = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_FOTO_ALTA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_INCLUSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INCLUSAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_INCLUSAO = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_EXCLUSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_EXCLUSAO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_EXCLUSAO = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_BOOK_X = 1;
    private static final Integer UPDATED_BOOK_X = 2;
    private static final Integer SMALLER_BOOK_X = 1 - 1;

    private static final Integer DEFAULT_BOOK_Y = 1;
    private static final Integer UPDATED_BOOK_Y = 2;
    private static final Integer SMALLER_BOOK_Y = 1 - 1;

    private static final Integer DEFAULT_GEN_DESCRICAO = 1;
    private static final Integer UPDATED_GEN_DESCRICAO = 2;
    private static final Integer SMALLER_GEN_DESCRICAO = 1 - 1;

    private static final Integer DEFAULT_GEN_FIELD_1 = 1;
    private static final Integer UPDATED_GEN_FIELD_1 = 2;
    private static final Integer SMALLER_GEN_FIELD_1 = 1 - 1;

    private static final Boolean DEFAULT_PARA_FOTOGRAFIA = false;
    private static final Boolean UPDATED_PARA_FOTOGRAFIA = true;

    private static final Integer DEFAULT_GEN_MARCA_INSCR_OBRA = 1;
    private static final Integer UPDATED_GEN_MARCA_INSCR_OBRA = 2;
    private static final Integer SMALLER_GEN_MARCA_INSCR_OBRA = 1 - 1;

    private static final String DEFAULT_PALAVRAS_CHAVE = "AAAAAAAAAA";
    private static final String UPDATED_PALAVRAS_CHAVE = "BBBBBBBBBB";

    private static final Integer DEFAULT_GEN_MARCA_INSCR_SUPORTE = 1;
    private static final Integer UPDATED_GEN_MARCA_INSCR_SUPORTE = 2;
    private static final Integer SMALLER_GEN_MARCA_INSCR_SUPORTE = 1 - 1;

    private static final Integer DEFAULT_GEN_OBS = 1;
    private static final Integer UPDATED_GEN_OBS = 2;
    private static final Integer SMALLER_GEN_OBS = 1 - 1;

    private static final Integer DEFAULT_GEN_VERBETE = 1;
    private static final Integer UPDATED_GEN_VERBETE = 2;
    private static final Integer SMALLER_GEN_VERBETE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/obras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObraRepository obraRepository;

    @Mock
    private ObraRepository obraRepositoryMock;

    @Autowired
    private ObraMapper obraMapper;

    @Mock
    private ObraService obraServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObraMockMvc;

    private Obra obra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Obra createEntity(EntityManager em) {
        Obra obra = new Obra()
            .tombo(DEFAULT_TOMBO)
            .multiplo(DEFAULT_MULTIPLO)
            .numeroRegistro(DEFAULT_NUMERO_REGISTRO)
            .outrosTitulos(DEFAULT_OUTROS_TITULOS)
            .tituloOriginal(DEFAULT_TITULO_ORIGINAL)
            .origem(DEFAULT_ORIGEM)
            .descricao(DEFAULT_DESCRICAO)
            .assinatura(DEFAULT_ASSINATURA)
            .localAssinatura(DEFAULT_LOCAL_ASSINATURA)
            .marcaInscrObra(DEFAULT_MARCA_INSCR_OBRA)
            .marcaInscrSuporte(DEFAULT_MARCA_INSCR_SUPORTE)
            .medidasAprox(DEFAULT_MEDIDAS_APROX)
            .alturaObra(DEFAULT_ALTURA_OBRA)
            .largObra(DEFAULT_LARG_OBRA)
            .profObra(DEFAULT_PROF_OBRA)
            .diametrObra(DEFAULT_DIAMETR_OBRA)
            .alturaMold(DEFAULT_ALTURA_MOLD)
            .largMold(DEFAULT_LARG_MOLD)
            .profMold(DEFAULT_PROF_MOLD)
            .diametroMold(DEFAULT_DIAMETRO_MOLD)
            .dimensAdic(DEFAULT_DIMENS_ADIC)
            .pesObra(DEFAULT_PES_OBRA)
            .atribuido(DEFAULT_ATRIBUIDO)
            .nFoto(DEFAULT_N_FOTO)
            .conjunto(DEFAULT_CONJUNTO)
            .conjTombo(DEFAULT_CONJ_TOMBO)
            .valorSeguro(DEFAULT_VALOR_SEGURO)
            .valorSeguroReal(DEFAULT_VALOR_SEGURO_REAL)
            .dataconversao(DEFAULT_DATACONVERSAO)
            .dataAlterApolice(DEFAULT_DATA_ALTER_APOLICE)
            .localAtual(DEFAULT_LOCAL_ATUAL)
            .localAtualNovo(DEFAULT_LOCAL_ATUAL_NOVO)
            .codParametro(DEFAULT_COD_PARAMETRO)
            .obs(DEFAULT_OBS)
            .tiragem(DEFAULT_TIRAGEM)
            .serie(DEFAULT_SERIE)
            .selo(DEFAULT_SELO)
            .tomboAnterio(DEFAULT_TOMBO_ANTERIO)
            .verbete(DEFAULT_VERBETE)
            .livro(DEFAULT_LIVRO)
            .imagemAlta(DEFAULT_IMAGEM_ALTA)
            .apabex(DEFAULT_APABEX)
            .bunkyo(DEFAULT_BUNKYO)
            .faseDepuracao(DEFAULT_FASE_DEPURACAO)
            .paraAvaliacao(DEFAULT_PARA_AVALIACAO)
            .paraRestauracao(DEFAULT_PARA_RESTAURACAO)
            .paraMoldura(DEFAULT_PARA_MOLDURA)
            .paraLegenda(DEFAULT_PARA_LEGENDA)
            .tempField(DEFAULT_TEMP_FIELD)
            .selecaoListaAvulsa(DEFAULT_SELECAO_LISTA_AVULSA)
            .dominioPubl(DEFAULT_DOMINIO_PUBL)
            .dtVencFoto(DEFAULT_DT_VENC_FOTO)
            .obsUsoFoto(DEFAULT_OBS_USO_FOTO)
            .localFotoAlta(DEFAULT_LOCAL_FOTO_ALTA)
            .dataInclusao(DEFAULT_DATA_INCLUSAO)
            .dataExclusao(DEFAULT_DATA_EXCLUSAO)
            .bookX(DEFAULT_BOOK_X)
            .bookY(DEFAULT_BOOK_Y)
            .genDescricao(DEFAULT_GEN_DESCRICAO)
            .genField1(DEFAULT_GEN_FIELD_1)
            .paraFotografia(DEFAULT_PARA_FOTOGRAFIA)
            .genMarcaInscrObra(DEFAULT_GEN_MARCA_INSCR_OBRA)
            .palavrasChave(DEFAULT_PALAVRAS_CHAVE)
            .genMarcaInscrSuporte(DEFAULT_GEN_MARCA_INSCR_SUPORTE)
            .genObs(DEFAULT_GEN_OBS)
            .genVerbete(DEFAULT_GEN_VERBETE);
        return obra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Obra createUpdatedEntity(EntityManager em) {
        Obra obra = new Obra()
            .tombo(UPDATED_TOMBO)
            .multiplo(UPDATED_MULTIPLO)
            .numeroRegistro(UPDATED_NUMERO_REGISTRO)
            .outrosTitulos(UPDATED_OUTROS_TITULOS)
            .tituloOriginal(UPDATED_TITULO_ORIGINAL)
            .origem(UPDATED_ORIGEM)
            .descricao(UPDATED_DESCRICAO)
            .assinatura(UPDATED_ASSINATURA)
            .localAssinatura(UPDATED_LOCAL_ASSINATURA)
            .marcaInscrObra(UPDATED_MARCA_INSCR_OBRA)
            .marcaInscrSuporte(UPDATED_MARCA_INSCR_SUPORTE)
            .medidasAprox(UPDATED_MEDIDAS_APROX)
            .alturaObra(UPDATED_ALTURA_OBRA)
            .largObra(UPDATED_LARG_OBRA)
            .profObra(UPDATED_PROF_OBRA)
            .diametrObra(UPDATED_DIAMETR_OBRA)
            .alturaMold(UPDATED_ALTURA_MOLD)
            .largMold(UPDATED_LARG_MOLD)
            .profMold(UPDATED_PROF_MOLD)
            .diametroMold(UPDATED_DIAMETRO_MOLD)
            .dimensAdic(UPDATED_DIMENS_ADIC)
            .pesObra(UPDATED_PES_OBRA)
            .atribuido(UPDATED_ATRIBUIDO)
            .nFoto(UPDATED_N_FOTO)
            .conjunto(UPDATED_CONJUNTO)
            .conjTombo(UPDATED_CONJ_TOMBO)
            .valorSeguro(UPDATED_VALOR_SEGURO)
            .valorSeguroReal(UPDATED_VALOR_SEGURO_REAL)
            .dataconversao(UPDATED_DATACONVERSAO)
            .dataAlterApolice(UPDATED_DATA_ALTER_APOLICE)
            .localAtual(UPDATED_LOCAL_ATUAL)
            .localAtualNovo(UPDATED_LOCAL_ATUAL_NOVO)
            .codParametro(UPDATED_COD_PARAMETRO)
            .obs(UPDATED_OBS)
            .tiragem(UPDATED_TIRAGEM)
            .serie(UPDATED_SERIE)
            .selo(UPDATED_SELO)
            .tomboAnterio(UPDATED_TOMBO_ANTERIO)
            .verbete(UPDATED_VERBETE)
            .livro(UPDATED_LIVRO)
            .imagemAlta(UPDATED_IMAGEM_ALTA)
            .apabex(UPDATED_APABEX)
            .bunkyo(UPDATED_BUNKYO)
            .faseDepuracao(UPDATED_FASE_DEPURACAO)
            .paraAvaliacao(UPDATED_PARA_AVALIACAO)
            .paraRestauracao(UPDATED_PARA_RESTAURACAO)
            .paraMoldura(UPDATED_PARA_MOLDURA)
            .paraLegenda(UPDATED_PARA_LEGENDA)
            .tempField(UPDATED_TEMP_FIELD)
            .selecaoListaAvulsa(UPDATED_SELECAO_LISTA_AVULSA)
            .dominioPubl(UPDATED_DOMINIO_PUBL)
            .dtVencFoto(UPDATED_DT_VENC_FOTO)
            .obsUsoFoto(UPDATED_OBS_USO_FOTO)
            .localFotoAlta(UPDATED_LOCAL_FOTO_ALTA)
            .dataInclusao(UPDATED_DATA_INCLUSAO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .bookX(UPDATED_BOOK_X)
            .bookY(UPDATED_BOOK_Y)
            .genDescricao(UPDATED_GEN_DESCRICAO)
            .genField1(UPDATED_GEN_FIELD_1)
            .paraFotografia(UPDATED_PARA_FOTOGRAFIA)
            .genMarcaInscrObra(UPDATED_GEN_MARCA_INSCR_OBRA)
            .palavrasChave(UPDATED_PALAVRAS_CHAVE)
            .genMarcaInscrSuporte(UPDATED_GEN_MARCA_INSCR_SUPORTE)
            .genObs(UPDATED_GEN_OBS)
            .genVerbete(UPDATED_GEN_VERBETE);
        return obra;
    }

    @BeforeEach
    public void initTest() {
        obra = createEntity(em);
    }

    @Test
    @Transactional
    void createObra() throws Exception {
        int databaseSizeBeforeCreate = obraRepository.findAll().size();
        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);
        restObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isCreated());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeCreate + 1);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getTombo()).isEqualTo(DEFAULT_TOMBO);
        assertThat(testObra.getMultiplo()).isEqualTo(DEFAULT_MULTIPLO);
        assertThat(testObra.getNumeroRegistro()).isEqualTo(DEFAULT_NUMERO_REGISTRO);
        assertThat(testObra.getOutrosTitulos()).isEqualTo(DEFAULT_OUTROS_TITULOS);
        assertThat(testObra.getTituloOriginal()).isEqualTo(DEFAULT_TITULO_ORIGINAL);
        assertThat(testObra.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
        assertThat(testObra.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testObra.getAssinatura()).isEqualTo(DEFAULT_ASSINATURA);
        assertThat(testObra.getLocalAssinatura()).isEqualTo(DEFAULT_LOCAL_ASSINATURA);
        assertThat(testObra.getMarcaInscrObra()).isEqualTo(DEFAULT_MARCA_INSCR_OBRA);
        assertThat(testObra.getMarcaInscrSuporte()).isEqualTo(DEFAULT_MARCA_INSCR_SUPORTE);
        assertThat(testObra.getMedidasAprox()).isEqualTo(DEFAULT_MEDIDAS_APROX);
        assertThat(testObra.getAlturaObra()).isEqualByComparingTo(DEFAULT_ALTURA_OBRA);
        assertThat(testObra.getLargObra()).isEqualByComparingTo(DEFAULT_LARG_OBRA);
        assertThat(testObra.getProfObra()).isEqualByComparingTo(DEFAULT_PROF_OBRA);
        assertThat(testObra.getDiametrObra()).isEqualTo(DEFAULT_DIAMETR_OBRA);
        assertThat(testObra.getAlturaMold()).isEqualByComparingTo(DEFAULT_ALTURA_MOLD);
        assertThat(testObra.getLargMold()).isEqualByComparingTo(DEFAULT_LARG_MOLD);
        assertThat(testObra.getProfMold()).isEqualByComparingTo(DEFAULT_PROF_MOLD);
        assertThat(testObra.getDiametroMold()).isEqualTo(DEFAULT_DIAMETRO_MOLD);
        assertThat(testObra.getDimensAdic()).isEqualTo(DEFAULT_DIMENS_ADIC);
        assertThat(testObra.getPesObra()).isEqualTo(DEFAULT_PES_OBRA);
        assertThat(testObra.getAtribuido()).isEqualTo(DEFAULT_ATRIBUIDO);
        assertThat(testObra.getnFoto()).isEqualTo(DEFAULT_N_FOTO);
        assertThat(testObra.getConjunto()).isEqualTo(DEFAULT_CONJUNTO);
        assertThat(testObra.getConjTombo()).isEqualTo(DEFAULT_CONJ_TOMBO);
        assertThat(testObra.getValorSeguro()).isEqualByComparingTo(DEFAULT_VALOR_SEGURO);
        assertThat(testObra.getValorSeguroReal()).isEqualByComparingTo(DEFAULT_VALOR_SEGURO_REAL);
        assertThat(testObra.getDataconversao()).isEqualTo(DEFAULT_DATACONVERSAO);
        assertThat(testObra.getDataAlterApolice()).isEqualTo(DEFAULT_DATA_ALTER_APOLICE);
        assertThat(testObra.getLocalAtual()).isEqualTo(DEFAULT_LOCAL_ATUAL);
        assertThat(testObra.getLocalAtualNovo()).isEqualTo(DEFAULT_LOCAL_ATUAL_NOVO);
        assertThat(testObra.getCodParametro()).isEqualTo(DEFAULT_COD_PARAMETRO);
        assertThat(testObra.getObs()).isEqualTo(DEFAULT_OBS);
        assertThat(testObra.getTiragem()).isEqualTo(DEFAULT_TIRAGEM);
        assertThat(testObra.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testObra.getSelo()).isEqualTo(DEFAULT_SELO);
        assertThat(testObra.getTomboAnterio()).isEqualTo(DEFAULT_TOMBO_ANTERIO);
        assertThat(testObra.getVerbete()).isEqualTo(DEFAULT_VERBETE);
        assertThat(testObra.getLivro()).isEqualTo(DEFAULT_LIVRO);
        assertThat(testObra.getImagemAlta()).isEqualTo(DEFAULT_IMAGEM_ALTA);
        assertThat(testObra.getApabex()).isEqualTo(DEFAULT_APABEX);
        assertThat(testObra.getBunkyo()).isEqualTo(DEFAULT_BUNKYO);
        assertThat(testObra.getFaseDepuracao()).isEqualTo(DEFAULT_FASE_DEPURACAO);
        assertThat(testObra.getParaAvaliacao()).isEqualTo(DEFAULT_PARA_AVALIACAO);
        assertThat(testObra.getParaRestauracao()).isEqualTo(DEFAULT_PARA_RESTAURACAO);
        assertThat(testObra.getParaMoldura()).isEqualTo(DEFAULT_PARA_MOLDURA);
        assertThat(testObra.getParaLegenda()).isEqualTo(DEFAULT_PARA_LEGENDA);
        assertThat(testObra.getTempField()).isEqualTo(DEFAULT_TEMP_FIELD);
        assertThat(testObra.getSelecaoListaAvulsa()).isEqualTo(DEFAULT_SELECAO_LISTA_AVULSA);
        assertThat(testObra.getDominioPubl()).isEqualTo(DEFAULT_DOMINIO_PUBL);
        assertThat(testObra.getDtVencFoto()).isEqualTo(DEFAULT_DT_VENC_FOTO);
        assertThat(testObra.getObsUsoFoto()).isEqualTo(DEFAULT_OBS_USO_FOTO);
        assertThat(testObra.getLocalFotoAlta()).isEqualTo(DEFAULT_LOCAL_FOTO_ALTA);
        assertThat(testObra.getDataInclusao()).isEqualTo(DEFAULT_DATA_INCLUSAO);
        assertThat(testObra.getDataExclusao()).isEqualTo(DEFAULT_DATA_EXCLUSAO);
        assertThat(testObra.getBookX()).isEqualTo(DEFAULT_BOOK_X);
        assertThat(testObra.getBookY()).isEqualTo(DEFAULT_BOOK_Y);
        assertThat(testObra.getGenDescricao()).isEqualTo(DEFAULT_GEN_DESCRICAO);
        assertThat(testObra.getGenField1()).isEqualTo(DEFAULT_GEN_FIELD_1);
        assertThat(testObra.getParaFotografia()).isEqualTo(DEFAULT_PARA_FOTOGRAFIA);
        assertThat(testObra.getGenMarcaInscrObra()).isEqualTo(DEFAULT_GEN_MARCA_INSCR_OBRA);
        assertThat(testObra.getPalavrasChave()).isEqualTo(DEFAULT_PALAVRAS_CHAVE);
        assertThat(testObra.getGenMarcaInscrSuporte()).isEqualTo(DEFAULT_GEN_MARCA_INSCR_SUPORTE);
        assertThat(testObra.getGenObs()).isEqualTo(DEFAULT_GEN_OBS);
        assertThat(testObra.getGenVerbete()).isEqualTo(DEFAULT_GEN_VERBETE);
    }

    @Test
    @Transactional
    void createObraWithExistingId() throws Exception {
        // Create the Obra with an existing ID
        obra.setId(1L);
        ObraDTO obraDTO = obraMapper.toDto(obra);

        int databaseSizeBeforeCreate = obraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTomboIsRequired() throws Exception {
        int databaseSizeBeforeTest = obraRepository.findAll().size();
        // set the field null
        obra.setTombo(null);

        // Create the Obra, which fails.
        ObraDTO obraDTO = obraMapper.toDto(obra);

        restObraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isBadRequest());

        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllObras() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obra.getId().intValue())))
            .andExpect(jsonPath("$.[*].tombo").value(hasItem(DEFAULT_TOMBO)))
            .andExpect(jsonPath("$.[*].multiplo").value(hasItem(DEFAULT_MULTIPLO)))
            .andExpect(jsonPath("$.[*].numeroRegistro").value(hasItem(DEFAULT_NUMERO_REGISTRO)))
            .andExpect(jsonPath("$.[*].outrosTitulos").value(hasItem(DEFAULT_OUTROS_TITULOS)))
            .andExpect(jsonPath("$.[*].tituloOriginal").value(hasItem(DEFAULT_TITULO_ORIGINAL)))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].assinatura").value(hasItem(DEFAULT_ASSINATURA.booleanValue())))
            .andExpect(jsonPath("$.[*].localAssinatura").value(hasItem(DEFAULT_LOCAL_ASSINATURA)))
            .andExpect(jsonPath("$.[*].marcaInscrObra").value(hasItem(DEFAULT_MARCA_INSCR_OBRA)))
            .andExpect(jsonPath("$.[*].marcaInscrSuporte").value(hasItem(DEFAULT_MARCA_INSCR_SUPORTE)))
            .andExpect(jsonPath("$.[*].medidasAprox").value(hasItem(DEFAULT_MEDIDAS_APROX.booleanValue())))
            .andExpect(jsonPath("$.[*].alturaObra").value(hasItem(sameNumber(DEFAULT_ALTURA_OBRA))))
            .andExpect(jsonPath("$.[*].largObra").value(hasItem(sameNumber(DEFAULT_LARG_OBRA))))
            .andExpect(jsonPath("$.[*].profObra").value(hasItem(sameNumber(DEFAULT_PROF_OBRA))))
            .andExpect(jsonPath("$.[*].diametrObra").value(hasItem(DEFAULT_DIAMETR_OBRA)))
            .andExpect(jsonPath("$.[*].alturaMold").value(hasItem(sameNumber(DEFAULT_ALTURA_MOLD))))
            .andExpect(jsonPath("$.[*].largMold").value(hasItem(sameNumber(DEFAULT_LARG_MOLD))))
            .andExpect(jsonPath("$.[*].profMold").value(hasItem(sameNumber(DEFAULT_PROF_MOLD))))
            .andExpect(jsonPath("$.[*].diametroMold").value(hasItem(DEFAULT_DIAMETRO_MOLD)))
            .andExpect(jsonPath("$.[*].dimensAdic").value(hasItem(DEFAULT_DIMENS_ADIC)))
            .andExpect(jsonPath("$.[*].pesObra").value(hasItem(DEFAULT_PES_OBRA)))
            .andExpect(jsonPath("$.[*].atribuido").value(hasItem(DEFAULT_ATRIBUIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].nFoto").value(hasItem(DEFAULT_N_FOTO)))
            .andExpect(jsonPath("$.[*].conjunto").value(hasItem(DEFAULT_CONJUNTO.booleanValue())))
            .andExpect(jsonPath("$.[*].conjTombo").value(hasItem(DEFAULT_CONJ_TOMBO)))
            .andExpect(jsonPath("$.[*].valorSeguro").value(hasItem(sameNumber(DEFAULT_VALOR_SEGURO))))
            .andExpect(jsonPath("$.[*].valorSeguroReal").value(hasItem(sameNumber(DEFAULT_VALOR_SEGURO_REAL))))
            .andExpect(jsonPath("$.[*].dataconversao").value(hasItem(DEFAULT_DATACONVERSAO.toString())))
            .andExpect(jsonPath("$.[*].dataAlterApolice").value(hasItem(DEFAULT_DATA_ALTER_APOLICE.toString())))
            .andExpect(jsonPath("$.[*].localAtual").value(hasItem(DEFAULT_LOCAL_ATUAL)))
            .andExpect(jsonPath("$.[*].localAtualNovo").value(hasItem(DEFAULT_LOCAL_ATUAL_NOVO)))
            .andExpect(jsonPath("$.[*].codParametro").value(hasItem(DEFAULT_COD_PARAMETRO)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)))
            .andExpect(jsonPath("$.[*].tiragem").value(hasItem(DEFAULT_TIRAGEM)))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].selo").value(hasItem(DEFAULT_SELO)))
            .andExpect(jsonPath("$.[*].tomboAnterio").value(hasItem(DEFAULT_TOMBO_ANTERIO)))
            .andExpect(jsonPath("$.[*].verbete").value(hasItem(DEFAULT_VERBETE)))
            .andExpect(jsonPath("$.[*].livro").value(hasItem(DEFAULT_LIVRO.booleanValue())))
            .andExpect(jsonPath("$.[*].imagemAlta").value(hasItem(DEFAULT_IMAGEM_ALTA.booleanValue())))
            .andExpect(jsonPath("$.[*].apabex").value(hasItem(DEFAULT_APABEX.booleanValue())))
            .andExpect(jsonPath("$.[*].bunkyo").value(hasItem(DEFAULT_BUNKYO.booleanValue())))
            .andExpect(jsonPath("$.[*].faseDepuracao").value(hasItem(DEFAULT_FASE_DEPURACAO)))
            .andExpect(jsonPath("$.[*].paraAvaliacao").value(hasItem(DEFAULT_PARA_AVALIACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].paraRestauracao").value(hasItem(DEFAULT_PARA_RESTAURACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].paraMoldura").value(hasItem(DEFAULT_PARA_MOLDURA.booleanValue())))
            .andExpect(jsonPath("$.[*].paraLegenda").value(hasItem(DEFAULT_PARA_LEGENDA.booleanValue())))
            .andExpect(jsonPath("$.[*].tempField").value(hasItem(DEFAULT_TEMP_FIELD.booleanValue())))
            .andExpect(jsonPath("$.[*].selecaoListaAvulsa").value(hasItem(DEFAULT_SELECAO_LISTA_AVULSA.booleanValue())))
            .andExpect(jsonPath("$.[*].dominioPubl").value(hasItem(DEFAULT_DOMINIO_PUBL.booleanValue())))
            .andExpect(jsonPath("$.[*].dtVencFoto").value(hasItem(DEFAULT_DT_VENC_FOTO.toString())))
            .andExpect(jsonPath("$.[*].obsUsoFoto").value(hasItem(DEFAULT_OBS_USO_FOTO)))
            .andExpect(jsonPath("$.[*].localFotoAlta").value(hasItem(DEFAULT_LOCAL_FOTO_ALTA)))
            .andExpect(jsonPath("$.[*].dataInclusao").value(hasItem(DEFAULT_DATA_INCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataExclusao").value(hasItem(DEFAULT_DATA_EXCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].bookX").value(hasItem(DEFAULT_BOOK_X)))
            .andExpect(jsonPath("$.[*].bookY").value(hasItem(DEFAULT_BOOK_Y)))
            .andExpect(jsonPath("$.[*].genDescricao").value(hasItem(DEFAULT_GEN_DESCRICAO)))
            .andExpect(jsonPath("$.[*].genField1").value(hasItem(DEFAULT_GEN_FIELD_1)))
            .andExpect(jsonPath("$.[*].paraFotografia").value(hasItem(DEFAULT_PARA_FOTOGRAFIA.booleanValue())))
            .andExpect(jsonPath("$.[*].genMarcaInscrObra").value(hasItem(DEFAULT_GEN_MARCA_INSCR_OBRA)))
            .andExpect(jsonPath("$.[*].palavrasChave").value(hasItem(DEFAULT_PALAVRAS_CHAVE)))
            .andExpect(jsonPath("$.[*].genMarcaInscrSuporte").value(hasItem(DEFAULT_GEN_MARCA_INSCR_SUPORTE)))
            .andExpect(jsonPath("$.[*].genObs").value(hasItem(DEFAULT_GEN_OBS)))
            .andExpect(jsonPath("$.[*].genVerbete").value(hasItem(DEFAULT_GEN_VERBETE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllObrasWithEagerRelationshipsIsEnabled() throws Exception {
        when(obraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restObraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(obraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllObrasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(obraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restObraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(obraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get the obra
        restObraMockMvc
            .perform(get(ENTITY_API_URL_ID, obra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(obra.getId().intValue()))
            .andExpect(jsonPath("$.tombo").value(DEFAULT_TOMBO))
            .andExpect(jsonPath("$.multiplo").value(DEFAULT_MULTIPLO))
            .andExpect(jsonPath("$.numeroRegistro").value(DEFAULT_NUMERO_REGISTRO))
            .andExpect(jsonPath("$.outrosTitulos").value(DEFAULT_OUTROS_TITULOS))
            .andExpect(jsonPath("$.tituloOriginal").value(DEFAULT_TITULO_ORIGINAL))
            .andExpect(jsonPath("$.origem").value(DEFAULT_ORIGEM))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.assinatura").value(DEFAULT_ASSINATURA.booleanValue()))
            .andExpect(jsonPath("$.localAssinatura").value(DEFAULT_LOCAL_ASSINATURA))
            .andExpect(jsonPath("$.marcaInscrObra").value(DEFAULT_MARCA_INSCR_OBRA))
            .andExpect(jsonPath("$.marcaInscrSuporte").value(DEFAULT_MARCA_INSCR_SUPORTE))
            .andExpect(jsonPath("$.medidasAprox").value(DEFAULT_MEDIDAS_APROX.booleanValue()))
            .andExpect(jsonPath("$.alturaObra").value(sameNumber(DEFAULT_ALTURA_OBRA)))
            .andExpect(jsonPath("$.largObra").value(sameNumber(DEFAULT_LARG_OBRA)))
            .andExpect(jsonPath("$.profObra").value(sameNumber(DEFAULT_PROF_OBRA)))
            .andExpect(jsonPath("$.diametrObra").value(DEFAULT_DIAMETR_OBRA))
            .andExpect(jsonPath("$.alturaMold").value(sameNumber(DEFAULT_ALTURA_MOLD)))
            .andExpect(jsonPath("$.largMold").value(sameNumber(DEFAULT_LARG_MOLD)))
            .andExpect(jsonPath("$.profMold").value(sameNumber(DEFAULT_PROF_MOLD)))
            .andExpect(jsonPath("$.diametroMold").value(DEFAULT_DIAMETRO_MOLD))
            .andExpect(jsonPath("$.dimensAdic").value(DEFAULT_DIMENS_ADIC))
            .andExpect(jsonPath("$.pesObra").value(DEFAULT_PES_OBRA))
            .andExpect(jsonPath("$.atribuido").value(DEFAULT_ATRIBUIDO.booleanValue()))
            .andExpect(jsonPath("$.nFoto").value(DEFAULT_N_FOTO))
            .andExpect(jsonPath("$.conjunto").value(DEFAULT_CONJUNTO.booleanValue()))
            .andExpect(jsonPath("$.conjTombo").value(DEFAULT_CONJ_TOMBO))
            .andExpect(jsonPath("$.valorSeguro").value(sameNumber(DEFAULT_VALOR_SEGURO)))
            .andExpect(jsonPath("$.valorSeguroReal").value(sameNumber(DEFAULT_VALOR_SEGURO_REAL)))
            .andExpect(jsonPath("$.dataconversao").value(DEFAULT_DATACONVERSAO.toString()))
            .andExpect(jsonPath("$.dataAlterApolice").value(DEFAULT_DATA_ALTER_APOLICE.toString()))
            .andExpect(jsonPath("$.localAtual").value(DEFAULT_LOCAL_ATUAL))
            .andExpect(jsonPath("$.localAtualNovo").value(DEFAULT_LOCAL_ATUAL_NOVO))
            .andExpect(jsonPath("$.codParametro").value(DEFAULT_COD_PARAMETRO))
            .andExpect(jsonPath("$.obs").value(DEFAULT_OBS))
            .andExpect(jsonPath("$.tiragem").value(DEFAULT_TIRAGEM))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE))
            .andExpect(jsonPath("$.selo").value(DEFAULT_SELO))
            .andExpect(jsonPath("$.tomboAnterio").value(DEFAULT_TOMBO_ANTERIO))
            .andExpect(jsonPath("$.verbete").value(DEFAULT_VERBETE))
            .andExpect(jsonPath("$.livro").value(DEFAULT_LIVRO.booleanValue()))
            .andExpect(jsonPath("$.imagemAlta").value(DEFAULT_IMAGEM_ALTA.booleanValue()))
            .andExpect(jsonPath("$.apabex").value(DEFAULT_APABEX.booleanValue()))
            .andExpect(jsonPath("$.bunkyo").value(DEFAULT_BUNKYO.booleanValue()))
            .andExpect(jsonPath("$.faseDepuracao").value(DEFAULT_FASE_DEPURACAO))
            .andExpect(jsonPath("$.paraAvaliacao").value(DEFAULT_PARA_AVALIACAO.booleanValue()))
            .andExpect(jsonPath("$.paraRestauracao").value(DEFAULT_PARA_RESTAURACAO.booleanValue()))
            .andExpect(jsonPath("$.paraMoldura").value(DEFAULT_PARA_MOLDURA.booleanValue()))
            .andExpect(jsonPath("$.paraLegenda").value(DEFAULT_PARA_LEGENDA.booleanValue()))
            .andExpect(jsonPath("$.tempField").value(DEFAULT_TEMP_FIELD.booleanValue()))
            .andExpect(jsonPath("$.selecaoListaAvulsa").value(DEFAULT_SELECAO_LISTA_AVULSA.booleanValue()))
            .andExpect(jsonPath("$.dominioPubl").value(DEFAULT_DOMINIO_PUBL.booleanValue()))
            .andExpect(jsonPath("$.dtVencFoto").value(DEFAULT_DT_VENC_FOTO.toString()))
            .andExpect(jsonPath("$.obsUsoFoto").value(DEFAULT_OBS_USO_FOTO))
            .andExpect(jsonPath("$.localFotoAlta").value(DEFAULT_LOCAL_FOTO_ALTA))
            .andExpect(jsonPath("$.dataInclusao").value(DEFAULT_DATA_INCLUSAO.toString()))
            .andExpect(jsonPath("$.dataExclusao").value(DEFAULT_DATA_EXCLUSAO.toString()))
            .andExpect(jsonPath("$.bookX").value(DEFAULT_BOOK_X))
            .andExpect(jsonPath("$.bookY").value(DEFAULT_BOOK_Y))
            .andExpect(jsonPath("$.genDescricao").value(DEFAULT_GEN_DESCRICAO))
            .andExpect(jsonPath("$.genField1").value(DEFAULT_GEN_FIELD_1))
            .andExpect(jsonPath("$.paraFotografia").value(DEFAULT_PARA_FOTOGRAFIA.booleanValue()))
            .andExpect(jsonPath("$.genMarcaInscrObra").value(DEFAULT_GEN_MARCA_INSCR_OBRA))
            .andExpect(jsonPath("$.palavrasChave").value(DEFAULT_PALAVRAS_CHAVE))
            .andExpect(jsonPath("$.genMarcaInscrSuporte").value(DEFAULT_GEN_MARCA_INSCR_SUPORTE))
            .andExpect(jsonPath("$.genObs").value(DEFAULT_GEN_OBS))
            .andExpect(jsonPath("$.genVerbete").value(DEFAULT_GEN_VERBETE));
    }

    @Test
    @Transactional
    void getObrasByIdFiltering() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        Long id = obra.getId();

        defaultObraShouldBeFound("id.equals=" + id);
        defaultObraShouldNotBeFound("id.notEquals=" + id);

        defaultObraShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultObraShouldNotBeFound("id.greaterThan=" + id);

        defaultObraShouldBeFound("id.lessThanOrEqual=" + id);
        defaultObraShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllObrasByTomboIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tombo equals to DEFAULT_TOMBO
        defaultObraShouldBeFound("tombo.equals=" + DEFAULT_TOMBO);

        // Get all the obraList where tombo equals to UPDATED_TOMBO
        defaultObraShouldNotBeFound("tombo.equals=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByTomboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tombo not equals to DEFAULT_TOMBO
        defaultObraShouldNotBeFound("tombo.notEquals=" + DEFAULT_TOMBO);

        // Get all the obraList where tombo not equals to UPDATED_TOMBO
        defaultObraShouldBeFound("tombo.notEquals=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByTomboIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tombo in DEFAULT_TOMBO or UPDATED_TOMBO
        defaultObraShouldBeFound("tombo.in=" + DEFAULT_TOMBO + "," + UPDATED_TOMBO);

        // Get all the obraList where tombo equals to UPDATED_TOMBO
        defaultObraShouldNotBeFound("tombo.in=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByTomboIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tombo is not null
        defaultObraShouldBeFound("tombo.specified=true");

        // Get all the obraList where tombo is null
        defaultObraShouldNotBeFound("tombo.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByTomboContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tombo contains DEFAULT_TOMBO
        defaultObraShouldBeFound("tombo.contains=" + DEFAULT_TOMBO);

        // Get all the obraList where tombo contains UPDATED_TOMBO
        defaultObraShouldNotBeFound("tombo.contains=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByTomboNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tombo does not contain DEFAULT_TOMBO
        defaultObraShouldNotBeFound("tombo.doesNotContain=" + DEFAULT_TOMBO);

        // Get all the obraList where tombo does not contain UPDATED_TOMBO
        defaultObraShouldBeFound("tombo.doesNotContain=" + UPDATED_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByMultiploIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where multiplo equals to DEFAULT_MULTIPLO
        defaultObraShouldBeFound("multiplo.equals=" + DEFAULT_MULTIPLO);

        // Get all the obraList where multiplo equals to UPDATED_MULTIPLO
        defaultObraShouldNotBeFound("multiplo.equals=" + UPDATED_MULTIPLO);
    }

    @Test
    @Transactional
    void getAllObrasByMultiploIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where multiplo not equals to DEFAULT_MULTIPLO
        defaultObraShouldNotBeFound("multiplo.notEquals=" + DEFAULT_MULTIPLO);

        // Get all the obraList where multiplo not equals to UPDATED_MULTIPLO
        defaultObraShouldBeFound("multiplo.notEquals=" + UPDATED_MULTIPLO);
    }

    @Test
    @Transactional
    void getAllObrasByMultiploIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where multiplo in DEFAULT_MULTIPLO or UPDATED_MULTIPLO
        defaultObraShouldBeFound("multiplo.in=" + DEFAULT_MULTIPLO + "," + UPDATED_MULTIPLO);

        // Get all the obraList where multiplo equals to UPDATED_MULTIPLO
        defaultObraShouldNotBeFound("multiplo.in=" + UPDATED_MULTIPLO);
    }

    @Test
    @Transactional
    void getAllObrasByMultiploIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where multiplo is not null
        defaultObraShouldBeFound("multiplo.specified=true");

        // Get all the obraList where multiplo is null
        defaultObraShouldNotBeFound("multiplo.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByMultiploContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where multiplo contains DEFAULT_MULTIPLO
        defaultObraShouldBeFound("multiplo.contains=" + DEFAULT_MULTIPLO);

        // Get all the obraList where multiplo contains UPDATED_MULTIPLO
        defaultObraShouldNotBeFound("multiplo.contains=" + UPDATED_MULTIPLO);
    }

    @Test
    @Transactional
    void getAllObrasByMultiploNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where multiplo does not contain DEFAULT_MULTIPLO
        defaultObraShouldNotBeFound("multiplo.doesNotContain=" + DEFAULT_MULTIPLO);

        // Get all the obraList where multiplo does not contain UPDATED_MULTIPLO
        defaultObraShouldBeFound("multiplo.doesNotContain=" + UPDATED_MULTIPLO);
    }

    @Test
    @Transactional
    void getAllObrasByNumeroRegistroIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where numeroRegistro equals to DEFAULT_NUMERO_REGISTRO
        defaultObraShouldBeFound("numeroRegistro.equals=" + DEFAULT_NUMERO_REGISTRO);

        // Get all the obraList where numeroRegistro equals to UPDATED_NUMERO_REGISTRO
        defaultObraShouldNotBeFound("numeroRegistro.equals=" + UPDATED_NUMERO_REGISTRO);
    }

    @Test
    @Transactional
    void getAllObrasByNumeroRegistroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where numeroRegistro not equals to DEFAULT_NUMERO_REGISTRO
        defaultObraShouldNotBeFound("numeroRegistro.notEquals=" + DEFAULT_NUMERO_REGISTRO);

        // Get all the obraList where numeroRegistro not equals to UPDATED_NUMERO_REGISTRO
        defaultObraShouldBeFound("numeroRegistro.notEquals=" + UPDATED_NUMERO_REGISTRO);
    }

    @Test
    @Transactional
    void getAllObrasByNumeroRegistroIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where numeroRegistro in DEFAULT_NUMERO_REGISTRO or UPDATED_NUMERO_REGISTRO
        defaultObraShouldBeFound("numeroRegistro.in=" + DEFAULT_NUMERO_REGISTRO + "," + UPDATED_NUMERO_REGISTRO);

        // Get all the obraList where numeroRegistro equals to UPDATED_NUMERO_REGISTRO
        defaultObraShouldNotBeFound("numeroRegistro.in=" + UPDATED_NUMERO_REGISTRO);
    }

    @Test
    @Transactional
    void getAllObrasByNumeroRegistroIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where numeroRegistro is not null
        defaultObraShouldBeFound("numeroRegistro.specified=true");

        // Get all the obraList where numeroRegistro is null
        defaultObraShouldNotBeFound("numeroRegistro.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByNumeroRegistroContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where numeroRegistro contains DEFAULT_NUMERO_REGISTRO
        defaultObraShouldBeFound("numeroRegistro.contains=" + DEFAULT_NUMERO_REGISTRO);

        // Get all the obraList where numeroRegistro contains UPDATED_NUMERO_REGISTRO
        defaultObraShouldNotBeFound("numeroRegistro.contains=" + UPDATED_NUMERO_REGISTRO);
    }

    @Test
    @Transactional
    void getAllObrasByNumeroRegistroNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where numeroRegistro does not contain DEFAULT_NUMERO_REGISTRO
        defaultObraShouldNotBeFound("numeroRegistro.doesNotContain=" + DEFAULT_NUMERO_REGISTRO);

        // Get all the obraList where numeroRegistro does not contain UPDATED_NUMERO_REGISTRO
        defaultObraShouldBeFound("numeroRegistro.doesNotContain=" + UPDATED_NUMERO_REGISTRO);
    }

    @Test
    @Transactional
    void getAllObrasByOutrosTitulosIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where outrosTitulos equals to DEFAULT_OUTROS_TITULOS
        defaultObraShouldBeFound("outrosTitulos.equals=" + DEFAULT_OUTROS_TITULOS);

        // Get all the obraList where outrosTitulos equals to UPDATED_OUTROS_TITULOS
        defaultObraShouldNotBeFound("outrosTitulos.equals=" + UPDATED_OUTROS_TITULOS);
    }

    @Test
    @Transactional
    void getAllObrasByOutrosTitulosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where outrosTitulos not equals to DEFAULT_OUTROS_TITULOS
        defaultObraShouldNotBeFound("outrosTitulos.notEquals=" + DEFAULT_OUTROS_TITULOS);

        // Get all the obraList where outrosTitulos not equals to UPDATED_OUTROS_TITULOS
        defaultObraShouldBeFound("outrosTitulos.notEquals=" + UPDATED_OUTROS_TITULOS);
    }

    @Test
    @Transactional
    void getAllObrasByOutrosTitulosIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where outrosTitulos in DEFAULT_OUTROS_TITULOS or UPDATED_OUTROS_TITULOS
        defaultObraShouldBeFound("outrosTitulos.in=" + DEFAULT_OUTROS_TITULOS + "," + UPDATED_OUTROS_TITULOS);

        // Get all the obraList where outrosTitulos equals to UPDATED_OUTROS_TITULOS
        defaultObraShouldNotBeFound("outrosTitulos.in=" + UPDATED_OUTROS_TITULOS);
    }

    @Test
    @Transactional
    void getAllObrasByOutrosTitulosIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where outrosTitulos is not null
        defaultObraShouldBeFound("outrosTitulos.specified=true");

        // Get all the obraList where outrosTitulos is null
        defaultObraShouldNotBeFound("outrosTitulos.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByOutrosTitulosContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where outrosTitulos contains DEFAULT_OUTROS_TITULOS
        defaultObraShouldBeFound("outrosTitulos.contains=" + DEFAULT_OUTROS_TITULOS);

        // Get all the obraList where outrosTitulos contains UPDATED_OUTROS_TITULOS
        defaultObraShouldNotBeFound("outrosTitulos.contains=" + UPDATED_OUTROS_TITULOS);
    }

    @Test
    @Transactional
    void getAllObrasByOutrosTitulosNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where outrosTitulos does not contain DEFAULT_OUTROS_TITULOS
        defaultObraShouldNotBeFound("outrosTitulos.doesNotContain=" + DEFAULT_OUTROS_TITULOS);

        // Get all the obraList where outrosTitulos does not contain UPDATED_OUTROS_TITULOS
        defaultObraShouldBeFound("outrosTitulos.doesNotContain=" + UPDATED_OUTROS_TITULOS);
    }

    @Test
    @Transactional
    void getAllObrasByTituloOriginalIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tituloOriginal equals to DEFAULT_TITULO_ORIGINAL
        defaultObraShouldBeFound("tituloOriginal.equals=" + DEFAULT_TITULO_ORIGINAL);

        // Get all the obraList where tituloOriginal equals to UPDATED_TITULO_ORIGINAL
        defaultObraShouldNotBeFound("tituloOriginal.equals=" + UPDATED_TITULO_ORIGINAL);
    }

    @Test
    @Transactional
    void getAllObrasByTituloOriginalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tituloOriginal not equals to DEFAULT_TITULO_ORIGINAL
        defaultObraShouldNotBeFound("tituloOriginal.notEquals=" + DEFAULT_TITULO_ORIGINAL);

        // Get all the obraList where tituloOriginal not equals to UPDATED_TITULO_ORIGINAL
        defaultObraShouldBeFound("tituloOriginal.notEquals=" + UPDATED_TITULO_ORIGINAL);
    }

    @Test
    @Transactional
    void getAllObrasByTituloOriginalIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tituloOriginal in DEFAULT_TITULO_ORIGINAL or UPDATED_TITULO_ORIGINAL
        defaultObraShouldBeFound("tituloOriginal.in=" + DEFAULT_TITULO_ORIGINAL + "," + UPDATED_TITULO_ORIGINAL);

        // Get all the obraList where tituloOriginal equals to UPDATED_TITULO_ORIGINAL
        defaultObraShouldNotBeFound("tituloOriginal.in=" + UPDATED_TITULO_ORIGINAL);
    }

    @Test
    @Transactional
    void getAllObrasByTituloOriginalIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tituloOriginal is not null
        defaultObraShouldBeFound("tituloOriginal.specified=true");

        // Get all the obraList where tituloOriginal is null
        defaultObraShouldNotBeFound("tituloOriginal.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByTituloOriginalContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tituloOriginal contains DEFAULT_TITULO_ORIGINAL
        defaultObraShouldBeFound("tituloOriginal.contains=" + DEFAULT_TITULO_ORIGINAL);

        // Get all the obraList where tituloOriginal contains UPDATED_TITULO_ORIGINAL
        defaultObraShouldNotBeFound("tituloOriginal.contains=" + UPDATED_TITULO_ORIGINAL);
    }

    @Test
    @Transactional
    void getAllObrasByTituloOriginalNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tituloOriginal does not contain DEFAULT_TITULO_ORIGINAL
        defaultObraShouldNotBeFound("tituloOriginal.doesNotContain=" + DEFAULT_TITULO_ORIGINAL);

        // Get all the obraList where tituloOriginal does not contain UPDATED_TITULO_ORIGINAL
        defaultObraShouldBeFound("tituloOriginal.doesNotContain=" + UPDATED_TITULO_ORIGINAL);
    }

    @Test
    @Transactional
    void getAllObrasByOrigemIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where origem equals to DEFAULT_ORIGEM
        defaultObraShouldBeFound("origem.equals=" + DEFAULT_ORIGEM);

        // Get all the obraList where origem equals to UPDATED_ORIGEM
        defaultObraShouldNotBeFound("origem.equals=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllObrasByOrigemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where origem not equals to DEFAULT_ORIGEM
        defaultObraShouldNotBeFound("origem.notEquals=" + DEFAULT_ORIGEM);

        // Get all the obraList where origem not equals to UPDATED_ORIGEM
        defaultObraShouldBeFound("origem.notEquals=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllObrasByOrigemIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where origem in DEFAULT_ORIGEM or UPDATED_ORIGEM
        defaultObraShouldBeFound("origem.in=" + DEFAULT_ORIGEM + "," + UPDATED_ORIGEM);

        // Get all the obraList where origem equals to UPDATED_ORIGEM
        defaultObraShouldNotBeFound("origem.in=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllObrasByOrigemIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where origem is not null
        defaultObraShouldBeFound("origem.specified=true");

        // Get all the obraList where origem is null
        defaultObraShouldNotBeFound("origem.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByOrigemContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where origem contains DEFAULT_ORIGEM
        defaultObraShouldBeFound("origem.contains=" + DEFAULT_ORIGEM);

        // Get all the obraList where origem contains UPDATED_ORIGEM
        defaultObraShouldNotBeFound("origem.contains=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllObrasByOrigemNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where origem does not contain DEFAULT_ORIGEM
        defaultObraShouldNotBeFound("origem.doesNotContain=" + DEFAULT_ORIGEM);

        // Get all the obraList where origem does not contain UPDATED_ORIGEM
        defaultObraShouldBeFound("origem.doesNotContain=" + UPDATED_ORIGEM);
    }

    @Test
    @Transactional
    void getAllObrasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where descricao equals to DEFAULT_DESCRICAO
        defaultObraShouldBeFound("descricao.equals=" + DEFAULT_DESCRICAO);

        // Get all the obraList where descricao equals to UPDATED_DESCRICAO
        defaultObraShouldNotBeFound("descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where descricao not equals to DEFAULT_DESCRICAO
        defaultObraShouldNotBeFound("descricao.notEquals=" + DEFAULT_DESCRICAO);

        // Get all the obraList where descricao not equals to UPDATED_DESCRICAO
        defaultObraShouldBeFound("descricao.notEquals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where descricao in DEFAULT_DESCRICAO or UPDATED_DESCRICAO
        defaultObraShouldBeFound("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO);

        // Get all the obraList where descricao equals to UPDATED_DESCRICAO
        defaultObraShouldNotBeFound("descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where descricao is not null
        defaultObraShouldBeFound("descricao.specified=true");

        // Get all the obraList where descricao is null
        defaultObraShouldNotBeFound("descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where descricao contains DEFAULT_DESCRICAO
        defaultObraShouldBeFound("descricao.contains=" + DEFAULT_DESCRICAO);

        // Get all the obraList where descricao contains UPDATED_DESCRICAO
        defaultObraShouldNotBeFound("descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where descricao does not contain DEFAULT_DESCRICAO
        defaultObraShouldNotBeFound("descricao.doesNotContain=" + DEFAULT_DESCRICAO);

        // Get all the obraList where descricao does not contain UPDATED_DESCRICAO
        defaultObraShouldBeFound("descricao.doesNotContain=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByAssinaturaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where assinatura equals to DEFAULT_ASSINATURA
        defaultObraShouldBeFound("assinatura.equals=" + DEFAULT_ASSINATURA);

        // Get all the obraList where assinatura equals to UPDATED_ASSINATURA
        defaultObraShouldNotBeFound("assinatura.equals=" + UPDATED_ASSINATURA);
    }

    @Test
    @Transactional
    void getAllObrasByAssinaturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where assinatura not equals to DEFAULT_ASSINATURA
        defaultObraShouldNotBeFound("assinatura.notEquals=" + DEFAULT_ASSINATURA);

        // Get all the obraList where assinatura not equals to UPDATED_ASSINATURA
        defaultObraShouldBeFound("assinatura.notEquals=" + UPDATED_ASSINATURA);
    }

    @Test
    @Transactional
    void getAllObrasByAssinaturaIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where assinatura in DEFAULT_ASSINATURA or UPDATED_ASSINATURA
        defaultObraShouldBeFound("assinatura.in=" + DEFAULT_ASSINATURA + "," + UPDATED_ASSINATURA);

        // Get all the obraList where assinatura equals to UPDATED_ASSINATURA
        defaultObraShouldNotBeFound("assinatura.in=" + UPDATED_ASSINATURA);
    }

    @Test
    @Transactional
    void getAllObrasByAssinaturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where assinatura is not null
        defaultObraShouldBeFound("assinatura.specified=true");

        // Get all the obraList where assinatura is null
        defaultObraShouldNotBeFound("assinatura.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByLocalAssinaturaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAssinatura equals to DEFAULT_LOCAL_ASSINATURA
        defaultObraShouldBeFound("localAssinatura.equals=" + DEFAULT_LOCAL_ASSINATURA);

        // Get all the obraList where localAssinatura equals to UPDATED_LOCAL_ASSINATURA
        defaultObraShouldNotBeFound("localAssinatura.equals=" + UPDATED_LOCAL_ASSINATURA);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAssinaturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAssinatura not equals to DEFAULT_LOCAL_ASSINATURA
        defaultObraShouldNotBeFound("localAssinatura.notEquals=" + DEFAULT_LOCAL_ASSINATURA);

        // Get all the obraList where localAssinatura not equals to UPDATED_LOCAL_ASSINATURA
        defaultObraShouldBeFound("localAssinatura.notEquals=" + UPDATED_LOCAL_ASSINATURA);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAssinaturaIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAssinatura in DEFAULT_LOCAL_ASSINATURA or UPDATED_LOCAL_ASSINATURA
        defaultObraShouldBeFound("localAssinatura.in=" + DEFAULT_LOCAL_ASSINATURA + "," + UPDATED_LOCAL_ASSINATURA);

        // Get all the obraList where localAssinatura equals to UPDATED_LOCAL_ASSINATURA
        defaultObraShouldNotBeFound("localAssinatura.in=" + UPDATED_LOCAL_ASSINATURA);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAssinaturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAssinatura is not null
        defaultObraShouldBeFound("localAssinatura.specified=true");

        // Get all the obraList where localAssinatura is null
        defaultObraShouldNotBeFound("localAssinatura.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByLocalAssinaturaContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAssinatura contains DEFAULT_LOCAL_ASSINATURA
        defaultObraShouldBeFound("localAssinatura.contains=" + DEFAULT_LOCAL_ASSINATURA);

        // Get all the obraList where localAssinatura contains UPDATED_LOCAL_ASSINATURA
        defaultObraShouldNotBeFound("localAssinatura.contains=" + UPDATED_LOCAL_ASSINATURA);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAssinaturaNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAssinatura does not contain DEFAULT_LOCAL_ASSINATURA
        defaultObraShouldNotBeFound("localAssinatura.doesNotContain=" + DEFAULT_LOCAL_ASSINATURA);

        // Get all the obraList where localAssinatura does not contain UPDATED_LOCAL_ASSINATURA
        defaultObraShouldBeFound("localAssinatura.doesNotContain=" + UPDATED_LOCAL_ASSINATURA);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrObraIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrObra equals to DEFAULT_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("marcaInscrObra.equals=" + DEFAULT_MARCA_INSCR_OBRA);

        // Get all the obraList where marcaInscrObra equals to UPDATED_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("marcaInscrObra.equals=" + UPDATED_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrObraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrObra not equals to DEFAULT_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("marcaInscrObra.notEquals=" + DEFAULT_MARCA_INSCR_OBRA);

        // Get all the obraList where marcaInscrObra not equals to UPDATED_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("marcaInscrObra.notEquals=" + UPDATED_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrObraIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrObra in DEFAULT_MARCA_INSCR_OBRA or UPDATED_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("marcaInscrObra.in=" + DEFAULT_MARCA_INSCR_OBRA + "," + UPDATED_MARCA_INSCR_OBRA);

        // Get all the obraList where marcaInscrObra equals to UPDATED_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("marcaInscrObra.in=" + UPDATED_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrObraIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrObra is not null
        defaultObraShouldBeFound("marcaInscrObra.specified=true");

        // Get all the obraList where marcaInscrObra is null
        defaultObraShouldNotBeFound("marcaInscrObra.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrObraContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrObra contains DEFAULT_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("marcaInscrObra.contains=" + DEFAULT_MARCA_INSCR_OBRA);

        // Get all the obraList where marcaInscrObra contains UPDATED_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("marcaInscrObra.contains=" + UPDATED_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrObraNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrObra does not contain DEFAULT_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("marcaInscrObra.doesNotContain=" + DEFAULT_MARCA_INSCR_OBRA);

        // Get all the obraList where marcaInscrObra does not contain UPDATED_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("marcaInscrObra.doesNotContain=" + UPDATED_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrSuporteIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrSuporte equals to DEFAULT_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("marcaInscrSuporte.equals=" + DEFAULT_MARCA_INSCR_SUPORTE);

        // Get all the obraList where marcaInscrSuporte equals to UPDATED_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("marcaInscrSuporte.equals=" + UPDATED_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrSuporteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrSuporte not equals to DEFAULT_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("marcaInscrSuporte.notEquals=" + DEFAULT_MARCA_INSCR_SUPORTE);

        // Get all the obraList where marcaInscrSuporte not equals to UPDATED_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("marcaInscrSuporte.notEquals=" + UPDATED_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrSuporteIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrSuporte in DEFAULT_MARCA_INSCR_SUPORTE or UPDATED_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("marcaInscrSuporte.in=" + DEFAULT_MARCA_INSCR_SUPORTE + "," + UPDATED_MARCA_INSCR_SUPORTE);

        // Get all the obraList where marcaInscrSuporte equals to UPDATED_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("marcaInscrSuporte.in=" + UPDATED_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrSuporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrSuporte is not null
        defaultObraShouldBeFound("marcaInscrSuporte.specified=true");

        // Get all the obraList where marcaInscrSuporte is null
        defaultObraShouldNotBeFound("marcaInscrSuporte.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrSuporteContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrSuporte contains DEFAULT_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("marcaInscrSuporte.contains=" + DEFAULT_MARCA_INSCR_SUPORTE);

        // Get all the obraList where marcaInscrSuporte contains UPDATED_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("marcaInscrSuporte.contains=" + UPDATED_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByMarcaInscrSuporteNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where marcaInscrSuporte does not contain DEFAULT_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("marcaInscrSuporte.doesNotContain=" + DEFAULT_MARCA_INSCR_SUPORTE);

        // Get all the obraList where marcaInscrSuporte does not contain UPDATED_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("marcaInscrSuporte.doesNotContain=" + UPDATED_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByMedidasAproxIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where medidasAprox equals to DEFAULT_MEDIDAS_APROX
        defaultObraShouldBeFound("medidasAprox.equals=" + DEFAULT_MEDIDAS_APROX);

        // Get all the obraList where medidasAprox equals to UPDATED_MEDIDAS_APROX
        defaultObraShouldNotBeFound("medidasAprox.equals=" + UPDATED_MEDIDAS_APROX);
    }

    @Test
    @Transactional
    void getAllObrasByMedidasAproxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where medidasAprox not equals to DEFAULT_MEDIDAS_APROX
        defaultObraShouldNotBeFound("medidasAprox.notEquals=" + DEFAULT_MEDIDAS_APROX);

        // Get all the obraList where medidasAprox not equals to UPDATED_MEDIDAS_APROX
        defaultObraShouldBeFound("medidasAprox.notEquals=" + UPDATED_MEDIDAS_APROX);
    }

    @Test
    @Transactional
    void getAllObrasByMedidasAproxIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where medidasAprox in DEFAULT_MEDIDAS_APROX or UPDATED_MEDIDAS_APROX
        defaultObraShouldBeFound("medidasAprox.in=" + DEFAULT_MEDIDAS_APROX + "," + UPDATED_MEDIDAS_APROX);

        // Get all the obraList where medidasAprox equals to UPDATED_MEDIDAS_APROX
        defaultObraShouldNotBeFound("medidasAprox.in=" + UPDATED_MEDIDAS_APROX);
    }

    @Test
    @Transactional
    void getAllObrasByMedidasAproxIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where medidasAprox is not null
        defaultObraShouldBeFound("medidasAprox.specified=true");

        // Get all the obraList where medidasAprox is null
        defaultObraShouldNotBeFound("medidasAprox.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByAlturaObraIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaObra equals to DEFAULT_ALTURA_OBRA
        defaultObraShouldBeFound("alturaObra.equals=" + DEFAULT_ALTURA_OBRA);

        // Get all the obraList where alturaObra equals to UPDATED_ALTURA_OBRA
        defaultObraShouldNotBeFound("alturaObra.equals=" + UPDATED_ALTURA_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaObraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaObra not equals to DEFAULT_ALTURA_OBRA
        defaultObraShouldNotBeFound("alturaObra.notEquals=" + DEFAULT_ALTURA_OBRA);

        // Get all the obraList where alturaObra not equals to UPDATED_ALTURA_OBRA
        defaultObraShouldBeFound("alturaObra.notEquals=" + UPDATED_ALTURA_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaObraIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaObra in DEFAULT_ALTURA_OBRA or UPDATED_ALTURA_OBRA
        defaultObraShouldBeFound("alturaObra.in=" + DEFAULT_ALTURA_OBRA + "," + UPDATED_ALTURA_OBRA);

        // Get all the obraList where alturaObra equals to UPDATED_ALTURA_OBRA
        defaultObraShouldNotBeFound("alturaObra.in=" + UPDATED_ALTURA_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaObraIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaObra is not null
        defaultObraShouldBeFound("alturaObra.specified=true");

        // Get all the obraList where alturaObra is null
        defaultObraShouldNotBeFound("alturaObra.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByAlturaObraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaObra is greater than or equal to DEFAULT_ALTURA_OBRA
        defaultObraShouldBeFound("alturaObra.greaterThanOrEqual=" + DEFAULT_ALTURA_OBRA);

        // Get all the obraList where alturaObra is greater than or equal to UPDATED_ALTURA_OBRA
        defaultObraShouldNotBeFound("alturaObra.greaterThanOrEqual=" + UPDATED_ALTURA_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaObraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaObra is less than or equal to DEFAULT_ALTURA_OBRA
        defaultObraShouldBeFound("alturaObra.lessThanOrEqual=" + DEFAULT_ALTURA_OBRA);

        // Get all the obraList where alturaObra is less than or equal to SMALLER_ALTURA_OBRA
        defaultObraShouldNotBeFound("alturaObra.lessThanOrEqual=" + SMALLER_ALTURA_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaObraIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaObra is less than DEFAULT_ALTURA_OBRA
        defaultObraShouldNotBeFound("alturaObra.lessThan=" + DEFAULT_ALTURA_OBRA);

        // Get all the obraList where alturaObra is less than UPDATED_ALTURA_OBRA
        defaultObraShouldBeFound("alturaObra.lessThan=" + UPDATED_ALTURA_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaObraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaObra is greater than DEFAULT_ALTURA_OBRA
        defaultObraShouldNotBeFound("alturaObra.greaterThan=" + DEFAULT_ALTURA_OBRA);

        // Get all the obraList where alturaObra is greater than SMALLER_ALTURA_OBRA
        defaultObraShouldBeFound("alturaObra.greaterThan=" + SMALLER_ALTURA_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByLargObraIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largObra equals to DEFAULT_LARG_OBRA
        defaultObraShouldBeFound("largObra.equals=" + DEFAULT_LARG_OBRA);

        // Get all the obraList where largObra equals to UPDATED_LARG_OBRA
        defaultObraShouldNotBeFound("largObra.equals=" + UPDATED_LARG_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByLargObraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largObra not equals to DEFAULT_LARG_OBRA
        defaultObraShouldNotBeFound("largObra.notEquals=" + DEFAULT_LARG_OBRA);

        // Get all the obraList where largObra not equals to UPDATED_LARG_OBRA
        defaultObraShouldBeFound("largObra.notEquals=" + UPDATED_LARG_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByLargObraIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largObra in DEFAULT_LARG_OBRA or UPDATED_LARG_OBRA
        defaultObraShouldBeFound("largObra.in=" + DEFAULT_LARG_OBRA + "," + UPDATED_LARG_OBRA);

        // Get all the obraList where largObra equals to UPDATED_LARG_OBRA
        defaultObraShouldNotBeFound("largObra.in=" + UPDATED_LARG_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByLargObraIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largObra is not null
        defaultObraShouldBeFound("largObra.specified=true");

        // Get all the obraList where largObra is null
        defaultObraShouldNotBeFound("largObra.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByLargObraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largObra is greater than or equal to DEFAULT_LARG_OBRA
        defaultObraShouldBeFound("largObra.greaterThanOrEqual=" + DEFAULT_LARG_OBRA);

        // Get all the obraList where largObra is greater than or equal to UPDATED_LARG_OBRA
        defaultObraShouldNotBeFound("largObra.greaterThanOrEqual=" + UPDATED_LARG_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByLargObraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largObra is less than or equal to DEFAULT_LARG_OBRA
        defaultObraShouldBeFound("largObra.lessThanOrEqual=" + DEFAULT_LARG_OBRA);

        // Get all the obraList where largObra is less than or equal to SMALLER_LARG_OBRA
        defaultObraShouldNotBeFound("largObra.lessThanOrEqual=" + SMALLER_LARG_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByLargObraIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largObra is less than DEFAULT_LARG_OBRA
        defaultObraShouldNotBeFound("largObra.lessThan=" + DEFAULT_LARG_OBRA);

        // Get all the obraList where largObra is less than UPDATED_LARG_OBRA
        defaultObraShouldBeFound("largObra.lessThan=" + UPDATED_LARG_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByLargObraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largObra is greater than DEFAULT_LARG_OBRA
        defaultObraShouldNotBeFound("largObra.greaterThan=" + DEFAULT_LARG_OBRA);

        // Get all the obraList where largObra is greater than SMALLER_LARG_OBRA
        defaultObraShouldBeFound("largObra.greaterThan=" + SMALLER_LARG_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByProfObraIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profObra equals to DEFAULT_PROF_OBRA
        defaultObraShouldBeFound("profObra.equals=" + DEFAULT_PROF_OBRA);

        // Get all the obraList where profObra equals to UPDATED_PROF_OBRA
        defaultObraShouldNotBeFound("profObra.equals=" + UPDATED_PROF_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByProfObraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profObra not equals to DEFAULT_PROF_OBRA
        defaultObraShouldNotBeFound("profObra.notEquals=" + DEFAULT_PROF_OBRA);

        // Get all the obraList where profObra not equals to UPDATED_PROF_OBRA
        defaultObraShouldBeFound("profObra.notEquals=" + UPDATED_PROF_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByProfObraIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profObra in DEFAULT_PROF_OBRA or UPDATED_PROF_OBRA
        defaultObraShouldBeFound("profObra.in=" + DEFAULT_PROF_OBRA + "," + UPDATED_PROF_OBRA);

        // Get all the obraList where profObra equals to UPDATED_PROF_OBRA
        defaultObraShouldNotBeFound("profObra.in=" + UPDATED_PROF_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByProfObraIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profObra is not null
        defaultObraShouldBeFound("profObra.specified=true");

        // Get all the obraList where profObra is null
        defaultObraShouldNotBeFound("profObra.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByProfObraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profObra is greater than or equal to DEFAULT_PROF_OBRA
        defaultObraShouldBeFound("profObra.greaterThanOrEqual=" + DEFAULT_PROF_OBRA);

        // Get all the obraList where profObra is greater than or equal to UPDATED_PROF_OBRA
        defaultObraShouldNotBeFound("profObra.greaterThanOrEqual=" + UPDATED_PROF_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByProfObraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profObra is less than or equal to DEFAULT_PROF_OBRA
        defaultObraShouldBeFound("profObra.lessThanOrEqual=" + DEFAULT_PROF_OBRA);

        // Get all the obraList where profObra is less than or equal to SMALLER_PROF_OBRA
        defaultObraShouldNotBeFound("profObra.lessThanOrEqual=" + SMALLER_PROF_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByProfObraIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profObra is less than DEFAULT_PROF_OBRA
        defaultObraShouldNotBeFound("profObra.lessThan=" + DEFAULT_PROF_OBRA);

        // Get all the obraList where profObra is less than UPDATED_PROF_OBRA
        defaultObraShouldBeFound("profObra.lessThan=" + UPDATED_PROF_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByProfObraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profObra is greater than DEFAULT_PROF_OBRA
        defaultObraShouldNotBeFound("profObra.greaterThan=" + DEFAULT_PROF_OBRA);

        // Get all the obraList where profObra is greater than SMALLER_PROF_OBRA
        defaultObraShouldBeFound("profObra.greaterThan=" + SMALLER_PROF_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByDiametrObraIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametrObra equals to DEFAULT_DIAMETR_OBRA
        defaultObraShouldBeFound("diametrObra.equals=" + DEFAULT_DIAMETR_OBRA);

        // Get all the obraList where diametrObra equals to UPDATED_DIAMETR_OBRA
        defaultObraShouldNotBeFound("diametrObra.equals=" + UPDATED_DIAMETR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByDiametrObraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametrObra not equals to DEFAULT_DIAMETR_OBRA
        defaultObraShouldNotBeFound("diametrObra.notEquals=" + DEFAULT_DIAMETR_OBRA);

        // Get all the obraList where diametrObra not equals to UPDATED_DIAMETR_OBRA
        defaultObraShouldBeFound("diametrObra.notEquals=" + UPDATED_DIAMETR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByDiametrObraIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametrObra in DEFAULT_DIAMETR_OBRA or UPDATED_DIAMETR_OBRA
        defaultObraShouldBeFound("diametrObra.in=" + DEFAULT_DIAMETR_OBRA + "," + UPDATED_DIAMETR_OBRA);

        // Get all the obraList where diametrObra equals to UPDATED_DIAMETR_OBRA
        defaultObraShouldNotBeFound("diametrObra.in=" + UPDATED_DIAMETR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByDiametrObraIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametrObra is not null
        defaultObraShouldBeFound("diametrObra.specified=true");

        // Get all the obraList where diametrObra is null
        defaultObraShouldNotBeFound("diametrObra.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDiametrObraContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametrObra contains DEFAULT_DIAMETR_OBRA
        defaultObraShouldBeFound("diametrObra.contains=" + DEFAULT_DIAMETR_OBRA);

        // Get all the obraList where diametrObra contains UPDATED_DIAMETR_OBRA
        defaultObraShouldNotBeFound("diametrObra.contains=" + UPDATED_DIAMETR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByDiametrObraNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametrObra does not contain DEFAULT_DIAMETR_OBRA
        defaultObraShouldNotBeFound("diametrObra.doesNotContain=" + DEFAULT_DIAMETR_OBRA);

        // Get all the obraList where diametrObra does not contain UPDATED_DIAMETR_OBRA
        defaultObraShouldBeFound("diametrObra.doesNotContain=" + UPDATED_DIAMETR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaMoldIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaMold equals to DEFAULT_ALTURA_MOLD
        defaultObraShouldBeFound("alturaMold.equals=" + DEFAULT_ALTURA_MOLD);

        // Get all the obraList where alturaMold equals to UPDATED_ALTURA_MOLD
        defaultObraShouldNotBeFound("alturaMold.equals=" + UPDATED_ALTURA_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaMoldIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaMold not equals to DEFAULT_ALTURA_MOLD
        defaultObraShouldNotBeFound("alturaMold.notEquals=" + DEFAULT_ALTURA_MOLD);

        // Get all the obraList where alturaMold not equals to UPDATED_ALTURA_MOLD
        defaultObraShouldBeFound("alturaMold.notEquals=" + UPDATED_ALTURA_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaMoldIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaMold in DEFAULT_ALTURA_MOLD or UPDATED_ALTURA_MOLD
        defaultObraShouldBeFound("alturaMold.in=" + DEFAULT_ALTURA_MOLD + "," + UPDATED_ALTURA_MOLD);

        // Get all the obraList where alturaMold equals to UPDATED_ALTURA_MOLD
        defaultObraShouldNotBeFound("alturaMold.in=" + UPDATED_ALTURA_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaMoldIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaMold is not null
        defaultObraShouldBeFound("alturaMold.specified=true");

        // Get all the obraList where alturaMold is null
        defaultObraShouldNotBeFound("alturaMold.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByAlturaMoldIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaMold is greater than or equal to DEFAULT_ALTURA_MOLD
        defaultObraShouldBeFound("alturaMold.greaterThanOrEqual=" + DEFAULT_ALTURA_MOLD);

        // Get all the obraList where alturaMold is greater than or equal to UPDATED_ALTURA_MOLD
        defaultObraShouldNotBeFound("alturaMold.greaterThanOrEqual=" + UPDATED_ALTURA_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaMoldIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaMold is less than or equal to DEFAULT_ALTURA_MOLD
        defaultObraShouldBeFound("alturaMold.lessThanOrEqual=" + DEFAULT_ALTURA_MOLD);

        // Get all the obraList where alturaMold is less than or equal to SMALLER_ALTURA_MOLD
        defaultObraShouldNotBeFound("alturaMold.lessThanOrEqual=" + SMALLER_ALTURA_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaMoldIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaMold is less than DEFAULT_ALTURA_MOLD
        defaultObraShouldNotBeFound("alturaMold.lessThan=" + DEFAULT_ALTURA_MOLD);

        // Get all the obraList where alturaMold is less than UPDATED_ALTURA_MOLD
        defaultObraShouldBeFound("alturaMold.lessThan=" + UPDATED_ALTURA_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByAlturaMoldIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where alturaMold is greater than DEFAULT_ALTURA_MOLD
        defaultObraShouldNotBeFound("alturaMold.greaterThan=" + DEFAULT_ALTURA_MOLD);

        // Get all the obraList where alturaMold is greater than SMALLER_ALTURA_MOLD
        defaultObraShouldBeFound("alturaMold.greaterThan=" + SMALLER_ALTURA_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByLargMoldIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largMold equals to DEFAULT_LARG_MOLD
        defaultObraShouldBeFound("largMold.equals=" + DEFAULT_LARG_MOLD);

        // Get all the obraList where largMold equals to UPDATED_LARG_MOLD
        defaultObraShouldNotBeFound("largMold.equals=" + UPDATED_LARG_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByLargMoldIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largMold not equals to DEFAULT_LARG_MOLD
        defaultObraShouldNotBeFound("largMold.notEquals=" + DEFAULT_LARG_MOLD);

        // Get all the obraList where largMold not equals to UPDATED_LARG_MOLD
        defaultObraShouldBeFound("largMold.notEquals=" + UPDATED_LARG_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByLargMoldIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largMold in DEFAULT_LARG_MOLD or UPDATED_LARG_MOLD
        defaultObraShouldBeFound("largMold.in=" + DEFAULT_LARG_MOLD + "," + UPDATED_LARG_MOLD);

        // Get all the obraList where largMold equals to UPDATED_LARG_MOLD
        defaultObraShouldNotBeFound("largMold.in=" + UPDATED_LARG_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByLargMoldIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largMold is not null
        defaultObraShouldBeFound("largMold.specified=true");

        // Get all the obraList where largMold is null
        defaultObraShouldNotBeFound("largMold.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByLargMoldIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largMold is greater than or equal to DEFAULT_LARG_MOLD
        defaultObraShouldBeFound("largMold.greaterThanOrEqual=" + DEFAULT_LARG_MOLD);

        // Get all the obraList where largMold is greater than or equal to UPDATED_LARG_MOLD
        defaultObraShouldNotBeFound("largMold.greaterThanOrEqual=" + UPDATED_LARG_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByLargMoldIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largMold is less than or equal to DEFAULT_LARG_MOLD
        defaultObraShouldBeFound("largMold.lessThanOrEqual=" + DEFAULT_LARG_MOLD);

        // Get all the obraList where largMold is less than or equal to SMALLER_LARG_MOLD
        defaultObraShouldNotBeFound("largMold.lessThanOrEqual=" + SMALLER_LARG_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByLargMoldIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largMold is less than DEFAULT_LARG_MOLD
        defaultObraShouldNotBeFound("largMold.lessThan=" + DEFAULT_LARG_MOLD);

        // Get all the obraList where largMold is less than UPDATED_LARG_MOLD
        defaultObraShouldBeFound("largMold.lessThan=" + UPDATED_LARG_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByLargMoldIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where largMold is greater than DEFAULT_LARG_MOLD
        defaultObraShouldNotBeFound("largMold.greaterThan=" + DEFAULT_LARG_MOLD);

        // Get all the obraList where largMold is greater than SMALLER_LARG_MOLD
        defaultObraShouldBeFound("largMold.greaterThan=" + SMALLER_LARG_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByProfMoldIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profMold equals to DEFAULT_PROF_MOLD
        defaultObraShouldBeFound("profMold.equals=" + DEFAULT_PROF_MOLD);

        // Get all the obraList where profMold equals to UPDATED_PROF_MOLD
        defaultObraShouldNotBeFound("profMold.equals=" + UPDATED_PROF_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByProfMoldIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profMold not equals to DEFAULT_PROF_MOLD
        defaultObraShouldNotBeFound("profMold.notEquals=" + DEFAULT_PROF_MOLD);

        // Get all the obraList where profMold not equals to UPDATED_PROF_MOLD
        defaultObraShouldBeFound("profMold.notEquals=" + UPDATED_PROF_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByProfMoldIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profMold in DEFAULT_PROF_MOLD or UPDATED_PROF_MOLD
        defaultObraShouldBeFound("profMold.in=" + DEFAULT_PROF_MOLD + "," + UPDATED_PROF_MOLD);

        // Get all the obraList where profMold equals to UPDATED_PROF_MOLD
        defaultObraShouldNotBeFound("profMold.in=" + UPDATED_PROF_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByProfMoldIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profMold is not null
        defaultObraShouldBeFound("profMold.specified=true");

        // Get all the obraList where profMold is null
        defaultObraShouldNotBeFound("profMold.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByProfMoldIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profMold is greater than or equal to DEFAULT_PROF_MOLD
        defaultObraShouldBeFound("profMold.greaterThanOrEqual=" + DEFAULT_PROF_MOLD);

        // Get all the obraList where profMold is greater than or equal to UPDATED_PROF_MOLD
        defaultObraShouldNotBeFound("profMold.greaterThanOrEqual=" + UPDATED_PROF_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByProfMoldIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profMold is less than or equal to DEFAULT_PROF_MOLD
        defaultObraShouldBeFound("profMold.lessThanOrEqual=" + DEFAULT_PROF_MOLD);

        // Get all the obraList where profMold is less than or equal to SMALLER_PROF_MOLD
        defaultObraShouldNotBeFound("profMold.lessThanOrEqual=" + SMALLER_PROF_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByProfMoldIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profMold is less than DEFAULT_PROF_MOLD
        defaultObraShouldNotBeFound("profMold.lessThan=" + DEFAULT_PROF_MOLD);

        // Get all the obraList where profMold is less than UPDATED_PROF_MOLD
        defaultObraShouldBeFound("profMold.lessThan=" + UPDATED_PROF_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByProfMoldIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where profMold is greater than DEFAULT_PROF_MOLD
        defaultObraShouldNotBeFound("profMold.greaterThan=" + DEFAULT_PROF_MOLD);

        // Get all the obraList where profMold is greater than SMALLER_PROF_MOLD
        defaultObraShouldBeFound("profMold.greaterThan=" + SMALLER_PROF_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByDiametroMoldIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametroMold equals to DEFAULT_DIAMETRO_MOLD
        defaultObraShouldBeFound("diametroMold.equals=" + DEFAULT_DIAMETRO_MOLD);

        // Get all the obraList where diametroMold equals to UPDATED_DIAMETRO_MOLD
        defaultObraShouldNotBeFound("diametroMold.equals=" + UPDATED_DIAMETRO_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByDiametroMoldIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametroMold not equals to DEFAULT_DIAMETRO_MOLD
        defaultObraShouldNotBeFound("diametroMold.notEquals=" + DEFAULT_DIAMETRO_MOLD);

        // Get all the obraList where diametroMold not equals to UPDATED_DIAMETRO_MOLD
        defaultObraShouldBeFound("diametroMold.notEquals=" + UPDATED_DIAMETRO_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByDiametroMoldIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametroMold in DEFAULT_DIAMETRO_MOLD or UPDATED_DIAMETRO_MOLD
        defaultObraShouldBeFound("diametroMold.in=" + DEFAULT_DIAMETRO_MOLD + "," + UPDATED_DIAMETRO_MOLD);

        // Get all the obraList where diametroMold equals to UPDATED_DIAMETRO_MOLD
        defaultObraShouldNotBeFound("diametroMold.in=" + UPDATED_DIAMETRO_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByDiametroMoldIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametroMold is not null
        defaultObraShouldBeFound("diametroMold.specified=true");

        // Get all the obraList where diametroMold is null
        defaultObraShouldNotBeFound("diametroMold.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDiametroMoldContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametroMold contains DEFAULT_DIAMETRO_MOLD
        defaultObraShouldBeFound("diametroMold.contains=" + DEFAULT_DIAMETRO_MOLD);

        // Get all the obraList where diametroMold contains UPDATED_DIAMETRO_MOLD
        defaultObraShouldNotBeFound("diametroMold.contains=" + UPDATED_DIAMETRO_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByDiametroMoldNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where diametroMold does not contain DEFAULT_DIAMETRO_MOLD
        defaultObraShouldNotBeFound("diametroMold.doesNotContain=" + DEFAULT_DIAMETRO_MOLD);

        // Get all the obraList where diametroMold does not contain UPDATED_DIAMETRO_MOLD
        defaultObraShouldBeFound("diametroMold.doesNotContain=" + UPDATED_DIAMETRO_MOLD);
    }

    @Test
    @Transactional
    void getAllObrasByDimensAdicIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dimensAdic equals to DEFAULT_DIMENS_ADIC
        defaultObraShouldBeFound("dimensAdic.equals=" + DEFAULT_DIMENS_ADIC);

        // Get all the obraList where dimensAdic equals to UPDATED_DIMENS_ADIC
        defaultObraShouldNotBeFound("dimensAdic.equals=" + UPDATED_DIMENS_ADIC);
    }

    @Test
    @Transactional
    void getAllObrasByDimensAdicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dimensAdic not equals to DEFAULT_DIMENS_ADIC
        defaultObraShouldNotBeFound("dimensAdic.notEquals=" + DEFAULT_DIMENS_ADIC);

        // Get all the obraList where dimensAdic not equals to UPDATED_DIMENS_ADIC
        defaultObraShouldBeFound("dimensAdic.notEquals=" + UPDATED_DIMENS_ADIC);
    }

    @Test
    @Transactional
    void getAllObrasByDimensAdicIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dimensAdic in DEFAULT_DIMENS_ADIC or UPDATED_DIMENS_ADIC
        defaultObraShouldBeFound("dimensAdic.in=" + DEFAULT_DIMENS_ADIC + "," + UPDATED_DIMENS_ADIC);

        // Get all the obraList where dimensAdic equals to UPDATED_DIMENS_ADIC
        defaultObraShouldNotBeFound("dimensAdic.in=" + UPDATED_DIMENS_ADIC);
    }

    @Test
    @Transactional
    void getAllObrasByDimensAdicIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dimensAdic is not null
        defaultObraShouldBeFound("dimensAdic.specified=true");

        // Get all the obraList where dimensAdic is null
        defaultObraShouldNotBeFound("dimensAdic.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDimensAdicContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dimensAdic contains DEFAULT_DIMENS_ADIC
        defaultObraShouldBeFound("dimensAdic.contains=" + DEFAULT_DIMENS_ADIC);

        // Get all the obraList where dimensAdic contains UPDATED_DIMENS_ADIC
        defaultObraShouldNotBeFound("dimensAdic.contains=" + UPDATED_DIMENS_ADIC);
    }

    @Test
    @Transactional
    void getAllObrasByDimensAdicNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dimensAdic does not contain DEFAULT_DIMENS_ADIC
        defaultObraShouldNotBeFound("dimensAdic.doesNotContain=" + DEFAULT_DIMENS_ADIC);

        // Get all the obraList where dimensAdic does not contain UPDATED_DIMENS_ADIC
        defaultObraShouldBeFound("dimensAdic.doesNotContain=" + UPDATED_DIMENS_ADIC);
    }

    @Test
    @Transactional
    void getAllObrasByPesObraIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where pesObra equals to DEFAULT_PES_OBRA
        defaultObraShouldBeFound("pesObra.equals=" + DEFAULT_PES_OBRA);

        // Get all the obraList where pesObra equals to UPDATED_PES_OBRA
        defaultObraShouldNotBeFound("pesObra.equals=" + UPDATED_PES_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByPesObraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where pesObra not equals to DEFAULT_PES_OBRA
        defaultObraShouldNotBeFound("pesObra.notEquals=" + DEFAULT_PES_OBRA);

        // Get all the obraList where pesObra not equals to UPDATED_PES_OBRA
        defaultObraShouldBeFound("pesObra.notEquals=" + UPDATED_PES_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByPesObraIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where pesObra in DEFAULT_PES_OBRA or UPDATED_PES_OBRA
        defaultObraShouldBeFound("pesObra.in=" + DEFAULT_PES_OBRA + "," + UPDATED_PES_OBRA);

        // Get all the obraList where pesObra equals to UPDATED_PES_OBRA
        defaultObraShouldNotBeFound("pesObra.in=" + UPDATED_PES_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByPesObraIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where pesObra is not null
        defaultObraShouldBeFound("pesObra.specified=true");

        // Get all the obraList where pesObra is null
        defaultObraShouldNotBeFound("pesObra.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByPesObraContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where pesObra contains DEFAULT_PES_OBRA
        defaultObraShouldBeFound("pesObra.contains=" + DEFAULT_PES_OBRA);

        // Get all the obraList where pesObra contains UPDATED_PES_OBRA
        defaultObraShouldNotBeFound("pesObra.contains=" + UPDATED_PES_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByPesObraNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where pesObra does not contain DEFAULT_PES_OBRA
        defaultObraShouldNotBeFound("pesObra.doesNotContain=" + DEFAULT_PES_OBRA);

        // Get all the obraList where pesObra does not contain UPDATED_PES_OBRA
        defaultObraShouldBeFound("pesObra.doesNotContain=" + UPDATED_PES_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByAtribuidoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where atribuido equals to DEFAULT_ATRIBUIDO
        defaultObraShouldBeFound("atribuido.equals=" + DEFAULT_ATRIBUIDO);

        // Get all the obraList where atribuido equals to UPDATED_ATRIBUIDO
        defaultObraShouldNotBeFound("atribuido.equals=" + UPDATED_ATRIBUIDO);
    }

    @Test
    @Transactional
    void getAllObrasByAtribuidoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where atribuido not equals to DEFAULT_ATRIBUIDO
        defaultObraShouldNotBeFound("atribuido.notEquals=" + DEFAULT_ATRIBUIDO);

        // Get all the obraList where atribuido not equals to UPDATED_ATRIBUIDO
        defaultObraShouldBeFound("atribuido.notEquals=" + UPDATED_ATRIBUIDO);
    }

    @Test
    @Transactional
    void getAllObrasByAtribuidoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where atribuido in DEFAULT_ATRIBUIDO or UPDATED_ATRIBUIDO
        defaultObraShouldBeFound("atribuido.in=" + DEFAULT_ATRIBUIDO + "," + UPDATED_ATRIBUIDO);

        // Get all the obraList where atribuido equals to UPDATED_ATRIBUIDO
        defaultObraShouldNotBeFound("atribuido.in=" + UPDATED_ATRIBUIDO);
    }

    @Test
    @Transactional
    void getAllObrasByAtribuidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where atribuido is not null
        defaultObraShouldBeFound("atribuido.specified=true");

        // Get all the obraList where atribuido is null
        defaultObraShouldNotBeFound("atribuido.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasBynFotoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where nFoto equals to DEFAULT_N_FOTO
        defaultObraShouldBeFound("nFoto.equals=" + DEFAULT_N_FOTO);

        // Get all the obraList where nFoto equals to UPDATED_N_FOTO
        defaultObraShouldNotBeFound("nFoto.equals=" + UPDATED_N_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasBynFotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where nFoto not equals to DEFAULT_N_FOTO
        defaultObraShouldNotBeFound("nFoto.notEquals=" + DEFAULT_N_FOTO);

        // Get all the obraList where nFoto not equals to UPDATED_N_FOTO
        defaultObraShouldBeFound("nFoto.notEquals=" + UPDATED_N_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasBynFotoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where nFoto in DEFAULT_N_FOTO or UPDATED_N_FOTO
        defaultObraShouldBeFound("nFoto.in=" + DEFAULT_N_FOTO + "," + UPDATED_N_FOTO);

        // Get all the obraList where nFoto equals to UPDATED_N_FOTO
        defaultObraShouldNotBeFound("nFoto.in=" + UPDATED_N_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasBynFotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where nFoto is not null
        defaultObraShouldBeFound("nFoto.specified=true");

        // Get all the obraList where nFoto is null
        defaultObraShouldNotBeFound("nFoto.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasBynFotoContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where nFoto contains DEFAULT_N_FOTO
        defaultObraShouldBeFound("nFoto.contains=" + DEFAULT_N_FOTO);

        // Get all the obraList where nFoto contains UPDATED_N_FOTO
        defaultObraShouldNotBeFound("nFoto.contains=" + UPDATED_N_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasBynFotoNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where nFoto does not contain DEFAULT_N_FOTO
        defaultObraShouldNotBeFound("nFoto.doesNotContain=" + DEFAULT_N_FOTO);

        // Get all the obraList where nFoto does not contain UPDATED_N_FOTO
        defaultObraShouldBeFound("nFoto.doesNotContain=" + UPDATED_N_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByConjuntoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjunto equals to DEFAULT_CONJUNTO
        defaultObraShouldBeFound("conjunto.equals=" + DEFAULT_CONJUNTO);

        // Get all the obraList where conjunto equals to UPDATED_CONJUNTO
        defaultObraShouldNotBeFound("conjunto.equals=" + UPDATED_CONJUNTO);
    }

    @Test
    @Transactional
    void getAllObrasByConjuntoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjunto not equals to DEFAULT_CONJUNTO
        defaultObraShouldNotBeFound("conjunto.notEquals=" + DEFAULT_CONJUNTO);

        // Get all the obraList where conjunto not equals to UPDATED_CONJUNTO
        defaultObraShouldBeFound("conjunto.notEquals=" + UPDATED_CONJUNTO);
    }

    @Test
    @Transactional
    void getAllObrasByConjuntoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjunto in DEFAULT_CONJUNTO or UPDATED_CONJUNTO
        defaultObraShouldBeFound("conjunto.in=" + DEFAULT_CONJUNTO + "," + UPDATED_CONJUNTO);

        // Get all the obraList where conjunto equals to UPDATED_CONJUNTO
        defaultObraShouldNotBeFound("conjunto.in=" + UPDATED_CONJUNTO);
    }

    @Test
    @Transactional
    void getAllObrasByConjuntoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjunto is not null
        defaultObraShouldBeFound("conjunto.specified=true");

        // Get all the obraList where conjunto is null
        defaultObraShouldNotBeFound("conjunto.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByConjTomboIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjTombo equals to DEFAULT_CONJ_TOMBO
        defaultObraShouldBeFound("conjTombo.equals=" + DEFAULT_CONJ_TOMBO);

        // Get all the obraList where conjTombo equals to UPDATED_CONJ_TOMBO
        defaultObraShouldNotBeFound("conjTombo.equals=" + UPDATED_CONJ_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByConjTomboIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjTombo not equals to DEFAULT_CONJ_TOMBO
        defaultObraShouldNotBeFound("conjTombo.notEquals=" + DEFAULT_CONJ_TOMBO);

        // Get all the obraList where conjTombo not equals to UPDATED_CONJ_TOMBO
        defaultObraShouldBeFound("conjTombo.notEquals=" + UPDATED_CONJ_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByConjTomboIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjTombo in DEFAULT_CONJ_TOMBO or UPDATED_CONJ_TOMBO
        defaultObraShouldBeFound("conjTombo.in=" + DEFAULT_CONJ_TOMBO + "," + UPDATED_CONJ_TOMBO);

        // Get all the obraList where conjTombo equals to UPDATED_CONJ_TOMBO
        defaultObraShouldNotBeFound("conjTombo.in=" + UPDATED_CONJ_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByConjTomboIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjTombo is not null
        defaultObraShouldBeFound("conjTombo.specified=true");

        // Get all the obraList where conjTombo is null
        defaultObraShouldNotBeFound("conjTombo.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByConjTomboContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjTombo contains DEFAULT_CONJ_TOMBO
        defaultObraShouldBeFound("conjTombo.contains=" + DEFAULT_CONJ_TOMBO);

        // Get all the obraList where conjTombo contains UPDATED_CONJ_TOMBO
        defaultObraShouldNotBeFound("conjTombo.contains=" + UPDATED_CONJ_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByConjTomboNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where conjTombo does not contain DEFAULT_CONJ_TOMBO
        defaultObraShouldNotBeFound("conjTombo.doesNotContain=" + DEFAULT_CONJ_TOMBO);

        // Get all the obraList where conjTombo does not contain UPDATED_CONJ_TOMBO
        defaultObraShouldBeFound("conjTombo.doesNotContain=" + UPDATED_CONJ_TOMBO);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguro equals to DEFAULT_VALOR_SEGURO
        defaultObraShouldBeFound("valorSeguro.equals=" + DEFAULT_VALOR_SEGURO);

        // Get all the obraList where valorSeguro equals to UPDATED_VALOR_SEGURO
        defaultObraShouldNotBeFound("valorSeguro.equals=" + UPDATED_VALOR_SEGURO);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguro not equals to DEFAULT_VALOR_SEGURO
        defaultObraShouldNotBeFound("valorSeguro.notEquals=" + DEFAULT_VALOR_SEGURO);

        // Get all the obraList where valorSeguro not equals to UPDATED_VALOR_SEGURO
        defaultObraShouldBeFound("valorSeguro.notEquals=" + UPDATED_VALOR_SEGURO);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguro in DEFAULT_VALOR_SEGURO or UPDATED_VALOR_SEGURO
        defaultObraShouldBeFound("valorSeguro.in=" + DEFAULT_VALOR_SEGURO + "," + UPDATED_VALOR_SEGURO);

        // Get all the obraList where valorSeguro equals to UPDATED_VALOR_SEGURO
        defaultObraShouldNotBeFound("valorSeguro.in=" + UPDATED_VALOR_SEGURO);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguro is not null
        defaultObraShouldBeFound("valorSeguro.specified=true");

        // Get all the obraList where valorSeguro is null
        defaultObraShouldNotBeFound("valorSeguro.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguro is greater than or equal to DEFAULT_VALOR_SEGURO
        defaultObraShouldBeFound("valorSeguro.greaterThanOrEqual=" + DEFAULT_VALOR_SEGURO);

        // Get all the obraList where valorSeguro is greater than or equal to UPDATED_VALOR_SEGURO
        defaultObraShouldNotBeFound("valorSeguro.greaterThanOrEqual=" + UPDATED_VALOR_SEGURO);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguro is less than or equal to DEFAULT_VALOR_SEGURO
        defaultObraShouldBeFound("valorSeguro.lessThanOrEqual=" + DEFAULT_VALOR_SEGURO);

        // Get all the obraList where valorSeguro is less than or equal to SMALLER_VALOR_SEGURO
        defaultObraShouldNotBeFound("valorSeguro.lessThanOrEqual=" + SMALLER_VALOR_SEGURO);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguro is less than DEFAULT_VALOR_SEGURO
        defaultObraShouldNotBeFound("valorSeguro.lessThan=" + DEFAULT_VALOR_SEGURO);

        // Get all the obraList where valorSeguro is less than UPDATED_VALOR_SEGURO
        defaultObraShouldBeFound("valorSeguro.lessThan=" + UPDATED_VALOR_SEGURO);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguro is greater than DEFAULT_VALOR_SEGURO
        defaultObraShouldNotBeFound("valorSeguro.greaterThan=" + DEFAULT_VALOR_SEGURO);

        // Get all the obraList where valorSeguro is greater than SMALLER_VALOR_SEGURO
        defaultObraShouldBeFound("valorSeguro.greaterThan=" + SMALLER_VALOR_SEGURO);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroRealIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguroReal equals to DEFAULT_VALOR_SEGURO_REAL
        defaultObraShouldBeFound("valorSeguroReal.equals=" + DEFAULT_VALOR_SEGURO_REAL);

        // Get all the obraList where valorSeguroReal equals to UPDATED_VALOR_SEGURO_REAL
        defaultObraShouldNotBeFound("valorSeguroReal.equals=" + UPDATED_VALOR_SEGURO_REAL);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroRealIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguroReal not equals to DEFAULT_VALOR_SEGURO_REAL
        defaultObraShouldNotBeFound("valorSeguroReal.notEquals=" + DEFAULT_VALOR_SEGURO_REAL);

        // Get all the obraList where valorSeguroReal not equals to UPDATED_VALOR_SEGURO_REAL
        defaultObraShouldBeFound("valorSeguroReal.notEquals=" + UPDATED_VALOR_SEGURO_REAL);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroRealIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguroReal in DEFAULT_VALOR_SEGURO_REAL or UPDATED_VALOR_SEGURO_REAL
        defaultObraShouldBeFound("valorSeguroReal.in=" + DEFAULT_VALOR_SEGURO_REAL + "," + UPDATED_VALOR_SEGURO_REAL);

        // Get all the obraList where valorSeguroReal equals to UPDATED_VALOR_SEGURO_REAL
        defaultObraShouldNotBeFound("valorSeguroReal.in=" + UPDATED_VALOR_SEGURO_REAL);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroRealIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguroReal is not null
        defaultObraShouldBeFound("valorSeguroReal.specified=true");

        // Get all the obraList where valorSeguroReal is null
        defaultObraShouldNotBeFound("valorSeguroReal.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroRealIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguroReal is greater than or equal to DEFAULT_VALOR_SEGURO_REAL
        defaultObraShouldBeFound("valorSeguroReal.greaterThanOrEqual=" + DEFAULT_VALOR_SEGURO_REAL);

        // Get all the obraList where valorSeguroReal is greater than or equal to UPDATED_VALOR_SEGURO_REAL
        defaultObraShouldNotBeFound("valorSeguroReal.greaterThanOrEqual=" + UPDATED_VALOR_SEGURO_REAL);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroRealIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguroReal is less than or equal to DEFAULT_VALOR_SEGURO_REAL
        defaultObraShouldBeFound("valorSeguroReal.lessThanOrEqual=" + DEFAULT_VALOR_SEGURO_REAL);

        // Get all the obraList where valorSeguroReal is less than or equal to SMALLER_VALOR_SEGURO_REAL
        defaultObraShouldNotBeFound("valorSeguroReal.lessThanOrEqual=" + SMALLER_VALOR_SEGURO_REAL);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroRealIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguroReal is less than DEFAULT_VALOR_SEGURO_REAL
        defaultObraShouldNotBeFound("valorSeguroReal.lessThan=" + DEFAULT_VALOR_SEGURO_REAL);

        // Get all the obraList where valorSeguroReal is less than UPDATED_VALOR_SEGURO_REAL
        defaultObraShouldBeFound("valorSeguroReal.lessThan=" + UPDATED_VALOR_SEGURO_REAL);
    }

    @Test
    @Transactional
    void getAllObrasByValorSeguroRealIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where valorSeguroReal is greater than DEFAULT_VALOR_SEGURO_REAL
        defaultObraShouldNotBeFound("valorSeguroReal.greaterThan=" + DEFAULT_VALOR_SEGURO_REAL);

        // Get all the obraList where valorSeguroReal is greater than SMALLER_VALOR_SEGURO_REAL
        defaultObraShouldBeFound("valorSeguroReal.greaterThan=" + SMALLER_VALOR_SEGURO_REAL);
    }

    @Test
    @Transactional
    void getAllObrasByDataconversaoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataconversao equals to DEFAULT_DATACONVERSAO
        defaultObraShouldBeFound("dataconversao.equals=" + DEFAULT_DATACONVERSAO);

        // Get all the obraList where dataconversao equals to UPDATED_DATACONVERSAO
        defaultObraShouldNotBeFound("dataconversao.equals=" + UPDATED_DATACONVERSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataconversaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataconversao not equals to DEFAULT_DATACONVERSAO
        defaultObraShouldNotBeFound("dataconversao.notEquals=" + DEFAULT_DATACONVERSAO);

        // Get all the obraList where dataconversao not equals to UPDATED_DATACONVERSAO
        defaultObraShouldBeFound("dataconversao.notEquals=" + UPDATED_DATACONVERSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataconversaoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataconversao in DEFAULT_DATACONVERSAO or UPDATED_DATACONVERSAO
        defaultObraShouldBeFound("dataconversao.in=" + DEFAULT_DATACONVERSAO + "," + UPDATED_DATACONVERSAO);

        // Get all the obraList where dataconversao equals to UPDATED_DATACONVERSAO
        defaultObraShouldNotBeFound("dataconversao.in=" + UPDATED_DATACONVERSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataconversaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataconversao is not null
        defaultObraShouldBeFound("dataconversao.specified=true");

        // Get all the obraList where dataconversao is null
        defaultObraShouldNotBeFound("dataconversao.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDataconversaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataconversao is greater than or equal to DEFAULT_DATACONVERSAO
        defaultObraShouldBeFound("dataconversao.greaterThanOrEqual=" + DEFAULT_DATACONVERSAO);

        // Get all the obraList where dataconversao is greater than or equal to UPDATED_DATACONVERSAO
        defaultObraShouldNotBeFound("dataconversao.greaterThanOrEqual=" + UPDATED_DATACONVERSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataconversaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataconversao is less than or equal to DEFAULT_DATACONVERSAO
        defaultObraShouldBeFound("dataconversao.lessThanOrEqual=" + DEFAULT_DATACONVERSAO);

        // Get all the obraList where dataconversao is less than or equal to SMALLER_DATACONVERSAO
        defaultObraShouldNotBeFound("dataconversao.lessThanOrEqual=" + SMALLER_DATACONVERSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataconversaoIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataconversao is less than DEFAULT_DATACONVERSAO
        defaultObraShouldNotBeFound("dataconversao.lessThan=" + DEFAULT_DATACONVERSAO);

        // Get all the obraList where dataconversao is less than UPDATED_DATACONVERSAO
        defaultObraShouldBeFound("dataconversao.lessThan=" + UPDATED_DATACONVERSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataconversaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataconversao is greater than DEFAULT_DATACONVERSAO
        defaultObraShouldNotBeFound("dataconversao.greaterThan=" + DEFAULT_DATACONVERSAO);

        // Get all the obraList where dataconversao is greater than SMALLER_DATACONVERSAO
        defaultObraShouldBeFound("dataconversao.greaterThan=" + SMALLER_DATACONVERSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataAlterApoliceIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataAlterApolice equals to DEFAULT_DATA_ALTER_APOLICE
        defaultObraShouldBeFound("dataAlterApolice.equals=" + DEFAULT_DATA_ALTER_APOLICE);

        // Get all the obraList where dataAlterApolice equals to UPDATED_DATA_ALTER_APOLICE
        defaultObraShouldNotBeFound("dataAlterApolice.equals=" + UPDATED_DATA_ALTER_APOLICE);
    }

    @Test
    @Transactional
    void getAllObrasByDataAlterApoliceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataAlterApolice not equals to DEFAULT_DATA_ALTER_APOLICE
        defaultObraShouldNotBeFound("dataAlterApolice.notEquals=" + DEFAULT_DATA_ALTER_APOLICE);

        // Get all the obraList where dataAlterApolice not equals to UPDATED_DATA_ALTER_APOLICE
        defaultObraShouldBeFound("dataAlterApolice.notEquals=" + UPDATED_DATA_ALTER_APOLICE);
    }

    @Test
    @Transactional
    void getAllObrasByDataAlterApoliceIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataAlterApolice in DEFAULT_DATA_ALTER_APOLICE or UPDATED_DATA_ALTER_APOLICE
        defaultObraShouldBeFound("dataAlterApolice.in=" + DEFAULT_DATA_ALTER_APOLICE + "," + UPDATED_DATA_ALTER_APOLICE);

        // Get all the obraList where dataAlterApolice equals to UPDATED_DATA_ALTER_APOLICE
        defaultObraShouldNotBeFound("dataAlterApolice.in=" + UPDATED_DATA_ALTER_APOLICE);
    }

    @Test
    @Transactional
    void getAllObrasByDataAlterApoliceIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataAlterApolice is not null
        defaultObraShouldBeFound("dataAlterApolice.specified=true");

        // Get all the obraList where dataAlterApolice is null
        defaultObraShouldNotBeFound("dataAlterApolice.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDataAlterApoliceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataAlterApolice is greater than or equal to DEFAULT_DATA_ALTER_APOLICE
        defaultObraShouldBeFound("dataAlterApolice.greaterThanOrEqual=" + DEFAULT_DATA_ALTER_APOLICE);

        // Get all the obraList where dataAlterApolice is greater than or equal to UPDATED_DATA_ALTER_APOLICE
        defaultObraShouldNotBeFound("dataAlterApolice.greaterThanOrEqual=" + UPDATED_DATA_ALTER_APOLICE);
    }

    @Test
    @Transactional
    void getAllObrasByDataAlterApoliceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataAlterApolice is less than or equal to DEFAULT_DATA_ALTER_APOLICE
        defaultObraShouldBeFound("dataAlterApolice.lessThanOrEqual=" + DEFAULT_DATA_ALTER_APOLICE);

        // Get all the obraList where dataAlterApolice is less than or equal to SMALLER_DATA_ALTER_APOLICE
        defaultObraShouldNotBeFound("dataAlterApolice.lessThanOrEqual=" + SMALLER_DATA_ALTER_APOLICE);
    }

    @Test
    @Transactional
    void getAllObrasByDataAlterApoliceIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataAlterApolice is less than DEFAULT_DATA_ALTER_APOLICE
        defaultObraShouldNotBeFound("dataAlterApolice.lessThan=" + DEFAULT_DATA_ALTER_APOLICE);

        // Get all the obraList where dataAlterApolice is less than UPDATED_DATA_ALTER_APOLICE
        defaultObraShouldBeFound("dataAlterApolice.lessThan=" + UPDATED_DATA_ALTER_APOLICE);
    }

    @Test
    @Transactional
    void getAllObrasByDataAlterApoliceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataAlterApolice is greater than DEFAULT_DATA_ALTER_APOLICE
        defaultObraShouldNotBeFound("dataAlterApolice.greaterThan=" + DEFAULT_DATA_ALTER_APOLICE);

        // Get all the obraList where dataAlterApolice is greater than SMALLER_DATA_ALTER_APOLICE
        defaultObraShouldBeFound("dataAlterApolice.greaterThan=" + SMALLER_DATA_ALTER_APOLICE);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtual equals to DEFAULT_LOCAL_ATUAL
        defaultObraShouldBeFound("localAtual.equals=" + DEFAULT_LOCAL_ATUAL);

        // Get all the obraList where localAtual equals to UPDATED_LOCAL_ATUAL
        defaultObraShouldNotBeFound("localAtual.equals=" + UPDATED_LOCAL_ATUAL);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtual not equals to DEFAULT_LOCAL_ATUAL
        defaultObraShouldNotBeFound("localAtual.notEquals=" + DEFAULT_LOCAL_ATUAL);

        // Get all the obraList where localAtual not equals to UPDATED_LOCAL_ATUAL
        defaultObraShouldBeFound("localAtual.notEquals=" + UPDATED_LOCAL_ATUAL);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtual in DEFAULT_LOCAL_ATUAL or UPDATED_LOCAL_ATUAL
        defaultObraShouldBeFound("localAtual.in=" + DEFAULT_LOCAL_ATUAL + "," + UPDATED_LOCAL_ATUAL);

        // Get all the obraList where localAtual equals to UPDATED_LOCAL_ATUAL
        defaultObraShouldNotBeFound("localAtual.in=" + UPDATED_LOCAL_ATUAL);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtual is not null
        defaultObraShouldBeFound("localAtual.specified=true");

        // Get all the obraList where localAtual is null
        defaultObraShouldNotBeFound("localAtual.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtual contains DEFAULT_LOCAL_ATUAL
        defaultObraShouldBeFound("localAtual.contains=" + DEFAULT_LOCAL_ATUAL);

        // Get all the obraList where localAtual contains UPDATED_LOCAL_ATUAL
        defaultObraShouldNotBeFound("localAtual.contains=" + UPDATED_LOCAL_ATUAL);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtual does not contain DEFAULT_LOCAL_ATUAL
        defaultObraShouldNotBeFound("localAtual.doesNotContain=" + DEFAULT_LOCAL_ATUAL);

        // Get all the obraList where localAtual does not contain UPDATED_LOCAL_ATUAL
        defaultObraShouldBeFound("localAtual.doesNotContain=" + UPDATED_LOCAL_ATUAL);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualNovoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtualNovo equals to DEFAULT_LOCAL_ATUAL_NOVO
        defaultObraShouldBeFound("localAtualNovo.equals=" + DEFAULT_LOCAL_ATUAL_NOVO);

        // Get all the obraList where localAtualNovo equals to UPDATED_LOCAL_ATUAL_NOVO
        defaultObraShouldNotBeFound("localAtualNovo.equals=" + UPDATED_LOCAL_ATUAL_NOVO);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualNovoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtualNovo not equals to DEFAULT_LOCAL_ATUAL_NOVO
        defaultObraShouldNotBeFound("localAtualNovo.notEquals=" + DEFAULT_LOCAL_ATUAL_NOVO);

        // Get all the obraList where localAtualNovo not equals to UPDATED_LOCAL_ATUAL_NOVO
        defaultObraShouldBeFound("localAtualNovo.notEquals=" + UPDATED_LOCAL_ATUAL_NOVO);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualNovoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtualNovo in DEFAULT_LOCAL_ATUAL_NOVO or UPDATED_LOCAL_ATUAL_NOVO
        defaultObraShouldBeFound("localAtualNovo.in=" + DEFAULT_LOCAL_ATUAL_NOVO + "," + UPDATED_LOCAL_ATUAL_NOVO);

        // Get all the obraList where localAtualNovo equals to UPDATED_LOCAL_ATUAL_NOVO
        defaultObraShouldNotBeFound("localAtualNovo.in=" + UPDATED_LOCAL_ATUAL_NOVO);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualNovoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtualNovo is not null
        defaultObraShouldBeFound("localAtualNovo.specified=true");

        // Get all the obraList where localAtualNovo is null
        defaultObraShouldNotBeFound("localAtualNovo.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualNovoContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtualNovo contains DEFAULT_LOCAL_ATUAL_NOVO
        defaultObraShouldBeFound("localAtualNovo.contains=" + DEFAULT_LOCAL_ATUAL_NOVO);

        // Get all the obraList where localAtualNovo contains UPDATED_LOCAL_ATUAL_NOVO
        defaultObraShouldNotBeFound("localAtualNovo.contains=" + UPDATED_LOCAL_ATUAL_NOVO);
    }

    @Test
    @Transactional
    void getAllObrasByLocalAtualNovoNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localAtualNovo does not contain DEFAULT_LOCAL_ATUAL_NOVO
        defaultObraShouldNotBeFound("localAtualNovo.doesNotContain=" + DEFAULT_LOCAL_ATUAL_NOVO);

        // Get all the obraList where localAtualNovo does not contain UPDATED_LOCAL_ATUAL_NOVO
        defaultObraShouldBeFound("localAtualNovo.doesNotContain=" + UPDATED_LOCAL_ATUAL_NOVO);
    }

    @Test
    @Transactional
    void getAllObrasByCodParametroIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where codParametro equals to DEFAULT_COD_PARAMETRO
        defaultObraShouldBeFound("codParametro.equals=" + DEFAULT_COD_PARAMETRO);

        // Get all the obraList where codParametro equals to UPDATED_COD_PARAMETRO
        defaultObraShouldNotBeFound("codParametro.equals=" + UPDATED_COD_PARAMETRO);
    }

    @Test
    @Transactional
    void getAllObrasByCodParametroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where codParametro not equals to DEFAULT_COD_PARAMETRO
        defaultObraShouldNotBeFound("codParametro.notEquals=" + DEFAULT_COD_PARAMETRO);

        // Get all the obraList where codParametro not equals to UPDATED_COD_PARAMETRO
        defaultObraShouldBeFound("codParametro.notEquals=" + UPDATED_COD_PARAMETRO);
    }

    @Test
    @Transactional
    void getAllObrasByCodParametroIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where codParametro in DEFAULT_COD_PARAMETRO or UPDATED_COD_PARAMETRO
        defaultObraShouldBeFound("codParametro.in=" + DEFAULT_COD_PARAMETRO + "," + UPDATED_COD_PARAMETRO);

        // Get all the obraList where codParametro equals to UPDATED_COD_PARAMETRO
        defaultObraShouldNotBeFound("codParametro.in=" + UPDATED_COD_PARAMETRO);
    }

    @Test
    @Transactional
    void getAllObrasByCodParametroIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where codParametro is not null
        defaultObraShouldBeFound("codParametro.specified=true");

        // Get all the obraList where codParametro is null
        defaultObraShouldNotBeFound("codParametro.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByCodParametroContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where codParametro contains DEFAULT_COD_PARAMETRO
        defaultObraShouldBeFound("codParametro.contains=" + DEFAULT_COD_PARAMETRO);

        // Get all the obraList where codParametro contains UPDATED_COD_PARAMETRO
        defaultObraShouldNotBeFound("codParametro.contains=" + UPDATED_COD_PARAMETRO);
    }

    @Test
    @Transactional
    void getAllObrasByCodParametroNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where codParametro does not contain DEFAULT_COD_PARAMETRO
        defaultObraShouldNotBeFound("codParametro.doesNotContain=" + DEFAULT_COD_PARAMETRO);

        // Get all the obraList where codParametro does not contain UPDATED_COD_PARAMETRO
        defaultObraShouldBeFound("codParametro.doesNotContain=" + UPDATED_COD_PARAMETRO);
    }

    @Test
    @Transactional
    void getAllObrasByObsIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obs equals to DEFAULT_OBS
        defaultObraShouldBeFound("obs.equals=" + DEFAULT_OBS);

        // Get all the obraList where obs equals to UPDATED_OBS
        defaultObraShouldNotBeFound("obs.equals=" + UPDATED_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByObsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obs not equals to DEFAULT_OBS
        defaultObraShouldNotBeFound("obs.notEquals=" + DEFAULT_OBS);

        // Get all the obraList where obs not equals to UPDATED_OBS
        defaultObraShouldBeFound("obs.notEquals=" + UPDATED_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByObsIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obs in DEFAULT_OBS or UPDATED_OBS
        defaultObraShouldBeFound("obs.in=" + DEFAULT_OBS + "," + UPDATED_OBS);

        // Get all the obraList where obs equals to UPDATED_OBS
        defaultObraShouldNotBeFound("obs.in=" + UPDATED_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByObsIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obs is not null
        defaultObraShouldBeFound("obs.specified=true");

        // Get all the obraList where obs is null
        defaultObraShouldNotBeFound("obs.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByObsContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obs contains DEFAULT_OBS
        defaultObraShouldBeFound("obs.contains=" + DEFAULT_OBS);

        // Get all the obraList where obs contains UPDATED_OBS
        defaultObraShouldNotBeFound("obs.contains=" + UPDATED_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByObsNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obs does not contain DEFAULT_OBS
        defaultObraShouldNotBeFound("obs.doesNotContain=" + DEFAULT_OBS);

        // Get all the obraList where obs does not contain UPDATED_OBS
        defaultObraShouldBeFound("obs.doesNotContain=" + UPDATED_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByTiragemIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tiragem equals to DEFAULT_TIRAGEM
        defaultObraShouldBeFound("tiragem.equals=" + DEFAULT_TIRAGEM);

        // Get all the obraList where tiragem equals to UPDATED_TIRAGEM
        defaultObraShouldNotBeFound("tiragem.equals=" + UPDATED_TIRAGEM);
    }

    @Test
    @Transactional
    void getAllObrasByTiragemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tiragem not equals to DEFAULT_TIRAGEM
        defaultObraShouldNotBeFound("tiragem.notEquals=" + DEFAULT_TIRAGEM);

        // Get all the obraList where tiragem not equals to UPDATED_TIRAGEM
        defaultObraShouldBeFound("tiragem.notEquals=" + UPDATED_TIRAGEM);
    }

    @Test
    @Transactional
    void getAllObrasByTiragemIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tiragem in DEFAULT_TIRAGEM or UPDATED_TIRAGEM
        defaultObraShouldBeFound("tiragem.in=" + DEFAULT_TIRAGEM + "," + UPDATED_TIRAGEM);

        // Get all the obraList where tiragem equals to UPDATED_TIRAGEM
        defaultObraShouldNotBeFound("tiragem.in=" + UPDATED_TIRAGEM);
    }

    @Test
    @Transactional
    void getAllObrasByTiragemIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tiragem is not null
        defaultObraShouldBeFound("tiragem.specified=true");

        // Get all the obraList where tiragem is null
        defaultObraShouldNotBeFound("tiragem.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByTiragemContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tiragem contains DEFAULT_TIRAGEM
        defaultObraShouldBeFound("tiragem.contains=" + DEFAULT_TIRAGEM);

        // Get all the obraList where tiragem contains UPDATED_TIRAGEM
        defaultObraShouldNotBeFound("tiragem.contains=" + UPDATED_TIRAGEM);
    }

    @Test
    @Transactional
    void getAllObrasByTiragemNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tiragem does not contain DEFAULT_TIRAGEM
        defaultObraShouldNotBeFound("tiragem.doesNotContain=" + DEFAULT_TIRAGEM);

        // Get all the obraList where tiragem does not contain UPDATED_TIRAGEM
        defaultObraShouldBeFound("tiragem.doesNotContain=" + UPDATED_TIRAGEM);
    }

    @Test
    @Transactional
    void getAllObrasBySerieIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where serie equals to DEFAULT_SERIE
        defaultObraShouldBeFound("serie.equals=" + DEFAULT_SERIE);

        // Get all the obraList where serie equals to UPDATED_SERIE
        defaultObraShouldNotBeFound("serie.equals=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    void getAllObrasBySerieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where serie not equals to DEFAULT_SERIE
        defaultObraShouldNotBeFound("serie.notEquals=" + DEFAULT_SERIE);

        // Get all the obraList where serie not equals to UPDATED_SERIE
        defaultObraShouldBeFound("serie.notEquals=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    void getAllObrasBySerieIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where serie in DEFAULT_SERIE or UPDATED_SERIE
        defaultObraShouldBeFound("serie.in=" + DEFAULT_SERIE + "," + UPDATED_SERIE);

        // Get all the obraList where serie equals to UPDATED_SERIE
        defaultObraShouldNotBeFound("serie.in=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    void getAllObrasBySerieIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where serie is not null
        defaultObraShouldBeFound("serie.specified=true");

        // Get all the obraList where serie is null
        defaultObraShouldNotBeFound("serie.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasBySerieContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where serie contains DEFAULT_SERIE
        defaultObraShouldBeFound("serie.contains=" + DEFAULT_SERIE);

        // Get all the obraList where serie contains UPDATED_SERIE
        defaultObraShouldNotBeFound("serie.contains=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    void getAllObrasBySerieNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where serie does not contain DEFAULT_SERIE
        defaultObraShouldNotBeFound("serie.doesNotContain=" + DEFAULT_SERIE);

        // Get all the obraList where serie does not contain UPDATED_SERIE
        defaultObraShouldBeFound("serie.doesNotContain=" + UPDATED_SERIE);
    }

    @Test
    @Transactional
    void getAllObrasBySeloIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selo equals to DEFAULT_SELO
        defaultObraShouldBeFound("selo.equals=" + DEFAULT_SELO);

        // Get all the obraList where selo equals to UPDATED_SELO
        defaultObraShouldNotBeFound("selo.equals=" + UPDATED_SELO);
    }

    @Test
    @Transactional
    void getAllObrasBySeloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selo not equals to DEFAULT_SELO
        defaultObraShouldNotBeFound("selo.notEquals=" + DEFAULT_SELO);

        // Get all the obraList where selo not equals to UPDATED_SELO
        defaultObraShouldBeFound("selo.notEquals=" + UPDATED_SELO);
    }

    @Test
    @Transactional
    void getAllObrasBySeloIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selo in DEFAULT_SELO or UPDATED_SELO
        defaultObraShouldBeFound("selo.in=" + DEFAULT_SELO + "," + UPDATED_SELO);

        // Get all the obraList where selo equals to UPDATED_SELO
        defaultObraShouldNotBeFound("selo.in=" + UPDATED_SELO);
    }

    @Test
    @Transactional
    void getAllObrasBySeloIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selo is not null
        defaultObraShouldBeFound("selo.specified=true");

        // Get all the obraList where selo is null
        defaultObraShouldNotBeFound("selo.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasBySeloIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selo is greater than or equal to DEFAULT_SELO
        defaultObraShouldBeFound("selo.greaterThanOrEqual=" + DEFAULT_SELO);

        // Get all the obraList where selo is greater than or equal to UPDATED_SELO
        defaultObraShouldNotBeFound("selo.greaterThanOrEqual=" + UPDATED_SELO);
    }

    @Test
    @Transactional
    void getAllObrasBySeloIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selo is less than or equal to DEFAULT_SELO
        defaultObraShouldBeFound("selo.lessThanOrEqual=" + DEFAULT_SELO);

        // Get all the obraList where selo is less than or equal to SMALLER_SELO
        defaultObraShouldNotBeFound("selo.lessThanOrEqual=" + SMALLER_SELO);
    }

    @Test
    @Transactional
    void getAllObrasBySeloIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selo is less than DEFAULT_SELO
        defaultObraShouldNotBeFound("selo.lessThan=" + DEFAULT_SELO);

        // Get all the obraList where selo is less than UPDATED_SELO
        defaultObraShouldBeFound("selo.lessThan=" + UPDATED_SELO);
    }

    @Test
    @Transactional
    void getAllObrasBySeloIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selo is greater than DEFAULT_SELO
        defaultObraShouldNotBeFound("selo.greaterThan=" + DEFAULT_SELO);

        // Get all the obraList where selo is greater than SMALLER_SELO
        defaultObraShouldBeFound("selo.greaterThan=" + SMALLER_SELO);
    }

    @Test
    @Transactional
    void getAllObrasByTomboAnterioIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tomboAnterio equals to DEFAULT_TOMBO_ANTERIO
        defaultObraShouldBeFound("tomboAnterio.equals=" + DEFAULT_TOMBO_ANTERIO);

        // Get all the obraList where tomboAnterio equals to UPDATED_TOMBO_ANTERIO
        defaultObraShouldNotBeFound("tomboAnterio.equals=" + UPDATED_TOMBO_ANTERIO);
    }

    @Test
    @Transactional
    void getAllObrasByTomboAnterioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tomboAnterio not equals to DEFAULT_TOMBO_ANTERIO
        defaultObraShouldNotBeFound("tomboAnterio.notEquals=" + DEFAULT_TOMBO_ANTERIO);

        // Get all the obraList where tomboAnterio not equals to UPDATED_TOMBO_ANTERIO
        defaultObraShouldBeFound("tomboAnterio.notEquals=" + UPDATED_TOMBO_ANTERIO);
    }

    @Test
    @Transactional
    void getAllObrasByTomboAnterioIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tomboAnterio in DEFAULT_TOMBO_ANTERIO or UPDATED_TOMBO_ANTERIO
        defaultObraShouldBeFound("tomboAnterio.in=" + DEFAULT_TOMBO_ANTERIO + "," + UPDATED_TOMBO_ANTERIO);

        // Get all the obraList where tomboAnterio equals to UPDATED_TOMBO_ANTERIO
        defaultObraShouldNotBeFound("tomboAnterio.in=" + UPDATED_TOMBO_ANTERIO);
    }

    @Test
    @Transactional
    void getAllObrasByTomboAnterioIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tomboAnterio is not null
        defaultObraShouldBeFound("tomboAnterio.specified=true");

        // Get all the obraList where tomboAnterio is null
        defaultObraShouldNotBeFound("tomboAnterio.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByTomboAnterioContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tomboAnterio contains DEFAULT_TOMBO_ANTERIO
        defaultObraShouldBeFound("tomboAnterio.contains=" + DEFAULT_TOMBO_ANTERIO);

        // Get all the obraList where tomboAnterio contains UPDATED_TOMBO_ANTERIO
        defaultObraShouldNotBeFound("tomboAnterio.contains=" + UPDATED_TOMBO_ANTERIO);
    }

    @Test
    @Transactional
    void getAllObrasByTomboAnterioNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tomboAnterio does not contain DEFAULT_TOMBO_ANTERIO
        defaultObraShouldNotBeFound("tomboAnterio.doesNotContain=" + DEFAULT_TOMBO_ANTERIO);

        // Get all the obraList where tomboAnterio does not contain UPDATED_TOMBO_ANTERIO
        defaultObraShouldBeFound("tomboAnterio.doesNotContain=" + UPDATED_TOMBO_ANTERIO);
    }

    @Test
    @Transactional
    void getAllObrasByVerbeteIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where verbete equals to DEFAULT_VERBETE
        defaultObraShouldBeFound("verbete.equals=" + DEFAULT_VERBETE);

        // Get all the obraList where verbete equals to UPDATED_VERBETE
        defaultObraShouldNotBeFound("verbete.equals=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByVerbeteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where verbete not equals to DEFAULT_VERBETE
        defaultObraShouldNotBeFound("verbete.notEquals=" + DEFAULT_VERBETE);

        // Get all the obraList where verbete not equals to UPDATED_VERBETE
        defaultObraShouldBeFound("verbete.notEquals=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByVerbeteIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where verbete in DEFAULT_VERBETE or UPDATED_VERBETE
        defaultObraShouldBeFound("verbete.in=" + DEFAULT_VERBETE + "," + UPDATED_VERBETE);

        // Get all the obraList where verbete equals to UPDATED_VERBETE
        defaultObraShouldNotBeFound("verbete.in=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByVerbeteIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where verbete is not null
        defaultObraShouldBeFound("verbete.specified=true");

        // Get all the obraList where verbete is null
        defaultObraShouldNotBeFound("verbete.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByVerbeteContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where verbete contains DEFAULT_VERBETE
        defaultObraShouldBeFound("verbete.contains=" + DEFAULT_VERBETE);

        // Get all the obraList where verbete contains UPDATED_VERBETE
        defaultObraShouldNotBeFound("verbete.contains=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByVerbeteNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where verbete does not contain DEFAULT_VERBETE
        defaultObraShouldNotBeFound("verbete.doesNotContain=" + DEFAULT_VERBETE);

        // Get all the obraList where verbete does not contain UPDATED_VERBETE
        defaultObraShouldBeFound("verbete.doesNotContain=" + UPDATED_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByLivroIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where livro equals to DEFAULT_LIVRO
        defaultObraShouldBeFound("livro.equals=" + DEFAULT_LIVRO);

        // Get all the obraList where livro equals to UPDATED_LIVRO
        defaultObraShouldNotBeFound("livro.equals=" + UPDATED_LIVRO);
    }

    @Test
    @Transactional
    void getAllObrasByLivroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where livro not equals to DEFAULT_LIVRO
        defaultObraShouldNotBeFound("livro.notEquals=" + DEFAULT_LIVRO);

        // Get all the obraList where livro not equals to UPDATED_LIVRO
        defaultObraShouldBeFound("livro.notEquals=" + UPDATED_LIVRO);
    }

    @Test
    @Transactional
    void getAllObrasByLivroIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where livro in DEFAULT_LIVRO or UPDATED_LIVRO
        defaultObraShouldBeFound("livro.in=" + DEFAULT_LIVRO + "," + UPDATED_LIVRO);

        // Get all the obraList where livro equals to UPDATED_LIVRO
        defaultObraShouldNotBeFound("livro.in=" + UPDATED_LIVRO);
    }

    @Test
    @Transactional
    void getAllObrasByLivroIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where livro is not null
        defaultObraShouldBeFound("livro.specified=true");

        // Get all the obraList where livro is null
        defaultObraShouldNotBeFound("livro.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByImagemAltaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where imagemAlta equals to DEFAULT_IMAGEM_ALTA
        defaultObraShouldBeFound("imagemAlta.equals=" + DEFAULT_IMAGEM_ALTA);

        // Get all the obraList where imagemAlta equals to UPDATED_IMAGEM_ALTA
        defaultObraShouldNotBeFound("imagemAlta.equals=" + UPDATED_IMAGEM_ALTA);
    }

    @Test
    @Transactional
    void getAllObrasByImagemAltaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where imagemAlta not equals to DEFAULT_IMAGEM_ALTA
        defaultObraShouldNotBeFound("imagemAlta.notEquals=" + DEFAULT_IMAGEM_ALTA);

        // Get all the obraList where imagemAlta not equals to UPDATED_IMAGEM_ALTA
        defaultObraShouldBeFound("imagemAlta.notEquals=" + UPDATED_IMAGEM_ALTA);
    }

    @Test
    @Transactional
    void getAllObrasByImagemAltaIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where imagemAlta in DEFAULT_IMAGEM_ALTA or UPDATED_IMAGEM_ALTA
        defaultObraShouldBeFound("imagemAlta.in=" + DEFAULT_IMAGEM_ALTA + "," + UPDATED_IMAGEM_ALTA);

        // Get all the obraList where imagemAlta equals to UPDATED_IMAGEM_ALTA
        defaultObraShouldNotBeFound("imagemAlta.in=" + UPDATED_IMAGEM_ALTA);
    }

    @Test
    @Transactional
    void getAllObrasByImagemAltaIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where imagemAlta is not null
        defaultObraShouldBeFound("imagemAlta.specified=true");

        // Get all the obraList where imagemAlta is null
        defaultObraShouldNotBeFound("imagemAlta.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByApabexIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where apabex equals to DEFAULT_APABEX
        defaultObraShouldBeFound("apabex.equals=" + DEFAULT_APABEX);

        // Get all the obraList where apabex equals to UPDATED_APABEX
        defaultObraShouldNotBeFound("apabex.equals=" + UPDATED_APABEX);
    }

    @Test
    @Transactional
    void getAllObrasByApabexIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where apabex not equals to DEFAULT_APABEX
        defaultObraShouldNotBeFound("apabex.notEquals=" + DEFAULT_APABEX);

        // Get all the obraList where apabex not equals to UPDATED_APABEX
        defaultObraShouldBeFound("apabex.notEquals=" + UPDATED_APABEX);
    }

    @Test
    @Transactional
    void getAllObrasByApabexIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where apabex in DEFAULT_APABEX or UPDATED_APABEX
        defaultObraShouldBeFound("apabex.in=" + DEFAULT_APABEX + "," + UPDATED_APABEX);

        // Get all the obraList where apabex equals to UPDATED_APABEX
        defaultObraShouldNotBeFound("apabex.in=" + UPDATED_APABEX);
    }

    @Test
    @Transactional
    void getAllObrasByApabexIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where apabex is not null
        defaultObraShouldBeFound("apabex.specified=true");

        // Get all the obraList where apabex is null
        defaultObraShouldNotBeFound("apabex.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByBunkyoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bunkyo equals to DEFAULT_BUNKYO
        defaultObraShouldBeFound("bunkyo.equals=" + DEFAULT_BUNKYO);

        // Get all the obraList where bunkyo equals to UPDATED_BUNKYO
        defaultObraShouldNotBeFound("bunkyo.equals=" + UPDATED_BUNKYO);
    }

    @Test
    @Transactional
    void getAllObrasByBunkyoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bunkyo not equals to DEFAULT_BUNKYO
        defaultObraShouldNotBeFound("bunkyo.notEquals=" + DEFAULT_BUNKYO);

        // Get all the obraList where bunkyo not equals to UPDATED_BUNKYO
        defaultObraShouldBeFound("bunkyo.notEquals=" + UPDATED_BUNKYO);
    }

    @Test
    @Transactional
    void getAllObrasByBunkyoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bunkyo in DEFAULT_BUNKYO or UPDATED_BUNKYO
        defaultObraShouldBeFound("bunkyo.in=" + DEFAULT_BUNKYO + "," + UPDATED_BUNKYO);

        // Get all the obraList where bunkyo equals to UPDATED_BUNKYO
        defaultObraShouldNotBeFound("bunkyo.in=" + UPDATED_BUNKYO);
    }

    @Test
    @Transactional
    void getAllObrasByBunkyoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bunkyo is not null
        defaultObraShouldBeFound("bunkyo.specified=true");

        // Get all the obraList where bunkyo is null
        defaultObraShouldNotBeFound("bunkyo.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByFaseDepuracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where faseDepuracao equals to DEFAULT_FASE_DEPURACAO
        defaultObraShouldBeFound("faseDepuracao.equals=" + DEFAULT_FASE_DEPURACAO);

        // Get all the obraList where faseDepuracao equals to UPDATED_FASE_DEPURACAO
        defaultObraShouldNotBeFound("faseDepuracao.equals=" + UPDATED_FASE_DEPURACAO);
    }

    @Test
    @Transactional
    void getAllObrasByFaseDepuracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where faseDepuracao not equals to DEFAULT_FASE_DEPURACAO
        defaultObraShouldNotBeFound("faseDepuracao.notEquals=" + DEFAULT_FASE_DEPURACAO);

        // Get all the obraList where faseDepuracao not equals to UPDATED_FASE_DEPURACAO
        defaultObraShouldBeFound("faseDepuracao.notEquals=" + UPDATED_FASE_DEPURACAO);
    }

    @Test
    @Transactional
    void getAllObrasByFaseDepuracaoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where faseDepuracao in DEFAULT_FASE_DEPURACAO or UPDATED_FASE_DEPURACAO
        defaultObraShouldBeFound("faseDepuracao.in=" + DEFAULT_FASE_DEPURACAO + "," + UPDATED_FASE_DEPURACAO);

        // Get all the obraList where faseDepuracao equals to UPDATED_FASE_DEPURACAO
        defaultObraShouldNotBeFound("faseDepuracao.in=" + UPDATED_FASE_DEPURACAO);
    }

    @Test
    @Transactional
    void getAllObrasByFaseDepuracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where faseDepuracao is not null
        defaultObraShouldBeFound("faseDepuracao.specified=true");

        // Get all the obraList where faseDepuracao is null
        defaultObraShouldNotBeFound("faseDepuracao.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByFaseDepuracaoContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where faseDepuracao contains DEFAULT_FASE_DEPURACAO
        defaultObraShouldBeFound("faseDepuracao.contains=" + DEFAULT_FASE_DEPURACAO);

        // Get all the obraList where faseDepuracao contains UPDATED_FASE_DEPURACAO
        defaultObraShouldNotBeFound("faseDepuracao.contains=" + UPDATED_FASE_DEPURACAO);
    }

    @Test
    @Transactional
    void getAllObrasByFaseDepuracaoNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where faseDepuracao does not contain DEFAULT_FASE_DEPURACAO
        defaultObraShouldNotBeFound("faseDepuracao.doesNotContain=" + DEFAULT_FASE_DEPURACAO);

        // Get all the obraList where faseDepuracao does not contain UPDATED_FASE_DEPURACAO
        defaultObraShouldBeFound("faseDepuracao.doesNotContain=" + UPDATED_FASE_DEPURACAO);
    }

    @Test
    @Transactional
    void getAllObrasByParaAvaliacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraAvaliacao equals to DEFAULT_PARA_AVALIACAO
        defaultObraShouldBeFound("paraAvaliacao.equals=" + DEFAULT_PARA_AVALIACAO);

        // Get all the obraList where paraAvaliacao equals to UPDATED_PARA_AVALIACAO
        defaultObraShouldNotBeFound("paraAvaliacao.equals=" + UPDATED_PARA_AVALIACAO);
    }

    @Test
    @Transactional
    void getAllObrasByParaAvaliacaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraAvaliacao not equals to DEFAULT_PARA_AVALIACAO
        defaultObraShouldNotBeFound("paraAvaliacao.notEquals=" + DEFAULT_PARA_AVALIACAO);

        // Get all the obraList where paraAvaliacao not equals to UPDATED_PARA_AVALIACAO
        defaultObraShouldBeFound("paraAvaliacao.notEquals=" + UPDATED_PARA_AVALIACAO);
    }

    @Test
    @Transactional
    void getAllObrasByParaAvaliacaoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraAvaliacao in DEFAULT_PARA_AVALIACAO or UPDATED_PARA_AVALIACAO
        defaultObraShouldBeFound("paraAvaliacao.in=" + DEFAULT_PARA_AVALIACAO + "," + UPDATED_PARA_AVALIACAO);

        // Get all the obraList where paraAvaliacao equals to UPDATED_PARA_AVALIACAO
        defaultObraShouldNotBeFound("paraAvaliacao.in=" + UPDATED_PARA_AVALIACAO);
    }

    @Test
    @Transactional
    void getAllObrasByParaAvaliacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraAvaliacao is not null
        defaultObraShouldBeFound("paraAvaliacao.specified=true");

        // Get all the obraList where paraAvaliacao is null
        defaultObraShouldNotBeFound("paraAvaliacao.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByParaRestauracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraRestauracao equals to DEFAULT_PARA_RESTAURACAO
        defaultObraShouldBeFound("paraRestauracao.equals=" + DEFAULT_PARA_RESTAURACAO);

        // Get all the obraList where paraRestauracao equals to UPDATED_PARA_RESTAURACAO
        defaultObraShouldNotBeFound("paraRestauracao.equals=" + UPDATED_PARA_RESTAURACAO);
    }

    @Test
    @Transactional
    void getAllObrasByParaRestauracaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraRestauracao not equals to DEFAULT_PARA_RESTAURACAO
        defaultObraShouldNotBeFound("paraRestauracao.notEquals=" + DEFAULT_PARA_RESTAURACAO);

        // Get all the obraList where paraRestauracao not equals to UPDATED_PARA_RESTAURACAO
        defaultObraShouldBeFound("paraRestauracao.notEquals=" + UPDATED_PARA_RESTAURACAO);
    }

    @Test
    @Transactional
    void getAllObrasByParaRestauracaoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraRestauracao in DEFAULT_PARA_RESTAURACAO or UPDATED_PARA_RESTAURACAO
        defaultObraShouldBeFound("paraRestauracao.in=" + DEFAULT_PARA_RESTAURACAO + "," + UPDATED_PARA_RESTAURACAO);

        // Get all the obraList where paraRestauracao equals to UPDATED_PARA_RESTAURACAO
        defaultObraShouldNotBeFound("paraRestauracao.in=" + UPDATED_PARA_RESTAURACAO);
    }

    @Test
    @Transactional
    void getAllObrasByParaRestauracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraRestauracao is not null
        defaultObraShouldBeFound("paraRestauracao.specified=true");

        // Get all the obraList where paraRestauracao is null
        defaultObraShouldNotBeFound("paraRestauracao.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByParaMolduraIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraMoldura equals to DEFAULT_PARA_MOLDURA
        defaultObraShouldBeFound("paraMoldura.equals=" + DEFAULT_PARA_MOLDURA);

        // Get all the obraList where paraMoldura equals to UPDATED_PARA_MOLDURA
        defaultObraShouldNotBeFound("paraMoldura.equals=" + UPDATED_PARA_MOLDURA);
    }

    @Test
    @Transactional
    void getAllObrasByParaMolduraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraMoldura not equals to DEFAULT_PARA_MOLDURA
        defaultObraShouldNotBeFound("paraMoldura.notEquals=" + DEFAULT_PARA_MOLDURA);

        // Get all the obraList where paraMoldura not equals to UPDATED_PARA_MOLDURA
        defaultObraShouldBeFound("paraMoldura.notEquals=" + UPDATED_PARA_MOLDURA);
    }

    @Test
    @Transactional
    void getAllObrasByParaMolduraIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraMoldura in DEFAULT_PARA_MOLDURA or UPDATED_PARA_MOLDURA
        defaultObraShouldBeFound("paraMoldura.in=" + DEFAULT_PARA_MOLDURA + "," + UPDATED_PARA_MOLDURA);

        // Get all the obraList where paraMoldura equals to UPDATED_PARA_MOLDURA
        defaultObraShouldNotBeFound("paraMoldura.in=" + UPDATED_PARA_MOLDURA);
    }

    @Test
    @Transactional
    void getAllObrasByParaMolduraIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraMoldura is not null
        defaultObraShouldBeFound("paraMoldura.specified=true");

        // Get all the obraList where paraMoldura is null
        defaultObraShouldNotBeFound("paraMoldura.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByParaLegendaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraLegenda equals to DEFAULT_PARA_LEGENDA
        defaultObraShouldBeFound("paraLegenda.equals=" + DEFAULT_PARA_LEGENDA);

        // Get all the obraList where paraLegenda equals to UPDATED_PARA_LEGENDA
        defaultObraShouldNotBeFound("paraLegenda.equals=" + UPDATED_PARA_LEGENDA);
    }

    @Test
    @Transactional
    void getAllObrasByParaLegendaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraLegenda not equals to DEFAULT_PARA_LEGENDA
        defaultObraShouldNotBeFound("paraLegenda.notEquals=" + DEFAULT_PARA_LEGENDA);

        // Get all the obraList where paraLegenda not equals to UPDATED_PARA_LEGENDA
        defaultObraShouldBeFound("paraLegenda.notEquals=" + UPDATED_PARA_LEGENDA);
    }

    @Test
    @Transactional
    void getAllObrasByParaLegendaIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraLegenda in DEFAULT_PARA_LEGENDA or UPDATED_PARA_LEGENDA
        defaultObraShouldBeFound("paraLegenda.in=" + DEFAULT_PARA_LEGENDA + "," + UPDATED_PARA_LEGENDA);

        // Get all the obraList where paraLegenda equals to UPDATED_PARA_LEGENDA
        defaultObraShouldNotBeFound("paraLegenda.in=" + UPDATED_PARA_LEGENDA);
    }

    @Test
    @Transactional
    void getAllObrasByParaLegendaIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraLegenda is not null
        defaultObraShouldBeFound("paraLegenda.specified=true");

        // Get all the obraList where paraLegenda is null
        defaultObraShouldNotBeFound("paraLegenda.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByTempFieldIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tempField equals to DEFAULT_TEMP_FIELD
        defaultObraShouldBeFound("tempField.equals=" + DEFAULT_TEMP_FIELD);

        // Get all the obraList where tempField equals to UPDATED_TEMP_FIELD
        defaultObraShouldNotBeFound("tempField.equals=" + UPDATED_TEMP_FIELD);
    }

    @Test
    @Transactional
    void getAllObrasByTempFieldIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tempField not equals to DEFAULT_TEMP_FIELD
        defaultObraShouldNotBeFound("tempField.notEquals=" + DEFAULT_TEMP_FIELD);

        // Get all the obraList where tempField not equals to UPDATED_TEMP_FIELD
        defaultObraShouldBeFound("tempField.notEquals=" + UPDATED_TEMP_FIELD);
    }

    @Test
    @Transactional
    void getAllObrasByTempFieldIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tempField in DEFAULT_TEMP_FIELD or UPDATED_TEMP_FIELD
        defaultObraShouldBeFound("tempField.in=" + DEFAULT_TEMP_FIELD + "," + UPDATED_TEMP_FIELD);

        // Get all the obraList where tempField equals to UPDATED_TEMP_FIELD
        defaultObraShouldNotBeFound("tempField.in=" + UPDATED_TEMP_FIELD);
    }

    @Test
    @Transactional
    void getAllObrasByTempFieldIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where tempField is not null
        defaultObraShouldBeFound("tempField.specified=true");

        // Get all the obraList where tempField is null
        defaultObraShouldNotBeFound("tempField.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasBySelecaoListaAvulsaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selecaoListaAvulsa equals to DEFAULT_SELECAO_LISTA_AVULSA
        defaultObraShouldBeFound("selecaoListaAvulsa.equals=" + DEFAULT_SELECAO_LISTA_AVULSA);

        // Get all the obraList where selecaoListaAvulsa equals to UPDATED_SELECAO_LISTA_AVULSA
        defaultObraShouldNotBeFound("selecaoListaAvulsa.equals=" + UPDATED_SELECAO_LISTA_AVULSA);
    }

    @Test
    @Transactional
    void getAllObrasBySelecaoListaAvulsaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selecaoListaAvulsa not equals to DEFAULT_SELECAO_LISTA_AVULSA
        defaultObraShouldNotBeFound("selecaoListaAvulsa.notEquals=" + DEFAULT_SELECAO_LISTA_AVULSA);

        // Get all the obraList where selecaoListaAvulsa not equals to UPDATED_SELECAO_LISTA_AVULSA
        defaultObraShouldBeFound("selecaoListaAvulsa.notEquals=" + UPDATED_SELECAO_LISTA_AVULSA);
    }

    @Test
    @Transactional
    void getAllObrasBySelecaoListaAvulsaIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selecaoListaAvulsa in DEFAULT_SELECAO_LISTA_AVULSA or UPDATED_SELECAO_LISTA_AVULSA
        defaultObraShouldBeFound("selecaoListaAvulsa.in=" + DEFAULT_SELECAO_LISTA_AVULSA + "," + UPDATED_SELECAO_LISTA_AVULSA);

        // Get all the obraList where selecaoListaAvulsa equals to UPDATED_SELECAO_LISTA_AVULSA
        defaultObraShouldNotBeFound("selecaoListaAvulsa.in=" + UPDATED_SELECAO_LISTA_AVULSA);
    }

    @Test
    @Transactional
    void getAllObrasBySelecaoListaAvulsaIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where selecaoListaAvulsa is not null
        defaultObraShouldBeFound("selecaoListaAvulsa.specified=true");

        // Get all the obraList where selecaoListaAvulsa is null
        defaultObraShouldNotBeFound("selecaoListaAvulsa.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDominioPublIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dominioPubl equals to DEFAULT_DOMINIO_PUBL
        defaultObraShouldBeFound("dominioPubl.equals=" + DEFAULT_DOMINIO_PUBL);

        // Get all the obraList where dominioPubl equals to UPDATED_DOMINIO_PUBL
        defaultObraShouldNotBeFound("dominioPubl.equals=" + UPDATED_DOMINIO_PUBL);
    }

    @Test
    @Transactional
    void getAllObrasByDominioPublIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dominioPubl not equals to DEFAULT_DOMINIO_PUBL
        defaultObraShouldNotBeFound("dominioPubl.notEquals=" + DEFAULT_DOMINIO_PUBL);

        // Get all the obraList where dominioPubl not equals to UPDATED_DOMINIO_PUBL
        defaultObraShouldBeFound("dominioPubl.notEquals=" + UPDATED_DOMINIO_PUBL);
    }

    @Test
    @Transactional
    void getAllObrasByDominioPublIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dominioPubl in DEFAULT_DOMINIO_PUBL or UPDATED_DOMINIO_PUBL
        defaultObraShouldBeFound("dominioPubl.in=" + DEFAULT_DOMINIO_PUBL + "," + UPDATED_DOMINIO_PUBL);

        // Get all the obraList where dominioPubl equals to UPDATED_DOMINIO_PUBL
        defaultObraShouldNotBeFound("dominioPubl.in=" + UPDATED_DOMINIO_PUBL);
    }

    @Test
    @Transactional
    void getAllObrasByDominioPublIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dominioPubl is not null
        defaultObraShouldBeFound("dominioPubl.specified=true");

        // Get all the obraList where dominioPubl is null
        defaultObraShouldNotBeFound("dominioPubl.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDtVencFotoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dtVencFoto equals to DEFAULT_DT_VENC_FOTO
        defaultObraShouldBeFound("dtVencFoto.equals=" + DEFAULT_DT_VENC_FOTO);

        // Get all the obraList where dtVencFoto equals to UPDATED_DT_VENC_FOTO
        defaultObraShouldNotBeFound("dtVencFoto.equals=" + UPDATED_DT_VENC_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByDtVencFotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dtVencFoto not equals to DEFAULT_DT_VENC_FOTO
        defaultObraShouldNotBeFound("dtVencFoto.notEquals=" + DEFAULT_DT_VENC_FOTO);

        // Get all the obraList where dtVencFoto not equals to UPDATED_DT_VENC_FOTO
        defaultObraShouldBeFound("dtVencFoto.notEquals=" + UPDATED_DT_VENC_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByDtVencFotoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dtVencFoto in DEFAULT_DT_VENC_FOTO or UPDATED_DT_VENC_FOTO
        defaultObraShouldBeFound("dtVencFoto.in=" + DEFAULT_DT_VENC_FOTO + "," + UPDATED_DT_VENC_FOTO);

        // Get all the obraList where dtVencFoto equals to UPDATED_DT_VENC_FOTO
        defaultObraShouldNotBeFound("dtVencFoto.in=" + UPDATED_DT_VENC_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByDtVencFotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dtVencFoto is not null
        defaultObraShouldBeFound("dtVencFoto.specified=true");

        // Get all the obraList where dtVencFoto is null
        defaultObraShouldNotBeFound("dtVencFoto.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDtVencFotoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dtVencFoto is greater than or equal to DEFAULT_DT_VENC_FOTO
        defaultObraShouldBeFound("dtVencFoto.greaterThanOrEqual=" + DEFAULT_DT_VENC_FOTO);

        // Get all the obraList where dtVencFoto is greater than or equal to UPDATED_DT_VENC_FOTO
        defaultObraShouldNotBeFound("dtVencFoto.greaterThanOrEqual=" + UPDATED_DT_VENC_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByDtVencFotoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dtVencFoto is less than or equal to DEFAULT_DT_VENC_FOTO
        defaultObraShouldBeFound("dtVencFoto.lessThanOrEqual=" + DEFAULT_DT_VENC_FOTO);

        // Get all the obraList where dtVencFoto is less than or equal to SMALLER_DT_VENC_FOTO
        defaultObraShouldNotBeFound("dtVencFoto.lessThanOrEqual=" + SMALLER_DT_VENC_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByDtVencFotoIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dtVencFoto is less than DEFAULT_DT_VENC_FOTO
        defaultObraShouldNotBeFound("dtVencFoto.lessThan=" + DEFAULT_DT_VENC_FOTO);

        // Get all the obraList where dtVencFoto is less than UPDATED_DT_VENC_FOTO
        defaultObraShouldBeFound("dtVencFoto.lessThan=" + UPDATED_DT_VENC_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByDtVencFotoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dtVencFoto is greater than DEFAULT_DT_VENC_FOTO
        defaultObraShouldNotBeFound("dtVencFoto.greaterThan=" + DEFAULT_DT_VENC_FOTO);

        // Get all the obraList where dtVencFoto is greater than SMALLER_DT_VENC_FOTO
        defaultObraShouldBeFound("dtVencFoto.greaterThan=" + SMALLER_DT_VENC_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByObsUsoFotoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obsUsoFoto equals to DEFAULT_OBS_USO_FOTO
        defaultObraShouldBeFound("obsUsoFoto.equals=" + DEFAULT_OBS_USO_FOTO);

        // Get all the obraList where obsUsoFoto equals to UPDATED_OBS_USO_FOTO
        defaultObraShouldNotBeFound("obsUsoFoto.equals=" + UPDATED_OBS_USO_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByObsUsoFotoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obsUsoFoto not equals to DEFAULT_OBS_USO_FOTO
        defaultObraShouldNotBeFound("obsUsoFoto.notEquals=" + DEFAULT_OBS_USO_FOTO);

        // Get all the obraList where obsUsoFoto not equals to UPDATED_OBS_USO_FOTO
        defaultObraShouldBeFound("obsUsoFoto.notEquals=" + UPDATED_OBS_USO_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByObsUsoFotoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obsUsoFoto in DEFAULT_OBS_USO_FOTO or UPDATED_OBS_USO_FOTO
        defaultObraShouldBeFound("obsUsoFoto.in=" + DEFAULT_OBS_USO_FOTO + "," + UPDATED_OBS_USO_FOTO);

        // Get all the obraList where obsUsoFoto equals to UPDATED_OBS_USO_FOTO
        defaultObraShouldNotBeFound("obsUsoFoto.in=" + UPDATED_OBS_USO_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByObsUsoFotoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obsUsoFoto is not null
        defaultObraShouldBeFound("obsUsoFoto.specified=true");

        // Get all the obraList where obsUsoFoto is null
        defaultObraShouldNotBeFound("obsUsoFoto.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByObsUsoFotoContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obsUsoFoto contains DEFAULT_OBS_USO_FOTO
        defaultObraShouldBeFound("obsUsoFoto.contains=" + DEFAULT_OBS_USO_FOTO);

        // Get all the obraList where obsUsoFoto contains UPDATED_OBS_USO_FOTO
        defaultObraShouldNotBeFound("obsUsoFoto.contains=" + UPDATED_OBS_USO_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByObsUsoFotoNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where obsUsoFoto does not contain DEFAULT_OBS_USO_FOTO
        defaultObraShouldNotBeFound("obsUsoFoto.doesNotContain=" + DEFAULT_OBS_USO_FOTO);

        // Get all the obraList where obsUsoFoto does not contain UPDATED_OBS_USO_FOTO
        defaultObraShouldBeFound("obsUsoFoto.doesNotContain=" + UPDATED_OBS_USO_FOTO);
    }

    @Test
    @Transactional
    void getAllObrasByLocalFotoAltaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localFotoAlta equals to DEFAULT_LOCAL_FOTO_ALTA
        defaultObraShouldBeFound("localFotoAlta.equals=" + DEFAULT_LOCAL_FOTO_ALTA);

        // Get all the obraList where localFotoAlta equals to UPDATED_LOCAL_FOTO_ALTA
        defaultObraShouldNotBeFound("localFotoAlta.equals=" + UPDATED_LOCAL_FOTO_ALTA);
    }

    @Test
    @Transactional
    void getAllObrasByLocalFotoAltaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localFotoAlta not equals to DEFAULT_LOCAL_FOTO_ALTA
        defaultObraShouldNotBeFound("localFotoAlta.notEquals=" + DEFAULT_LOCAL_FOTO_ALTA);

        // Get all the obraList where localFotoAlta not equals to UPDATED_LOCAL_FOTO_ALTA
        defaultObraShouldBeFound("localFotoAlta.notEquals=" + UPDATED_LOCAL_FOTO_ALTA);
    }

    @Test
    @Transactional
    void getAllObrasByLocalFotoAltaIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localFotoAlta in DEFAULT_LOCAL_FOTO_ALTA or UPDATED_LOCAL_FOTO_ALTA
        defaultObraShouldBeFound("localFotoAlta.in=" + DEFAULT_LOCAL_FOTO_ALTA + "," + UPDATED_LOCAL_FOTO_ALTA);

        // Get all the obraList where localFotoAlta equals to UPDATED_LOCAL_FOTO_ALTA
        defaultObraShouldNotBeFound("localFotoAlta.in=" + UPDATED_LOCAL_FOTO_ALTA);
    }

    @Test
    @Transactional
    void getAllObrasByLocalFotoAltaIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localFotoAlta is not null
        defaultObraShouldBeFound("localFotoAlta.specified=true");

        // Get all the obraList where localFotoAlta is null
        defaultObraShouldNotBeFound("localFotoAlta.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByLocalFotoAltaContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localFotoAlta contains DEFAULT_LOCAL_FOTO_ALTA
        defaultObraShouldBeFound("localFotoAlta.contains=" + DEFAULT_LOCAL_FOTO_ALTA);

        // Get all the obraList where localFotoAlta contains UPDATED_LOCAL_FOTO_ALTA
        defaultObraShouldNotBeFound("localFotoAlta.contains=" + UPDATED_LOCAL_FOTO_ALTA);
    }

    @Test
    @Transactional
    void getAllObrasByLocalFotoAltaNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where localFotoAlta does not contain DEFAULT_LOCAL_FOTO_ALTA
        defaultObraShouldNotBeFound("localFotoAlta.doesNotContain=" + DEFAULT_LOCAL_FOTO_ALTA);

        // Get all the obraList where localFotoAlta does not contain UPDATED_LOCAL_FOTO_ALTA
        defaultObraShouldBeFound("localFotoAlta.doesNotContain=" + UPDATED_LOCAL_FOTO_ALTA);
    }

    @Test
    @Transactional
    void getAllObrasByDataInclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataInclusao equals to DEFAULT_DATA_INCLUSAO
        defaultObraShouldBeFound("dataInclusao.equals=" + DEFAULT_DATA_INCLUSAO);

        // Get all the obraList where dataInclusao equals to UPDATED_DATA_INCLUSAO
        defaultObraShouldNotBeFound("dataInclusao.equals=" + UPDATED_DATA_INCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataInclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataInclusao not equals to DEFAULT_DATA_INCLUSAO
        defaultObraShouldNotBeFound("dataInclusao.notEquals=" + DEFAULT_DATA_INCLUSAO);

        // Get all the obraList where dataInclusao not equals to UPDATED_DATA_INCLUSAO
        defaultObraShouldBeFound("dataInclusao.notEquals=" + UPDATED_DATA_INCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataInclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataInclusao in DEFAULT_DATA_INCLUSAO or UPDATED_DATA_INCLUSAO
        defaultObraShouldBeFound("dataInclusao.in=" + DEFAULT_DATA_INCLUSAO + "," + UPDATED_DATA_INCLUSAO);

        // Get all the obraList where dataInclusao equals to UPDATED_DATA_INCLUSAO
        defaultObraShouldNotBeFound("dataInclusao.in=" + UPDATED_DATA_INCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataInclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataInclusao is not null
        defaultObraShouldBeFound("dataInclusao.specified=true");

        // Get all the obraList where dataInclusao is null
        defaultObraShouldNotBeFound("dataInclusao.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDataInclusaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataInclusao is greater than or equal to DEFAULT_DATA_INCLUSAO
        defaultObraShouldBeFound("dataInclusao.greaterThanOrEqual=" + DEFAULT_DATA_INCLUSAO);

        // Get all the obraList where dataInclusao is greater than or equal to UPDATED_DATA_INCLUSAO
        defaultObraShouldNotBeFound("dataInclusao.greaterThanOrEqual=" + UPDATED_DATA_INCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataInclusaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataInclusao is less than or equal to DEFAULT_DATA_INCLUSAO
        defaultObraShouldBeFound("dataInclusao.lessThanOrEqual=" + DEFAULT_DATA_INCLUSAO);

        // Get all the obraList where dataInclusao is less than or equal to SMALLER_DATA_INCLUSAO
        defaultObraShouldNotBeFound("dataInclusao.lessThanOrEqual=" + SMALLER_DATA_INCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataInclusaoIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataInclusao is less than DEFAULT_DATA_INCLUSAO
        defaultObraShouldNotBeFound("dataInclusao.lessThan=" + DEFAULT_DATA_INCLUSAO);

        // Get all the obraList where dataInclusao is less than UPDATED_DATA_INCLUSAO
        defaultObraShouldBeFound("dataInclusao.lessThan=" + UPDATED_DATA_INCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataInclusaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataInclusao is greater than DEFAULT_DATA_INCLUSAO
        defaultObraShouldNotBeFound("dataInclusao.greaterThan=" + DEFAULT_DATA_INCLUSAO);

        // Get all the obraList where dataInclusao is greater than SMALLER_DATA_INCLUSAO
        defaultObraShouldBeFound("dataInclusao.greaterThan=" + SMALLER_DATA_INCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataExclusaoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataExclusao equals to DEFAULT_DATA_EXCLUSAO
        defaultObraShouldBeFound("dataExclusao.equals=" + DEFAULT_DATA_EXCLUSAO);

        // Get all the obraList where dataExclusao equals to UPDATED_DATA_EXCLUSAO
        defaultObraShouldNotBeFound("dataExclusao.equals=" + UPDATED_DATA_EXCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataExclusaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataExclusao not equals to DEFAULT_DATA_EXCLUSAO
        defaultObraShouldNotBeFound("dataExclusao.notEquals=" + DEFAULT_DATA_EXCLUSAO);

        // Get all the obraList where dataExclusao not equals to UPDATED_DATA_EXCLUSAO
        defaultObraShouldBeFound("dataExclusao.notEquals=" + UPDATED_DATA_EXCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataExclusaoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataExclusao in DEFAULT_DATA_EXCLUSAO or UPDATED_DATA_EXCLUSAO
        defaultObraShouldBeFound("dataExclusao.in=" + DEFAULT_DATA_EXCLUSAO + "," + UPDATED_DATA_EXCLUSAO);

        // Get all the obraList where dataExclusao equals to UPDATED_DATA_EXCLUSAO
        defaultObraShouldNotBeFound("dataExclusao.in=" + UPDATED_DATA_EXCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataExclusaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataExclusao is not null
        defaultObraShouldBeFound("dataExclusao.specified=true");

        // Get all the obraList where dataExclusao is null
        defaultObraShouldNotBeFound("dataExclusao.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByDataExclusaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataExclusao is greater than or equal to DEFAULT_DATA_EXCLUSAO
        defaultObraShouldBeFound("dataExclusao.greaterThanOrEqual=" + DEFAULT_DATA_EXCLUSAO);

        // Get all the obraList where dataExclusao is greater than or equal to UPDATED_DATA_EXCLUSAO
        defaultObraShouldNotBeFound("dataExclusao.greaterThanOrEqual=" + UPDATED_DATA_EXCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataExclusaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataExclusao is less than or equal to DEFAULT_DATA_EXCLUSAO
        defaultObraShouldBeFound("dataExclusao.lessThanOrEqual=" + DEFAULT_DATA_EXCLUSAO);

        // Get all the obraList where dataExclusao is less than or equal to SMALLER_DATA_EXCLUSAO
        defaultObraShouldNotBeFound("dataExclusao.lessThanOrEqual=" + SMALLER_DATA_EXCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataExclusaoIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataExclusao is less than DEFAULT_DATA_EXCLUSAO
        defaultObraShouldNotBeFound("dataExclusao.lessThan=" + DEFAULT_DATA_EXCLUSAO);

        // Get all the obraList where dataExclusao is less than UPDATED_DATA_EXCLUSAO
        defaultObraShouldBeFound("dataExclusao.lessThan=" + UPDATED_DATA_EXCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByDataExclusaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where dataExclusao is greater than DEFAULT_DATA_EXCLUSAO
        defaultObraShouldNotBeFound("dataExclusao.greaterThan=" + DEFAULT_DATA_EXCLUSAO);

        // Get all the obraList where dataExclusao is greater than SMALLER_DATA_EXCLUSAO
        defaultObraShouldBeFound("dataExclusao.greaterThan=" + SMALLER_DATA_EXCLUSAO);
    }

    @Test
    @Transactional
    void getAllObrasByBookXIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookX equals to DEFAULT_BOOK_X
        defaultObraShouldBeFound("bookX.equals=" + DEFAULT_BOOK_X);

        // Get all the obraList where bookX equals to UPDATED_BOOK_X
        defaultObraShouldNotBeFound("bookX.equals=" + UPDATED_BOOK_X);
    }

    @Test
    @Transactional
    void getAllObrasByBookXIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookX not equals to DEFAULT_BOOK_X
        defaultObraShouldNotBeFound("bookX.notEquals=" + DEFAULT_BOOK_X);

        // Get all the obraList where bookX not equals to UPDATED_BOOK_X
        defaultObraShouldBeFound("bookX.notEquals=" + UPDATED_BOOK_X);
    }

    @Test
    @Transactional
    void getAllObrasByBookXIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookX in DEFAULT_BOOK_X or UPDATED_BOOK_X
        defaultObraShouldBeFound("bookX.in=" + DEFAULT_BOOK_X + "," + UPDATED_BOOK_X);

        // Get all the obraList where bookX equals to UPDATED_BOOK_X
        defaultObraShouldNotBeFound("bookX.in=" + UPDATED_BOOK_X);
    }

    @Test
    @Transactional
    void getAllObrasByBookXIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookX is not null
        defaultObraShouldBeFound("bookX.specified=true");

        // Get all the obraList where bookX is null
        defaultObraShouldNotBeFound("bookX.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByBookXIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookX is greater than or equal to DEFAULT_BOOK_X
        defaultObraShouldBeFound("bookX.greaterThanOrEqual=" + DEFAULT_BOOK_X);

        // Get all the obraList where bookX is greater than or equal to UPDATED_BOOK_X
        defaultObraShouldNotBeFound("bookX.greaterThanOrEqual=" + UPDATED_BOOK_X);
    }

    @Test
    @Transactional
    void getAllObrasByBookXIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookX is less than or equal to DEFAULT_BOOK_X
        defaultObraShouldBeFound("bookX.lessThanOrEqual=" + DEFAULT_BOOK_X);

        // Get all the obraList where bookX is less than or equal to SMALLER_BOOK_X
        defaultObraShouldNotBeFound("bookX.lessThanOrEqual=" + SMALLER_BOOK_X);
    }

    @Test
    @Transactional
    void getAllObrasByBookXIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookX is less than DEFAULT_BOOK_X
        defaultObraShouldNotBeFound("bookX.lessThan=" + DEFAULT_BOOK_X);

        // Get all the obraList where bookX is less than UPDATED_BOOK_X
        defaultObraShouldBeFound("bookX.lessThan=" + UPDATED_BOOK_X);
    }

    @Test
    @Transactional
    void getAllObrasByBookXIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookX is greater than DEFAULT_BOOK_X
        defaultObraShouldNotBeFound("bookX.greaterThan=" + DEFAULT_BOOK_X);

        // Get all the obraList where bookX is greater than SMALLER_BOOK_X
        defaultObraShouldBeFound("bookX.greaterThan=" + SMALLER_BOOK_X);
    }

    @Test
    @Transactional
    void getAllObrasByBookYIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookY equals to DEFAULT_BOOK_Y
        defaultObraShouldBeFound("bookY.equals=" + DEFAULT_BOOK_Y);

        // Get all the obraList where bookY equals to UPDATED_BOOK_Y
        defaultObraShouldNotBeFound("bookY.equals=" + UPDATED_BOOK_Y);
    }

    @Test
    @Transactional
    void getAllObrasByBookYIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookY not equals to DEFAULT_BOOK_Y
        defaultObraShouldNotBeFound("bookY.notEquals=" + DEFAULT_BOOK_Y);

        // Get all the obraList where bookY not equals to UPDATED_BOOK_Y
        defaultObraShouldBeFound("bookY.notEquals=" + UPDATED_BOOK_Y);
    }

    @Test
    @Transactional
    void getAllObrasByBookYIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookY in DEFAULT_BOOK_Y or UPDATED_BOOK_Y
        defaultObraShouldBeFound("bookY.in=" + DEFAULT_BOOK_Y + "," + UPDATED_BOOK_Y);

        // Get all the obraList where bookY equals to UPDATED_BOOK_Y
        defaultObraShouldNotBeFound("bookY.in=" + UPDATED_BOOK_Y);
    }

    @Test
    @Transactional
    void getAllObrasByBookYIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookY is not null
        defaultObraShouldBeFound("bookY.specified=true");

        // Get all the obraList where bookY is null
        defaultObraShouldNotBeFound("bookY.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByBookYIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookY is greater than or equal to DEFAULT_BOOK_Y
        defaultObraShouldBeFound("bookY.greaterThanOrEqual=" + DEFAULT_BOOK_Y);

        // Get all the obraList where bookY is greater than or equal to UPDATED_BOOK_Y
        defaultObraShouldNotBeFound("bookY.greaterThanOrEqual=" + UPDATED_BOOK_Y);
    }

    @Test
    @Transactional
    void getAllObrasByBookYIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookY is less than or equal to DEFAULT_BOOK_Y
        defaultObraShouldBeFound("bookY.lessThanOrEqual=" + DEFAULT_BOOK_Y);

        // Get all the obraList where bookY is less than or equal to SMALLER_BOOK_Y
        defaultObraShouldNotBeFound("bookY.lessThanOrEqual=" + SMALLER_BOOK_Y);
    }

    @Test
    @Transactional
    void getAllObrasByBookYIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookY is less than DEFAULT_BOOK_Y
        defaultObraShouldNotBeFound("bookY.lessThan=" + DEFAULT_BOOK_Y);

        // Get all the obraList where bookY is less than UPDATED_BOOK_Y
        defaultObraShouldBeFound("bookY.lessThan=" + UPDATED_BOOK_Y);
    }

    @Test
    @Transactional
    void getAllObrasByBookYIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where bookY is greater than DEFAULT_BOOK_Y
        defaultObraShouldNotBeFound("bookY.greaterThan=" + DEFAULT_BOOK_Y);

        // Get all the obraList where bookY is greater than SMALLER_BOOK_Y
        defaultObraShouldBeFound("bookY.greaterThan=" + SMALLER_BOOK_Y);
    }

    @Test
    @Transactional
    void getAllObrasByGenDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genDescricao equals to DEFAULT_GEN_DESCRICAO
        defaultObraShouldBeFound("genDescricao.equals=" + DEFAULT_GEN_DESCRICAO);

        // Get all the obraList where genDescricao equals to UPDATED_GEN_DESCRICAO
        defaultObraShouldNotBeFound("genDescricao.equals=" + UPDATED_GEN_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByGenDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genDescricao not equals to DEFAULT_GEN_DESCRICAO
        defaultObraShouldNotBeFound("genDescricao.notEquals=" + DEFAULT_GEN_DESCRICAO);

        // Get all the obraList where genDescricao not equals to UPDATED_GEN_DESCRICAO
        defaultObraShouldBeFound("genDescricao.notEquals=" + UPDATED_GEN_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByGenDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genDescricao in DEFAULT_GEN_DESCRICAO or UPDATED_GEN_DESCRICAO
        defaultObraShouldBeFound("genDescricao.in=" + DEFAULT_GEN_DESCRICAO + "," + UPDATED_GEN_DESCRICAO);

        // Get all the obraList where genDescricao equals to UPDATED_GEN_DESCRICAO
        defaultObraShouldNotBeFound("genDescricao.in=" + UPDATED_GEN_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByGenDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genDescricao is not null
        defaultObraShouldBeFound("genDescricao.specified=true");

        // Get all the obraList where genDescricao is null
        defaultObraShouldNotBeFound("genDescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByGenDescricaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genDescricao is greater than or equal to DEFAULT_GEN_DESCRICAO
        defaultObraShouldBeFound("genDescricao.greaterThanOrEqual=" + DEFAULT_GEN_DESCRICAO);

        // Get all the obraList where genDescricao is greater than or equal to UPDATED_GEN_DESCRICAO
        defaultObraShouldNotBeFound("genDescricao.greaterThanOrEqual=" + UPDATED_GEN_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByGenDescricaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genDescricao is less than or equal to DEFAULT_GEN_DESCRICAO
        defaultObraShouldBeFound("genDescricao.lessThanOrEqual=" + DEFAULT_GEN_DESCRICAO);

        // Get all the obraList where genDescricao is less than or equal to SMALLER_GEN_DESCRICAO
        defaultObraShouldNotBeFound("genDescricao.lessThanOrEqual=" + SMALLER_GEN_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByGenDescricaoIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genDescricao is less than DEFAULT_GEN_DESCRICAO
        defaultObraShouldNotBeFound("genDescricao.lessThan=" + DEFAULT_GEN_DESCRICAO);

        // Get all the obraList where genDescricao is less than UPDATED_GEN_DESCRICAO
        defaultObraShouldBeFound("genDescricao.lessThan=" + UPDATED_GEN_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByGenDescricaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genDescricao is greater than DEFAULT_GEN_DESCRICAO
        defaultObraShouldNotBeFound("genDescricao.greaterThan=" + DEFAULT_GEN_DESCRICAO);

        // Get all the obraList where genDescricao is greater than SMALLER_GEN_DESCRICAO
        defaultObraShouldBeFound("genDescricao.greaterThan=" + SMALLER_GEN_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllObrasByGenField1IsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genField1 equals to DEFAULT_GEN_FIELD_1
        defaultObraShouldBeFound("genField1.equals=" + DEFAULT_GEN_FIELD_1);

        // Get all the obraList where genField1 equals to UPDATED_GEN_FIELD_1
        defaultObraShouldNotBeFound("genField1.equals=" + UPDATED_GEN_FIELD_1);
    }

    @Test
    @Transactional
    void getAllObrasByGenField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genField1 not equals to DEFAULT_GEN_FIELD_1
        defaultObraShouldNotBeFound("genField1.notEquals=" + DEFAULT_GEN_FIELD_1);

        // Get all the obraList where genField1 not equals to UPDATED_GEN_FIELD_1
        defaultObraShouldBeFound("genField1.notEquals=" + UPDATED_GEN_FIELD_1);
    }

    @Test
    @Transactional
    void getAllObrasByGenField1IsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genField1 in DEFAULT_GEN_FIELD_1 or UPDATED_GEN_FIELD_1
        defaultObraShouldBeFound("genField1.in=" + DEFAULT_GEN_FIELD_1 + "," + UPDATED_GEN_FIELD_1);

        // Get all the obraList where genField1 equals to UPDATED_GEN_FIELD_1
        defaultObraShouldNotBeFound("genField1.in=" + UPDATED_GEN_FIELD_1);
    }

    @Test
    @Transactional
    void getAllObrasByGenField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genField1 is not null
        defaultObraShouldBeFound("genField1.specified=true");

        // Get all the obraList where genField1 is null
        defaultObraShouldNotBeFound("genField1.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByGenField1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genField1 is greater than or equal to DEFAULT_GEN_FIELD_1
        defaultObraShouldBeFound("genField1.greaterThanOrEqual=" + DEFAULT_GEN_FIELD_1);

        // Get all the obraList where genField1 is greater than or equal to UPDATED_GEN_FIELD_1
        defaultObraShouldNotBeFound("genField1.greaterThanOrEqual=" + UPDATED_GEN_FIELD_1);
    }

    @Test
    @Transactional
    void getAllObrasByGenField1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genField1 is less than or equal to DEFAULT_GEN_FIELD_1
        defaultObraShouldBeFound("genField1.lessThanOrEqual=" + DEFAULT_GEN_FIELD_1);

        // Get all the obraList where genField1 is less than or equal to SMALLER_GEN_FIELD_1
        defaultObraShouldNotBeFound("genField1.lessThanOrEqual=" + SMALLER_GEN_FIELD_1);
    }

    @Test
    @Transactional
    void getAllObrasByGenField1IsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genField1 is less than DEFAULT_GEN_FIELD_1
        defaultObraShouldNotBeFound("genField1.lessThan=" + DEFAULT_GEN_FIELD_1);

        // Get all the obraList where genField1 is less than UPDATED_GEN_FIELD_1
        defaultObraShouldBeFound("genField1.lessThan=" + UPDATED_GEN_FIELD_1);
    }

    @Test
    @Transactional
    void getAllObrasByGenField1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genField1 is greater than DEFAULT_GEN_FIELD_1
        defaultObraShouldNotBeFound("genField1.greaterThan=" + DEFAULT_GEN_FIELD_1);

        // Get all the obraList where genField1 is greater than SMALLER_GEN_FIELD_1
        defaultObraShouldBeFound("genField1.greaterThan=" + SMALLER_GEN_FIELD_1);
    }

    @Test
    @Transactional
    void getAllObrasByParaFotografiaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraFotografia equals to DEFAULT_PARA_FOTOGRAFIA
        defaultObraShouldBeFound("paraFotografia.equals=" + DEFAULT_PARA_FOTOGRAFIA);

        // Get all the obraList where paraFotografia equals to UPDATED_PARA_FOTOGRAFIA
        defaultObraShouldNotBeFound("paraFotografia.equals=" + UPDATED_PARA_FOTOGRAFIA);
    }

    @Test
    @Transactional
    void getAllObrasByParaFotografiaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraFotografia not equals to DEFAULT_PARA_FOTOGRAFIA
        defaultObraShouldNotBeFound("paraFotografia.notEquals=" + DEFAULT_PARA_FOTOGRAFIA);

        // Get all the obraList where paraFotografia not equals to UPDATED_PARA_FOTOGRAFIA
        defaultObraShouldBeFound("paraFotografia.notEquals=" + UPDATED_PARA_FOTOGRAFIA);
    }

    @Test
    @Transactional
    void getAllObrasByParaFotografiaIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraFotografia in DEFAULT_PARA_FOTOGRAFIA or UPDATED_PARA_FOTOGRAFIA
        defaultObraShouldBeFound("paraFotografia.in=" + DEFAULT_PARA_FOTOGRAFIA + "," + UPDATED_PARA_FOTOGRAFIA);

        // Get all the obraList where paraFotografia equals to UPDATED_PARA_FOTOGRAFIA
        defaultObraShouldNotBeFound("paraFotografia.in=" + UPDATED_PARA_FOTOGRAFIA);
    }

    @Test
    @Transactional
    void getAllObrasByParaFotografiaIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where paraFotografia is not null
        defaultObraShouldBeFound("paraFotografia.specified=true");

        // Get all the obraList where paraFotografia is null
        defaultObraShouldNotBeFound("paraFotografia.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrObraIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrObra equals to DEFAULT_GEN_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("genMarcaInscrObra.equals=" + DEFAULT_GEN_MARCA_INSCR_OBRA);

        // Get all the obraList where genMarcaInscrObra equals to UPDATED_GEN_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("genMarcaInscrObra.equals=" + UPDATED_GEN_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrObraIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrObra not equals to DEFAULT_GEN_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("genMarcaInscrObra.notEquals=" + DEFAULT_GEN_MARCA_INSCR_OBRA);

        // Get all the obraList where genMarcaInscrObra not equals to UPDATED_GEN_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("genMarcaInscrObra.notEquals=" + UPDATED_GEN_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrObraIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrObra in DEFAULT_GEN_MARCA_INSCR_OBRA or UPDATED_GEN_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("genMarcaInscrObra.in=" + DEFAULT_GEN_MARCA_INSCR_OBRA + "," + UPDATED_GEN_MARCA_INSCR_OBRA);

        // Get all the obraList where genMarcaInscrObra equals to UPDATED_GEN_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("genMarcaInscrObra.in=" + UPDATED_GEN_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrObraIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrObra is not null
        defaultObraShouldBeFound("genMarcaInscrObra.specified=true");

        // Get all the obraList where genMarcaInscrObra is null
        defaultObraShouldNotBeFound("genMarcaInscrObra.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrObraIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrObra is greater than or equal to DEFAULT_GEN_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("genMarcaInscrObra.greaterThanOrEqual=" + DEFAULT_GEN_MARCA_INSCR_OBRA);

        // Get all the obraList where genMarcaInscrObra is greater than or equal to UPDATED_GEN_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("genMarcaInscrObra.greaterThanOrEqual=" + UPDATED_GEN_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrObraIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrObra is less than or equal to DEFAULT_GEN_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("genMarcaInscrObra.lessThanOrEqual=" + DEFAULT_GEN_MARCA_INSCR_OBRA);

        // Get all the obraList where genMarcaInscrObra is less than or equal to SMALLER_GEN_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("genMarcaInscrObra.lessThanOrEqual=" + SMALLER_GEN_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrObraIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrObra is less than DEFAULT_GEN_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("genMarcaInscrObra.lessThan=" + DEFAULT_GEN_MARCA_INSCR_OBRA);

        // Get all the obraList where genMarcaInscrObra is less than UPDATED_GEN_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("genMarcaInscrObra.lessThan=" + UPDATED_GEN_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrObraIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrObra is greater than DEFAULT_GEN_MARCA_INSCR_OBRA
        defaultObraShouldNotBeFound("genMarcaInscrObra.greaterThan=" + DEFAULT_GEN_MARCA_INSCR_OBRA);

        // Get all the obraList where genMarcaInscrObra is greater than SMALLER_GEN_MARCA_INSCR_OBRA
        defaultObraShouldBeFound("genMarcaInscrObra.greaterThan=" + SMALLER_GEN_MARCA_INSCR_OBRA);
    }

    @Test
    @Transactional
    void getAllObrasByPalavrasChaveIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where palavrasChave equals to DEFAULT_PALAVRAS_CHAVE
        defaultObraShouldBeFound("palavrasChave.equals=" + DEFAULT_PALAVRAS_CHAVE);

        // Get all the obraList where palavrasChave equals to UPDATED_PALAVRAS_CHAVE
        defaultObraShouldNotBeFound("palavrasChave.equals=" + UPDATED_PALAVRAS_CHAVE);
    }

    @Test
    @Transactional
    void getAllObrasByPalavrasChaveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where palavrasChave not equals to DEFAULT_PALAVRAS_CHAVE
        defaultObraShouldNotBeFound("palavrasChave.notEquals=" + DEFAULT_PALAVRAS_CHAVE);

        // Get all the obraList where palavrasChave not equals to UPDATED_PALAVRAS_CHAVE
        defaultObraShouldBeFound("palavrasChave.notEquals=" + UPDATED_PALAVRAS_CHAVE);
    }

    @Test
    @Transactional
    void getAllObrasByPalavrasChaveIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where palavrasChave in DEFAULT_PALAVRAS_CHAVE or UPDATED_PALAVRAS_CHAVE
        defaultObraShouldBeFound("palavrasChave.in=" + DEFAULT_PALAVRAS_CHAVE + "," + UPDATED_PALAVRAS_CHAVE);

        // Get all the obraList where palavrasChave equals to UPDATED_PALAVRAS_CHAVE
        defaultObraShouldNotBeFound("palavrasChave.in=" + UPDATED_PALAVRAS_CHAVE);
    }

    @Test
    @Transactional
    void getAllObrasByPalavrasChaveIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where palavrasChave is not null
        defaultObraShouldBeFound("palavrasChave.specified=true");

        // Get all the obraList where palavrasChave is null
        defaultObraShouldNotBeFound("palavrasChave.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByPalavrasChaveContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where palavrasChave contains DEFAULT_PALAVRAS_CHAVE
        defaultObraShouldBeFound("palavrasChave.contains=" + DEFAULT_PALAVRAS_CHAVE);

        // Get all the obraList where palavrasChave contains UPDATED_PALAVRAS_CHAVE
        defaultObraShouldNotBeFound("palavrasChave.contains=" + UPDATED_PALAVRAS_CHAVE);
    }

    @Test
    @Transactional
    void getAllObrasByPalavrasChaveNotContainsSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where palavrasChave does not contain DEFAULT_PALAVRAS_CHAVE
        defaultObraShouldNotBeFound("palavrasChave.doesNotContain=" + DEFAULT_PALAVRAS_CHAVE);

        // Get all the obraList where palavrasChave does not contain UPDATED_PALAVRAS_CHAVE
        defaultObraShouldBeFound("palavrasChave.doesNotContain=" + UPDATED_PALAVRAS_CHAVE);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrSuporteIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrSuporte equals to DEFAULT_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("genMarcaInscrSuporte.equals=" + DEFAULT_GEN_MARCA_INSCR_SUPORTE);

        // Get all the obraList where genMarcaInscrSuporte equals to UPDATED_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("genMarcaInscrSuporte.equals=" + UPDATED_GEN_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrSuporteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrSuporte not equals to DEFAULT_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("genMarcaInscrSuporte.notEquals=" + DEFAULT_GEN_MARCA_INSCR_SUPORTE);

        // Get all the obraList where genMarcaInscrSuporte not equals to UPDATED_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("genMarcaInscrSuporte.notEquals=" + UPDATED_GEN_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrSuporteIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrSuporte in DEFAULT_GEN_MARCA_INSCR_SUPORTE or UPDATED_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("genMarcaInscrSuporte.in=" + DEFAULT_GEN_MARCA_INSCR_SUPORTE + "," + UPDATED_GEN_MARCA_INSCR_SUPORTE);

        // Get all the obraList where genMarcaInscrSuporte equals to UPDATED_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("genMarcaInscrSuporte.in=" + UPDATED_GEN_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrSuporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrSuporte is not null
        defaultObraShouldBeFound("genMarcaInscrSuporte.specified=true");

        // Get all the obraList where genMarcaInscrSuporte is null
        defaultObraShouldNotBeFound("genMarcaInscrSuporte.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrSuporteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrSuporte is greater than or equal to DEFAULT_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("genMarcaInscrSuporte.greaterThanOrEqual=" + DEFAULT_GEN_MARCA_INSCR_SUPORTE);

        // Get all the obraList where genMarcaInscrSuporte is greater than or equal to UPDATED_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("genMarcaInscrSuporte.greaterThanOrEqual=" + UPDATED_GEN_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrSuporteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrSuporte is less than or equal to DEFAULT_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("genMarcaInscrSuporte.lessThanOrEqual=" + DEFAULT_GEN_MARCA_INSCR_SUPORTE);

        // Get all the obraList where genMarcaInscrSuporte is less than or equal to SMALLER_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("genMarcaInscrSuporte.lessThanOrEqual=" + SMALLER_GEN_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrSuporteIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrSuporte is less than DEFAULT_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("genMarcaInscrSuporte.lessThan=" + DEFAULT_GEN_MARCA_INSCR_SUPORTE);

        // Get all the obraList where genMarcaInscrSuporte is less than UPDATED_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("genMarcaInscrSuporte.lessThan=" + UPDATED_GEN_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByGenMarcaInscrSuporteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genMarcaInscrSuporte is greater than DEFAULT_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldNotBeFound("genMarcaInscrSuporte.greaterThan=" + DEFAULT_GEN_MARCA_INSCR_SUPORTE);

        // Get all the obraList where genMarcaInscrSuporte is greater than SMALLER_GEN_MARCA_INSCR_SUPORTE
        defaultObraShouldBeFound("genMarcaInscrSuporte.greaterThan=" + SMALLER_GEN_MARCA_INSCR_SUPORTE);
    }

    @Test
    @Transactional
    void getAllObrasByGenObsIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genObs equals to DEFAULT_GEN_OBS
        defaultObraShouldBeFound("genObs.equals=" + DEFAULT_GEN_OBS);

        // Get all the obraList where genObs equals to UPDATED_GEN_OBS
        defaultObraShouldNotBeFound("genObs.equals=" + UPDATED_GEN_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByGenObsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genObs not equals to DEFAULT_GEN_OBS
        defaultObraShouldNotBeFound("genObs.notEquals=" + DEFAULT_GEN_OBS);

        // Get all the obraList where genObs not equals to UPDATED_GEN_OBS
        defaultObraShouldBeFound("genObs.notEquals=" + UPDATED_GEN_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByGenObsIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genObs in DEFAULT_GEN_OBS or UPDATED_GEN_OBS
        defaultObraShouldBeFound("genObs.in=" + DEFAULT_GEN_OBS + "," + UPDATED_GEN_OBS);

        // Get all the obraList where genObs equals to UPDATED_GEN_OBS
        defaultObraShouldNotBeFound("genObs.in=" + UPDATED_GEN_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByGenObsIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genObs is not null
        defaultObraShouldBeFound("genObs.specified=true");

        // Get all the obraList where genObs is null
        defaultObraShouldNotBeFound("genObs.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByGenObsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genObs is greater than or equal to DEFAULT_GEN_OBS
        defaultObraShouldBeFound("genObs.greaterThanOrEqual=" + DEFAULT_GEN_OBS);

        // Get all the obraList where genObs is greater than or equal to UPDATED_GEN_OBS
        defaultObraShouldNotBeFound("genObs.greaterThanOrEqual=" + UPDATED_GEN_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByGenObsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genObs is less than or equal to DEFAULT_GEN_OBS
        defaultObraShouldBeFound("genObs.lessThanOrEqual=" + DEFAULT_GEN_OBS);

        // Get all the obraList where genObs is less than or equal to SMALLER_GEN_OBS
        defaultObraShouldNotBeFound("genObs.lessThanOrEqual=" + SMALLER_GEN_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByGenObsIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genObs is less than DEFAULT_GEN_OBS
        defaultObraShouldNotBeFound("genObs.lessThan=" + DEFAULT_GEN_OBS);

        // Get all the obraList where genObs is less than UPDATED_GEN_OBS
        defaultObraShouldBeFound("genObs.lessThan=" + UPDATED_GEN_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByGenObsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genObs is greater than DEFAULT_GEN_OBS
        defaultObraShouldNotBeFound("genObs.greaterThan=" + DEFAULT_GEN_OBS);

        // Get all the obraList where genObs is greater than SMALLER_GEN_OBS
        defaultObraShouldBeFound("genObs.greaterThan=" + SMALLER_GEN_OBS);
    }

    @Test
    @Transactional
    void getAllObrasByGenVerbeteIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genVerbete equals to DEFAULT_GEN_VERBETE
        defaultObraShouldBeFound("genVerbete.equals=" + DEFAULT_GEN_VERBETE);

        // Get all the obraList where genVerbete equals to UPDATED_GEN_VERBETE
        defaultObraShouldNotBeFound("genVerbete.equals=" + UPDATED_GEN_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByGenVerbeteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genVerbete not equals to DEFAULT_GEN_VERBETE
        defaultObraShouldNotBeFound("genVerbete.notEquals=" + DEFAULT_GEN_VERBETE);

        // Get all the obraList where genVerbete not equals to UPDATED_GEN_VERBETE
        defaultObraShouldBeFound("genVerbete.notEquals=" + UPDATED_GEN_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByGenVerbeteIsInShouldWork() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genVerbete in DEFAULT_GEN_VERBETE or UPDATED_GEN_VERBETE
        defaultObraShouldBeFound("genVerbete.in=" + DEFAULT_GEN_VERBETE + "," + UPDATED_GEN_VERBETE);

        // Get all the obraList where genVerbete equals to UPDATED_GEN_VERBETE
        defaultObraShouldNotBeFound("genVerbete.in=" + UPDATED_GEN_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByGenVerbeteIsNullOrNotNull() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genVerbete is not null
        defaultObraShouldBeFound("genVerbete.specified=true");

        // Get all the obraList where genVerbete is null
        defaultObraShouldNotBeFound("genVerbete.specified=false");
    }

    @Test
    @Transactional
    void getAllObrasByGenVerbeteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genVerbete is greater than or equal to DEFAULT_GEN_VERBETE
        defaultObraShouldBeFound("genVerbete.greaterThanOrEqual=" + DEFAULT_GEN_VERBETE);

        // Get all the obraList where genVerbete is greater than or equal to UPDATED_GEN_VERBETE
        defaultObraShouldNotBeFound("genVerbete.greaterThanOrEqual=" + UPDATED_GEN_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByGenVerbeteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genVerbete is less than or equal to DEFAULT_GEN_VERBETE
        defaultObraShouldBeFound("genVerbete.lessThanOrEqual=" + DEFAULT_GEN_VERBETE);

        // Get all the obraList where genVerbete is less than or equal to SMALLER_GEN_VERBETE
        defaultObraShouldNotBeFound("genVerbete.lessThanOrEqual=" + SMALLER_GEN_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByGenVerbeteIsLessThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genVerbete is less than DEFAULT_GEN_VERBETE
        defaultObraShouldNotBeFound("genVerbete.lessThan=" + DEFAULT_GEN_VERBETE);

        // Get all the obraList where genVerbete is less than UPDATED_GEN_VERBETE
        defaultObraShouldBeFound("genVerbete.lessThan=" + UPDATED_GEN_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByGenVerbeteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        // Get all the obraList where genVerbete is greater than DEFAULT_GEN_VERBETE
        defaultObraShouldNotBeFound("genVerbete.greaterThan=" + DEFAULT_GEN_VERBETE);

        // Get all the obraList where genVerbete is greater than SMALLER_GEN_VERBETE
        defaultObraShouldBeFound("genVerbete.greaterThan=" + SMALLER_GEN_VERBETE);
    }

    @Test
    @Transactional
    void getAllObrasByDadoDocumentalIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        DadoDocumental dadoDocumental = DadoDocumentalResourceIT.createEntity(em);
        em.persist(dadoDocumental);
        em.flush();
        obra.addDadoDocumental(dadoDocumental);
        obraRepository.saveAndFlush(obra);
        Long dadoDocumentalId = dadoDocumental.getId();

        // Get all the obraList where dadoDocumental equals to dadoDocumentalId
        defaultObraShouldBeFound("dadoDocumentalId.equals=" + dadoDocumentalId);

        // Get all the obraList where dadoDocumental equals to (dadoDocumentalId + 1)
        defaultObraShouldNotBeFound("dadoDocumentalId.equals=" + (dadoDocumentalId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByArtistaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Artista artista = ArtistaResourceIT.createEntity(em);
        em.persist(artista);
        em.flush();
        obra.setArtista(artista);
        obraRepository.saveAndFlush(obra);
        Long artistaId = artista.getId();

        // Get all the obraList where artista equals to artistaId
        defaultObraShouldBeFound("artistaId.equals=" + artistaId);

        // Get all the obraList where artista equals to (artistaId + 1)
        defaultObraShouldNotBeFound("artistaId.equals=" + (artistaId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Categoria categoria = CategoriaResourceIT.createEntity(em);
        em.persist(categoria);
        em.flush();
        obra.setCategoria(categoria);
        obraRepository.saveAndFlush(obra);
        Long categoriaId = categoria.getId();

        // Get all the obraList where categoria equals to categoriaId
        defaultObraShouldBeFound("categoriaId.equals=" + categoriaId);

        // Get all the obraList where categoria equals to (categoriaId + 1)
        defaultObraShouldNotBeFound("categoriaId.equals=" + (categoriaId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByTecnicaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Tecnica tecnica = TecnicaResourceIT.createEntity(em);
        em.persist(tecnica);
        em.flush();
        obra.setTecnica(tecnica);
        obraRepository.saveAndFlush(obra);
        Long tecnicaId = tecnica.getId();

        // Get all the obraList where tecnica equals to tecnicaId
        defaultObraShouldBeFound("tecnicaId.equals=" + tecnicaId);

        // Get all the obraList where tecnica equals to (tecnicaId + 1)
        defaultObraShouldNotBeFound("tecnicaId.equals=" + (tecnicaId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByNivelIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Nivel nivel = NivelResourceIT.createEntity(em);
        em.persist(nivel);
        em.flush();
        obra.setNivel(nivel);
        obraRepository.saveAndFlush(obra);
        Long nivelId = nivel.getId();

        // Get all the obraList where nivel equals to nivelId
        defaultObraShouldBeFound("nivelId.equals=" + nivelId);

        // Get all the obraList where nivel equals to (nivelId + 1)
        defaultObraShouldNotBeFound("nivelId.equals=" + (nivelId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Data data = DataResourceIT.createEntity(em);
        em.persist(data);
        em.flush();
        obra.setData(data);
        obraRepository.saveAndFlush(obra);
        Long dataId = data.getId();

        // Get all the obraList where data equals to dataId
        defaultObraShouldBeFound("dataId.equals=" + dataId);

        // Get all the obraList where data equals to (dataId + 1)
        defaultObraShouldNotBeFound("dataId.equals=" + (dataId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Empresa empresa = EmpresaResourceIT.createEntity(em);
        em.persist(empresa);
        em.flush();
        obra.setEmpresa(empresa);
        obraRepository.saveAndFlush(obra);
        Long empresaId = empresa.getId();

        // Get all the obraList where empresa equals to empresaId
        defaultObraShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the obraList where empresa equals to (empresaId + 1)
        defaultObraShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByMoedaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Moeda moeda = MoedaResourceIT.createEntity(em);
        em.persist(moeda);
        em.flush();
        obra.setMoeda(moeda);
        obraRepository.saveAndFlush(obra);
        Long moedaId = moeda.getId();

        // Get all the obraList where moeda equals to moedaId
        defaultObraShouldBeFound("moedaId.equals=" + moedaId);

        // Get all the obraList where moeda equals to (moedaId + 1)
        defaultObraShouldNotBeFound("moedaId.equals=" + (moedaId + 1));
    }

    @Test
    @Transactional
    void getAllObrasBySeguroIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Seguro seguro = SeguroResourceIT.createEntity(em);
        em.persist(seguro);
        em.flush();
        obra.setSeguro(seguro);
        obraRepository.saveAndFlush(obra);
        Long seguroId = seguro.getId();

        // Get all the obraList where seguro equals to seguroId
        defaultObraShouldBeFound("seguroId.equals=" + seguroId);

        // Get all the obraList where seguro equals to (seguroId + 1)
        defaultObraShouldNotBeFound("seguroId.equals=" + (seguroId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByResponsavelIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Responsavel responsavel = ResponsavelResourceIT.createEntity(em);
        em.persist(responsavel);
        em.flush();
        obra.setResponsavel(responsavel);
        obraRepository.saveAndFlush(obra);
        Long responsavelId = responsavel.getId();

        // Get all the obraList where responsavel equals to responsavelId
        defaultObraShouldBeFound("responsavelId.equals=" + responsavelId);

        // Get all the obraList where responsavel equals to (responsavelId + 1)
        defaultObraShouldNotBeFound("responsavelId.equals=" + (responsavelId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByAcervoatualIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        AcervoAtual acervoatual = AcervoAtualResourceIT.createEntity(em);
        em.persist(acervoatual);
        em.flush();
        obra.setAcervoatual(acervoatual);
        obraRepository.saveAndFlush(obra);
        Long acervoatualId = acervoatual.getId();

        // Get all the obraList where acervoatual equals to acervoatualId
        defaultObraShouldBeFound("acervoatualId.equals=" + acervoatualId);

        // Get all the obraList where acervoatual equals to (acervoatualId + 1)
        defaultObraShouldNotBeFound("acervoatualId.equals=" + (acervoatualId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByFotografoIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        Contato fotografo = ContatoResourceIT.createEntity(em);
        em.persist(fotografo);
        em.flush();
        obra.setFotografo(fotografo);
        obraRepository.saveAndFlush(obra);
        Long fotografoId = fotografo.getId();

        // Get all the obraList where fotografo equals to fotografoId
        defaultObraShouldBeFound("fotografoId.equals=" + fotografoId);

        // Get all the obraList where fotografo equals to (fotografoId + 1)
        defaultObraShouldNotBeFound("fotografoId.equals=" + (fotografoId + 1));
    }

    @Test
    @Transactional
    void getAllObrasByAndarMapaIsEqualToSomething() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);
        AndarMapa andarMapa = AndarMapaResourceIT.createEntity(em);
        em.persist(andarMapa);
        em.flush();
        obra.setAndarMapa(andarMapa);
        obraRepository.saveAndFlush(obra);
        Long andarMapaId = andarMapa.getId();

        // Get all the obraList where andarMapa equals to andarMapaId
        defaultObraShouldBeFound("andarMapaId.equals=" + andarMapaId);

        // Get all the obraList where andarMapa equals to (andarMapaId + 1)
        defaultObraShouldNotBeFound("andarMapaId.equals=" + (andarMapaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultObraShouldBeFound(String filter) throws Exception {
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(obra.getId().intValue())))
            .andExpect(jsonPath("$.[*].tombo").value(hasItem(DEFAULT_TOMBO)))
            .andExpect(jsonPath("$.[*].multiplo").value(hasItem(DEFAULT_MULTIPLO)))
            .andExpect(jsonPath("$.[*].numeroRegistro").value(hasItem(DEFAULT_NUMERO_REGISTRO)))
            .andExpect(jsonPath("$.[*].outrosTitulos").value(hasItem(DEFAULT_OUTROS_TITULOS)))
            .andExpect(jsonPath("$.[*].tituloOriginal").value(hasItem(DEFAULT_TITULO_ORIGINAL)))
            .andExpect(jsonPath("$.[*].origem").value(hasItem(DEFAULT_ORIGEM)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].assinatura").value(hasItem(DEFAULT_ASSINATURA.booleanValue())))
            .andExpect(jsonPath("$.[*].localAssinatura").value(hasItem(DEFAULT_LOCAL_ASSINATURA)))
            .andExpect(jsonPath("$.[*].marcaInscrObra").value(hasItem(DEFAULT_MARCA_INSCR_OBRA)))
            .andExpect(jsonPath("$.[*].marcaInscrSuporte").value(hasItem(DEFAULT_MARCA_INSCR_SUPORTE)))
            .andExpect(jsonPath("$.[*].medidasAprox").value(hasItem(DEFAULT_MEDIDAS_APROX.booleanValue())))
            .andExpect(jsonPath("$.[*].alturaObra").value(hasItem(sameNumber(DEFAULT_ALTURA_OBRA))))
            .andExpect(jsonPath("$.[*].largObra").value(hasItem(sameNumber(DEFAULT_LARG_OBRA))))
            .andExpect(jsonPath("$.[*].profObra").value(hasItem(sameNumber(DEFAULT_PROF_OBRA))))
            .andExpect(jsonPath("$.[*].diametrObra").value(hasItem(DEFAULT_DIAMETR_OBRA)))
            .andExpect(jsonPath("$.[*].alturaMold").value(hasItem(sameNumber(DEFAULT_ALTURA_MOLD))))
            .andExpect(jsonPath("$.[*].largMold").value(hasItem(sameNumber(DEFAULT_LARG_MOLD))))
            .andExpect(jsonPath("$.[*].profMold").value(hasItem(sameNumber(DEFAULT_PROF_MOLD))))
            .andExpect(jsonPath("$.[*].diametroMold").value(hasItem(DEFAULT_DIAMETRO_MOLD)))
            .andExpect(jsonPath("$.[*].dimensAdic").value(hasItem(DEFAULT_DIMENS_ADIC)))
            .andExpect(jsonPath("$.[*].pesObra").value(hasItem(DEFAULT_PES_OBRA)))
            .andExpect(jsonPath("$.[*].atribuido").value(hasItem(DEFAULT_ATRIBUIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].nFoto").value(hasItem(DEFAULT_N_FOTO)))
            .andExpect(jsonPath("$.[*].conjunto").value(hasItem(DEFAULT_CONJUNTO.booleanValue())))
            .andExpect(jsonPath("$.[*].conjTombo").value(hasItem(DEFAULT_CONJ_TOMBO)))
            .andExpect(jsonPath("$.[*].valorSeguro").value(hasItem(sameNumber(DEFAULT_VALOR_SEGURO))))
            .andExpect(jsonPath("$.[*].valorSeguroReal").value(hasItem(sameNumber(DEFAULT_VALOR_SEGURO_REAL))))
            .andExpect(jsonPath("$.[*].dataconversao").value(hasItem(DEFAULT_DATACONVERSAO.toString())))
            .andExpect(jsonPath("$.[*].dataAlterApolice").value(hasItem(DEFAULT_DATA_ALTER_APOLICE.toString())))
            .andExpect(jsonPath("$.[*].localAtual").value(hasItem(DEFAULT_LOCAL_ATUAL)))
            .andExpect(jsonPath("$.[*].localAtualNovo").value(hasItem(DEFAULT_LOCAL_ATUAL_NOVO)))
            .andExpect(jsonPath("$.[*].codParametro").value(hasItem(DEFAULT_COD_PARAMETRO)))
            .andExpect(jsonPath("$.[*].obs").value(hasItem(DEFAULT_OBS)))
            .andExpect(jsonPath("$.[*].tiragem").value(hasItem(DEFAULT_TIRAGEM)))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE)))
            .andExpect(jsonPath("$.[*].selo").value(hasItem(DEFAULT_SELO)))
            .andExpect(jsonPath("$.[*].tomboAnterio").value(hasItem(DEFAULT_TOMBO_ANTERIO)))
            .andExpect(jsonPath("$.[*].verbete").value(hasItem(DEFAULT_VERBETE)))
            .andExpect(jsonPath("$.[*].livro").value(hasItem(DEFAULT_LIVRO.booleanValue())))
            .andExpect(jsonPath("$.[*].imagemAlta").value(hasItem(DEFAULT_IMAGEM_ALTA.booleanValue())))
            .andExpect(jsonPath("$.[*].apabex").value(hasItem(DEFAULT_APABEX.booleanValue())))
            .andExpect(jsonPath("$.[*].bunkyo").value(hasItem(DEFAULT_BUNKYO.booleanValue())))
            .andExpect(jsonPath("$.[*].faseDepuracao").value(hasItem(DEFAULT_FASE_DEPURACAO)))
            .andExpect(jsonPath("$.[*].paraAvaliacao").value(hasItem(DEFAULT_PARA_AVALIACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].paraRestauracao").value(hasItem(DEFAULT_PARA_RESTAURACAO.booleanValue())))
            .andExpect(jsonPath("$.[*].paraMoldura").value(hasItem(DEFAULT_PARA_MOLDURA.booleanValue())))
            .andExpect(jsonPath("$.[*].paraLegenda").value(hasItem(DEFAULT_PARA_LEGENDA.booleanValue())))
            .andExpect(jsonPath("$.[*].tempField").value(hasItem(DEFAULT_TEMP_FIELD.booleanValue())))
            .andExpect(jsonPath("$.[*].selecaoListaAvulsa").value(hasItem(DEFAULT_SELECAO_LISTA_AVULSA.booleanValue())))
            .andExpect(jsonPath("$.[*].dominioPubl").value(hasItem(DEFAULT_DOMINIO_PUBL.booleanValue())))
            .andExpect(jsonPath("$.[*].dtVencFoto").value(hasItem(DEFAULT_DT_VENC_FOTO.toString())))
            .andExpect(jsonPath("$.[*].obsUsoFoto").value(hasItem(DEFAULT_OBS_USO_FOTO)))
            .andExpect(jsonPath("$.[*].localFotoAlta").value(hasItem(DEFAULT_LOCAL_FOTO_ALTA)))
            .andExpect(jsonPath("$.[*].dataInclusao").value(hasItem(DEFAULT_DATA_INCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].dataExclusao").value(hasItem(DEFAULT_DATA_EXCLUSAO.toString())))
            .andExpect(jsonPath("$.[*].bookX").value(hasItem(DEFAULT_BOOK_X)))
            .andExpect(jsonPath("$.[*].bookY").value(hasItem(DEFAULT_BOOK_Y)))
            .andExpect(jsonPath("$.[*].genDescricao").value(hasItem(DEFAULT_GEN_DESCRICAO)))
            .andExpect(jsonPath("$.[*].genField1").value(hasItem(DEFAULT_GEN_FIELD_1)))
            .andExpect(jsonPath("$.[*].paraFotografia").value(hasItem(DEFAULT_PARA_FOTOGRAFIA.booleanValue())))
            .andExpect(jsonPath("$.[*].genMarcaInscrObra").value(hasItem(DEFAULT_GEN_MARCA_INSCR_OBRA)))
            .andExpect(jsonPath("$.[*].palavrasChave").value(hasItem(DEFAULT_PALAVRAS_CHAVE)))
            .andExpect(jsonPath("$.[*].genMarcaInscrSuporte").value(hasItem(DEFAULT_GEN_MARCA_INSCR_SUPORTE)))
            .andExpect(jsonPath("$.[*].genObs").value(hasItem(DEFAULT_GEN_OBS)))
            .andExpect(jsonPath("$.[*].genVerbete").value(hasItem(DEFAULT_GEN_VERBETE)));

        // Check, that the count call also returns 1
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultObraShouldNotBeFound(String filter) throws Exception {
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restObraMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingObra() throws Exception {
        // Get the obra
        restObraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra
        Obra updatedObra = obraRepository.findById(obra.getId()).get();
        // Disconnect from session so that the updates on updatedObra are not directly saved in db
        em.detach(updatedObra);
        updatedObra
            .tombo(UPDATED_TOMBO)
            .multiplo(UPDATED_MULTIPLO)
            .numeroRegistro(UPDATED_NUMERO_REGISTRO)
            .outrosTitulos(UPDATED_OUTROS_TITULOS)
            .tituloOriginal(UPDATED_TITULO_ORIGINAL)
            .origem(UPDATED_ORIGEM)
            .descricao(UPDATED_DESCRICAO)
            .assinatura(UPDATED_ASSINATURA)
            .localAssinatura(UPDATED_LOCAL_ASSINATURA)
            .marcaInscrObra(UPDATED_MARCA_INSCR_OBRA)
            .marcaInscrSuporte(UPDATED_MARCA_INSCR_SUPORTE)
            .medidasAprox(UPDATED_MEDIDAS_APROX)
            .alturaObra(UPDATED_ALTURA_OBRA)
            .largObra(UPDATED_LARG_OBRA)
            .profObra(UPDATED_PROF_OBRA)
            .diametrObra(UPDATED_DIAMETR_OBRA)
            .alturaMold(UPDATED_ALTURA_MOLD)
            .largMold(UPDATED_LARG_MOLD)
            .profMold(UPDATED_PROF_MOLD)
            .diametroMold(UPDATED_DIAMETRO_MOLD)
            .dimensAdic(UPDATED_DIMENS_ADIC)
            .pesObra(UPDATED_PES_OBRA)
            .atribuido(UPDATED_ATRIBUIDO)
            .nFoto(UPDATED_N_FOTO)
            .conjunto(UPDATED_CONJUNTO)
            .conjTombo(UPDATED_CONJ_TOMBO)
            .valorSeguro(UPDATED_VALOR_SEGURO)
            .valorSeguroReal(UPDATED_VALOR_SEGURO_REAL)
            .dataconversao(UPDATED_DATACONVERSAO)
            .dataAlterApolice(UPDATED_DATA_ALTER_APOLICE)
            .localAtual(UPDATED_LOCAL_ATUAL)
            .localAtualNovo(UPDATED_LOCAL_ATUAL_NOVO)
            .codParametro(UPDATED_COD_PARAMETRO)
            .obs(UPDATED_OBS)
            .tiragem(UPDATED_TIRAGEM)
            .serie(UPDATED_SERIE)
            .selo(UPDATED_SELO)
            .tomboAnterio(UPDATED_TOMBO_ANTERIO)
            .verbete(UPDATED_VERBETE)
            .livro(UPDATED_LIVRO)
            .imagemAlta(UPDATED_IMAGEM_ALTA)
            .apabex(UPDATED_APABEX)
            .bunkyo(UPDATED_BUNKYO)
            .faseDepuracao(UPDATED_FASE_DEPURACAO)
            .paraAvaliacao(UPDATED_PARA_AVALIACAO)
            .paraRestauracao(UPDATED_PARA_RESTAURACAO)
            .paraMoldura(UPDATED_PARA_MOLDURA)
            .paraLegenda(UPDATED_PARA_LEGENDA)
            .tempField(UPDATED_TEMP_FIELD)
            .selecaoListaAvulsa(UPDATED_SELECAO_LISTA_AVULSA)
            .dominioPubl(UPDATED_DOMINIO_PUBL)
            .dtVencFoto(UPDATED_DT_VENC_FOTO)
            .obsUsoFoto(UPDATED_OBS_USO_FOTO)
            .localFotoAlta(UPDATED_LOCAL_FOTO_ALTA)
            .dataInclusao(UPDATED_DATA_INCLUSAO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .bookX(UPDATED_BOOK_X)
            .bookY(UPDATED_BOOK_Y)
            .genDescricao(UPDATED_GEN_DESCRICAO)
            .genField1(UPDATED_GEN_FIELD_1)
            .paraFotografia(UPDATED_PARA_FOTOGRAFIA)
            .genMarcaInscrObra(UPDATED_GEN_MARCA_INSCR_OBRA)
            .palavrasChave(UPDATED_PALAVRAS_CHAVE)
            .genMarcaInscrSuporte(UPDATED_GEN_MARCA_INSCR_SUPORTE)
            .genObs(UPDATED_GEN_OBS)
            .genVerbete(UPDATED_GEN_VERBETE);
        ObraDTO obraDTO = obraMapper.toDto(updatedObra);

        restObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, obraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getTombo()).isEqualTo(UPDATED_TOMBO);
        assertThat(testObra.getMultiplo()).isEqualTo(UPDATED_MULTIPLO);
        assertThat(testObra.getNumeroRegistro()).isEqualTo(UPDATED_NUMERO_REGISTRO);
        assertThat(testObra.getOutrosTitulos()).isEqualTo(UPDATED_OUTROS_TITULOS);
        assertThat(testObra.getTituloOriginal()).isEqualTo(UPDATED_TITULO_ORIGINAL);
        assertThat(testObra.getOrigem()).isEqualTo(UPDATED_ORIGEM);
        assertThat(testObra.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testObra.getAssinatura()).isEqualTo(UPDATED_ASSINATURA);
        assertThat(testObra.getLocalAssinatura()).isEqualTo(UPDATED_LOCAL_ASSINATURA);
        assertThat(testObra.getMarcaInscrObra()).isEqualTo(UPDATED_MARCA_INSCR_OBRA);
        assertThat(testObra.getMarcaInscrSuporte()).isEqualTo(UPDATED_MARCA_INSCR_SUPORTE);
        assertThat(testObra.getMedidasAprox()).isEqualTo(UPDATED_MEDIDAS_APROX);
        assertThat(testObra.getAlturaObra()).isEqualTo(UPDATED_ALTURA_OBRA);
        assertThat(testObra.getLargObra()).isEqualTo(UPDATED_LARG_OBRA);
        assertThat(testObra.getProfObra()).isEqualTo(UPDATED_PROF_OBRA);
        assertThat(testObra.getDiametrObra()).isEqualTo(UPDATED_DIAMETR_OBRA);
        assertThat(testObra.getAlturaMold()).isEqualTo(UPDATED_ALTURA_MOLD);
        assertThat(testObra.getLargMold()).isEqualTo(UPDATED_LARG_MOLD);
        assertThat(testObra.getProfMold()).isEqualTo(UPDATED_PROF_MOLD);
        assertThat(testObra.getDiametroMold()).isEqualTo(UPDATED_DIAMETRO_MOLD);
        assertThat(testObra.getDimensAdic()).isEqualTo(UPDATED_DIMENS_ADIC);
        assertThat(testObra.getPesObra()).isEqualTo(UPDATED_PES_OBRA);
        assertThat(testObra.getAtribuido()).isEqualTo(UPDATED_ATRIBUIDO);
        assertThat(testObra.getnFoto()).isEqualTo(UPDATED_N_FOTO);
        assertThat(testObra.getConjunto()).isEqualTo(UPDATED_CONJUNTO);
        assertThat(testObra.getConjTombo()).isEqualTo(UPDATED_CONJ_TOMBO);
        assertThat(testObra.getValorSeguro()).isEqualTo(UPDATED_VALOR_SEGURO);
        assertThat(testObra.getValorSeguroReal()).isEqualTo(UPDATED_VALOR_SEGURO_REAL);
        assertThat(testObra.getDataconversao()).isEqualTo(UPDATED_DATACONVERSAO);
        assertThat(testObra.getDataAlterApolice()).isEqualTo(UPDATED_DATA_ALTER_APOLICE);
        assertThat(testObra.getLocalAtual()).isEqualTo(UPDATED_LOCAL_ATUAL);
        assertThat(testObra.getLocalAtualNovo()).isEqualTo(UPDATED_LOCAL_ATUAL_NOVO);
        assertThat(testObra.getCodParametro()).isEqualTo(UPDATED_COD_PARAMETRO);
        assertThat(testObra.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testObra.getTiragem()).isEqualTo(UPDATED_TIRAGEM);
        assertThat(testObra.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testObra.getSelo()).isEqualTo(UPDATED_SELO);
        assertThat(testObra.getTomboAnterio()).isEqualTo(UPDATED_TOMBO_ANTERIO);
        assertThat(testObra.getVerbete()).isEqualTo(UPDATED_VERBETE);
        assertThat(testObra.getLivro()).isEqualTo(UPDATED_LIVRO);
        assertThat(testObra.getImagemAlta()).isEqualTo(UPDATED_IMAGEM_ALTA);
        assertThat(testObra.getApabex()).isEqualTo(UPDATED_APABEX);
        assertThat(testObra.getBunkyo()).isEqualTo(UPDATED_BUNKYO);
        assertThat(testObra.getFaseDepuracao()).isEqualTo(UPDATED_FASE_DEPURACAO);
        assertThat(testObra.getParaAvaliacao()).isEqualTo(UPDATED_PARA_AVALIACAO);
        assertThat(testObra.getParaRestauracao()).isEqualTo(UPDATED_PARA_RESTAURACAO);
        assertThat(testObra.getParaMoldura()).isEqualTo(UPDATED_PARA_MOLDURA);
        assertThat(testObra.getParaLegenda()).isEqualTo(UPDATED_PARA_LEGENDA);
        assertThat(testObra.getTempField()).isEqualTo(UPDATED_TEMP_FIELD);
        assertThat(testObra.getSelecaoListaAvulsa()).isEqualTo(UPDATED_SELECAO_LISTA_AVULSA);
        assertThat(testObra.getDominioPubl()).isEqualTo(UPDATED_DOMINIO_PUBL);
        assertThat(testObra.getDtVencFoto()).isEqualTo(UPDATED_DT_VENC_FOTO);
        assertThat(testObra.getObsUsoFoto()).isEqualTo(UPDATED_OBS_USO_FOTO);
        assertThat(testObra.getLocalFotoAlta()).isEqualTo(UPDATED_LOCAL_FOTO_ALTA);
        assertThat(testObra.getDataInclusao()).isEqualTo(UPDATED_DATA_INCLUSAO);
        assertThat(testObra.getDataExclusao()).isEqualTo(UPDATED_DATA_EXCLUSAO);
        assertThat(testObra.getBookX()).isEqualTo(UPDATED_BOOK_X);
        assertThat(testObra.getBookY()).isEqualTo(UPDATED_BOOK_Y);
        assertThat(testObra.getGenDescricao()).isEqualTo(UPDATED_GEN_DESCRICAO);
        assertThat(testObra.getGenField1()).isEqualTo(UPDATED_GEN_FIELD_1);
        assertThat(testObra.getParaFotografia()).isEqualTo(UPDATED_PARA_FOTOGRAFIA);
        assertThat(testObra.getGenMarcaInscrObra()).isEqualTo(UPDATED_GEN_MARCA_INSCR_OBRA);
        assertThat(testObra.getPalavrasChave()).isEqualTo(UPDATED_PALAVRAS_CHAVE);
        assertThat(testObra.getGenMarcaInscrSuporte()).isEqualTo(UPDATED_GEN_MARCA_INSCR_SUPORTE);
        assertThat(testObra.getGenObs()).isEqualTo(UPDATED_GEN_OBS);
        assertThat(testObra.getGenVerbete()).isEqualTo(UPDATED_GEN_VERBETE);
    }

    @Test
    @Transactional
    void putNonExistingObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, obraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObraWithPatch() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra using partial update
        Obra partialUpdatedObra = new Obra();
        partialUpdatedObra.setId(obra.getId());

        partialUpdatedObra
            .numeroRegistro(UPDATED_NUMERO_REGISTRO)
            .tituloOriginal(UPDATED_TITULO_ORIGINAL)
            .assinatura(UPDATED_ASSINATURA)
            .localAssinatura(UPDATED_LOCAL_ASSINATURA)
            .marcaInscrObra(UPDATED_MARCA_INSCR_OBRA)
            .alturaObra(UPDATED_ALTURA_OBRA)
            .alturaMold(UPDATED_ALTURA_MOLD)
            .diametroMold(UPDATED_DIAMETRO_MOLD)
            .dimensAdic(UPDATED_DIMENS_ADIC)
            .atribuido(UPDATED_ATRIBUIDO)
            .conjTombo(UPDATED_CONJ_TOMBO)
            .valorSeguro(UPDATED_VALOR_SEGURO)
            .dataAlterApolice(UPDATED_DATA_ALTER_APOLICE)
            .obs(UPDATED_OBS)
            .tiragem(UPDATED_TIRAGEM)
            .serie(UPDATED_SERIE)
            .selo(UPDATED_SELO)
            .tomboAnterio(UPDATED_TOMBO_ANTERIO)
            .verbete(UPDATED_VERBETE)
            .livro(UPDATED_LIVRO)
            .bunkyo(UPDATED_BUNKYO)
            .paraMoldura(UPDATED_PARA_MOLDURA)
            .selecaoListaAvulsa(UPDATED_SELECAO_LISTA_AVULSA)
            .dtVencFoto(UPDATED_DT_VENC_FOTO)
            .bookY(UPDATED_BOOK_Y)
            .paraFotografia(UPDATED_PARA_FOTOGRAFIA)
            .palavrasChave(UPDATED_PALAVRAS_CHAVE)
            .genObs(UPDATED_GEN_OBS);

        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObra))
            )
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getTombo()).isEqualTo(DEFAULT_TOMBO);
        assertThat(testObra.getMultiplo()).isEqualTo(DEFAULT_MULTIPLO);
        assertThat(testObra.getNumeroRegistro()).isEqualTo(UPDATED_NUMERO_REGISTRO);
        assertThat(testObra.getOutrosTitulos()).isEqualTo(DEFAULT_OUTROS_TITULOS);
        assertThat(testObra.getTituloOriginal()).isEqualTo(UPDATED_TITULO_ORIGINAL);
        assertThat(testObra.getOrigem()).isEqualTo(DEFAULT_ORIGEM);
        assertThat(testObra.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testObra.getAssinatura()).isEqualTo(UPDATED_ASSINATURA);
        assertThat(testObra.getLocalAssinatura()).isEqualTo(UPDATED_LOCAL_ASSINATURA);
        assertThat(testObra.getMarcaInscrObra()).isEqualTo(UPDATED_MARCA_INSCR_OBRA);
        assertThat(testObra.getMarcaInscrSuporte()).isEqualTo(DEFAULT_MARCA_INSCR_SUPORTE);
        assertThat(testObra.getMedidasAprox()).isEqualTo(DEFAULT_MEDIDAS_APROX);
        assertThat(testObra.getAlturaObra()).isEqualByComparingTo(UPDATED_ALTURA_OBRA);
        assertThat(testObra.getLargObra()).isEqualByComparingTo(DEFAULT_LARG_OBRA);
        assertThat(testObra.getProfObra()).isEqualByComparingTo(DEFAULT_PROF_OBRA);
        assertThat(testObra.getDiametrObra()).isEqualTo(DEFAULT_DIAMETR_OBRA);
        assertThat(testObra.getAlturaMold()).isEqualByComparingTo(UPDATED_ALTURA_MOLD);
        assertThat(testObra.getLargMold()).isEqualByComparingTo(DEFAULT_LARG_MOLD);
        assertThat(testObra.getProfMold()).isEqualByComparingTo(DEFAULT_PROF_MOLD);
        assertThat(testObra.getDiametroMold()).isEqualTo(UPDATED_DIAMETRO_MOLD);
        assertThat(testObra.getDimensAdic()).isEqualTo(UPDATED_DIMENS_ADIC);
        assertThat(testObra.getPesObra()).isEqualTo(DEFAULT_PES_OBRA);
        assertThat(testObra.getAtribuido()).isEqualTo(UPDATED_ATRIBUIDO);
        assertThat(testObra.getnFoto()).isEqualTo(DEFAULT_N_FOTO);
        assertThat(testObra.getConjunto()).isEqualTo(DEFAULT_CONJUNTO);
        assertThat(testObra.getConjTombo()).isEqualTo(UPDATED_CONJ_TOMBO);
        assertThat(testObra.getValorSeguro()).isEqualByComparingTo(UPDATED_VALOR_SEGURO);
        assertThat(testObra.getValorSeguroReal()).isEqualByComparingTo(DEFAULT_VALOR_SEGURO_REAL);
        assertThat(testObra.getDataconversao()).isEqualTo(DEFAULT_DATACONVERSAO);
        assertThat(testObra.getDataAlterApolice()).isEqualTo(UPDATED_DATA_ALTER_APOLICE);
        assertThat(testObra.getLocalAtual()).isEqualTo(DEFAULT_LOCAL_ATUAL);
        assertThat(testObra.getLocalAtualNovo()).isEqualTo(DEFAULT_LOCAL_ATUAL_NOVO);
        assertThat(testObra.getCodParametro()).isEqualTo(DEFAULT_COD_PARAMETRO);
        assertThat(testObra.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testObra.getTiragem()).isEqualTo(UPDATED_TIRAGEM);
        assertThat(testObra.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testObra.getSelo()).isEqualTo(UPDATED_SELO);
        assertThat(testObra.getTomboAnterio()).isEqualTo(UPDATED_TOMBO_ANTERIO);
        assertThat(testObra.getVerbete()).isEqualTo(UPDATED_VERBETE);
        assertThat(testObra.getLivro()).isEqualTo(UPDATED_LIVRO);
        assertThat(testObra.getImagemAlta()).isEqualTo(DEFAULT_IMAGEM_ALTA);
        assertThat(testObra.getApabex()).isEqualTo(DEFAULT_APABEX);
        assertThat(testObra.getBunkyo()).isEqualTo(UPDATED_BUNKYO);
        assertThat(testObra.getFaseDepuracao()).isEqualTo(DEFAULT_FASE_DEPURACAO);
        assertThat(testObra.getParaAvaliacao()).isEqualTo(DEFAULT_PARA_AVALIACAO);
        assertThat(testObra.getParaRestauracao()).isEqualTo(DEFAULT_PARA_RESTAURACAO);
        assertThat(testObra.getParaMoldura()).isEqualTo(UPDATED_PARA_MOLDURA);
        assertThat(testObra.getParaLegenda()).isEqualTo(DEFAULT_PARA_LEGENDA);
        assertThat(testObra.getTempField()).isEqualTo(DEFAULT_TEMP_FIELD);
        assertThat(testObra.getSelecaoListaAvulsa()).isEqualTo(UPDATED_SELECAO_LISTA_AVULSA);
        assertThat(testObra.getDominioPubl()).isEqualTo(DEFAULT_DOMINIO_PUBL);
        assertThat(testObra.getDtVencFoto()).isEqualTo(UPDATED_DT_VENC_FOTO);
        assertThat(testObra.getObsUsoFoto()).isEqualTo(DEFAULT_OBS_USO_FOTO);
        assertThat(testObra.getLocalFotoAlta()).isEqualTo(DEFAULT_LOCAL_FOTO_ALTA);
        assertThat(testObra.getDataInclusao()).isEqualTo(DEFAULT_DATA_INCLUSAO);
        assertThat(testObra.getDataExclusao()).isEqualTo(DEFAULT_DATA_EXCLUSAO);
        assertThat(testObra.getBookX()).isEqualTo(DEFAULT_BOOK_X);
        assertThat(testObra.getBookY()).isEqualTo(UPDATED_BOOK_Y);
        assertThat(testObra.getGenDescricao()).isEqualTo(DEFAULT_GEN_DESCRICAO);
        assertThat(testObra.getGenField1()).isEqualTo(DEFAULT_GEN_FIELD_1);
        assertThat(testObra.getParaFotografia()).isEqualTo(UPDATED_PARA_FOTOGRAFIA);
        assertThat(testObra.getGenMarcaInscrObra()).isEqualTo(DEFAULT_GEN_MARCA_INSCR_OBRA);
        assertThat(testObra.getPalavrasChave()).isEqualTo(UPDATED_PALAVRAS_CHAVE);
        assertThat(testObra.getGenMarcaInscrSuporte()).isEqualTo(DEFAULT_GEN_MARCA_INSCR_SUPORTE);
        assertThat(testObra.getGenObs()).isEqualTo(UPDATED_GEN_OBS);
        assertThat(testObra.getGenVerbete()).isEqualTo(DEFAULT_GEN_VERBETE);
    }

    @Test
    @Transactional
    void fullUpdateObraWithPatch() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeUpdate = obraRepository.findAll().size();

        // Update the obra using partial update
        Obra partialUpdatedObra = new Obra();
        partialUpdatedObra.setId(obra.getId());

        partialUpdatedObra
            .tombo(UPDATED_TOMBO)
            .multiplo(UPDATED_MULTIPLO)
            .numeroRegistro(UPDATED_NUMERO_REGISTRO)
            .outrosTitulos(UPDATED_OUTROS_TITULOS)
            .tituloOriginal(UPDATED_TITULO_ORIGINAL)
            .origem(UPDATED_ORIGEM)
            .descricao(UPDATED_DESCRICAO)
            .assinatura(UPDATED_ASSINATURA)
            .localAssinatura(UPDATED_LOCAL_ASSINATURA)
            .marcaInscrObra(UPDATED_MARCA_INSCR_OBRA)
            .marcaInscrSuporte(UPDATED_MARCA_INSCR_SUPORTE)
            .medidasAprox(UPDATED_MEDIDAS_APROX)
            .alturaObra(UPDATED_ALTURA_OBRA)
            .largObra(UPDATED_LARG_OBRA)
            .profObra(UPDATED_PROF_OBRA)
            .diametrObra(UPDATED_DIAMETR_OBRA)
            .alturaMold(UPDATED_ALTURA_MOLD)
            .largMold(UPDATED_LARG_MOLD)
            .profMold(UPDATED_PROF_MOLD)
            .diametroMold(UPDATED_DIAMETRO_MOLD)
            .dimensAdic(UPDATED_DIMENS_ADIC)
            .pesObra(UPDATED_PES_OBRA)
            .atribuido(UPDATED_ATRIBUIDO)
            .nFoto(UPDATED_N_FOTO)
            .conjunto(UPDATED_CONJUNTO)
            .conjTombo(UPDATED_CONJ_TOMBO)
            .valorSeguro(UPDATED_VALOR_SEGURO)
            .valorSeguroReal(UPDATED_VALOR_SEGURO_REAL)
            .dataconversao(UPDATED_DATACONVERSAO)
            .dataAlterApolice(UPDATED_DATA_ALTER_APOLICE)
            .localAtual(UPDATED_LOCAL_ATUAL)
            .localAtualNovo(UPDATED_LOCAL_ATUAL_NOVO)
            .codParametro(UPDATED_COD_PARAMETRO)
            .obs(UPDATED_OBS)
            .tiragem(UPDATED_TIRAGEM)
            .serie(UPDATED_SERIE)
            .selo(UPDATED_SELO)
            .tomboAnterio(UPDATED_TOMBO_ANTERIO)
            .verbete(UPDATED_VERBETE)
            .livro(UPDATED_LIVRO)
            .imagemAlta(UPDATED_IMAGEM_ALTA)
            .apabex(UPDATED_APABEX)
            .bunkyo(UPDATED_BUNKYO)
            .faseDepuracao(UPDATED_FASE_DEPURACAO)
            .paraAvaliacao(UPDATED_PARA_AVALIACAO)
            .paraRestauracao(UPDATED_PARA_RESTAURACAO)
            .paraMoldura(UPDATED_PARA_MOLDURA)
            .paraLegenda(UPDATED_PARA_LEGENDA)
            .tempField(UPDATED_TEMP_FIELD)
            .selecaoListaAvulsa(UPDATED_SELECAO_LISTA_AVULSA)
            .dominioPubl(UPDATED_DOMINIO_PUBL)
            .dtVencFoto(UPDATED_DT_VENC_FOTO)
            .obsUsoFoto(UPDATED_OBS_USO_FOTO)
            .localFotoAlta(UPDATED_LOCAL_FOTO_ALTA)
            .dataInclusao(UPDATED_DATA_INCLUSAO)
            .dataExclusao(UPDATED_DATA_EXCLUSAO)
            .bookX(UPDATED_BOOK_X)
            .bookY(UPDATED_BOOK_Y)
            .genDescricao(UPDATED_GEN_DESCRICAO)
            .genField1(UPDATED_GEN_FIELD_1)
            .paraFotografia(UPDATED_PARA_FOTOGRAFIA)
            .genMarcaInscrObra(UPDATED_GEN_MARCA_INSCR_OBRA)
            .palavrasChave(UPDATED_PALAVRAS_CHAVE)
            .genMarcaInscrSuporte(UPDATED_GEN_MARCA_INSCR_SUPORTE)
            .genObs(UPDATED_GEN_OBS)
            .genVerbete(UPDATED_GEN_VERBETE);

        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObra))
            )
            .andExpect(status().isOk());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
        Obra testObra = obraList.get(obraList.size() - 1);
        assertThat(testObra.getTombo()).isEqualTo(UPDATED_TOMBO);
        assertThat(testObra.getMultiplo()).isEqualTo(UPDATED_MULTIPLO);
        assertThat(testObra.getNumeroRegistro()).isEqualTo(UPDATED_NUMERO_REGISTRO);
        assertThat(testObra.getOutrosTitulos()).isEqualTo(UPDATED_OUTROS_TITULOS);
        assertThat(testObra.getTituloOriginal()).isEqualTo(UPDATED_TITULO_ORIGINAL);
        assertThat(testObra.getOrigem()).isEqualTo(UPDATED_ORIGEM);
        assertThat(testObra.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testObra.getAssinatura()).isEqualTo(UPDATED_ASSINATURA);
        assertThat(testObra.getLocalAssinatura()).isEqualTo(UPDATED_LOCAL_ASSINATURA);
        assertThat(testObra.getMarcaInscrObra()).isEqualTo(UPDATED_MARCA_INSCR_OBRA);
        assertThat(testObra.getMarcaInscrSuporte()).isEqualTo(UPDATED_MARCA_INSCR_SUPORTE);
        assertThat(testObra.getMedidasAprox()).isEqualTo(UPDATED_MEDIDAS_APROX);
        assertThat(testObra.getAlturaObra()).isEqualByComparingTo(UPDATED_ALTURA_OBRA);
        assertThat(testObra.getLargObra()).isEqualByComparingTo(UPDATED_LARG_OBRA);
        assertThat(testObra.getProfObra()).isEqualByComparingTo(UPDATED_PROF_OBRA);
        assertThat(testObra.getDiametrObra()).isEqualTo(UPDATED_DIAMETR_OBRA);
        assertThat(testObra.getAlturaMold()).isEqualByComparingTo(UPDATED_ALTURA_MOLD);
        assertThat(testObra.getLargMold()).isEqualByComparingTo(UPDATED_LARG_MOLD);
        assertThat(testObra.getProfMold()).isEqualByComparingTo(UPDATED_PROF_MOLD);
        assertThat(testObra.getDiametroMold()).isEqualTo(UPDATED_DIAMETRO_MOLD);
        assertThat(testObra.getDimensAdic()).isEqualTo(UPDATED_DIMENS_ADIC);
        assertThat(testObra.getPesObra()).isEqualTo(UPDATED_PES_OBRA);
        assertThat(testObra.getAtribuido()).isEqualTo(UPDATED_ATRIBUIDO);
        assertThat(testObra.getnFoto()).isEqualTo(UPDATED_N_FOTO);
        assertThat(testObra.getConjunto()).isEqualTo(UPDATED_CONJUNTO);
        assertThat(testObra.getConjTombo()).isEqualTo(UPDATED_CONJ_TOMBO);
        assertThat(testObra.getValorSeguro()).isEqualByComparingTo(UPDATED_VALOR_SEGURO);
        assertThat(testObra.getValorSeguroReal()).isEqualByComparingTo(UPDATED_VALOR_SEGURO_REAL);
        assertThat(testObra.getDataconversao()).isEqualTo(UPDATED_DATACONVERSAO);
        assertThat(testObra.getDataAlterApolice()).isEqualTo(UPDATED_DATA_ALTER_APOLICE);
        assertThat(testObra.getLocalAtual()).isEqualTo(UPDATED_LOCAL_ATUAL);
        assertThat(testObra.getLocalAtualNovo()).isEqualTo(UPDATED_LOCAL_ATUAL_NOVO);
        assertThat(testObra.getCodParametro()).isEqualTo(UPDATED_COD_PARAMETRO);
        assertThat(testObra.getObs()).isEqualTo(UPDATED_OBS);
        assertThat(testObra.getTiragem()).isEqualTo(UPDATED_TIRAGEM);
        assertThat(testObra.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testObra.getSelo()).isEqualTo(UPDATED_SELO);
        assertThat(testObra.getTomboAnterio()).isEqualTo(UPDATED_TOMBO_ANTERIO);
        assertThat(testObra.getVerbete()).isEqualTo(UPDATED_VERBETE);
        assertThat(testObra.getLivro()).isEqualTo(UPDATED_LIVRO);
        assertThat(testObra.getImagemAlta()).isEqualTo(UPDATED_IMAGEM_ALTA);
        assertThat(testObra.getApabex()).isEqualTo(UPDATED_APABEX);
        assertThat(testObra.getBunkyo()).isEqualTo(UPDATED_BUNKYO);
        assertThat(testObra.getFaseDepuracao()).isEqualTo(UPDATED_FASE_DEPURACAO);
        assertThat(testObra.getParaAvaliacao()).isEqualTo(UPDATED_PARA_AVALIACAO);
        assertThat(testObra.getParaRestauracao()).isEqualTo(UPDATED_PARA_RESTAURACAO);
        assertThat(testObra.getParaMoldura()).isEqualTo(UPDATED_PARA_MOLDURA);
        assertThat(testObra.getParaLegenda()).isEqualTo(UPDATED_PARA_LEGENDA);
        assertThat(testObra.getTempField()).isEqualTo(UPDATED_TEMP_FIELD);
        assertThat(testObra.getSelecaoListaAvulsa()).isEqualTo(UPDATED_SELECAO_LISTA_AVULSA);
        assertThat(testObra.getDominioPubl()).isEqualTo(UPDATED_DOMINIO_PUBL);
        assertThat(testObra.getDtVencFoto()).isEqualTo(UPDATED_DT_VENC_FOTO);
        assertThat(testObra.getObsUsoFoto()).isEqualTo(UPDATED_OBS_USO_FOTO);
        assertThat(testObra.getLocalFotoAlta()).isEqualTo(UPDATED_LOCAL_FOTO_ALTA);
        assertThat(testObra.getDataInclusao()).isEqualTo(UPDATED_DATA_INCLUSAO);
        assertThat(testObra.getDataExclusao()).isEqualTo(UPDATED_DATA_EXCLUSAO);
        assertThat(testObra.getBookX()).isEqualTo(UPDATED_BOOK_X);
        assertThat(testObra.getBookY()).isEqualTo(UPDATED_BOOK_Y);
        assertThat(testObra.getGenDescricao()).isEqualTo(UPDATED_GEN_DESCRICAO);
        assertThat(testObra.getGenField1()).isEqualTo(UPDATED_GEN_FIELD_1);
        assertThat(testObra.getParaFotografia()).isEqualTo(UPDATED_PARA_FOTOGRAFIA);
        assertThat(testObra.getGenMarcaInscrObra()).isEqualTo(UPDATED_GEN_MARCA_INSCR_OBRA);
        assertThat(testObra.getPalavrasChave()).isEqualTo(UPDATED_PALAVRAS_CHAVE);
        assertThat(testObra.getGenMarcaInscrSuporte()).isEqualTo(UPDATED_GEN_MARCA_INSCR_SUPORTE);
        assertThat(testObra.getGenObs()).isEqualTo(UPDATED_GEN_OBS);
        assertThat(testObra.getGenVerbete()).isEqualTo(UPDATED_GEN_VERBETE);
    }

    @Test
    @Transactional
    void patchNonExistingObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, obraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(obraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObra() throws Exception {
        int databaseSizeBeforeUpdate = obraRepository.findAll().size();
        obra.setId(count.incrementAndGet());

        // Create the Obra
        ObraDTO obraDTO = obraMapper.toDto(obra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObraMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(obraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Obra in the database
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObra() throws Exception {
        // Initialize the database
        obraRepository.saveAndFlush(obra);

        int databaseSizeBeforeDelete = obraRepository.findAll().size();

        // Delete the obra
        restObraMockMvc
            .perform(delete(ENTITY_API_URL_ID, obra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Obra> obraList = obraRepository.findAll();
        assertThat(obraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
