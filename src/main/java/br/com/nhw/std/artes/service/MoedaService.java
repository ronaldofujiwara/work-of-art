package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Moeda;
import br.com.nhw.std.artes.repository.MoedaRepository;
import br.com.nhw.std.artes.service.dto.MoedaDTO;
import br.com.nhw.std.artes.service.mapper.MoedaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Moeda}.
 */
@Service
@Transactional
public class MoedaService {

    private final Logger log = LoggerFactory.getLogger(MoedaService.class);

    private final MoedaRepository moedaRepository;

    private final MoedaMapper moedaMapper;

    public MoedaService(MoedaRepository moedaRepository, MoedaMapper moedaMapper) {
        this.moedaRepository = moedaRepository;
        this.moedaMapper = moedaMapper;
    }

    /**
     * Save a moeda.
     *
     * @param moedaDTO the entity to save.
     * @return the persisted entity.
     */
    public MoedaDTO save(MoedaDTO moedaDTO) {
        log.debug("Request to save Moeda : {}", moedaDTO);
        Moeda moeda = moedaMapper.toEntity(moedaDTO);
        moeda = moedaRepository.save(moeda);
        return moedaMapper.toDto(moeda);
    }

    /**
     * Partially update a moeda.
     *
     * @param moedaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MoedaDTO> partialUpdate(MoedaDTO moedaDTO) {
        log.debug("Request to partially update Moeda : {}", moedaDTO);

        return moedaRepository
            .findById(moedaDTO.getId())
            .map(
                existingMoeda -> {
                    moedaMapper.partialUpdate(existingMoeda, moedaDTO);

                    return existingMoeda;
                }
            )
            .map(moedaRepository::save)
            .map(moedaMapper::toDto);
    }

    /**
     * Get all the moedas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MoedaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Moedas");
        return moedaRepository.findAll(pageable).map(moedaMapper::toDto);
    }

    /**
     * Get one moeda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MoedaDTO> findOne(Long id) {
        log.debug("Request to get Moeda : {}", id);
        return moedaRepository.findById(id).map(moedaMapper::toDto);
    }

    /**
     * Delete the moeda by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Moeda : {}", id);
        moedaRepository.deleteById(id);
    }
}
