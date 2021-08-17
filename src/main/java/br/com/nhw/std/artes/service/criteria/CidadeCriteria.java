package br.com.nhw.std.artes.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link br.com.nhw.std.artes.domain.Cidade} entity. This class is used
 * in {@link br.com.nhw.std.artes.web.rest.CidadeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cidades?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CidadeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cidade;

    private StringFilter estado;

    private StringFilter pais;

    private LongFilter contatoId;

    public CidadeCriteria() {}

    public CidadeCriteria(CidadeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cidade = other.cidade == null ? null : other.cidade.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.pais = other.pais == null ? null : other.pais.copy();
        this.contatoId = other.contatoId == null ? null : other.contatoId.copy();
    }

    @Override
    public CidadeCriteria copy() {
        return new CidadeCriteria(this);
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

    public StringFilter getCidade() {
        return cidade;
    }

    public StringFilter cidade() {
        if (cidade == null) {
            cidade = new StringFilter();
        }
        return cidade;
    }

    public void setCidade(StringFilter cidade) {
        this.cidade = cidade;
    }

    public StringFilter getEstado() {
        return estado;
    }

    public StringFilter estado() {
        if (estado == null) {
            estado = new StringFilter();
        }
        return estado;
    }

    public void setEstado(StringFilter estado) {
        this.estado = estado;
    }

    public StringFilter getPais() {
        return pais;
    }

    public StringFilter pais() {
        if (pais == null) {
            pais = new StringFilter();
        }
        return pais;
    }

    public void setPais(StringFilter pais) {
        this.pais = pais;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CidadeCriteria that = (CidadeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cidade, that.cidade) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(pais, that.pais) &&
            Objects.equals(contatoId, that.contatoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cidade, estado, pais, contatoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cidade != null ? "cidade=" + cidade + ", " : "") +
            (estado != null ? "estado=" + estado + ", " : "") +
            (pais != null ? "pais=" + pais + ", " : "") +
            (contatoId != null ? "contatoId=" + contatoId + ", " : "") +
            "}";
    }
}
