package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Cidade;
import br.com.nhw.std.artes.repository.CidadeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cidade}.
 */
@Service
@Transactional
public class CidadeService {

    private final Logger log = LoggerFactory.getLogger(CidadeService.class);

    private final CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    /**
     * Save a cidade.
     *
     * @param cidade the entity to save.
     * @return the persisted entity.
     */
    public Cidade save(Cidade cidade) {
        log.debug("Request to save Cidade : {}", cidade);
        return cidadeRepository.save(cidade);
    }

    /**
     * Partially update a cidade.
     *
     * @param cidade the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Cidade> partialUpdate(Cidade cidade) {
        log.debug("Request to partially update Cidade : {}", cidade);

        return cidadeRepository
            .findById(cidade.getId())
            .map(
                existingCidade -> {
                    if (cidade.getCidade() != null) {
                        existingCidade.setCidade(cidade.getCidade());
                    }
                    if (cidade.getEstado() != null) {
                        existingCidade.setEstado(cidade.getEstado());
                    }
                    if (cidade.getPais() != null) {
                        existingCidade.setPais(cidade.getPais());
                    }

                    return existingCidade;
                }
            )
            .map(cidadeRepository::save);
    }

    /**
     * Get all the cidades.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Cidade> findAll() {
        log.debug("Request to get all Cidades");
        return cidadeRepository.findAll();
    }

    /**
     * Get one cidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cidade> findOne(Long id) {
        log.debug("Request to get Cidade : {}", id);
        return cidadeRepository.findById(id);
    }

    /**
     * Delete the cidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cidade : {}", id);
        cidadeRepository.deleteById(id);
    }
}
