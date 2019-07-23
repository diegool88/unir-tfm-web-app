package com.dfgtech.tfm.creditapp.web.rest;

import com.dfgtech.tfm.creditapp.security.AuthoritiesConstants;
import com.dfgtech.tfm.creditapp.security.SecurityUtils;
import com.dfgtech.tfm.creditapp.service.CustomerService;
import com.dfgtech.tfm.creditapp.service.PersonalReferenceService;
import com.dfgtech.tfm.creditapp.web.rest.errors.BadRequestAlertException;
import com.dfgtech.tfm.creditapp.service.dto.CustomerDTO;
import com.dfgtech.tfm.creditapp.service.dto.PersonalReferenceDTO;

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
 * REST controller for managing {@link com.dfgtech.tfm.creditapp.domain.PersonalReference}.
 */
@RestController
@RequestMapping("/api")
public class PersonalReferenceResource {

    private final Logger log = LoggerFactory.getLogger(PersonalReferenceResource.class);

    private static final String ENTITY_NAME = "personalReference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalReferenceService personalReferenceService;
    
    @Autowired
    private CustomerService customerService;

    public PersonalReferenceResource(PersonalReferenceService personalReferenceService) {
        this.personalReferenceService = personalReferenceService;
    }

    /**
     * {@code POST  /personal-references} : Create a new personalReference.
     *
     * @param personalReferenceDTO the personalReferenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personalReferenceDTO, or with status {@code 400 (Bad Request)} if the personalReference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personal-references")
    public ResponseEntity<PersonalReferenceDTO> createPersonalReference(@Valid @RequestBody PersonalReferenceDTO personalReferenceDTO) throws URISyntaxException {
        log.debug("REST request to save PersonalReference : {}", personalReferenceDTO);
        if (personalReferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new personalReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalReferenceDTO result = personalReferenceService.save(personalReferenceDTO);
        return ResponseEntity.created(new URI("/api/personal-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personal-references} : Updates an existing personalReference.
     *
     * @param personalReferenceDTO the personalReferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalReferenceDTO,
     * or with status {@code 400 (Bad Request)} if the personalReferenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personalReferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personal-references")
    public ResponseEntity<PersonalReferenceDTO> updatePersonalReference(@Valid @RequestBody PersonalReferenceDTO personalReferenceDTO) throws URISyntaxException {
        log.debug("REST request to update PersonalReference : {}", personalReferenceDTO);
        if (personalReferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonalReferenceDTO result = personalReferenceService.save(personalReferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalReferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personal-references} : get all the personalReferences.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalReferences in body.
     */
    @GetMapping("/personal-references")
    public ResponseEntity<List<PersonalReferenceDTO>> getAllPersonalReferences(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of PersonalReferences");
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
	        Page<PersonalReferenceDTO> page = personalReferenceService.findAll(pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
        	Optional<CustomerDTO> customerDTO = customerService.findByUserLogin(SecurityUtils.getCurrentUserLogin().get());
        	Page<PersonalReferenceDTO> page = customerDTO.isPresent() ? personalReferenceService.findAllByCustomer(customerDTO.get().getId(), pageable) : Page.empty();
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
    }

    /**
     * {@code GET  /personal-references/:id} : get the "id" personalReference.
     *
     * @param id the id of the personalReferenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personalReferenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personal-references/{id}")
    public ResponseEntity<PersonalReferenceDTO> getPersonalReference(@PathVariable Long id) {
        log.debug("REST request to get PersonalReference : {}", id);
        Optional<PersonalReferenceDTO> personalReferenceDTO = personalReferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalReferenceDTO);
    }

    /**
     * {@code DELETE  /personal-references/:id} : delete the "id" personalReference.
     *
     * @param id the id of the personalReferenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personal-references/{id}")
    public ResponseEntity<Void> deletePersonalReference(@PathVariable Long id) {
        log.debug("REST request to delete PersonalReference : {}", id);
        personalReferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
