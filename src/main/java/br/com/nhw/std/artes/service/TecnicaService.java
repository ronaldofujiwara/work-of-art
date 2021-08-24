package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Tecnica;
import br.com.nhw.std.artes.repository.TecnicaRepository;
import br.com.nhw.std.artes.service.dto.TecnicaDTO;
import br.com.nhw.std.artes.service.mapper.TecnicaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tecnica}.
 */
@Service
@Transactional
public class TecnicaService {

    private final Logger log = LoggerFactory.getLogger(TecnicaService.class);

    private final TecnicaRepository tecnicaRepository;

    private final TecnicaMapper tecnicaMapper;

    public TecnicaService(TecnicaRepository tecnicaRepository, TecnicaMapper tecnicaMapper) {
        this.tecnicaRepository = tecnicaRepository;
        this.tecnicaMapper = tecnicaMapper;
    }

    /**
     * Save a tecnica.
     *
     * @param tecnicaDTO the entity to save.
     * @return the persisted entity.
     */
    public TecnicaDTO save(TecnicaDTO tecnicaDTO) {
        log.debug("Request to save Tecnica : {}", tecnicaDTO);
        Tecnica tecnica = tecnicaMapper.toEntity(tecnicaDTO);
        tecnica = tecnicaRepository.save(tecnica);
        return tecnicaMapper.toDto(tecnica);
    }

    /**
     * Partially update a tecnica.
     *
     * @param tecnicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TecnicaDTO> partialUpdate(TecnicaDTO tecnicaDTO) {
        log.debug("Request to partially update Tecnica : {}", tecnicaDTO);

        return tecnicaRepository
            .findById(tecnicaDTO.getId())
            .map(
                existingTecnica -> {
                    tecnicaMapper.partialUpdate(existingTecnica, tecnicaDTO);

                    return existingTecnica;
                }
            )
            .map(tecnicaRepository::save)
            .map(tecnicaMapper::toDto);
    }

    /**
     * Get all the tecnicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TecnicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tecnicas");
        return tecnicaRepository.findAll(pageable).map(tecnicaMapper::toDto);
    }

    /**
     * Get one tecnica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TecnicaDTO> findOne(Long id) {
        log.debug("Request to get Tecnica : {}", id);
        return tecnicaRepository.findById(id).map(tecnicaMapper::toDto);
    }

    /**
     * Delete the tecnica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tecnica : {}", id);
        tecnicaRepository.deleteById(id);
    }
}
