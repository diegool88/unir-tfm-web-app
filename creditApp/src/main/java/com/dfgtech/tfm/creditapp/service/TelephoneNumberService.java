package com.dfgtech.tfm.creditapp.service;

import com.dfgtech.tfm.creditapp.domain.TelephoneNumber;
import com.dfgtech.tfm.creditapp.repository.TelephoneNumberRepository;
import com.dfgtech.tfm.creditapp.service.dto.TelephoneNumberDTO;
import com.dfgtech.tfm.creditapp.service.mapper.TelephoneNumberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TelephoneNumber}.
 */
@Service
@Transactional
public class TelephoneNumberService {

    private final Logger log = LoggerFactory.getLogger(TelephoneNumberService.class);

    private final TelephoneNumberRepository telephoneNumberRepository;

    private final TelephoneNumberMapper telephoneNumberMapper;

    public TelephoneNumberService(TelephoneNumberRepository telephoneNumberRepository, TelephoneNumberMapper telephoneNumberMapper) {
        this.telephoneNumberRepository = telephoneNumberRepository;
        this.telephoneNumberMapper = telephoneNumberMapper;
    }

    /**
     * Save a telephoneNumber.
     *
     * @param telephoneNumberDTO the entity to save.
     * @return the persisted entity.
     */
    public TelephoneNumberDTO save(TelephoneNumberDTO telephoneNumberDTO) {
        log.debug("Request to save TelephoneNumber : {}", telephoneNumberDTO);
        TelephoneNumber telephoneNumber = telephoneNumberMapper.toEntity(telephoneNumberDTO);
        telephoneNumber = telephoneNumberRepository.save(telephoneNumber);
        return telephoneNumberMapper.toDto(telephoneNumber);
    }

    /**
     * Get all the telephoneNumbers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TelephoneNumberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TelephoneNumbers");
        return telephoneNumberRepository.findAll(pageable)
            .map(telephoneNumberMapper::toDto);
    }
    
    /**
     * Get all the telephoneNumbers by Customer Id.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TelephoneNumberDTO> findAllByCustomer(Long customer, Pageable pageable) {
        log.debug("Request to get all TelephoneNumbers by customer");
        return telephoneNumberRepository.findByCustomerId(customer, pageable)
            .map(telephoneNumberMapper::toDto);
    }


    /**
     * Get one telephoneNumber by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TelephoneNumberDTO> findOne(Long id) {
        log.debug("Request to get TelephoneNumber : {}", id);
        return telephoneNumberRepository.findById(id)
            .map(telephoneNumberMapper::toDto);
    }

    /**
     * Delete the telephoneNumber by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TelephoneNumber : {}", id);
        telephoneNumberRepository.deleteById(id);
    }
}
