package br.com.nhw.std.artes.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ambiente.
 */
@Entity
@Table(name = "ambiente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ambiente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "ambiente", length = 50, nullable = false)
    private String ambiente;

    @Column(name = "ativo")
    private Boolean ativo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ambiente id(Long id) {
        this.id = id;
        return this;
    }

    public String getAmbiente() {
        return this.ambiente;
    }

    public Ambiente ambiente(String ambiente) {
        this.ambiente = ambiente;
        return this;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public Ambiente ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ambiente)) {
            return false;
        }
        return id != null && id.equals(((Ambiente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ambiente{" +
            "id=" + getId() +
            ", ambiente='" + getAmbiente() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
