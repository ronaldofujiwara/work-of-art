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
 * Entidade: Responsavel\nColaborador responsavel pelo verbete/registro - Obs: no Access -> tabela Expomus
 */
@Entity
@Table(name = "responsavel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Responsavel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

    /**
     * Colaborador responsavel pelo verbete
     */
    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "respVerbete")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "obras", "contatoes", "cidadeNasc", "cidadeFalesc", "respVerbete", "funcaoArtista" },
        allowSetters = true
    )
    private Set<Artista> artVerbetes = new HashSet<>();

    @OneToMany(mappedBy = "responsavel")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Responsavel id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public Responsavel nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Responsavel inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Artista> getArtVerbetes() {
        return this.artVerbetes;
    }

    public Responsavel artVerbetes(Set<Artista> artistas) {
        this.setArtVerbetes(artistas);
        return this;
    }

    public Responsavel addArtVerbete(Artista artista) {
        this.artVerbetes.add(artista);
        artista.setRespVerbete(this);
        return this;
    }

    public Responsavel removeArtVerbete(Artista artista) {
        this.artVerbetes.remove(artista);
        artista.setRespVerbete(null);
        return this;
    }

    public void setArtVerbetes(Set<Artista> artistas) {
        if (this.artVerbetes != null) {
            this.artVerbetes.forEach(i -> i.setRespVerbete(null));
        }
        if (artistas != null) {
            artistas.forEach(i -> i.setRespVerbete(this));
        }
        this.artVerbetes = artistas;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Responsavel obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Responsavel addObra(Obra obra) {
        this.obras.add(obra);
        obra.setResponsavel(this);
        return this;
    }

    public Responsavel removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setResponsavel(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setResponsavel(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setResponsavel(this));
        }
        this.obras = obras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Responsavel)) {
            return false;
        }
        return id != null && id.equals(((Responsavel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Responsavel{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
