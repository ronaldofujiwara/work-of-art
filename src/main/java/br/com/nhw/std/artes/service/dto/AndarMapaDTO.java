package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.AndarMapa} entity.
 */
@ApiModel(description = "Mapa do Andar/Local/ambiente de exposicao da obra")
public class AndarMapaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String imagemMapa;

    private EspacoDTO espaco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagemMapa() {
        return imagemMapa;
    }

    public void setImagemMapa(String imagemMapa) {
        this.imagemMapa = imagemMapa;
    }

    public EspacoDTO getEspaco() {
        return espaco;
    }

    public void setEspaco(EspacoDTO espaco) {
        this.espaco = espaco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AndarMapaDTO)) {
            return false;
        }

        AndarMapaDTO andarMapaDTO = (AndarMapaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, andarMapaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AndarMapaDTO{" +
            "id=" + getId() +
            ", imagemMapa='" + getImagemMapa() + "'" +
            ", espaco=" + getEspaco() +
            "}";
    }
}
