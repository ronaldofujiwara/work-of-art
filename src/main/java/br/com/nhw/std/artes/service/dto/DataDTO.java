package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Data} entity.
 */
@ApiModel(description = "Entidade: Data\nData de execução da obra")
public class DataDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String data;

    /**
     * Data da obra
     */
    @ApiModelProperty(value = "Data da obra")
    private Boolean inativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
        if (!(o instanceof DataDTO)) {
            return false;
        }

        DataDTO dataDTO = (DataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
