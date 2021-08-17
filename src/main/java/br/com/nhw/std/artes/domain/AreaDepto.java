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
 * A AreaDepto.
 */
@Entity
@Table(name = "area_depto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AreaDepto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "area", length = 100, nullable = false)
    private String area;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(mappedBy = "area")
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

    public AreaDepto id(Long id) {
        this.id = id;
        return this;
    }

    public String getArea() {
        return this.area;
    }

    public AreaDepto area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public AreaDepto ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Contato> getContatoes() {
        return this.contatoes;
    }

    public AreaDepto contatoes(Set<Contato> contatoes) {
        this.setContatoes(contatoes);
        return this;
    }

    public AreaDepto addContato(Contato contato) {
        this.contatoes.add(contato);
        contato.setArea(this);
        return this;
    }

    public AreaDepto removeContato(Contato contato) {
        this.contatoes.remove(contato);
        contato.setArea(null);
        return this;
    }

    public void setContatoes(Set<Contato> contatoes) {
        if (this.contatoes != null) {
            this.contatoes.forEach(i -> i.setArea(null));
        }
        if (contatoes != null) {
            contatoes.forEach(i -> i.setArea(this));
        }
        this.contatoes = contatoes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaDepto)) {
            return false;
        }
        return id != null && id.equals(((AreaDepto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaDepto{" +
            "id=" + getId() +
            ", area='" + getArea() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
