package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Espaco;
import br.com.nhw.std.artes.repository.EspacoRepository;
import br.com.nhw.std.artes.service.dto.EspacoDTO;
import br.com.nhw.std.artes.service.mapper.EspacoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Espaco}.
 */
@Service
@Transactional
public class EspacoService {

    private final Logger log = LoggerFactory.getLogger(EspacoService.class);

    private final EspacoRepository espacoRepository;

    private final EspacoMapper espacoMapper;

    public EspacoService(EspacoRepository espacoRepository, EspacoMapper espacoMapper) {
        this.espacoRepository = espacoRepository;
        this.espacoMapper = espacoMapper;
    }

    /**
     * Save a espaco.
     *
     * @param espacoDTO the entity to save.
     * @return the persisted entity.
     */
    public EspacoDTO save(EspacoDTO espacoDTO) {
        log.debug("Request to save Espaco : {}", espacoDTO);
        Espaco espaco = espacoMapper.toEntity(espacoDTO);
        espaco = espacoRepository.save(espaco);
        return espacoMapper.toDto(espaco);
    }

    /**
     * Partially update a espaco.
     *
     * @param espacoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EspacoDTO> partialUpdate(EspacoDTO espacoDTO) {
        log.debug("Request to partially update Espaco : {}", espacoDTO);

        return espacoRepository
            .findById(espacoDTO.getId())
            .map(
                existingEspaco -> {
                    espacoMapper.partialUpdate(existingEspaco, espacoDTO);

                    return existingEspaco;
                }
            )
            .map(espacoRepository::save)
            .map(espacoMapper::toDto);
    }

    /**
     * Get all the espacos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EspacoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Espacos");
        return espacoRepository.findAll(pageable).map(espacoMapper::toDto);
    }

    /**
     * Get one espaco by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EspacoDTO> findOne(Long id) {
        log.debug("Request to get Espaco : {}", id);
        return espacoRepository.findById(id).map(espacoMapper::toDto);
    }

    /**
     * Delete the espaco by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Espaco : {}", id);
        espacoRepository.deleteById(id);
    }
}
