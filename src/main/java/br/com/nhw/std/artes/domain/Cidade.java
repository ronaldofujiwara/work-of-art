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
 * A Cidade.
 */
@Entity
@Table(name = "cidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "cidade", length = 255, nullable = false)
    private String cidade;

    @Size(max = 255)
    @Column(name = "estado", length = 255)
    private String estado;

    @Size(max = 255)
    @Column(name = "pais", length = 255)
    private String pais;

    @OneToMany(mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "area", "cidade" }, allowSetters = true)
    private Set<Contato> contatoes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cidade id(Long id) {
        this.id = id;
        return this;
    }

    public String getCidade() {
        return this.cidade;
    }

    public Cidade cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public Cidade estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return this.pais;
    }

    public Cidade pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Set<Contato> getContatoes() {
        return this.contatoes;
    }

    public Cidade contatoes(Set<Contato> contatoes) {
        this.setContatoes(contatoes);
        return this;
    }

    public Cidade addContato(Contato contato) {
        this.contatoes.add(contato);
        contato.setCidade(this);
        return this;
    }

    public Cidade removeContato(Contato contato) {
        this.contatoes.remove(contato);
        contato.setCidade(null);
        return this;
    }

    public void setContatoes(Set<Contato> contatoes) {
        if (this.contatoes != null) {
            this.contatoes.forEach(i -> i.setCidade(null));
        }
        if (contatoes != null) {
            contatoes.forEach(i -> i.setCidade(this));
        }
        this.contatoes = contatoes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cidade)) {
            return false;
        }
        return id != null && id.equals(((Cidade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cidade{" +
            "id=" + getId() +
            ", cidade='" + getCidade() + "'" +
            ", estado='" + getEstado() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
