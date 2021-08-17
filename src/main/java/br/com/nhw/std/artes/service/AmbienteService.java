package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Ambiente;
import br.com.nhw.std.artes.repository.AmbienteRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ambiente}.
 */
@Service
@Transactional
public class AmbienteService {

    private final Logger log = LoggerFactory.getLogger(AmbienteService.class);

    private final AmbienteRepository ambienteRepository;

    public AmbienteService(AmbienteRepository ambienteRepository) {
        this.ambienteRepository = ambienteRepository;
    }

    /**
     * Save a ambiente.
     *
     * @param ambiente the entity to save.
     * @return the persisted entity.
     */
    public Ambiente save(Ambiente ambiente) {
        log.debug("Request to save Ambiente : {}", ambiente);
        return ambienteRepository.save(ambiente);
    }

    /**
     * Partially update a ambiente.
     *
     * @param ambiente the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Ambiente> partialUpdate(Ambiente ambiente) {
        log.debug("Request to partially update Ambiente : {}", ambiente);

        return ambienteRepository
            .findById(ambiente.getId())
            .map(
                existingAmbiente -> {
                    if (ambiente.getAmbiente() != null) {
                        existingAmbiente.setAmbiente(ambiente.getAmbiente());
                    }
                    if (ambiente.getAtivo() != null) {
                        existingAmbiente.setAtivo(ambiente.getAtivo());
                    }

                    return existingAmbiente;
                }
            )
            .map(ambienteRepository::save);
    }

    /**
     * Get all the ambientes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Ambiente> findAll() {
        log.debug("Request to get all Ambientes");
        return ambienteRepository.findAll();
    }

    /**
     * Get one ambiente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ambiente> findOne(Long id) {
        log.debug("Request to get Ambiente : {}", id);
        return ambienteRepository.findById(id);
    }

    /**
     * Delete the ambiente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ambiente : {}", id);
        ambienteRepository.deleteById(id);
    }
}
