package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.*; // for static metamodels
import br.com.nhw.std.artes.domain.Artista;
import br.com.nhw.std.artes.repository.ArtistaRepository;
import br.com.nhw.std.artes.service.criteria.ArtistaCriteria;
import br.com.nhw.std.artes.service.dto.ArtistaDTO;
import br.com.nhw.std.artes.service.mapper.ArtistaMapper;
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
 * Service for executing complex queries for {@link Artista} entities in the database.
 * The main input is a {@link ArtistaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ArtistaDTO} or a {@link Page} of {@link ArtistaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ArtistaQueryService extends QueryService<Artista> {

    private final Logger log = LoggerFactory.getLogger(ArtistaQueryService.class);

    private final ArtistaRepository artistaRepository;

    private final ArtistaMapper artistaMapper;

    public ArtistaQueryService(ArtistaRepository artistaRepository, ArtistaMapper artistaMapper) {
        this.artistaRepository = artistaRepository;
        this.artistaMapper = artistaMapper;
    }

    /**
     * Return a {@link List} of {@link ArtistaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ArtistaDTO> findByCriteria(ArtistaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Artista> specification = createSpecification(criteria);
        return artistaMapper.toDto(artistaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ArtistaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArtistaDTO> findByCriteria(ArtistaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Artista> specification = createSpecification(criteria);
        return artistaRepository.findAll(specification, page).map(artistaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArtistaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Artista> specification = createSpecification(criteria);
        return artistaRepository.count(specification);
    }

    /**
     * Function to convert {@link ArtistaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Artista> createSpecification(ArtistaCriteria criteria) {
        Specification<Artista> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Artista_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Artista_.nome));
            }
            if (criteria.getDataNasc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataNasc(), Artista_.dataNasc));
            }
            if (criteria.getDataFalec() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataFalec(), Artista_.dataFalec));
            }
            if (criteria.getBiografia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBiografia(), Artista_.biografia));
            }
            if (criteria.getVerbete() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVerbete(), Artista_.verbete));
            }
            if (criteria.getDataAtualBio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAtualBio(), Artista_.dataAtualBio));
            }
            if (criteria.getDataAtualVerb() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAtualVerb(), Artista_.dataAtualVerb));
            }
            if (criteria.getPossuiBio() != null) {
                specification = specification.and(buildSpecification(criteria.getPossuiBio(), Artista_.possuiBio));
            }
            if (criteria.getPossuiVerb() != null) {
                specification = specification.and(buildSpecification(criteria.getPossuiVerb(), Artista_.possuiVerb));
            }
            if (criteria.getFonteBio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFonteBio(), Artista_.fonteBio));
            }
            if (criteria.getObrasNoAcervo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObrasNoAcervo(), Artista_.obrasNoAcervo));
            }
            if (criteria.getInativo() != null) {
                specification = specification.and(buildSpecification(criteria.getInativo(), Artista_.inativo));
            }
            if (criteria.getAgradecimentos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgradecimentos(), Artista_.agradecimentos));
            }
            if (criteria.getCopyright() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCopyright(), Artista_.copyright));
            }
            if (criteria.getObsUso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObsUso(), Artista_.obsUso));
            }
            if (criteria.getObraId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObraId(), root -> root.join(Artista_.obras, JoinType.LEFT).get(Obra_.id))
                    );
            }
            if (criteria.getContatoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getContatoId(), root -> root.join(Artista_.contatoes, JoinType.LEFT).get(Contato_.id))
                    );
            }
            if (criteria.getCidadeNascId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCidadeNascId(),
                            root -> root.join(Artista_.cidadeNasc, JoinType.LEFT).get(Cidade_.id)
                        )
                    );
            }
            if (criteria.getCidadeFalescId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCidadeFalescId(),
                            root -> root.join(Artista_.cidadeFalesc, JoinType.LEFT).get(Cidade_.id)
                        )
                    );
            }
            if (criteria.getRespVerbeteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRespVerbeteId(),
                            root -> root.join(Artista_.respVerbete, JoinType.LEFT).get(Responsavel_.id)
                        )
                    );
            }
            if (criteria.getFuncaoArtistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFuncaoArtistaId(),
                            root -> root.join(Artista_.funcaoArtista, JoinType.LEFT).get(FuncaoArtista_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
