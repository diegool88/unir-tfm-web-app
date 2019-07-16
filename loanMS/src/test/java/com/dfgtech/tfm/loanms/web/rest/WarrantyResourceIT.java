package com.dfgtech.tfm.loanms.web.rest;

import com.dfgtech.tfm.loanms.LoanMsApp;
import com.dfgtech.tfm.loanms.domain.Warranty;
import com.dfgtech.tfm.loanms.domain.LoanProcess;
import com.dfgtech.tfm.loanms.repository.WarrantyRepository;
import com.dfgtech.tfm.loanms.service.WarrantyService;
import com.dfgtech.tfm.loanms.service.dto.WarrantyDTO;
import com.dfgtech.tfm.loanms.service.mapper.WarrantyMapper;
import com.dfgtech.tfm.loanms.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.dfgtech.tfm.loanms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link WarrantyResource} REST controller.
 */
@SpringBootTest(classes = LoanMsApp.class)
public class WarrantyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final byte[] DEFAULT_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DEBTOR_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_DEBTOR_IDENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_DEBTOR_IDENTIFICATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEBTOR_IDENTIFICATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEBTOR_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_DEBTOR_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private WarrantyRepository warrantyRepository;

    @Mock
    private WarrantyRepository warrantyRepositoryMock;

    @Autowired
    private WarrantyMapper warrantyMapper;

    @Mock
    private WarrantyService warrantyServiceMock;

    @Autowired
    private WarrantyService warrantyService;

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

    private MockMvc restWarrantyMockMvc;

    private Warranty warranty;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WarrantyResource warrantyResource = new WarrantyResource(warrantyService);
        this.restWarrantyMockMvc = MockMvcBuilders.standaloneSetup(warrantyResource)
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
    public static Warranty createEntity(EntityManager em) {
        Warranty warranty = new Warranty()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE)
            .document(DEFAULT_DOCUMENT)
            .documentContentType(DEFAULT_DOCUMENT_CONTENT_TYPE)
            .debtorIdentification(DEFAULT_DEBTOR_IDENTIFICATION)
            .debtorIdentificationType(DEFAULT_DEBTOR_IDENTIFICATION_TYPE)
            .debtorCountry(DEFAULT_DEBTOR_COUNTRY);
        // Add required entity
        LoanProcess loanProcess;
        if (TestUtil.findAll(em, LoanProcess.class).isEmpty()) {
            loanProcess = LoanProcessResourceIT.createEntity(em);
            em.persist(loanProcess);
            em.flush();
        } else {
            loanProcess = TestUtil.findAll(em, LoanProcess.class).get(0);
        }
        warranty.getLoanProcesses().add(loanProcess);
        return warranty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Warranty createUpdatedEntity(EntityManager em) {
        Warranty warranty = new Warranty()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE)
            .debtorIdentification(UPDATED_DEBTOR_IDENTIFICATION)
            .debtorIdentificationType(UPDATED_DEBTOR_IDENTIFICATION_TYPE)
            .debtorCountry(UPDATED_DEBTOR_COUNTRY);
        // Add required entity
        LoanProcess loanProcess;
        if (TestUtil.findAll(em, LoanProcess.class).isEmpty()) {
            loanProcess = LoanProcessResourceIT.createUpdatedEntity(em);
            em.persist(loanProcess);
            em.flush();
        } else {
            loanProcess = TestUtil.findAll(em, LoanProcess.class).get(0);
        }
        warranty.getLoanProcesses().add(loanProcess);
        return warranty;
    }

    @BeforeEach
    public void initTest() {
        warranty = createEntity(em);
    }

    @Test
    @Transactional
    public void createWarranty() throws Exception {
        int databaseSizeBeforeCreate = warrantyRepository.findAll().size();

        // Create the Warranty
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);
        restWarrantyMockMvc.perform(post("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isCreated());

        // Validate the Warranty in the database
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeCreate + 1);
        Warranty testWarranty = warrantyList.get(warrantyList.size() - 1);
        assertThat(testWarranty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWarranty.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWarranty.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testWarranty.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testWarranty.getDocumentContentType()).isEqualTo(DEFAULT_DOCUMENT_CONTENT_TYPE);
        assertThat(testWarranty.getDebtorIdentification()).isEqualTo(DEFAULT_DEBTOR_IDENTIFICATION);
        assertThat(testWarranty.getDebtorIdentificationType()).isEqualTo(DEFAULT_DEBTOR_IDENTIFICATION_TYPE);
        assertThat(testWarranty.getDebtorCountry()).isEqualTo(DEFAULT_DEBTOR_COUNTRY);
    }

    @Test
    @Transactional
    public void createWarrantyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = warrantyRepository.findAll().size();

        // Create the Warranty with an existing ID
        warranty.setId(1L);
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarrantyMockMvc.perform(post("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Warranty in the database
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyRepository.findAll().size();
        // set the field null
        warranty.setName(null);

        // Create the Warranty, which fails.
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);

        restWarrantyMockMvc.perform(post("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isBadRequest());

        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyRepository.findAll().size();
        // set the field null
        warranty.setValue(null);

        // Create the Warranty, which fails.
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);

        restWarrantyMockMvc.perform(post("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isBadRequest());

        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDebtorIdentificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyRepository.findAll().size();
        // set the field null
        warranty.setDebtorIdentification(null);

        // Create the Warranty, which fails.
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);

        restWarrantyMockMvc.perform(post("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isBadRequest());

        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDebtorIdentificationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyRepository.findAll().size();
        // set the field null
        warranty.setDebtorIdentificationType(null);

        // Create the Warranty, which fails.
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);

        restWarrantyMockMvc.perform(post("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isBadRequest());

        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDebtorCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyRepository.findAll().size();
        // set the field null
        warranty.setDebtorCountry(null);

        // Create the Warranty, which fails.
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);

        restWarrantyMockMvc.perform(post("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isBadRequest());

        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWarranties() throws Exception {
        // Initialize the database
        warrantyRepository.saveAndFlush(warranty);

        // Get all the warrantyList
        restWarrantyMockMvc.perform(get("/api/warranties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warranty.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].documentContentType").value(hasItem(DEFAULT_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT))))
            .andExpect(jsonPath("$.[*].debtorIdentification").value(hasItem(DEFAULT_DEBTOR_IDENTIFICATION.toString())))
            .andExpect(jsonPath("$.[*].debtorIdentificationType").value(hasItem(DEFAULT_DEBTOR_IDENTIFICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].debtorCountry").value(hasItem(DEFAULT_DEBTOR_COUNTRY.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllWarrantiesWithEagerRelationshipsIsEnabled() throws Exception {
        WarrantyResource warrantyResource = new WarrantyResource(warrantyServiceMock);
        when(warrantyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restWarrantyMockMvc = MockMvcBuilders.standaloneSetup(warrantyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWarrantyMockMvc.perform(get("/api/warranties?eagerload=true"))
        .andExpect(status().isOk());

        verify(warrantyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllWarrantiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        WarrantyResource warrantyResource = new WarrantyResource(warrantyServiceMock);
            when(warrantyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restWarrantyMockMvc = MockMvcBuilders.standaloneSetup(warrantyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restWarrantyMockMvc.perform(get("/api/warranties?eagerload=true"))
        .andExpect(status().isOk());

            verify(warrantyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getWarranty() throws Exception {
        // Initialize the database
        warrantyRepository.saveAndFlush(warranty);

        // Get the warranty
        restWarrantyMockMvc.perform(get("/api/warranties/{id}", warranty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(warranty.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.documentContentType").value(DEFAULT_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.document").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT)))
            .andExpect(jsonPath("$.debtorIdentification").value(DEFAULT_DEBTOR_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.debtorIdentificationType").value(DEFAULT_DEBTOR_IDENTIFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.debtorCountry").value(DEFAULT_DEBTOR_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWarranty() throws Exception {
        // Get the warranty
        restWarrantyMockMvc.perform(get("/api/warranties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWarranty() throws Exception {
        // Initialize the database
        warrantyRepository.saveAndFlush(warranty);

        int databaseSizeBeforeUpdate = warrantyRepository.findAll().size();

        // Update the warranty
        Warranty updatedWarranty = warrantyRepository.findById(warranty.getId()).get();
        // Disconnect from session so that the updates on updatedWarranty are not directly saved in db
        em.detach(updatedWarranty);
        updatedWarranty
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE)
            .debtorIdentification(UPDATED_DEBTOR_IDENTIFICATION)
            .debtorIdentificationType(UPDATED_DEBTOR_IDENTIFICATION_TYPE)
            .debtorCountry(UPDATED_DEBTOR_COUNTRY);
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(updatedWarranty);

        restWarrantyMockMvc.perform(put("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isOk());

        // Validate the Warranty in the database
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeUpdate);
        Warranty testWarranty = warrantyList.get(warrantyList.size() - 1);
        assertThat(testWarranty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWarranty.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWarranty.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testWarranty.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testWarranty.getDocumentContentType()).isEqualTo(UPDATED_DOCUMENT_CONTENT_TYPE);
        assertThat(testWarranty.getDebtorIdentification()).isEqualTo(UPDATED_DEBTOR_IDENTIFICATION);
        assertThat(testWarranty.getDebtorIdentificationType()).isEqualTo(UPDATED_DEBTOR_IDENTIFICATION_TYPE);
        assertThat(testWarranty.getDebtorCountry()).isEqualTo(UPDATED_DEBTOR_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingWarranty() throws Exception {
        int databaseSizeBeforeUpdate = warrantyRepository.findAll().size();

        // Create the Warranty
        WarrantyDTO warrantyDTO = warrantyMapper.toDto(warranty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyMockMvc.perform(put("/api/warranties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Warranty in the database
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWarranty() throws Exception {
        // Initialize the database
        warrantyRepository.saveAndFlush(warranty);

        int databaseSizeBeforeDelete = warrantyRepository.findAll().size();

        // Delete the warranty
        restWarrantyMockMvc.perform(delete("/api/warranties/{id}", warranty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Warranty> warrantyList = warrantyRepository.findAll();
        assertThat(warrantyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Warranty.class);
        Warranty warranty1 = new Warranty();
        warranty1.setId(1L);
        Warranty warranty2 = new Warranty();
        warranty2.setId(warranty1.getId());
        assertThat(warranty1).isEqualTo(warranty2);
        warranty2.setId(2L);
        assertThat(warranty1).isNotEqualTo(warranty2);
        warranty1.setId(null);
        assertThat(warranty1).isNotEqualTo(warranty2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyDTO.class);
        WarrantyDTO warrantyDTO1 = new WarrantyDTO();
        warrantyDTO1.setId(1L);
        WarrantyDTO warrantyDTO2 = new WarrantyDTO();
        assertThat(warrantyDTO1).isNotEqualTo(warrantyDTO2);
        warrantyDTO2.setId(warrantyDTO1.getId());
        assertThat(warrantyDTO1).isEqualTo(warrantyDTO2);
        warrantyDTO2.setId(2L);
        assertThat(warrantyDTO1).isNotEqualTo(warrantyDTO2);
        warrantyDTO1.setId(null);
        assertThat(warrantyDTO1).isNotEqualTo(warrantyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(warrantyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(warrantyMapper.fromId(null)).isNull();
    }
}
