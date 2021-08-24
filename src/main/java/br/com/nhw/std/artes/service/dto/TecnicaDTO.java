package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Tecnica} entity.
 */
@ApiModel(description = "Entidade: Tecnica\nRelação de técnicas ou materiais que constituem a obra")
public class TecnicaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String tecnica;

    /**
     * Técnicas ou materiais que constituem a obra
     */
    @ApiModelProperty(value = "Técnicas ou materiais que constituem a obra")
    private Boolean inativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
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
        if (!(o instanceof TecnicaDTO)) {
            return false;
        }

        TecnicaDTO tecnicaDTO = (TecnicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tecnicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TecnicaDTO{" +
            "id=" + getId() +
            ", tecnica='" + getTecnica() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
