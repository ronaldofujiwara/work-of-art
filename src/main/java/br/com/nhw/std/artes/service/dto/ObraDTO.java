package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Obra} entity.
 */
@ApiModel(description = "Entidade: Obra\nDados principais das obras do Acervo de Arte")
public class ObraDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 8)
    private String tombo;

    /**
     * Nº Tombo da Obras
     */
    @Size(max = 1)
    @ApiModelProperty(value = "Nº Tombo da Obras")
    private String multiplo;

    /**
     * (M) - Indica Obra Multipla: (-)=multiplo; (.)=cópia
     */
    @Size(max = 50)
    @ApiModelProperty(value = "(M) - Indica Obra Multipla: (-)=multiplo; (.)=cópia")
    private String numeroRegistro;

    /**
     * Número do Registro existente no Bradesco = ATIP
     */
    @Size(max = 200)
    @ApiModelProperty(value = "Número do Registro existente no Bradesco = ATIP")
    private String outrosTitulos;

    /**
     * Outros Títulos da obra
     */
    @Size(max = 200)
    @ApiModelProperty(value = "Outros Títulos da obra")
    private String tituloOriginal;

    /**
     * Título da original da obra
     */
    @Size(max = 100)
    @ApiModelProperty(value = "Título da original da obra")
    private String origem;

    /**
     * Local de origem onde foi feita a obra
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Local de origem onde foi feita a obra")
    private String descricao;

    /**
     * Descrição da obra
     */
    @ApiModelProperty(value = "Descrição da obra")
    private Boolean assinatura;

    /**
     * Se a obra é assinada ou não
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Se a obra é assinada ou não")
    private String localAssinatura;

    /**
     * Localização da assinatura do artista na obra
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Localização da assinatura do artista na obra")
    private String marcaInscrObra;

    /**
     * Marcas e/ou Inscrições que se encontram na obra
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Marcas e/ou Inscrições que se encontram na obra")
    private String marcaInscrSuporte;

    /**
     * Marcas e/ou Inscrições que se encontram no suporte/moldura da obra
     */
    @ApiModelProperty(value = "Marcas e/ou Inscrições que se encontram no suporte/moldura da obra")
    private Boolean medidasAprox;

    /**
     * Indica se as medidas são aproximadas ou se reais
     */
    @ApiModelProperty(value = "Indica se as medidas são aproximadas ou se reais")
    private BigDecimal alturaObra;

    /**
     * Altura da Obra s/suporte ou moldura (precisão 2 decimais)
     */
    @ApiModelProperty(value = "Altura da Obra s/suporte ou moldura (precisão 2 decimais)")
    private BigDecimal largObra;

    /**
     * Largura da Obra s/suporte ou moldura (precisão 2 decimais)
     */
    @ApiModelProperty(value = "Largura da Obra s/suporte ou moldura (precisão 2 decimais)")
    private BigDecimal profObra;

    /**
     * Profundidade da Obra s/suporte ou moldura (precisão 2 decimais)
     */
    @Size(max = 6)
    @ApiModelProperty(value = "Profundidade da Obra s/suporte ou moldura (precisão 2 decimais)")
    private String diametrObra;

    /**
     * Diâmetro da Obra s/suporte ou moldura
     */
    @ApiModelProperty(value = "Diâmetro da Obra s/suporte ou moldura")
    private BigDecimal alturaMold;

    /**
     * Altura da Obra c/Moldura (precisão 2 decimais)
     */
    @ApiModelProperty(value = "Altura da Obra c/Moldura (precisão 2 decimais)")
    private BigDecimal largMold;

    /**
     * Largura da Obra c/Moldura (precisão 2 decimais)
     */
    @ApiModelProperty(value = "Largura da Obra c/Moldura (precisão 2 decimais)")
    private BigDecimal profMold;

    /**
     * Profundidade da Obra c/Moldura (precisão 2 decimais)
     */
    @Size(max = 10)
    @ApiModelProperty(value = "Profundidade da Obra c/Moldura (precisão 2 decimais)")
    private String diametroMold;

    /**
     * Diâmetro da Obra s/suporte ou moldura
     */
    @Size(max = 150)
    @ApiModelProperty(value = "Diâmetro da Obra s/suporte ou moldura")
    private String dimensAdic;

    /**
     * Dimensões adicionais da obra
     */
    @Size(max = 10)
    @ApiModelProperty(value = "Dimensões adicionais da obra")
    private String pesObra;

    /**
     * Peso da Obra
     */
    @ApiModelProperty(value = "Peso da Obra")
    private Boolean atribuido;

    /**
     * Autoria é atribuída ou não
     */
    @Size(max = 10)
    @ApiModelProperty(value = "Autoria é atribuída ou não")
    private String nFoto;

    /**
     * Nome e ou número da foto correspondente a obra
     */
    @ApiModelProperty(value = "Nome e ou número da foto correspondente a obra")
    private Boolean conjunto;

    /**
     * Faz parte de um conjunto?
     */
    @Size(max = 100)
    @ApiModelProperty(value = "Faz parte de um conjunto?")
    private String conjTombo;

    /**
     * Nº Tombos que formam o  conjunto
     */
    @ApiModelProperty(value = "Nº Tombos que formam o  conjunto")
    private BigDecimal valorSeguro;

    /**
     * Valor do Seguro (precisão 4 decimais)
     */
    @ApiModelProperty(value = "Valor do Seguro (precisão 4 decimais)")
    private BigDecimal valorSeguroReal;

    /**
     * Valor do Seguro convertido em Reais (precisão 4 decimais)
     */
    @ApiModelProperty(value = "Valor do Seguro convertido em Reais (precisão 4 decimais)")
    private LocalDate dataconversao;

    /**
     * Data da conversão da moeda
     */
    @ApiModelProperty(value = "Data da conversão da moeda")
    private LocalDate dataAlterApolice;

    /**
     * Data que a obra foi Incluida/Excluida na Apólice
     */
    @Size(max = 100)
    @ApiModelProperty(value = "Data que a obra foi Incluida/Excluida na Apólice")
    private String localAtual;

    /**
     * Localização atual da obra - relacionada com QryLocalização
     */
    @Size(max = 200)
    @ApiModelProperty(value = "Localização atual da obra - relacionada com QryLocalização")
    private String localAtualNovo;

    /**
     * Localização atual da obra - relacionada com QryLocalizaçãoNOVA
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Localização atual da obra - relacionada com QryLocalizaçãoNOVA")
    private String codParametro;

    /**
     * Categorias da tblParametros para associarmos com a obra
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Categorias da tblParametros para associarmos com a obra")
    private String obs;

    /**
     * Observações gerais da obra
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Observações gerais da obra")
    private String tiragem;

    /**
     * Tiragem da gravura
     */
    @Size(max = 200)
    @ApiModelProperty(value = "Tiragem da gravura")
    private String serie;

    /**
     * Título da Série da gravura
     */
    @ApiModelProperty(value = "Título da Série da gravura")
    private Integer selo;

    /**
     * Classificação da obra quanto ao uso, designado pelos pesquisadores
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Classificação da obra quanto ao uso, designado pelos pesquisadores")
    private String tomboAnterio;

    /**
     * Número da antiga catalogação
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Número da antiga catalogação")
    private String verbete;

    /**
     * Dados específicos sobre a obra
     */
    @ApiModelProperty(value = "Dados específicos sobre a obra")
    private Boolean livro;

    /**
     * Selecionar caso a obra esteja participando da última publicação da coleção
     */
    @ApiModelProperty(value = "Selecionar caso a obra esteja participando da última publicação da coleção")
    private Boolean imagemAlta;

    /**
     * Selecionar caso a obra possua imagem digital em alta resolução
     */
    @ApiModelProperty(value = "Selecionar caso a obra possua imagem digital em alta resolução")
    private Boolean apabex;

    /**
     * Selecione caso a obra esteja incluída no projeto da Depuração da Coleção, com destino à APABEX
     */
    @ApiModelProperty(value = "Selecione caso a obra esteja incluída no projeto da Depuração da Coleção, com destino à APABEX")
    private Boolean bunkyo;

    /**
     * Selecione caso a obra esteja incluída no projeto da Depuração da Coleção, com destino ao BUNKYO
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Selecione caso a obra esteja incluída no projeto da Depuração da Coleção, com destino ao BUNKYO")
    private String faseDepuracao;

    /**
     * Indique em qual fase a obra será destinada à Apabex ou ao Bunkyo
     */
    @ApiModelProperty(value = "Indique em qual fase a obra será destinada à Apabex ou ao Bunkyo")
    private Boolean paraAvaliacao;

    /**
     * Selecionar caso a obra necessite ser encaminhada para avaliação de mercado
     */
    @ApiModelProperty(value = "Selecionar caso a obra necessite ser encaminhada para avaliação de mercado")
    private Boolean paraRestauracao;

    /**
     * Selecionar caso a obra necessite ser encaminhada para restauração
     */
    @ApiModelProperty(value = "Selecionar caso a obra necessite ser encaminhada para restauração")
    private Boolean paraMoldura;

    /**
     * Selecionar caso a obra necessite ser encaminhada para confecção de moldura
     */
    @ApiModelProperty(value = "Selecionar caso a obra necessite ser encaminhada para confecção de moldura")
    private Boolean paraLegenda;

    /**
     * Selecionar caso a obra necessite ser encaminhada para confecção de legenda
     */
    @ApiModelProperty(value = "Selecionar caso a obra necessite ser encaminhada para confecção de legenda")
    private Boolean tempField;

    /**
     * Selecionar caso a obra necessite ser encaminhada para foto em alta
     */
    @ApiModelProperty(value = "Selecionar caso a obra necessite ser encaminhada para foto em alta")
    private Boolean selecaoListaAvulsa;

    /**
     * Selecionar para incluir a obra em uma eventual seleção avulsa
     */
    @ApiModelProperty(value = "Selecionar para incluir a obra em uma eventual seleção avulsa")
    private Boolean dominioPubl;

    /**
     * É de domínio público
     */
    @ApiModelProperty(value = "É de domínio público")
    private LocalDate dtVencFoto;

    /**
     * Data de vencimento de uso de imagem do Fotografo
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Data de vencimento de uso de imagem do Fotografo")
    private String obsUsoFoto;

    /**
     * Observações de uso de imagem do Fotografo
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Observações de uso de imagem do Fotografo")
    private String localFotoAlta;

    /**
     * Localização da FotoAlta
     */
    @ApiModelProperty(value = "Localização da FotoAlta")
    private LocalDate dataInclusao;

    /**
     * Data que a obra foi inserida
     */
    @ApiModelProperty(value = "Data que a obra foi inserida")
    private LocalDate dataExclusao;

    /**
     * Data que a obra foi excluida
     */
    @ApiModelProperty(value = "Data que a obra foi excluida")
    private Integer bookX;

    /**
     * Posicao do Eixo X no book
     */
    @ApiModelProperty(value = "Posicao do Eixo X no book")
    private Integer bookY;

    /**
     * Posicao do Eixo Y no book
     */
    @ApiModelProperty(value = "Posicao do Eixo Y no book")
    private Integer genDescricao;

    private Integer genField1;

    private Boolean paraFotografia;

    /**
     * Selecionar caso a obra necessite ser encaminhada para foto em alta
     */
    @ApiModelProperty(value = "Selecionar caso a obra necessite ser encaminhada para foto em alta")
    private Integer genMarcaInscrObra;

    @Size(max = 64000)
    private String palavrasChave;

    /**
     * Palavras-chave relacionadas à obra, para pesquisa mais detalhada
     */
    @ApiModelProperty(value = "Palavras-chave relacionadas à obra, para pesquisa mais detalhada")
    private Integer genMarcaInscrSuporte;

    private Integer genObs;

    private Integer genVerbete;

    private Set<DadoDocumentalDTO> dadoDocumentals = new HashSet<>();

    private ArtistaDTO artista;

    private CategoriaDTO categoria;

    private TecnicaDTO tecnica;

    private NivelDTO nivel;

    private DataDTO data;

    private EmpresaDTO empresa;

    private MoedaDTO moeda;

    private SeguroDTO seguro;

    private ResponsavelDTO responsavel;

    private AcervoAtualDTO acervoatual;

    private ContatoDTO fotografo;

    private AndarMapaDTO andarMapa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTombo() {
        return tombo;
    }

    public void setTombo(String tombo) {
        this.tombo = tombo;
    }

    public String getMultiplo() {
        return multiplo;
    }

    public void setMultiplo(String multiplo) {
        this.multiplo = multiplo;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getOutrosTitulos() {
        return outrosTitulos;
    }

    public void setOutrosTitulos(String outrosTitulos) {
        this.outrosTitulos = outrosTitulos;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(Boolean assinatura) {
        this.assinatura = assinatura;
    }

    public String getLocalAssinatura() {
        return localAssinatura;
    }

    public void setLocalAssinatura(String localAssinatura) {
        this.localAssinatura = localAssinatura;
    }

    public String getMarcaInscrObra() {
        return marcaInscrObra;
    }

    public void setMarcaInscrObra(String marcaInscrObra) {
        this.marcaInscrObra = marcaInscrObra;
    }

    public String getMarcaInscrSuporte() {
        return marcaInscrSuporte;
    }

    public void setMarcaInscrSuporte(String marcaInscrSuporte) {
        this.marcaInscrSuporte = marcaInscrSuporte;
    }

    public Boolean getMedidasAprox() {
        return medidasAprox;
    }

    public void setMedidasAprox(Boolean medidasAprox) {
        this.medidasAprox = medidasAprox;
    }

    public BigDecimal getAlturaObra() {
        return alturaObra;
    }

    public void setAlturaObra(BigDecimal alturaObra) {
        this.alturaObra = alturaObra;
    }

    public BigDecimal getLargObra() {
        return largObra;
    }

    public void setLargObra(BigDecimal largObra) {
        this.largObra = largObra;
    }

    public BigDecimal getProfObra() {
        return profObra;
    }

    public void setProfObra(BigDecimal profObra) {
        this.profObra = profObra;
    }

    public String getDiametrObra() {
        return diametrObra;
    }

    public void setDiametrObra(String diametrObra) {
        this.diametrObra = diametrObra;
    }

    public BigDecimal getAlturaMold() {
        return alturaMold;
    }

    public void setAlturaMold(BigDecimal alturaMold) {
        this.alturaMold = alturaMold;
    }

    public BigDecimal getLargMold() {
        return largMold;
    }

    public void setLargMold(BigDecimal largMold) {
        this.largMold = largMold;
    }

    public BigDecimal getProfMold() {
        return profMold;
    }

    public void setProfMold(BigDecimal profMold) {
        this.profMold = profMold;
    }

    public String getDiametroMold() {
        return diametroMold;
    }

    public void setDiametroMold(String diametroMold) {
        this.diametroMold = diametroMold;
    }

    public String getDimensAdic() {
        return dimensAdic;
    }

    public void setDimensAdic(String dimensAdic) {
        this.dimensAdic = dimensAdic;
    }

    public String getPesObra() {
        return pesObra;
    }

    public void setPesObra(String pesObra) {
        this.pesObra = pesObra;
    }

    public Boolean getAtribuido() {
        return atribuido;
    }

    public void setAtribuido(Boolean atribuido) {
        this.atribuido = atribuido;
    }

    public String getnFoto() {
        return nFoto;
    }

    public void setnFoto(String nFoto) {
        this.nFoto = nFoto;
    }

    public Boolean getConjunto() {
        return conjunto;
    }

    public void setConjunto(Boolean conjunto) {
        this.conjunto = conjunto;
    }

    public String getConjTombo() {
        return conjTombo;
    }

    public void setConjTombo(String conjTombo) {
        this.conjTombo = conjTombo;
    }

    public BigDecimal getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(BigDecimal valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public BigDecimal getValorSeguroReal() {
        return valorSeguroReal;
    }

    public void setValorSeguroReal(BigDecimal valorSeguroReal) {
        this.valorSeguroReal = valorSeguroReal;
    }

    public LocalDate getDataconversao() {
        return dataconversao;
    }

    public void setDataconversao(LocalDate dataconversao) {
        this.dataconversao = dataconversao;
    }

    public LocalDate getDataAlterApolice() {
        return dataAlterApolice;
    }

    public void setDataAlterApolice(LocalDate dataAlterApolice) {
        this.dataAlterApolice = dataAlterApolice;
    }

    public String getLocalAtual() {
        return localAtual;
    }

    public void setLocalAtual(String localAtual) {
        this.localAtual = localAtual;
    }

    public String getLocalAtualNovo() {
        return localAtualNovo;
    }

    public void setLocalAtualNovo(String localAtualNovo) {
        this.localAtualNovo = localAtualNovo;
    }

    public String getCodParametro() {
        return codParametro;
    }

    public void setCodParametro(String codParametro) {
        this.codParametro = codParametro;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getTiragem() {
        return tiragem;
    }

    public void setTiragem(String tiragem) {
        this.tiragem = tiragem;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getSelo() {
        return selo;
    }

    public void setSelo(Integer selo) {
        this.selo = selo;
    }

    public String getTomboAnterio() {
        return tomboAnterio;
    }

    public void setTomboAnterio(String tomboAnterio) {
        this.tomboAnterio = tomboAnterio;
    }

    public String getVerbete() {
        return verbete;
    }

    public void setVerbete(String verbete) {
        this.verbete = verbete;
    }

    public Boolean getLivro() {
        return livro;
    }

    public void setLivro(Boolean livro) {
        this.livro = livro;
    }

    public Boolean getImagemAlta() {
        return imagemAlta;
    }

    public void setImagemAlta(Boolean imagemAlta) {
        this.imagemAlta = imagemAlta;
    }

    public Boolean getApabex() {
        return apabex;
    }

    public void setApabex(Boolean apabex) {
        this.apabex = apabex;
    }

    public Boolean getBunkyo() {
        return bunkyo;
    }

    public void setBunkyo(Boolean bunkyo) {
        this.bunkyo = bunkyo;
    }

    public String getFaseDepuracao() {
        return faseDepuracao;
    }

    public void setFaseDepuracao(String faseDepuracao) {
        this.faseDepuracao = faseDepuracao;
    }

    public Boolean getParaAvaliacao() {
        return paraAvaliacao;
    }

    public void setParaAvaliacao(Boolean paraAvaliacao) {
        this.paraAvaliacao = paraAvaliacao;
    }

    public Boolean getParaRestauracao() {
        return paraRestauracao;
    }

    public void setParaRestauracao(Boolean paraRestauracao) {
        this.paraRestauracao = paraRestauracao;
    }

    public Boolean getParaMoldura() {
        return paraMoldura;
    }

    public void setParaMoldura(Boolean paraMoldura) {
        this.paraMoldura = paraMoldura;
    }

    public Boolean getParaLegenda() {
        return paraLegenda;
    }

    public void setParaLegenda(Boolean paraLegenda) {
        this.paraLegenda = paraLegenda;
    }

    public Boolean getTempField() {
        return tempField;
    }

    public void setTempField(Boolean tempField) {
        this.tempField = tempField;
    }

    public Boolean getSelecaoListaAvulsa() {
        return selecaoListaAvulsa;
    }

    public void setSelecaoListaAvulsa(Boolean selecaoListaAvulsa) {
        this.selecaoListaAvulsa = selecaoListaAvulsa;
    }

    public Boolean getDominioPubl() {
        return dominioPubl;
    }

    public void setDominioPubl(Boolean dominioPubl) {
        this.dominioPubl = dominioPubl;
    }

    public LocalDate getDtVencFoto() {
        return dtVencFoto;
    }

    public void setDtVencFoto(LocalDate dtVencFoto) {
        this.dtVencFoto = dtVencFoto;
    }

    public String getObsUsoFoto() {
        return obsUsoFoto;
    }

    public void setObsUsoFoto(String obsUsoFoto) {
        this.obsUsoFoto = obsUsoFoto;
    }

    public String getLocalFotoAlta() {
        return localFotoAlta;
    }

    public void setLocalFotoAlta(String localFotoAlta) {
        this.localFotoAlta = localFotoAlta;
    }

    public LocalDate getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDate dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDate getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(LocalDate dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public Integer getBookX() {
        return bookX;
    }

    public void setBookX(Integer bookX) {
        this.bookX = bookX;
    }

    public Integer getBookY() {
        return bookY;
    }

    public void setBookY(Integer bookY) {
        this.bookY = bookY;
    }

    public Integer getGenDescricao() {
        return genDescricao;
    }

    public void setGenDescricao(Integer genDescricao) {
        this.genDescricao = genDescricao;
    }

    public Integer getGenField1() {
        return genField1;
    }

    public void setGenField1(Integer genField1) {
        this.genField1 = genField1;
    }

    public Boolean getParaFotografia() {
        return paraFotografia;
    }

    public void setParaFotografia(Boolean paraFotografia) {
        this.paraFotografia = paraFotografia;
    }

    public Integer getGenMarcaInscrObra() {
        return genMarcaInscrObra;
    }

    public void setGenMarcaInscrObra(Integer genMarcaInscrObra) {
        this.genMarcaInscrObra = genMarcaInscrObra;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public Integer getGenMarcaInscrSuporte() {
        return genMarcaInscrSuporte;
    }

    public void setGenMarcaInscrSuporte(Integer genMarcaInscrSuporte) {
        this.genMarcaInscrSuporte = genMarcaInscrSuporte;
    }

    public Integer getGenObs() {
        return genObs;
    }

    public void setGenObs(Integer genObs) {
        this.genObs = genObs;
    }

    public Integer getGenVerbete() {
        return genVerbete;
    }

    public void setGenVerbete(Integer genVerbete) {
        this.genVerbete = genVerbete;
    }

    public Set<DadoDocumentalDTO> getDadoDocumentals() {
        return dadoDocumentals;
    }

    public void setDadoDocumentals(Set<DadoDocumentalDTO> dadoDocumentals) {
        this.dadoDocumentals = dadoDocumentals;
    }

    public ArtistaDTO getArtista() {
        return artista;
    }

    public void setArtista(ArtistaDTO artista) {
        this.artista = artista;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public TecnicaDTO getTecnica() {
        return tecnica;
    }

    public void setTecnica(TecnicaDTO tecnica) {
        this.tecnica = tecnica;
    }

    public NivelDTO getNivel() {
        return nivel;
    }

    public void setNivel(NivelDTO nivel) {
        this.nivel = nivel;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public MoedaDTO getMoeda() {
        return moeda;
    }

    public void setMoeda(MoedaDTO moeda) {
        this.moeda = moeda;
    }

    public SeguroDTO getSeguro() {
        return seguro;
    }

    public void setSeguro(SeguroDTO seguro) {
        this.seguro = seguro;
    }

    public ResponsavelDTO getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelDTO responsavel) {
        this.responsavel = responsavel;
    }

    public AcervoAtualDTO getAcervoatual() {
        return acervoatual;
    }

    public void setAcervoatual(AcervoAtualDTO acervoatual) {
        this.acervoatual = acervoatual;
    }

    public ContatoDTO getFotografo() {
        return fotografo;
    }

    public void setFotografo(ContatoDTO fotografo) {
        this.fotografo = fotografo;
    }

    public AndarMapaDTO getAndarMapa() {
        return andarMapa;
    }

    public void setAndarMapa(AndarMapaDTO andarMapa) {
        this.andarMapa = andarMapa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObraDTO)) {
            return false;
        }

        ObraDTO obraDTO = (ObraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, obraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObraDTO{" +
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
            ", dadoDocumentals=" + getDadoDocumentals() +
            ", artista=" + getArtista() +
            ", categoria=" + getCategoria() +
            ", tecnica=" + getTecnica() +
            ", nivel=" + getNivel() +
            ", data=" + getData() +
            ", empresa=" + getEmpresa() +
            ", moeda=" + getMoeda() +
            ", seguro=" + getSeguro() +
            ", responsavel=" + getResponsavel() +
            ", acervoatual=" + getAcervoatual() +
            ", fotografo=" + getFotografo() +
            ", andarMapa=" + getAndarMapa() +
            "}";
    }
}
