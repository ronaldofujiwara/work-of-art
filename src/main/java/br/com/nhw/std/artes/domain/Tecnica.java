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
 * Entidade: Tecnica\nRelação de técnicas ou materiais que constituem a obra
 */
@Entity
@Table(name = "tecnica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tecnica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "tecnica", length = 150, nullable = false)
    private String tecnica;

    /**
     * Técnicas ou materiais que constituem a obra
     */
    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "tecnica")
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

    public Tecnica id(Long id) {
        this.id = id;
        return this;
    }

    public String getTecnica() {
        return this.tecnica;
    }

    public Tecnica tecnica(String tecnica) {
        this.tecnica = tecnica;
        return this;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Tecnica inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Tecnica obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Tecnica addObra(Obra obra) {
        this.obras.add(obra);
        obra.setTecnica(this);
        return this;
    }

    public Tecnica removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setTecnica(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setTecnica(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setTecnica(this));
        }
        this.obras = obras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tecnica)) {
            return false;
        }
        return id != null && id.equals(((Tecnica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tecnica{" +
            "id=" + getId() +
            ", tecnica='" + getTecnica() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
