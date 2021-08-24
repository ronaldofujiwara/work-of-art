package br.com.nhw.std.artes.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link br.com.nhw.std.artes.domain.Artista} entity. This class is used
 * in {@link br.com.nhw.std.artes.web.rest.ArtistaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /artistas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ArtistaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter dataNasc;

    private StringFilter dataFalec;

    private StringFilter biografia;

    private StringFilter verbete;

    private LocalDateFilter dataAtualBio;

    private LocalDateFilter dataAtualVerb;

    private BooleanFilter possuiBio;

    private BooleanFilter possuiVerb;

    private StringFilter fonteBio;

    private StringFilter obrasNoAcervo;

    private BooleanFilter inativo;

    private StringFilter agradecimentos;

    private StringFilter copyright;

    private StringFilter obsUso;

    private LongFilter obraId;

    private LongFilter contatoId;

    private LongFilter cidadeNascId;

    private LongFilter cidadeFalescId;

    private LongFilter respVerbeteId;

    private LongFilter funcaoArtistaId;

    public ArtistaCriteria() {}

    public ArtistaCriteria(ArtistaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.dataNasc = other.dataNasc == null ? null : other.dataNasc.copy();
        this.dataFalec = other.dataFalec == null ? null : other.dataFalec.copy();
        this.biografia = other.biografia == null ? null : other.biografia.copy();
        this.verbete = other.verbete == null ? null : other.verbete.copy();
        this.dataAtualBio = other.dataAtualBio == null ? null : other.dataAtualBio.copy();
        this.dataAtualVerb = other.dataAtualVerb == null ? null : other.dataAtualVerb.copy();
        this.possuiBio = other.possuiBio == null ? null : other.possuiBio.copy();
        this.possuiVerb = other.possuiVerb == null ? null : other.possuiVerb.copy();
        this.fonteBio = other.fonteBio == null ? null : other.fonteBio.copy();
        this.obrasNoAcervo = other.obrasNoAcervo == null ? null : other.obrasNoAcervo.copy();
        this.inativo = other.inativo == null ? null : other.inativo.copy();
        this.agradecimentos = other.agradecimentos == null ? null : other.agradecimentos.copy();
        this.copyright = other.copyright == null ? null : other.copyright.copy();
        this.obsUso = other.obsUso == null ? null : other.obsUso.copy();
        this.obraId = other.obraId == null ? null : other.obraId.copy();
        this.contatoId = other.contatoId == null ? null : other.contatoId.copy();
        this.cidadeNascId = other.cidadeNascId == null ? null : other.cidadeNascId.copy();
        this.cidadeFalescId = other.cidadeFalescId == null ? null : other.cidadeFalescId.copy();
        this.respVerbeteId = other.respVerbeteId == null ? null : other.respVerbeteId.copy();
        this.funcaoArtistaId = other.funcaoArtistaId == null ? null : other.funcaoArtistaId.copy();
    }

    @Override
    public ArtistaCriteria copy() {
        return new ArtistaCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getDataNasc() {
        return dataNasc;
    }

    public StringFilter dataNasc() {
        if (dataNasc == null) {
            dataNasc = new StringFilter();
        }
        return dataNasc;
    }

    public void setDataNasc(StringFilter dataNasc) {
        this.dataNasc = dataNasc;
    }

    public StringFilter getDataFalec() {
        return dataFalec;
    }

    public StringFilter dataFalec() {
        if (dataFalec == null) {
            dataFalec = new StringFilter();
        }
        return dataFalec;
    }

    public void setDataFalec(StringFilter dataFalec) {
        this.dataFalec = dataFalec;
    }

    public StringFilter getBiografia() {
        return biografia;
    }

    public StringFilter biografia() {
        if (biografia == null) {
            biografia = new StringFilter();
        }
        return biografia;
    }

    public void setBiografia(StringFilter biografia) {
        this.biografia = biografia;
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

    public LocalDateFilter getDataAtualBio() {
        return dataAtualBio;
    }

    public LocalDateFilter dataAtualBio() {
        if (dataAtualBio == null) {
            dataAtualBio = new LocalDateFilter();
        }
        return dataAtualBio;
    }

    public void setDataAtualBio(LocalDateFilter dataAtualBio) {
        this.dataAtualBio = dataAtualBio;
    }

    public LocalDateFilter getDataAtualVerb() {
        return dataAtualVerb;
    }

    public LocalDateFilter dataAtualVerb() {
        if (dataAtualVerb == null) {
            dataAtualVerb = new LocalDateFilter();
        }
        return dataAtualVerb;
    }

    public void setDataAtualVerb(LocalDateFilter dataAtualVerb) {
        this.dataAtualVerb = dataAtualVerb;
    }

    public BooleanFilter getPossuiBio() {
        return possuiBio;
    }

    public BooleanFilter possuiBio() {
        if (possuiBio == null) {
            possuiBio = new BooleanFilter();
        }
        return possuiBio;
    }

    public void setPossuiBio(BooleanFilter possuiBio) {
        this.possuiBio = possuiBio;
    }

    public BooleanFilter getPossuiVerb() {
        return possuiVerb;
    }

    public BooleanFilter possuiVerb() {
        if (possuiVerb == null) {
            possuiVerb = new BooleanFilter();
        }
        return possuiVerb;
    }

    public void setPossuiVerb(BooleanFilter possuiVerb) {
        this.possuiVerb = possuiVerb;
    }

    public StringFilter getFonteBio() {
        return fonteBio;
    }

    public StringFilter fonteBio() {
        if (fonteBio == null) {
            fonteBio = new StringFilter();
        }
        return fonteBio;
    }

    public void setFonteBio(StringFilter fonteBio) {
        this.fonteBio = fonteBio;
    }

    public StringFilter getObrasNoAcervo() {
        return obrasNoAcervo;
    }

    public StringFilter obrasNoAcervo() {
        if (obrasNoAcervo == null) {
            obrasNoAcervo = new StringFilter();
        }
        return obrasNoAcervo;
    }

    public void setObrasNoAcervo(StringFilter obrasNoAcervo) {
        this.obrasNoAcervo = obrasNoAcervo;
    }

    public BooleanFilter getInativo() {
        return inativo;
    }

    public BooleanFilter inativo() {
        if (inativo == null) {
            inativo = new BooleanFilter();
        }
        return inativo;
    }

    public void setInativo(BooleanFilter inativo) {
        this.inativo = inativo;
    }

    public StringFilter getAgradecimentos() {
        return agradecimentos;
    }

    public StringFilter agradecimentos() {
        if (agradecimentos == null) {
            agradecimentos = new StringFilter();
        }
        return agradecimentos;
    }

    public void setAgradecimentos(StringFilter agradecimentos) {
        this.agradecimentos = agradecimentos;
    }

    public StringFilter getCopyright() {
        return copyright;
    }

    public StringFilter copyright() {
        if (copyright == null) {
            copyright = new StringFilter();
        }
        return copyright;
    }

    public void setCopyright(StringFilter copyright) {
        this.copyright = copyright;
    }

    public StringFilter getObsUso() {
        return obsUso;
    }

    public StringFilter obsUso() {
        if (obsUso == null) {
            obsUso = new StringFilter();
        }
        return obsUso;
    }

    public void setObsUso(StringFilter obsUso) {
        this.obsUso = obsUso;
    }

    public LongFilter getObraId() {
        return obraId;
    }

    public LongFilter obraId() {
        if (obraId == null) {
            obraId = new LongFilter();
        }
        return obraId;
    }

    public void setObraId(LongFilter obraId) {
        this.obraId = obraId;
    }

    public LongFilter getContatoId() {
        return contatoId;
    }

    public LongFilter contatoId() {
        if (contatoId == null) {
            contatoId = new LongFilter();
        }
        return contatoId;
    }

    public void setContatoId(LongFilter contatoId) {
        this.contatoId = contatoId;
    }

    public LongFilter getCidadeNascId() {
        return cidadeNascId;
    }

    public LongFilter cidadeNascId() {
        if (cidadeNascId == null) {
            cidadeNascId = new LongFilter();
        }
        return cidadeNascId;
    }

    public void setCidadeNascId(LongFilter cidadeNascId) {
        this.cidadeNascId = cidadeNascId;
    }

    public LongFilter getCidadeFalescId() {
        return cidadeFalescId;
    }

    public LongFilter cidadeFalescId() {
        if (cidadeFalescId == null) {
            cidadeFalescId = new LongFilter();
        }
        return cidadeFalescId;
    }

    public void setCidadeFalescId(LongFilter cidadeFalescId) {
        this.cidadeFalescId = cidadeFalescId;
    }

    public LongFilter getRespVerbeteId() {
        return respVerbeteId;
    }

    public LongFilter respVerbeteId() {
        if (respVerbeteId == null) {
            respVerbeteId = new LongFilter();
        }
        return respVerbeteId;
    }

    public void setRespVerbeteId(LongFilter respVerbeteId) {
        this.respVerbeteId = respVerbeteId;
    }

    public LongFilter getFuncaoArtistaId() {
        return funcaoArtistaId;
    }

    public LongFilter funcaoArtistaId() {
        if (funcaoArtistaId == null) {
            funcaoArtistaId = new LongFilter();
        }
        return funcaoArtistaId;
    }

    public void setFuncaoArtistaId(LongFilter funcaoArtistaId) {
        this.funcaoArtistaId = funcaoArtistaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ArtistaCriteria that = (ArtistaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(dataNasc, that.dataNasc) &&
            Objects.equals(dataFalec, that.dataFalec) &&
            Objects.equals(biografia, that.biografia) &&
            Objects.equals(verbete, that.verbete) &&
            Objects.equals(dataAtualBio, that.dataAtualBio) &&
            Objects.equals(dataAtualVerb, that.dataAtualVerb) &&
            Objects.equals(possuiBio, that.possuiBio) &&
            Objects.equals(possuiVerb, that.possuiVerb) &&
            Objects.equals(fonteBio, that.fonteBio) &&
            Objects.equals(obrasNoAcervo, that.obrasNoAcervo) &&
            Objects.equals(inativo, that.inativo) &&
            Objects.equals(agradecimentos, that.agradecimentos) &&
            Objects.equals(copyright, that.copyright) &&
            Objects.equals(obsUso, that.obsUso) &&
            Objects.equals(obraId, that.obraId) &&
            Objects.equals(contatoId, that.contatoId) &&
            Objects.equals(cidadeNascId, that.cidadeNascId) &&
            Objects.equals(cidadeFalescId, that.cidadeFalescId) &&
            Objects.equals(respVerbeteId, that.respVerbeteId) &&
            Objects.equals(funcaoArtistaId, that.funcaoArtistaId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            dataNasc,
            dataFalec,
            biografia,
            verbete,
            dataAtualBio,
            dataAtualVerb,
            possuiBio,
            possuiVerb,
            fonteBio,
            obrasNoAcervo,
            inativo,
            agradecimentos,
            copyright,
            obsUso,
            obraId,
            contatoId,
            cidadeNascId,
            cidadeFalescId,
            respVerbeteId,
            funcaoArtistaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtistaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (dataNasc != null ? "dataNasc=" + dataNasc + ", " : "") +
            (dataFalec != null ? "dataFalec=" + dataFalec + ", " : "") +
            (biografia != null ? "biografia=" + biografia + ", " : "") +
            (verbete != null ? "verbete=" + verbete + ", " : "") +
            (dataAtualBio != null ? "dataAtualBio=" + dataAtualBio + ", " : "") +
            (dataAtualVerb != null ? "dataAtualVerb=" + dataAtualVerb + ", " : "") +
            (possuiBio != null ? "possuiBio=" + possuiBio + ", " : "") +
            (possuiVerb != null ? "possuiVerb=" + possuiVerb + ", " : "") +
            (fonteBio != null ? "fonteBio=" + fonteBio + ", " : "") +
            (obrasNoAcervo != null ? "obrasNoAcervo=" + obrasNoAcervo + ", " : "") +
            (inativo != null ? "inativo=" + inativo + ", " : "") +
            (agradecimentos != null ? "agradecimentos=" + agradecimentos + ", " : "") +
            (copyright != null ? "copyright=" + copyright + ", " : "") +
            (obsUso != null ? "obsUso=" + obsUso + ", " : "") +
            (obraId != null ? "obraId=" + obraId + ", " : "") +
            (contatoId != null ? "contatoId=" + contatoId + ", " : "") +
            (cidadeNascId != null ? "cidadeNascId=" + cidadeNascId + ", " : "") +
            (cidadeFalescId != null ? "cidadeFalescId=" + cidadeFalescId + ", " : "") +
            (respVerbeteId != null ? "respVerbeteId=" + respVerbeteId + ", " : "") +
            (funcaoArtistaId != null ? "funcaoArtistaId=" + funcaoArtistaId + ", " : "") +
            "}";
    }
}
