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
 * Criteria class for the {@link br.com.nhw.std.artes.domain.Ambiente} entity. This class is used
 * in {@link br.com.nhw.std.artes.web.rest.AmbienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ambientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AmbienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ambiente;

    private BooleanFilter ativo;

    public AmbienteCriteria() {}

    public AmbienteCriteria(AmbienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ambiente = other.ambiente == null ? null : other.ambiente.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
    }

    @Override
    public AmbienteCriteria copy() {
        return new AmbienteCriteria(this);
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

    public StringFilter getAmbiente() {
        return ambiente;
    }

    public StringFilter ambiente() {
        if (ambiente == null) {
            ambiente = new StringFilter();
        }
        return ambiente;
    }

    public void setAmbiente(StringFilter ambiente) {
        this.ambiente = ambiente;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public BooleanFilter ativo() {
        if (ativo == null) {
            ativo = new BooleanFilter();
        }
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AmbienteCriteria that = (AmbienteCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(ambiente, that.ambiente) && Objects.equals(ativo, that.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ambiente, ativo);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmbienteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (ambiente != null ? "ambiente=" + ambiente + ", " : "") +
            (ativo != null ? "ativo=" + ativo + ", " : "") +
            "}";
    }
}
