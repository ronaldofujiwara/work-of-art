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

    private BooleanFilter inativo;

    private LongFilter contatoId;

    public AreaDeptoCriteria() {}

    public AreaDeptoCriteria(AreaDeptoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.inativo = other.inativo == null ? null : other.inativo.copy();
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
            Objects.equals(inativo, that.inativo) &&
            Objects.equals(contatoId, that.contatoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, area, inativo, contatoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDeptoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (inativo != null ? "inativo=" + inativo + ", " : "") +
            (contatoId != null ? "contatoId=" + contatoId + ", " : "") +
            "}";
    }
}
