package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.AcervoAtual} entity.
 */
@ApiModel(description = "Acervo atual em uso")
public class AcervoAtualDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String acervoAtual;

    /**
     * Acervo Atual
     */
    @ApiModelProperty(value = "Acervo Atual")
    private Boolean inativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcervoAtual() {
        return acervoAtual;
    }

    public void setAcervoAtual(String acervoAtual) {
        this.acervoAtual = acervoAtual;
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
        if (!(o instanceof AcervoAtualDTO)) {
            return false;
        }

        AcervoAtualDTO acervoAtualDTO = (AcervoAtualDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, acervoAtualDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcervoAtualDTO{" +
            "id=" + getId() +
            ", acervoAtual='" + getAcervoAtual() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
