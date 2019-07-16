package com.dfgtech.tfm.regionms.web.rest;

import com.dfgtech.tfm.regionms.RegionMsApp;
import com.dfgtech.tfm.regionms.domain.StateProvince;
import com.dfgtech.tfm.regionms.domain.Country;
import com.dfgtech.tfm.regionms.repository.StateProvinceRepository;
import com.dfgtech.tfm.regionms.service.StateProvinceService;
import com.dfgtech.tfm.regionms.service.dto.StateProvinceDTO;
import com.dfgtech.tfm.regionms.service.mapper.StateProvinceMapper;
import com.dfgtech.tfm.regionms.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dfgtech.tfm.regionms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link StateProvinceResource} REST controller.
 */
@SpringBootTest(classes = RegionMsApp.class)
public class StateProvinceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private StateProvinceRepository stateProvinceRepository;

    @Autowired
    private StateProvinceMapper stateProvinceMapper;

    @Autowired
    private StateProvinceService stateProvinceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStateProvinceMockMvc;

    private StateProvince stateProvince;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StateProvinceResource stateProvinceResource = new StateProvinceResource(stateProvinceService);
        this.restStateProvinceMockMvc = MockMvcBuilders.standaloneSetup(stateProvinceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateProvince createEntity(EntityManager em) {
        StateProvince stateProvince = new StateProvince()
            .name(DEFAULT_NAME);
        // Add required entity
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            country = CountryResourceIT.createEntity(em);
            em.persist(country);
            em.flush();
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        stateProvince.setCountry(country);
        return stateProvince;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateProvince createUpdatedEntity(EntityManager em) {
        StateProvince stateProvince = new StateProvince()
            .name(UPDATED_NAME);
        // Add required entity
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            country = CountryResourceIT.createUpdatedEntity(em);
            em.persist(country);
            em.flush();
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        stateProvince.setCountry(country);
        return stateProvince;
    }

    @BeforeEach
    public void initTest() {
        stateProvince = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateProvince() throws Exception {
        int databaseSizeBeforeCreate = stateProvinceRepository.findAll().size();

        // Create the StateProvince
        StateProvinceDTO stateProvinceDTO = stateProvinceMapper.toDto(stateProvince);
        restStateProvinceMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvinceDTO)))
            .andExpect(status().isCreated());

        // Validate the StateProvince in the database
        List<StateProvince> stateProvinceList = stateProvinceRepository.findAll();
        assertThat(stateProvinceList).hasSize(databaseSizeBeforeCreate + 1);
        StateProvince testStateProvince = stateProvinceList.get(stateProvinceList.size() - 1);
        assertThat(testStateProvince.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStateProvinceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateProvinceRepository.findAll().size();

        // Create the StateProvince with an existing ID
        stateProvince.setId(1L);
        StateProvinceDTO stateProvinceDTO = stateProvinceMapper.toDto(stateProvince);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateProvinceMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvinceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvince in the database
        List<StateProvince> stateProvinceList = stateProvinceRepository.findAll();
        assertThat(stateProvinceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateProvinceRepository.findAll().size();
        // set the field null
        stateProvince.setName(null);

        // Create the StateProvince, which fails.
        StateProvinceDTO stateProvinceDTO = stateProvinceMapper.toDto(stateProvince);

        restStateProvinceMockMvc.perform(post("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvinceDTO)))
            .andExpect(status().isBadRequest());

        List<StateProvince> stateProvinceList = stateProvinceRepository.findAll();
        assertThat(stateProvinceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStateProvinces() throws Exception {
        // Initialize the database
        stateProvinceRepository.saveAndFlush(stateProvince);

        // Get all the stateProvinceList
        restStateProvinceMockMvc.perform(get("/api/state-provinces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateProvince.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getStateProvince() throws Exception {
        // Initialize the database
        stateProvinceRepository.saveAndFlush(stateProvince);

        // Get the stateProvince
        restStateProvinceMockMvc.perform(get("/api/state-provinces/{id}", stateProvince.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stateProvince.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStateProvince() throws Exception {
        // Get the stateProvince
        restStateProvinceMockMvc.perform(get("/api/state-provinces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateProvince() throws Exception {
        // Initialize the database
        stateProvinceRepository.saveAndFlush(stateProvince);

        int databaseSizeBeforeUpdate = stateProvinceRepository.findAll().size();

        // Update the stateProvince
        StateProvince updatedStateProvince = stateProvinceRepository.findById(stateProvince.getId()).get();
        // Disconnect from session so that the updates on updatedStateProvince are not directly saved in db
        em.detach(updatedStateProvince);
        updatedStateProvince
            .name(UPDATED_NAME);
        StateProvinceDTO stateProvinceDTO = stateProvinceMapper.toDto(updatedStateProvince);

        restStateProvinceMockMvc.perform(put("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvinceDTO)))
            .andExpect(status().isOk());

        // Validate the StateProvince in the database
        List<StateProvince> stateProvinceList = stateProvinceRepository.findAll();
        assertThat(stateProvinceList).hasSize(databaseSizeBeforeUpdate);
        StateProvince testStateProvince = stateProvinceList.get(stateProvinceList.size() - 1);
        assertThat(testStateProvince.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStateProvince() throws Exception {
        int databaseSizeBeforeUpdate = stateProvinceRepository.findAll().size();

        // Create the StateProvince
        StateProvinceDTO stateProvinceDTO = stateProvinceMapper.toDto(stateProvince);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateProvinceMockMvc.perform(put("/api/state-provinces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateProvinceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StateProvince in the database
        List<StateProvince> stateProvinceList = stateProvinceRepository.findAll();
        assertThat(stateProvinceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStateProvince() throws Exception {
        // Initialize the database
        stateProvinceRepository.saveAndFlush(stateProvince);

        int databaseSizeBeforeDelete = stateProvinceRepository.findAll().size();

        // Delete the stateProvince
        restStateProvinceMockMvc.perform(delete("/api/state-provinces/{id}", stateProvince.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StateProvince> stateProvinceList = stateProvinceRepository.findAll();
        assertThat(stateProvinceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvince.class);
        StateProvince stateProvince1 = new StateProvince();
        stateProvince1.setId(1L);
        StateProvince stateProvince2 = new StateProvince();
        stateProvince2.setId(stateProvince1.getId());
        assertThat(stateProvince1).isEqualTo(stateProvince2);
        stateProvince2.setId(2L);
        assertThat(stateProvince1).isNotEqualTo(stateProvince2);
        stateProvince1.setId(null);
        assertThat(stateProvince1).isNotEqualTo(stateProvince2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateProvinceDTO.class);
        StateProvinceDTO stateProvinceDTO1 = new StateProvinceDTO();
        stateProvinceDTO1.setId(1L);
        StateProvinceDTO stateProvinceDTO2 = new StateProvinceDTO();
        assertThat(stateProvinceDTO1).isNotEqualTo(stateProvinceDTO2);
        stateProvinceDTO2.setId(stateProvinceDTO1.getId());
        assertThat(stateProvinceDTO1).isEqualTo(stateProvinceDTO2);
        stateProvinceDTO2.setId(2L);
        assertThat(stateProvinceDTO1).isNotEqualTo(stateProvinceDTO2);
        stateProvinceDTO1.setId(null);
        assertThat(stateProvinceDTO1).isNotEqualTo(stateProvinceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stateProvinceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stateProvinceMapper.fromId(null)).isNull();
    }
}
