package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Moeda} entity.
 */
@ApiModel(description = "Entidade: Moeda\nCadastro de Tipos de Moeda")
public class MoedaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 15)
    private String tipoMoeda;

    /**
     * Moeda
     */
    @ApiModelProperty(value = "Moeda")
    private Boolean inativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoMoeda() {
        return tipoMoeda;
    }

    public void setTipoMoeda(String tipoMoeda) {
        this.tipoMoeda = tipoMoeda;
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
        if (!(o instanceof MoedaDTO)) {
            return false;
        }

        MoedaDTO moedaDTO = (MoedaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, moedaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MoedaDTO{" +
            "id=" + getId() +
            ", tipoMoeda='" + getTipoMoeda() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
