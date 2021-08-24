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
 * Entidade: Espaco - Espacos expositivos
 */
@Entity
@Table(name = "espaco")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Espaco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "espaco", length = 100, nullable = false)
    private String espaco;

    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "espaco")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "obras", "espaco" }, allowSetters = true)
    private Set<AndarMapa> andarMapas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Espaco id(Long id) {
        this.id = id;
        return this;
    }

    public String getEspaco() {
        return this.espaco;
    }

    public Espaco espaco(String espaco) {
        this.espaco = espaco;
        return this;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Espaco inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<AndarMapa> getAndarMapas() {
        return this.andarMapas;
    }

    public Espaco andarMapas(Set<AndarMapa> andarMapas) {
        this.setAndarMapas(andarMapas);
        return this;
    }

    public Espaco addAndarMapa(AndarMapa andarMapa) {
        this.andarMapas.add(andarMapa);
        andarMapa.setEspaco(this);
        return this;
    }

    public Espaco removeAndarMapa(AndarMapa andarMapa) {
        this.andarMapas.remove(andarMapa);
        andarMapa.setEspaco(null);
        return this;
    }

    public void setAndarMapas(Set<AndarMapa> andarMapas) {
        if (this.andarMapas != null) {
            this.andarMapas.forEach(i -> i.setEspaco(null));
        }
        if (andarMapas != null) {
            andarMapas.forEach(i -> i.setEspaco(this));
        }
        this.andarMapas = andarMapas;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Espaco)) {
            return false;
        }
        return id != null && id.equals(((Espaco) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Espaco{" +
            "id=" + getId() +
            ", espaco='" + getEspaco() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
