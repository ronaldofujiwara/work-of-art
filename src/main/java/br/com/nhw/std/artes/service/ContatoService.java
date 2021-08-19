package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Contato;
import br.com.nhw.std.artes.repository.ContatoRepository;
import br.com.nhw.std.artes.service.dto.ContatoDTO;
import br.com.nhw.std.artes.service.mapper.ContatoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contato}.
 */
@Service
@Transactional
public class ContatoService {

    private final Logger log = LoggerFactory.getLogger(ContatoService.class);

    private final ContatoRepository contatoRepository;

    private final ContatoMapper contatoMapper;

    public ContatoService(ContatoRepository contatoRepository, ContatoMapper contatoMapper) {
        this.contatoRepository = contatoRepository;
        this.contatoMapper = contatoMapper;
    }

    /**
     * Save a contato.
     *
     * @param contatoDTO the entity to save.
     * @return the persisted entity.
     */
    public ContatoDTO save(ContatoDTO contatoDTO) {
        log.debug("Request to save Contato : {}", contatoDTO);
        Contato contato = contatoMapper.toEntity(contatoDTO);
        contato = contatoRepository.save(contato);
        return contatoMapper.toDto(contato);
    }

    /**
     * Partially update a contato.
     *
     * @param contatoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContatoDTO> partialUpdate(ContatoDTO contatoDTO) {
        log.debug("Request to partially update Contato : {}", contatoDTO);

        return contatoRepository
            .findById(contatoDTO.getId())
            .map(
                existingContato -> {
                    contatoMapper.partialUpdate(existingContato, contatoDTO);

                    return existingContato;
                }
            )
            .map(contatoRepository::save)
            .map(contatoMapper::toDto);
    }

    /**
     * Get all the contatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContatoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contatoes");
        return contatoRepository.findAll(pageable).map(contatoMapper::toDto);
    }

    /**
     * Get one contato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContatoDTO> findOne(Long id) {
        log.debug("Request to get Contato : {}", id);
        return contatoRepository.findById(id).map(contatoMapper::toDto);
    }

    /**
     * Delete the contato by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contato : {}", id);
        contatoRepository.deleteById(id);
    }
}
