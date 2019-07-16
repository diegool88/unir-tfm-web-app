package com.dfgtech.tfm.regionms.web.rest;

import com.dfgtech.tfm.regionms.service.StateProvinceService;
import com.dfgtech.tfm.regionms.web.rest.errors.BadRequestAlertException;
import com.dfgtech.tfm.regionms.service.dto.StateProvinceDTO;

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
 * REST controller for managing {@link com.dfgtech.tfm.regionms.domain.StateProvince}.
 */
@RestController
@RequestMapping("/api")
public class StateProvinceResource {

    private final Logger log = LoggerFactory.getLogger(StateProvinceResource.class);

    private static final String ENTITY_NAME = "regionMsStateProvince";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateProvinceService stateProvinceService;

    public StateProvinceResource(StateProvinceService stateProvinceService) {
        this.stateProvinceService = stateProvinceService;
    }

    /**
     * {@code POST  /state-provinces} : Create a new stateProvince.
     *
     * @param stateProvinceDTO the stateProvinceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateProvinceDTO, or with status {@code 400 (Bad Request)} if the stateProvince has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/state-provinces")
    public ResponseEntity<StateProvinceDTO> createStateProvince(@Valid @RequestBody StateProvinceDTO stateProvinceDTO) throws URISyntaxException {
        log.debug("REST request to save StateProvince : {}", stateProvinceDTO);
        if (stateProvinceDTO.getId() != null) {
            throw new BadRequestAlertException("A new stateProvince cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateProvinceDTO result = stateProvinceService.save(stateProvinceDTO);
        return ResponseEntity.created(new URI("/api/state-provinces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-provinces} : Updates an existing stateProvince.
     *
     * @param stateProvinceDTO the stateProvinceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateProvinceDTO,
     * or with status {@code 400 (Bad Request)} if the stateProvinceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateProvinceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/state-provinces")
    public ResponseEntity<StateProvinceDTO> updateStateProvince(@Valid @RequestBody StateProvinceDTO stateProvinceDTO) throws URISyntaxException {
        log.debug("REST request to update StateProvince : {}", stateProvinceDTO);
        if (stateProvinceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StateProvinceDTO result = stateProvinceService.save(stateProvinceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateProvinceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /state-provinces} : get all the stateProvinces.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stateProvinces in body.
     */
    @GetMapping("/state-provinces")
    public ResponseEntity<List<StateProvinceDTO>> getAllStateProvinces(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of StateProvinces");
        Page<StateProvinceDTO> page = stateProvinceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /state-provinces/:id} : get the "id" stateProvince.
     *
     * @param id the id of the stateProvinceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateProvinceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/state-provinces/{id}")
    public ResponseEntity<StateProvinceDTO> getStateProvince(@PathVariable Long id) {
        log.debug("REST request to get StateProvince : {}", id);
        Optional<StateProvinceDTO> stateProvinceDTO = stateProvinceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateProvinceDTO);
    }

    /**
     * {@code DELETE  /state-provinces/:id} : delete the "id" stateProvince.
     *
     * @param id the id of the stateProvinceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/state-provinces/{id}")
    public ResponseEntity<Void> deleteStateProvince(@PathVariable Long id) {
        log.debug("REST request to delete StateProvince : {}", id);
        stateProvinceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
