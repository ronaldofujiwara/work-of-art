package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.AcervoAtual;
import br.com.nhw.std.artes.repository.AcervoAtualRepository;
import br.com.nhw.std.artes.service.dto.AcervoAtualDTO;
import br.com.nhw.std.artes.service.mapper.AcervoAtualMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AcervoAtual}.
 */
@Service
@Transactional
public class AcervoAtualService {

    private final Logger log = LoggerFactory.getLogger(AcervoAtualService.class);

    private final AcervoAtualRepository acervoAtualRepository;

    private final AcervoAtualMapper acervoAtualMapper;

    public AcervoAtualService(AcervoAtualRepository acervoAtualRepository, AcervoAtualMapper acervoAtualMapper) {
        this.acervoAtualRepository = acervoAtualRepository;
        this.acervoAtualMapper = acervoAtualMapper;
    }

    /**
     * Save a acervoAtual.
     *
     * @param acervoAtualDTO the entity to save.
     * @return the persisted entity.
     */
    public AcervoAtualDTO save(AcervoAtualDTO acervoAtualDTO) {
        log.debug("Request to save AcervoAtual : {}", acervoAtualDTO);
        AcervoAtual acervoAtual = acervoAtualMapper.toEntity(acervoAtualDTO);
        acervoAtual = acervoAtualRepository.save(acervoAtual);
        return acervoAtualMapper.toDto(acervoAtual);
    }

    /**
     * Partially update a acervoAtual.
     *
     * @param acervoAtualDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AcervoAtualDTO> partialUpdate(AcervoAtualDTO acervoAtualDTO) {
        log.debug("Request to partially update AcervoAtual : {}", acervoAtualDTO);

        return acervoAtualRepository
            .findById(acervoAtualDTO.getId())
            .map(
                existingAcervoAtual -> {
                    acervoAtualMapper.partialUpdate(existingAcervoAtual, acervoAtualDTO);

                    return existingAcervoAtual;
                }
            )
            .map(acervoAtualRepository::save)
            .map(acervoAtualMapper::toDto);
    }

    /**
     * Get all the acervoAtuals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AcervoAtualDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AcervoAtuals");
        return acervoAtualRepository.findAll(pageable).map(acervoAtualMapper::toDto);
    }

    /**
     * Get one acervoAtual by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AcervoAtualDTO> findOne(Long id) {
        log.debug("Request to get AcervoAtual : {}", id);
        return acervoAtualRepository.findById(id).map(acervoAtualMapper::toDto);
    }

    /**
     * Delete the acervoAtual by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AcervoAtual : {}", id);
        acervoAtualRepository.deleteById(id);
    }
}
