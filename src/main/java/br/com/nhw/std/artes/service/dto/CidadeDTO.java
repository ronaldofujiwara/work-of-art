package br.com.nhw.std.artes.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Cidade} entity.
 */
public class CidadeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String cidade;

    @Size(max = 255)
    private String estado;

    @Size(max = 255)
    private String pais;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CidadeDTO)) {
            return false;
        }

        CidadeDTO cidadeDTO = (CidadeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cidadeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CidadeDTO{" +
            "id=" + getId() +
            ", cidade='" + getCidade() + "'" +
            ", estado='" + getEstado() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
