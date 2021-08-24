package br.com.nhw.std.artes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Artistas autores das obras
 */
@Entity
@Table(name = "artista")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Artista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    /**
     * Nome artístico
     */
    @Size(max = 255)
    @Column(name = "data_nasc", length = 255)
    private String dataNasc;

    /**
     * Data do Nascimento
     */
    @Size(max = 255)
    @Column(name = "data_falec", length = 255)
    private String dataFalec;

    /**
     * Data do Falecimento
     */
    @Size(max = 64000)
    @Column(name = "biografia", length = 64000)
    private String biografia;

    /**
     * Texto memorando para biografia do artista
     */
    @Size(max = 64000)
    @Column(name = "verbete", length = 64000)
    private String verbete;

    /**
     * Texto memorando para verbete sobre o artista
     */
    @Column(name = "data_atual_bio")
    private LocalDate dataAtualBio;

    /**
     * Data de atualização da biografia
     */
    @Column(name = "data_atual_verb")
    private LocalDate dataAtualVerb;

    /**
     * Data de atualização do verbete
     */
    @Column(name = "possui_bio")
    private Boolean possuiBio;

    /**
     * Possui biografia?
     */
    @Column(name = "possui_verb")
    private Boolean possuiVerb;

    /**
     * Possui verbete?
     */
    @Size(max = 255)
    @Column(name = "fonte_bio", length = 255)
    private String fonteBio;

    /**
     * Fonte da biografia
     */
    @Size(max = 255)
    @Column(name = "obras_no_acervo", length = 255)
    private String obrasNoAcervo;

    /**
     * Número de Tombo das obras do artista no acervo??
     */
    @Column(name = "inativo")
    private Boolean inativo;

    /**
     * Registro inativo?
     */
    @Size(max = 64000)
    @Column(name = "agradecimentos", length = 64000)
    private String agradecimentos;

    /**
     * Agradecimentos (Direitos de Uso)
     */
    @Size(max = 64000)
    @Column(name = "copyright", length = 64000)
    private String copyright;

    /**
     * Copyright (Direitos de Uso)
     */
    @Size(max = 255)
    @Column(name = "obs_uso", length = 255)
    private String obsUso;

    @OneToMany(mappedBy = "artista")
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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(
        name = "rel_artista__contato",
        joinColumns = @JoinColumn(name = "artista_id"),
        inverseJoinColumns = @JoinColumn(name = "contato_id")
    )
    @JsonIgnoreProperties(value = { "obras", "seguroSegs", "seguroCors", "area", "cidade", "artistas" }, allowSetters = true)
    private Set<Contato> contatoes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "empresas", "artistaNascs", "artistaFalescs", "contatoes" }, allowSetters = true)
    private Cidade cidadeNasc;

    @ManyToOne
    @JsonIgnoreProperties(value = { "empresas", "artistaNascs", "artistaFalescs", "contatoes" }, allowSetters = true)
    private Cidade cidadeFalesc;

    @ManyToOne
    @JsonIgnoreProperties(value = { "artVerbetes", "obras" }, allowSetters = true)
    private Responsavel respVerbete;

    @ManyToOne
    @JsonIgnoreProperties(value = { "artistas" }, allowSetters = true)
    private FuncaoArtista funcaoArtista;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artista id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public Artista nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return this.dataNasc;
    }

    public Artista dataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
        return this;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getDataFalec() {
        return this.dataFalec;
    }

    public Artista dataFalec(String dataFalec) {
        this.dataFalec = dataFalec;
        return this;
    }

    public void setDataFalec(String dataFalec) {
        this.dataFalec = dataFalec;
    }

    public String getBiografia() {
        return this.biografia;
    }

    public Artista biografia(String biografia) {
        this.biografia = biografia;
        return this;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getVerbete() {
        return this.verbete;
    }

    public Artista verbete(String verbete) {
        this.verbete = verbete;
        return this;
    }

    public void setVerbete(String verbete) {
        this.verbete = verbete;
    }

    public LocalDate getDataAtualBio() {
        return this.dataAtualBio;
    }

    public Artista dataAtualBio(LocalDate dataAtualBio) {
        this.dataAtualBio = dataAtualBio;
        return this;
    }

    public void setDataAtualBio(LocalDate dataAtualBio) {
        this.dataAtualBio = dataAtualBio;
    }

    public LocalDate getDataAtualVerb() {
        return this.dataAtualVerb;
    }

    public Artista dataAtualVerb(LocalDate dataAtualVerb) {
        this.dataAtualVerb = dataAtualVerb;
        return this;
    }

    public void setDataAtualVerb(LocalDate dataAtualVerb) {
        this.dataAtualVerb = dataAtualVerb;
    }

    public Boolean getPossuiBio() {
        return this.possuiBio;
    }

    public Artista possuiBio(Boolean possuiBio) {
        this.possuiBio = possuiBio;
        return this;
    }

    public void setPossuiBio(Boolean possuiBio) {
        this.possuiBio = possuiBio;
    }

    public Boolean getPossuiVerb() {
        return this.possuiVerb;
    }

    public Artista possuiVerb(Boolean possuiVerb) {
        this.possuiVerb = possuiVerb;
        return this;
    }

    public void setPossuiVerb(Boolean possuiVerb) {
        this.possuiVerb = possuiVerb;
    }

    public String getFonteBio() {
        return this.fonteBio;
    }

    public Artista fonteBio(String fonteBio) {
        this.fonteBio = fonteBio;
        return this;
    }

    public void setFonteBio(String fonteBio) {
        this.fonteBio = fonteBio;
    }

    public String getObrasNoAcervo() {
        return this.obrasNoAcervo;
    }

    public Artista obrasNoAcervo(String obrasNoAcervo) {
        this.obrasNoAcervo = obrasNoAcervo;
        return this;
    }

    public void setObrasNoAcervo(String obrasNoAcervo) {
        this.obrasNoAcervo = obrasNoAcervo;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Artista inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public String getAgradecimentos() {
        return this.agradecimentos;
    }

    public Artista agradecimentos(String agradecimentos) {
        this.agradecimentos = agradecimentos;
        return this;
    }

    public void setAgradecimentos(String agradecimentos) {
        this.agradecimentos = agradecimentos;
    }

    public String getCopyright() {
        return this.copyright;
    }

    public Artista copyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getObsUso() {
        return this.obsUso;
    }

    public Artista obsUso(String obsUso) {
        this.obsUso = obsUso;
        return this;
    }

    public void setObsUso(String obsUso) {
        this.obsUso = obsUso;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Artista obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Artista addObra(Obra obra) {
        this.obras.add(obra);
        obra.setArtista(this);
        return this;
    }

    public Artista removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setArtista(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setArtista(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setArtista(this));
        }
        this.obras = obras;
    }

    public Set<Contato> getContatoes() {
        return this.contatoes;
    }

    public Artista contatoes(Set<Contato> contatoes) {
        this.setContatoes(contatoes);
        return this;
    }

    public Artista addContato(Contato contato) {
        this.contatoes.add(contato);
        contato.getArtistas().add(this);
        return this;
    }

    public Artista removeContato(Contato contato) {
        this.contatoes.remove(contato);
        contato.getArtistas().remove(this);
        return this;
    }

    public void setContatoes(Set<Contato> contatoes) {
        this.contatoes = contatoes;
    }

    public Cidade getCidadeNasc() {
        return this.cidadeNasc;
    }

    public Artista cidadeNasc(Cidade cidade) {
        this.setCidadeNasc(cidade);
        return this;
    }

    public void setCidadeNasc(Cidade cidade) {
        this.cidadeNasc = cidade;
    }

    public Cidade getCidadeFalesc() {
        return this.cidadeFalesc;
    }

    public Artista cidadeFalesc(Cidade cidade) {
        this.setCidadeFalesc(cidade);
        return this;
    }

    public void setCidadeFalesc(Cidade cidade) {
        this.cidadeFalesc = cidade;
    }

    public Responsavel getRespVerbete() {
        return this.respVerbete;
    }

    public Artista respVerbete(Responsavel responsavel) {
        this.setRespVerbete(responsavel);
        return this;
    }

    public void setRespVerbete(Responsavel responsavel) {
        this.respVerbete = responsavel;
    }

    public FuncaoArtista getFuncaoArtista() {
        return this.funcaoArtista;
    }

    public Artista funcaoArtista(FuncaoArtista funcaoArtista) {
        this.setFuncaoArtista(funcaoArtista);
        return this;
    }

    public void setFuncaoArtista(FuncaoArtista funcaoArtista) {
        this.funcaoArtista = funcaoArtista;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artista)) {
            return false;
        }
        return id != null && id.equals(((Artista) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artista{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataNasc='" + getDataNasc() + "'" +
            ", dataFalec='" + getDataFalec() + "'" +
            ", biografia='" + getBiografia() + "'" +
            ", verbete='" + getVerbete() + "'" +
            ", dataAtualBio='" + getDataAtualBio() + "'" +
            ", dataAtualVerb='" + getDataAtualVerb() + "'" +
            ", possuiBio='" + getPossuiBio() + "'" +
            ", possuiVerb='" + getPossuiVerb() + "'" +
            ", fonteBio='" + getFonteBio() + "'" +
            ", obrasNoAcervo='" + getObrasNoAcervo() + "'" +
            ", inativo='" + getInativo() + "'" +
            ", agradecimentos='" + getAgradecimentos() + "'" +
            ", copyright='" + getCopyright() + "'" +
            ", obsUso='" + getObsUso() + "'" +
            "}";
    }
}
