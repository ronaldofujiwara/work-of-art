package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Contato} entity.
 */
@ApiModel(description = "Entidade: Contato\nDados cadastrais de contatos relacionados ao Acervo Cultural")
public class ContatoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 120)
    private String nomeComp;

    /**
     * Nome completo do contato
     */
    @Size(max = 150)
    @ApiModelProperty(value = "Nome completo do contato")
    private String empresa;

    /**
     * Nome Empresa e/ou Instituição
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Nome Empresa e/ou Instituição")
    private String funcao;

    /**
     * Cargo ocupado
     */
    @Size(max = 15)
    @ApiModelProperty(value = "Cargo ocupado")
    private String rg;

    /**
     * Documento de identidade
     */
    @Size(max = 15)
    @ApiModelProperty(value = "Documento de identidade")
    private String cpf;

    /**
     * CPF do contato
     */
    @Size(max = 200)
    @ApiModelProperty(value = "CPF do contato")
    private String infoContato;

    /**
     * Informacoes adicionais do contato
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Informacoes adicionais do contato")
    private String endRua;

    /**
     * Endereço (rua,avenida,etc)
     */
    @Size(max = 10)
    @ApiModelProperty(value = "Endereço (rua,avenida,etc)")
    private String endNumero;

    /**
     * Número do endereço
     */
    @Size(max = 50)
    @ApiModelProperty(value = "Número do endereço")
    private String endBairro;

    /**
     * Bairro do endereço
     */
    @Size(max = 30)
    @ApiModelProperty(value = "Bairro do endereço")
    private String endComplemento;

    /**
     * Complemento do endereço
     */
    @Size(max = 10)
    @ApiModelProperty(value = "Complemento do endereço")
    private String endCep;

    /**
     * CEP do endereço
     */
    @Size(max = 50)
    @ApiModelProperty(value = "CEP do endereço")
    private String telefone;

    @Size(max = 50)
    private String fax;

    @Size(max = 30)
    private String celular;

    @Size(max = 50)
    private String email;

    @Size(max = 50)
    private String site;

    @Size(max = 200)
    private String observacoes;

    private Instant dataAtualizacao;

    private Boolean inativo;

    private AreaDeptoDTO area;

    private CidadeDTO cidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeComp() {
        return nomeComp;
    }

    public void setNomeComp(String nomeComp) {
        this.nomeComp = nomeComp;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getInfoContato() {
        return infoContato;
    }

    public void setInfoContato(String infoContato) {
        this.infoContato = infoContato;
    }

    public String getEndRua() {
        return endRua;
    }

    public void setEndRua(String endRua) {
        this.endRua = endRua;
    }

    public String getEndNumero() {
        return endNumero;
    }

    public void setEndNumero(String endNumero) {
        this.endNumero = endNumero;
    }

    public String getEndBairro() {
        return endBairro;
    }

    public void setEndBairro(String endBairro) {
        this.endBairro = endBairro;
    }

    public String getEndComplemento() {
        return endComplemento;
    }

    public void setEndComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
    }

    public String getEndCep() {
        return endCep;
    }

    public void setEndCep(String endCep) {
        this.endCep = endCep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Instant getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Instant dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public AreaDeptoDTO getArea() {
        return area;
    }

    public void setArea(AreaDeptoDTO area) {
        this.area = area;
    }

    public CidadeDTO getCidade() {
        return cidade;
    }

    public void setCidade(CidadeDTO cidade) {
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContatoDTO)) {
            return false;
        }

        ContatoDTO contatoDTO = (ContatoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contatoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContatoDTO{" +
            "id=" + getId() +
            ", nomeComp='" + getNomeComp() + "'" +
            ", empresa='" + getEmpresa() + "'" +
            ", funcao='" + getFuncao() + "'" +
            ", rg='" + getRg() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", infoContato='" + getInfoContato() + "'" +
            ", endRua='" + getEndRua() + "'" +
            ", endNumero='" + getEndNumero() + "'" +
            ", endBairro='" + getEndBairro() + "'" +
            ", endComplemento='" + getEndComplemento() + "'" +
            ", endCep='" + getEndCep() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", fax='" + getFax() + "'" +
            ", celular='" + getCelular() + "'" +
            ", email='" + getEmail() + "'" +
            ", site='" + getSite() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", dataAtualizacao='" + getDataAtualizacao() + "'" +
            ", inativo='" + getInativo() + "'" +
            ", area=" + getArea() +
            ", cidade=" + getCidade() +
            "}";
    }
}
