package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.AreaDepto;
import br.com.nhw.std.artes.repository.AreaDeptoRepository;
import br.com.nhw.std.artes.service.dto.AreaDeptoDTO;
import br.com.nhw.std.artes.service.mapper.AreaDeptoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AreaDepto}.
 */
@Service
@Transactional
public class AreaDeptoService {

    private final Logger log = LoggerFactory.getLogger(AreaDeptoService.class);

    private final AreaDeptoRepository areaDeptoRepository;

    private final AreaDeptoMapper areaDeptoMapper;

    public AreaDeptoService(AreaDeptoRepository areaDeptoRepository, AreaDeptoMapper areaDeptoMapper) {
        this.areaDeptoRepository = areaDeptoRepository;
        this.areaDeptoMapper = areaDeptoMapper;
    }

    /**
     * Save a areaDepto.
     *
     * @param areaDeptoDTO the entity to save.
     * @return the persisted entity.
     */
    public AreaDeptoDTO save(AreaDeptoDTO areaDeptoDTO) {
        log.debug("Request to save AreaDepto : {}", areaDeptoDTO);
        AreaDepto areaDepto = areaDeptoMapper.toEntity(areaDeptoDTO);
        areaDepto = areaDeptoRepository.save(areaDepto);
        return areaDeptoMapper.toDto(areaDepto);
    }

    /**
     * Partially update a areaDepto.
     *
     * @param areaDeptoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AreaDeptoDTO> partialUpdate(AreaDeptoDTO areaDeptoDTO) {
        log.debug("Request to partially update AreaDepto : {}", areaDeptoDTO);

        return areaDeptoRepository
            .findById(areaDeptoDTO.getId())
            .map(
                existingAreaDepto -> {
                    areaDeptoMapper.partialUpdate(existingAreaDepto, areaDeptoDTO);

                    return existingAreaDepto;
                }
            )
            .map(areaDeptoRepository::save)
            .map(areaDeptoMapper::toDto);
    }

    /**
     * Get all the areaDeptos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AreaDeptoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AreaDeptos");
        return areaDeptoRepository.findAll(pageable).map(areaDeptoMapper::toDto);
    }

    /**
     * Get one areaDepto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaDeptoDTO> findOne(Long id) {
        log.debug("Request to get AreaDepto : {}", id);
        return areaDeptoRepository.findById(id).map(areaDeptoMapper::toDto);
    }

    /**
     * Delete the areaDepto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaDepto : {}", id);
        areaDeptoRepository.deleteById(id);
    }
}
