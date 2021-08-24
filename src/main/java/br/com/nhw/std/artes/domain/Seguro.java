package br.com.nhw.std.artes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Informações de seguro das obras
 */
@Entity
@Table(name = "seguro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Seguro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 200)
    @Column(name = "apolice", length = 200)
    private String apolice;

    @Size(max = 30)
    @Column(name = "obs_seguro", length = 30)
    private String obsSeguro;

    @Size(max = 255)
    @Column(name = "contrato_proposta", length = 255)
    private String contratoProposta;

    @Size(max = 50)
    @Column(name = "cia_seguradora", length = 50)
    private String ciaSeguradora;

    @Size(max = 255)
    @Column(name = "cnpj_seguradora", length = 255)
    private String cnpjSeguradora;

    @Size(max = 255)
    @Column(name = "susep_seguradora", length = 255)
    private String susepSeguradora;

    @Size(max = 50)
    @Column(name = "corretora", length = 50)
    private String corretora;

    @Size(max = 255)
    @Column(name = "cnpj_corretora", length = 255)
    private String cnpjCorretora;

    @Size(max = 255)
    @Column(name = "susep_corretora", length = 255)
    private String susepCorretora;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_venc")
    private LocalDate dataVenc;

    @Column(name = "inativo")
    private Boolean inativo;

    @Size(max = 255)
    @Column(name = "premio", length = 255)
    private String premio;

    @Column(name = "tx_conversao", precision = 21, scale = 2)
    private BigDecimal txConversao;

    @Column(name = "gen_status_seguro")
    private Integer genStatusSeguro;

    @Column(name = "data_atual_seguro")
    private LocalDate dataAtualSeguro;

    @OneToMany(mappedBy = "seguro")
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
    @JsonIgnoreProperties(value = { "obras", "seguroSegs", "seguroCors", "area", "cidade", "artistas" }, allowSetters = true)
    private Contato contatoSeg;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras", "seguroSegs", "seguroCors", "area", "cidade", "artistas" }, allowSetters = true)
    private Contato contatoCor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "obras", "seguros" }, allowSetters = true)
    private Moeda moeda;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Seguro id(Long id) {
        this.id = id;
        return this;
    }

    public String getApolice() {
        return this.apolice;
    }

    public Seguro apolice(String apolice) {
        this.apolice = apolice;
        return this;
    }

    public void setApolice(String apolice) {
        this.apolice = apolice;
    }

    public String getObsSeguro() {
        return this.obsSeguro;
    }

    public Seguro obsSeguro(String obsSeguro) {
        this.obsSeguro = obsSeguro;
        return this;
    }

    public void setObsSeguro(String obsSeguro) {
        this.obsSeguro = obsSeguro;
    }

    public String getContratoProposta() {
        return this.contratoProposta;
    }

    public Seguro contratoProposta(String contratoProposta) {
        this.contratoProposta = contratoProposta;
        return this;
    }

    public void setContratoProposta(String contratoProposta) {
        this.contratoProposta = contratoProposta;
    }

    public String getCiaSeguradora() {
        return this.ciaSeguradora;
    }

    public Seguro ciaSeguradora(String ciaSeguradora) {
        this.ciaSeguradora = ciaSeguradora;
        return this;
    }

    public void setCiaSeguradora(String ciaSeguradora) {
        this.ciaSeguradora = ciaSeguradora;
    }

    public String getCnpjSeguradora() {
        return this.cnpjSeguradora;
    }

    public Seguro cnpjSeguradora(String cnpjSeguradora) {
        this.cnpjSeguradora = cnpjSeguradora;
        return this;
    }

    public void setCnpjSeguradora(String cnpjSeguradora) {
        this.cnpjSeguradora = cnpjSeguradora;
    }

    public String getSusepSeguradora() {
        return this.susepSeguradora;
    }

    public Seguro susepSeguradora(String susepSeguradora) {
        this.susepSeguradora = susepSeguradora;
        return this;
    }

    public void setSusepSeguradora(String susepSeguradora) {
        this.susepSeguradora = susepSeguradora;
    }

    public String getCorretora() {
        return this.corretora;
    }

    public Seguro corretora(String corretora) {
        this.corretora = corretora;
        return this;
    }

    public void setCorretora(String corretora) {
        this.corretora = corretora;
    }

    public String getCnpjCorretora() {
        return this.cnpjCorretora;
    }

    public Seguro cnpjCorretora(String cnpjCorretora) {
        this.cnpjCorretora = cnpjCorretora;
        return this;
    }

    public void setCnpjCorretora(String cnpjCorretora) {
        this.cnpjCorretora = cnpjCorretora;
    }

    public String getSusepCorretora() {
        return this.susepCorretora;
    }

    public Seguro susepCorretora(String susepCorretora) {
        this.susepCorretora = susepCorretora;
        return this;
    }

    public void setSusepCorretora(String susepCorretora) {
        this.susepCorretora = susepCorretora;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public Seguro dataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataVenc() {
        return this.dataVenc;
    }

    public Seguro dataVenc(LocalDate dataVenc) {
        this.dataVenc = dataVenc;
        return this;
    }

    public void setDataVenc(LocalDate dataVenc) {
        this.dataVenc = dataVenc;
    }

    public Boolean getInativo() {
        return this.inativo;
    }

    public Seguro inativo(Boolean inativo) {
        this.inativo = inativo;
        return this;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public String getPremio() {
        return this.premio;
    }

    public Seguro premio(String premio) {
        this.premio = premio;
        return this;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public BigDecimal getTxConversao() {
        return this.txConversao;
    }

    public Seguro txConversao(BigDecimal txConversao) {
        this.txConversao = txConversao;
        return this;
    }

    public void setTxConversao(BigDecimal txConversao) {
        this.txConversao = txConversao;
    }

    public Integer getGenStatusSeguro() {
        return this.genStatusSeguro;
    }

    public Seguro genStatusSeguro(Integer genStatusSeguro) {
        this.genStatusSeguro = genStatusSeguro;
        return this;
    }

    public void setGenStatusSeguro(Integer genStatusSeguro) {
        this.genStatusSeguro = genStatusSeguro;
    }

    public LocalDate getDataAtualSeguro() {
        return this.dataAtualSeguro;
    }

    public Seguro dataAtualSeguro(LocalDate dataAtualSeguro) {
        this.dataAtualSeguro = dataAtualSeguro;
        return this;
    }

    public void setDataAtualSeguro(LocalDate dataAtualSeguro) {
        this.dataAtualSeguro = dataAtualSeguro;
    }

    public Set<Obra> getObras() {
        return this.obras;
    }

    public Seguro obras(Set<Obra> obras) {
        this.setObras(obras);
        return this;
    }

    public Seguro addObra(Obra obra) {
        this.obras.add(obra);
        obra.setSeguro(this);
        return this;
    }

    public Seguro removeObra(Obra obra) {
        this.obras.remove(obra);
        obra.setSeguro(null);
        return this;
    }

    public void setObras(Set<Obra> obras) {
        if (this.obras != null) {
            this.obras.forEach(i -> i.setSeguro(null));
        }
        if (obras != null) {
            obras.forEach(i -> i.setSeguro(this));
        }
        this.obras = obras;
    }

    public Contato getContatoSeg() {
        return this.contatoSeg;
    }

    public Seguro contatoSeg(Contato contato) {
        this.setContatoSeg(contato);
        return this;
    }

    public void setContatoSeg(Contato contato) {
        this.contatoSeg = contato;
    }

    public Contato getContatoCor() {
        return this.contatoCor;
    }

    public Seguro contatoCor(Contato contato) {
        this.setContatoCor(contato);
        return this;
    }

    public void setContatoCor(Contato contato) {
        this.contatoCor = contato;
    }

    public Moeda getMoeda() {
        return this.moeda;
    }

    public Seguro moeda(Moeda moeda) {
        this.setMoeda(moeda);
        return this;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seguro)) {
            return false;
        }
        return id != null && id.equals(((Seguro) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Seguro{" +
            "id=" + getId() +
            ", apolice='" + getApolice() + "'" +
            ", obsSeguro='" + getObsSeguro() + "'" +
            ", contratoProposta='" + getContratoProposta() + "'" +
            ", ciaSeguradora='" + getCiaSeguradora() + "'" +
            ", cnpjSeguradora='" + getCnpjSeguradora() + "'" +
            ", susepSeguradora='" + getSusepSeguradora() + "'" +
            ", corretora='" + getCorretora() + "'" +
            ", cnpjCorretora='" + getCnpjCorretora() + "'" +
            ", susepCorretora='" + getSusepCorretora() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataVenc='" + getDataVenc() + "'" +
            ", inativo='" + getInativo() + "'" +
            ", premio='" + getPremio() + "'" +
            ", txConversao=" + getTxConversao() +
            ", genStatusSeguro=" + getGenStatusSeguro() +
            ", dataAtualSeguro='" + getDataAtualSeguro() + "'" +
            "}";
    }
}
