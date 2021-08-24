package br.com.nhw.std.artes.service;

import br.com.nhw.std.artes.domain.Data;
import br.com.nhw.std.artes.repository.DataRepository;
import br.com.nhw.std.artes.service.dto.DataDTO;
import br.com.nhw.std.artes.service.mapper.DataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Data}.
 */
@Service
@Transactional
public class DataService {

    private final Logger log = LoggerFactory.getLogger(DataService.class);

    private final DataRepository dataRepository;

    private final DataMapper dataMapper;

    public DataService(DataRepository dataRepository, DataMapper dataMapper) {
        this.dataRepository = dataRepository;
        this.dataMapper = dataMapper;
    }

    /**
     * Save a data.
     *
     * @param dataDTO the entity to save.
     * @return the persisted entity.
     */
    public DataDTO save(DataDTO dataDTO) {
        log.debug("Request to save Data : {}", dataDTO);
        Data data = dataMapper.toEntity(dataDTO);
        data = dataRepository.save(data);
        return dataMapper.toDto(data);
    }

    /**
     * Partially update a data.
     *
     * @param dataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DataDTO> partialUpdate(DataDTO dataDTO) {
        log.debug("Request to partially update Data : {}", dataDTO);

        return dataRepository
            .findById(dataDTO.getId())
            .map(
                existingData -> {
                    dataMapper.partialUpdate(existingData, dataDTO);

                    return existingData;
                }
            )
            .map(dataRepository::save)
            .map(dataMapper::toDto);
    }

    /**
     * Get all the data.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Data");
        return dataRepository.findAll(pageable).map(dataMapper::toDto);
    }

    /**
     * Get one data by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DataDTO> findOne(Long id) {
        log.debug("Request to get Data : {}", id);
        return dataRepository.findById(id).map(dataMapper::toDto);
    }

    /**
     * Delete the data by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Data : {}", id);
        dataRepository.deleteById(id);
    }
}
