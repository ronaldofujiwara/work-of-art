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
 * Cidade-UF-Pais - Obs: no Access -> tabela: Cid-UF-Pais1
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

    @Size(max = 255)
    @Column(name = "cidade_uf_pais", length = 255)
    private String cidadeUFPais;

    @Size(max = 255)
    @Column(name = "estado_pais", length = 255)
    private String estadoPais;

    /**
     * Registro inativo?
     */
    @Column(name = "inativo")
    private Boolean inativo;

    @OneToMany(mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "obras", "cidade" }, allowSetters = true)
    private Set<Empresa> empresas = new HashSet<>();

    @OneToMany(mappedBy = "cidadeNasc")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "obras", "contatoes", "cidadeNasc", "cidadeFalesc", "respVerbete", "funcaoArtista" },
        allowSetters = true
    )
    private Set<Artista> artistaNascs = new HashSet<>();

    @OneToMany(mappedBy = "cidadeFalesc")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "obras", "contatoes", "cidadeNasc", "cidadeFalesc", "respVerbete", "funcaoArtista" },
        allowSetters = true
    )
    private Set<Artista> artistaFalescs = new HashSet<>();

    @OneToMany(mappedBy = "cidade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "obras", "seguroSegs", "seguroCors", "area", "cidade", "artistas" }, allowSetters = true)
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

    public String getCidadeUFPais() {
        return this.cidadeUFPais;
    }

    public Cidade cidadeUFPais(String cidadeUFPais) {
        this.cidadeUFPais = cidadeUFPais;
        return this;
    }

    public void setCidadeUFPais(String cidadeUFPais) {
        this.cidadeUFPais = cidadeUFPais;
    }

    public String getEstadoPais() {
        return this.estadoPais;
    }

    public Cidade estadoPais(String estadoPais) {
        this.estadoPais = estadoPais;
        return this;
    }

    public void setEstadoPais(String estadoPais) {
        this.estadoPais = estadoPais;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Cidade inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public Set<Empresa> getEmpresas() {
        return this.empresas;
    }

    public Cidade empresas(Set<Empresa> empresas) {
        this.setEmpresas(empresas);
        return this;
    }

    public Cidade addEmpresa(Empresa empresa) {
        this.empresas.add(empresa);
        empresa.setCidade(this);
        return this;
    }

    public Cidade removeEmpresa(Empresa empresa) {
        this.empresas.remove(empresa);
        empresa.setCidade(null);
        return this;
    }

    public void setEmpresas(Set<Empresa> empresas) {
        if (this.empresas != null) {
            this.empresas.forEach(i -> i.setCidade(null));
        }
        if (empresas != null) {
            empresas.forEach(i -> i.setCidade(this));
        }
        this.empresas = empresas;
    }

    public Set<Artista> getArtistaNascs() {
        return this.artistaNascs;
    }

    public Cidade artistaNascs(Set<Artista> artistas) {
        this.setArtistaNascs(artistas);
        return this;
    }

    public Cidade addArtistaNasc(Artista artista) {
        this.artistaNascs.add(artista);
        artista.setCidadeNasc(this);
        return this;
    }

    public Cidade removeArtistaNasc(Artista artista) {
        this.artistaNascs.remove(artista);
        artista.setCidadeNasc(null);
        return this;
    }

    public void setArtistaNascs(Set<Artista> artistas) {
        if (this.artistaNascs != null) {
            this.artistaNascs.forEach(i -> i.setCidadeNasc(null));
        }
        if (artistas != null) {
            artistas.forEach(i -> i.setCidadeNasc(this));
        }
        this.artistaNascs = artistas;
    }

    public Set<Artista> getArtistaFalescs() {
        return this.artistaFalescs;
    }

    public Cidade artistaFalescs(Set<Artista> artistas) {
        this.setArtistaFalescs(artistas);
        return this;
    }

    public Cidade addArtistaFalesc(Artista artista) {
        this.artistaFalescs.add(artista);
        artista.setCidadeFalesc(this);
        return this;
    }

    public Cidade removeArtistaFalesc(Artista artista) {
        this.artistaFalescs.remove(artista);
        artista.setCidadeFalesc(null);
        return this;
    }

    public void setArtistaFalescs(Set<Artista> artistas) {
        if (this.artistaFalescs != null) {
            this.artistaFalescs.forEach(i -> i.setCidadeFalesc(null));
        }
        if (artistas != null) {
            artistas.forEach(i -> i.setCidadeFalesc(this));
        }
        this.artistaFalescs = artistas;
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
            ", cidadeUFPais='" + getCidadeUFPais() + "'" +
            ", estadoPais='" + getEstadoPais() + "'" +
            ", inativo='" + getInativo() + "'" +
            "}";
    }
}
