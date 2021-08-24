package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.FuncaoArtista} entity.
 */
@ApiModel(description = "Entidade: Funcao do Artista")
public class FuncaoArtistaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String funcaoArtista;

    /**
     * Funcao do artista
     */
    @ApiModelProperty(value = "Funcao do artista")
    private Boolean inativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuncaoArtista() {
        return funcaoArtista;
    }

    public void setFuncaoArtista(String funcaoArtista) {
        this.funcaoArtista = funcaoArtista;
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
        if (!(o instanceof FuncaoArtistaDTO)) {
            return false;
        }

        FuncaoArtistaDTO funcaoArtistaDTO = (FuncaoArtistaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, funcaoArtistaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncaoArtistaDTO{" +
            "id=" + getId() +
            ", funcaoArtista='" + getFuncaoArtista() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
