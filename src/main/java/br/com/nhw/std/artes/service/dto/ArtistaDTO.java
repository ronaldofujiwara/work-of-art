package br.com.nhw.std.artes.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link br.com.nhw.std.artes.domain.Artista} entity.
 */
@ApiModel(description = "Artistas autores das obras")
public class ArtistaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nome;

    /**
     * Nome artístico
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Nome artístico")
    private String dataNasc;

    /**
     * Data do Nascimento
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Data do Nascimento")
    private String dataFalec;

    /**
     * Data do Falecimento
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Data do Falecimento")
    private String biografia;

    /**
     * Texto memorando para biografia do artista
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Texto memorando para biografia do artista")
    private String verbete;

    /**
     * Texto memorando para verbete sobre o artista
     */
    @ApiModelProperty(value = "Texto memorando para verbete sobre o artista")
    private LocalDate dataAtualBio;

    /**
     * Data de atualização da biografia
     */
    @ApiModelProperty(value = "Data de atualização da biografia")
    private LocalDate dataAtualVerb;

    /**
     * Data de atualização do verbete
     */
    @ApiModelProperty(value = "Data de atualização do verbete")
    private Boolean possuiBio;

    /**
     * Possui biografia?
     */
    @ApiModelProperty(value = "Possui biografia?")
    private Boolean possuiVerb;

    /**
     * Possui verbete?
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Possui verbete?")
    private String fonteBio;

    /**
     * Fonte da biografia
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Fonte da biografia")
    private String obrasNoAcervo;

    /**
     * Número de Tombo das obras do artista no acervo??
     */
    @ApiModelProperty(value = "Número de Tombo das obras do artista no acervo??")
    private Boolean inativo;

    /**
     * Registro inativo?
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Registro inativo?")
    private String agradecimentos;

    /**
     * Agradecimentos (Direitos de Uso)
     */
    @Size(max = 64000)
    @ApiModelProperty(value = "Agradecimentos (Direitos de Uso)")
    private String copyright;

    /**
     * Copyright (Direitos de Uso)
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Copyright (Direitos de Uso)")
    private String obsUso;

    private Set<ContatoDTO> contatoes = new HashSet<>();

    private CidadeDTO cidadeNasc;

    private CidadeDTO cidadeFalesc;

    private ResponsavelDTO respVerbete;

    private FuncaoArtistaDTO funcaoArtista;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getDataFalec() {
        return dataFalec;
    }

    public void setDataFalec(String dataFalec) {
        this.dataFalec = dataFalec;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getVerbete() {
        return verbete;
    }

    public void setVerbete(String verbete) {
        this.verbete = verbete;
    }

    public LocalDate getDataAtualBio() {
        return dataAtualBio;
    }

    public void setDataAtualBio(LocalDate dataAtualBio) {
        this.dataAtualBio = dataAtualBio;
    }

    public LocalDate getDataAtualVerb() {
        return dataAtualVerb;
    }

    public void setDataAtualVerb(LocalDate dataAtualVerb) {
        this.dataAtualVerb = dataAtualVerb;
    }

    public Boolean getPossuiBio() {
        return possuiBio;
    }

    public void setPossuiBio(Boolean possuiBio) {
        this.possuiBio = possuiBio;
    }

    public Boolean getPossuiVerb() {
        return possuiVerb;
    }

    public void setPossuiVerb(Boolean possuiVerb) {
        this.possuiVerb = possuiVerb;
    }

    public String getFonteBio() {
        return fonteBio;
    }

    public void setFonteBio(String fonteBio) {
        this.fonteBio = fonteBio;
    }

    public String getObrasNoAcervo() {
        return obrasNoAcervo;
    }

    public void setObrasNoAcervo(String obrasNoAcervo) {
        this.obrasNoAcervo = obrasNoAcervo;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public void setInativo(Boolean inativo) {
        this.inativo = inativo;
    }

    public String getAgradecimentos() {
        return agradecimentos;
    }

    public void setAgradecimentos(String agradecimentos) {
        this.agradecimentos = agradecimentos;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getObsUso() {
        return obsUso;
    }

    public void setObsUso(String obsUso) {
        this.obsUso = obsUso;
    }

    public Set<ContatoDTO> getContatoes() {
        return contatoes;
    }

    public void setContatoes(Set<ContatoDTO> contatoes) {
        this.contatoes = contatoes;
    }

    public CidadeDTO getCidadeNasc() {
        return cidadeNasc;
    }

    public void setCidadeNasc(CidadeDTO cidadeNasc) {
        this.cidadeNasc = cidadeNasc;
    }

    public CidadeDTO getCidadeFalesc() {
        return cidadeFalesc;
    }

    public void setCidadeFalesc(CidadeDTO cidadeFalesc) {
        this.cidadeFalesc = cidadeFalesc;
    }

    public ResponsavelDTO getRespVerbete() {
        return respVerbete;
    }

    public void setRespVerbete(ResponsavelDTO respVerbete) {
        this.respVerbete = respVerbete;
    }

    public FuncaoArtistaDTO getFuncaoArtista() {
        return funcaoArtista;
    }

    public void setFuncaoArtista(FuncaoArtistaDTO funcaoArtista) {
        this.funcaoArtista = funcaoArtista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtistaDTO)) {
            return false;
        }

        ArtistaDTO artistaDTO = (ArtistaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artistaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtistaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataNasc='" + getDataNasc() + "'" +
            ", dataFalec='" + getDataFalec() + "'" +
            ", biografia='" + getBiografia() + "'" +
            ", verbete='" + getVerbete() + "'" +
            ", dataAtualBio='" + getDataAtualBio() + "'" +
            ", dataAtualVerb='" + getDataAtualVerb() + "'" +
            ", possuiBio='" + getPossuiBio() + "'" +
            ", possuiVerb='" + getPossuiVerb() + "'" +
            ", fonteBio='" + getFonteBio() + "'" +
            ", obrasNoAcervo='" + getObrasNoAcervo() + "'" +
            ", inativo='" + getInativo() + "'" +
            ", agradecimentos='" + getAgradecimentos() + "'" +
            ", copyright='" + getCopyright() + "'" +
            ", obsUso='" + getObsUso() + "'" +
            ", contatoes=" + getContatoes() +
            ", cidadeNasc=" + getCidadeNasc() +
            ", cidadeFalesc=" + getCidadeFalesc() +
            ", respVerbete=" + getRespVerbete() +
            ", funcaoArtista=" + getFuncaoArtista() +
            "}";
    }
}
