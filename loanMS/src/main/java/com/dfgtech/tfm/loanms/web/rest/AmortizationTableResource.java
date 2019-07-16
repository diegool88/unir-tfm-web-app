package com.dfgtech.tfm.loanms.web.rest;

import com.dfgtech.tfm.loanms.service.AmortizationTableService;
import com.dfgtech.tfm.loanms.web.rest.errors.BadRequestAlertException;
import com.dfgtech.tfm.loanms.service.dto.AmortizationTableDTO;

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
 * REST controller for managing {@link com.dfgtech.tfm.loanms.domain.AmortizationTable}.
 */
@RestController
@RequestMapping("/api")
public class AmortizationTableResource {

    private final Logger log = LoggerFactory.getLogger(AmortizationTableResource.class);

    private static final String ENTITY_NAME = "loanMsAmortizationTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmortizationTableService amortizationTableService;

    public AmortizationTableResource(AmortizationTableService amortizationTableService) {
        this.amortizationTableService = amortizationTableService;
    }

    /**
     * {@code POST  /amortization-tables} : Create a new amortizationTable.
     *
     * @param amortizationTableDTO the amortizationTableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationTableDTO, or with status {@code 400 (Bad Request)} if the amortizationTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amortization-tables")
    public ResponseEntity<AmortizationTableDTO> createAmortizationTable(@Valid @RequestBody AmortizationTableDTO amortizationTableDTO) throws URISyntaxException {
        log.debug("REST request to save AmortizationTable : {}", amortizationTableDTO);
        if (amortizationTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new amortizationTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmortizationTableDTO result = amortizationTableService.save(amortizationTableDTO);
        return ResponseEntity.created(new URI("/api/amortization-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amortization-tables} : Updates an existing amortizationTable.
     *
     * @param amortizationTableDTO the amortizationTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationTableDTO,
     * or with status {@code 400 (Bad Request)} if the amortizationTableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amortizationTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-tables")
    public ResponseEntity<AmortizationTableDTO> updateAmortizationTable(@Valid @RequestBody AmortizationTableDTO amortizationTableDTO) throws URISyntaxException {
        log.debug("REST request to update AmortizationTable : {}", amortizationTableDTO);
        if (amortizationTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmortizationTableDTO result = amortizationTableService.save(amortizationTableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, amortizationTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /amortization-tables} : get all the amortizationTables.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationTables in body.
     */
    @GetMapping("/amortization-tables")
    public List<AmortizationTableDTO> getAllAmortizationTables() {
        log.debug("REST request to get all AmortizationTables");
        return amortizationTableService.findAll();
    }

    /**
     * {@code GET  /amortization-tables/:id} : get the "id" amortizationTable.
     *
     * @param id the id of the amortizationTableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationTableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-tables/{id}")
    public ResponseEntity<AmortizationTableDTO> getAmortizationTable(@PathVariable Long id) {
        log.debug("REST request to get AmortizationTable : {}", id);
        Optional<AmortizationTableDTO> amortizationTableDTO = amortizationTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amortizationTableDTO);
    }

    /**
     * {@code DELETE  /amortization-tables/:id} : delete the "id" amortizationTable.
     *
     * @param id the id of the amortizationTableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-tables/{id}")
    public ResponseEntity<Void> deleteAmortizationTable(@PathVariable Long id) {
        log.debug("REST request to delete AmortizationTable : {}", id);
        amortizationTableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
