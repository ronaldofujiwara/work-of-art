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
 * Entidade: Data\nData de execução da obra
 */
@Entity
@Table(name = "data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "data", length = 50, nullable = false)
    private String data;

    /**
     * Data da obra
     */
    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "data")
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

    public Data id(Long id) {
        this.id = id;
        return this;
    }

    public String getData() {
        return this.data;
    }

    public Data data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Data inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Data obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Data addObra(Obra obra) {
        this.obras.add(obra);
        obra.setData(this);
        return this;
    }

    public Data removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setData(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setData(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setData(this));
        }
        this.obras = obras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Data)) {
            return false;
        }
        return id != null && id.equals(((Data) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Data{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
