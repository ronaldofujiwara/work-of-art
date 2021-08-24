package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.AndarMapa;
import br.com.nhw.std.artes.repository.AndarMapaRepository;
import br.com.nhw.std.artes.service.dto.AndarMapaDTO;
import br.com.nhw.std.artes.service.mapper.AndarMapaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AndarMapa}.
 */
@Service
@Transactional
public class AndarMapaService {

    private final Logger log = LoggerFactory.getLogger(AndarMapaService.class);

    private final AndarMapaRepository andarMapaRepository;

    private final AndarMapaMapper andarMapaMapper;

    public AndarMapaService(AndarMapaRepository andarMapaRepository, AndarMapaMapper andarMapaMapper) {
        this.andarMapaRepository = andarMapaRepository;
        this.andarMapaMapper = andarMapaMapper;
    }

    /**
     * Save a andarMapa.
     *
     * @param andarMapaDTO the entity to save.
     * @return the persisted entity.
     */
    public AndarMapaDTO save(AndarMapaDTO andarMapaDTO) {
        log.debug("Request to save AndarMapa : {}", andarMapaDTO);
        AndarMapa andarMapa = andarMapaMapper.toEntity(andarMapaDTO);
        andarMapa = andarMapaRepository.save(andarMapa);
        return andarMapaMapper.toDto(andarMapa);
    }

    /**
     * Partially update a andarMapa.
     *
     * @param andarMapaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AndarMapaDTO> partialUpdate(AndarMapaDTO andarMapaDTO) {
        log.debug("Request to partially update AndarMapa : {}", andarMapaDTO);

        return andarMapaRepository
            .findById(andarMapaDTO.getId())
            .map(
                existingAndarMapa -> {
                    andarMapaMapper.partialUpdate(existingAndarMapa, andarMapaDTO);

                    return existingAndarMapa;
                }
            )
            .map(andarMapaRepository::save)
            .map(andarMapaMapper::toDto);
    }

    /**
     * Get all the andarMapas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AndarMapaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AndarMapas");
        return andarMapaRepository.findAll(pageable).map(andarMapaMapper::toDto);
    }

    /**
     * Get one andarMapa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AndarMapaDTO> findOne(Long id) {
        log.debug("Request to get AndarMapa : {}", id);
        return andarMapaRepository.findById(id).map(andarMapaMapper::toDto);
    }

    /**
     * Delete the andarMapa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AndarMapa : {}", id);
        andarMapaRepository.deleteById(id);
    }
}
