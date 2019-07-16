package com.dfgtech.tfm.loanms.web.rest;

import com.dfgtech.tfm.loanms.LoanMsApp;
import com.dfgtech.tfm.loanms.domain.AmortizationTable;
import com.dfgtech.tfm.loanms.domain.LoanProcess;
import com.dfgtech.tfm.loanms.repository.AmortizationTableRepository;
import com.dfgtech.tfm.loanms.service.AmortizationTableService;
import com.dfgtech.tfm.loanms.service.dto.AmortizationTableDTO;
import com.dfgtech.tfm.loanms.service.mapper.AmortizationTableMapper;
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

/**
 * Integration tests for the {@Link AmortizationTableResource} REST controller.
 */
@SpringBootTest(classes = LoanMsApp.class)
public class AmortizationTableResourceIT {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_INTEREST = 1D;
    private static final Double UPDATED_INTEREST = 2D;

    @Autowired
    private AmortizationTableRepository amortizationTableRepository;

    @Autowired
    private AmortizationTableMapper amortizationTableMapper;

    @Autowired
    private AmortizationTableService amortizationTableService;

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

    private MockMvc restAmortizationTableMockMvc;

    private AmortizationTable amortizationTable;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmortizationTableResource amortizationTableResource = new AmortizationTableResource(amortizationTableService);
        this.restAmortizationTableMockMvc = MockMvcBuilders.standaloneSetup(amortizationTableResource)
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
    public static AmortizationTable createEntity(EntityManager em) {
        AmortizationTable amortizationTable = new AmortizationTable()
            .order(DEFAULT_ORDER)
            .dueDate(DEFAULT_DUE_DATE)
            .amount(DEFAULT_AMOUNT)
            .interest(DEFAULT_INTEREST);
        // Add required entity
        LoanProcess loanProcess;
        if (TestUtil.findAll(em, LoanProcess.class).isEmpty()) {
            loanProcess = LoanProcessResourceIT.createEntity(em);
            em.persist(loanProcess);
            em.flush();
        } else {
            loanProcess = TestUtil.findAll(em, LoanProcess.class).get(0);
        }
        amortizationTable.setLoanProcess(loanProcess);
        return amortizationTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmortizationTable createUpdatedEntity(EntityManager em) {
        AmortizationTable amortizationTable = new AmortizationTable()
            .order(UPDATED_ORDER)
            .dueDate(UPDATED_DUE_DATE)
            .amount(UPDATED_AMOUNT)
            .interest(UPDATED_INTEREST);
        // Add required entity
        LoanProcess loanProcess;
        if (TestUtil.findAll(em, LoanProcess.class).isEmpty()) {
            loanProcess = LoanProcessResourceIT.createUpdatedEntity(em);
            em.persist(loanProcess);
            em.flush();
        } else {
            loanProcess = TestUtil.findAll(em, LoanProcess.class).get(0);
        }
        amortizationTable.setLoanProcess(loanProcess);
        return amortizationTable;
    }

    @BeforeEach
    public void initTest() {
        amortizationTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmortizationTable() throws Exception {
        int databaseSizeBeforeCreate = amortizationTableRepository.findAll().size();

        // Create the AmortizationTable
        AmortizationTableDTO amortizationTableDTO = amortizationTableMapper.toDto(amortizationTable);
        restAmortizationTableMockMvc.perform(post("/api/amortization-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationTableDTO)))
            .andExpect(status().isCreated());

        // Validate the AmortizationTable in the database
        List<AmortizationTable> amortizationTableList = amortizationTableRepository.findAll();
        assertThat(amortizationTableList).hasSize(databaseSizeBeforeCreate + 1);
        AmortizationTable testAmortizationTable = amortizationTableList.get(amortizationTableList.size() - 1);
        assertThat(testAmortizationTable.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testAmortizationTable.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testAmortizationTable.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAmortizationTable.getInterest()).isEqualTo(DEFAULT_INTEREST);
    }

    @Test
    @Transactional
    public void createAmortizationTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amortizationTableRepository.findAll().size();

        // Create the AmortizationTable with an existing ID
        amortizationTable.setId(1L);
        AmortizationTableDTO amortizationTableDTO = amortizationTableMapper.toDto(amortizationTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizationTableMockMvc.perform(post("/api/amortization-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationTable in the database
        List<AmortizationTable> amortizationTableList = amortizationTableRepository.findAll();
        assertThat(amortizationTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationTableRepository.findAll().size();
        // set the field null
        amortizationTable.setOrder(null);

        // Create the AmortizationTable, which fails.
        AmortizationTableDTO amortizationTableDTO = amortizationTableMapper.toDto(amortizationTable);

        restAmortizationTableMockMvc.perform(post("/api/amortization-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationTableDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationTable> amortizationTableList = amortizationTableRepository.findAll();
        assertThat(amortizationTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationTableRepository.findAll().size();
        // set the field null
        amortizationTable.setDueDate(null);

        // Create the AmortizationTable, which fails.
        AmortizationTableDTO amortizationTableDTO = amortizationTableMapper.toDto(amortizationTable);

        restAmortizationTableMockMvc.perform(post("/api/amortization-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationTableDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationTable> amortizationTableList = amortizationTableRepository.findAll();
        assertThat(amortizationTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationTableRepository.findAll().size();
        // set the field null
        amortizationTable.setAmount(null);

        // Create the AmortizationTable, which fails.
        AmortizationTableDTO amortizationTableDTO = amortizationTableMapper.toDto(amortizationTable);

        restAmortizationTableMockMvc.perform(post("/api/amortization-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationTableDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationTable> amortizationTableList = amortizationTableRepository.findAll();
        assertThat(amortizationTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInterestIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationTableRepository.findAll().size();
        // set the field null
        amortizationTable.setInterest(null);

        // Create the AmortizationTable, which fails.
        AmortizationTableDTO amortizationTableDTO = amortizationTableMapper.toDto(amortizationTable);

        restAmortizationTableMockMvc.perform(post("/api/amortization-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationTableDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationTable> amortizationTableList = amortizationTableRepository.findAll();
        assertThat(amortizationTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmortizationTables() throws Exception {
        // Initialize the database
        amortizationTableRepository.saveAndFlush(amortizationTable);

        // Get all the amortizationTableList
        restAmortizationTableMockMvc.perform(get("/api/amortization-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].interest").value(hasItem(DEFAULT_INTEREST.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAmortizationTable() throws Exception {
        // Initialize the database
        amortizationTableRepository.saveAndFlush(amortizationTable);

        // Get the amortizationTable
        restAmortizationTableMockMvc.perform(get("/api/amortization-tables/{id}", amortizationTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amortizationTable.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.interest").value(DEFAULT_INTEREST.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAmortizationTable() throws Exception {
        // Get the amortizationTable
        restAmortizationTableMockMvc.perform(get("/api/amortization-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmortizationTable() throws Exception {
        // Initialize the database
        amortizationTableRepository.saveAndFlush(amortizationTable);

        int databaseSizeBeforeUpdate = amortizationTableRepository.findAll().size();

        // Update the amortizationTable
        AmortizationTable updatedAmortizationTable = amortizationTableRepository.findById(amortizationTable.getId()).get();
        // Disconnect from session so that the updates on updatedAmortizationTable are not directly saved in db
        em.detach(updatedAmortizationTable);
        updatedAmortizationTable
            .order(UPDATED_ORDER)
            .dueDate(UPDATED_DUE_DATE)
            .amount(UPDATED_AMOUNT)
            .interest(UPDATED_INTEREST);
        AmortizationTableDTO amortizationTableDTO = amortizationTableMapper.toDto(updatedAmortizationTable);

        restAmortizationTableMockMvc.perform(put("/api/amortization-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationTableDTO)))
            .andExpect(status().isOk());

        // Validate the AmortizationTable in the database
        List<AmortizationTable> amortizationTableList = amortizationTableRepository.findAll();
        assertThat(amortizationTableList).hasSize(databaseSizeBeforeUpdate);
        AmortizationTable testAmortizationTable = amortizationTableList.get(amortizationTableList.size() - 1);
        assertThat(testAmortizationTable.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testAmortizationTable.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testAmortizationTable.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAmortizationTable.getInterest()).isEqualTo(UPDATED_INTEREST);
    }

    @Test
    @Transactional
    public void updateNonExistingAmortizationTable() throws Exception {
        int databaseSizeBeforeUpdate = amortizationTableRepository.findAll().size();

        // Create the AmortizationTable
        AmortizationTableDTO amortizationTableDTO = amortizationTableMapper.toDto(amortizationTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationTableMockMvc.perform(put("/api/amortization-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationTable in the database
        List<AmortizationTable> amortizationTableList = amortizationTableRepository.findAll();
        assertThat(amortizationTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAmortizationTable() throws Exception {
        // Initialize the database
        amortizationTableRepository.saveAndFlush(amortizationTable);

        int databaseSizeBeforeDelete = amortizationTableRepository.findAll().size();

        // Delete the amortizationTable
        restAmortizationTableMockMvc.perform(delete("/api/amortization-tables/{id}", amortizationTable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AmortizationTable> amortizationTableList = amortizationTableRepository.findAll();
        assertThat(amortizationTableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationTable.class);
        AmortizationTable amortizationTable1 = new AmortizationTable();
        amortizationTable1.setId(1L);
        AmortizationTable amortizationTable2 = new AmortizationTable();
        amortizationTable2.setId(amortizationTable1.getId());
        assertThat(amortizationTable1).isEqualTo(amortizationTable2);
        amortizationTable2.setId(2L);
        assertThat(amortizationTable1).isNotEqualTo(amortizationTable2);
        amortizationTable1.setId(null);
        assertThat(amortizationTable1).isNotEqualTo(amortizationTable2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationTableDTO.class);
        AmortizationTableDTO amortizationTableDTO1 = new AmortizationTableDTO();
        amortizationTableDTO1.setId(1L);
        AmortizationTableDTO amortizationTableDTO2 = new AmortizationTableDTO();
        assertThat(amortizationTableDTO1).isNotEqualTo(amortizationTableDTO2);
        amortizationTableDTO2.setId(amortizationTableDTO1.getId());
        assertThat(amortizationTableDTO1).isEqualTo(amortizationTableDTO2);
        amortizationTableDTO2.setId(2L);
        assertThat(amortizationTableDTO1).isNotEqualTo(amortizationTableDTO2);
        amortizationTableDTO1.setId(null);
        assertThat(amortizationTableDTO1).isNotEqualTo(amortizationTableDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amortizationTableMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amortizationTableMapper.fromId(null)).isNull();
    }
}
