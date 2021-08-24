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
 * A Categoria.
 */
@Entity
@Table(name = "categoria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "categoria", length = 40, nullable = false)
    private String categoria;

    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "categoria")
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

    public Categoria id(Long id) {
        this.id = id;
        return this;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public Categoria categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Categoria inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Categoria obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Categoria addObra(Obra obra) {
        this.obras.add(obra);
        obra.setCategoria(this);
        return this;
    }

    public Categoria removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setCategoria(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setCategoria(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setCategoria(this));
        }
        this.obras = obras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categoria)) {
            return false;
        }
        return id != null && id.equals(((Categoria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categoria{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
