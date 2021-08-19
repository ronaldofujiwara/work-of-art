package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.*; // for static metamodels
import br.com.nhw.std.artes.domain.AreaDepto;
import br.com.nhw.std.artes.repository.AreaDeptoRepository;
import br.com.nhw.std.artes.service.criteria.AreaDeptoCriteria;
import br.com.nhw.std.artes.service.dto.AreaDeptoDTO;
import br.com.nhw.std.artes.service.mapper.AreaDeptoMapper;
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
 * Service for executing complex queries for {@link AreaDepto} entities in the database.
 * The main input is a {@link AreaDeptoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AreaDeptoDTO} or a {@link Page} of {@link AreaDeptoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AreaDeptoQueryService extends QueryService<AreaDepto> {

    private final Logger log = LoggerFactory.getLogger(AreaDeptoQueryService.class);

    private final AreaDeptoRepository areaDeptoRepository;

    private final AreaDeptoMapper areaDeptoMapper;

    public AreaDeptoQueryService(AreaDeptoRepository areaDeptoRepository, AreaDeptoMapper areaDeptoMapper) {
        this.areaDeptoRepository = areaDeptoRepository;
        this.areaDeptoMapper = areaDeptoMapper;
    }

    /**
     * Return a {@link List} of {@link AreaDeptoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AreaDeptoDTO> findByCriteria(AreaDeptoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AreaDepto> specification = createSpecification(criteria);
        return areaDeptoMapper.toDto(areaDeptoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AreaDeptoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AreaDeptoDTO> findByCriteria(AreaDeptoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AreaDepto> specification = createSpecification(criteria);
        return areaDeptoRepository.findAll(specification, page).map(areaDeptoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AreaDeptoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AreaDepto> specification = createSpecification(criteria);
        return areaDeptoRepository.count(specification);
    }

    /**
     * Function to convert {@link AreaDeptoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AreaDepto> createSpecification(AreaDeptoCriteria criteria) {
        Specification<AreaDepto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AreaDepto_.id));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArea(), AreaDepto_.area));
            }
            if (criteria.getAtivo() != null) {
                specification = specification.and(buildSpecification(criteria.getAtivo(), AreaDepto_.ativo));
            }
            if (criteria.getContatoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getContatoId(), root -> root.join(AreaDepto_.contatoes, JoinType.LEFT).get(Contato_.id))
                    );
            }
        }
        return specification;
    }
}
