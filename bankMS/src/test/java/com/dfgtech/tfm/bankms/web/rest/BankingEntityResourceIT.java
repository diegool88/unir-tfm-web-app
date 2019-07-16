package com.dfgtech.tfm.bankms.web.rest;

import com.dfgtech.tfm.bankms.BankMsApp;
import com.dfgtech.tfm.bankms.domain.BankingEntity;
import com.dfgtech.tfm.bankms.repository.BankingEntityRepository;
import com.dfgtech.tfm.bankms.service.BankingEntityService;
import com.dfgtech.tfm.bankms.service.dto.BankingEntityDTO;
import com.dfgtech.tfm.bankms.service.mapper.BankingEntityMapper;
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

/**
 * Integration tests for the {@Link BankingEntityResource} REST controller.
 */
@SpringBootTest(classes = BankMsApp.class)
public class BankingEntityResourceIT {

    private static final String DEFAULT_MNEMONIC = "AAAAAAAAAA";
    private static final String UPDATED_MNEMONIC = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BankingEntityRepository bankingEntityRepository;

    @Autowired
    private BankingEntityMapper bankingEntityMapper;

    @Autowired
    private BankingEntityService bankingEntityService;

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

    private MockMvc restBankingEntityMockMvc;

    private BankingEntity bankingEntity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BankingEntityResource bankingEntityResource = new BankingEntityResource(bankingEntityService);
        this.restBankingEntityMockMvc = MockMvcBuilders.standaloneSetup(bankingEntityResource)
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
    public static BankingEntity createEntity(EntityManager em) {
        BankingEntity bankingEntity = new BankingEntity()
            .mnemonic(DEFAULT_MNEMONIC)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return bankingEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankingEntity createUpdatedEntity(EntityManager em) {
        BankingEntity bankingEntity = new BankingEntity()
            .mnemonic(UPDATED_MNEMONIC)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return bankingEntity;
    }

    @BeforeEach
    public void initTest() {
        bankingEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankingEntity() throws Exception {
        int databaseSizeBeforeCreate = bankingEntityRepository.findAll().size();

        // Create the BankingEntity
        BankingEntityDTO bankingEntityDTO = bankingEntityMapper.toDto(bankingEntity);
        restBankingEntityMockMvc.perform(post("/api/banking-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the BankingEntity in the database
        List<BankingEntity> bankingEntityList = bankingEntityRepository.findAll();
        assertThat(bankingEntityList).hasSize(databaseSizeBeforeCreate + 1);
        BankingEntity testBankingEntity = bankingEntityList.get(bankingEntityList.size() - 1);
        assertThat(testBankingEntity.getMnemonic()).isEqualTo(DEFAULT_MNEMONIC);
        assertThat(testBankingEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankingEntity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBankingEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankingEntityRepository.findAll().size();

        // Create the BankingEntity with an existing ID
        bankingEntity.setId(1L);
        BankingEntityDTO bankingEntityDTO = bankingEntityMapper.toDto(bankingEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankingEntityMockMvc.perform(post("/api/banking-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankingEntity in the database
        List<BankingEntity> bankingEntityList = bankingEntityRepository.findAll();
        assertThat(bankingEntityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMnemonicIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingEntityRepository.findAll().size();
        // set the field null
        bankingEntity.setMnemonic(null);

        // Create the BankingEntity, which fails.
        BankingEntityDTO bankingEntityDTO = bankingEntityMapper.toDto(bankingEntity);

        restBankingEntityMockMvc.perform(post("/api/banking-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingEntityDTO)))
            .andExpect(status().isBadRequest());

        List<BankingEntity> bankingEntityList = bankingEntityRepository.findAll();
        assertThat(bankingEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankingEntityRepository.findAll().size();
        // set the field null
        bankingEntity.setName(null);

        // Create the BankingEntity, which fails.
        BankingEntityDTO bankingEntityDTO = bankingEntityMapper.toDto(bankingEntity);

        restBankingEntityMockMvc.perform(post("/api/banking-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingEntityDTO)))
            .andExpect(status().isBadRequest());

        List<BankingEntity> bankingEntityList = bankingEntityRepository.findAll();
        assertThat(bankingEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBankingEntities() throws Exception {
        // Initialize the database
        bankingEntityRepository.saveAndFlush(bankingEntity);

        // Get all the bankingEntityList
        restBankingEntityMockMvc.perform(get("/api/banking-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankingEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].mnemonic").value(hasItem(DEFAULT_MNEMONIC.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getBankingEntity() throws Exception {
        // Initialize the database
        bankingEntityRepository.saveAndFlush(bankingEntity);

        // Get the bankingEntity
        restBankingEntityMockMvc.perform(get("/api/banking-entities/{id}", bankingEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankingEntity.getId().intValue()))
            .andExpect(jsonPath("$.mnemonic").value(DEFAULT_MNEMONIC.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBankingEntity() throws Exception {
        // Get the bankingEntity
        restBankingEntityMockMvc.perform(get("/api/banking-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankingEntity() throws Exception {
        // Initialize the database
        bankingEntityRepository.saveAndFlush(bankingEntity);

        int databaseSizeBeforeUpdate = bankingEntityRepository.findAll().size();

        // Update the bankingEntity
        BankingEntity updatedBankingEntity = bankingEntityRepository.findById(bankingEntity.getId()).get();
        // Disconnect from session so that the updates on updatedBankingEntity are not directly saved in db
        em.detach(updatedBankingEntity);
        updatedBankingEntity
            .mnemonic(UPDATED_MNEMONIC)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        BankingEntityDTO bankingEntityDTO = bankingEntityMapper.toDto(updatedBankingEntity);

        restBankingEntityMockMvc.perform(put("/api/banking-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingEntityDTO)))
            .andExpect(status().isOk());

        // Validate the BankingEntity in the database
        List<BankingEntity> bankingEntityList = bankingEntityRepository.findAll();
        assertThat(bankingEntityList).hasSize(databaseSizeBeforeUpdate);
        BankingEntity testBankingEntity = bankingEntityList.get(bankingEntityList.size() - 1);
        assertThat(testBankingEntity.getMnemonic()).isEqualTo(UPDATED_MNEMONIC);
        assertThat(testBankingEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankingEntity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBankingEntity() throws Exception {
        int databaseSizeBeforeUpdate = bankingEntityRepository.findAll().size();

        // Create the BankingEntity
        BankingEntityDTO bankingEntityDTO = bankingEntityMapper.toDto(bankingEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankingEntityMockMvc.perform(put("/api/banking-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankingEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankingEntity in the database
        List<BankingEntity> bankingEntityList = bankingEntityRepository.findAll();
        assertThat(bankingEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankingEntity() throws Exception {
        // Initialize the database
        bankingEntityRepository.saveAndFlush(bankingEntity);

        int databaseSizeBeforeDelete = bankingEntityRepository.findAll().size();

        // Delete the bankingEntity
        restBankingEntityMockMvc.perform(delete("/api/banking-entities/{id}", bankingEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankingEntity> bankingEntityList = bankingEntityRepository.findAll();
        assertThat(bankingEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankingEntity.class);
        BankingEntity bankingEntity1 = new BankingEntity();
        bankingEntity1.setId(1L);
        BankingEntity bankingEntity2 = new BankingEntity();
        bankingEntity2.setId(bankingEntity1.getId());
        assertThat(bankingEntity1).isEqualTo(bankingEntity2);
        bankingEntity2.setId(2L);
        assertThat(bankingEntity1).isNotEqualTo(bankingEntity2);
        bankingEntity1.setId(null);
        assertThat(bankingEntity1).isNotEqualTo(bankingEntity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankingEntityDTO.class);
        BankingEntityDTO bankingEntityDTO1 = new BankingEntityDTO();
        bankingEntityDTO1.setId(1L);
        BankingEntityDTO bankingEntityDTO2 = new BankingEntityDTO();
        assertThat(bankingEntityDTO1).isNotEqualTo(bankingEntityDTO2);
        bankingEntityDTO2.setId(bankingEntityDTO1.getId());
        assertThat(bankingEntityDTO1).isEqualTo(bankingEntityDTO2);
        bankingEntityDTO2.setId(2L);
        assertThat(bankingEntityDTO1).isNotEqualTo(bankingEntityDTO2);
        bankingEntityDTO1.setId(null);
        assertThat(bankingEntityDTO1).isNotEqualTo(bankingEntityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bankingEntityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bankingEntityMapper.fromId(null)).isNull();
    }
}
