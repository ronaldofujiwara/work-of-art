package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Empresa} entity.
 */
@ApiModel(description = "Empresas do Grupo")
public class EmpresaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String nomeEmpresa;

    private Integer codigoBrad;

    @Size(max = 50)
    private String empresa;

    @Size(max = 50)
    private String nome;

    @Size(max = 50)
    private String funcao;

    @Size(max = 20)
    private String cNPJ;

    @Size(max = 10)
    private String inscricaoEstadual;

    @Size(max = 150)
    private String obs;

    @Size(max = 50)
    private String rua;

    @Size(max = 5)
    private String numero;

    @Size(max = 30)
    private String bairro;

    @Size(max = 30)
    private String complemento;

    @Size(max = 8)
    private String cEP;

    @Size(max = 50)
    private String telefone;

    @Size(max = 50)
    private String fax;

    @Size(max = 30)
    private String celular;

    @Size(max = 64000)
    private String email;

    @Size(max = 150)
    private String credito;

    private Boolean inativo;

    private Integer genEmail;

    private CidadeDTO cidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public Integer getCodigoBrad() {
        return codigoBrad;
    }

    public void setCodigoBrad(Integer codigoBrad) {
        this.codigoBrad = codigoBrad;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getcNPJ() {
        return cNPJ;
    }

    public void setcNPJ(String cNPJ) {
        this.cNPJ = cNPJ;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getcEP() {
        return cEP;
    }

    public void setcEP(String cEP) {
        this.cEP = cEP;
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

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Integer getGenEmail() {
        return genEmail;
    }

    public void setGenEmail(Integer genEmail) {
        this.genEmail = genEmail;
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
        if (!(o instanceof EmpresaDTO)) {
            return false;
        }

        EmpresaDTO empresaDTO = (EmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaDTO{" +
            "id=" + getId() +
            ", nomeEmpresa='" + getNomeEmpresa() + "'" +
            ", codigoBrad=" + getCodigoBrad() +
            ", empresa='" + getEmpresa() + "'" +
            ", nome='" + getNome() + "'" +
            ", funcao='" + getFuncao() + "'" +
            ", cNPJ='" + getcNPJ() + "'" +
            ", inscricaoEstadual='" + getInscricaoEstadual() + "'" +
            ", obs='" + getObs() + "'" +
            ", rua='" + getRua() + "'" +
            ", numero='" + getNumero() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", cEP='" + getcEP() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", fax='" + getFax() + "'" +
            ", celular='" + getCelular() + "'" +
            ", email='" + getEmail() + "'" +
            ", credito='" + getCredito() + "'" +
            ", inativo='" + getInativo() + "'" +
            ", genEmail=" + getGenEmail() +
            ", cidade=" + getCidade() +
            "}";
    }
}
