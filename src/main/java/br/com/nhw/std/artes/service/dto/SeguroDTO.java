package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Seguro} entity.
 */
@ApiModel(description = "Informações de seguro das obras")
public class SeguroDTO implements Serializable {

    private Long id;

    @Size(max = 200)
    private String apolice;

    @Size(max = 30)
    private String obsSeguro;

    @Size(max = 255)
    private String contratoProposta;

    @Size(max = 50)
    private String ciaSeguradora;

    @Size(max = 255)
    private String cnpjSeguradora;

    @Size(max = 255)
    private String susepSeguradora;

    @Size(max = 50)
    private String corretora;

    @Size(max = 255)
    private String cnpjCorretora;

    @Size(max = 255)
    private String susepCorretora;

    private LocalDate dataInicio;

    private LocalDate dataVenc;

    private Boolean inativo;

    @Size(max = 255)
    private String premio;

    private BigDecimal txConversao;

    private Integer genStatusSeguro;

    private LocalDate dataAtualSeguro;

    private ContatoDTO contatoSeg;

    private ContatoDTO contatoCor;

    private MoedaDTO moeda;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApolice() {
        return apolice;
    }

    public void setApolice(String apolice) {
        this.apolice = apolice;
    }

    public String getObsSeguro() {
        return obsSeguro;
    }

    public void setObsSeguro(String obsSeguro) {
        this.obsSeguro = obsSeguro;
    }

    public String getContratoProposta() {
        return contratoProposta;
    }

    public void setContratoProposta(String contratoProposta) {
        this.contratoProposta = contratoProposta;
    }

    public String getCiaSeguradora() {
        return ciaSeguradora;
    }

    public void setCiaSeguradora(String ciaSeguradora) {
        this.ciaSeguradora = ciaSeguradora;
    }

    public String getCnpjSeguradora() {
        return cnpjSeguradora;
    }

    public void setCnpjSeguradora(String cnpjSeguradora) {
        this.cnpjSeguradora = cnpjSeguradora;
    }

    public String getSusepSeguradora() {
        return susepSeguradora;
    }

    public void setSusepSeguradora(String susepSeguradora) {
        this.susepSeguradora = susepSeguradora;
    }

    public String getCorretora() {
        return corretora;
    }

    public void setCorretora(String corretora) {
        this.corretora = corretora;
    }

    public String getCnpjCorretora() {
        return cnpjCorretora;
    }

    public void setCnpjCorretora(String cnpjCorretora) {
        this.cnpjCorretora = cnpjCorretora;
    }

    public String getSusepCorretora() {
        return susepCorretora;
    }

    public void setSusepCorretora(String susepCorretora) {
        this.susepCorretora = susepCorretora;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataVenc() {
        return dataVenc;
    }

    public void setDataVenc(LocalDate dataVenc) {
        this.dataVenc = dataVenc;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public BigDecimal getTxConversao() {
        return txConversao;
    }

    public void setTxConversao(BigDecimal txConversao) {
        this.txConversao = txConversao;
    }

    public Integer getGenStatusSeguro() {
        return genStatusSeguro;
    }

    public void setGenStatusSeguro(Integer genStatusSeguro) {
        this.genStatusSeguro = genStatusSeguro;
    }

    public LocalDate getDataAtualSeguro() {
        return dataAtualSeguro;
    }

    public void setDataAtualSeguro(LocalDate dataAtualSeguro) {
        this.dataAtualSeguro = dataAtualSeguro;
    }

    public ContatoDTO getContatoSeg() {
        return contatoSeg;
    }

    public void setContatoSeg(ContatoDTO contatoSeg) {
        this.contatoSeg = contatoSeg;
    }

    public ContatoDTO getContatoCor() {
        return contatoCor;
    }

    public void setContatoCor(ContatoDTO contatoCor) {
        this.contatoCor = contatoCor;
    }

    public MoedaDTO getMoeda() {
        return moeda;
    }

    public void setMoeda(MoedaDTO moeda) {
        this.moeda = moeda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeguroDTO)) {
            return false;
        }

        SeguroDTO seguroDTO = (SeguroDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, seguroDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeguroDTO{" +
            "id=" + getId() +
            ", apolice='" + getApolice() + "'" +
            ", obsSeguro='" + getObsSeguro() + "'" +
            ", contratoProposta='" + getContratoProposta() + "'" +
            ", ciaSeguradora='" + getCiaSeguradora() + "'" +
            ", cnpjSeguradora='" + getCnpjSeguradora() + "'" +
            ", susepSeguradora='" + getSusepSeguradora() + "'" +
            ", corretora='" + getCorretora() + "'" +
            ", cnpjCorretora='" + getCnpjCorretora() + "'" +
            ", susepCorretora='" + getSusepCorretora() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataVenc='" + getDataVenc() + "'" +
            ", inativo='" + getInativo() + "'" +
            ", premio='" + getPremio() + "'" +
            ", txConversao=" + getTxConversao() +
            ", genStatusSeguro=" + getGenStatusSeguro() +
            ", dataAtualSeguro='" + getDataAtualSeguro() + "'" +
            ", contatoSeg=" + getContatoSeg() +
            ", contatoCor=" + getContatoCor() +
            ", moeda=" + getMoeda() +
            "}";
    }
}
