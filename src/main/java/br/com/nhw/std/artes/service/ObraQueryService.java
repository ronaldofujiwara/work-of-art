package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.*; // for static metamodels
import br.com.nhw.std.artes.domain.Obra;
import br.com.nhw.std.artes.repository.ObraRepository;
import br.com.nhw.std.artes.service.criteria.ObraCriteria;
import br.com.nhw.std.artes.service.dto.ObraDTO;
import br.com.nhw.std.artes.service.mapper.ObraMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Obra} entities in the database.
 * The main input is a {@link ObraCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ObraDTO} or a {@link Page} of {@link ObraDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ObraQueryService extends QueryService<Obra> {

    private final Logger log = LoggerFactory.getLogger(ObraQueryService.class);

    private final ObraRepository obraRepository;

    private final ObraMapper obraMapper;

    public ObraQueryService(ObraRepository obraRepository, ObraMapper obraMapper) {
        this.obraRepository = obraRepository;
        this.obraMapper = obraMapper;
    }

    /**
     * Return a {@link List} of {@link ObraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ObraDTO> findByCriteria(ObraCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Obra> specification = createSpecification(criteria);
        return obraMapper.toDto(obraRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ObraDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ObraDTO> findByCriteria(ObraCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Obra> specification = createSpecification(criteria);
        return obraRepository.findAll(specification, page).map(obraMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ObraCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Obra> specification = createSpecification(criteria);
        return obraRepository.count(specification);
    }

    /**
     * Function to convert {@link ObraCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Obra> createSpecification(ObraCriteria criteria) {
        Specification<Obra> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Obra_.id));
            }
            if (criteria.getTombo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTombo(), Obra_.tombo));
            }
            if (criteria.getMultiplo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMultiplo(), Obra_.multiplo));
            }
            if (criteria.getNumeroRegistro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroRegistro(), Obra_.numeroRegistro));
            }
            if (criteria.getOutrosTitulos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutrosTitulos(), Obra_.outrosTitulos));
            }
            if (criteria.getTituloOriginal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTituloOriginal(), Obra_.tituloOriginal));
            }
            if (criteria.getOrigem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrigem(), Obra_.origem));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), Obra_.descricao));
            }
            if (criteria.getAssinatura() != null) {
                specification = specification.and(buildSpecification(criteria.getAssinatura(), Obra_.assinatura));
            }
            if (criteria.getLocalAssinatura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalAssinatura(), Obra_.localAssinatura));
            }
            if (criteria.getMarcaInscrObra() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarcaInscrObra(), Obra_.marcaInscrObra));
            }
            if (criteria.getMarcaInscrSuporte() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarcaInscrSuporte(), Obra_.marcaInscrSuporte));
            }
            if (criteria.getMedidasAprox() != null) {
                specification = specification.and(buildSpecification(criteria.getMedidasAprox(), Obra_.medidasAprox));
            }
            if (criteria.getAlturaObra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAlturaObra(), Obra_.alturaObra));
            }
            if (criteria.getLargObra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLargObra(), Obra_.largObra));
            }
            if (criteria.getProfObra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProfObra(), Obra_.profObra));
            }
            if (criteria.getDiametrObra() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiametrObra(), Obra_.diametrObra));
            }
            if (criteria.getAlturaMold() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAlturaMold(), Obra_.alturaMold));
            }
            if (criteria.getLargMold() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLargMold(), Obra_.largMold));
            }
            if (criteria.getProfMold() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProfMold(), Obra_.profMold));
            }
            if (criteria.getDiametroMold() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiametroMold(), Obra_.diametroMold));
            }
            if (criteria.getDimensAdic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDimensAdic(), Obra_.dimensAdic));
            }
            if (criteria.getPesObra() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPesObra(), Obra_.pesObra));
            }
            if (criteria.getAtribuido() != null) {
                specification = specification.and(buildSpecification(criteria.getAtribuido(), Obra_.atribuido));
            }
            if (criteria.getnFoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnFoto(), Obra_.nFoto));
            }
            if (criteria.getConjunto() != null) {
                specification = specification.and(buildSpecification(criteria.getConjunto(), Obra_.conjunto));
            }
            if (criteria.getConjTombo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConjTombo(), Obra_.conjTombo));
            }
            if (criteria.getValorSeguro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorSeguro(), Obra_.valorSeguro));
            }
            if (criteria.getValorSeguroReal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorSeguroReal(), Obra_.valorSeguroReal));
            }
            if (criteria.getDataconversao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataconversao(), Obra_.dataconversao));
            }
            if (criteria.getDataAlterApolice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAlterApolice(), Obra_.dataAlterApolice));
            }
            if (criteria.getLocalAtual() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalAtual(), Obra_.localAtual));
            }
            if (criteria.getLocalAtualNovo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalAtualNovo(), Obra_.localAtualNovo));
            }
            if (criteria.getCodParametro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodParametro(), Obra_.codParametro));
            }
            if (criteria.getObs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObs(), Obra_.obs));
            }
            if (criteria.getTiragem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTiragem(), Obra_.tiragem));
            }
            if (criteria.getSerie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerie(), Obra_.serie));
            }
            if (criteria.getSelo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSelo(), Obra_.selo));
            }
            if (criteria.getTomboAnterio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTomboAnterio(), Obra_.tomboAnterio));
            }
            if (criteria.getVerbete() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVerbete(), Obra_.verbete));
            }
            if (criteria.getLivro() != null) {
                specification = specification.and(buildSpecification(criteria.getLivro(), Obra_.livro));
            }
            if (criteria.getImagemAlta() != null) {
                specification = specification.and(buildSpecification(criteria.getImagemAlta(), Obra_.imagemAlta));
            }
            if (criteria.getApabex() != null) {
                specification = specification.and(buildSpecification(criteria.getApabex(), Obra_.apabex));
            }
            if (criteria.getBunkyo() != null) {
                specification = specification.and(buildSpecification(criteria.getBunkyo(), Obra_.bunkyo));
            }
            if (criteria.getFaseDepuracao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFaseDepuracao(), Obra_.faseDepuracao));
            }
            if (criteria.getParaAvaliacao() != null) {
                specification = specification.and(buildSpecification(criteria.getParaAvaliacao(), Obra_.paraAvaliacao));
            }
            if (criteria.getParaRestauracao() != null) {
                specification = specification.and(buildSpecification(criteria.getParaRestauracao(), Obra_.paraRestauracao));
            }
            if (criteria.getParaMoldura() != null) {
                specification = specification.and(buildSpecification(criteria.getParaMoldura(), Obra_.paraMoldura));
            }
            if (criteria.getParaLegenda() != null) {
                specification = specification.and(buildSpecification(criteria.getParaLegenda(), Obra_.paraLegenda));
            }
            if (criteria.getTempField() != null) {
                specification = specification.and(buildSpecification(criteria.getTempField(), Obra_.tempField));
            }
            if (criteria.getSelecaoListaAvulsa() != null) {
                specification = specification.and(buildSpecification(criteria.getSelecaoListaAvulsa(), Obra_.selecaoListaAvulsa));
            }
            if (criteria.getDominioPubl() != null) {
                specification = specification.and(buildSpecification(criteria.getDominioPubl(), Obra_.dominioPubl));
            }
            if (criteria.getDtVencFoto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtVencFoto(), Obra_.dtVencFoto));
            }
            if (criteria.getObsUsoFoto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObsUsoFoto(), Obra_.obsUsoFoto));
            }
            if (criteria.getLocalFotoAlta() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalFotoAlta(), Obra_.localFotoAlta));
            }
            if (criteria.getDataInclusao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataInclusao(), Obra_.dataInclusao));
            }
            if (criteria.getDataExclusao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataExclusao(), Obra_.dataExclusao));
            }
            if (criteria.getBookX() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBookX(), Obra_.bookX));
            }
            if (criteria.getBookY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBookY(), Obra_.bookY));
            }
            if (criteria.getGenDescricao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGenDescricao(), Obra_.genDescricao));
            }
            if (criteria.getGenField1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGenField1(), Obra_.genField1));
            }
            if (criteria.getParaFotografia() != null) {
                specification = specification.and(buildSpecification(criteria.getParaFotografia(), Obra_.paraFotografia));
            }
            if (criteria.getGenMarcaInscrObra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGenMarcaInscrObra(), Obra_.genMarcaInscrObra));
            }
            if (criteria.getPalavrasChave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPalavrasChave(), Obra_.palavrasChave));
            }
            if (criteria.getGenMarcaInscrSuporte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGenMarcaInscrSuporte(), Obra_.genMarcaInscrSuporte));
            }
            if (criteria.getGenObs() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGenObs(), Obra_.genObs));
            }
            if (criteria.getGenVerbete() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGenVerbete(), Obra_.genVerbete));
            }
            if (criteria.getDadoDocumentalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDadoDocumentalId(),
                            root -> root.join(Obra_.dadoDocumentals, JoinType.LEFT).get(DadoDocumental_.id)
                        )
                    );
            }
            if (criteria.getArtistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getArtistaId(), root -> root.join(Obra_.artista, JoinType.LEFT).get(Artista_.id))
                    );
            }
            if (criteria.getCategoriaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCategoriaId(), root -> root.join(Obra_.categoria, JoinType.LEFT).get(Categoria_.id))
                    );
            }
            if (criteria.getTecnicaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTecnicaId(), root -> root.join(Obra_.tecnica, JoinType.LEFT).get(Tecnica_.id))
                    );
            }
            if (criteria.getNivelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNivelId(), root -> root.join(Obra_.nivel, JoinType.LEFT).get(Nivel_.id))
                    );
            }
            if (criteria.getDataId() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getDataId(), root -> root.join(Obra_.data, JoinType.LEFT).get(Data_.id)));
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmpresaId(), root -> root.join(Obra_.empresa, JoinType.LEFT).get(Empresa_.id))
                    );
            }
            if (criteria.getMoedaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMoedaId(), root -> root.join(Obra_.moeda, JoinType.LEFT).get(Moeda_.id))
                    );
            }
            if (criteria.getSeguroId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSeguroId(), root -> root.join(Obra_.seguro, JoinType.LEFT).get(Seguro_.id))
                    );
            }
            if (criteria.getResponsavelId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResponsavelId(),
                            root -> root.join(Obra_.responsavel, JoinType.LEFT).get(Responsavel_.id)
                        )
                    );
            }
            if (criteria.getAcervoatualId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAcervoatualId(),
                            root -> root.join(Obra_.acervoatual, JoinType.LEFT).get(AcervoAtual_.id)
                        )
                    );
            }
            if (criteria.getFotografoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getFotografoId(), root -> root.join(Obra_.fotografo, JoinType.LEFT).get(Contato_.id))
                    );
            }
            if (criteria.getAndarMapaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAndarMapaId(), root -> root.join(Obra_.andarMapa, JoinType.LEFT).get(AndarMapa_.id))
                    );
            }
        }
        return specification;
    }
}
