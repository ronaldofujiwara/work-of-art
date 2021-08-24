package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Cidade} entity.
 */
@ApiModel(description = "Cidade-UF-Pais - Obs: no Access -> tabela: Cid-UF-Pais1")
public class CidadeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String cidade;

    @Size(max = 255)
    private String estado;

    @Size(max = 255)
    private String pais;

    @Size(max = 255)
    private String cidadeUFPais;

    @Size(max = 255)
    private String estadoPais;

    /**
     * Registro inativo?
     */
    @ApiModelProperty(value = "Registro inativo?")
    private Boolean inativo;

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

    public String getCidadeUFPais() {
        return cidadeUFPais;
    }

    public void setCidadeUFPais(String cidadeUFPais) {
        this.cidadeUFPais = cidadeUFPais;
    }

    public String getEstadoPais() {
        return estadoPais;
    }

    public void setEstadoPais(String estadoPais) {
        this.estadoPais = estadoPais;
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
            ", cidadeUFPais='" + getCidadeUFPais() + "'" +
            ", estadoPais='" + getEstadoPais() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
