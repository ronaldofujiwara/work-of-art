package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.FuncaoArtista;
import br.com.nhw.std.artes.repository.FuncaoArtistaRepository;
import br.com.nhw.std.artes.service.dto.FuncaoArtistaDTO;
import br.com.nhw.std.artes.service.mapper.FuncaoArtistaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FuncaoArtista}.
 */
@Service
@Transactional
public class FuncaoArtistaService {

    private final Logger log = LoggerFactory.getLogger(FuncaoArtistaService.class);

    private final FuncaoArtistaRepository funcaoArtistaRepository;

    private final FuncaoArtistaMapper funcaoArtistaMapper;

    public FuncaoArtistaService(FuncaoArtistaRepository funcaoArtistaRepository, FuncaoArtistaMapper funcaoArtistaMapper) {
        this.funcaoArtistaRepository = funcaoArtistaRepository;
        this.funcaoArtistaMapper = funcaoArtistaMapper;
    }

    /**
     * Save a funcaoArtista.
     *
     * @param funcaoArtistaDTO the entity to save.
     * @return the persisted entity.
     */
    public FuncaoArtistaDTO save(FuncaoArtistaDTO funcaoArtistaDTO) {
        log.debug("Request to save FuncaoArtista : {}", funcaoArtistaDTO);
        FuncaoArtista funcaoArtista = funcaoArtistaMapper.toEntity(funcaoArtistaDTO);
        funcaoArtista = funcaoArtistaRepository.save(funcaoArtista);
        return funcaoArtistaMapper.toDto(funcaoArtista);
    }

    /**
     * Partially update a funcaoArtista.
     *
     * @param funcaoArtistaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuncaoArtistaDTO> partialUpdate(FuncaoArtistaDTO funcaoArtistaDTO) {
        log.debug("Request to partially update FuncaoArtista : {}", funcaoArtistaDTO);

        return funcaoArtistaRepository
            .findById(funcaoArtistaDTO.getId())
            .map(
                existingFuncaoArtista -> {
                    funcaoArtistaMapper.partialUpdate(existingFuncaoArtista, funcaoArtistaDTO);

                    return existingFuncaoArtista;
                }
            )
            .map(funcaoArtistaRepository::save)
            .map(funcaoArtistaMapper::toDto);
    }

    /**
     * Get all the funcaoArtistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FuncaoArtistaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FuncaoArtistas");
        return funcaoArtistaRepository.findAll(pageable).map(funcaoArtistaMapper::toDto);
    }

    /**
     * Get one funcaoArtista by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuncaoArtistaDTO> findOne(Long id) {
        log.debug("Request to get FuncaoArtista : {}", id);
        return funcaoArtistaRepository.findById(id).map(funcaoArtistaMapper::toDto);
    }

    /**
     * Delete the funcaoArtista by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FuncaoArtista : {}", id);
        funcaoArtistaRepository.deleteById(id);
    }
}
