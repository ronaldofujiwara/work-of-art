package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.*; // for static metamodels
import br.com.nhw.std.artes.domain.Cidade;
import br.com.nhw.std.artes.repository.CidadeRepository;
import br.com.nhw.std.artes.service.criteria.CidadeCriteria;
import br.com.nhw.std.artes.service.dto.CidadeDTO;
import br.com.nhw.std.artes.service.mapper.CidadeMapper;
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
 * Service for executing complex queries for {@link Cidade} entities in the database.
 * The main input is a {@link CidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CidadeDTO} or a {@link Page} of {@link CidadeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CidadeQueryService extends QueryService<Cidade> {

    private final Logger log = LoggerFactory.getLogger(CidadeQueryService.class);

    private final CidadeRepository cidadeRepository;

    private final CidadeMapper cidadeMapper;

    public CidadeQueryService(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeMapper = cidadeMapper;
    }

    /**
     * Return a {@link List} of {@link CidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CidadeDTO> findByCriteria(CidadeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cidade> specification = createSpecification(criteria);
        return cidadeMapper.toDto(cidadeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CidadeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CidadeDTO> findByCriteria(CidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cidade> specification = createSpecification(criteria);
        return cidadeRepository.findAll(specification, page).map(cidadeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cidade> specification = createSpecification(criteria);
        return cidadeRepository.count(specification);
    }

    /**
     * Function to convert {@link CidadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cidade> createSpecification(CidadeCriteria criteria) {
        Specification<Cidade> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cidade_.id));
            }
            if (criteria.getCidade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCidade(), Cidade_.cidade));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), Cidade_.estado));
            }
            if (criteria.getPais() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPais(), Cidade_.pais));
            }
            if (criteria.getCidadeUFPais() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCidadeUFPais(), Cidade_.cidadeUFPais));
            }
            if (criteria.getEstadoPais() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstadoPais(), Cidade_.estadoPais));
            }
            if (criteria.getInativo() != null) {
                specification = specification.and(buildSpecification(criteria.getInativo(), Cidade_.inativo));
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmpresaId(), root -> root.join(Cidade_.empresas, JoinType.LEFT).get(Empresa_.id))
                    );
            }
            if (criteria.getArtistaNascId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getArtistaNascId(),
                            root -> root.join(Cidade_.artistaNascs, JoinType.LEFT).get(Artista_.id)
                        )
                    );
            }
            if (criteria.getArtistaFalescId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getArtistaFalescId(),
                            root -> root.join(Cidade_.artistaFalescs, JoinType.LEFT).get(Artista_.id)
                        )
                    );
            }
            if (criteria.getContatoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getContatoId(), root -> root.join(Cidade_.contatoes, JoinType.LEFT).get(Contato_.id))
                    );
            }
        }
        return specification;
    }
}
