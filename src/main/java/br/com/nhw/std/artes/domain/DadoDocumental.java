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
 * Dados Documentais das Obras
 */
@Entity
@Table(name = "dado_documental")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DadoDocumental implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 50)
    @Column(name = "data", length = 50)
    private String data;

    @Size(max = 60)
    @Column(name = "emissor", length = 60)
    private String emissor;

    @Size(max = 30)
    @Column(name = "receptor", length = 30)
    private String receptor;

    @Size(max = 200)
    @Column(name = "obs", length = 200)
    private String obs;

    @Size(max = 64000)
    @Column(name = "transcricao", length = 64000)
    private String transcricao;

    @Column(name = "finalizado")
    private Boolean finalizado;

    @Column(name = "gen_transcricao")
    private Integer genTranscricao;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dadoDocumentals" }, allowSetters = true)
    private TipoDocumento tipoDocumento;

    @ManyToMany(mappedBy = "dadoDocumentals")
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

    public DadoDocumental id(Long id) {
        this.id = id;
        return this;
    }

    public String getData() {
        return this.data;
    }

    public DadoDocumental data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmissor() {
        return this.emissor;
    }

    public DadoDocumental emissor(String emissor) {
        this.emissor = emissor;
        return this;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getReceptor() {
        return this.receptor;
    }

    public DadoDocumental receptor(String receptor) {
        this.receptor = receptor;
        return this;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getObs() {
        return this.obs;
    }

    public DadoDocumental obs(String obs) {
        this.obs = obs;
        return this;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getTranscricao() {
        return this.transcricao;
    }

    public DadoDocumental transcricao(String transcricao) {
        this.transcricao = transcricao;
        return this;
    }

    public void setTranscricao(String transcricao) {
        this.transcricao = transcricao;
    }

    public Boolean getFinalizado() {
        return this.finalizado;
    }

    public DadoDocumental finalizado(Boolean finalizado) {
        this.finalizado = finalizado;
        return this;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    public Integer getGenTranscricao() {
        return this.genTranscricao;
    }

    public DadoDocumental genTranscricao(Integer genTranscricao) {
        this.genTranscricao = genTranscricao;
        return this;
    }

    public void setGenTranscricao(Integer genTranscricao) {
        this.genTranscricao = genTranscricao;
    }

    public TipoDocumento getTipoDocumento() {
        return this.tipoDocumento;
    }

    public DadoDocumental tipoDocumento(TipoDocumento tipoDocumento) {
        this.setTipoDocumento(tipoDocumento);
        return this;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public DadoDocumental obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public DadoDocumental addObra(Obra obra) {
        this.obras.add(obra);
        obra.getDadoDocumentals().add(this);
        return this;
    }

    public DadoDocumental removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.getDadoDocumentals().remove(this);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.removeDadoDocumental(this));
        }
        if (obras != null) {
            obras.forEach(i -> i.addDadoDocumental(this));
        }
        this.obras = obras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DadoDocumental)) {
            return false;
        }
        return id != null && id.equals(((DadoDocumental) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DadoDocumental{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", emissor='" + getEmissor() + "'" +
            ", receptor='" + getReceptor() + "'" +
            ", obs='" + getObs() + "'" +
            ", transcricao='" + getTranscricao() + "'" +
            ", finalizado='" + getFinalizado() + "'" +
            ", genTranscricao=" + getGenTranscricao() +
            "}";
    }
}
