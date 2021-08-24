package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.*; // for static metamodels
import br.com.nhw.std.artes.domain.Ambiente;
import br.com.nhw.std.artes.repository.AmbienteRepository;
import br.com.nhw.std.artes.service.criteria.AmbienteCriteria;
import br.com.nhw.std.artes.service.dto.AmbienteDTO;
import br.com.nhw.std.artes.service.mapper.AmbienteMapper;
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
 * Service for executing complex queries for {@link Ambiente} entities in the database.
 * The main input is a {@link AmbienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AmbienteDTO} or a {@link Page} of {@link AmbienteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AmbienteQueryService extends QueryService<Ambiente> {

    private final Logger log = LoggerFactory.getLogger(AmbienteQueryService.class);

    private final AmbienteRepository ambienteRepository;

    private final AmbienteMapper ambienteMapper;

    public AmbienteQueryService(AmbienteRepository ambienteRepository, AmbienteMapper ambienteMapper) {
        this.ambienteRepository = ambienteRepository;
        this.ambienteMapper = ambienteMapper;
    }

    /**
     * Return a {@link List} of {@link AmbienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AmbienteDTO> findByCriteria(AmbienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ambiente> specification = createSpecification(criteria);
        return ambienteMapper.toDto(ambienteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AmbienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AmbienteDTO> findByCriteria(AmbienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ambiente> specification = createSpecification(criteria);
        return ambienteRepository.findAll(specification, page).map(ambienteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AmbienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ambiente> specification = createSpecification(criteria);
        return ambienteRepository.count(specification);
    }

    /**
     * Function to convert {@link AmbienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Ambiente> createSpecification(AmbienteCriteria criteria) {
        Specification<Ambiente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Ambiente_.id));
            }
            if (criteria.getAmbiente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmbiente(), Ambiente_.ambiente));
            }
            if (criteria.getInativo() != null) {
                specification = specification.and(buildSpecification(criteria.getInativo(), Ambiente_.inativo));
            }
        }
        return specification;
    }
}
