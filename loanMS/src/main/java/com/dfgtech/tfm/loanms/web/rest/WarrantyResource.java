package com.dfgtech.tfm.loanms.web.rest;

import com.dfgtech.tfm.loanms.service.WarrantyService;
import com.dfgtech.tfm.loanms.web.rest.errors.BadRequestAlertException;
import com.dfgtech.tfm.loanms.service.dto.WarrantyDTO;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dfgtech.tfm.loanms.domain.Warranty}.
 */
@RestController
@RequestMapping("/api")
public class WarrantyResource {

    private final Logger log = LoggerFactory.getLogger(WarrantyResource.class);

    private static final String ENTITY_NAME = "loanMsWarranty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WarrantyService warrantyService;

    public WarrantyResource(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    /**
     * {@code POST  /warranties} : Create a new warranty.
     *
     * @param warrantyDTO the warrantyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warrantyDTO, or with status {@code 400 (Bad Request)} if the warranty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/warranties")
    public ResponseEntity<WarrantyDTO> createWarranty(@Valid @RequestBody WarrantyDTO warrantyDTO) throws URISyntaxException {
        log.debug("REST request to save Warranty : {}", warrantyDTO);
        if (warrantyDTO.getId() != null) {
            throw new BadRequestAlertException("A new warranty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WarrantyDTO result = warrantyService.save(warrantyDTO);
        return ResponseEntity.created(new URI("/api/warranties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code POST  /warranties} : Create a new warranty.
     *
     * @param warrantyDTO the warrantyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new warrantyDTO, or with status {@code 400 (Bad Request)} if the warranty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/warranties-masive")
    public List<WarrantyDTO> createWarrantiesMasive(@Valid @RequestBody List<WarrantyDTO> warrantyDTOs) throws URISyntaxException {
        List<WarrantyDTO> warrantiesAll = new ArrayList<WarrantyDTO>();
    	for(WarrantyDTO warrantyDTO: warrantyDTOs) {
        	log.debug("REST request to save Warranty : {}", warrantyDTO);
            if (warrantyDTO.getId() != null) {
                throw new BadRequestAlertException("A new warranty cannot already have an ID", ENTITY_NAME, "idexists");
            }
            WarrantyDTO result = warrantyService.save(warrantyDTO);
            warrantiesAll.add(result);
        }
        return warrantiesAll;
    }

    /**
     * {@code PUT  /warranties} : Updates an existing warranty.
     *
     * @param warrantyDTO the warrantyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated warrantyDTO,
     * or with status {@code 400 (Bad Request)} if the warrantyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the warrantyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/warranties")
    public ResponseEntity<WarrantyDTO> updateWarranty(@Valid @RequestBody WarrantyDTO warrantyDTO) throws URISyntaxException {
        log.debug("REST request to update Warranty : {}", warrantyDTO);
        if (warrantyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WarrantyDTO result = warrantyService.save(warrantyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, warrantyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /warranties} : get all the warranties.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of warranties in body.
     */
    @GetMapping("/warranties")
    public List<WarrantyDTO> getAllWarranties(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Warranties");
        return warrantyService.findAll();
    }

    /**
     * {@code GET  /warranties/:id} : get the "id" warranty.
     *
     * @param id the id of the warrantyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the warrantyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/warranties/{id}")
    public ResponseEntity<WarrantyDTO> getWarranty(@PathVariable Long id) {
        log.debug("REST request to get Warranty : {}", id);
        Optional<WarrantyDTO> warrantyDTO = warrantyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(warrantyDTO);
    }

    /**
     * {@code DELETE  /warranties/:id} : delete the "id" warranty.
     *
     * @param id the id of the warrantyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/warranties/{id}")
    public ResponseEntity<Void> deleteWarranty(@PathVariable Long id) {
        log.debug("REST request to delete Warranty : {}", id);
        warrantyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
