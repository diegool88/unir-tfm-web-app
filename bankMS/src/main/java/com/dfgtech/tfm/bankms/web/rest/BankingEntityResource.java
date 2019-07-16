package com.dfgtech.tfm.bankms.web.rest;

import com.dfgtech.tfm.bankms.service.BankingEntityService;
import com.dfgtech.tfm.bankms.web.rest.errors.BadRequestAlertException;
import com.dfgtech.tfm.bankms.service.dto.BankingEntityDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dfgtech.tfm.bankms.domain.BankingEntity}.
 */
@RestController
@RequestMapping("/api")
public class BankingEntityResource {

    private final Logger log = LoggerFactory.getLogger(BankingEntityResource.class);

    private static final String ENTITY_NAME = "bankMsBankingEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankingEntityService bankingEntityService;

    public BankingEntityResource(BankingEntityService bankingEntityService) {
        this.bankingEntityService = bankingEntityService;
    }

    /**
     * {@code POST  /banking-entities} : Create a new bankingEntity.
     *
     * @param bankingEntityDTO the bankingEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankingEntityDTO, or with status {@code 400 (Bad Request)} if the bankingEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banking-entities")
    public ResponseEntity<BankingEntityDTO> createBankingEntity(@Valid @RequestBody BankingEntityDTO bankingEntityDTO) throws URISyntaxException {
        log.debug("REST request to save BankingEntity : {}", bankingEntityDTO);
        if (bankingEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankingEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankingEntityDTO result = bankingEntityService.save(bankingEntityDTO);
        return ResponseEntity.created(new URI("/api/banking-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banking-entities} : Updates an existing bankingEntity.
     *
     * @param bankingEntityDTO the bankingEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankingEntityDTO,
     * or with status {@code 400 (Bad Request)} if the bankingEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankingEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banking-entities")
    public ResponseEntity<BankingEntityDTO> updateBankingEntity(@Valid @RequestBody BankingEntityDTO bankingEntityDTO) throws URISyntaxException {
        log.debug("REST request to update BankingEntity : {}", bankingEntityDTO);
        if (bankingEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankingEntityDTO result = bankingEntityService.save(bankingEntityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankingEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /banking-entities} : get all the bankingEntities.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankingEntities in body.
     */
    @GetMapping("/banking-entities")
    public ResponseEntity<List<BankingEntityDTO>> getAllBankingEntities(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of BankingEntities");
        Page<BankingEntityDTO> page = bankingEntityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /banking-entities/:id} : get the "id" bankingEntity.
     *
     * @param id the id of the bankingEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankingEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banking-entities/{id}")
    public ResponseEntity<BankingEntityDTO> getBankingEntity(@PathVariable Long id) {
        log.debug("REST request to get BankingEntity : {}", id);
        Optional<BankingEntityDTO> bankingEntityDTO = bankingEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankingEntityDTO);
    }

    /**
     * {@code DELETE  /banking-entities/:id} : delete the "id" bankingEntity.
     *
     * @param id the id of the bankingEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banking-entities/{id}")
    public ResponseEntity<Void> deleteBankingEntity(@PathVariable Long id) {
        log.debug("REST request to delete BankingEntity : {}", id);
        bankingEntityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
