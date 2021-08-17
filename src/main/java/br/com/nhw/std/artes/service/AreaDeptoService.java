package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.AreaDepto;
import br.com.nhw.std.artes.repository.AreaDeptoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AreaDepto}.
 */
@Service
@Transactional
public class AreaDeptoService {

    private final Logger log = LoggerFactory.getLogger(AreaDeptoService.class);

    private final AreaDeptoRepository areaDeptoRepository;

    public AreaDeptoService(AreaDeptoRepository areaDeptoRepository) {
        this.areaDeptoRepository = areaDeptoRepository;
    }

    /**
     * Save a areaDepto.
     *
     * @param areaDepto the entity to save.
     * @return the persisted entity.
     */
    public AreaDepto save(AreaDepto areaDepto) {
        log.debug("Request to save AreaDepto : {}", areaDepto);
        return areaDeptoRepository.save(areaDepto);
    }

    /**
     * Partially update a areaDepto.
     *
     * @param areaDepto the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AreaDepto> partialUpdate(AreaDepto areaDepto) {
        log.debug("Request to partially update AreaDepto : {}", areaDepto);

        return areaDeptoRepository
            .findById(areaDepto.getId())
            .map(
                existingAreaDepto -> {
                    if (areaDepto.getArea() != null) {
                        existingAreaDepto.setArea(areaDepto.getArea());
                    }
                    if (areaDepto.getAtivo() != null) {
                        existingAreaDepto.setAtivo(areaDepto.getAtivo());
                    }

                    return existingAreaDepto;
                }
            )
            .map(areaDeptoRepository::save);
    }

    /**
     * Get all the areaDeptos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AreaDepto> findAll() {
        log.debug("Request to get all AreaDeptos");
        return areaDeptoRepository.findAll();
    }

    /**
     * Get one areaDepto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaDepto> findOne(Long id) {
        log.debug("Request to get AreaDepto : {}", id);
        return areaDeptoRepository.findById(id);
    }

    /**
     * Delete the areaDepto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaDepto : {}", id);
        areaDeptoRepository.deleteById(id);
    }
}
