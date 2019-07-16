package com.dfgtech.tfm.loanms.service;

import com.dfgtech.tfm.loanms.domain.AmortizationTable;
import com.dfgtech.tfm.loanms.repository.AmortizationTableRepository;
import com.dfgtech.tfm.loanms.service.dto.AmortizationTableDTO;
import com.dfgtech.tfm.loanms.service.mapper.AmortizationTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AmortizationTable}.
 */
@Service
@Transactional
public class AmortizationTableService {

    private final Logger log = LoggerFactory.getLogger(AmortizationTableService.class);

    private final AmortizationTableRepository amortizationTableRepository;

    private final AmortizationTableMapper amortizationTableMapper;

    public AmortizationTableService(AmortizationTableRepository amortizationTableRepository, AmortizationTableMapper amortizationTableMapper) {
        this.amortizationTableRepository = amortizationTableRepository;
        this.amortizationTableMapper = amortizationTableMapper;
    }

    /**
     * Save a amortizationTable.
     *
     * @param amortizationTableDTO the entity to save.
     * @return the persisted entity.
     */
    public AmortizationTableDTO save(AmortizationTableDTO amortizationTableDTO) {
        log.debug("Request to save AmortizationTable : {}", amortizationTableDTO);
        AmortizationTable amortizationTable = amortizationTableMapper.toEntity(amortizationTableDTO);
        amortizationTable = amortizationTableRepository.save(amortizationTable);
        return amortizationTableMapper.toDto(amortizationTable);
    }

    /**
     * Get all the amortizationTables.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AmortizationTableDTO> findAll() {
        log.debug("Request to get all AmortizationTables");
        return amortizationTableRepository.findAll().stream()
            .map(amortizationTableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one amortizationTable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AmortizationTableDTO> findOne(Long id) {
        log.debug("Request to get AmortizationTable : {}", id);
        return amortizationTableRepository.findById(id)
            .map(amortizationTableMapper::toDto);
    }

    /**
     * Delete the amortizationTable by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AmortizationTable : {}", id);
        amortizationTableRepository.deleteById(id);
    }
}
