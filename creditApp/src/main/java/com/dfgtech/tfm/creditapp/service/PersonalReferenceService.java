package com.dfgtech.tfm.creditapp.service;

import com.dfgtech.tfm.creditapp.domain.PersonalReference;
import com.dfgtech.tfm.creditapp.repository.PersonalReferenceRepository;
import com.dfgtech.tfm.creditapp.service.dto.PersonalReferenceDTO;
import com.dfgtech.tfm.creditapp.service.mapper.PersonalReferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PersonalReference}.
 */
@Service
@Transactional
public class PersonalReferenceService {

    private final Logger log = LoggerFactory.getLogger(PersonalReferenceService.class);

    private final PersonalReferenceRepository personalReferenceRepository;

    private final PersonalReferenceMapper personalReferenceMapper;

    public PersonalReferenceService(PersonalReferenceRepository personalReferenceRepository, PersonalReferenceMapper personalReferenceMapper) {
        this.personalReferenceRepository = personalReferenceRepository;
        this.personalReferenceMapper = personalReferenceMapper;
    }

    /**
     * Save a personalReference.
     *
     * @param personalReferenceDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonalReferenceDTO save(PersonalReferenceDTO personalReferenceDTO) {
        log.debug("Request to save PersonalReference : {}", personalReferenceDTO);
        PersonalReference personalReference = personalReferenceMapper.toEntity(personalReferenceDTO);
        personalReference = personalReferenceRepository.save(personalReference);
        return personalReferenceMapper.toDto(personalReference);
    }

    /**
     * Get all the personalReferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonalReferenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonalReferences");
        return personalReferenceRepository.findAll(pageable)
            .map(personalReferenceMapper::toDto);
    }
    
    /**
     * Get all the personalReferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonalReferenceDTO> findAllByCustomer(Long customer, Pageable pageable) {
        log.debug("Request to get all PersonalReferences By Customer");
        return personalReferenceRepository.findByCustomerId(customer, pageable)
            .map(personalReferenceMapper::toDto);
    }


    /**
     * Get one personalReference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonalReferenceDTO> findOne(Long id) {
        log.debug("Request to get PersonalReference : {}", id);
        return personalReferenceRepository.findById(id)
            .map(personalReferenceMapper::toDto);
    }

    /**
     * Delete the personalReference by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonalReference : {}", id);
        personalReferenceRepository.deleteById(id);
    }
}
