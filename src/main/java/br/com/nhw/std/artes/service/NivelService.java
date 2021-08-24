package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Nivel;
import br.com.nhw.std.artes.repository.NivelRepository;
import br.com.nhw.std.artes.service.dto.NivelDTO;
import br.com.nhw.std.artes.service.mapper.NivelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nivel}.
 */
@Service
@Transactional
public class NivelService {

    private final Logger log = LoggerFactory.getLogger(NivelService.class);

    private final NivelRepository nivelRepository;

    private final NivelMapper nivelMapper;

    public NivelService(NivelRepository nivelRepository, NivelMapper nivelMapper) {
        this.nivelRepository = nivelRepository;
        this.nivelMapper = nivelMapper;
    }

    /**
     * Save a nivel.
     *
     * @param nivelDTO the entity to save.
     * @return the persisted entity.
     */
    public NivelDTO save(NivelDTO nivelDTO) {
        log.debug("Request to save Nivel : {}", nivelDTO);
        Nivel nivel = nivelMapper.toEntity(nivelDTO);
        nivel = nivelRepository.save(nivel);
        return nivelMapper.toDto(nivel);
    }

    /**
     * Partially update a nivel.
     *
     * @param nivelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NivelDTO> partialUpdate(NivelDTO nivelDTO) {
        log.debug("Request to partially update Nivel : {}", nivelDTO);

        return nivelRepository
            .findById(nivelDTO.getId())
            .map(
                existingNivel -> {
                    nivelMapper.partialUpdate(existingNivel, nivelDTO);

                    return existingNivel;
                }
            )
            .map(nivelRepository::save)
            .map(nivelMapper::toDto);
    }

    /**
     * Get all the nivels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NivelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Nivels");
        return nivelRepository.findAll(pageable).map(nivelMapper::toDto);
    }

    /**
     * Get one nivel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NivelDTO> findOne(Long id) {
        log.debug("Request to get Nivel : {}", id);
        return nivelRepository.findById(id).map(nivelMapper::toDto);
    }

    /**
     * Delete the nivel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Nivel : {}", id);
        nivelRepository.deleteById(id);
    }
}
