package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Ambiente;
import br.com.nhw.std.artes.repository.AmbienteRepository;
import br.com.nhw.std.artes.service.dto.AmbienteDTO;
import br.com.nhw.std.artes.service.mapper.AmbienteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ambiente}.
 */
@Service
@Transactional
public class AmbienteService {

    private final Logger log = LoggerFactory.getLogger(AmbienteService.class);

    private final AmbienteRepository ambienteRepository;

    private final AmbienteMapper ambienteMapper;

    public AmbienteService(AmbienteRepository ambienteRepository, AmbienteMapper ambienteMapper) {
        this.ambienteRepository = ambienteRepository;
        this.ambienteMapper = ambienteMapper;
    }

    /**
     * Save a ambiente.
     *
     * @param ambienteDTO the entity to save.
     * @return the persisted entity.
     */
    public AmbienteDTO save(AmbienteDTO ambienteDTO) {
        log.debug("Request to save Ambiente : {}", ambienteDTO);
        Ambiente ambiente = ambienteMapper.toEntity(ambienteDTO);
        ambiente = ambienteRepository.save(ambiente);
        return ambienteMapper.toDto(ambiente);
    }

    /**
     * Partially update a ambiente.
     *
     * @param ambienteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AmbienteDTO> partialUpdate(AmbienteDTO ambienteDTO) {
        log.debug("Request to partially update Ambiente : {}", ambienteDTO);

        return ambienteRepository
            .findById(ambienteDTO.getId())
            .map(
                existingAmbiente -> {
                    ambienteMapper.partialUpdate(existingAmbiente, ambienteDTO);

                    return existingAmbiente;
                }
            )
            .map(ambienteRepository::save)
            .map(ambienteMapper::toDto);
    }

    /**
     * Get all the ambientes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AmbienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ambientes");
        return ambienteRepository.findAll(pageable).map(ambienteMapper::toDto);
    }

    /**
     * Get one ambiente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AmbienteDTO> findOne(Long id) {
        log.debug("Request to get Ambiente : {}", id);
        return ambienteRepository.findById(id).map(ambienteMapper::toDto);
    }

    /**
     * Delete the ambiente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ambiente : {}", id);
        ambienteRepository.deleteById(id);
    }
}
