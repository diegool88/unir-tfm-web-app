package com.dfgtech.tfm.regionms.service;

import com.dfgtech.tfm.regionms.domain.StateProvince;
import com.dfgtech.tfm.regionms.repository.StateProvinceRepository;
import com.dfgtech.tfm.regionms.service.dto.StateProvinceDTO;
import com.dfgtech.tfm.regionms.service.mapper.StateProvinceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StateProvince}.
 */
@Service
@Transactional
public class StateProvinceService {

    private final Logger log = LoggerFactory.getLogger(StateProvinceService.class);

    private final StateProvinceRepository stateProvinceRepository;

    private final StateProvinceMapper stateProvinceMapper;

    public StateProvinceService(StateProvinceRepository stateProvinceRepository, StateProvinceMapper stateProvinceMapper) {
        this.stateProvinceRepository = stateProvinceRepository;
        this.stateProvinceMapper = stateProvinceMapper;
    }

    /**
     * Save a stateProvince.
     *
     * @param stateProvinceDTO the entity to save.
     * @return the persisted entity.
     */
    public StateProvinceDTO save(StateProvinceDTO stateProvinceDTO) {
        log.debug("Request to save StateProvince : {}", stateProvinceDTO);
        StateProvince stateProvince = stateProvinceMapper.toEntity(stateProvinceDTO);
        stateProvince = stateProvinceRepository.save(stateProvince);
        return stateProvinceMapper.toDto(stateProvince);
    }

    /**
     * Get all the stateProvinces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StateProvinceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StateProvinces");
        return stateProvinceRepository.findAll(pageable)
            .map(stateProvinceMapper::toDto);
    }


    /**
     * Get one stateProvince by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StateProvinceDTO> findOne(Long id) {
        log.debug("Request to get StateProvince : {}", id);
        return stateProvinceRepository.findById(id)
            .map(stateProvinceMapper::toDto);
    }

    /**
     * Delete the stateProvince by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StateProvince : {}", id);
        stateProvinceRepository.deleteById(id);
    }
}
