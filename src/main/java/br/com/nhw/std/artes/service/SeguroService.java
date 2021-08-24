package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Seguro;
import br.com.nhw.std.artes.repository.SeguroRepository;
import br.com.nhw.std.artes.service.dto.SeguroDTO;
import br.com.nhw.std.artes.service.mapper.SeguroMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Seguro}.
 */
@Service
@Transactional
public class SeguroService {

    private final Logger log = LoggerFactory.getLogger(SeguroService.class);

    private final SeguroRepository seguroRepository;

    private final SeguroMapper seguroMapper;

    public SeguroService(SeguroRepository seguroRepository, SeguroMapper seguroMapper) {
        this.seguroRepository = seguroRepository;
        this.seguroMapper = seguroMapper;
    }

    /**
     * Save a seguro.
     *
     * @param seguroDTO the entity to save.
     * @return the persisted entity.
     */
    public SeguroDTO save(SeguroDTO seguroDTO) {
        log.debug("Request to save Seguro : {}", seguroDTO);
        Seguro seguro = seguroMapper.toEntity(seguroDTO);
        seguro = seguroRepository.save(seguro);
        return seguroMapper.toDto(seguro);
    }

    /**
     * Partially update a seguro.
     *
     * @param seguroDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SeguroDTO> partialUpdate(SeguroDTO seguroDTO) {
        log.debug("Request to partially update Seguro : {}", seguroDTO);

        return seguroRepository
            .findById(seguroDTO.getId())
            .map(
                existingSeguro -> {
                    seguroMapper.partialUpdate(existingSeguro, seguroDTO);

                    return existingSeguro;
                }
            )
            .map(seguroRepository::save)
            .map(seguroMapper::toDto);
    }

    /**
     * Get all the seguros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SeguroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Seguros");
        return seguroRepository.findAll(pageable).map(seguroMapper::toDto);
    }

    /**
     * Get one seguro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SeguroDTO> findOne(Long id) {
        log.debug("Request to get Seguro : {}", id);
        return seguroRepository.findById(id).map(seguroMapper::toDto);
    }

    /**
     * Delete the seguro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Seguro : {}", id);
        seguroRepository.deleteById(id);
    }
}
