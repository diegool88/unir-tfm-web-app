package com.dfgtech.tfm.bankms.web.rest;

import com.dfgtech.tfm.bankms.BankMsApp;
import com.dfgtech.tfm.bankms.domain.BankingAccount;
import com.dfgtech.tfm.bankms.repository.BankingAccountRepository;
import com.dfgtech.tfm.bankms.service.BankingAccountService;
import com.dfgtech.tfm.bankms.service.dto.BankingAccountDTO;
import com.dfgtech.tfm.bankms.service.mapper.BankingAccountMapper;
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
import java.util.List;

import static com.dfgtech.tfm.bankms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dfgtech.tfm.bankms.domain.enumeration.AccountType;
/**
 * Integration tests for the {@Link BankingAccountResource} REST controller.
 */
@SpringBootTest(classes = BankMsApp.class)
public class BankingAccountResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.SAVINGS;
    private static final AccountType UPDATED_ACCOUNT_TYPE = AccountType.CHECKING;

    private static final Double DEFAULT_CURRENT_BALANCE = 1D;
    private static final Double UPDATED_CURRENT_BALANCE = 2D;

    private static final Double DEFAULT_AVAILABLE_BALANCE = 1D;
    private static final Double UPDATED_AVAILABLE_BALANCE = 2D;

    private static final String DEFAULT_CUSTOMER_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_IDENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_IDENTIFICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_IDENTIFICATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    @Autowired
    private BankingAccountMapper bankingAccountMapper;

    @Autowired
    private BankingAccountService bankingAccountService;

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

    private MockMvc restBankingAccountMockMvc;

    private BankingAccount bankingAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BankingAccountResource bankingAccountResource = new BankingAccountResource(bankingAccountService);
        this.restBankingAccountMockMvc = MockMvcBuilders.standaloneSetup(bankingAccountResource)
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
    public static BankingAccount createEntity(EntityManager em) {
        BankingAccount bankingAccount = new BankingAccount()
            .number(DEFAULT_NUMBER)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .currentBalance(DEFAULT_CURRENT_BALANCE)
            .availableBalance(DEFAULT_AVAILABLE_BALANCE)
            .customerIdentification(DEFAULT_CUSTOMER_IDENTIFICATION)
            .customerIdentificationType(DEFAULT_CUSTOMER_IDENTIFICATION_TYPE)
            .customerCountry(DEFAULT_CUSTOMER_COUNTRY);
        return bankingAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankingAccount createUpdatedEntity(EntityManager em) {
        BankingAccount bankingAccount = new BankingAccount()
            .number(UPDATED_NUMBER)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .currentBalance(UPDATED_CURRENT_BALANCE)
            .availableBalance(UPDATED_AVAILABLE_BALANCE)
            .customerIdentification(UPDATED_CUSTOMER_IDENTIFICATION)
            .customerIdentificationType(UPDATED_CUSTOMER_IDENTIFICATION_TYPE)
            .customerCountry(UPDATED_CUSTOMER_COUNTRY);
        return bankingAccount;
    }

    @BeforeEach
    public void initTest() {
        bankingAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankingAccount() throws Exception {
        int databaseSizeBeforeCreate = bankingAccountRepository.findAll().size();

        // Create the BankingAccount
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);
        restBankingAccountMockMvc.perform(post("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the BankingAccount in the database
        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeCreate + 1);
        BankingAccount testBankingAccount = bankingAccountList.get(bankingAccountList.size() - 1);
        assertThat(testBankingAccount.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBankingAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testBankingAccount.getCurrentBalance()).isEqualTo(DEFAULT_CURRENT_BALANCE);
        assertThat(testBankingAccount.getAvailableBalance()).isEqualTo(DEFAULT_AVAILABLE_BALANCE);
        assertThat(testBankingAccount.getCustomerIdentification()).isEqualTo(DEFAULT_CUSTOMER_IDENTIFICATION);
        assertThat(testBankingAccount.getCustomerIdentificationType()).isEqualTo(DEFAULT_CUSTOMER_IDENTIFICATION_TYPE);
        assertThat(testBankingAccount.getCustomerCountry()).isEqualTo(DEFAULT_CUSTOMER_COUNTRY);
    }

    @Test
    @Transactional
    public void createBankingAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankingAccountRepository.findAll().size();

        // Create the BankingAccount with an existing ID
        bankingAccount.setId(1L);
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankingAccountMockMvc.perform(post("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankingAccount in the database
        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingAccountRepository.findAll().size();
        // set the field null
        bankingAccount.setNumber(null);

        // Create the BankingAccount, which fails.
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);

        restBankingAccountMockMvc.perform(post("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isBadRequest());

        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingAccountRepository.findAll().size();
        // set the field null
        bankingAccount.setAccountType(null);

        // Create the BankingAccount, which fails.
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);

        restBankingAccountMockMvc.perform(post("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isBadRequest());

        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingAccountRepository.findAll().size();
        // set the field null
        bankingAccount.setCurrentBalance(null);

        // Create the BankingAccount, which fails.
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);

        restBankingAccountMockMvc.perform(post("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isBadRequest());

        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingAccountRepository.findAll().size();
        // set the field null
        bankingAccount.setAvailableBalance(null);

        // Create the BankingAccount, which fails.
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);

        restBankingAccountMockMvc.perform(post("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isBadRequest());

        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerIdentificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingAccountRepository.findAll().size();
        // set the field null
        bankingAccount.setCustomerIdentification(null);

        // Create the BankingAccount, which fails.
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);

        restBankingAccountMockMvc.perform(post("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isBadRequest());

        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerIdentificationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingAccountRepository.findAll().size();
        // set the field null
        bankingAccount.setCustomerIdentificationType(null);

        // Create the BankingAccount, which fails.
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);

        restBankingAccountMockMvc.perform(post("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isBadRequest());

        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingAccountRepository.findAll().size();
        // set the field null
        bankingAccount.setCustomerCountry(null);

        // Create the BankingAccount, which fails.
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);

        restBankingAccountMockMvc.perform(post("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isBadRequest());

        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBankingAccounts() throws Exception {
        // Initialize the database
        bankingAccountRepository.saveAndFlush(bankingAccount);

        // Get all the bankingAccountList
        restBankingAccountMockMvc.perform(get("/api/banking-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankingAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].currentBalance").value(hasItem(DEFAULT_CURRENT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].availableBalance").value(hasItem(DEFAULT_AVAILABLE_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].customerIdentification").value(hasItem(DEFAULT_CUSTOMER_IDENTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].customerIdentificationType").value(hasItem(DEFAULT_CUSTOMER_IDENTIFICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].customerCountry").value(hasItem(DEFAULT_CUSTOMER_COUNTRY.toString())));
    }
    
    @Test
    @Transactional
    public void getBankingAccount() throws Exception {
        // Initialize the database
        bankingAccountRepository.saveAndFlush(bankingAccount);

        // Get the bankingAccount
        restBankingAccountMockMvc.perform(get("/api/banking-accounts/{id}", bankingAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankingAccount.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.currentBalance").value(DEFAULT_CURRENT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.availableBalance").value(DEFAULT_AVAILABLE_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.customerIdentification").value(DEFAULT_CUSTOMER_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.customerIdentificationType").value(DEFAULT_CUSTOMER_IDENTIFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.customerCountry").value(DEFAULT_CUSTOMER_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankingAccount() throws Exception {
        // Get the bankingAccount
        restBankingAccountMockMvc.perform(get("/api/banking-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankingAccount() throws Exception {
        // Initialize the database
        bankingAccountRepository.saveAndFlush(bankingAccount);

        int databaseSizeBeforeUpdate = bankingAccountRepository.findAll().size();

        // Update the bankingAccount
        BankingAccount updatedBankingAccount = bankingAccountRepository.findById(bankingAccount.getId()).get();
        // Disconnect from session so that the updates on updatedBankingAccount are not directly saved in db
        em.detach(updatedBankingAccount);
        updatedBankingAccount
            .number(UPDATED_NUMBER)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .currentBalance(UPDATED_CURRENT_BALANCE)
            .availableBalance(UPDATED_AVAILABLE_BALANCE)
            .customerIdentification(UPDATED_CUSTOMER_IDENTIFICATION)
            .customerIdentificationType(UPDATED_CUSTOMER_IDENTIFICATION_TYPE)
            .customerCountry(UPDATED_CUSTOMER_COUNTRY);
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(updatedBankingAccount);

        restBankingAccountMockMvc.perform(put("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isOk());

        // Validate the BankingAccount in the database
        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeUpdate);
        BankingAccount testBankingAccount = bankingAccountList.get(bankingAccountList.size() - 1);
        assertThat(testBankingAccount.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBankingAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testBankingAccount.getCurrentBalance()).isEqualTo(UPDATED_CURRENT_BALANCE);
        assertThat(testBankingAccount.getAvailableBalance()).isEqualTo(UPDATED_AVAILABLE_BALANCE);
        assertThat(testBankingAccount.getCustomerIdentification()).isEqualTo(UPDATED_CUSTOMER_IDENTIFICATION);
        assertThat(testBankingAccount.getCustomerIdentificationType()).isEqualTo(UPDATED_CUSTOMER_IDENTIFICATION_TYPE);
        assertThat(testBankingAccount.getCustomerCountry()).isEqualTo(UPDATED_CUSTOMER_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingBankingAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankingAccountRepository.findAll().size();

        // Create the BankingAccount
        BankingAccountDTO bankingAccountDTO = bankingAccountMapper.toDto(bankingAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankingAccountMockMvc.perform(put("/api/banking-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankingAccount in the database
        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankingAccount() throws Exception {
        // Initialize the database
        bankingAccountRepository.saveAndFlush(bankingAccount);

        int databaseSizeBeforeDelete = bankingAccountRepository.findAll().size();

        // Delete the bankingAccount
        restBankingAccountMockMvc.perform(delete("/api/banking-accounts/{id}", bankingAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankingAccount> bankingAccountList = bankingAccountRepository.findAll();
        assertThat(bankingAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankingAccount.class);
        BankingAccount bankingAccount1 = new BankingAccount();
        bankingAccount1.setId(1L);
        BankingAccount bankingAccount2 = new BankingAccount();
        bankingAccount2.setId(bankingAccount1.getId());
        assertThat(bankingAccount1).isEqualTo(bankingAccount2);
        bankingAccount2.setId(2L);
        assertThat(bankingAccount1).isNotEqualTo(bankingAccount2);
        bankingAccount1.setId(null);
        assertThat(bankingAccount1).isNotEqualTo(bankingAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankingAccountDTO.class);
        BankingAccountDTO bankingAccountDTO1 = new BankingAccountDTO();
        bankingAccountDTO1.setId(1L);
        BankingAccountDTO bankingAccountDTO2 = new BankingAccountDTO();
        assertThat(bankingAccountDTO1).isNotEqualTo(bankingAccountDTO2);
        bankingAccountDTO2.setId(bankingAccountDTO1.getId());
        assertThat(bankingAccountDTO1).isEqualTo(bankingAccountDTO2);
        bankingAccountDTO2.setId(2L);
        assertThat(bankingAccountDTO1).isNotEqualTo(bankingAccountDTO2);
        bankingAccountDTO1.setId(null);
        assertThat(bankingAccountDTO1).isNotEqualTo(bankingAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bankingAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bankingAccountMapper.fromId(null)).isNull();
    }
}
