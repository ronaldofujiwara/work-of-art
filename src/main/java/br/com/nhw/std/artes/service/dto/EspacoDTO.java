package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Espaco} entity.
 */
@ApiModel(description = "Entidade: Espaco - Espacos expositivos")
public class EspacoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String espaco;

    private Boolean inativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspaco() {
        return espaco;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
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
        if (!(o instanceof EspacoDTO)) {
            return false;
        }

        EspacoDTO espacoDTO = (EspacoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, espacoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EspacoDTO{" +
            "id=" + getId() +
            ", espaco='" + getEspaco() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
