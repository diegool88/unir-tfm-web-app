package com.dfgtech.tfm.bankms.web.rest;

import com.dfgtech.tfm.bankms.BankMsApp;
import com.dfgtech.tfm.bankms.domain.BankingTransaction;
import com.dfgtech.tfm.bankms.domain.BankingEntity;
import com.dfgtech.tfm.bankms.repository.BankingTransactionRepository;
import com.dfgtech.tfm.bankms.service.BankingTransactionService;
import com.dfgtech.tfm.bankms.service.dto.BankingTransactionDTO;
import com.dfgtech.tfm.bankms.service.mapper.BankingTransactionMapper;
import com.dfgtech.tfm.bankms.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.dfgtech.tfm.bankms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dfgtech.tfm.bankms.domain.enumeration.TransactionType;
import com.dfgtech.tfm.bankms.domain.enumeration.AccountType;
import com.dfgtech.tfm.bankms.domain.enumeration.AccountType;
/**
 * Integration tests for the {@Link BankingTransactionResource} REST controller.
 */
@SpringBootTest(classes = BankMsApp.class)
public class BankingTransactionResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMMOUNT = 1D;
    private static final Double UPDATED_AMMOUNT = 2D;

    private static final TransactionType DEFAULT_TRANSACTION_TYPE = TransactionType.DEPOSIT;
    private static final TransactionType UPDATED_TRANSACTION_TYPE = TransactionType.DEBIT;

    private static final Integer DEFAULT_EXT_ORIGIN_ACCOUNT = 1;
    private static final Integer UPDATED_EXT_ORIGIN_ACCOUNT = 2;

    private static final AccountType DEFAULT_EXT_ORIGIN_ACCOUNT_TYPE = AccountType.SAVINGS;
    private static final AccountType UPDATED_EXT_ORIGIN_ACCOUNT_TYPE = AccountType.CHECKING;

    private static final String DEFAULT_EXT_ORIGIN_ACCOUNT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_EXT_ORIGIN_ACCOUNT_BANK = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXT_DESTINATION_ACCOUNT = 1;
    private static final Integer UPDATED_EXT_DESTINATION_ACCOUNT = 2;

    private static final AccountType DEFAULT_EXT_DESTINATION_ACCOUNT_TYPE = AccountType.SAVINGS;
    private static final AccountType UPDATED_EXT_DESTINATION_ACCOUNT_TYPE = AccountType.CHECKING;

    private static final String DEFAULT_EXT_DESTINATION_ACCOUNT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_EXT_DESTINATION_ACCOUNT_BANK = "BBBBBBBBBB";

    @Autowired
    private BankingTransactionRepository bankingTransactionRepository;

    @Autowired
    private BankingTransactionMapper bankingTransactionMapper;

    @Autowired
    private BankingTransactionService bankingTransactionService;

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

    private MockMvc restBankingTransactionMockMvc;

    private BankingTransaction bankingTransaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BankingTransactionResource bankingTransactionResource = new BankingTransactionResource(bankingTransactionService);
        this.restBankingTransactionMockMvc = MockMvcBuilders.standaloneSetup(bankingTransactionResource)
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
    public static BankingTransaction createEntity(EntityManager em) {
        BankingTransaction bankingTransaction = new BankingTransaction()
            .number(DEFAULT_NUMBER)
            .date(DEFAULT_DATE)
            .ammount(DEFAULT_AMMOUNT)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .extOriginAccount(DEFAULT_EXT_ORIGIN_ACCOUNT)
            .extOriginAccountType(DEFAULT_EXT_ORIGIN_ACCOUNT_TYPE)
            .extOriginAccountBank(DEFAULT_EXT_ORIGIN_ACCOUNT_BANK)
            .extDestinationAccount(DEFAULT_EXT_DESTINATION_ACCOUNT)
            .extDestinationAccountType(DEFAULT_EXT_DESTINATION_ACCOUNT_TYPE)
            .extDestinationAccountBank(DEFAULT_EXT_DESTINATION_ACCOUNT_BANK);
        // Add required entity
        BankingEntity bankingEntity;
        if (TestUtil.findAll(em, BankingEntity.class).isEmpty()) {
            bankingEntity = BankingEntityResourceIT.createEntity(em);
            em.persist(bankingEntity);
            em.flush();
        } else {
            bankingEntity = TestUtil.findAll(em, BankingEntity.class).get(0);
        }
        bankingTransaction.setBankingEntity(bankingEntity);
        return bankingTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankingTransaction createUpdatedEntity(EntityManager em) {
        BankingTransaction bankingTransaction = new BankingTransaction()
            .number(UPDATED_NUMBER)
            .date(UPDATED_DATE)
            .ammount(UPDATED_AMMOUNT)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .extOriginAccount(UPDATED_EXT_ORIGIN_ACCOUNT)
            .extOriginAccountType(UPDATED_EXT_ORIGIN_ACCOUNT_TYPE)
            .extOriginAccountBank(UPDATED_EXT_ORIGIN_ACCOUNT_BANK)
            .extDestinationAccount(UPDATED_EXT_DESTINATION_ACCOUNT)
            .extDestinationAccountType(UPDATED_EXT_DESTINATION_ACCOUNT_TYPE)
            .extDestinationAccountBank(UPDATED_EXT_DESTINATION_ACCOUNT_BANK);
        // Add required entity
        BankingEntity bankingEntity;
        if (TestUtil.findAll(em, BankingEntity.class).isEmpty()) {
            bankingEntity = BankingEntityResourceIT.createUpdatedEntity(em);
            em.persist(bankingEntity);
            em.flush();
        } else {
            bankingEntity = TestUtil.findAll(em, BankingEntity.class).get(0);
        }
        bankingTransaction.setBankingEntity(bankingEntity);
        return bankingTransaction;
    }

    @BeforeEach
    public void initTest() {
        bankingTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankingTransaction() throws Exception {
        int databaseSizeBeforeCreate = bankingTransactionRepository.findAll().size();

        // Create the BankingTransaction
        BankingTransactionDTO bankingTransactionDTO = bankingTransactionMapper.toDto(bankingTransaction);
        restBankingTransactionMockMvc.perform(post("/api/banking-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the BankingTransaction in the database
        List<BankingTransaction> bankingTransactionList = bankingTransactionRepository.findAll();
        assertThat(bankingTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        BankingTransaction testBankingTransaction = bankingTransactionList.get(bankingTransactionList.size() - 1);
        assertThat(testBankingTransaction.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBankingTransaction.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testBankingTransaction.getAmmount()).isEqualTo(DEFAULT_AMMOUNT);
        assertThat(testBankingTransaction.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testBankingTransaction.getExtOriginAccount()).isEqualTo(DEFAULT_EXT_ORIGIN_ACCOUNT);
        assertThat(testBankingTransaction.getExtOriginAccountType()).isEqualTo(DEFAULT_EXT_ORIGIN_ACCOUNT_TYPE);
        assertThat(testBankingTransaction.getExtOriginAccountBank()).isEqualTo(DEFAULT_EXT_ORIGIN_ACCOUNT_BANK);
        assertThat(testBankingTransaction.getExtDestinationAccount()).isEqualTo(DEFAULT_EXT_DESTINATION_ACCOUNT);
        assertThat(testBankingTransaction.getExtDestinationAccountType()).isEqualTo(DEFAULT_EXT_DESTINATION_ACCOUNT_TYPE);
        assertThat(testBankingTransaction.getExtDestinationAccountBank()).isEqualTo(DEFAULT_EXT_DESTINATION_ACCOUNT_BANK);
    }

    @Test
    @Transactional
    public void createBankingTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankingTransactionRepository.findAll().size();

        // Create the BankingTransaction with an existing ID
        bankingTransaction.setId(1L);
        BankingTransactionDTO bankingTransactionDTO = bankingTransactionMapper.toDto(bankingTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankingTransactionMockMvc.perform(post("/api/banking-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankingTransaction in the database
        List<BankingTransaction> bankingTransactionList = bankingTransactionRepository.findAll();
        assertThat(bankingTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingTransactionRepository.findAll().size();
        // set the field null
        bankingTransaction.setNumber(null);

        // Create the BankingTransaction, which fails.
        BankingTransactionDTO bankingTransactionDTO = bankingTransactionMapper.toDto(bankingTransaction);

        restBankingTransactionMockMvc.perform(post("/api/banking-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<BankingTransaction> bankingTransactionList = bankingTransactionRepository.findAll();
        assertThat(bankingTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingTransactionRepository.findAll().size();
        // set the field null
        bankingTransaction.setDate(null);

        // Create the BankingTransaction, which fails.
        BankingTransactionDTO bankingTransactionDTO = bankingTransactionMapper.toDto(bankingTransaction);

        restBankingTransactionMockMvc.perform(post("/api/banking-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<BankingTransaction> bankingTransactionList = bankingTransactionRepository.findAll();
        assertThat(bankingTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingTransactionRepository.findAll().size();
        // set the field null
        bankingTransaction.setAmmount(null);

        // Create the BankingTransaction, which fails.
        BankingTransactionDTO bankingTransactionDTO = bankingTransactionMapper.toDto(bankingTransaction);

        restBankingTransactionMockMvc.perform(post("/api/banking-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<BankingTransaction> bankingTransactionList = bankingTransactionRepository.findAll();
        assertThat(bankingTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingTransactionRepository.findAll().size();
        // set the field null
        bankingTransaction.setTransactionType(null);

        // Create the BankingTransaction, which fails.
        BankingTransactionDTO bankingTransactionDTO = bankingTransactionMapper.toDto(bankingTransaction);

        restBankingTransactionMockMvc.perform(post("/api/banking-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<BankingTransaction> bankingTransactionList = bankingTransactionRepository.findAll();
        assertThat(bankingTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBankingTransactions() throws Exception {
        // Initialize the database
        bankingTransactionRepository.saveAndFlush(bankingTransaction);

        // Get all the bankingTransactionList
        restBankingTransactionMockMvc.perform(get("/api/banking-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankingTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].ammount").value(hasItem(DEFAULT_AMMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].extOriginAccount").value(hasItem(DEFAULT_EXT_ORIGIN_ACCOUNT)))
            .andExpect(jsonPath("$.[*].extOriginAccountType").value(hasItem(DEFAULT_EXT_ORIGIN_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].extOriginAccountBank").value(hasItem(DEFAULT_EXT_ORIGIN_ACCOUNT_BANK.toString())))
            .andExpect(jsonPath("$.[*].extDestinationAccount").value(hasItem(DEFAULT_EXT_DESTINATION_ACCOUNT)))
            .andExpect(jsonPath("$.[*].extDestinationAccountType").value(hasItem(DEFAULT_EXT_DESTINATION_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].extDestinationAccountBank").value(hasItem(DEFAULT_EXT_DESTINATION_ACCOUNT_BANK.toString())));
    }
    
    @Test
    @Transactional
    public void getBankingTransaction() throws Exception {
        // Initialize the database
        bankingTransactionRepository.saveAndFlush(bankingTransaction);

        // Get the bankingTransaction
        restBankingTransactionMockMvc.perform(get("/api/banking-transactions/{id}", bankingTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankingTransaction.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.ammount").value(DEFAULT_AMMOUNT.doubleValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE.toString()))
            .andExpect(jsonPath("$.extOriginAccount").value(DEFAULT_EXT_ORIGIN_ACCOUNT))
            .andExpect(jsonPath("$.extOriginAccountType").value(DEFAULT_EXT_ORIGIN_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.extOriginAccountBank").value(DEFAULT_EXT_ORIGIN_ACCOUNT_BANK.toString()))
            .andExpect(jsonPath("$.extDestinationAccount").value(DEFAULT_EXT_DESTINATION_ACCOUNT))
            .andExpect(jsonPath("$.extDestinationAccountType").value(DEFAULT_EXT_DESTINATION_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.extDestinationAccountBank").value(DEFAULT_EXT_DESTINATION_ACCOUNT_BANK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankingTransaction() throws Exception {
        // Get the bankingTransaction
        restBankingTransactionMockMvc.perform(get("/api/banking-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankingTransaction() throws Exception {
        // Initialize the database
        bankingTransactionRepository.saveAndFlush(bankingTransaction);

        int databaseSizeBeforeUpdate = bankingTransactionRepository.findAll().size();

        // Update the bankingTransaction
        BankingTransaction updatedBankingTransaction = bankingTransactionRepository.findById(bankingTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedBankingTransaction are not directly saved in db
        em.detach(updatedBankingTransaction);
        updatedBankingTransaction
            .number(UPDATED_NUMBER)
            .date(UPDATED_DATE)
            .ammount(UPDATED_AMMOUNT)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .extOriginAccount(UPDATED_EXT_ORIGIN_ACCOUNT)
            .extOriginAccountType(UPDATED_EXT_ORIGIN_ACCOUNT_TYPE)
            .extOriginAccountBank(UPDATED_EXT_ORIGIN_ACCOUNT_BANK)
            .extDestinationAccount(UPDATED_EXT_DESTINATION_ACCOUNT)
            .extDestinationAccountType(UPDATED_EXT_DESTINATION_ACCOUNT_TYPE)
            .extDestinationAccountBank(UPDATED_EXT_DESTINATION_ACCOUNT_BANK);
        BankingTransactionDTO bankingTransactionDTO = bankingTransactionMapper.toDto(updatedBankingTransaction);

        restBankingTransactionMockMvc.perform(put("/api/banking-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the BankingTransaction in the database
        List<BankingTransaction> bankingTransactionList = bankingTransactionRepository.findAll();
        assertThat(bankingTransactionList).hasSize(databaseSizeBeforeUpdate);
        BankingTransaction testBankingTransaction = bankingTransactionList.get(bankingTransactionList.size() - 1);
        assertThat(testBankingTransaction.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBankingTransaction.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testBankingTransaction.getAmmount()).isEqualTo(UPDATED_AMMOUNT);
        assertThat(testBankingTransaction.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testBankingTransaction.getExtOriginAccount()).isEqualTo(UPDATED_EXT_ORIGIN_ACCOUNT);
        assertThat(testBankingTransaction.getExtOriginAccountType()).isEqualTo(UPDATED_EXT_ORIGIN_ACCOUNT_TYPE);
        assertThat(testBankingTransaction.getExtOriginAccountBank()).isEqualTo(UPDATED_EXT_ORIGIN_ACCOUNT_BANK);
        assertThat(testBankingTransaction.getExtDestinationAccount()).isEqualTo(UPDATED_EXT_DESTINATION_ACCOUNT);
        assertThat(testBankingTransaction.getExtDestinationAccountType()).isEqualTo(UPDATED_EXT_DESTINATION_ACCOUNT_TYPE);
        assertThat(testBankingTransaction.getExtDestinationAccountBank()).isEqualTo(UPDATED_EXT_DESTINATION_ACCOUNT_BANK);
    }

    @Test
    @Transactional
    public void updateNonExistingBankingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bankingTransactionRepository.findAll().size();

        // Create the BankingTransaction
        BankingTransactionDTO bankingTransactionDTO = bankingTransactionMapper.toDto(bankingTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankingTransactionMockMvc.perform(put("/api/banking-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankingTransaction in the database
        List<BankingTransaction> bankingTransactionList = bankingTransactionRepository.findAll();
        assertThat(bankingTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankingTransaction() throws Exception {
        // Initialize the database
        bankingTransactionRepository.saveAndFlush(bankingTransaction);

        int databaseSizeBeforeDelete = bankingTransactionRepository.findAll().size();

        // Delete the bankingTransaction
        restBankingTransactionMockMvc.perform(delete("/api/banking-transactions/{id}", bankingTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankingTransaction> bankingTransactionList = bankingTransactionRepository.findAll();
        assertThat(bankingTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankingTransaction.class);
        BankingTransaction bankingTransaction1 = new BankingTransaction();
        bankingTransaction1.setId(1L);
        BankingTransaction bankingTransaction2 = new BankingTransaction();
        bankingTransaction2.setId(bankingTransaction1.getId());
        assertThat(bankingTransaction1).isEqualTo(bankingTransaction2);
        bankingTransaction2.setId(2L);
        assertThat(bankingTransaction1).isNotEqualTo(bankingTransaction2);
        bankingTransaction1.setId(null);
        assertThat(bankingTransaction1).isNotEqualTo(bankingTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankingTransactionDTO.class);
        BankingTransactionDTO bankingTransactionDTO1 = new BankingTransactionDTO();
        bankingTransactionDTO1.setId(1L);
        BankingTransactionDTO bankingTransactionDTO2 = new BankingTransactionDTO();
        assertThat(bankingTransactionDTO1).isNotEqualTo(bankingTransactionDTO2);
        bankingTransactionDTO2.setId(bankingTransactionDTO1.getId());
        assertThat(bankingTransactionDTO1).isEqualTo(bankingTransactionDTO2);
        bankingTransactionDTO2.setId(2L);
        assertThat(bankingTransactionDTO1).isNotEqualTo(bankingTransactionDTO2);
        bankingTransactionDTO1.setId(null);
        assertThat(bankingTransactionDTO1).isNotEqualTo(bankingTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bankingTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bankingTransactionMapper.fromId(null)).isNull();
    }
}
