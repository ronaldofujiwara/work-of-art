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
 * Entidade: Moeda\nCadastro de Tipos de Moeda
 */
@Entity
@Table(name = "moeda")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Moeda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "tipo_moeda", length = 15, nullable = false)
    private String tipoMoeda;

    /**
     * Moeda
     */
    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "moeda")
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

    @OneToMany(mappedBy = "moeda")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "obras", "contatoSeg", "contatoCor", "moeda" }, allowSetters = true)
    private Set<Seguro> seguros = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Moeda id(Long id) {
        this.id = id;
        return this;
    }

    public String getTipoMoeda() {
        return this.tipoMoeda;
    }

    public Moeda tipoMoeda(String tipoMoeda) {
        this.tipoMoeda = tipoMoeda;
        return this;
    }

    public void setTipoMoeda(String tipoMoeda) {
        this.tipoMoeda = tipoMoeda;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Moeda inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Moeda obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Moeda addObra(Obra obra) {
        this.obras.add(obra);
        obra.setMoeda(this);
        return this;
    }

    public Moeda removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setMoeda(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setMoeda(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setMoeda(this));
        }
        this.obras = obras;
    }

    public Set<Seguro> getSeguros() {
        return this.seguros;
    }

    public Moeda seguros(Set<Seguro> seguros) {
        this.setSeguros(seguros);
        return this;
    }

    public Moeda addSeguro(Seguro seguro) {
        this.seguros.add(seguro);
        seguro.setMoeda(this);
        return this;
    }

    public Moeda removeSeguro(Seguro seguro) {
        this.seguros.remove(seguro);
        seguro.setMoeda(null);
        return this;
    }

    public void setSeguros(Set<Seguro> seguros) {
        if (this.seguros != null) {
            this.seguros.forEach(i -> i.setMoeda(null));
        }
        if (seguros != null) {
            seguros.forEach(i -> i.setMoeda(this));
        }
        this.seguros = seguros;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Moeda)) {
            return false;
        }
        return id != null && id.equals(((Moeda) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Moeda{" +
            "id=" + getId() +
            ", tipoMoeda='" + getTipoMoeda() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
