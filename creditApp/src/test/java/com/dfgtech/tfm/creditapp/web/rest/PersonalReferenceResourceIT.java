package com.dfgtech.tfm.creditapp.web.rest;

import com.dfgtech.tfm.creditapp.CreditApp;
import com.dfgtech.tfm.creditapp.domain.PersonalReference;
import com.dfgtech.tfm.creditapp.domain.Customer;
import com.dfgtech.tfm.creditapp.repository.PersonalReferenceRepository;
import com.dfgtech.tfm.creditapp.service.PersonalReferenceService;
import com.dfgtech.tfm.creditapp.service.dto.PersonalReferenceDTO;
import com.dfgtech.tfm.creditapp.service.mapper.PersonalReferenceMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.dfgtech.tfm.creditapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dfgtech.tfm.creditapp.domain.enumeration.Genre;
import com.dfgtech.tfm.creditapp.domain.enumeration.Relation;
/**
 * Integration tests for the {@Link PersonalReferenceResource} REST controller.
 */
@SpringBootTest(classes = CreditApp.class)
public class PersonalReferenceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final Genre DEFAULT_GENRE = Genre.MALE;
    private static final Genre UPDATED_GENRE = Genre.FEMALE;

    private static final String DEFAULT_EMAIL = "q@4wyPzh";
    private static final String UPDATED_EMAIL = "xv@YZXgM";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Relation DEFAULT_RELATION = Relation.FRIEND;
    private static final Relation UPDATED_RELATION = Relation.EMPLOYEER;

    @Autowired
    private PersonalReferenceRepository personalReferenceRepository;

    @Autowired
    private PersonalReferenceMapper personalReferenceMapper;

    @Autowired
    private PersonalReferenceService personalReferenceService;

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

    private MockMvc restPersonalReferenceMockMvc;

    private PersonalReference personalReference;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonalReferenceResource personalReferenceResource = new PersonalReferenceResource(personalReferenceService);
        this.restPersonalReferenceMockMvc = MockMvcBuilders.standaloneSetup(personalReferenceResource)
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
    public static PersonalReference createEntity(EntityManager em) {
        PersonalReference personalReference = new PersonalReference()
            .name(DEFAULT_NAME)
            .lastname(DEFAULT_LASTNAME)
            .genre(DEFAULT_GENRE)
            .email(DEFAULT_EMAIL)
            .birthDate(DEFAULT_BIRTH_DATE)
            .relation(DEFAULT_RELATION);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        personalReference.setCustomer(customer);
        return personalReference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalReference createUpdatedEntity(EntityManager em) {
        PersonalReference personalReference = new PersonalReference()
            .name(UPDATED_NAME)
            .lastname(UPDATED_LASTNAME)
            .genre(UPDATED_GENRE)
            .email(UPDATED_EMAIL)
            .birthDate(UPDATED_BIRTH_DATE)
            .relation(UPDATED_RELATION);
        // Add required entity
        Customer customer;
        if (TestUtil.findAll(em, Customer.class).isEmpty()) {
            customer = CustomerResourceIT.createUpdatedEntity(em);
            em.persist(customer);
            em.flush();
        } else {
            customer = TestUtil.findAll(em, Customer.class).get(0);
        }
        personalReference.setCustomer(customer);
        return personalReference;
    }

    @BeforeEach
    public void initTest() {
        personalReference = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonalReference() throws Exception {
        int databaseSizeBeforeCreate = personalReferenceRepository.findAll().size();

        // Create the PersonalReference
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(personalReference);
        restPersonalReferenceMockMvc.perform(post("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonalReference in the database
        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeCreate + 1);
        PersonalReference testPersonalReference = personalReferenceList.get(personalReferenceList.size() - 1);
        assertThat(testPersonalReference.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonalReference.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPersonalReference.getGenre()).isEqualTo(DEFAULT_GENRE);
        assertThat(testPersonalReference.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonalReference.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPersonalReference.getRelation()).isEqualTo(DEFAULT_RELATION);
    }

    @Test
    @Transactional
    public void createPersonalReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personalReferenceRepository.findAll().size();

        // Create the PersonalReference with an existing ID
        personalReference.setId(1L);
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(personalReference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalReferenceMockMvc.perform(post("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonalReference in the database
        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalReferenceRepository.findAll().size();
        // set the field null
        personalReference.setName(null);

        // Create the PersonalReference, which fails.
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(personalReference);

        restPersonalReferenceMockMvc.perform(post("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalReferenceRepository.findAll().size();
        // set the field null
        personalReference.setLastname(null);

        // Create the PersonalReference, which fails.
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(personalReference);

        restPersonalReferenceMockMvc.perform(post("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenreIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalReferenceRepository.findAll().size();
        // set the field null
        personalReference.setGenre(null);

        // Create the PersonalReference, which fails.
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(personalReference);

        restPersonalReferenceMockMvc.perform(post("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalReferenceRepository.findAll().size();
        // set the field null
        personalReference.setEmail(null);

        // Create the PersonalReference, which fails.
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(personalReference);

        restPersonalReferenceMockMvc.perform(post("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalReferenceRepository.findAll().size();
        // set the field null
        personalReference.setBirthDate(null);

        // Create the PersonalReference, which fails.
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(personalReference);

        restPersonalReferenceMockMvc.perform(post("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRelationIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalReferenceRepository.findAll().size();
        // set the field null
        personalReference.setRelation(null);

        // Create the PersonalReference, which fails.
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(personalReference);

        restPersonalReferenceMockMvc.perform(post("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonalReferences() throws Exception {
        // Initialize the database
        personalReferenceRepository.saveAndFlush(personalReference);

        // Get all the personalReferenceList
        restPersonalReferenceMockMvc.perform(get("/api/personal-references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].genre").value(hasItem(DEFAULT_GENRE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())));
    }
    
    @Test
    @Transactional
    public void getPersonalReference() throws Exception {
        // Initialize the database
        personalReferenceRepository.saveAndFlush(personalReference);

        // Get the personalReference
        restPersonalReferenceMockMvc.perform(get("/api/personal-references/{id}", personalReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personalReference.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.genre").value(DEFAULT_GENRE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonalReference() throws Exception {
        // Get the personalReference
        restPersonalReferenceMockMvc.perform(get("/api/personal-references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonalReference() throws Exception {
        // Initialize the database
        personalReferenceRepository.saveAndFlush(personalReference);

        int databaseSizeBeforeUpdate = personalReferenceRepository.findAll().size();

        // Update the personalReference
        PersonalReference updatedPersonalReference = personalReferenceRepository.findById(personalReference.getId()).get();
        // Disconnect from session so that the updates on updatedPersonalReference are not directly saved in db
        em.detach(updatedPersonalReference);
        updatedPersonalReference
            .name(UPDATED_NAME)
            .lastname(UPDATED_LASTNAME)
            .genre(UPDATED_GENRE)
            .email(UPDATED_EMAIL)
            .birthDate(UPDATED_BIRTH_DATE)
            .relation(UPDATED_RELATION);
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(updatedPersonalReference);

        restPersonalReferenceMockMvc.perform(put("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isOk());

        // Validate the PersonalReference in the database
        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeUpdate);
        PersonalReference testPersonalReference = personalReferenceList.get(personalReferenceList.size() - 1);
        assertThat(testPersonalReference.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonalReference.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPersonalReference.getGenre()).isEqualTo(UPDATED_GENRE);
        assertThat(testPersonalReference.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonalReference.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPersonalReference.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonalReference() throws Exception {
        int databaseSizeBeforeUpdate = personalReferenceRepository.findAll().size();

        // Create the PersonalReference
        PersonalReferenceDTO personalReferenceDTO = personalReferenceMapper.toDto(personalReference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalReferenceMockMvc.perform(put("/api/personal-references")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personalReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonalReference in the database
        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonalReference() throws Exception {
        // Initialize the database
        personalReferenceRepository.saveAndFlush(personalReference);

        int databaseSizeBeforeDelete = personalReferenceRepository.findAll().size();

        // Delete the personalReference
        restPersonalReferenceMockMvc.perform(delete("/api/personal-references/{id}", personalReference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonalReference> personalReferenceList = personalReferenceRepository.findAll();
        assertThat(personalReferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalReference.class);
        PersonalReference personalReference1 = new PersonalReference();
        personalReference1.setId(1L);
        PersonalReference personalReference2 = new PersonalReference();
        personalReference2.setId(personalReference1.getId());
        assertThat(personalReference1).isEqualTo(personalReference2);
        personalReference2.setId(2L);
        assertThat(personalReference1).isNotEqualTo(personalReference2);
        personalReference1.setId(null);
        assertThat(personalReference1).isNotEqualTo(personalReference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalReferenceDTO.class);
        PersonalReferenceDTO personalReferenceDTO1 = new PersonalReferenceDTO();
        personalReferenceDTO1.setId(1L);
        PersonalReferenceDTO personalReferenceDTO2 = new PersonalReferenceDTO();
        assertThat(personalReferenceDTO1).isNotEqualTo(personalReferenceDTO2);
        personalReferenceDTO2.setId(personalReferenceDTO1.getId());
        assertThat(personalReferenceDTO1).isEqualTo(personalReferenceDTO2);
        personalReferenceDTO2.setId(2L);
        assertThat(personalReferenceDTO1).isNotEqualTo(personalReferenceDTO2);
        personalReferenceDTO1.setId(null);
        assertThat(personalReferenceDTO1).isNotEqualTo(personalReferenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personalReferenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personalReferenceMapper.fromId(null)).isNull();
    }
}
