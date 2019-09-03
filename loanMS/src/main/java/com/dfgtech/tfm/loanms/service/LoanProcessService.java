package com.dfgtech.tfm.loanms.service;

import com.dfgtech.tfm.loanms.domain.LoanProcess;
import com.dfgtech.tfm.loanms.repository.LoanProcessRepository;
import com.dfgtech.tfm.loanms.service.dto.LoanProcessDTO;
import com.dfgtech.tfm.loanms.service.mapper.LoanProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LoanProcess}.
 */
@Service
@Transactional
public class LoanProcessService {

    private final Logger log = LoggerFactory.getLogger(LoanProcessService.class);

    private final LoanProcessRepository loanProcessRepository;

    private final LoanProcessMapper loanProcessMapper;

    public LoanProcessService(LoanProcessRepository loanProcessRepository, LoanProcessMapper loanProcessMapper) {
        this.loanProcessRepository = loanProcessRepository;
        this.loanProcessMapper = loanProcessMapper;
    }

    /**
     * Save a loanProcess.
     *
     * @param loanProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanProcessDTO save(LoanProcessDTO loanProcessDTO) {
        log.debug("Request to save LoanProcess : {}", loanProcessDTO);
        LoanProcess loanProcess = loanProcessMapper.toEntity(loanProcessDTO);
        loanProcess = loanProcessRepository.save(loanProcess);
        return loanProcessMapper.toDto(loanProcess);
    }

    /**
     * Get all the loanProcesses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LoanProcessDTO> findAll() {
        log.debug("Request to get all LoanProcesses");
        return loanProcessRepository.findAll().stream()
            .map(loanProcessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * Get all the loanProcesses by customer.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LoanProcessDTO> findByCustomer(String debtorIdentification, String debtorIdentificationType, String debtorCountry) {
        log.debug("Request to get all LoanProcesses by customer");
        return loanProcessRepository.findByCustomer(debtorIdentification, debtorIdentificationType, debtorCountry).stream()
            .map(loanProcessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one loanProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanProcessDTO> findOne(Long id) {
        log.debug("Request to get LoanProcess : {}", id);
        return loanProcessRepository.findById(id)
            .map(loanProcessMapper::toDto);
    }

    /**
     * Delete the loanProcess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanProcess : {}", id);
        loanProcessRepository.deleteById(id);
    }
}
