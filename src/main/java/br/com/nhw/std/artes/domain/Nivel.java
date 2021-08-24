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
 * Entidade: Nivel\nNivel de classificacao geral da obra
 */
@Entity
@Table(name = "nivel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nivel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "nivel", length = 250, nullable = false)
    private String nivel;

    /**
     * Nivel de classificacao
     */
    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "nivel")
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

    public Nivel id(Long id) {
        this.id = id;
        return this;
    }

    public String getNivel() {
        return this.nivel;
    }

    public Nivel nivel(String nivel) {
        this.nivel = nivel;
        return this;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Nivel inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Nivel obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Nivel addObra(Obra obra) {
        this.obras.add(obra);
        obra.setNivel(this);
        return this;
    }

    public Nivel removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setNivel(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setNivel(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setNivel(this));
        }
        this.obras = obras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nivel)) {
            return false;
        }
        return id != null && id.equals(((Nivel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nivel{" +
            "id=" + getId() +
            ", nivel='" + getNivel() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
