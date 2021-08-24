package br.com.nhw.std.artes.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link br.com.nhw.std.artes.domain.Obra} entity. This class is used
 * in {@link br.com.nhw.std.artes.web.rest.ObraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /obras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ObraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tombo;

    private StringFilter multiplo;

    private StringFilter numeroRegistro;

    private StringFilter outrosTitulos;

    private StringFilter tituloOriginal;

    private StringFilter origem;

    private StringFilter descricao;

    private BooleanFilter assinatura;

    private StringFilter localAssinatura;

    private StringFilter marcaInscrObra;

    private StringFilter marcaInscrSuporte;

    private BooleanFilter medidasAprox;

    private BigDecimalFilter alturaObra;

    private BigDecimalFilter largObra;

    private BigDecimalFilter profObra;

    private StringFilter diametrObra;

    private BigDecimalFilter alturaMold;

    private BigDecimalFilter largMold;

    private BigDecimalFilter profMold;

    private StringFilter diametroMold;

    private StringFilter dimensAdic;

    private StringFilter pesObra;

    private BooleanFilter atribuido;

    private StringFilter nFoto;

    private BooleanFilter conjunto;

    private StringFilter conjTombo;

    private BigDecimalFilter valorSeguro;

    private BigDecimalFilter valorSeguroReal;

    private LocalDateFilter dataconversao;

    private LocalDateFilter dataAlterApolice;

    private StringFilter localAtual;

    private StringFilter localAtualNovo;

    private StringFilter codParametro;

    private StringFilter obs;

    private StringFilter tiragem;

    private StringFilter serie;

    private IntegerFilter selo;

    private StringFilter tomboAnterio;

    private StringFilter verbete;

    private BooleanFilter livro;

    private BooleanFilter imagemAlta;

    private BooleanFilter apabex;

    private BooleanFilter bunkyo;

    private StringFilter faseDepuracao;

    private BooleanFilter paraAvaliacao;

    private BooleanFilter paraRestauracao;

    private BooleanFilter paraMoldura;

    private BooleanFilter paraLegenda;

    private BooleanFilter tempField;

    private BooleanFilter selecaoListaAvulsa;

    private BooleanFilter dominioPubl;

    private LocalDateFilter dtVencFoto;

    private StringFilter obsUsoFoto;

    private StringFilter localFotoAlta;

    private LocalDateFilter dataInclusao;

    private LocalDateFilter dataExclusao;

    private IntegerFilter bookX;

    private IntegerFilter bookY;

    private IntegerFilter genDescricao;

    private IntegerFilter genField1;

    private BooleanFilter paraFotografia;

    private IntegerFilter genMarcaInscrObra;

    private StringFilter palavrasChave;

    private IntegerFilter genMarcaInscrSuporte;

    private IntegerFilter genObs;

    private IntegerFilter genVerbete;

    private LongFilter dadoDocumentalId;

    private LongFilter artistaId;

    private LongFilter categoriaId;

    private LongFilter tecnicaId;

    private LongFilter nivelId;

    private LongFilter dataId;

    private LongFilter empresaId;

    private LongFilter moedaId;

    private LongFilter seguroId;

    private LongFilter responsavelId;

    private LongFilter acervoatualId;

    private LongFilter fotografoId;

    private LongFilter andarMapaId;

    public ObraCriteria() {}

    public ObraCriteria(ObraCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tombo = other.tombo == null ? null : other.tombo.copy();
        this.multiplo = other.multiplo == null ? null : other.multiplo.copy();
        this.numeroRegistro = other.numeroRegistro == null ? null : other.numeroRegistro.copy();
        this.outrosTitulos = other.outrosTitulos == null ? null : other.outrosTitulos.copy();
        this.tituloOriginal = other.tituloOriginal == null ? null : other.tituloOriginal.copy();
        this.origem = other.origem == null ? null : other.origem.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.assinatura = other.assinatura == null ? null : other.assinatura.copy();
        this.localAssinatura = other.localAssinatura == null ? null : other.localAssinatura.copy();
        this.marcaInscrObra = other.marcaInscrObra == null ? null : other.marcaInscrObra.copy();
        this.marcaInscrSuporte = other.marcaInscrSuporte == null ? null : other.marcaInscrSuporte.copy();
        this.medidasAprox = other.medidasAprox == null ? null : other.medidasAprox.copy();
        this.alturaObra = other.alturaObra == null ? null : other.alturaObra.copy();
        this.largObra = other.largObra == null ? null : other.largObra.copy();
        this.profObra = other.profObra == null ? null : other.profObra.copy();
        this.diametrObra = other.diametrObra == null ? null : other.diametrObra.copy();
        this.alturaMold = other.alturaMold == null ? null : other.alturaMold.copy();
        this.largMold = other.largMold == null ? null : other.largMold.copy();
        this.profMold = other.profMold == null ? null : other.profMold.copy();
        this.diametroMold = other.diametroMold == null ? null : other.diametroMold.copy();
        this.dimensAdic = other.dimensAdic == null ? null : other.dimensAdic.copy();
        this.pesObra = other.pesObra == null ? null : other.pesObra.copy();
        this.atribuido = other.atribuido == null ? null : other.atribuido.copy();
        this.nFoto = other.nFoto == null ? null : other.nFoto.copy();
        this.conjunto = other.conjunto == null ? null : other.conjunto.copy();
        this.conjTombo = other.conjTombo == null ? null : other.conjTombo.copy();
        this.valorSeguro = other.valorSeguro == null ? null : other.valorSeguro.copy();
        this.valorSeguroReal = other.valorSeguroReal == null ? null : other.valorSeguroReal.copy();
        this.dataconversao = other.dataconversao == null ? null : other.dataconversao.copy();
        this.dataAlterApolice = other.dataAlterApolice == null ? null : other.dataAlterApolice.copy();
        this.localAtual = other.localAtual == null ? null : other.localAtual.copy();
        this.localAtualNovo = other.localAtualNovo == null ? null : other.localAtualNovo.copy();
        this.codParametro = other.codParametro == null ? null : other.codParametro.copy();
        this.obs = other.obs == null ? null : other.obs.copy();
        this.tiragem = other.tiragem == null ? null : other.tiragem.copy();
        this.serie = other.serie == null ? null : other.serie.copy();
        this.selo = other.selo == null ? null : other.selo.copy();
        this.tomboAnterio = other.tomboAnterio == null ? null : other.tomboAnterio.copy();
        this.verbete = other.verbete == null ? null : other.verbete.copy();
        this.livro = other.livro == null ? null : other.livro.copy();
        this.imagemAlta = other.imagemAlta == null ? null : other.imagemAlta.copy();
        this.apabex = other.apabex == null ? null : other.apabex.copy();
        this.bunkyo = other.bunkyo == null ? null : other.bunkyo.copy();
        this.faseDepuracao = other.faseDepuracao == null ? null : other.faseDepuracao.copy();
        this.paraAvaliacao = other.paraAvaliacao == null ? null : other.paraAvaliacao.copy();
        this.paraRestauracao = other.paraRestauracao == null ? null : other.paraRestauracao.copy();
        this.paraMoldura = other.paraMoldura == null ? null : other.paraMoldura.copy();
        this.paraLegenda = other.paraLegenda == null ? null : other.paraLegenda.copy();
        this.tempField = other.tempField == null ? null : other.tempField.copy();
        this.selecaoListaAvulsa = other.selecaoListaAvulsa == null ? null : other.selecaoListaAvulsa.copy();
        this.dominioPubl = other.dominioPubl == null ? null : other.dominioPubl.copy();
        this.dtVencFoto = other.dtVencFoto == null ? null : other.dtVencFoto.copy();
        this.obsUsoFoto = other.obsUsoFoto == null ? null : other.obsUsoFoto.copy();
        this.localFotoAlta = other.localFotoAlta == null ? null : other.localFotoAlta.copy();
        this.dataInclusao = other.dataInclusao == null ? null : other.dataInclusao.copy();
        this.dataExclusao = other.dataExclusao == null ? null : other.dataExclusao.copy();
        this.bookX = other.bookX == null ? null : other.bookX.copy();
        this.bookY = other.bookY == null ? null : other.bookY.copy();
        this.genDescricao = other.genDescricao == null ? null : other.genDescricao.copy();
        this.genField1 = other.genField1 == null ? null : other.genField1.copy();
        this.paraFotografia = other.paraFotografia == null ? null : other.paraFotografia.copy();
        this.genMarcaInscrObra = other.genMarcaInscrObra == null ? null : other.genMarcaInscrObra.copy();
        this.palavrasChave = other.palavrasChave == null ? null : other.palavrasChave.copy();
        this.genMarcaInscrSuporte = other.genMarcaInscrSuporte == null ? null : other.genMarcaInscrSuporte.copy();
        this.genObs = other.genObs == null ? null : other.genObs.copy();
        this.genVerbete = other.genVerbete == null ? null : other.genVerbete.copy();
        this.dadoDocumentalId = other.dadoDocumentalId == null ? null : other.dadoDocumentalId.copy();
        this.artistaId = other.artistaId == null ? null : other.artistaId.copy();
        this.categoriaId = other.categoriaId == null ? null : other.categoriaId.copy();
        this.tecnicaId = other.tecnicaId == null ? null : other.tecnicaId.copy();
        this.nivelId = other.nivelId == null ? null : other.nivelId.copy();
        this.dataId = other.dataId == null ? null : other.dataId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.moedaId = other.moedaId == null ? null : other.moedaId.copy();
        this.seguroId = other.seguroId == null ? null : other.seguroId.copy();
        this.responsavelId = other.responsavelId == null ? null : other.responsavelId.copy();
        this.acervoatualId = other.acervoatualId == null ? null : other.acervoatualId.copy();
        this.fotografoId = other.fotografoId == null ? null : other.fotografoId.copy();
        this.andarMapaId = other.andarMapaId == null ? null : other.andarMapaId.copy();
    }

    @Override
    public ObraCriteria copy() {
        return new ObraCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTombo() {
        return tombo;
    }

    public StringFilter tombo() {
        if (tombo == null) {
            tombo = new StringFilter();
        }
        return tombo;
    }

    public void setTombo(StringFilter tombo) {
        this.tombo = tombo;
    }

    public StringFilter getMultiplo() {
        return multiplo;
    }

    public StringFilter multiplo() {
        if (multiplo == null) {
            multiplo = new StringFilter();
        }
        return multiplo;
    }

    public void setMultiplo(StringFilter multiplo) {
        this.multiplo = multiplo;
    }

    public StringFilter getNumeroRegistro() {
        return numeroRegistro;
    }

    public StringFilter numeroRegistro() {
        if (numeroRegistro == null) {
            numeroRegistro = new StringFilter();
        }
        return numeroRegistro;
    }

    public void setNumeroRegistro(StringFilter numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public StringFilter getOutrosTitulos() {
        return outrosTitulos;
    }

    public StringFilter outrosTitulos() {
        if (outrosTitulos == null) {
            outrosTitulos = new StringFilter();
        }
        return outrosTitulos;
    }

    public void setOutrosTitulos(StringFilter outrosTitulos) {
        this.outrosTitulos = outrosTitulos;
    }

    public StringFilter getTituloOriginal() {
        return tituloOriginal;
    }

    public StringFilter tituloOriginal() {
        if (tituloOriginal == null) {
            tituloOriginal = new StringFilter();
        }
        return tituloOriginal;
    }

    public void setTituloOriginal(StringFilter tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public StringFilter getOrigem() {
        return origem;
    }

    public StringFilter origem() {
        if (origem == null) {
            origem = new StringFilter();
        }
        return origem;
    }

    public void setOrigem(StringFilter origem) {
        this.origem = origem;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public BooleanFilter getAssinatura() {
        return assinatura;
    }

    public BooleanFilter assinatura() {
        if (assinatura == null) {
            assinatura = new BooleanFilter();
        }
        return assinatura;
    }

    public void setAssinatura(BooleanFilter assinatura) {
        this.assinatura = assinatura;
    }

    public StringFilter getLocalAssinatura() {
        return localAssinatura;
    }

    public StringFilter localAssinatura() {
        if (localAssinatura == null) {
            localAssinatura = new StringFilter();
        }
        return localAssinatura;
    }

    public void setLocalAssinatura(StringFilter localAssinatura) {
        this.localAssinatura = localAssinatura;
    }

    public StringFilter getMarcaInscrObra() {
        return marcaInscrObra;
    }

    public StringFilter marcaInscrObra() {
        if (marcaInscrObra == null) {
            marcaInscrObra = new StringFilter();
        }
        return marcaInscrObra;
    }

    public void setMarcaInscrObra(StringFilter marcaInscrObra) {
        this.marcaInscrObra = marcaInscrObra;
    }

    public StringFilter getMarcaInscrSuporte() {
        return marcaInscrSuporte;
    }

    public StringFilter marcaInscrSuporte() {
        if (marcaInscrSuporte == null) {
            marcaInscrSuporte = new StringFilter();
        }
        return marcaInscrSuporte;
    }

    public void setMarcaInscrSuporte(StringFilter marcaInscrSuporte) {
        this.marcaInscrSuporte = marcaInscrSuporte;
    }

    public BooleanFilter getMedidasAprox() {
        return medidasAprox;
    }

    public BooleanFilter medidasAprox() {
        if (medidasAprox == null) {
            medidasAprox = new BooleanFilter();
        }
        return medidasAprox;
    }

    public void setMedidasAprox(BooleanFilter medidasAprox) {
        this.medidasAprox = medidasAprox;
    }

    public BigDecimalFilter getAlturaObra() {
        return alturaObra;
    }

    public BigDecimalFilter alturaObra() {
        if (alturaObra == null) {
            alturaObra = new BigDecimalFilter();
        }
        return alturaObra;
    }

    public void setAlturaObra(BigDecimalFilter alturaObra) {
        this.alturaObra = alturaObra;
    }

    public BigDecimalFilter getLargObra() {
        return largObra;
    }

    public BigDecimalFilter largObra() {
        if (largObra == null) {
            largObra = new BigDecimalFilter();
        }
        return largObra;
    }

    public void setLargObra(BigDecimalFilter largObra) {
        this.largObra = largObra;
    }

    public BigDecimalFilter getProfObra() {
        return profObra;
    }

    public BigDecimalFilter profObra() {
        if (profObra == null) {
            profObra = new BigDecimalFilter();
        }
        return profObra;
    }

    public void setProfObra(BigDecimalFilter profObra) {
        this.profObra = profObra;
    }

    public StringFilter getDiametrObra() {
        return diametrObra;
    }

    public StringFilter diametrObra() {
        if (diametrObra == null) {
            diametrObra = new StringFilter();
        }
        return diametrObra;
    }

    public void setDiametrObra(StringFilter diametrObra) {
        this.diametrObra = diametrObra;
    }

    public BigDecimalFilter getAlturaMold() {
        return alturaMold;
    }

    public BigDecimalFilter alturaMold() {
        if (alturaMold == null) {
            alturaMold = new BigDecimalFilter();
        }
        return alturaMold;
    }

    public void setAlturaMold(BigDecimalFilter alturaMold) {
        this.alturaMold = alturaMold;
    }

    public BigDecimalFilter getLargMold() {
        return largMold;
    }

    public BigDecimalFilter largMold() {
        if (largMold == null) {
            largMold = new BigDecimalFilter();
        }
        return largMold;
    }

    public void setLargMold(BigDecimalFilter largMold) {
        this.largMold = largMold;
    }

    public BigDecimalFilter getProfMold() {
        return profMold;
    }

    public BigDecimalFilter profMold() {
        if (profMold == null) {
            profMold = new BigDecimalFilter();
        }
        return profMold;
    }

    public void setProfMold(BigDecimalFilter profMold) {
        this.profMold = profMold;
    }

    public StringFilter getDiametroMold() {
        return diametroMold;
    }

    public StringFilter diametroMold() {
        if (diametroMold == null) {
            diametroMold = new StringFilter();
        }
        return diametroMold;
    }

    public void setDiametroMold(StringFilter diametroMold) {
        this.diametroMold = diametroMold;
    }

    public StringFilter getDimensAdic() {
        return dimensAdic;
    }

    public StringFilter dimensAdic() {
        if (dimensAdic == null) {
            dimensAdic = new StringFilter();
        }
        return dimensAdic;
    }

    public void setDimensAdic(StringFilter dimensAdic) {
        this.dimensAdic = dimensAdic;
    }

    public StringFilter getPesObra() {
        return pesObra;
    }

    public StringFilter pesObra() {
        if (pesObra == null) {
            pesObra = new StringFilter();
        }
        return pesObra;
    }

    public void setPesObra(StringFilter pesObra) {
        this.pesObra = pesObra;
    }

    public BooleanFilter getAtribuido() {
        return atribuido;
    }

    public BooleanFilter atribuido() {
        if (atribuido == null) {
            atribuido = new BooleanFilter();
        }
        return atribuido;
    }

    public void setAtribuido(BooleanFilter atribuido) {
        this.atribuido = atribuido;
    }

    public StringFilter getnFoto() {
        return nFoto;
    }

    public StringFilter nFoto() {
        if (nFoto == null) {
            nFoto = new StringFilter();
        }
        return nFoto;
    }

    public void setnFoto(StringFilter nFoto) {
        this.nFoto = nFoto;
    }

    public BooleanFilter getConjunto() {
        return conjunto;
    }

    public BooleanFilter conjunto() {
        if (conjunto == null) {
            conjunto = new BooleanFilter();
        }
        return conjunto;
    }

    public void setConjunto(BooleanFilter conjunto) {
        this.conjunto = conjunto;
    }

    public StringFilter getConjTombo() {
        return conjTombo;
    }

    public StringFilter conjTombo() {
        if (conjTombo == null) {
            conjTombo = new StringFilter();
        }
        return conjTombo;
    }

    public void setConjTombo(StringFilter conjTombo) {
        this.conjTombo = conjTombo;
    }

    public BigDecimalFilter getValorSeguro() {
        return valorSeguro;
    }

    public BigDecimalFilter valorSeguro() {
        if (valorSeguro == null) {
            valorSeguro = new BigDecimalFilter();
        }
        return valorSeguro;
    }

    public void setValorSeguro(BigDecimalFilter valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public BigDecimalFilter getValorSeguroReal() {
        return valorSeguroReal;
    }

    public BigDecimalFilter valorSeguroReal() {
        if (valorSeguroReal == null) {
            valorSeguroReal = new BigDecimalFilter();
        }
        return valorSeguroReal;
    }

    public void setValorSeguroReal(BigDecimalFilter valorSeguroReal) {
        this.valorSeguroReal = valorSeguroReal;
    }

    public LocalDateFilter getDataconversao() {
        return dataconversao;
    }

    public LocalDateFilter dataconversao() {
        if (dataconversao == null) {
            dataconversao = new LocalDateFilter();
        }
        return dataconversao;
    }

    public void setDataconversao(LocalDateFilter dataconversao) {
        this.dataconversao = dataconversao;
    }

    public LocalDateFilter getDataAlterApolice() {
        return dataAlterApolice;
    }

    public LocalDateFilter dataAlterApolice() {
        if (dataAlterApolice == null) {
            dataAlterApolice = new LocalDateFilter();
        }
        return dataAlterApolice;
    }

    public void setDataAlterApolice(LocalDateFilter dataAlterApolice) {
        this.dataAlterApolice = dataAlterApolice;
    }

    public StringFilter getLocalAtual() {
        return localAtual;
    }

    public StringFilter localAtual() {
        if (localAtual == null) {
            localAtual = new StringFilter();
        }
        return localAtual;
    }

    public void setLocalAtual(StringFilter localAtual) {
        this.localAtual = localAtual;
    }

    public StringFilter getLocalAtualNovo() {
        return localAtualNovo;
    }

    public StringFilter localAtualNovo() {
        if (localAtualNovo == null) {
            localAtualNovo = new StringFilter();
        }
        return localAtualNovo;
    }

    public void setLocalAtualNovo(StringFilter localAtualNovo) {
        this.localAtualNovo = localAtualNovo;
    }

    public StringFilter getCodParametro() {
        return codParametro;
    }

    public StringFilter codParametro() {
        if (codParametro == null) {
            codParametro = new StringFilter();
        }
        return codParametro;
    }

    public void setCodParametro(StringFilter codParametro) {
        this.codParametro = codParametro;
    }

    public StringFilter getObs() {
        return obs;
    }

    public StringFilter obs() {
        if (obs == null) {
            obs = new StringFilter();
        }
        return obs;
    }

    public void setObs(StringFilter obs) {
        this.obs = obs;
    }

    public StringFilter getTiragem() {
        return tiragem;
    }

    public StringFilter tiragem() {
        if (tiragem == null) {
            tiragem = new StringFilter();
        }
        return tiragem;
    }

    public void setTiragem(StringFilter tiragem) {
        this.tiragem = tiragem;
    }

    public StringFilter getSerie() {
        return serie;
    }

    public StringFilter serie() {
        if (serie == null) {
            serie = new StringFilter();
        }
        return serie;
    }

    public void setSerie(StringFilter serie) {
        this.serie = serie;
    }

    public IntegerFilter getSelo() {
        return selo;
    }

    public IntegerFilter selo() {
        if (selo == null) {
            selo = new IntegerFilter();
        }
        return selo;
    }

    public void setSelo(IntegerFilter selo) {
        this.selo = selo;
    }

    public StringFilter getTomboAnterio() {
        return tomboAnterio;
    }

    public StringFilter tomboAnterio() {
        if (tomboAnterio == null) {
            tomboAnterio = new StringFilter();
        }
        return tomboAnterio;
    }

    public void setTomboAnterio(StringFilter tomboAnterio) {
        this.tomboAnterio = tomboAnterio;
    }

    public StringFilter getVerbete() {
        return verbete;
    }

    public StringFilter verbete() {
        if (verbete == null) {
            verbete = new StringFilter();
        }
        return verbete;
    }

    public void setVerbete(StringFilter verbete) {
        this.verbete = verbete;
    }

    public BooleanFilter getLivro() {
        return livro;
    }

    public BooleanFilter livro() {
        if (livro == null) {
            livro = new BooleanFilter();
        }
        return livro;
    }

    public void setLivro(BooleanFilter livro) {
        this.livro = livro;
    }

    public BooleanFilter getImagemAlta() {
        return imagemAlta;
    }

    public BooleanFilter imagemAlta() {
        if (imagemAlta == null) {
            imagemAlta = new BooleanFilter();
        }
        return imagemAlta;
    }

    public void setImagemAlta(BooleanFilter imagemAlta) {
        this.imagemAlta = imagemAlta;
    }

    public BooleanFilter getApabex() {
        return apabex;
    }

    public BooleanFilter apabex() {
        if (apabex == null) {
            apabex = new BooleanFilter();
        }
        return apabex;
    }

    public void setApabex(BooleanFilter apabex) {
        this.apabex = apabex;
    }

    public BooleanFilter getBunkyo() {
        return bunkyo;
    }

    public BooleanFilter bunkyo() {
        if (bunkyo == null) {
            bunkyo = new BooleanFilter();
        }
        return bunkyo;
    }

    public void setBunkyo(BooleanFilter bunkyo) {
        this.bunkyo = bunkyo;
    }

    public StringFilter getFaseDepuracao() {
        return faseDepuracao;
    }

    public StringFilter faseDepuracao() {
        if (faseDepuracao == null) {
            faseDepuracao = new StringFilter();
        }
        return faseDepuracao;
    }

    public void setFaseDepuracao(StringFilter faseDepuracao) {
        this.faseDepuracao = faseDepuracao;
    }

    public BooleanFilter getParaAvaliacao() {
        return paraAvaliacao;
    }

    public BooleanFilter paraAvaliacao() {
        if (paraAvaliacao == null) {
            paraAvaliacao = new BooleanFilter();
        }
        return paraAvaliacao;
    }

    public void setParaAvaliacao(BooleanFilter paraAvaliacao) {
        this.paraAvaliacao = paraAvaliacao;
    }

    public BooleanFilter getParaRestauracao() {
        return paraRestauracao;
    }

    public BooleanFilter paraRestauracao() {
        if (paraRestauracao == null) {
            paraRestauracao = new BooleanFilter();
        }
        return paraRestauracao;
    }

    public void setParaRestauracao(BooleanFilter paraRestauracao) {
        this.paraRestauracao = paraRestauracao;
    }

    public BooleanFilter getParaMoldura() {
        return paraMoldura;
    }

    public BooleanFilter paraMoldura() {
        if (paraMoldura == null) {
            paraMoldura = new BooleanFilter();
        }
        return paraMoldura;
    }

    public void setParaMoldura(BooleanFilter paraMoldura) {
        this.paraMoldura = paraMoldura;
    }

    public BooleanFilter getParaLegenda() {
        return paraLegenda;
    }

    public BooleanFilter paraLegenda() {
        if (paraLegenda == null) {
            paraLegenda = new BooleanFilter();
        }
        return paraLegenda;
    }

    public void setParaLegenda(BooleanFilter paraLegenda) {
        this.paraLegenda = paraLegenda;
    }

    public BooleanFilter getTempField() {
        return tempField;
    }

    public BooleanFilter tempField() {
        if (tempField == null) {
            tempField = new BooleanFilter();
        }
        return tempField;
    }

    public void setTempField(BooleanFilter tempField) {
        this.tempField = tempField;
    }

    public BooleanFilter getSelecaoListaAvulsa() {
        return selecaoListaAvulsa;
    }

    public BooleanFilter selecaoListaAvulsa() {
        if (selecaoListaAvulsa == null) {
            selecaoListaAvulsa = new BooleanFilter();
        }
        return selecaoListaAvulsa;
    }

    public void setSelecaoListaAvulsa(BooleanFilter selecaoListaAvulsa) {
        this.selecaoListaAvulsa = selecaoListaAvulsa;
    }

    public BooleanFilter getDominioPubl() {
        return dominioPubl;
    }

    public BooleanFilter dominioPubl() {
        if (dominioPubl == null) {
            dominioPubl = new BooleanFilter();
        }
        return dominioPubl;
    }

    public void setDominioPubl(BooleanFilter dominioPubl) {
        this.dominioPubl = dominioPubl;
    }

    public LocalDateFilter getDtVencFoto() {
        return dtVencFoto;
    }

    public LocalDateFilter dtVencFoto() {
        if (dtVencFoto == null) {
            dtVencFoto = new LocalDateFilter();
        }
        return dtVencFoto;
    }

    public void setDtVencFoto(LocalDateFilter dtVencFoto) {
        this.dtVencFoto = dtVencFoto;
    }

    public StringFilter getObsUsoFoto() {
        return obsUsoFoto;
    }

    public StringFilter obsUsoFoto() {
        if (obsUsoFoto == null) {
            obsUsoFoto = new StringFilter();
        }
        return obsUsoFoto;
    }

    public void setObsUsoFoto(StringFilter obsUsoFoto) {
        this.obsUsoFoto = obsUsoFoto;
    }

    public StringFilter getLocalFotoAlta() {
        return localFotoAlta;
    }

    public StringFilter localFotoAlta() {
        if (localFotoAlta == null) {
            localFotoAlta = new StringFilter();
        }
        return localFotoAlta;
    }

    public void setLocalFotoAlta(StringFilter localFotoAlta) {
        this.localFotoAlta = localFotoAlta;
    }

    public LocalDateFilter getDataInclusao() {
        return dataInclusao;
    }

    public LocalDateFilter dataInclusao() {
        if (dataInclusao == null) {
            dataInclusao = new LocalDateFilter();
        }
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateFilter dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDateFilter getDataExclusao() {
        return dataExclusao;
    }

    public LocalDateFilter dataExclusao() {
        if (dataExclusao == null) {
            dataExclusao = new LocalDateFilter();
        }
        return dataExclusao;
    }

    public void setDataExclusao(LocalDateFilter dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public IntegerFilter getBookX() {
        return bookX;
    }

    public IntegerFilter bookX() {
        if (bookX == null) {
            bookX = new IntegerFilter();
        }
        return bookX;
    }

    public void setBookX(IntegerFilter bookX) {
        this.bookX = bookX;
    }

    public IntegerFilter getBookY() {
        return bookY;
    }

    public IntegerFilter bookY() {
        if (bookY == null) {
            bookY = new IntegerFilter();
        }
        return bookY;
    }

    public void setBookY(IntegerFilter bookY) {
        this.bookY = bookY;
    }

    public IntegerFilter getGenDescricao() {
        return genDescricao;
    }

    public IntegerFilter genDescricao() {
        if (genDescricao == null) {
            genDescricao = new IntegerFilter();
        }
        return genDescricao;
    }

    public void setGenDescricao(IntegerFilter genDescricao) {
        this.genDescricao = genDescricao;
    }

    public IntegerFilter getGenField1() {
        return genField1;
    }

    public IntegerFilter genField1() {
        if (genField1 == null) {
            genField1 = new IntegerFilter();
        }
        return genField1;
    }

    public void setGenField1(IntegerFilter genField1) {
        this.genField1 = genField1;
    }

    public BooleanFilter getParaFotografia() {
        return paraFotografia;
    }

    public BooleanFilter paraFotografia() {
        if (paraFotografia == null) {
            paraFotografia = new BooleanFilter();
        }
        return paraFotografia;
    }

    public void setParaFotografia(BooleanFilter paraFotografia) {
        this.paraFotografia = paraFotografia;
    }

    public IntegerFilter getGenMarcaInscrObra() {
        return genMarcaInscrObra;
    }

    public IntegerFilter genMarcaInscrObra() {
        if (genMarcaInscrObra == null) {
            genMarcaInscrObra = new IntegerFilter();
        }
        return genMarcaInscrObra;
    }

    public void setGenMarcaInscrObra(IntegerFilter genMarcaInscrObra) {
        this.genMarcaInscrObra = genMarcaInscrObra;
    }

    public StringFilter getPalavrasChave() {
        return palavrasChave;
    }

    public StringFilter palavrasChave() {
        if (palavrasChave == null) {
            palavrasChave = new StringFilter();
        }
        return palavrasChave;
    }

    public void setPalavrasChave(StringFilter palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public IntegerFilter getGenMarcaInscrSuporte() {
        return genMarcaInscrSuporte;
    }

    public IntegerFilter genMarcaInscrSuporte() {
        if (genMarcaInscrSuporte == null) {
            genMarcaInscrSuporte = new IntegerFilter();
        }
        return genMarcaInscrSuporte;
    }

    public void setGenMarcaInscrSuporte(IntegerFilter genMarcaInscrSuporte) {
        this.genMarcaInscrSuporte = genMarcaInscrSuporte;
    }

    public IntegerFilter getGenObs() {
        return genObs;
    }

    public IntegerFilter genObs() {
        if (genObs == null) {
            genObs = new IntegerFilter();
        }
        return genObs;
    }

    public void setGenObs(IntegerFilter genObs) {
        this.genObs = genObs;
    }

    public IntegerFilter getGenVerbete() {
        return genVerbete;
    }

    public IntegerFilter genVerbete() {
        if (genVerbete == null) {
            genVerbete = new IntegerFilter();
        }
        return genVerbete;
    }

    public void setGenVerbete(IntegerFilter genVerbete) {
        this.genVerbete = genVerbete;
    }

    public LongFilter getDadoDocumentalId() {
        return dadoDocumentalId;
    }

    public LongFilter dadoDocumentalId() {
        if (dadoDocumentalId == null) {
            dadoDocumentalId = new LongFilter();
        }
        return dadoDocumentalId;
    }

    public void setDadoDocumentalId(LongFilter dadoDocumentalId) {
        this.dadoDocumentalId = dadoDocumentalId;
    }

    public LongFilter getArtistaId() {
        return artistaId;
    }

    public LongFilter artistaId() {
        if (artistaId == null) {
            artistaId = new LongFilter();
        }
        return artistaId;
    }

    public void setArtistaId(LongFilter artistaId) {
        this.artistaId = artistaId;
    }

    public LongFilter getCategoriaId() {
        return categoriaId;
    }

    public LongFilter categoriaId() {
        if (categoriaId == null) {
            categoriaId = new LongFilter();
        }
        return categoriaId;
    }

    public void setCategoriaId(LongFilter categoriaId) {
        this.categoriaId = categoriaId;
    }

    public LongFilter getTecnicaId() {
        return tecnicaId;
    }

    public LongFilter tecnicaId() {
        if (tecnicaId == null) {
            tecnicaId = new LongFilter();
        }
        return tecnicaId;
    }

    public void setTecnicaId(LongFilter tecnicaId) {
        this.tecnicaId = tecnicaId;
    }

    public LongFilter getNivelId() {
        return nivelId;
    }

    public LongFilter nivelId() {
        if (nivelId == null) {
            nivelId = new LongFilter();
        }
        return nivelId;
    }

    public void setNivelId(LongFilter nivelId) {
        this.nivelId = nivelId;
    }

    public LongFilter getDataId() {
        return dataId;
    }

    public LongFilter dataId() {
        if (dataId == null) {
            dataId = new LongFilter();
        }
        return dataId;
    }

    public void setDataId(LongFilter dataId) {
        this.dataId = dataId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public LongFilter empresaId() {
        if (empresaId == null) {
            empresaId = new LongFilter();
        }
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public LongFilter getMoedaId() {
        return moedaId;
    }

    public LongFilter moedaId() {
        if (moedaId == null) {
            moedaId = new LongFilter();
        }
        return moedaId;
    }

    public void setMoedaId(LongFilter moedaId) {
        this.moedaId = moedaId;
    }

    public LongFilter getSeguroId() {
        return seguroId;
    }

    public LongFilter seguroId() {
        if (seguroId == null) {
            seguroId = new LongFilter();
        }
        return seguroId;
    }

    public void setSeguroId(LongFilter seguroId) {
        this.seguroId = seguroId;
    }

    public LongFilter getResponsavelId() {
        return responsavelId;
    }

    public LongFilter responsavelId() {
        if (responsavelId == null) {
            responsavelId = new LongFilter();
        }
        return responsavelId;
    }

    public void setResponsavelId(LongFilter responsavelId) {
        this.responsavelId = responsavelId;
    }

    public LongFilter getAcervoatualId() {
        return acervoatualId;
    }

    public LongFilter acervoatualId() {
        if (acervoatualId == null) {
            acervoatualId = new LongFilter();
        }
        return acervoatualId;
    }

    public void setAcervoatualId(LongFilter acervoatualId) {
        this.acervoatualId = acervoatualId;
    }

    public LongFilter getFotografoId() {
        return fotografoId;
    }

    public LongFilter fotografoId() {
        if (fotografoId == null) {
            fotografoId = new LongFilter();
        }
        return fotografoId;
    }

    public void setFotografoId(LongFilter fotografoId) {
        this.fotografoId = fotografoId;
    }

    public LongFilter getAndarMapaId() {
        return andarMapaId;
    }

    public LongFilter andarMapaId() {
        if (andarMapaId == null) {
            andarMapaId = new LongFilter();
        }
        return andarMapaId;
    }

    public void setAndarMapaId(LongFilter andarMapaId) {
        this.andarMapaId = andarMapaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ObraCriteria that = (ObraCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tombo, that.tombo) &&
            Objects.equals(multiplo, that.multiplo) &&
            Objects.equals(numeroRegistro, that.numeroRegistro) &&
            Objects.equals(outrosTitulos, that.outrosTitulos) &&
            Objects.equals(tituloOriginal, that.tituloOriginal) &&
            Objects.equals(origem, that.origem) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(assinatura, that.assinatura) &&
            Objects.equals(localAssinatura, that.localAssinatura) &&
            Objects.equals(marcaInscrObra, that.marcaInscrObra) &&
            Objects.equals(marcaInscrSuporte, that.marcaInscrSuporte) &&
            Objects.equals(medidasAprox, that.medidasAprox) &&
            Objects.equals(alturaObra, that.alturaObra) &&
            Objects.equals(largObra, that.largObra) &&
            Objects.equals(profObra, that.profObra) &&
            Objects.equals(diametrObra, that.diametrObra) &&
            Objects.equals(alturaMold, that.alturaMold) &&
            Objects.equals(largMold, that.largMold) &&
            Objects.equals(profMold, that.profMold) &&
            Objects.equals(diametroMold, that.diametroMold) &&
            Objects.equals(dimensAdic, that.dimensAdic) &&
            Objects.equals(pesObra, that.pesObra) &&
            Objects.equals(atribuido, that.atribuido) &&
            Objects.equals(nFoto, that.nFoto) &&
            Objects.equals(conjunto, that.conjunto) &&
            Objects.equals(conjTombo, that.conjTombo) &&
            Objects.equals(valorSeguro, that.valorSeguro) &&
            Objects.equals(valorSeguroReal, that.valorSeguroReal) &&
            Objects.equals(dataconversao, that.dataconversao) &&
            Objects.equals(dataAlterApolice, that.dataAlterApolice) &&
            Objects.equals(localAtual, that.localAtual) &&
            Objects.equals(localAtualNovo, that.localAtualNovo) &&
            Objects.equals(codParametro, that.codParametro) &&
            Objects.equals(obs, that.obs) &&
            Objects.equals(tiragem, that.tiragem) &&
            Objects.equals(serie, that.serie) &&
            Objects.equals(selo, that.selo) &&
            Objects.equals(tomboAnterio, that.tomboAnterio) &&
            Objects.equals(verbete, that.verbete) &&
            Objects.equals(livro, that.livro) &&
            Objects.equals(imagemAlta, that.imagemAlta) &&
            Objects.equals(apabex, that.apabex) &&
            Objects.equals(bunkyo, that.bunkyo) &&
            Objects.equals(faseDepuracao, that.faseDepuracao) &&
            Objects.equals(paraAvaliacao, that.paraAvaliacao) &&
            Objects.equals(paraRestauracao, that.paraRestauracao) &&
            Objects.equals(paraMoldura, that.paraMoldura) &&
            Objects.equals(paraLegenda, that.paraLegenda) &&
            Objects.equals(tempField, that.tempField) &&
            Objects.equals(selecaoListaAvulsa, that.selecaoListaAvulsa) &&
            Objects.equals(dominioPubl, that.dominioPubl) &&
            Objects.equals(dtVencFoto, that.dtVencFoto) &&
            Objects.equals(obsUsoFoto, that.obsUsoFoto) &&
            Objects.equals(localFotoAlta, that.localFotoAlta) &&
            Objects.equals(dataInclusao, that.dataInclusao) &&
            Objects.equals(dataExclusao, that.dataExclusao) &&
            Objects.equals(bookX, that.bookX) &&
            Objects.equals(bookY, that.bookY) &&
            Objects.equals(genDescricao, that.genDescricao) &&
            Objects.equals(genField1, that.genField1) &&
            Objects.equals(paraFotografia, that.paraFotografia) &&
            Objects.equals(genMarcaInscrObra, that.genMarcaInscrObra) &&
            Objects.equals(palavrasChave, that.palavrasChave) &&
            Objects.equals(genMarcaInscrSuporte, that.genMarcaInscrSuporte) &&
            Objects.equals(genObs, that.genObs) &&
            Objects.equals(genVerbete, that.genVerbete) &&
            Objects.equals(dadoDocumentalId, that.dadoDocumentalId) &&
            Objects.equals(artistaId, that.artistaId) &&
            Objects.equals(categoriaId, that.categoriaId) &&
            Objects.equals(tecnicaId, that.tecnicaId) &&
            Objects.equals(nivelId, that.nivelId) &&
            Objects.equals(dataId, that.dataId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(moedaId, that.moedaId) &&
            Objects.equals(seguroId, that.seguroId) &&
            Objects.equals(responsavelId, that.responsavelId) &&
            Objects.equals(acervoatualId, that.acervoatualId) &&
            Objects.equals(fotografoId, that.fotografoId) &&
            Objects.equals(andarMapaId, that.andarMapaId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tombo,
            multiplo,
            numeroRegistro,
            outrosTitulos,
            tituloOriginal,
            origem,
            descricao,
            assinatura,
            localAssinatura,
            marcaInscrObra,
            marcaInscrSuporte,
            medidasAprox,
            alturaObra,
            largObra,
            profObra,
            diametrObra,
            alturaMold,
            largMold,
            profMold,
            diametroMold,
            dimensAdic,
            pesObra,
            atribuido,
            nFoto,
            conjunto,
            conjTombo,
            valorSeguro,
            valorSeguroReal,
            dataconversao,
            dataAlterApolice,
            localAtual,
            localAtualNovo,
            codParametro,
            obs,
            tiragem,
            serie,
            selo,
            tomboAnterio,
            verbete,
            livro,
            imagemAlta,
            apabex,
            bunkyo,
            faseDepuracao,
            paraAvaliacao,
            paraRestauracao,
            paraMoldura,
            paraLegenda,
            tempField,
            selecaoListaAvulsa,
            dominioPubl,
            dtVencFoto,
            obsUsoFoto,
            localFotoAlta,
            dataInclusao,
            dataExclusao,
            bookX,
            bookY,
            genDescricao,
            genField1,
            paraFotografia,
            genMarcaInscrObra,
            palavrasChave,
            genMarcaInscrSuporte,
            genObs,
            genVerbete,
            dadoDocumentalId,
            artistaId,
            categoriaId,
            tecnicaId,
            nivelId,
            dataId,
            empresaId,
            moedaId,
            seguroId,
            responsavelId,
            acervoatualId,
            fotografoId,
            andarMapaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObraCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tombo != null ? "tombo=" + tombo + ", " : "") +
            (multiplo != null ? "multiplo=" + multiplo + ", " : "") +
            (numeroRegistro != null ? "numeroRegistro=" + numeroRegistro + ", " : "") +
            (outrosTitulos != null ? "outrosTitulos=" + outrosTitulos + ", " : "") +
            (tituloOriginal != null ? "tituloOriginal=" + tituloOriginal + ", " : "") +
            (origem != null ? "origem=" + origem + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (assinatura != null ? "assinatura=" + assinatura + ", " : "") +
            (localAssinatura != null ? "localAssinatura=" + localAssinatura + ", " : "") +
            (marcaInscrObra != null ? "marcaInscrObra=" + marcaInscrObra + ", " : "") +
            (marcaInscrSuporte != null ? "marcaInscrSuporte=" + marcaInscrSuporte + ", " : "") +
            (medidasAprox != null ? "medidasAprox=" + medidasAprox + ", " : "") +
            (alturaObra != null ? "alturaObra=" + alturaObra + ", " : "") +
            (largObra != null ? "largObra=" + largObra + ", " : "") +
            (profObra != null ? "profObra=" + profObra + ", " : "") +
            (diametrObra != null ? "diametrObra=" + diametrObra + ", " : "") +
            (alturaMold != null ? "alturaMold=" + alturaMold + ", " : "") +
            (largMold != null ? "largMold=" + largMold + ", " : "") +
            (profMold != null ? "profMold=" + profMold + ", " : "") +
            (diametroMold != null ? "diametroMold=" + diametroMold + ", " : "") +
            (dimensAdic != null ? "dimensAdic=" + dimensAdic + ", " : "") +
            (pesObra != null ? "pesObra=" + pesObra + ", " : "") +
            (atribuido != null ? "atribuido=" + atribuido + ", " : "") +
            (nFoto != null ? "nFoto=" + nFoto + ", " : "") +
            (conjunto != null ? "conjunto=" + conjunto + ", " : "") +
            (conjTombo != null ? "conjTombo=" + conjTombo + ", " : "") +
            (valorSeguro != null ? "valorSeguro=" + valorSeguro + ", " : "") +
            (valorSeguroReal != null ? "valorSeguroReal=" + valorSeguroReal + ", " : "") +
            (dataconversao != null ? "dataconversao=" + dataconversao + ", " : "") +
            (dataAlterApolice != null ? "dataAlterApolice=" + dataAlterApolice + ", " : "") +
            (localAtual != null ? "localAtual=" + localAtual + ", " : "") +
            (localAtualNovo != null ? "localAtualNovo=" + localAtualNovo + ", " : "") +
            (codParametro != null ? "codParametro=" + codParametro + ", " : "") +
            (obs != null ? "obs=" + obs + ", " : "") +
            (tiragem != null ? "tiragem=" + tiragem + ", " : "") +
            (serie != null ? "serie=" + serie + ", " : "") +
            (selo != null ? "selo=" + selo + ", " : "") +
            (tomboAnterio != null ? "tomboAnterio=" + tomboAnterio + ", " : "") +
            (verbete != null ? "verbete=" + verbete + ", " : "") +
            (livro != null ? "livro=" + livro + ", " : "") +
            (imagemAlta != null ? "imagemAlta=" + imagemAlta + ", " : "") +
            (apabex != null ? "apabex=" + apabex + ", " : "") +
            (bunkyo != null ? "bunkyo=" + bunkyo + ", " : "") +
            (faseDepuracao != null ? "faseDepuracao=" + faseDepuracao + ", " : "") +
            (paraAvaliacao != null ? "paraAvaliacao=" + paraAvaliacao + ", " : "") +
            (paraRestauracao != null ? "paraRestauracao=" + paraRestauracao + ", " : "") +
            (paraMoldura != null ? "paraMoldura=" + paraMoldura + ", " : "") +
            (paraLegenda != null ? "paraLegenda=" + paraLegenda + ", " : "") +
            (tempField != null ? "tempField=" + tempField + ", " : "") +
            (selecaoListaAvulsa != null ? "selecaoListaAvulsa=" + selecaoListaAvulsa + ", " : "") +
            (dominioPubl != null ? "dominioPubl=" + dominioPubl + ", " : "") +
            (dtVencFoto != null ? "dtVencFoto=" + dtVencFoto + ", " : "") +
            (obsUsoFoto != null ? "obsUsoFoto=" + obsUsoFoto + ", " : "") +
            (localFotoAlta != null ? "localFotoAlta=" + localFotoAlta + ", " : "") +
            (dataInclusao != null ? "dataInclusao=" + dataInclusao + ", " : "") +
            (dataExclusao != null ? "dataExclusao=" + dataExclusao + ", " : "") +
            (bookX != null ? "bookX=" + bookX + ", " : "") +
            (bookY != null ? "bookY=" + bookY + ", " : "") +
            (genDescricao != null ? "genDescricao=" + genDescricao + ", " : "") +
            (genField1 != null ? "genField1=" + genField1 + ", " : "") +
            (paraFotografia != null ? "paraFotografia=" + paraFotografia + ", " : "") +
            (genMarcaInscrObra != null ? "genMarcaInscrObra=" + genMarcaInscrObra + ", " : "") +
            (palavrasChave != null ? "palavrasChave=" + palavrasChave + ", " : "") +
            (genMarcaInscrSuporte != null ? "genMarcaInscrSuporte=" + genMarcaInscrSuporte + ", " : "") +
            (genObs != null ? "genObs=" + genObs + ", " : "") +
            (genVerbete != null ? "genVerbete=" + genVerbete + ", " : "") +
            (dadoDocumentalId != null ? "dadoDocumentalId=" + dadoDocumentalId + ", " : "") +
            (artistaId != null ? "artistaId=" + artistaId + ", " : "") +
            (categoriaId != null ? "categoriaId=" + categoriaId + ", " : "") +
            (tecnicaId != null ? "tecnicaId=" + tecnicaId + ", " : "") +
            (nivelId != null ? "nivelId=" + nivelId + ", " : "") +
            (dataId != null ? "dataId=" + dataId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (moedaId != null ? "moedaId=" + moedaId + ", " : "") +
            (seguroId != null ? "seguroId=" + seguroId + ", " : "") +
            (responsavelId != null ? "responsavelId=" + responsavelId + ", " : "") +
            (acervoatualId != null ? "acervoatualId=" + acervoatualId + ", " : "") +
            (fotografoId != null ? "fotografoId=" + fotografoId + ", " : "") +
            (andarMapaId != null ? "andarMapaId=" + andarMapaId + ", " : "") +
            "}";
    }
}
