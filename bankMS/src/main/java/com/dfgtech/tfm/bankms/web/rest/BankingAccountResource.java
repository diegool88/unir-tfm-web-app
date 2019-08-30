package com.dfgtech.tfm.bankms.web.rest;

import com.dfgtech.tfm.bankms.external.service.CustomerServiceClient;
import com.dfgtech.tfm.bankms.external.service.dto.CustomerDTO;
import com.dfgtech.tfm.bankms.security.AuthoritiesConstants;
import com.dfgtech.tfm.bankms.security.SecurityUtils;
import com.dfgtech.tfm.bankms.service.BankingAccountService;
import com.dfgtech.tfm.bankms.web.rest.errors.BadRequestAlertException;
import com.dfgtech.tfm.bankms.service.dto.BankingAccountDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dfgtech.tfm.bankms.domain.BankingAccount}.
 */
@RestController
@RequestMapping("/api")
public class BankingAccountResource {

    private final Logger log = LoggerFactory.getLogger(BankingAccountResource.class);

    private static final String ENTITY_NAME = "bankMsBankingAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankingAccountService bankingAccountService;
    
    @Autowired
    private CustomerServiceClient customerServiceClient;

    public BankingAccountResource(BankingAccountService bankingAccountService) {
        this.bankingAccountService = bankingAccountService;
    }

    /**
     * {@code POST  /banking-accounts} : Create a new bankingAccount.
     *
     * @param bankingAccountDTO the bankingAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankingAccountDTO, or with status {@code 400 (Bad Request)} if the bankingAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banking-accounts")
    public ResponseEntity<BankingAccountDTO> createBankingAccount(@Valid @RequestBody BankingAccountDTO bankingAccountDTO) throws URISyntaxException {
        log.debug("REST request to save BankingAccount : {}", bankingAccountDTO);
        if (bankingAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankingAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankingAccountDTO result = bankingAccountService.save(bankingAccountDTO);
        return ResponseEntity.created(new URI("/api/banking-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banking-accounts} : Updates an existing bankingAccount.
     *
     * @param bankingAccountDTO the bankingAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankingAccountDTO,
     * or with status {@code 400 (Bad Request)} if the bankingAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankingAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banking-accounts")
    public ResponseEntity<BankingAccountDTO> updateBankingAccount(@Valid @RequestBody BankingAccountDTO bankingAccountDTO) throws URISyntaxException {
        log.debug("REST request to update BankingAccount : {}", bankingAccountDTO);
        if (bankingAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankingAccountDTO result = bankingAccountService.save(bankingAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankingAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /banking-accounts} : get all the bankingAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankingAccounts in body.
     */
    @GetMapping("/banking-accounts")
    public List<BankingAccountDTO> getAllBankingAccounts() {
        log.debug("REST request to get all BankingAccounts");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
        	return bankingAccountService.findAll();
        } else {
        	List<CustomerDTO> result = customerServiceClient.getLoggedCustomer();
        	return result.size() > 0 ? bankingAccountService.findByCustomer(result.get(0).getIdentificationNumber(), result.get(0).getIdentificationType().toString(), result.get(0).getCountry()) : new ArrayList<BankingAccountDTO>();
        }
    }
    
    /**
     * {@code GET  /banking-accounts} : get all the bankingAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankingAccounts in body.
     */
    @GetMapping("/banking-accounts/{customerIdentification}/{customerIdentificationType}/{customerCountry}")
    public List<BankingAccountDTO> getCustomerBankingAccounts(@PathVariable String customerIdentification, @PathVariable String customerIdentificationType, @PathVariable String customerCountry) {
        log.debug("REST request to get all Customer Accounts BankingAccounts");
        return bankingAccountService.findByCustomer(customerIdentification, customerIdentificationType, customerCountry);
    }
    
    /**
     * {@code GET  /banking-accounts/number} : get banking account by number.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankingAccounts in body.
     */
    @GetMapping("/banking-accounts/number/{number}/{accountType}/{bankingEntityMnemonic}")
    public ResponseEntity<BankingAccountDTO> getBankingAccountByNumber(@PathVariable Integer number, @PathVariable String accountType, @PathVariable String bankingEntityMnemonic) {
        log.debug("REST request to get a Customer Account BankingAccount");
        Optional<BankingAccountDTO> bankingAccountDTO = bankingAccountService.findByAccountNumber(number, accountType, bankingEntityMnemonic);
        return ResponseUtil.wrapOrNotFound(bankingAccountDTO);
    }
    
    /**
     * {@code GET  /banking-accounts} : get all the bankingAccounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankingAccounts in body.
     */
    @GetMapping("/banking-accounts/customer")
    @Timed
    public List<BankingAccountDTO> getCustomerBankingAccountsHidden() {
        log.debug("REST request to get all Customer Accounts BankingAccounts");
        //call the data microservice apis
        List<CustomerDTO> result = customerServiceClient.getLoggedCustomer();
        return result.size() > 0 ? bankingAccountService.findByCustomer(result.get(0).getIdentificationNumber(), result.get(0).getIdentificationType().toString(), result.get(0).getCountry()) : new ArrayList<BankingAccountDTO>();
    }

    /**
     * {@code GET  /banking-accounts/:id} : get the "id" bankingAccount.
     *
     * @param id the id of the bankingAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankingAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banking-accounts/{id}")
    public ResponseEntity<BankingAccountDTO> getBankingAccount(@PathVariable Long id) {
        log.debug("REST request to get BankingAccount : {}", id);
        Optional<BankingAccountDTO> bankingAccountDTO = bankingAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankingAccountDTO);
    }

    /**
     * {@code DELETE  /banking-accounts/:id} : delete the "id" bankingAccount.
     *
     * @param id the id of the bankingAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banking-accounts/{id}")
    public ResponseEntity<Void> deleteBankingAccount(@PathVariable Long id) {
        log.debug("REST request to delete BankingAccount : {}", id);
        bankingAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
