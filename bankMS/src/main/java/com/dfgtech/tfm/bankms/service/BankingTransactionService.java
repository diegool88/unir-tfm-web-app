package com.dfgtech.tfm.bankms.service;

import com.dfgtech.tfm.bankms.domain.BankingTransaction;
import com.dfgtech.tfm.bankms.repository.BankingTransactionRepository;
import com.dfgtech.tfm.bankms.service.dto.BankingTransactionDTO;
import com.dfgtech.tfm.bankms.service.mapper.BankingTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BankingTransaction}.
 */
@Service
@Transactional
public class BankingTransactionService {

    private final Logger log = LoggerFactory.getLogger(BankingTransactionService.class);

    private final BankingTransactionRepository bankingTransactionRepository;

    private final BankingTransactionMapper bankingTransactionMapper;

    public BankingTransactionService(BankingTransactionRepository bankingTransactionRepository, BankingTransactionMapper bankingTransactionMapper) {
        this.bankingTransactionRepository = bankingTransactionRepository;
        this.bankingTransactionMapper = bankingTransactionMapper;
    }

    /**
     * Save a bankingTransaction.
     *
     * @param bankingTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    public BankingTransactionDTO save(BankingTransactionDTO bankingTransactionDTO) {
        log.debug("Request to save BankingTransaction : {}", bankingTransactionDTO);
        BankingTransaction bankingTransaction = bankingTransactionMapper.toEntity(bankingTransactionDTO);
        bankingTransaction = bankingTransactionRepository.save(bankingTransaction);
        return bankingTransactionMapper.toDto(bankingTransaction);
    }

    /**
     * Get all the bankingTransactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankingTransactionDTO> findAll() {
        log.debug("Request to get all BankingTransactions");
        return bankingTransactionRepository.findAll().stream()
            .map(bankingTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bankingTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankingTransactionDTO> findOne(Long id) {
        log.debug("Request to get BankingTransaction : {}", id);
        return bankingTransactionRepository.findById(id)
            .map(bankingTransactionMapper::toDto);
    }

    /**
     * Delete the bankingTransaction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankingTransaction : {}", id);
        bankingTransactionRepository.deleteById(id);
    }
}
