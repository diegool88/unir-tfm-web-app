package com.dfgtech.tfm.loanms.web.rest;

import com.dfgtech.tfm.loanms.external.service.CustomerServiceClient;
import com.dfgtech.tfm.loanms.external.service.dto.CustomerDTO;
import com.dfgtech.tfm.loanms.security.AuthoritiesConstants;
import com.dfgtech.tfm.loanms.security.SecurityUtils;
import com.dfgtech.tfm.loanms.service.AmortizationTableService;
import com.dfgtech.tfm.loanms.service.LoanProcessService;
import com.dfgtech.tfm.loanms.service.WarrantyService;
import com.dfgtech.tfm.loanms.web.rest.errors.BadRequestAlertException;
import com.dfgtech.tfm.loanms.service.dto.AmortizationTableDTO;
import com.dfgtech.tfm.loanms.service.dto.LoanProcessDTO;
import com.dfgtech.tfm.loanms.service.dto.LoanProcessWrapper;
import com.dfgtech.tfm.loanms.service.dto.WarrantyDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.dfgtech.tfm.loanms.domain.LoanProcess}.
 */
@RestController
@RequestMapping("/api")
public class LoanProcessResource {

    private final Logger log = LoggerFactory.getLogger(LoanProcessResource.class);

    private static final String ENTITY_NAME = "loanMsLoanProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    @Autowired
    private CustomerServiceClient customerServiceClient;
    
    @Autowired
    private AmortizationTableService amortizationTableService;
    
    @Autowired
    private WarrantyService warrantyService;

    private final LoanProcessService loanProcessService;

    public LoanProcessResource(LoanProcessService loanProcessService) {
        this.loanProcessService = loanProcessService;
    }

    /**
     * {@code POST  /loan-processes} : Create a new loanProcess.
     *
     * @param loanProcessDTO the loanProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanProcessDTO, or with status {@code 400 (Bad Request)} if the loanProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-processes")
    public ResponseEntity<LoanProcessDTO> createLoanProcess(@Valid @RequestBody LoanProcessDTO loanProcessDTO) throws URISyntaxException {
        log.debug("REST request to save LoanProcess : {}", loanProcessDTO);
        if (loanProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanProcessDTO result = loanProcessService.save(loanProcessDTO);
        return ResponseEntity.created(new URI("/api/loan-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code POST  /loan-processes} : Create a new loanProcess with all child objects.
     *
     * @param loanProcessDTO the loanProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanProcessDTO, or with status {@code 400 (Bad Request)} if the loanProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Transactional
    @PostMapping("/loan-processes-complete")
    public ResponseEntity<LoanProcessDTO> createLoanProcessComplete(@Valid @RequestBody LoanProcessWrapper loanProcessWrapper) throws URISyntaxException {
        log.debug("REST request to save LoanProcess : {}", loanProcessWrapper);
        if (loanProcessWrapper.getLoanProcess().getId() != null) {
            throw new BadRequestAlertException("A new loanProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanProcessDTO result = loanProcessService.save(loanProcessWrapper.getLoanProcess());
        for(AmortizationTableDTO amortizationEntry : loanProcessWrapper.getAmortizationSchedule()) {
        	amortizationEntry.setLoanProcessId(result.getId());
        	this.amortizationTableService.save(amortizationEntry);
        }
        for(WarrantyDTO warrantyEntry : loanProcessWrapper.getWarranties()) {
        	Set<LoanProcessDTO> loanProcessDTOSet = new HashSet<LoanProcessDTO>();
        	loanProcessDTOSet.add(result);
        	warrantyEntry.setLoanProcesses(loanProcessDTOSet);
        	this.warrantyService.save(warrantyEntry);
        }
        return ResponseEntity.created(new URI("/api/loan-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-processes} : Updates an existing loanProcess.
     *
     * @param loanProcessDTO the loanProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanProcessDTO,
     * or with status {@code 400 (Bad Request)} if the loanProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-processes")
    public ResponseEntity<LoanProcessDTO> updateLoanProcess(@Valid @RequestBody LoanProcessDTO loanProcessDTO) throws URISyntaxException {
        log.debug("REST request to update LoanProcess : {}", loanProcessDTO);
        if (loanProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoanProcessDTO result = loanProcessService.save(loanProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /loan-processes} : get all the loanProcesses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanProcesses in body.
     */
    @GetMapping("/loan-processes")
    public List<LoanProcessDTO> getAllLoanProcesses() {
        log.debug("REST request to get all LoanProcesses");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.OFFICER)) {
        	return loanProcessService.findAll();
        } else {
        	List<CustomerDTO> result = customerServiceClient.getLoggedCustomer();
        	return result.size() > 0 ? loanProcessService.findByCustomer(result.get(0).getIdentificationNumber(), result.get(0).getIdentificationType().toString(), result.get(0).getCountry()) : new ArrayList<LoanProcessDTO>();
        }
    }

    /**
     * {@code GET  /loan-processes/:id} : get the "id" loanProcess.
     *
     * @param id the id of the loanProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-processes/{id}")
    public ResponseEntity<LoanProcessDTO> getLoanProcess(@PathVariable Long id) {
        log.debug("REST request to get LoanProcess : {}", id);
        Optional<LoanProcessDTO> loanProcessDTO = loanProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanProcessDTO);
    }
    
    /**
     * {@code GET  /loan-processes/:id} : get the "id" loanProcess.
     *
     * @param id the id of the loanProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-processes-complete/{id}")
    public ResponseEntity<LoanProcessWrapper> getLoanProcessComplete(@PathVariable Long id) {
        log.debug("REST request to get LoanProcess : {}", id);
        LoanProcessWrapper loanProcessWrapper = new LoanProcessWrapper();
        Optional<LoanProcessWrapper> loanProcessWrapperOpt = Optional.empty();
        Optional<LoanProcessDTO> loanProcessDTO = loanProcessService.findOne(id);
        if(loanProcessDTO.isPresent()) {
        	loanProcessWrapper.setLoanProcess(loanProcessDTO.get());
        	List<AmortizationTableDTO> amortizationTable = this.amortizationTableService.findAllByLoanProcessId(loanProcessDTO.get().getId());
        	loanProcessWrapper.setAmortizationSchedule(amortizationTable);
        	List<WarrantyDTO> warranties = this.warrantyService.findAllWarrantiesByLoanProcessId(loanProcessDTO.get().getId());
        	loanProcessWrapper.setWarranties(warranties);
        	loanProcessWrapperOpt = Optional.of(loanProcessWrapper);
        }
        return ResponseUtil.wrapOrNotFound(loanProcessWrapperOpt);
    }

    /**
     * {@code DELETE  /loan-processes/:id} : delete the "id" loanProcess.
     *
     * @param id the id of the loanProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-processes/{id}")
    public ResponseEntity<Void> deleteLoanProcess(@PathVariable Long id) {
        log.debug("REST request to delete LoanProcess : {}", id);
        loanProcessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
