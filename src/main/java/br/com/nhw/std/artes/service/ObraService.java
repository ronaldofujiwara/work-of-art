package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Obra;
import br.com.nhw.std.artes.repository.ObraRepository;
import br.com.nhw.std.artes.service.dto.ObraDTO;
import br.com.nhw.std.artes.service.mapper.ObraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Obra}.
 */
@Service
@Transactional
public class ObraService {

    private final Logger log = LoggerFactory.getLogger(ObraService.class);

    private final ObraRepository obraRepository;

    private final ObraMapper obraMapper;

    public ObraService(ObraRepository obraRepository, ObraMapper obraMapper) {
        this.obraRepository = obraRepository;
        this.obraMapper = obraMapper;
    }

    /**
     * Save a obra.
     *
     * @param obraDTO the entity to save.
     * @return the persisted entity.
     */
    public ObraDTO save(ObraDTO obraDTO) {
        log.debug("Request to save Obra : {}", obraDTO);
        Obra obra = obraMapper.toEntity(obraDTO);
        obra = obraRepository.save(obra);
        return obraMapper.toDto(obra);
    }

    /**
     * Partially update a obra.
     *
     * @param obraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ObraDTO> partialUpdate(ObraDTO obraDTO) {
        log.debug("Request to partially update Obra : {}", obraDTO);

        return obraRepository
            .findById(obraDTO.getId())
            .map(
                existingObra -> {
                    obraMapper.partialUpdate(existingObra, obraDTO);

                    return existingObra;
                }
            )
            .map(obraRepository::save)
            .map(obraMapper::toDto);
    }

    /**
     * Get all the obras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ObraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Obras");
        return obraRepository.findAll(pageable).map(obraMapper::toDto);
    }

    /**
     * Get all the obras with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ObraDTO> findAllWithEagerRelationships(Pageable pageable) {
        return obraRepository.findAllWithEagerRelationships(pageable).map(obraMapper::toDto);
    }

    /**
     * Get one obra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ObraDTO> findOne(Long id) {
        log.debug("Request to get Obra : {}", id);
        return obraRepository.findOneWithEagerRelationships(id).map(obraMapper::toDto);
    }

    /**
     * Delete the obra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Obra : {}", id);
        obraRepository.deleteById(id);
    }
}
