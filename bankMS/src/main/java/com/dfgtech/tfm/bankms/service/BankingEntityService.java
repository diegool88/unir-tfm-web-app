package com.dfgtech.tfm.bankms.service;

import com.dfgtech.tfm.bankms.domain.BankingEntity;
import com.dfgtech.tfm.bankms.repository.BankingEntityRepository;
import com.dfgtech.tfm.bankms.service.dto.BankingEntityDTO;
import com.dfgtech.tfm.bankms.service.mapper.BankingEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BankingEntity}.
 */
@Service
@Transactional
public class BankingEntityService {

    private final Logger log = LoggerFactory.getLogger(BankingEntityService.class);

    private final BankingEntityRepository bankingEntityRepository;

    private final BankingEntityMapper bankingEntityMapper;

    public BankingEntityService(BankingEntityRepository bankingEntityRepository, BankingEntityMapper bankingEntityMapper) {
        this.bankingEntityRepository = bankingEntityRepository;
        this.bankingEntityMapper = bankingEntityMapper;
    }

    /**
     * Save a bankingEntity.
     *
     * @param bankingEntityDTO the entity to save.
     * @return the persisted entity.
     */
    public BankingEntityDTO save(BankingEntityDTO bankingEntityDTO) {
        log.debug("Request to save BankingEntity : {}", bankingEntityDTO);
        BankingEntity bankingEntity = bankingEntityMapper.toEntity(bankingEntityDTO);
        bankingEntity = bankingEntityRepository.save(bankingEntity);
        return bankingEntityMapper.toDto(bankingEntity);
    }

    /**
     * Get all the bankingEntities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankingEntityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankingEntities");
        return bankingEntityRepository.findAll(pageable)
            .map(bankingEntityMapper::toDto);
    }


    /**
     * Get one bankingEntity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankingEntityDTO> findOne(Long id) {
        log.debug("Request to get BankingEntity : {}", id);
        return bankingEntityRepository.findById(id)
            .map(bankingEntityMapper::toDto);
    }
    
    /**
     * Get one bankingEntity by mnemonic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankingEntityDTO> findByMnemonic(String mnemonic) {
        log.debug("Request to get BankingEntity : {}", mnemonic);
        return bankingEntityRepository.findByMnemonic(mnemonic)
            .map(bankingEntityMapper::toDto);
    }

    /**
     * Delete the bankingEntity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankingEntity : {}", id);
        bankingEntityRepository.deleteById(id);
    }
}
