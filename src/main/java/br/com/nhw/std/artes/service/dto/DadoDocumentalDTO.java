package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.DadoDocumental} entity.
 */
@ApiModel(description = "Dados Documentais das Obras")
public class DadoDocumentalDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String data;

    @Size(max = 60)
    private String emissor;

    @Size(max = 30)
    private String receptor;

    @Size(max = 200)
    private String obs;

    @Size(max = 64000)
    private String transcricao;

    private Boolean finalizado;

    private Integer genTranscricao;

    private TipoDocumentoDTO tipoDocumento;

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

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getTranscricao() {
        return transcricao;
    }

    public void setTranscricao(String transcricao) {
        this.transcricao = transcricao;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    public Integer getGenTranscricao() {
        return genTranscricao;
    }

    public void setGenTranscricao(Integer genTranscricao) {
        this.genTranscricao = genTranscricao;
    }

    public TipoDocumentoDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DadoDocumentalDTO)) {
            return false;
        }

        DadoDocumentalDTO dadoDocumentalDTO = (DadoDocumentalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dadoDocumentalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DadoDocumentalDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", emissor='" + getEmissor() + "'" +
            ", receptor='" + getReceptor() + "'" +
            ", obs='" + getObs() + "'" +
            ", transcricao='" + getTranscricao() + "'" +
            ", finalizado='" + getFinalizado() + "'" +
            ", genTranscricao=" + getGenTranscricao() +
            ", tipoDocumento=" + getTipoDocumento() +
            "}";
    }
}
