package com.dfgtech.tfm.creditapp.web.rest;

import com.dfgtech.tfm.creditapp.CreditApp;
import com.dfgtech.tfm.creditapp.domain.TelephoneNumber;
import com.dfgtech.tfm.creditapp.domain.Address;
import com.dfgtech.tfm.creditapp.repository.TelephoneNumberRepository;
import com.dfgtech.tfm.creditapp.service.TelephoneNumberService;
import com.dfgtech.tfm.creditapp.service.dto.TelephoneNumberDTO;
import com.dfgtech.tfm.creditapp.service.mapper.TelephoneNumberMapper;
import com.dfgtech.tfm.creditapp.web.rest.errors.ExceptionTranslator;

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

import static com.dfgtech.tfm.creditapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TelephoneNumberResource} REST controller.
 */
@SpringBootTest(classes = CreditApp.class)
public class TelephoneNumberResourceIT {

    private static final Integer DEFAULT_COUNTRY_CODE = 1;
    private static final Integer UPDATED_COUNTRY_CODE = 2;

    private static final Integer DEFAULT_REGION_CODE = 1;
    private static final Integer UPDATED_REGION_CODE = 2;

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    @Autowired
    private TelephoneNumberRepository telephoneNumberRepository;

    @Autowired
    private TelephoneNumberMapper telephoneNumberMapper;

    @Autowired
    private TelephoneNumberService telephoneNumberService;

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

    private MockMvc restTelephoneNumberMockMvc;

    private TelephoneNumber telephoneNumber;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TelephoneNumberResource telephoneNumberResource = new TelephoneNumberResource(telephoneNumberService);
        this.restTelephoneNumberMockMvc = MockMvcBuilders.standaloneSetup(telephoneNumberResource)
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
    public static TelephoneNumber createEntity(EntityManager em) {
        TelephoneNumber telephoneNumber = new TelephoneNumber()
            .countryCode(DEFAULT_COUNTRY_CODE)
            .regionCode(DEFAULT_REGION_CODE)
            .number(DEFAULT_NUMBER);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        telephoneNumber.setAddress(address);
        return telephoneNumber;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelephoneNumber createUpdatedEntity(EntityManager em) {
        TelephoneNumber telephoneNumber = new TelephoneNumber()
            .countryCode(UPDATED_COUNTRY_CODE)
            .regionCode(UPDATED_REGION_CODE)
            .number(UPDATED_NUMBER);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createUpdatedEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        telephoneNumber.setAddress(address);
        return telephoneNumber;
    }

    @BeforeEach
    public void initTest() {
        telephoneNumber = createEntity(em);
    }

    @Test
    @Transactional
    public void createTelephoneNumber() throws Exception {
        int databaseSizeBeforeCreate = telephoneNumberRepository.findAll().size();

        // Create the TelephoneNumber
        TelephoneNumberDTO telephoneNumberDTO = telephoneNumberMapper.toDto(telephoneNumber);
        restTelephoneNumberMockMvc.perform(post("/api/telephone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneNumberDTO)))
            .andExpect(status().isCreated());

        // Validate the TelephoneNumber in the database
        List<TelephoneNumber> telephoneNumberList = telephoneNumberRepository.findAll();
        assertThat(telephoneNumberList).hasSize(databaseSizeBeforeCreate + 1);
        TelephoneNumber testTelephoneNumber = telephoneNumberList.get(telephoneNumberList.size() - 1);
        assertThat(testTelephoneNumber.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testTelephoneNumber.getRegionCode()).isEqualTo(DEFAULT_REGION_CODE);
        assertThat(testTelephoneNumber.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    public void createTelephoneNumberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = telephoneNumberRepository.findAll().size();

        // Create the TelephoneNumber with an existing ID
        telephoneNumber.setId(1L);
        TelephoneNumberDTO telephoneNumberDTO = telephoneNumberMapper.toDto(telephoneNumber);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelephoneNumberMockMvc.perform(post("/api/telephone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneNumberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TelephoneNumber in the database
        List<TelephoneNumber> telephoneNumberList = telephoneNumberRepository.findAll();
        assertThat(telephoneNumberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCountryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = telephoneNumberRepository.findAll().size();
        // set the field null
        telephoneNumber.setCountryCode(null);

        // Create the TelephoneNumber, which fails.
        TelephoneNumberDTO telephoneNumberDTO = telephoneNumberMapper.toDto(telephoneNumber);

        restTelephoneNumberMockMvc.perform(post("/api/telephone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneNumberDTO)))
            .andExpect(status().isBadRequest());

        List<TelephoneNumber> telephoneNumberList = telephoneNumberRepository.findAll();
        assertThat(telephoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = telephoneNumberRepository.findAll().size();
        // set the field null
        telephoneNumber.setRegionCode(null);

        // Create the TelephoneNumber, which fails.
        TelephoneNumberDTO telephoneNumberDTO = telephoneNumberMapper.toDto(telephoneNumber);

        restTelephoneNumberMockMvc.perform(post("/api/telephone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneNumberDTO)))
            .andExpect(status().isBadRequest());

        List<TelephoneNumber> telephoneNumberList = telephoneNumberRepository.findAll();
        assertThat(telephoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = telephoneNumberRepository.findAll().size();
        // set the field null
        telephoneNumber.setNumber(null);

        // Create the TelephoneNumber, which fails.
        TelephoneNumberDTO telephoneNumberDTO = telephoneNumberMapper.toDto(telephoneNumber);

        restTelephoneNumberMockMvc.perform(post("/api/telephone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneNumberDTO)))
            .andExpect(status().isBadRequest());

        List<TelephoneNumber> telephoneNumberList = telephoneNumberRepository.findAll();
        assertThat(telephoneNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTelephoneNumbers() throws Exception {
        // Initialize the database
        telephoneNumberRepository.saveAndFlush(telephoneNumber);

        // Get all the telephoneNumberList
        restTelephoneNumberMockMvc.perform(get("/api/telephone-numbers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telephoneNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].regionCode").value(hasItem(DEFAULT_REGION_CODE)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getTelephoneNumber() throws Exception {
        // Initialize the database
        telephoneNumberRepository.saveAndFlush(telephoneNumber);

        // Get the telephoneNumber
        restTelephoneNumberMockMvc.perform(get("/api/telephone-numbers/{id}", telephoneNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(telephoneNumber.getId().intValue()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.regionCode").value(DEFAULT_REGION_CODE))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingTelephoneNumber() throws Exception {
        // Get the telephoneNumber
        restTelephoneNumberMockMvc.perform(get("/api/telephone-numbers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTelephoneNumber() throws Exception {
        // Initialize the database
        telephoneNumberRepository.saveAndFlush(telephoneNumber);

        int databaseSizeBeforeUpdate = telephoneNumberRepository.findAll().size();

        // Update the telephoneNumber
        TelephoneNumber updatedTelephoneNumber = telephoneNumberRepository.findById(telephoneNumber.getId()).get();
        // Disconnect from session so that the updates on updatedTelephoneNumber are not directly saved in db
        em.detach(updatedTelephoneNumber);
        updatedTelephoneNumber
            .countryCode(UPDATED_COUNTRY_CODE)
            .regionCode(UPDATED_REGION_CODE)
            .number(UPDATED_NUMBER);
        TelephoneNumberDTO telephoneNumberDTO = telephoneNumberMapper.toDto(updatedTelephoneNumber);

        restTelephoneNumberMockMvc.perform(put("/api/telephone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneNumberDTO)))
            .andExpect(status().isOk());

        // Validate the TelephoneNumber in the database
        List<TelephoneNumber> telephoneNumberList = telephoneNumberRepository.findAll();
        assertThat(telephoneNumberList).hasSize(databaseSizeBeforeUpdate);
        TelephoneNumber testTelephoneNumber = telephoneNumberList.get(telephoneNumberList.size() - 1);
        assertThat(testTelephoneNumber.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testTelephoneNumber.getRegionCode()).isEqualTo(UPDATED_REGION_CODE);
        assertThat(testTelephoneNumber.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingTelephoneNumber() throws Exception {
        int databaseSizeBeforeUpdate = telephoneNumberRepository.findAll().size();

        // Create the TelephoneNumber
        TelephoneNumberDTO telephoneNumberDTO = telephoneNumberMapper.toDto(telephoneNumber);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelephoneNumberMockMvc.perform(put("/api/telephone-numbers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(telephoneNumberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TelephoneNumber in the database
        List<TelephoneNumber> telephoneNumberList = telephoneNumberRepository.findAll();
        assertThat(telephoneNumberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTelephoneNumber() throws Exception {
        // Initialize the database
        telephoneNumberRepository.saveAndFlush(telephoneNumber);

        int databaseSizeBeforeDelete = telephoneNumberRepository.findAll().size();

        // Delete the telephoneNumber
        restTelephoneNumberMockMvc.perform(delete("/api/telephone-numbers/{id}", telephoneNumber.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TelephoneNumber> telephoneNumberList = telephoneNumberRepository.findAll();
        assertThat(telephoneNumberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelephoneNumber.class);
        TelephoneNumber telephoneNumber1 = new TelephoneNumber();
        telephoneNumber1.setId(1L);
        TelephoneNumber telephoneNumber2 = new TelephoneNumber();
        telephoneNumber2.setId(telephoneNumber1.getId());
        assertThat(telephoneNumber1).isEqualTo(telephoneNumber2);
        telephoneNumber2.setId(2L);
        assertThat(telephoneNumber1).isNotEqualTo(telephoneNumber2);
        telephoneNumber1.setId(null);
        assertThat(telephoneNumber1).isNotEqualTo(telephoneNumber2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelephoneNumberDTO.class);
        TelephoneNumberDTO telephoneNumberDTO1 = new TelephoneNumberDTO();
        telephoneNumberDTO1.setId(1L);
        TelephoneNumberDTO telephoneNumberDTO2 = new TelephoneNumberDTO();
        assertThat(telephoneNumberDTO1).isNotEqualTo(telephoneNumberDTO2);
        telephoneNumberDTO2.setId(telephoneNumberDTO1.getId());
        assertThat(telephoneNumberDTO1).isEqualTo(telephoneNumberDTO2);
        telephoneNumberDTO2.setId(2L);
        assertThat(telephoneNumberDTO1).isNotEqualTo(telephoneNumberDTO2);
        telephoneNumberDTO1.setId(null);
        assertThat(telephoneNumberDTO1).isNotEqualTo(telephoneNumberDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(telephoneNumberMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(telephoneNumberMapper.fromId(null)).isNull();
    }
}
