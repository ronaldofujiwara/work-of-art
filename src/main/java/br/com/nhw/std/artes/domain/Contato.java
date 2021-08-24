package br.com.nhw.std.artes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entidade: Contato\nDados cadastrais de contatos relacionados ao Acervo Cultural
 */
@Entity
@Table(name = "contato")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 120)
    @Column(name = "nome_comp", length = 120, nullable = false)
    private String nomeComp;

    /**
     * Nome completo do contato
     */
    @Size(max = 150)
    @Column(name = "empresa", length = 150)
    private String empresa;

    /**
     * Nome Empresa e/ou Instituição
     */
    @Size(max = 50)
    @Column(name = "funcao", length = 50)
    private String funcao;

    /**
     * Cargo ocupado
     */
    @Size(max = 15)
    @Column(name = "rg", length = 15)
    private String rg;

    /**
     * Documento de identidade
     */
    @Size(max = 15)
    @Column(name = "cpf", length = 15)
    private String cpf;

    /**
     * CPF do contato
     */
    @Size(max = 200)
    @Column(name = "info_contato", length = 200)
    private String infoContato;

    /**
     * Informacoes adicionais do contato
     */
    @Size(max = 50)
    @Column(name = "end_rua", length = 50)
    private String endRua;

    /**
     * Endereço (rua,avenida,etc)
     */
    @Size(max = 10)
    @Column(name = "end_numero", length = 10)
    private String endNumero;

    /**
     * Número do endereço
     */
    @Size(max = 50)
    @Column(name = "end_bairro", length = 50)
    private String endBairro;

    /**
     * Bairro do endereço
     */
    @Size(max = 30)
    @Column(name = "end_complemento", length = 30)
    private String endComplemento;

    /**
     * Complemento do endereço
     */
    @Size(max = 10)
    @Column(name = "end_cep", length = 10)
    private String endCep;

    /**
     * CEP do endereço
     */
    @Size(max = 50)
    @Column(name = "telefone", length = 50)
    private String telefone;

    @Size(max = 50)
    @Column(name = "fax", length = 50)
    private String fax;

    @Size(max = 30)
    @Column(name = "celular", length = 30)
    private String celular;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 50)
    @Column(name = "site", length = 50)
    private String site;

    @Size(max = 200)
    @Column(name = "observacoes", length = 200)
    private String observacoes;

    @Column(name = "data_atualizacao")
    private Instant dataAtualizacao;

    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "fotografo")
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

    @OneToMany(mappedBy = "contatoSeg")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "obras", "contatoSeg", "contatoCor", "moeda" }, allowSetters = true)
    private Set<Seguro> seguroSegs = new HashSet<>();

    @OneToMany(mappedBy = "contatoCor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "obras", "contatoSeg", "contatoCor", "moeda" }, allowSetters = true)
    private Set<Seguro> seguroCors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "contatoes" }, allowSetters = true)
    private AreaDepto area;

    @ManyToOne
    @JsonIgnoreProperties(value = { "empresas", "artistaNascs", "artistaFalescs", "contatoes" }, allowSetters = true)
    private Cidade cidade;

    @ManyToMany(mappedBy = "contatoes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "obras", "contatoes", "cidadeNasc", "cidadeFalesc", "respVerbete", "funcaoArtista" },
        allowSetters = true
    )
    private Set<Artista> artistas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contato id(Long id) {
        this.id = id;
        return this;
    }

    public String getNomeComp() {
        return this.nomeComp;
    }

    public Contato nomeComp(String nomeComp) {
        this.nomeComp = nomeComp;
        return this;
    }

    public void setNomeComp(String nomeComp) {
        this.nomeComp = nomeComp;
    }

    public String getEmpresa() {
        return this.empresa;
    }

    public Contato empresa(String empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getFuncao() {
        return this.funcao;
    }

    public Contato funcao(String funcao) {
        this.funcao = funcao;
        return this;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getRg() {
        return this.rg;
    }

    public Contato rg(String rg) {
        this.rg = rg;
        return this;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Contato cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getInfoContato() {
        return this.infoContato;
    }

    public Contato infoContato(String infoContato) {
        this.infoContato = infoContato;
        return this;
    }

    public void setInfoContato(String infoContato) {
        this.infoContato = infoContato;
    }

    public String getEndRua() {
        return this.endRua;
    }

    public Contato endRua(String endRua) {
        this.endRua = endRua;
        return this;
    }

    public void setEndRua(String endRua) {
        this.endRua = endRua;
    }

    public String getEndNumero() {
        return this.endNumero;
    }

    public Contato endNumero(String endNumero) {
        this.endNumero = endNumero;
        return this;
    }

    public void setEndNumero(String endNumero) {
        this.endNumero = endNumero;
    }

    public String getEndBairro() {
        return this.endBairro;
    }

    public Contato endBairro(String endBairro) {
        this.endBairro = endBairro;
        return this;
    }

    public void setEndBairro(String endBairro) {
        this.endBairro = endBairro;
    }

    public String getEndComplemento() {
        return this.endComplemento;
    }

    public Contato endComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
        return this;
    }

    public void setEndComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
    }

    public String getEndCep() {
        return this.endCep;
    }

    public Contato endCep(String endCep) {
        this.endCep = endCep;
        return this;
    }

    public void setEndCep(String endCep) {
        this.endCep = endCep;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Contato telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFax() {
        return this.fax;
    }

    public Contato fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCelular() {
        return this.celular;
    }

    public Contato celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return this.email;
    }

    public Contato email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return this.site;
    }

    public Contato site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Contato observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Instant getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    public Contato dataAtualizacao(Instant dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
        return this;
    }

    public void setDataAtualizacao(Instant dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Contato inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Contato obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Contato addObra(Obra obra) {
        this.obras.add(obra);
        obra.setFotografo(this);
        return this;
    }

    public Contato removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setFotografo(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setFotografo(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setFotografo(this));
        }
        this.obras = obras;
    }

    public Set<Seguro> getSeguroSegs() {
        return this.seguroSegs;
    }

    public Contato seguroSegs(Set<Seguro> seguros) {
        this.setSeguroSegs(seguros);
        return this;
    }

    public Contato addSeguroSeg(Seguro seguro) {
        this.seguroSegs.add(seguro);
        seguro.setContatoSeg(this);
        return this;
    }

    public Contato removeSeguroSeg(Seguro seguro) {
        this.seguroSegs.remove(seguro);
        seguro.setContatoSeg(null);
        return this;
    }

    public void setSeguroSegs(Set<Seguro> seguros) {
        if (this.seguroSegs != null) {
            this.seguroSegs.forEach(i -> i.setContatoSeg(null));
        }
        if (seguros != null) {
            seguros.forEach(i -> i.setContatoSeg(this));
        }
        this.seguroSegs = seguros;
    }

    public Set<Seguro> getSeguroCors() {
        return this.seguroCors;
    }

    public Contato seguroCors(Set<Seguro> seguros) {
        this.setSeguroCors(seguros);
        return this;
    }

    public Contato addSeguroCor(Seguro seguro) {
        this.seguroCors.add(seguro);
        seguro.setContatoCor(this);
        return this;
    }

    public Contato removeSeguroCor(Seguro seguro) {
        this.seguroCors.remove(seguro);
        seguro.setContatoCor(null);
        return this;
    }

    public void setSeguroCors(Set<Seguro> seguros) {
        if (this.seguroCors != null) {
            this.seguroCors.forEach(i -> i.setContatoCor(null));
        }
        if (seguros != null) {
            seguros.forEach(i -> i.setContatoCor(this));
        }
        this.seguroCors = seguros;
    }

    public AreaDepto getArea() {
        return this.area;
    }

    public Contato area(AreaDepto areaDepto) {
        this.setArea(areaDepto);
        return this;
    }

    public void setArea(AreaDepto areaDepto) {
        this.area = areaDepto;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public Contato cidade(Cidade cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Set<Artista> getArtistas() {
        return this.artistas;
    }

    public Contato artistas(Set<Artista> artistas) {
        this.setArtistas(artistas);
        return this;
    }

    public Contato addArtista(Artista artista) {
        this.artistas.add(artista);
        artista.getContatoes().add(this);
        return this;
    }

    public Contato removeArtista(Artista artista) {
        this.artistas.remove(artista);
        artista.getContatoes().remove(this);
        return this;
    }

    public void setArtistas(Set<Artista> artistas) {
        if (this.artistas != null) {
            this.artistas.forEach(i -> i.removeContato(this));
        }
        if (artistas != null) {
            artistas.forEach(i -> i.addContato(this));
        }
        this.artistas = artistas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contato)) {
            return false;
        }
        return id != null && id.equals(((Contato) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contato{" +
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
            "}";
    }
}
