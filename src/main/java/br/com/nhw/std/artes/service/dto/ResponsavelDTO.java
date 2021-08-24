package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Responsavel} entity.
 */
@ApiModel(description = "Entidade: Responsavel\nColaborador responsavel pelo verbete/registro - Obs: no Access -> tabela Expomus")
public class ResponsavelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String nome;

    /**
     * Colaborador responsavel pelo verbete
     */
    @ApiModelProperty(value = "Colaborador responsavel pelo verbete")
    private Boolean inativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(o instanceof ResponsavelDTO)) {
            return false;
        }

        ResponsavelDTO responsavelDTO = (ResponsavelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, responsavelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsavelDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
