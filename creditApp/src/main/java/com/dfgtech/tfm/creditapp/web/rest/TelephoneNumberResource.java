package com.dfgtech.tfm.creditapp.web.rest;

import com.dfgtech.tfm.creditapp.security.AuthoritiesConstants;
import com.dfgtech.tfm.creditapp.security.SecurityUtils;
import com.dfgtech.tfm.creditapp.service.CustomerService;
import com.dfgtech.tfm.creditapp.service.TelephoneNumberService;
import com.dfgtech.tfm.creditapp.web.rest.errors.BadRequestAlertException;
import com.dfgtech.tfm.creditapp.service.dto.CustomerDTO;
import com.dfgtech.tfm.creditapp.service.dto.TelephoneNumberDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.dfgtech.tfm.creditapp.domain.TelephoneNumber}.
 */
@RestController
@RequestMapping("/api")
public class TelephoneNumberResource {

    private final Logger log = LoggerFactory.getLogger(TelephoneNumberResource.class);

    private static final String ENTITY_NAME = "telephoneNumber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelephoneNumberService telephoneNumberService;
    
    @Autowired
    private CustomerService customerService;

    public TelephoneNumberResource(TelephoneNumberService telephoneNumberService) {
        this.telephoneNumberService = telephoneNumberService;
    }

    /**
     * {@code POST  /telephone-numbers} : Create a new telephoneNumber.
     *
     * @param telephoneNumberDTO the telephoneNumberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telephoneNumberDTO, or with status {@code 400 (Bad Request)} if the telephoneNumber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telephone-numbers")
    public ResponseEntity<TelephoneNumberDTO> createTelephoneNumber(@Valid @RequestBody TelephoneNumberDTO telephoneNumberDTO) throws URISyntaxException {
        log.debug("REST request to save TelephoneNumber : {}", telephoneNumberDTO);
        if (telephoneNumberDTO.getId() != null) {
            throw new BadRequestAlertException("A new telephoneNumber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelephoneNumberDTO result = telephoneNumberService.save(telephoneNumberDTO);
        return ResponseEntity.created(new URI("/api/telephone-numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telephone-numbers} : Updates an existing telephoneNumber.
     *
     * @param telephoneNumberDTO the telephoneNumberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telephoneNumberDTO,
     * or with status {@code 400 (Bad Request)} if the telephoneNumberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telephoneNumberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telephone-numbers")
    public ResponseEntity<TelephoneNumberDTO> updateTelephoneNumber(@Valid @RequestBody TelephoneNumberDTO telephoneNumberDTO) throws URISyntaxException {
        log.debug("REST request to update TelephoneNumber : {}", telephoneNumberDTO);
        if (telephoneNumberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TelephoneNumberDTO result = telephoneNumberService.save(telephoneNumberDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telephoneNumberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /telephone-numbers} : get all the telephoneNumbers.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telephoneNumbers in body.
     */
    @GetMapping("/telephone-numbers")
    public ResponseEntity<List<TelephoneNumberDTO>> getAllTelephoneNumbers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TelephoneNumbers");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
	        Page<TelephoneNumberDTO> page = telephoneNumberService.findAll(pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
        	Optional<CustomerDTO> customerDTO = customerService.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        	Page<TelephoneNumberDTO> page = customerDTO.isPresent() ? telephoneNumberService.findAllByCustomer(customerDTO.get().getId(), pageable) : Page.empty();
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }

    /**
     * {@code GET  /telephone-numbers/:id} : get the "id" telephoneNumber.
     *
     * @param id the id of the telephoneNumberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telephoneNumberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telephone-numbers/{id}")
    public ResponseEntity<TelephoneNumberDTO> getTelephoneNumber(@PathVariable Long id) {
        log.debug("REST request to get TelephoneNumber : {}", id);
        Optional<TelephoneNumberDTO> telephoneNumberDTO = telephoneNumberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telephoneNumberDTO);
    }

    /**
     * {@code DELETE  /telephone-numbers/:id} : delete the "id" telephoneNumber.
     *
     * @param id the id of the telephoneNumberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telephone-numbers/{id}")
    public ResponseEntity<Void> deleteTelephoneNumber(@PathVariable Long id) {
        log.debug("REST request to delete TelephoneNumber : {}", id);
        telephoneNumberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
