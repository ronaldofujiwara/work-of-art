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
 * Mapa do Andar/Local/ambiente de exposicao da obra
 */
@Entity
@Table(name = "andar_mapa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AndarMapa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "imagem_mapa", length = 255, nullable = false)
    private String imagemMapa;

    @OneToMany(mappedBy = "andarMapa")
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "andarMapas" }, allowSetters = true)
    private Espaco espaco;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AndarMapa id(Long id) {
        this.id = id;
        return this;
    }

    public String getImagemMapa() {
        return this.imagemMapa;
    }

    public AndarMapa imagemMapa(String imagemMapa) {
        this.imagemMapa = imagemMapa;
        return this;
    }

    public void setImagemMapa(String imagemMapa) {
        this.imagemMapa = imagemMapa;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public AndarMapa obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public AndarMapa addObra(Obra obra) {
        this.obras.add(obra);
        obra.setAndarMapa(this);
        return this;
    }

    public AndarMapa removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setAndarMapa(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setAndarMapa(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setAndarMapa(this));
        }
        this.obras = obras;
    }

    public Espaco getEspaco() {
        return this.espaco;
    }

    public AndarMapa espaco(Espaco espaco) {
        this.setEspaco(espaco);
        return this;
    }

    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AndarMapa)) {
            return false;
        }
        return id != null && id.equals(((AndarMapa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AndarMapa{" +
            "id=" + getId() +
            ", imagemMapa='" + getImagemMapa() + "'" +
            "}";
    }
}
