package br.com.nhw.std.artes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entidade: Obra\nDados principais das obras do Acervo de Arte
 */
@Entity
@Table(name = "obra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Obra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 8)
    @Column(name = "tombo", length = 8, nullable = false)
    private String tombo;

    /**
     * Nº Tombo da Obras
     */
    @Size(max = 1)
    @Column(name = "multiplo", length = 1)
    private String multiplo;

    /**
     * (M) - Indica Obra Multipla: (-)=multiplo; (.)=cópia
     */
    @Size(max = 50)
    @Column(name = "numero_registro", length = 50)
    private String numeroRegistro;

    /**
     * Número do Registro existente no Bradesco = ATIP
     */
    @Size(max = 200)
    @Column(name = "outros_titulos", length = 200)
    private String outrosTitulos;

    /**
     * Outros Títulos da obra
     */
    @Size(max = 200)
    @Column(name = "titulo_original", length = 200)
    private String tituloOriginal;

    /**
     * Título da original da obra
     */
    @Size(max = 100)
    @Column(name = "origem", length = 100)
    private String origem;

    /**
     * Local de origem onde foi feita a obra
     */
    @Size(max = 64000)
    @Column(name = "descricao", length = 64000)
    private String descricao;

    /**
     * Descrição da obra
     */
    @Column(name = "assinatura")
    private Boolean assinatura;

    /**
     * Se a obra é assinada ou não
     */
    @Size(max = 64000)
    @Column(name = "local_assinatura", length = 64000)
    private String localAssinatura;

    /**
     * Localização da assinatura do artista na obra
     */
    @Size(max = 64000)
    @Column(name = "marca_inscr_obra", length = 64000)
    private String marcaInscrObra;

    /**
     * Marcas e/ou Inscrições que se encontram na obra
     */
    @Size(max = 64000)
    @Column(name = "marca_inscr_suporte", length = 64000)
    private String marcaInscrSuporte;

    /**
     * Marcas e/ou Inscrições que se encontram no suporte/moldura da obra
     */
    @Column(name = "medidas_aprox")
    private Boolean medidasAprox;

    /**
     * Indica se as medidas são aproximadas ou se reais
     */
    @Column(name = "altura_obra", precision = 21, scale = 2)
    private BigDecimal alturaObra;

    /**
     * Altura da Obra s/suporte ou moldura (precisão 2 decimais)
     */
    @Column(name = "larg_obra", precision = 21, scale = 2)
    private BigDecimal largObra;

    /**
     * Largura da Obra s/suporte ou moldura (precisão 2 decimais)
     */
    @Column(name = "prof_obra", precision = 21, scale = 2)
    private BigDecimal profObra;

    /**
     * Profundidade da Obra s/suporte ou moldura (precisão 2 decimais)
     */
    @Size(max = 6)
    @Column(name = "diametr_obra", length = 6)
    private String diametrObra;

    /**
     * Diâmetro da Obra s/suporte ou moldura
     */
    @Column(name = "altura_mold", precision = 21, scale = 2)
    private BigDecimal alturaMold;

    /**
     * Altura da Obra c/Moldura (precisão 2 decimais)
     */
    @Column(name = "larg_mold", precision = 21, scale = 2)
    private BigDecimal largMold;

    /**
     * Largura da Obra c/Moldura (precisão 2 decimais)
     */
    @Column(name = "prof_mold", precision = 21, scale = 2)
    private BigDecimal profMold;

    /**
     * Profundidade da Obra c/Moldura (precisão 2 decimais)
     */
    @Size(max = 10)
    @Column(name = "diametro_mold", length = 10)
    private String diametroMold;

    /**
     * Diâmetro da Obra s/suporte ou moldura
     */
    @Size(max = 150)
    @Column(name = "dimens_adic", length = 150)
    private String dimensAdic;

    /**
     * Dimensões adicionais da obra
     */
    @Size(max = 10)
    @Column(name = "pes_obra", length = 10)
    private String pesObra;

    /**
     * Peso da Obra
     */
    @Column(name = "atribuido")
    private Boolean atribuido;

    /**
     * Autoria é atribuída ou não
     */
    @Size(max = 10)
    @Column(name = "n_foto", length = 10)
    private String nFoto;

    /**
     * Nome e ou número da foto correspondente a obra
     */
    @Column(name = "conjunto")
    private Boolean conjunto;

    /**
     * Faz parte de um conjunto?
     */
    @Size(max = 100)
    @Column(name = "conj_tombo", length = 100)
    private String conjTombo;

    /**
     * Nº Tombos que formam o  conjunto
     */
    @Column(name = "valor_seguro", precision = 21, scale = 2)
    private BigDecimal valorSeguro;

    /**
     * Valor do Seguro (precisão 4 decimais)
     */
    @Column(name = "valor_seguro_real", precision = 21, scale = 2)
    private BigDecimal valorSeguroReal;

    /**
     * Valor do Seguro convertido em Reais (precisão 4 decimais)
     */
    @Column(name = "dataconversao")
    private LocalDate dataconversao;

    /**
     * Data da conversão da moeda
     */
    @Column(name = "data_alter_apolice")
    private LocalDate dataAlterApolice;

    /**
     * Data que a obra foi Incluida/Excluida na Apólice
     */
    @Size(max = 100)
    @Column(name = "local_atual", length = 100)
    private String localAtual;

    /**
     * Localização atual da obra - relacionada com QryLocalização
     */
    @Size(max = 200)
    @Column(name = "local_atual_novo", length = 200)
    private String localAtualNovo;

    /**
     * Localização atual da obra - relacionada com QryLocalizaçãoNOVA
     */
    @Size(max = 50)
    @Column(name = "cod_parametro", length = 50)
    private String codParametro;

    /**
     * Categorias da tblParametros para associarmos com a obra
     */
    @Size(max = 64000)
    @Column(name = "obs", length = 64000)
    private String obs;

    /**
     * Observações gerais da obra
     */
    @Size(max = 64000)
    @Column(name = "tiragem", length = 64000)
    private String tiragem;

    /**
     * Tiragem da gravura
     */
    @Size(max = 200)
    @Column(name = "serie", length = 200)
    private String serie;

    /**
     * Título da Série da gravura
     */
    @Column(name = "selo")
    private Integer selo;

    /**
     * Classificação da obra quanto ao uso, designado pelos pesquisadores
     */
    @Size(max = 50)
    @Column(name = "tombo_anterio", length = 50)
    private String tomboAnterio;

    /**
     * Número da antiga catalogação
     */
    @Size(max = 64000)
    @Column(name = "verbete", length = 64000)
    private String verbete;

    /**
     * Dados específicos sobre a obra
     */
    @Column(name = "livro")
    private Boolean livro;

    /**
     * Selecionar caso a obra esteja participando da última publicação da coleção
     */
    @Column(name = "imagem_alta")
    private Boolean imagemAlta;

    /**
     * Selecionar caso a obra possua imagem digital em alta resolução
     */
    @Column(name = "apabex")
    private Boolean apabex;

    /**
     * Selecione caso a obra esteja incluída no projeto da Depuração da Coleção, com destino à APABEX
     */
    @Column(name = "bunkyo")
    private Boolean bunkyo;

    /**
     * Selecione caso a obra esteja incluída no projeto da Depuração da Coleção, com destino ao BUNKYO
     */
    @Size(max = 255)
    @Column(name = "fase_depuracao", length = 255)
    private String faseDepuracao;

    /**
     * Indique em qual fase a obra será destinada à Apabex ou ao Bunkyo
     */
    @Column(name = "para_avaliacao")
    private Boolean paraAvaliacao;

    /**
     * Selecionar caso a obra necessite ser encaminhada para avaliação de mercado
     */
    @Column(name = "para_restauracao")
    private Boolean paraRestauracao;

    /**
     * Selecionar caso a obra necessite ser encaminhada para restauração
     */
    @Column(name = "para_moldura")
    private Boolean paraMoldura;

    /**
     * Selecionar caso a obra necessite ser encaminhada para confecção de moldura
     */
    @Column(name = "para_legenda")
    private Boolean paraLegenda;

    /**
     * Selecionar caso a obra necessite ser encaminhada para confecção de legenda
     */
    @Column(name = "temp_field")
    private Boolean tempField;

    /**
     * Selecionar caso a obra necessite ser encaminhada para foto em alta
     */
    @Column(name = "selecao_lista_avulsa")
    private Boolean selecaoListaAvulsa;

    /**
     * Selecionar para incluir a obra em uma eventual seleção avulsa
     */
    @Column(name = "dominio_publ")
    private Boolean dominioPubl;

    /**
     * É de domínio público
     */
    @Column(name = "dt_venc_foto")
    private LocalDate dtVencFoto;

    /**
     * Data de vencimento de uso de imagem do Fotografo
     */
    @Size(max = 255)
    @Column(name = "obs_uso_foto", length = 255)
    private String obsUsoFoto;

    /**
     * Observações de uso de imagem do Fotografo
     */
    @Size(max = 255)
    @Column(name = "local_foto_alta", length = 255)
    private String localFotoAlta;

    /**
     * Localização da FotoAlta
     */
    @Column(name = "data_inclusao")
    private LocalDate dataInclusao;

    /**
     * Data que a obra foi inserida
     */
    @Column(name = "data_exclusao")
    private LocalDate dataExclusao;

    /**
     * Data que a obra foi excluida
     */
    @Column(name = "book_x")
    private Integer bookX;

    /**
     * Posicao do Eixo X no book
     */
    @Column(name = "book_y")
    private Integer bookY;

    /**
     * Posicao do Eixo Y no book
     */
    @Column(name = "gen_descricao")
    private Integer genDescricao;

    @Column(name = "gen_field_1")
    private Integer genField1;

    @Column(name = "para_fotografia")
    private Boolean paraFotografia;

    /**
     * Selecionar caso a obra necessite ser encaminhada para foto em alta
     */
    @Column(name = "gen_marca_inscr_obra")
    private Integer genMarcaInscrObra;

    @Size(max = 64000)
    @Column(name = "palavras_chave", length = 64000)
    private String palavrasChave;

    /**
     * Palavras-chave relacionadas à obra, para pesquisa mais detalhada
     */
    @Column(name = "gen_marca_inscr_suporte")
    private Integer genMarcaInscrSuporte;

    @Column(name = "gen_obs")
    private Integer genObs;

    @Column(name = "gen_verbete")
    private Integer genVerbete;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_obra__dado_documental",
        joinColumns = @JoinColumn(name = "obra_id"),
        inverseJoinColumns = @JoinColumn(name = "dado_documental_id")
    )
    @JsonIgnoreProperties(value = { "tipoDocumento", "obras" }, allowSetters = true)
    private Set<DadoDocumental> dadoDocumentals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "obras", "contatoes", "cidadeNasc", "cidadeFalesc", "respVerbete", "funcaoArtista" },
        allowSetters = true
    )
    private Artista artista;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras" }, allowSetters = true)
    private Categoria categoria;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras" }, allowSetters = true)
    private Tecnica tecnica;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras" }, allowSetters = true)
    private Nivel nivel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras" }, allowSetters = true)
    private Data data;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras", "cidade" }, allowSetters = true)
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras", "seguros" }, allowSetters = true)
    private Moeda moeda;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras", "contatoSeg", "contatoCor", "moeda" }, allowSetters = true)
    private Seguro seguro;

    @ManyToOne
    @JsonIgnoreProperties(value = { "artVerbetes", "obras" }, allowSetters = true)
    private Responsavel responsavel;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras" }, allowSetters = true)
    private AcervoAtual acervoatual;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras", "seguroSegs", "seguroCors", "area", "cidade", "artistas" }, allowSetters = true)
    private Contato fotografo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras", "espaco" }, allowSetters = true)
    private AndarMapa andarMapa;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Obra id(Long id) {
        this.id = id;
        return this;
    }

    public String getTombo() {
        return this.tombo;
    }

    public Obra tombo(String tombo) {
        this.tombo = tombo;
        return this;
    }

    public void setTombo(String tombo) {
        this.tombo = tombo;
    }

    public String getMultiplo() {
        return this.multiplo;
    }

    public Obra multiplo(String multiplo) {
        this.multiplo = multiplo;
        return this;
    }

    public void setMultiplo(String multiplo) {
        this.multiplo = multiplo;
    }

    public String getNumeroRegistro() {
        return this.numeroRegistro;
    }

    public Obra numeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
        return this;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getOutrosTitulos() {
        return this.outrosTitulos;
    }

    public Obra outrosTitulos(String outrosTitulos) {
        this.outrosTitulos = outrosTitulos;
        return this;
    }

    public void setOutrosTitulos(String outrosTitulos) {
        this.outrosTitulos = outrosTitulos;
    }

    public String getTituloOriginal() {
        return this.tituloOriginal;
    }

    public Obra tituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
        return this;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getOrigem() {
        return this.origem;
    }

    public Obra origem(String origem) {
        this.origem = origem;
        return this;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Obra descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAssinatura() {
        return this.assinatura;
    }

    public Obra assinatura(Boolean assinatura) {
        this.assinatura = assinatura;
        return this;
    }

    public void setAssinatura(Boolean assinatura) {
        this.assinatura = assinatura;
    }

    public String getLocalAssinatura() {
        return this.localAssinatura;
    }

    public Obra localAssinatura(String localAssinatura) {
        this.localAssinatura = localAssinatura;
        return this;
    }

    public void setLocalAssinatura(String localAssinatura) {
        this.localAssinatura = localAssinatura;
    }

    public String getMarcaInscrObra() {
        return this.marcaInscrObra;
    }

    public Obra marcaInscrObra(String marcaInscrObra) {
        this.marcaInscrObra = marcaInscrObra;
        return this;
    }

    public void setMarcaInscrObra(String marcaInscrObra) {
        this.marcaInscrObra = marcaInscrObra;
    }

    public String getMarcaInscrSuporte() {
        return this.marcaInscrSuporte;
    }

    public Obra marcaInscrSuporte(String marcaInscrSuporte) {
        this.marcaInscrSuporte = marcaInscrSuporte;
        return this;
    }

    public void setMarcaInscrSuporte(String marcaInscrSuporte) {
        this.marcaInscrSuporte = marcaInscrSuporte;
    }

    public Boolean getMedidasAprox() {
        return this.medidasAprox;
    }

    public Obra medidasAprox(Boolean medidasAprox) {
        this.medidasAprox = medidasAprox;
        return this;
    }

    public void setMedidasAprox(Boolean medidasAprox) {
        this.medidasAprox = medidasAprox;
    }

    public BigDecimal getAlturaObra() {
        return this.alturaObra;
    }

    public Obra alturaObra(BigDecimal alturaObra) {
        this.alturaObra = alturaObra;
        return this;
    }

    public void setAlturaObra(BigDecimal alturaObra) {
        this.alturaObra = alturaObra;
    }

    public BigDecimal getLargObra() {
        return this.largObra;
    }

    public Obra largObra(BigDecimal largObra) {
        this.largObra = largObra;
        return this;
    }

    public void setLargObra(BigDecimal largObra) {
        this.largObra = largObra;
    }

    public BigDecimal getProfObra() {
        return this.profObra;
    }

    public Obra profObra(BigDecimal profObra) {
        this.profObra = profObra;
        return this;
    }

    public void setProfObra(BigDecimal profObra) {
        this.profObra = profObra;
    }

    public String getDiametrObra() {
        return this.diametrObra;
    }

    public Obra diametrObra(String diametrObra) {
        this.diametrObra = diametrObra;
        return this;
    }

    public void setDiametrObra(String diametrObra) {
        this.diametrObra = diametrObra;
    }

    public BigDecimal getAlturaMold() {
        return this.alturaMold;
    }

    public Obra alturaMold(BigDecimal alturaMold) {
        this.alturaMold = alturaMold;
        return this;
    }

    public void setAlturaMold(BigDecimal alturaMold) {
        this.alturaMold = alturaMold;
    }

    public BigDecimal getLargMold() {
        return this.largMold;
    }

    public Obra largMold(BigDecimal largMold) {
        this.largMold = largMold;
        return this;
    }

    public void setLargMold(BigDecimal largMold) {
        this.largMold = largMold;
    }

    public BigDecimal getProfMold() {
        return this.profMold;
    }

    public Obra profMold(BigDecimal profMold) {
        this.profMold = profMold;
        return this;
    }

    public void setProfMold(BigDecimal profMold) {
        this.profMold = profMold;
    }

    public String getDiametroMold() {
        return this.diametroMold;
    }

    public Obra diametroMold(String diametroMold) {
        this.diametroMold = diametroMold;
        return this;
    }

    public void setDiametroMold(String diametroMold) {
        this.diametroMold = diametroMold;
    }

    public String getDimensAdic() {
        return this.dimensAdic;
    }

    public Obra dimensAdic(String dimensAdic) {
        this.dimensAdic = dimensAdic;
        return this;
    }

    public void setDimensAdic(String dimensAdic) {
        this.dimensAdic = dimensAdic;
    }

    public String getPesObra() {
        return this.pesObra;
    }

    public Obra pesObra(String pesObra) {
        this.pesObra = pesObra;
        return this;
    }

    public void setPesObra(String pesObra) {
        this.pesObra = pesObra;
    }

    public Boolean getAtribuido() {
        return this.atribuido;
    }

    public Obra atribuido(Boolean atribuido) {
        this.atribuido = atribuido;
        return this;
    }

    public void setAtribuido(Boolean atribuido) {
        this.atribuido = atribuido;
    }

    public String getnFoto() {
        return this.nFoto;
    }

    public Obra nFoto(String nFoto) {
        this.nFoto = nFoto;
        return this;
    }

    public void setnFoto(String nFoto) {
        this.nFoto = nFoto;
    }

    public Boolean getConjunto() {
        return this.conjunto;
    }

    public Obra conjunto(Boolean conjunto) {
        this.conjunto = conjunto;
        return this;
    }

    public void setConjunto(Boolean conjunto) {
        this.conjunto = conjunto;
    }

    public String getConjTombo() {
        return this.conjTombo;
    }

    public Obra conjTombo(String conjTombo) {
        this.conjTombo = conjTombo;
        return this;
    }

    public void setConjTombo(String conjTombo) {
        this.conjTombo = conjTombo;
    }

    public BigDecimal getValorSeguro() {
        return this.valorSeguro;
    }

    public Obra valorSeguro(BigDecimal valorSeguro) {
        this.valorSeguro = valorSeguro;
        return this;
    }

    public void setValorSeguro(BigDecimal valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public BigDecimal getValorSeguroReal() {
        return this.valorSeguroReal;
    }

    public Obra valorSeguroReal(BigDecimal valorSeguroReal) {
        this.valorSeguroReal = valorSeguroReal;
        return this;
    }

    public void setValorSeguroReal(BigDecimal valorSeguroReal) {
        this.valorSeguroReal = valorSeguroReal;
    }

    public LocalDate getDataconversao() {
        return this.dataconversao;
    }

    public Obra dataconversao(LocalDate dataconversao) {
        this.dataconversao = dataconversao;
        return this;
    }

    public void setDataconversao(LocalDate dataconversao) {
        this.dataconversao = dataconversao;
    }

    public LocalDate getDataAlterApolice() {
        return this.dataAlterApolice;
    }

    public Obra dataAlterApolice(LocalDate dataAlterApolice) {
        this.dataAlterApolice = dataAlterApolice;
        return this;
    }

    public void setDataAlterApolice(LocalDate dataAlterApolice) {
        this.dataAlterApolice = dataAlterApolice;
    }

    public String getLocalAtual() {
        return this.localAtual;
    }

    public Obra localAtual(String localAtual) {
        this.localAtual = localAtual;
        return this;
    }

    public void setLocalAtual(String localAtual) {
        this.localAtual = localAtual;
    }

    public String getLocalAtualNovo() {
        return this.localAtualNovo;
    }

    public Obra localAtualNovo(String localAtualNovo) {
        this.localAtualNovo = localAtualNovo;
        return this;
    }

    public void setLocalAtualNovo(String localAtualNovo) {
        this.localAtualNovo = localAtualNovo;
    }

    public String getCodParametro() {
        return this.codParametro;
    }

    public Obra codParametro(String codParametro) {
        this.codParametro = codParametro;
        return this;
    }

    public void setCodParametro(String codParametro) {
        this.codParametro = codParametro;
    }

    public String getObs() {
        return this.obs;
    }

    public Obra obs(String obs) {
        this.obs = obs;
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getTiragem() {
        return this.tiragem;
    }

    public Obra tiragem(String tiragem) {
        this.tiragem = tiragem;
        return this;
    }

    public void setTiragem(String tiragem) {
        this.tiragem = tiragem;
    }

    public String getSerie() {
        return this.serie;
    }

    public Obra serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getSelo() {
        return this.selo;
    }

    public Obra selo(Integer selo) {
        this.selo = selo;
        return this;
    }

    public void setSelo(Integer selo) {
        this.selo = selo;
    }

    public String getTomboAnterio() {
        return this.tomboAnterio;
    }

    public Obra tomboAnterio(String tomboAnterio) {
        this.tomboAnterio = tomboAnterio;
        return this;
    }

    public void setTomboAnterio(String tomboAnterio) {
        this.tomboAnterio = tomboAnterio;
    }

    public String getVerbete() {
        return this.verbete;
    }

    public Obra verbete(String verbete) {
        this.verbete = verbete;
        return this;
    }

    public void setVerbete(String verbete) {
        this.verbete = verbete;
    }

    public Boolean getLivro() {
        return this.livro;
    }

    public Obra livro(Boolean livro) {
        this.livro = livro;
        return this;
    }

    public void setLivro(Boolean livro) {
        this.livro = livro;
    }

    public Boolean getImagemAlta() {
        return this.imagemAlta;
    }

    public Obra imagemAlta(Boolean imagemAlta) {
        this.imagemAlta = imagemAlta;
        return this;
    }

    public void setImagemAlta(Boolean imagemAlta) {
        this.imagemAlta = imagemAlta;
    }

    public Boolean getApabex() {
        return this.apabex;
    }

    public Obra apabex(Boolean apabex) {
        this.apabex = apabex;
        return this;
    }

    public void setApabex(Boolean apabex) {
        this.apabex = apabex;
    }

    public Boolean getBunkyo() {
        return this.bunkyo;
    }

    public Obra bunkyo(Boolean bunkyo) {
        this.bunkyo = bunkyo;
        return this;
    }

    public void setBunkyo(Boolean bunkyo) {
        this.bunkyo = bunkyo;
    }

    public String getFaseDepuracao() {
        return this.faseDepuracao;
    }

    public Obra faseDepuracao(String faseDepuracao) {
        this.faseDepuracao = faseDepuracao;
        return this;
    }

    public void setFaseDepuracao(String faseDepuracao) {
        this.faseDepuracao = faseDepuracao;
    }

    public Boolean getParaAvaliacao() {
        return this.paraAvaliacao;
    }

    public Obra paraAvaliacao(Boolean paraAvaliacao) {
        this.paraAvaliacao = paraAvaliacao;
        return this;
    }

    public void setParaAvaliacao(Boolean paraAvaliacao) {
        this.paraAvaliacao = paraAvaliacao;
    }

    public Boolean getParaRestauracao() {
        return this.paraRestauracao;
    }

    public Obra paraRestauracao(Boolean paraRestauracao) {
        this.paraRestauracao = paraRestauracao;
        return this;
    }

    public void setParaRestauracao(Boolean paraRestauracao) {
        this.paraRestauracao = paraRestauracao;
    }

    public Boolean getParaMoldura() {
        return this.paraMoldura;
    }

    public Obra paraMoldura(Boolean paraMoldura) {
        this.paraMoldura = paraMoldura;
        return this;
    }

    public void setParaMoldura(Boolean paraMoldura) {
        this.paraMoldura = paraMoldura;
    }

    public Boolean getParaLegenda() {
        return this.paraLegenda;
    }

    public Obra paraLegenda(Boolean paraLegenda) {
        this.paraLegenda = paraLegenda;
        return this;
    }

    public void setParaLegenda(Boolean paraLegenda) {
        this.paraLegenda = paraLegenda;
    }

    public Boolean getTempField() {
        return this.tempField;
    }

    public Obra tempField(Boolean tempField) {
        this.tempField = tempField;
        return this;
    }

    public void setTempField(Boolean tempField) {
        this.tempField = tempField;
    }

    public Boolean getSelecaoListaAvulsa() {
        return this.selecaoListaAvulsa;
    }

    public Obra selecaoListaAvulsa(Boolean selecaoListaAvulsa) {
        this.selecaoListaAvulsa = selecaoListaAvulsa;
        return this;
    }

    public void setSelecaoListaAvulsa(Boolean selecaoListaAvulsa) {
        this.selecaoListaAvulsa = selecaoListaAvulsa;
    }

    public Boolean getDominioPubl() {
        return this.dominioPubl;
    }

    public Obra dominioPubl(Boolean dominioPubl) {
        this.dominioPubl = dominioPubl;
        return this;
    }

    public void setDominioPubl(Boolean dominioPubl) {
        this.dominioPubl = dominioPubl;
    }

    public LocalDate getDtVencFoto() {
        return this.dtVencFoto;
    }

    public Obra dtVencFoto(LocalDate dtVencFoto) {
        this.dtVencFoto = dtVencFoto;
        return this;
    }

    public void setDtVencFoto(LocalDate dtVencFoto) {
        this.dtVencFoto = dtVencFoto;
    }

    public String getObsUsoFoto() {
        return this.obsUsoFoto;
    }

    public Obra obsUsoFoto(String obsUsoFoto) {
        this.obsUsoFoto = obsUsoFoto;
        return this;
    }

    public void setObsUsoFoto(String obsUsoFoto) {
        this.obsUsoFoto = obsUsoFoto;
    }

    public String getLocalFotoAlta() {
        return this.localFotoAlta;
    }

    public Obra localFotoAlta(String localFotoAlta) {
        this.localFotoAlta = localFotoAlta;
        return this;
    }

    public void setLocalFotoAlta(String localFotoAlta) {
        this.localFotoAlta = localFotoAlta;
    }

    public LocalDate getDataInclusao() {
        return this.dataInclusao;
    }

    public Obra dataInclusao(LocalDate dataInclusao) {
        this.dataInclusao = dataInclusao;
        return this;
    }

    public void setDataInclusao(LocalDate dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDate getDataExclusao() {
        return this.dataExclusao;
    }

    public Obra dataExclusao(LocalDate dataExclusao) {
        this.dataExclusao = dataExclusao;
        return this;
    }

    public void setDataExclusao(LocalDate dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public Integer getBookX() {
        return this.bookX;
    }

    public Obra bookX(Integer bookX) {
        this.bookX = bookX;
        return this;
    }

    public void setBookX(Integer bookX) {
        this.bookX = bookX;
    }

    public Integer getBookY() {
        return this.bookY;
    }

    public Obra bookY(Integer bookY) {
        this.bookY = bookY;
        return this;
    }

    public void setBookY(Integer bookY) {
        this.bookY = bookY;
    }

    public Integer getGenDescricao() {
        return this.genDescricao;
    }

    public Obra genDescricao(Integer genDescricao) {
        this.genDescricao = genDescricao;
        return this;
    }

    public void setGenDescricao(Integer genDescricao) {
        this.genDescricao = genDescricao;
    }

    public Integer getGenField1() {
        return this.genField1;
    }

    public Obra genField1(Integer genField1) {
        this.genField1 = genField1;
        return this;
    }

    public void setGenField1(Integer genField1) {
        this.genField1 = genField1;
    }

    public Boolean getParaFotografia() {
        return this.paraFotografia;
    }

    public Obra paraFotografia(Boolean paraFotografia) {
        this.paraFotografia = paraFotografia;
        return this;
    }

    public void setParaFotografia(Boolean paraFotografia) {
        this.paraFotografia = paraFotografia;
    }

    public Integer getGenMarcaInscrObra() {
        return this.genMarcaInscrObra;
    }

    public Obra genMarcaInscrObra(Integer genMarcaInscrObra) {
        this.genMarcaInscrObra = genMarcaInscrObra;
        return this;
    }

    public void setGenMarcaInscrObra(Integer genMarcaInscrObra) {
        this.genMarcaInscrObra = genMarcaInscrObra;
    }

    public String getPalavrasChave() {
        return this.palavrasChave;
    }

    public Obra palavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
        return this;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public Integer getGenMarcaInscrSuporte() {
        return this.genMarcaInscrSuporte;
    }

    public Obra genMarcaInscrSuporte(Integer genMarcaInscrSuporte) {
        this.genMarcaInscrSuporte = genMarcaInscrSuporte;
        return this;
    }

    public void setGenMarcaInscrSuporte(Integer genMarcaInscrSuporte) {
        this.genMarcaInscrSuporte = genMarcaInscrSuporte;
    }

    public Integer getGenObs() {
        return this.genObs;
    }

    public Obra genObs(Integer genObs) {
        this.genObs = genObs;
        return this;
    }

    public void setGenObs(Integer genObs) {
        this.genObs = genObs;
    }

    public Integer getGenVerbete() {
        return this.genVerbete;
    }

    public Obra genVerbete(Integer genVerbete) {
        this.genVerbete = genVerbete;
        return this;
    }

    public void setGenVerbete(Integer genVerbete) {
        this.genVerbete = genVerbete;
    }

    public Set<DadoDocumental> getDadoDocumentals() {
        return this.dadoDocumentals;
    }

    public Obra dadoDocumentals(Set<DadoDocumental> dadoDocumentals) {
        this.setDadoDocumentals(dadoDocumentals);
        return this;
    }

    public Obra addDadoDocumental(DadoDocumental dadoDocumental) {
        this.dadoDocumentals.add(dadoDocumental);
        dadoDocumental.getObras().add(this);
        return this;
    }

    public Obra removeDadoDocumental(DadoDocumental dadoDocumental) {
        this.dadoDocumentals.remove(dadoDocumental);
        dadoDocumental.getObras().remove(this);
        return this;
    }

    public void setDadoDocumentals(Set<DadoDocumental> dadoDocumentals) {
        this.dadoDocumentals = dadoDocumentals;
    }

    public Artista getArtista() {
        return this.artista;
    }

    public Obra artista(Artista artista) {
        this.setArtista(artista);
        return this;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public Obra categoria(Categoria categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Tecnica getTecnica() {
        return this.tecnica;
    }

    public Obra tecnica(Tecnica tecnica) {
        this.setTecnica(tecnica);
        return this;
    }

    public void setTecnica(Tecnica tecnica) {
        this.tecnica = tecnica;
    }

    public Nivel getNivel() {
        return this.nivel;
    }

    public Obra nivel(Nivel nivel) {
        this.setNivel(nivel);
        return this;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Data getData() {
        return this.data;
    }

    public Obra data(Data data) {
        this.setData(data);
        return this;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public Obra empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Moeda getMoeda() {
        return this.moeda;
    }

    public Obra moeda(Moeda moeda) {
        this.setMoeda(moeda);
        return this;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public Seguro getSeguro() {
        return this.seguro;
    }

    public Obra seguro(Seguro seguro) {
        this.setSeguro(seguro);
        return this;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    public Responsavel getResponsavel() {
        return this.responsavel;
    }

    public Obra responsavel(Responsavel responsavel) {
        this.setResponsavel(responsavel);
        return this;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public AcervoAtual getAcervoatual() {
        return this.acervoatual;
    }

    public Obra acervoatual(AcervoAtual acervoAtual) {
        this.setAcervoatual(acervoAtual);
        return this;
    }

    public void setAcervoatual(AcervoAtual acervoAtual) {
        this.acervoatual = acervoAtual;
    }

    public Contato getFotografo() {
        return this.fotografo;
    }

    public Obra fotografo(Contato contato) {
        this.setFotografo(contato);
        return this;
    }

    public void setFotografo(Contato contato) {
        this.fotografo = contato;
    }

    public AndarMapa getAndarMapa() {
        return this.andarMapa;
    }

    public Obra andarMapa(AndarMapa andarMapa) {
        this.setAndarMapa(andarMapa);
        return this;
    }

    public void setAndarMapa(AndarMapa andarMapa) {
        this.andarMapa = andarMapa;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Obra)) {
            return false;
        }
        return id != null && id.equals(((Obra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Obra{" +
            "id=" + getId() +
            ", tombo='" + getTombo() + "'" +
            ", multiplo='" + getMultiplo() + "'" +
            ", numeroRegistro='" + getNumeroRegistro() + "'" +
            ", outrosTitulos='" + getOutrosTitulos() + "'" +
            ", tituloOriginal='" + getTituloOriginal() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", assinatura='" + getAssinatura() + "'" +
            ", localAssinatura='" + getLocalAssinatura() + "'" +
            ", marcaInscrObra='" + getMarcaInscrObra() + "'" +
            ", marcaInscrSuporte='" + getMarcaInscrSuporte() + "'" +
            ", medidasAprox='" + getMedidasAprox() + "'" +
            ", alturaObra=" + getAlturaObra() +
            ", largObra=" + getLargObra() +
            ", profObra=" + getProfObra() +
            ", diametrObra='" + getDiametrObra() + "'" +
            ", alturaMold=" + getAlturaMold() +
            ", largMold=" + getLargMold() +
            ", profMold=" + getProfMold() +
            ", diametroMold='" + getDiametroMold() + "'" +
            ", dimensAdic='" + getDimensAdic() + "'" +
            ", pesObra='" + getPesObra() + "'" +
            ", atribuido='" + getAtribuido() + "'" +
            ", nFoto='" + getnFoto() + "'" +
            ", conjunto='" + getConjunto() + "'" +
            ", conjTombo='" + getConjTombo() + "'" +
            ", valorSeguro=" + getValorSeguro() +
            ", valorSeguroReal=" + getValorSeguroReal() +
            ", dataconversao='" + getDataconversao() + "'" +
            ", dataAlterApolice='" + getDataAlterApolice() + "'" +
            ", localAtual='" + getLocalAtual() + "'" +
            ", localAtualNovo='" + getLocalAtualNovo() + "'" +
            ", codParametro='" + getCodParametro() + "'" +
            ", obs='" + getObs() + "'" +
            ", tiragem='" + getTiragem() + "'" +
            ", serie='" + getSerie() + "'" +
            ", selo=" + getSelo() +
            ", tomboAnterio='" + getTomboAnterio() + "'" +
            ", verbete='" + getVerbete() + "'" +
            ", livro='" + getLivro() + "'" +
            ", imagemAlta='" + getImagemAlta() + "'" +
            ", apabex='" + getApabex() + "'" +
            ", bunkyo='" + getBunkyo() + "'" +
            ", faseDepuracao='" + getFaseDepuracao() + "'" +
            ", paraAvaliacao='" + getParaAvaliacao() + "'" +
            ", paraRestauracao='" + getParaRestauracao() + "'" +
            ", paraMoldura='" + getParaMoldura() + "'" +
            ", paraLegenda='" + getParaLegenda() + "'" +
            ", tempField='" + getTempField() + "'" +
            ", selecaoListaAvulsa='" + getSelecaoListaAvulsa() + "'" +
            ", dominioPubl='" + getDominioPubl() + "'" +
            ", dtVencFoto='" + getDtVencFoto() + "'" +
            ", obsUsoFoto='" + getObsUsoFoto() + "'" +
            ", localFotoAlta='" + getLocalFotoAlta() + "'" +
            ", dataInclusao='" + getDataInclusao() + "'" +
            ", dataExclusao='" + getDataExclusao() + "'" +
            ", bookX=" + getBookX() +
            ", bookY=" + getBookY() +
            ", genDescricao=" + getGenDescricao() +
            ", genField1=" + getGenField1() +
            ", paraFotografia='" + getParaFotografia() + "'" +
            ", genMarcaInscrObra=" + getGenMarcaInscrObra() +
            ", palavrasChave='" + getPalavrasChave() + "'" +
            ", genMarcaInscrSuporte=" + getGenMarcaInscrSuporte() +
            ", genObs=" + getGenObs() +
            ", genVerbete=" + getGenVerbete() +
            "}";
    }
}
