package br.com.nhw.std.artes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Empresas do Grupo
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nome_empresa", length = 100, nullable = false)
    private String nomeEmpresa;

    @Column(name = "codigo_brad")
    private Integer codigoBrad;

    @Size(max = 50)
    @Column(name = "empresa", length = 50)
    private String empresa;

    @Size(max = 50)
    @Column(name = "nome", length = 50)
    private String nome;

    @Size(max = 50)
    @Column(name = "funcao", length = 50)
    private String funcao;

    @Size(max = 20)
    @Column(name = "c_npj", length = 20)
    private String cNPJ;

    @Size(max = 10)
    @Column(name = "inscricao_estadual", length = 10)
    private String inscricaoEstadual;

    @Size(max = 150)
    @Column(name = "obs", length = 150)
    private String obs;

    @Size(max = 50)
    @Column(name = "rua", length = 50)
    private String rua;

    @Size(max = 5)
    @Column(name = "numero", length = 5)
    private String numero;

    @Size(max = 30)
    @Column(name = "bairro", length = 30)
    private String bairro;

    @Size(max = 30)
    @Column(name = "complemento", length = 30)
    private String complemento;

    @Size(max = 8)
    @Column(name = "c_ep", length = 8)
    private String cEP;

    @Size(max = 50)
    @Column(name = "telefone", length = 50)
    private String telefone;

    @Size(max = 50)
    @Column(name = "fax", length = 50)
    private String fax;

    @Size(max = 30)
    @Column(name = "celular", length = 30)
    private String celular;

    @Size(max = 64000)
    @Column(name = "email", length = 64000)
    private String email;

    @Size(max = 150)
    @Column(name = "credito", length = 150)
    private String credito;

    @Column(name = "inativo")
    private Boolean inativo;

    @Column(name = "gen_email")
    private Integer genEmail;

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "dadoDocumentals",
            "artista",
            "categoria",
            "tecnica",
            "nivel",
            "data",
            "empresa",
            "moeda",
            "seguro",
            "responsavel",
            "acervoatual",
            "fotografo",
            "andarMapa",
        },
        allowSetters = true
    )
    private Set<Obra> obras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "empresas", "artistaNascs", "artistaFalescs", "contatoes" }, allowSetters = true)
    private Cidade cidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empresa id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomeEmpresa() {
        return this.nomeEmpresa;
    }

    public Empresa nomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
        return this;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public Integer getCodigoBrad() {
        return this.codigoBrad;
    }

    public Empresa codigoBrad(Integer codigoBrad) {
        this.codigoBrad = codigoBrad;
        return this;
    }

    public void setCodigoBrad(Integer codigoBrad) {
        this.codigoBrad = codigoBrad;
    }

    public String getEmpresa() {
        return this.empresa;
    }

    public Empresa empresa(String empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNome() {
        return this.nome;
    }

    public Empresa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFuncao() {
        return this.funcao;
    }

    public Empresa funcao(String funcao) {
        this.funcao = funcao;
        return this;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getcNPJ() {
        return this.cNPJ;
    }

    public Empresa cNPJ(String cNPJ) {
        this.cNPJ = cNPJ;
        return this;
    }

    public void setcNPJ(String cNPJ) {
        this.cNPJ = cNPJ;
    }

    public String getInscricaoEstadual() {
        return this.inscricaoEstadual;
    }

    public Empresa inscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
        return this;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getObs() {
        return this.obs;
    }

    public Empresa obs(String obs) {
        this.obs = obs;
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getRua() {
        return this.rua;
    }

    public Empresa rua(String rua) {
        this.rua = rua;
        return this;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return this.numero;
    }

    public Empresa numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public Empresa bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public Empresa complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getcEP() {
        return this.cEP;
    }

    public Empresa cEP(String cEP) {
        this.cEP = cEP;
        return this;
    }

    public void setcEP(String cEP) {
        this.cEP = cEP;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Empresa telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFax() {
        return this.fax;
    }

    public Empresa fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCelular() {
        return this.celular;
    }

    public Empresa celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return this.email;
    }

    public Empresa email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCredito() {
        return this.credito;
    }

    public Empresa credito(String credito) {
        this.credito = credito;
        return this;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Empresa inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Integer getGenEmail() {
        return this.genEmail;
    }

    public Empresa genEmail(Integer genEmail) {
        this.genEmail = genEmail;
        return this;
    }

    public void setGenEmail(Integer genEmail) {
        this.genEmail = genEmail;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Empresa obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Empresa addObra(Obra obra) {
        this.obras.add(obra);
        obra.setEmpresa(this);
        return this;
    }

    public Empresa removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setEmpresa(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setEmpresa(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setEmpresa(this));
        }
        this.obras = obras;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public Empresa cidade(Cidade cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empresa)) {
            return false;
        }
        return id != null && id.equals(((Empresa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empresa{" +
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
            "}";
    }
}
