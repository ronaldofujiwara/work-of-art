package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.TipoDocumento;
import br.com.nhw.std.artes.repository.TipoDocumentoRepository;
import br.com.nhw.std.artes.service.dto.TipoDocumentoDTO;
import br.com.nhw.std.artes.service.mapper.TipoDocumentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoDocumento}.
 */
@Service
@Transactional
public class TipoDocumentoService {

    private final Logger log = LoggerFactory.getLogger(TipoDocumentoService.class);

    private final TipoDocumentoRepository tipoDocumentoRepository;

    private final TipoDocumentoMapper tipoDocumentoMapper;

    public TipoDocumentoService(TipoDocumentoRepository tipoDocumentoRepository, TipoDocumentoMapper tipoDocumentoMapper) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.tipoDocumentoMapper = tipoDocumentoMapper;
    }

    /**
     * Save a tipoDocumento.
     *
     * @param tipoDocumentoDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoDocumentoDTO save(TipoDocumentoDTO tipoDocumentoDTO) {
        log.debug("Request to save TipoDocumento : {}", tipoDocumentoDTO);
        TipoDocumento tipoDocumento = tipoDocumentoMapper.toEntity(tipoDocumentoDTO);
        tipoDocumento = tipoDocumentoRepository.save(tipoDocumento);
        return tipoDocumentoMapper.toDto(tipoDocumento);
    }

    /**
     * Partially update a tipoDocumento.
     *
     * @param tipoDocumentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TipoDocumentoDTO> partialUpdate(TipoDocumentoDTO tipoDocumentoDTO) {
        log.debug("Request to partially update TipoDocumento : {}", tipoDocumentoDTO);

        return tipoDocumentoRepository
            .findById(tipoDocumentoDTO.getId())
            .map(
                existingTipoDocumento -> {
                    tipoDocumentoMapper.partialUpdate(existingTipoDocumento, tipoDocumentoDTO);

                    return existingTipoDocumento;
                }
            )
            .map(tipoDocumentoRepository::save)
            .map(tipoDocumentoMapper::toDto);
    }

    /**
     * Get all the tipoDocumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDocumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDocumentos");
        return tipoDocumentoRepository.findAll(pageable).map(tipoDocumentoMapper::toDto);
    }

    /**
     * Get one tipoDocumento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoDocumentoDTO> findOne(Long id) {
        log.debug("Request to get TipoDocumento : {}", id);
        return tipoDocumentoRepository.findById(id).map(tipoDocumentoMapper::toDto);
    }

    /**
     * Delete the tipoDocumento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoDocumento : {}", id);
        tipoDocumentoRepository.deleteById(id);
    }
}
