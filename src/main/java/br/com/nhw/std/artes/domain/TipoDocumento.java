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
 * Entidade: TipoDocumento\nTipos de Documentos
 */
@Entity
@Table(name = "tipo_documento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipoDocumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "tipo_documento", length = 100, nullable = false)
    private String tipoDocumento;

    /**
     * Técnicas ou materiais que constituem a obra
     */
    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "tipoDocumento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tipoDocumento", "obras" }, allowSetters = true)
    private Set<DadoDocumental> dadoDocumentals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumento id(Long id) {
        this.id = id;
        return this;
    }

    public String getTipoDocumento() {
        return this.tipoDocumento;
    }

    public TipoDocumento tipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public TipoDocumento inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<DadoDocumental> getDadoDocumentals() {
        return this.dadoDocumentals;
    }

    public TipoDocumento dadoDocumentals(Set<DadoDocumental> dadoDocumentals) {
        this.setDadoDocumentals(dadoDocumentals);
        return this;
    }

    public TipoDocumento addDadoDocumental(DadoDocumental dadoDocumental) {
        this.dadoDocumentals.add(dadoDocumental);
        dadoDocumental.setTipoDocumento(this);
        return this;
    }

    public TipoDocumento removeDadoDocumental(DadoDocumental dadoDocumental) {
        this.dadoDocumentals.remove(dadoDocumental);
        dadoDocumental.setTipoDocumento(null);
        return this;
    }

    public void setDadoDocumentals(Set<DadoDocumental> dadoDocumentals) {
        if (this.dadoDocumentals != null) {
            this.dadoDocumentals.forEach(i -> i.setTipoDocumento(null));
        }
        if (dadoDocumentals != null) {
            dadoDocumentals.forEach(i -> i.setTipoDocumento(this));
        }
        this.dadoDocumentals = dadoDocumentals;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDocumento)) {
            return false;
        }
        return id != null && id.equals(((TipoDocumento) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDocumento{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
