package br.com.nhw.std.artes.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link br.com.nhw.std.artes.domain.Contato} entity. This class is used
 * in {@link br.com.nhw.std.artes.web.rest.ContatoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contatoes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContatoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomeComp;

    private StringFilter empresa;

    private StringFilter funcao;

    private StringFilter rg;

    private StringFilter cpf;

    private StringFilter infoContato;

    private StringFilter endRua;

    private StringFilter endNumero;

    private StringFilter endBairro;

    private StringFilter endComplemento;

    private StringFilter endCep;

    private StringFilter telefone;

    private StringFilter fax;

    private StringFilter celular;

    private StringFilter email;

    private StringFilter site;

    private StringFilter observacoes;

    private InstantFilter dataAtualizacao;

    private BooleanFilter ativo;

    private LongFilter areaId;

    private LongFilter cidadeId;

    public ContatoCriteria() {}

    public ContatoCriteria(ContatoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomeComp = other.nomeComp == null ? null : other.nomeComp.copy();
        this.empresa = other.empresa == null ? null : other.empresa.copy();
        this.funcao = other.funcao == null ? null : other.funcao.copy();
        this.rg = other.rg == null ? null : other.rg.copy();
        this.cpf = other.cpf == null ? null : other.cpf.copy();
        this.infoContato = other.infoContato == null ? null : other.infoContato.copy();
        this.endRua = other.endRua == null ? null : other.endRua.copy();
        this.endNumero = other.endNumero == null ? null : other.endNumero.copy();
        this.endBairro = other.endBairro == null ? null : other.endBairro.copy();
        this.endComplemento = other.endComplemento == null ? null : other.endComplemento.copy();
        this.endCep = other.endCep == null ? null : other.endCep.copy();
        this.telefone = other.telefone == null ? null : other.telefone.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.celular = other.celular == null ? null : other.celular.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.site = other.site == null ? null : other.site.copy();
        this.observacoes = other.observacoes == null ? null : other.observacoes.copy();
        this.dataAtualizacao = other.dataAtualizacao == null ? null : other.dataAtualizacao.copy();
        this.ativo = other.ativo == null ? null : other.ativo.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.cidadeId = other.cidadeId == null ? null : other.cidadeId.copy();
    }

    @Override
    public ContatoCriteria copy() {
        return new ContatoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomeComp() {
        return nomeComp;
    }

    public StringFilter nomeComp() {
        if (nomeComp == null) {
            nomeComp = new StringFilter();
        }
        return nomeComp;
    }

    public void setNomeComp(StringFilter nomeComp) {
        this.nomeComp = nomeComp;
    }

    public StringFilter getEmpresa() {
        return empresa;
    }

    public StringFilter empresa() {
        if (empresa == null) {
            empresa = new StringFilter();
        }
        return empresa;
    }

    public void setEmpresa(StringFilter empresa) {
        this.empresa = empresa;
    }

    public StringFilter getFuncao() {
        return funcao;
    }

    public StringFilter funcao() {
        if (funcao == null) {
            funcao = new StringFilter();
        }
        return funcao;
    }

    public void setFuncao(StringFilter funcao) {
        this.funcao = funcao;
    }

    public StringFilter getRg() {
        return rg;
    }

    public StringFilter rg() {
        if (rg == null) {
            rg = new StringFilter();
        }
        return rg;
    }

    public void setRg(StringFilter rg) {
        this.rg = rg;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public StringFilter cpf() {
        if (cpf == null) {
            cpf = new StringFilter();
        }
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public StringFilter getInfoContato() {
        return infoContato;
    }

    public StringFilter infoContato() {
        if (infoContato == null) {
            infoContato = new StringFilter();
        }
        return infoContato;
    }

    public void setInfoContato(StringFilter infoContato) {
        this.infoContato = infoContato;
    }

    public StringFilter getEndRua() {
        return endRua;
    }

    public StringFilter endRua() {
        if (endRua == null) {
            endRua = new StringFilter();
        }
        return endRua;
    }

    public void setEndRua(StringFilter endRua) {
        this.endRua = endRua;
    }

    public StringFilter getEndNumero() {
        return endNumero;
    }

    public StringFilter endNumero() {
        if (endNumero == null) {
            endNumero = new StringFilter();
        }
        return endNumero;
    }

    public void setEndNumero(StringFilter endNumero) {
        this.endNumero = endNumero;
    }

    public StringFilter getEndBairro() {
        return endBairro;
    }

    public StringFilter endBairro() {
        if (endBairro == null) {
            endBairro = new StringFilter();
        }
        return endBairro;
    }

    public void setEndBairro(StringFilter endBairro) {
        this.endBairro = endBairro;
    }

    public StringFilter getEndComplemento() {
        return endComplemento;
    }

    public StringFilter endComplemento() {
        if (endComplemento == null) {
            endComplemento = new StringFilter();
        }
        return endComplemento;
    }

    public void setEndComplemento(StringFilter endComplemento) {
        this.endComplemento = endComplemento;
    }

    public StringFilter getEndCep() {
        return endCep;
    }

    public StringFilter endCep() {
        if (endCep == null) {
            endCep = new StringFilter();
        }
        return endCep;
    }

    public void setEndCep(StringFilter endCep) {
        this.endCep = endCep;
    }

    public StringFilter getTelefone() {
        return telefone;
    }

    public StringFilter telefone() {
        if (telefone == null) {
            telefone = new StringFilter();
        }
        return telefone;
    }

    public void setTelefone(StringFilter telefone) {
        this.telefone = telefone;
    }

    public StringFilter getFax() {
        return fax;
    }

    public StringFilter fax() {
        if (fax == null) {
            fax = new StringFilter();
        }
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getCelular() {
        return celular;
    }

    public StringFilter celular() {
        if (celular == null) {
            celular = new StringFilter();
        }
        return celular;
    }

    public void setCelular(StringFilter celular) {
        this.celular = celular;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getSite() {
        return site;
    }

    public StringFilter site() {
        if (site == null) {
            site = new StringFilter();
        }
        return site;
    }

    public void setSite(StringFilter site) {
        this.site = site;
    }

    public StringFilter getObservacoes() {
        return observacoes;
    }

    public StringFilter observacoes() {
        if (observacoes == null) {
            observacoes = new StringFilter();
        }
        return observacoes;
    }

    public void setObservacoes(StringFilter observacoes) {
        this.observacoes = observacoes;
    }

    public InstantFilter getDataAtualizacao() {
        return dataAtualizacao;
    }

    public InstantFilter dataAtualizacao() {
        if (dataAtualizacao == null) {
            dataAtualizacao = new InstantFilter();
        }
        return dataAtualizacao;
    }

    public void setDataAtualizacao(InstantFilter dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public BooleanFilter getAtivo() {
        return ativo;
    }

    public BooleanFilter ativo() {
        if (ativo == null) {
            ativo = new BooleanFilter();
        }
        return ativo;
    }

    public void setAtivo(BooleanFilter ativo) {
        this.ativo = ativo;
    }

    public LongFilter getAreaId() {
        return areaId;
    }

    public LongFilter areaId() {
        if (areaId == null) {
            areaId = new LongFilter();
        }
        return areaId;
    }

    public void setAreaId(LongFilter areaId) {
        this.areaId = areaId;
    }

    public LongFilter getCidadeId() {
        return cidadeId;
    }

    public LongFilter cidadeId() {
        if (cidadeId == null) {
            cidadeId = new LongFilter();
        }
        return cidadeId;
    }

    public void setCidadeId(LongFilter cidadeId) {
        this.cidadeId = cidadeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContatoCriteria that = (ContatoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nomeComp, that.nomeComp) &&
            Objects.equals(empresa, that.empresa) &&
            Objects.equals(funcao, that.funcao) &&
            Objects.equals(rg, that.rg) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(infoContato, that.infoContato) &&
            Objects.equals(endRua, that.endRua) &&
            Objects.equals(endNumero, that.endNumero) &&
            Objects.equals(endBairro, that.endBairro) &&
            Objects.equals(endComplemento, that.endComplemento) &&
            Objects.equals(endCep, that.endCep) &&
            Objects.equals(telefone, that.telefone) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(celular, that.celular) &&
            Objects.equals(email, that.email) &&
            Objects.equals(site, that.site) &&
            Objects.equals(observacoes, that.observacoes) &&
            Objects.equals(dataAtualizacao, that.dataAtualizacao) &&
            Objects.equals(ativo, that.ativo) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(cidadeId, that.cidadeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nomeComp,
            empresa,
            funcao,
            rg,
            cpf,
            infoContato,
            endRua,
            endNumero,
            endBairro,
            endComplemento,
            endCep,
            telefone,
            fax,
            celular,
            email,
            site,
            observacoes,
            dataAtualizacao,
            ativo,
            areaId,
            cidadeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContatoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nomeComp != null ? "nomeComp=" + nomeComp + ", " : "") +
            (empresa != null ? "empresa=" + empresa + ", " : "") +
            (funcao != null ? "funcao=" + funcao + ", " : "") +
            (rg != null ? "rg=" + rg + ", " : "") +
            (cpf != null ? "cpf=" + cpf + ", " : "") +
            (infoContato != null ? "infoContato=" + infoContato + ", " : "") +
            (endRua != null ? "endRua=" + endRua + ", " : "") +
            (endNumero != null ? "endNumero=" + endNumero + ", " : "") +
            (endBairro != null ? "endBairro=" + endBairro + ", " : "") +
            (endComplemento != null ? "endComplemento=" + endComplemento + ", " : "") +
            (endCep != null ? "endCep=" + endCep + ", " : "") +
            (telefone != null ? "telefone=" + telefone + ", " : "") +
            (fax != null ? "fax=" + fax + ", " : "") +
            (celular != null ? "celular=" + celular + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (site != null ? "site=" + site + ", " : "") +
            (observacoes != null ? "observacoes=" + observacoes + ", " : "") +
            (dataAtualizacao != null ? "dataAtualizacao=" + dataAtualizacao + ", " : "") +
            (ativo != null ? "ativo=" + ativo + ", " : "") +
            (areaId != null ? "areaId=" + areaId + ", " : "") +
            (cidadeId != null ? "cidadeId=" + cidadeId + ", " : "") +
            "}";
    }
}
