package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Responsavel;
import br.com.nhw.std.artes.repository.ResponsavelRepository;
import br.com.nhw.std.artes.service.dto.ResponsavelDTO;
import br.com.nhw.std.artes.service.mapper.ResponsavelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Responsavel}.
 */
@Service
@Transactional
public class ResponsavelService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelService.class);

    private final ResponsavelRepository responsavelRepository;

    private final ResponsavelMapper responsavelMapper;

    public ResponsavelService(ResponsavelRepository responsavelRepository, ResponsavelMapper responsavelMapper) {
        this.responsavelRepository = responsavelRepository;
        this.responsavelMapper = responsavelMapper;
    }

    /**
     * Save a responsavel.
     *
     * @param responsavelDTO the entity to save.
     * @return the persisted entity.
     */
    public ResponsavelDTO save(ResponsavelDTO responsavelDTO) {
        log.debug("Request to save Responsavel : {}", responsavelDTO);
        Responsavel responsavel = responsavelMapper.toEntity(responsavelDTO);
        responsavel = responsavelRepository.save(responsavel);
        return responsavelMapper.toDto(responsavel);
    }

    /**
     * Partially update a responsavel.
     *
     * @param responsavelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ResponsavelDTO> partialUpdate(ResponsavelDTO responsavelDTO) {
        log.debug("Request to partially update Responsavel : {}", responsavelDTO);

        return responsavelRepository
            .findById(responsavelDTO.getId())
            .map(
                existingResponsavel -> {
                    responsavelMapper.partialUpdate(existingResponsavel, responsavelDTO);

                    return existingResponsavel;
                }
            )
            .map(responsavelRepository::save)
            .map(responsavelMapper::toDto);
    }

    /**
     * Get all the responsavels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResponsavelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Responsavels");
        return responsavelRepository.findAll(pageable).map(responsavelMapper::toDto);
    }

    /**
     * Get one responsavel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResponsavelDTO> findOne(Long id) {
        log.debug("Request to get Responsavel : {}", id);
        return responsavelRepository.findById(id).map(responsavelMapper::toDto);
    }

    /**
     * Delete the responsavel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Responsavel : {}", id);
        responsavelRepository.deleteById(id);
    }
}
