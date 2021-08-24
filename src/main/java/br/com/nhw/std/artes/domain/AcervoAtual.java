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
 * Acervo atual em uso
 */
@Entity
@Table(name = "acervo_atual")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AcervoAtual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "acervo_atual", length = 50, nullable = false)
    private String acervoAtual;

    /**
     * Acervo Atual
     */
    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "acervoatual")
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

    public AcervoAtual id(Long id) {
        this.id = id;
        return this;
    }

    public String getAcervoAtual() {
        return this.acervoAtual;
    }

    public AcervoAtual acervoAtual(String acervoAtual) {
        this.acervoAtual = acervoAtual;
        return this;
    }

    public void setAcervoAtual(String acervoAtual) {
        this.acervoAtual = acervoAtual;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public AcervoAtual inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public AcervoAtual obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public AcervoAtual addObra(Obra obra) {
        this.obras.add(obra);
        obra.setAcervoatual(this);
        return this;
    }

    public AcervoAtual removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setAcervoatual(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setAcervoatual(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setAcervoatual(this));
        }
        this.obras = obras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcervoAtual)) {
            return false;
        }
        return id != null && id.equals(((AcervoAtual) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcervoAtual{" +
            "id=" + getId() +
            ", acervoAtual='" + getAcervoAtual() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
