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
 * Entidade: Funcao do Artista
 */
@Entity
@Table(name = "funcao_artista")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FuncaoArtista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "funcao_artista", length = 40, nullable = false)
    private String funcaoArtista;

    /**
     * Funcao do artista
     */
    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "funcaoArtista")
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

    public FuncaoArtista id(Long id) {
        this.id = id;
        return this;
    }

    public String getFuncaoArtista() {
        return this.funcaoArtista;
    }

    public FuncaoArtista funcaoArtista(String funcaoArtista) {
        this.funcaoArtista = funcaoArtista;
        return this;
    }

    public void setFuncaoArtista(String funcaoArtista) {
        this.funcaoArtista = funcaoArtista;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public FuncaoArtista inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Artista> getArtistas() {
        return this.artistas;
    }

    public FuncaoArtista artistas(Set<Artista> artistas) {
        this.setArtistas(artistas);
        return this;
    }

    public FuncaoArtista addArtista(Artista artista) {
        this.artistas.add(artista);
        artista.setFuncaoArtista(this);
        return this;
    }

    public FuncaoArtista removeArtista(Artista artista) {
        this.artistas.remove(artista);
        artista.setFuncaoArtista(null);
        return this;
    }

    public void setArtistas(Set<Artista> artistas) {
        if (this.artistas != null) {
            this.artistas.forEach(i -> i.setFuncaoArtista(null));
        }
        if (artistas != null) {
            artistas.forEach(i -> i.setFuncaoArtista(this));
        }
        this.artistas = artistas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuncaoArtista)) {
            return false;
        }
        return id != null && id.equals(((FuncaoArtista) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncaoArtista{" +
            "id=" + getId() +
            ", funcaoArtista='" + getFuncaoArtista() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
