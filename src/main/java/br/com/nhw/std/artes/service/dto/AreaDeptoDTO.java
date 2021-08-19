package br.com.nhw.std.artes.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.AreaDepto} entity.
 */
public class AreaDeptoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String area;

    private Boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaDeptoDTO)) {
            return false;
        }

        AreaDeptoDTO areaDeptoDTO = (AreaDeptoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, areaDeptoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDeptoDTO{" +
            "id=" + getId() +
            ", area='" + getArea() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
