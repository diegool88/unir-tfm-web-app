package com.dfgtech.tfm.loanms.web.rest;

import com.dfgtech.tfm.loanms.LoanMsApp;
import com.dfgtech.tfm.loanms.domain.LoanProcess;
import com.dfgtech.tfm.loanms.repository.LoanProcessRepository;
import com.dfgtech.tfm.loanms.service.LoanProcessService;
import com.dfgtech.tfm.loanms.service.dto.LoanProcessDTO;
import com.dfgtech.tfm.loanms.service.mapper.LoanProcessMapper;
import com.dfgtech.tfm.loanms.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.dfgtech.tfm.loanms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dfgtech.tfm.loanms.domain.enumeration.LoanProcessStatus;
/**
 * Integration tests for the {@Link LoanProcessResource} REST controller.
 */
@SpringBootTest(classes = LoanMsApp.class)
public class LoanProcessResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_REQUESTED_AMOUNT = 1D;
    private static final Double UPDATED_REQUESTED_AMOUNT = 2D;

    private static final Double DEFAULT_GIVEN_AMOUNT = 1D;
    private static final Double UPDATED_GIVEN_AMOUNT = 2D;

    private static final Integer DEFAULT_LOAN_PERIOD = 1;
    private static final Integer UPDATED_LOAN_PERIOD = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_JUSTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_JUSTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_DEBTOR_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_DEBTOR_IDENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_DEBTOR_IDENTIFICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEBTOR_IDENTIFICATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEBTOR_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_DEBTOR_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_BANKING_ENTITY_MNEMONIC = "AAAAAAAAAA";
    private static final String UPDATED_BANKING_ENTITY_MNEMONIC = "BBBBBBBBBB";

    private static final String DEFAULT_BANKING_PRODUCT_MNEMONIC = "AAAAAAAAAA";
    private static final String UPDATED_BANKING_PRODUCT_MNEMONIC = "BBBBBBBBBB";

    private static final LoanProcessStatus DEFAULT_LOAN_PROCESS_STATUS = LoanProcessStatus.APPROVED;
    private static final LoanProcessStatus UPDATED_LOAN_PROCESS_STATUS = LoanProcessStatus.DENIED;

    @Autowired
    private LoanProcessRepository loanProcessRepository;

    @Autowired
    private LoanProcessMapper loanProcessMapper;

    @Autowired
    private LoanProcessService loanProcessService;

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

    private MockMvc restLoanProcessMockMvc;

    private LoanProcess loanProcess;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanProcessResource loanProcessResource = new LoanProcessResource(loanProcessService);
        this.restLoanProcessMockMvc = MockMvcBuilders.standaloneSetup(loanProcessResource)
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
    public static LoanProcess createEntity(EntityManager em) {
        LoanProcess loanProcess = new LoanProcess()
            .name(DEFAULT_NAME)
            .requestedAmount(DEFAULT_REQUESTED_AMOUNT)
            .givenAmount(DEFAULT_GIVEN_AMOUNT)
            .loanPeriod(DEFAULT_LOAN_PERIOD)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .justification(DEFAULT_JUSTIFICATION)
            .debtorIdentification(DEFAULT_DEBTOR_IDENTIFICATION)
            .debtorIdentificationType(DEFAULT_DEBTOR_IDENTIFICATION_TYPE)
            .debtorCountry(DEFAULT_DEBTOR_COUNTRY)
            .bankingEntityMnemonic(DEFAULT_BANKING_ENTITY_MNEMONIC)
            .bankingProductMnemonic(DEFAULT_BANKING_PRODUCT_MNEMONIC)
            .loanProcessStatus(DEFAULT_LOAN_PROCESS_STATUS);
        return loanProcess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanProcess createUpdatedEntity(EntityManager em) {
        LoanProcess loanProcess = new LoanProcess()
            .name(UPDATED_NAME)
            .requestedAmount(UPDATED_REQUESTED_AMOUNT)
            .givenAmount(UPDATED_GIVEN_AMOUNT)
            .loanPeriod(UPDATED_LOAN_PERIOD)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .justification(UPDATED_JUSTIFICATION)
            .debtorIdentification(UPDATED_DEBTOR_IDENTIFICATION)
            .debtorIdentificationType(UPDATED_DEBTOR_IDENTIFICATION_TYPE)
            .debtorCountry(UPDATED_DEBTOR_COUNTRY)
            .bankingEntityMnemonic(UPDATED_BANKING_ENTITY_MNEMONIC)
            .bankingProductMnemonic(UPDATED_BANKING_PRODUCT_MNEMONIC)
            .loanProcessStatus(UPDATED_LOAN_PROCESS_STATUS);
        return loanProcess;
    }

    @BeforeEach
    public void initTest() {
        loanProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoanProcess() throws Exception {
        int databaseSizeBeforeCreate = loanProcessRepository.findAll().size();

        // Create the LoanProcess
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);
        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the LoanProcess in the database
        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeCreate + 1);
        LoanProcess testLoanProcess = loanProcessList.get(loanProcessList.size() - 1);
        assertThat(testLoanProcess.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLoanProcess.getRequestedAmount()).isEqualTo(DEFAULT_REQUESTED_AMOUNT);
        assertThat(testLoanProcess.getGivenAmount()).isEqualTo(DEFAULT_GIVEN_AMOUNT);
        assertThat(testLoanProcess.getLoanPeriod()).isEqualTo(DEFAULT_LOAN_PERIOD);
        assertThat(testLoanProcess.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testLoanProcess.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLoanProcess.getJustification()).isEqualTo(DEFAULT_JUSTIFICATION);
        assertThat(testLoanProcess.getDebtorIdentification()).isEqualTo(DEFAULT_DEBTOR_IDENTIFICATION);
        assertThat(testLoanProcess.getDebtorIdentificationType()).isEqualTo(DEFAULT_DEBTOR_IDENTIFICATION_TYPE);
        assertThat(testLoanProcess.getDebtorCountry()).isEqualTo(DEFAULT_DEBTOR_COUNTRY);
        assertThat(testLoanProcess.getBankingEntityMnemonic()).isEqualTo(DEFAULT_BANKING_ENTITY_MNEMONIC);
        assertThat(testLoanProcess.getBankingProductMnemonic()).isEqualTo(DEFAULT_BANKING_PRODUCT_MNEMONIC);
        assertThat(testLoanProcess.getLoanProcessStatus()).isEqualTo(DEFAULT_LOAN_PROCESS_STATUS);
    }

    @Test
    @Transactional
    public void createLoanProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanProcessRepository.findAll().size();

        // Create the LoanProcess with an existing ID
        loanProcess.setId(1L);
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoanProcess in the database
        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setName(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequestedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setRequestedAmount(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoanPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setLoanPeriod(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setStartDate(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setEndDate(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkJustificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setJustification(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDebtorIdentificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setDebtorIdentification(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDebtorIdentificationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setDebtorIdentificationType(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDebtorCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setDebtorCountry(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankingEntityMnemonicIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setBankingEntityMnemonic(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankingProductMnemonicIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanProcessRepository.findAll().size();
        // set the field null
        loanProcess.setBankingProductMnemonic(null);

        // Create the LoanProcess, which fails.
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        restLoanProcessMockMvc.perform(post("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoanProcesses() throws Exception {
        // Initialize the database
        loanProcessRepository.saveAndFlush(loanProcess);

        // Get all the loanProcessList
        restLoanProcessMockMvc.perform(get("/api/loan-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].requestedAmount").value(hasItem(DEFAULT_REQUESTED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].givenAmount").value(hasItem(DEFAULT_GIVEN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanPeriod").value(hasItem(DEFAULT_LOAN_PERIOD)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].justification").value(hasItem(DEFAULT_JUSTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].debtorIdentification").value(hasItem(DEFAULT_DEBTOR_IDENTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].debtorIdentificationType").value(hasItem(DEFAULT_DEBTOR_IDENTIFICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].debtorCountry").value(hasItem(DEFAULT_DEBTOR_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].bankingEntityMnemonic").value(hasItem(DEFAULT_BANKING_ENTITY_MNEMONIC.toString())))
            .andExpect(jsonPath("$.[*].bankingProductMnemonic").value(hasItem(DEFAULT_BANKING_PRODUCT_MNEMONIC.toString())))
            .andExpect(jsonPath("$.[*].loanProcessStatus").value(hasItem(DEFAULT_LOAN_PROCESS_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getLoanProcess() throws Exception {
        // Initialize the database
        loanProcessRepository.saveAndFlush(loanProcess);

        // Get the loanProcess
        restLoanProcessMockMvc.perform(get("/api/loan-processes/{id}", loanProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loanProcess.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.requestedAmount").value(DEFAULT_REQUESTED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.givenAmount").value(DEFAULT_GIVEN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.loanPeriod").value(DEFAULT_LOAN_PERIOD))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.justification").value(DEFAULT_JUSTIFICATION.toString()))
            .andExpect(jsonPath("$.debtorIdentification").value(DEFAULT_DEBTOR_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.debtorIdentificationType").value(DEFAULT_DEBTOR_IDENTIFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.debtorCountry").value(DEFAULT_DEBTOR_COUNTRY.toString()))
            .andExpect(jsonPath("$.bankingEntityMnemonic").value(DEFAULT_BANKING_ENTITY_MNEMONIC.toString()))
            .andExpect(jsonPath("$.bankingProductMnemonic").value(DEFAULT_BANKING_PRODUCT_MNEMONIC.toString()))
            .andExpect(jsonPath("$.loanProcessStatus").value(DEFAULT_LOAN_PROCESS_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoanProcess() throws Exception {
        // Get the loanProcess
        restLoanProcessMockMvc.perform(get("/api/loan-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoanProcess() throws Exception {
        // Initialize the database
        loanProcessRepository.saveAndFlush(loanProcess);

        int databaseSizeBeforeUpdate = loanProcessRepository.findAll().size();

        // Update the loanProcess
        LoanProcess updatedLoanProcess = loanProcessRepository.findById(loanProcess.getId()).get();
        // Disconnect from session so that the updates on updatedLoanProcess are not directly saved in db
        em.detach(updatedLoanProcess);
        updatedLoanProcess
            .name(UPDATED_NAME)
            .requestedAmount(UPDATED_REQUESTED_AMOUNT)
            .givenAmount(UPDATED_GIVEN_AMOUNT)
            .loanPeriod(UPDATED_LOAN_PERIOD)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .justification(UPDATED_JUSTIFICATION)
            .debtorIdentification(UPDATED_DEBTOR_IDENTIFICATION)
            .debtorIdentificationType(UPDATED_DEBTOR_IDENTIFICATION_TYPE)
            .debtorCountry(UPDATED_DEBTOR_COUNTRY)
            .bankingEntityMnemonic(UPDATED_BANKING_ENTITY_MNEMONIC)
            .bankingProductMnemonic(UPDATED_BANKING_PRODUCT_MNEMONIC)
            .loanProcessStatus(UPDATED_LOAN_PROCESS_STATUS);
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(updatedLoanProcess);

        restLoanProcessMockMvc.perform(put("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isOk());

        // Validate the LoanProcess in the database
        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeUpdate);
        LoanProcess testLoanProcess = loanProcessList.get(loanProcessList.size() - 1);
        assertThat(testLoanProcess.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLoanProcess.getRequestedAmount()).isEqualTo(UPDATED_REQUESTED_AMOUNT);
        assertThat(testLoanProcess.getGivenAmount()).isEqualTo(UPDATED_GIVEN_AMOUNT);
        assertThat(testLoanProcess.getLoanPeriod()).isEqualTo(UPDATED_LOAN_PERIOD);
        assertThat(testLoanProcess.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testLoanProcess.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLoanProcess.getJustification()).isEqualTo(UPDATED_JUSTIFICATION);
        assertThat(testLoanProcess.getDebtorIdentification()).isEqualTo(UPDATED_DEBTOR_IDENTIFICATION);
        assertThat(testLoanProcess.getDebtorIdentificationType()).isEqualTo(UPDATED_DEBTOR_IDENTIFICATION_TYPE);
        assertThat(testLoanProcess.getDebtorCountry()).isEqualTo(UPDATED_DEBTOR_COUNTRY);
        assertThat(testLoanProcess.getBankingEntityMnemonic()).isEqualTo(UPDATED_BANKING_ENTITY_MNEMONIC);
        assertThat(testLoanProcess.getBankingProductMnemonic()).isEqualTo(UPDATED_BANKING_PRODUCT_MNEMONIC);
        assertThat(testLoanProcess.getLoanProcessStatus()).isEqualTo(UPDATED_LOAN_PROCESS_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingLoanProcess() throws Exception {
        int databaseSizeBeforeUpdate = loanProcessRepository.findAll().size();

        // Create the LoanProcess
        LoanProcessDTO loanProcessDTO = loanProcessMapper.toDto(loanProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanProcessMockMvc.perform(put("/api/loan-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoanProcess in the database
        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoanProcess() throws Exception {
        // Initialize the database
        loanProcessRepository.saveAndFlush(loanProcess);

        int databaseSizeBeforeDelete = loanProcessRepository.findAll().size();

        // Delete the loanProcess
        restLoanProcessMockMvc.perform(delete("/api/loan-processes/{id}", loanProcess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanProcess> loanProcessList = loanProcessRepository.findAll();
        assertThat(loanProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanProcess.class);
        LoanProcess loanProcess1 = new LoanProcess();
        loanProcess1.setId(1L);
        LoanProcess loanProcess2 = new LoanProcess();
        loanProcess2.setId(loanProcess1.getId());
        assertThat(loanProcess1).isEqualTo(loanProcess2);
        loanProcess2.setId(2L);
        assertThat(loanProcess1).isNotEqualTo(loanProcess2);
        loanProcess1.setId(null);
        assertThat(loanProcess1).isNotEqualTo(loanProcess2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanProcessDTO.class);
        LoanProcessDTO loanProcessDTO1 = new LoanProcessDTO();
        loanProcessDTO1.setId(1L);
        LoanProcessDTO loanProcessDTO2 = new LoanProcessDTO();
        assertThat(loanProcessDTO1).isNotEqualTo(loanProcessDTO2);
        loanProcessDTO2.setId(loanProcessDTO1.getId());
        assertThat(loanProcessDTO1).isEqualTo(loanProcessDTO2);
        loanProcessDTO2.setId(2L);
        assertThat(loanProcessDTO1).isNotEqualTo(loanProcessDTO2);
        loanProcessDTO1.setId(null);
        assertThat(loanProcessDTO1).isNotEqualTo(loanProcessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loanProcessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loanProcessMapper.fromId(null)).isNull();
    }
}
