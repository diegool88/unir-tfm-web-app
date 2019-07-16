package com.dfgtech.tfm.bankms.service;

import com.dfgtech.tfm.bankms.domain.BankingAccount;
import com.dfgtech.tfm.bankms.repository.BankingAccountRepository;
import com.dfgtech.tfm.bankms.service.dto.BankingAccountDTO;
import com.dfgtech.tfm.bankms.service.mapper.BankingAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BankingAccount}.
 */
@Service
@Transactional
public class BankingAccountService {

    private final Logger log = LoggerFactory.getLogger(BankingAccountService.class);

    private final BankingAccountRepository bankingAccountRepository;

    private final BankingAccountMapper bankingAccountMapper;

    public BankingAccountService(BankingAccountRepository bankingAccountRepository, BankingAccountMapper bankingAccountMapper) {
        this.bankingAccountRepository = bankingAccountRepository;
        this.bankingAccountMapper = bankingAccountMapper;
    }

    /**
     * Save a bankingAccount.
     *
     * @param bankingAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public BankingAccountDTO save(BankingAccountDTO bankingAccountDTO) {
        log.debug("Request to save BankingAccount : {}", bankingAccountDTO);
        BankingAccount bankingAccount = bankingAccountMapper.toEntity(bankingAccountDTO);
        bankingAccount = bankingAccountRepository.save(bankingAccount);
        return bankingAccountMapper.toDto(bankingAccount);
    }

    /**
     * Get all the bankingAccounts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BankingAccountDTO> findAll() {
        log.debug("Request to get all BankingAccounts");
        return bankingAccountRepository.findAll().stream()
            .map(bankingAccountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bankingAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankingAccountDTO> findOne(Long id) {
        log.debug("Request to get BankingAccount : {}", id);
        return bankingAccountRepository.findById(id)
            .map(bankingAccountMapper::toDto);
    }

    /**
     * Delete the bankingAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankingAccount : {}", id);
        bankingAccountRepository.deleteById(id);
    }
}
