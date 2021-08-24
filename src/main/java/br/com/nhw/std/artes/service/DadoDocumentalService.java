package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.DadoDocumental;
import br.com.nhw.std.artes.repository.DadoDocumentalRepository;
import br.com.nhw.std.artes.service.dto.DadoDocumentalDTO;
import br.com.nhw.std.artes.service.mapper.DadoDocumentalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DadoDocumental}.
 */
@Service
@Transactional
public class DadoDocumentalService {

    private final Logger log = LoggerFactory.getLogger(DadoDocumentalService.class);

    private final DadoDocumentalRepository dadoDocumentalRepository;

    private final DadoDocumentalMapper dadoDocumentalMapper;

    public DadoDocumentalService(DadoDocumentalRepository dadoDocumentalRepository, DadoDocumentalMapper dadoDocumentalMapper) {
        this.dadoDocumentalRepository = dadoDocumentalRepository;
        this.dadoDocumentalMapper = dadoDocumentalMapper;
    }

    /**
     * Save a dadoDocumental.
     *
     * @param dadoDocumentalDTO the entity to save.
     * @return the persisted entity.
     */
    public DadoDocumentalDTO save(DadoDocumentalDTO dadoDocumentalDTO) {
        log.debug("Request to save DadoDocumental : {}", dadoDocumentalDTO);
        DadoDocumental dadoDocumental = dadoDocumentalMapper.toEntity(dadoDocumentalDTO);
        dadoDocumental = dadoDocumentalRepository.save(dadoDocumental);
        return dadoDocumentalMapper.toDto(dadoDocumental);
    }

    /**
     * Partially update a dadoDocumental.
     *
     * @param dadoDocumentalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DadoDocumentalDTO> partialUpdate(DadoDocumentalDTO dadoDocumentalDTO) {
        log.debug("Request to partially update DadoDocumental : {}", dadoDocumentalDTO);

        return dadoDocumentalRepository
            .findById(dadoDocumentalDTO.getId())
            .map(
                existingDadoDocumental -> {
                    dadoDocumentalMapper.partialUpdate(existingDadoDocumental, dadoDocumentalDTO);

                    return existingDadoDocumental;
                }
            )
            .map(dadoDocumentalRepository::save)
            .map(dadoDocumentalMapper::toDto);
    }

    /**
     * Get all the dadoDocumentals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DadoDocumentalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DadoDocumentals");
        return dadoDocumentalRepository.findAll(pageable).map(dadoDocumentalMapper::toDto);
    }

    /**
     * Get one dadoDocumental by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DadoDocumentalDTO> findOne(Long id) {
        log.debug("Request to get DadoDocumental : {}", id);
        return dadoDocumentalRepository.findById(id).map(dadoDocumentalMapper::toDto);
    }

    /**
     * Delete the dadoDocumental by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DadoDocumental : {}", id);
        dadoDocumentalRepository.deleteById(id);
    }
}
