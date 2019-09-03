package com.dfgtech.tfm.loanms.service;

import com.dfgtech.tfm.loanms.domain.Warranty;
import com.dfgtech.tfm.loanms.repository.WarrantyRepository;
import com.dfgtech.tfm.loanms.service.dto.WarrantyDTO;
import com.dfgtech.tfm.loanms.service.mapper.WarrantyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Warranty}.
 */
@Service
@Transactional
public class WarrantyService {

    private final Logger log = LoggerFactory.getLogger(WarrantyService.class);

    private final WarrantyRepository warrantyRepository;

    private final WarrantyMapper warrantyMapper;

    public WarrantyService(WarrantyRepository warrantyRepository, WarrantyMapper warrantyMapper) {
        this.warrantyRepository = warrantyRepository;
        this.warrantyMapper = warrantyMapper;
    }

    /**
     * Save a warranty.
     *
     * @param warrantyDTO the entity to save.
     * @return the persisted entity.
     */
    public WarrantyDTO save(WarrantyDTO warrantyDTO) {
        log.debug("Request to save Warranty : {}", warrantyDTO);
        Warranty warranty = warrantyMapper.toEntity(warrantyDTO);
        warranty = warrantyRepository.save(warranty);
        return warrantyMapper.toDto(warranty);
    }

    /**
     * Get all the warranties.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WarrantyDTO> findAll() {
        log.debug("Request to get all Warranties");
        return warrantyRepository.findAllWithEagerRelationships().stream()
            .map(warrantyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * Get all the warranties by loan process id.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WarrantyDTO> findAllWarrantiesByLoanProcessId(Long id) {
        log.debug("Request to get all Warranties by loan process id");
        return warrantyRepository.findAllWarrantiesByLoanProcessId(id).stream()
            .map(warrantyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the warranties with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<WarrantyDTO> findAllWithEagerRelationships(Pageable pageable) {
        return warrantyRepository.findAllWithEagerRelationships(pageable).map(warrantyMapper::toDto);
    }
    

    /**
     * Get one warranty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WarrantyDTO> findOne(Long id) {
        log.debug("Request to get Warranty : {}", id);
        return warrantyRepository.findOneWithEagerRelationships(id)
            .map(warrantyMapper::toDto);
    }

    /**
     * Delete the warranty by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Warranty : {}", id);
        warrantyRepository.deleteById(id);
    }
}
