package br.com.nhw.std.artes.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Ambiente} entity.
 */
public class AmbienteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String ambiente;

    private Boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
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
        if (!(o instanceof AmbienteDTO)) {
            return false;
        }

        AmbienteDTO ambienteDTO = (AmbienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ambienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmbienteDTO{" +
            "id=" + getId() +
            ", ambiente='" + getAmbiente() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
