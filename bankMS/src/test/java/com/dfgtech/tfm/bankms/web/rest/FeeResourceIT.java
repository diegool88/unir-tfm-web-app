package com.dfgtech.tfm.bankms.web.rest;

import com.dfgtech.tfm.bankms.BankMsApp;
import com.dfgtech.tfm.bankms.domain.Fee;
import com.dfgtech.tfm.bankms.domain.Product;
import com.dfgtech.tfm.bankms.repository.FeeRepository;
import com.dfgtech.tfm.bankms.service.FeeService;
import com.dfgtech.tfm.bankms.service.dto.FeeDTO;
import com.dfgtech.tfm.bankms.service.mapper.FeeMapper;
import com.dfgtech.tfm.bankms.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.dfgtech.tfm.bankms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FeeResource} REST controller.
 */
@SpringBootTest(classes = BankMsApp.class)
public class FeeResourceIT {

    private static final String DEFAULT_MNEMONIC = "AAAAAAAAAA";
    private static final String UPDATED_MNEMONIC = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_INTEREST_RATE = 1D;
    private static final Double UPDATED_INTEREST_RATE = 2D;

    private static final Double DEFAULT_UNIQUE_VALUE = 1D;
    private static final Double UPDATED_UNIQUE_VALUE = 2D;

    @Autowired
    private FeeRepository feeRepository;

    @Mock
    private FeeRepository feeRepositoryMock;

    @Autowired
    private FeeMapper feeMapper;

    @Mock
    private FeeService feeServiceMock;

    @Autowired
    private FeeService feeService;

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

    private MockMvc restFeeMockMvc;

    private Fee fee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeeResource feeResource = new FeeResource(feeService);
        this.restFeeMockMvc = MockMvcBuilders.standaloneSetup(feeResource)
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
    public static Fee createEntity(EntityManager em) {
        Fee fee = new Fee()
            .mnemonic(DEFAULT_MNEMONIC)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .interestRate(DEFAULT_INTEREST_RATE)
            .uniqueValue(DEFAULT_UNIQUE_VALUE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        fee.getProducts().add(product);
        return fee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fee createUpdatedEntity(EntityManager em) {
        Fee fee = new Fee()
            .mnemonic(UPDATED_MNEMONIC)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .interestRate(UPDATED_INTEREST_RATE)
            .uniqueValue(UPDATED_UNIQUE_VALUE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        fee.getProducts().add(product);
        return fee;
    }

    @BeforeEach
    public void initTest() {
        fee = createEntity(em);
    }

    @Test
    @Transactional
    public void createFee() throws Exception {
        int databaseSizeBeforeCreate = feeRepository.findAll().size();

        // Create the Fee
        FeeDTO feeDTO = feeMapper.toDto(fee);
        restFeeMockMvc.perform(post("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isCreated());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeCreate + 1);
        Fee testFee = feeList.get(feeList.size() - 1);
        assertThat(testFee.getMnemonic()).isEqualTo(DEFAULT_MNEMONIC);
        assertThat(testFee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFee.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFee.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testFee.getUniqueValue()).isEqualTo(DEFAULT_UNIQUE_VALUE);
    }

    @Test
    @Transactional
    public void createFeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feeRepository.findAll().size();

        // Create the Fee with an existing ID
        fee.setId(1L);
        FeeDTO feeDTO = feeMapper.toDto(fee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeeMockMvc.perform(post("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMnemonicIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeRepository.findAll().size();
        // set the field null
        fee.setMnemonic(null);

        // Create the Fee, which fails.
        FeeDTO feeDTO = feeMapper.toDto(fee);

        restFeeMockMvc.perform(post("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = feeRepository.findAll().size();
        // set the field null
        fee.setName(null);

        // Create the Fee, which fails.
        FeeDTO feeDTO = feeMapper.toDto(fee);

        restFeeMockMvc.perform(post("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFees() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        // Get all the feeList
        restFeeMockMvc.perform(get("/api/fees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fee.getId().intValue())))
            .andExpect(jsonPath("$.[*].mnemonic").value(hasItem(DEFAULT_MNEMONIC.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].uniqueValue").value(hasItem(DEFAULT_UNIQUE_VALUE.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFeesWithEagerRelationshipsIsEnabled() throws Exception {
        FeeResource feeResource = new FeeResource(feeServiceMock);
        when(feeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFeeMockMvc = MockMvcBuilders.standaloneSetup(feeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFeeMockMvc.perform(get("/api/fees?eagerload=true"))
        .andExpect(status().isOk());

        verify(feeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFeesWithEagerRelationshipsIsNotEnabled() throws Exception {
        FeeResource feeResource = new FeeResource(feeServiceMock);
            when(feeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFeeMockMvc = MockMvcBuilders.standaloneSetup(feeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFeeMockMvc.perform(get("/api/fees?eagerload=true"))
        .andExpect(status().isOk());

            verify(feeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        // Get the fee
        restFeeMockMvc.perform(get("/api/fees/{id}", fee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fee.getId().intValue()))
            .andExpect(jsonPath("$.mnemonic").value(DEFAULT_MNEMONIC.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.uniqueValue").value(DEFAULT_UNIQUE_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFee() throws Exception {
        // Get the fee
        restFeeMockMvc.perform(get("/api/fees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        int databaseSizeBeforeUpdate = feeRepository.findAll().size();

        // Update the fee
        Fee updatedFee = feeRepository.findById(fee.getId()).get();
        // Disconnect from session so that the updates on updatedFee are not directly saved in db
        em.detach(updatedFee);
        updatedFee
            .mnemonic(UPDATED_MNEMONIC)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .interestRate(UPDATED_INTEREST_RATE)
            .uniqueValue(UPDATED_UNIQUE_VALUE);
        FeeDTO feeDTO = feeMapper.toDto(updatedFee);

        restFeeMockMvc.perform(put("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isOk());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeUpdate);
        Fee testFee = feeList.get(feeList.size() - 1);
        assertThat(testFee.getMnemonic()).isEqualTo(UPDATED_MNEMONIC);
        assertThat(testFee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFee.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFee.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testFee.getUniqueValue()).isEqualTo(UPDATED_UNIQUE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingFee() throws Exception {
        int databaseSizeBeforeUpdate = feeRepository.findAll().size();

        // Create the Fee
        FeeDTO feeDTO = feeMapper.toDto(fee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeeMockMvc.perform(put("/api/fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fee in the database
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFee() throws Exception {
        // Initialize the database
        feeRepository.saveAndFlush(fee);

        int databaseSizeBeforeDelete = feeRepository.findAll().size();

        // Delete the fee
        restFeeMockMvc.perform(delete("/api/fees/{id}", fee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fee> feeList = feeRepository.findAll();
        assertThat(feeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fee.class);
        Fee fee1 = new Fee();
        fee1.setId(1L);
        Fee fee2 = new Fee();
        fee2.setId(fee1.getId());
        assertThat(fee1).isEqualTo(fee2);
        fee2.setId(2L);
        assertThat(fee1).isNotEqualTo(fee2);
        fee1.setId(null);
        assertThat(fee1).isNotEqualTo(fee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeeDTO.class);
        FeeDTO feeDTO1 = new FeeDTO();
        feeDTO1.setId(1L);
        FeeDTO feeDTO2 = new FeeDTO();
        assertThat(feeDTO1).isNotEqualTo(feeDTO2);
        feeDTO2.setId(feeDTO1.getId());
        assertThat(feeDTO1).isEqualTo(feeDTO2);
        feeDTO2.setId(2L);
        assertThat(feeDTO1).isNotEqualTo(feeDTO2);
        feeDTO1.setId(null);
        assertThat(feeDTO1).isNotEqualTo(feeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(feeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(feeMapper.fromId(null)).isNull();
    }
}
