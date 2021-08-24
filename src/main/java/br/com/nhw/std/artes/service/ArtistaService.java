package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Artista;
import br.com.nhw.std.artes.repository.ArtistaRepository;
import br.com.nhw.std.artes.service.dto.ArtistaDTO;
import br.com.nhw.std.artes.service.mapper.ArtistaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Artista}.
 */
@Service
@Transactional
public class ArtistaService {

    private final Logger log = LoggerFactory.getLogger(ArtistaService.class);

    private final ArtistaRepository artistaRepository;

    private final ArtistaMapper artistaMapper;

    public ArtistaService(ArtistaRepository artistaRepository, ArtistaMapper artistaMapper) {
        this.artistaRepository = artistaRepository;
        this.artistaMapper = artistaMapper;
    }

    /**
     * Save a artista.
     *
     * @param artistaDTO the entity to save.
     * @return the persisted entity.
     */
    public ArtistaDTO save(ArtistaDTO artistaDTO) {
        log.debug("Request to save Artista : {}", artistaDTO);
        Artista artista = artistaMapper.toEntity(artistaDTO);
        artista = artistaRepository.save(artista);
        return artistaMapper.toDto(artista);
    }

    /**
     * Partially update a artista.
     *
     * @param artistaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ArtistaDTO> partialUpdate(ArtistaDTO artistaDTO) {
        log.debug("Request to partially update Artista : {}", artistaDTO);

        return artistaRepository
            .findById(artistaDTO.getId())
            .map(
                existingArtista -> {
                    artistaMapper.partialUpdate(existingArtista, artistaDTO);

                    return existingArtista;
                }
            )
            .map(artistaRepository::save)
            .map(artistaMapper::toDto);
    }

    /**
     * Get all the artistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ArtistaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Artistas");
        return artistaRepository.findAll(pageable).map(artistaMapper::toDto);
    }

    /**
     * Get all the artistas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ArtistaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return artistaRepository.findAllWithEagerRelationships(pageable).map(artistaMapper::toDto);
    }

    /**
     * Get one artista by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ArtistaDTO> findOne(Long id) {
        log.debug("Request to get Artista : {}", id);
        return artistaRepository.findOneWithEagerRelationships(id).map(artistaMapper::toDto);
    }

    /**
     * Delete the artista by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Artista : {}", id);
        artistaRepository.deleteById(id);
    }
}
