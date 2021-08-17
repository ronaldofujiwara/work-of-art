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
 * Criteria class for the {@link br.com.nhw.std.artes.domain.AreaDepto} entity. This class is used
 * in {@link br.com.nhw.std.artes.web.rest.AreaDeptoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /area-deptos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AreaDeptoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter area;

    private BooleanFilter ativo;

    private LongFilter contatoId;

    public AreaDeptoCriteria() {}

    public AreaDeptoCriteria(AreaDeptoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
        this.contatoId = other.contatoId == null ? null : other.contatoId.copy();
    }

    @Override
    public AreaDeptoCriteria copy() {
        return new AreaDeptoCriteria(this);
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

    public StringFilter getArea() {
        return area;
    }

    public StringFilter area() {
        if (area == null) {
            area = new StringFilter();
        }
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
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
        final AreaDeptoCriteria that = (AreaDeptoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(area, that.area) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(contatoId, that.contatoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, area, ativo, contatoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDeptoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (ativo != null ? "ativo=" + ativo + ", " : "") +
            (contatoId != null ? "contatoId=" + contatoId + ", " : "") +
            "}";
    }
}
