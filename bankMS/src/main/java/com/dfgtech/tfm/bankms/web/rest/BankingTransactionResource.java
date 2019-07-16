package com.dfgtech.tfm.bankms.web.rest;

import com.dfgtech.tfm.bankms.service.BankingTransactionService;
import com.dfgtech.tfm.bankms.web.rest.errors.BadRequestAlertException;
import com.dfgtech.tfm.bankms.service.dto.BankingTransactionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dfgtech.tfm.bankms.domain.BankingTransaction}.
 */
@RestController
@RequestMapping("/api")
public class BankingTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BankingTransactionResource.class);

    private static final String ENTITY_NAME = "bankMsBankingTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankingTransactionService bankingTransactionService;

    public BankingTransactionResource(BankingTransactionService bankingTransactionService) {
        this.bankingTransactionService = bankingTransactionService;
    }

    /**
     * {@code POST  /banking-transactions} : Create a new bankingTransaction.
     *
     * @param bankingTransactionDTO the bankingTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankingTransactionDTO, or with status {@code 400 (Bad Request)} if the bankingTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banking-transactions")
    public ResponseEntity<BankingTransactionDTO> createBankingTransaction(@Valid @RequestBody BankingTransactionDTO bankingTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save BankingTransaction : {}", bankingTransactionDTO);
        if (bankingTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankingTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankingTransactionDTO result = bankingTransactionService.save(bankingTransactionDTO);
        return ResponseEntity.created(new URI("/api/banking-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banking-transactions} : Updates an existing bankingTransaction.
     *
     * @param bankingTransactionDTO the bankingTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankingTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bankingTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankingTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banking-transactions")
    public ResponseEntity<BankingTransactionDTO> updateBankingTransaction(@Valid @RequestBody BankingTransactionDTO bankingTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update BankingTransaction : {}", bankingTransactionDTO);
        if (bankingTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankingTransactionDTO result = bankingTransactionService.save(bankingTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankingTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /banking-transactions} : get all the bankingTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankingTransactions in body.
     */
    @GetMapping("/banking-transactions")
    public List<BankingTransactionDTO> getAllBankingTransactions() {
        log.debug("REST request to get all BankingTransactions");
        return bankingTransactionService.findAll();
    }

    /**
     * {@code GET  /banking-transactions/:id} : get the "id" bankingTransaction.
     *
     * @param id the id of the bankingTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankingTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banking-transactions/{id}")
    public ResponseEntity<BankingTransactionDTO> getBankingTransaction(@PathVariable Long id) {
        log.debug("REST request to get BankingTransaction : {}", id);
        Optional<BankingTransactionDTO> bankingTransactionDTO = bankingTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankingTransactionDTO);
    }

    /**
     * {@code DELETE  /banking-transactions/:id} : delete the "id" bankingTransaction.
     *
     * @param id the id of the bankingTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banking-transactions/{id}")
    public ResponseEntity<Void> deleteBankingTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BankingTransaction : {}", id);
        bankingTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
