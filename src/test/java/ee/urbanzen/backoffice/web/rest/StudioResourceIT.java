package ee.urbanzen.backoffice.web.rest;

import ee.urbanzen.backoffice.UrbanZenApp;
import ee.urbanzen.backoffice.domain.Studio;
import ee.urbanzen.backoffice.repository.StudioRepository;
import ee.urbanzen.backoffice.web.rest.errors.ExceptionTranslator;

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

import static ee.urbanzen.backoffice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StudioResource} REST controller.
 */
@SpringBootTest(classes = UrbanZenApp.class)
public class StudioResourceIT {

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_POST_CODE = "AAAAA";
    private static final String UPDATED_POST_CODE = "BBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private StudioRepository studioRepository;

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

    private MockMvc restStudioMockMvc;

    private Studio studio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudioResource studioResource = new StudioResource(studioRepository);
        this.restStudioMockMvc = MockMvcBuilders.standaloneSetup(studioResource)
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
    public static Studio createEntity(EntityManager em) {
        Studio studio = new Studio()
            .about(DEFAULT_ABOUT)
            .street(DEFAULT_STREET)
            .postCode(DEFAULT_POST_CODE)
            .city(DEFAULT_CITY)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return studio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Studio createUpdatedEntity(EntityManager em) {
        Studio studio = new Studio()
            .about(UPDATED_ABOUT)
            .street(UPDATED_STREET)
            .postCode(UPDATED_POST_CODE)
            .city(UPDATED_CITY)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        return studio;
    }

    @BeforeEach
    public void initTest() {
        studio = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudio() throws Exception {
        int databaseSizeBeforeCreate = studioRepository.findAll().size();

        // Create the Studio
        restStudioMockMvc.perform(post("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studio)))
            .andExpect(status().isCreated());

        // Validate the Studio in the database
        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeCreate + 1);
        Studio testStudio = studioList.get(studioList.size() - 1);
        assertThat(testStudio.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testStudio.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testStudio.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testStudio.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testStudio.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStudio.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createStudioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studioRepository.findAll().size();

        // Create the Studio with an existing ID
        studio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudioMockMvc.perform(post("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studio)))
            .andExpect(status().isBadRequest());

        // Validate the Studio in the database
        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAboutIsRequired() throws Exception {
        int databaseSizeBeforeTest = studioRepository.findAll().size();
        // set the field null
        studio.setAbout(null);

        // Create the Studio, which fails.

        restStudioMockMvc.perform(post("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studio)))
            .andExpect(status().isBadRequest());

        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = studioRepository.findAll().size();
        // set the field null
        studio.setStreet(null);

        // Create the Studio, which fails.

        restStudioMockMvc.perform(post("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studio)))
            .andExpect(status().isBadRequest());

        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = studioRepository.findAll().size();
        // set the field null
        studio.setPostCode(null);

        // Create the Studio, which fails.

        restStudioMockMvc.perform(post("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studio)))
            .andExpect(status().isBadRequest());

        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = studioRepository.findAll().size();
        // set the field null
        studio.setCity(null);

        // Create the Studio, which fails.

        restStudioMockMvc.perform(post("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studio)))
            .andExpect(status().isBadRequest());

        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = studioRepository.findAll().size();
        // set the field null
        studio.setEmail(null);

        // Create the Studio, which fails.

        restStudioMockMvc.perform(post("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studio)))
            .andExpect(status().isBadRequest());

        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = studioRepository.findAll().size();
        // set the field null
        studio.setPhone(null);

        // Create the Studio, which fails.

        restStudioMockMvc.perform(post("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studio)))
            .andExpect(status().isBadRequest());

        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudios() throws Exception {
        // Initialize the database
        studioRepository.saveAndFlush(studio);

        // Get all the studioList
        restStudioMockMvc.perform(get("/api/studios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studio.getId().intValue())))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }
    
    @Test
    @Transactional
    public void getStudio() throws Exception {
        // Initialize the database
        studioRepository.saveAndFlush(studio);

        // Get the studio
        restStudioMockMvc.perform(get("/api/studios/{id}", studio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studio.getId().intValue()))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudio() throws Exception {
        // Get the studio
        restStudioMockMvc.perform(get("/api/studios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudio() throws Exception {
        // Initialize the database
        studioRepository.saveAndFlush(studio);

        int databaseSizeBeforeUpdate = studioRepository.findAll().size();

        // Update the studio
        Studio updatedStudio = studioRepository.findById(studio.getId()).get();
        // Disconnect from session so that the updates on updatedStudio are not directly saved in db
        em.detach(updatedStudio);
        updatedStudio
            .about(UPDATED_ABOUT)
            .street(UPDATED_STREET)
            .postCode(UPDATED_POST_CODE)
            .city(UPDATED_CITY)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);

        restStudioMockMvc.perform(put("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudio)))
            .andExpect(status().isOk());

        // Validate the Studio in the database
        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeUpdate);
        Studio testStudio = studioList.get(studioList.size() - 1);
        assertThat(testStudio.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testStudio.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testStudio.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testStudio.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testStudio.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStudio.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudio() throws Exception {
        int databaseSizeBeforeUpdate = studioRepository.findAll().size();

        // Create the Studio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudioMockMvc.perform(put("/api/studios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studio)))
            .andExpect(status().isBadRequest());

        // Validate the Studio in the database
        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudio() throws Exception {
        // Initialize the database
        studioRepository.saveAndFlush(studio);

        int databaseSizeBeforeDelete = studioRepository.findAll().size();

        // Delete the studio
        restStudioMockMvc.perform(delete("/api/studios/{id}", studio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Studio> studioList = studioRepository.findAll();
        assertThat(studioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Studio.class);
        Studio studio1 = new Studio();
        studio1.setId(1L);
        Studio studio2 = new Studio();
        studio2.setId(studio1.getId());
        assertThat(studio1).isEqualTo(studio2);
        studio2.setId(2L);
        assertThat(studio1).isNotEqualTo(studio2);
        studio1.setId(null);
        assertThat(studio1).isNotEqualTo(studio2);
    }
}
