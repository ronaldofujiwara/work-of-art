package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Contato;
import br.com.nhw.std.artes.repository.ContatoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public ContatoService(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    /**
     * Save a contato.
     *
     * @param contato the entity to save.
     * @return the persisted entity.
     */
    public Contato save(Contato contato) {
        log.debug("Request to save Contato : {}", contato);
        return contatoRepository.save(contato);
    }

    /**
     * Partially update a contato.
     *
     * @param contato the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Contato> partialUpdate(Contato contato) {
        log.debug("Request to partially update Contato : {}", contato);

        return contatoRepository
            .findById(contato.getId())
            .map(
                existingContato -> {
                    if (contato.getNomeComp() != null) {
                        existingContato.setNomeComp(contato.getNomeComp());
                    }
                    if (contato.getEmpresa() != null) {
                        existingContato.setEmpresa(contato.getEmpresa());
                    }
                    if (contato.getFuncao() != null) {
                        existingContato.setFuncao(contato.getFuncao());
                    }
                    if (contato.getRg() != null) {
                        existingContato.setRg(contato.getRg());
                    }
                    if (contato.getCpf() != null) {
                        existingContato.setCpf(contato.getCpf());
                    }
                    if (contato.getInfoContato() != null) {
                        existingContato.setInfoContato(contato.getInfoContato());
                    }
                    if (contato.getEndRua() != null) {
                        existingContato.setEndRua(contato.getEndRua());
                    }
                    if (contato.getEndNumero() != null) {
                        existingContato.setEndNumero(contato.getEndNumero());
                    }
                    if (contato.getEndBairro() != null) {
                        existingContato.setEndBairro(contato.getEndBairro());
                    }
                    if (contato.getEndComplemento() != null) {
                        existingContato.setEndComplemento(contato.getEndComplemento());
                    }
                    if (contato.getEndCep() != null) {
                        existingContato.setEndCep(contato.getEndCep());
                    }
                    if (contato.getTelefone() != null) {
                        existingContato.setTelefone(contato.getTelefone());
                    }
                    if (contato.getFax() != null) {
                        existingContato.setFax(contato.getFax());
                    }
                    if (contato.getCelular() != null) {
                        existingContato.setCelular(contato.getCelular());
                    }
                    if (contato.getEmail() != null) {
                        existingContato.setEmail(contato.getEmail());
                    }
                    if (contato.getSite() != null) {
                        existingContato.setSite(contato.getSite());
                    }
                    if (contato.getObservacoes() != null) {
                        existingContato.setObservacoes(contato.getObservacoes());
                    }
                    if (contato.getDataAtualizacao() != null) {
                        existingContato.setDataAtualizacao(contato.getDataAtualizacao());
                    }
                    if (contato.getAtivo() != null) {
                        existingContato.setAtivo(contato.getAtivo());
                    }

                    return existingContato;
                }
            )
            .map(contatoRepository::save);
    }

    /**
     * Get all the contatoes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Contato> findAll() {
        log.debug("Request to get all Contatoes");
        return contatoRepository.findAll();
    }

    /**
     * Get one contato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Contato> findOne(Long id) {
        log.debug("Request to get Contato : {}", id);
        return contatoRepository.findById(id);
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
