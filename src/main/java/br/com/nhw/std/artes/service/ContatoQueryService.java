package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.*; // for static metamodels
import br.com.nhw.std.artes.domain.Contato;
import br.com.nhw.std.artes.repository.ContatoRepository;
import br.com.nhw.std.artes.service.criteria.ContatoCriteria;
import br.com.nhw.std.artes.service.dto.ContatoDTO;
import br.com.nhw.std.artes.service.mapper.ContatoMapper;
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
 * Service for executing complex queries for {@link Contato} entities in the database.
 * The main input is a {@link ContatoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContatoDTO} or a {@link Page} of {@link ContatoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContatoQueryService extends QueryService<Contato> {

    private final Logger log = LoggerFactory.getLogger(ContatoQueryService.class);

    private final ContatoRepository contatoRepository;

    private final ContatoMapper contatoMapper;

    public ContatoQueryService(ContatoRepository contatoRepository, ContatoMapper contatoMapper) {
        this.contatoRepository = contatoRepository;
        this.contatoMapper = contatoMapper;
    }

    /**
     * Return a {@link List} of {@link ContatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContatoDTO> findByCriteria(ContatoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contato> specification = createSpecification(criteria);
        return contatoMapper.toDto(contatoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContatoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContatoDTO> findByCriteria(ContatoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contato> specification = createSpecification(criteria);
        return contatoRepository.findAll(specification, page).map(contatoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContatoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contato> specification = createSpecification(criteria);
        return contatoRepository.count(specification);
    }

    /**
     * Function to convert {@link ContatoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Contato> createSpecification(ContatoCriteria criteria) {
        Specification<Contato> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Contato_.id));
            }
            if (criteria.getNomeComp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeComp(), Contato_.nomeComp));
            }
            if (criteria.getEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmpresa(), Contato_.empresa));
            }
            if (criteria.getFuncao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFuncao(), Contato_.funcao));
            }
            if (criteria.getRg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRg(), Contato_.rg));
            }
            if (criteria.getCpf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCpf(), Contato_.cpf));
            }
            if (criteria.getInfoContato() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfoContato(), Contato_.infoContato));
            }
            if (criteria.getEndRua() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndRua(), Contato_.endRua));
            }
            if (criteria.getEndNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndNumero(), Contato_.endNumero));
            }
            if (criteria.getEndBairro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndBairro(), Contato_.endBairro));
            }
            if (criteria.getEndComplemento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndComplemento(), Contato_.endComplemento));
            }
            if (criteria.getEndCep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndCep(), Contato_.endCep));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Contato_.telefone));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Contato_.fax));
            }
            if (criteria.getCelular() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCelular(), Contato_.celular));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Contato_.email));
            }
            if (criteria.getSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSite(), Contato_.site));
            }
            if (criteria.getObservacoes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservacoes(), Contato_.observacoes));
            }
            if (criteria.getDataAtualizacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAtualizacao(), Contato_.dataAtualizacao));
            }
            if (criteria.getInativo() != null) {
                specification = specification.and(buildSpecification(criteria.getInativo(), Contato_.inativo));
            }
            if (criteria.getObraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObraId(), root -> root.join(Contato_.obras, JoinType.LEFT).get(Obra_.id))
                    );
            }
            if (criteria.getSeguroSegId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSeguroSegId(), root -> root.join(Contato_.seguroSegs, JoinType.LEFT).get(Seguro_.id))
                    );
            }
            if (criteria.getSeguroCorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getSeguroCorId(), root -> root.join(Contato_.seguroCors, JoinType.LEFT).get(Seguro_.id))
                    );
            }
            if (criteria.getAreaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAreaId(), root -> root.join(Contato_.area, JoinType.LEFT).get(AreaDepto_.id))
                    );
            }
            if (criteria.getCidadeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCidadeId(), root -> root.join(Contato_.cidade, JoinType.LEFT).get(Cidade_.id))
                    );
            }
            if (criteria.getArtistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getArtistaId(), root -> root.join(Contato_.artistas, JoinType.LEFT).get(Artista_.id))
                    );
            }
        }
        return specification;
    }
}
