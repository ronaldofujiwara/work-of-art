package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Nivel} entity.
 */
@ApiModel(description = "Entidade: Nivel\nNivel de classificacao geral da obra")
public class NivelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 250)
    private String nivel;

    /**
     * Nivel de classificacao
     */
    @ApiModelProperty(value = "Nivel de classificacao")
    private Boolean inativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NivelDTO)) {
            return false;
        }

        NivelDTO nivelDTO = (NivelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nivelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NivelDTO{" +
            "id=" + getId() +
            ", nivel='" + getNivel() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
