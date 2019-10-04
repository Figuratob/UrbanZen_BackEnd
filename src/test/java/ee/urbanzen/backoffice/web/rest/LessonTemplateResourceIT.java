package ee.urbanzen.backoffice.web.rest;

import ee.urbanzen.backoffice.UrbanZenApp;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.domain.Teacher;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static ee.urbanzen.backoffice.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ee.urbanzen.backoffice.domain.enumeration.DayOfWeek;
/**
 * Integration tests for the {@link LessonTemplateResource} REST controller.
 */
@SpringBootTest(classes = UrbanZenApp.class)
public class LessonTemplateResourceIT {

    private static final DayOfWeek DEFAULT_DAY_OF_WEEK = DayOfWeek.Monday;
    private static final DayOfWeek UPDATED_DAY_OF_WEEK = DayOfWeek.Tuesday;

    private static final Integer DEFAULT_START_HOUR = 0;
    private static final Integer UPDATED_START_HOUR = 1;
    private static final Integer SMALLER_START_HOUR = 0 - 1;

    private static final Integer DEFAULT_START_MINUTE = 0;
    private static final Integer UPDATED_START_MINUTE = 1;
    private static final Integer SMALLER_START_MINUTE = 0 - 1;

    private static final Integer DEFAULT_END_HOUR = 0;
    private static final Integer UPDATED_END_HOUR = 1;
    private static final Integer SMALLER_END_HOUR = 0 - 1;

    private static final Integer DEFAULT_END_MINUTE = 0;
    private static final Integer UPDATED_END_MINUTE = 1;
    private static final Integer SMALLER_END_MINUTE = 0 - 1;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_AVAILABLE_SPACES = 0;
    private static final Integer UPDATED_AVAILABLE_SPACES = 1;
    private static final Integer SMALLER_AVAILABLE_SPACES = 0 - 1;

    private static final LocalDate DEFAULT_REPEAT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPEAT_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REPEAT_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_REPEAT_UNTIL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPEAT_UNTIL_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REPEAT_UNTIL_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private LessonTemplateRepository lessonTemplateRepository;

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

    private MockMvc restLessonTemplateMockMvc;

    private LessonTemplate lessonTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LessonTemplateResource lessonTemplateResource = new LessonTemplateResource(lessonTemplateRepository);
        this.restLessonTemplateMockMvc = MockMvcBuilders.standaloneSetup(lessonTemplateResource)
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
    public static LessonTemplate createEntity(EntityManager em) {
        LessonTemplate lessonTemplate = new LessonTemplate()
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .startHour(DEFAULT_START_HOUR)
            .startMinute(DEFAULT_START_MINUTE)
            .endHour(DEFAULT_END_HOUR)
            .endMinute(DEFAULT_END_MINUTE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .street(DEFAULT_STREET)
            .city(DEFAULT_CITY)
            .availableSpaces(DEFAULT_AVAILABLE_SPACES)
            .repeatStartDate(DEFAULT_REPEAT_START_DATE)
            .repeatUntilDate(DEFAULT_REPEAT_UNTIL_DATE);
        // Add required entity
        Teacher teacher;
        if (TestUtil.findAll(em, Teacher.class).isEmpty()) {
            teacher = TeacherResourceIT.createEntity(em);
            em.persist(teacher);
            em.flush();
        } else {
            teacher = TestUtil.findAll(em, Teacher.class).get(0);
        }
        lessonTemplate.setTeacher(teacher);
        return lessonTemplate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LessonTemplate createUpdatedEntity(EntityManager em) {
        LessonTemplate lessonTemplate = new LessonTemplate()
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .startHour(UPDATED_START_HOUR)
            .startMinute(UPDATED_START_MINUTE)
            .endHour(UPDATED_END_HOUR)
            .endMinute(UPDATED_END_MINUTE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .street(UPDATED_STREET)
            .city(UPDATED_CITY)
            .availableSpaces(UPDATED_AVAILABLE_SPACES)
            .repeatStartDate(UPDATED_REPEAT_START_DATE)
            .repeatUntilDate(UPDATED_REPEAT_UNTIL_DATE);
        // Add required entity
        Teacher teacher;
        if (TestUtil.findAll(em, Teacher.class).isEmpty()) {
            teacher = TeacherResourceIT.createUpdatedEntity(em);
            em.persist(teacher);
            em.flush();
        } else {
            teacher = TestUtil.findAll(em, Teacher.class).get(0);
        }
        lessonTemplate.setTeacher(teacher);
        return lessonTemplate;
    }

    @BeforeEach
    public void initTest() {
        lessonTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createLessonTemplate() throws Exception {
        int databaseSizeBeforeCreate = lessonTemplateRepository.findAll().size();

        // Create the LessonTemplate
        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isCreated());

        // Validate the LessonTemplate in the database
        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        LessonTemplate testLessonTemplate = lessonTemplateList.get(lessonTemplateList.size() - 1);
        assertThat(testLessonTemplate.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testLessonTemplate.getStartHour()).isEqualTo(DEFAULT_START_HOUR);
        assertThat(testLessonTemplate.getStartMinute()).isEqualTo(DEFAULT_START_MINUTE);
        assertThat(testLessonTemplate.getEndHour()).isEqualTo(DEFAULT_END_HOUR);
        assertThat(testLessonTemplate.getEndMinute()).isEqualTo(DEFAULT_END_MINUTE);
        assertThat(testLessonTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLessonTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLessonTemplate.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testLessonTemplate.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testLessonTemplate.getAvailableSpaces()).isEqualTo(DEFAULT_AVAILABLE_SPACES);
        assertThat(testLessonTemplate.getRepeatStartDate()).isEqualTo(DEFAULT_REPEAT_START_DATE);
        assertThat(testLessonTemplate.getRepeatUntilDate()).isEqualTo(DEFAULT_REPEAT_UNTIL_DATE);
    }

    @Test
    @Transactional
    public void createLessonTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lessonTemplateRepository.findAll().size();

        // Create the LessonTemplate with an existing ID
        lessonTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the LessonTemplate in the database
        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDayOfWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTemplateRepository.findAll().size();
        // set the field null
        lessonTemplate.setDayOfWeek(null);

        // Create the LessonTemplate, which fails.

        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTemplateRepository.findAll().size();
        // set the field null
        lessonTemplate.setStartHour(null);

        // Create the LessonTemplate, which fails.

        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartMinuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTemplateRepository.findAll().size();
        // set the field null
        lessonTemplate.setStartMinute(null);

        // Create the LessonTemplate, which fails.

        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTemplateRepository.findAll().size();
        // set the field null
        lessonTemplate.setEndHour(null);

        // Create the LessonTemplate, which fails.

        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndMinuteIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTemplateRepository.findAll().size();
        // set the field null
        lessonTemplate.setEndMinute(null);

        // Create the LessonTemplate, which fails.

        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTemplateRepository.findAll().size();
        // set the field null
        lessonTemplate.setName(null);

        // Create the LessonTemplate, which fails.

        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTemplateRepository.findAll().size();
        // set the field null
        lessonTemplate.setDescription(null);

        // Create the LessonTemplate, which fails.

        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableSpacesIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTemplateRepository.findAll().size();
        // set the field null
        lessonTemplate.setAvailableSpaces(null);

        // Create the LessonTemplate, which fails.

        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepeatStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonTemplateRepository.findAll().size();
        // set the field null
        lessonTemplate.setRepeatStartDate(null);

        // Create the LessonTemplate, which fails.

        restLessonTemplateMockMvc.perform(post("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLessonTemplates() throws Exception {
        // Initialize the database
        lessonTemplateRepository.saveAndFlush(lessonTemplate);

        // Get all the lessonTemplateList
        restLessonTemplateMockMvc.perform(get("/api/lesson-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lessonTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK.toString())))
            .andExpect(jsonPath("$.[*].startHour").value(hasItem(DEFAULT_START_HOUR)))
            .andExpect(jsonPath("$.[*].startMinute").value(hasItem(DEFAULT_START_MINUTE)))
            .andExpect(jsonPath("$.[*].endHour").value(hasItem(DEFAULT_END_HOUR)))
            .andExpect(jsonPath("$.[*].endMinute").value(hasItem(DEFAULT_END_MINUTE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].availableSpaces").value(hasItem(DEFAULT_AVAILABLE_SPACES)))
            .andExpect(jsonPath("$.[*].repeatStartDate").value(hasItem(DEFAULT_REPEAT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].repeatUntilDate").value(hasItem(DEFAULT_REPEAT_UNTIL_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getLessonTemplate() throws Exception {
        // Initialize the database
        lessonTemplateRepository.saveAndFlush(lessonTemplate);

        // Get the lessonTemplate
        restLessonTemplateMockMvc.perform(get("/api/lesson-templates/{id}", lessonTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lessonTemplate.getId().intValue()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK.toString()))
            .andExpect(jsonPath("$.startHour").value(DEFAULT_START_HOUR))
            .andExpect(jsonPath("$.startMinute").value(DEFAULT_START_MINUTE))
            .andExpect(jsonPath("$.endHour").value(DEFAULT_END_HOUR))
            .andExpect(jsonPath("$.endMinute").value(DEFAULT_END_MINUTE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.availableSpaces").value(DEFAULT_AVAILABLE_SPACES))
            .andExpect(jsonPath("$.repeatStartDate").value(DEFAULT_REPEAT_START_DATE.toString()))
            .andExpect(jsonPath("$.repeatUntilDate").value(DEFAULT_REPEAT_UNTIL_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLessonTemplate() throws Exception {
        // Get the lessonTemplate
        restLessonTemplateMockMvc.perform(get("/api/lesson-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLessonTemplate() throws Exception {
        // Initialize the database
        lessonTemplateRepository.saveAndFlush(lessonTemplate);

        int databaseSizeBeforeUpdate = lessonTemplateRepository.findAll().size();

        // Update the lessonTemplate
        LessonTemplate updatedLessonTemplate = lessonTemplateRepository.findById(lessonTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedLessonTemplate are not directly saved in db
        em.detach(updatedLessonTemplate);
        updatedLessonTemplate
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .startHour(UPDATED_START_HOUR)
            .startMinute(UPDATED_START_MINUTE)
            .endHour(UPDATED_END_HOUR)
            .endMinute(UPDATED_END_MINUTE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .street(UPDATED_STREET)
            .city(UPDATED_CITY)
            .availableSpaces(UPDATED_AVAILABLE_SPACES)
            .repeatStartDate(UPDATED_REPEAT_START_DATE)
            .repeatUntilDate(UPDATED_REPEAT_UNTIL_DATE);

        restLessonTemplateMockMvc.perform(put("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLessonTemplate)))
            .andExpect(status().isOk());

        // Validate the LessonTemplate in the database
        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeUpdate);
        LessonTemplate testLessonTemplate = lessonTemplateList.get(lessonTemplateList.size() - 1);
        assertThat(testLessonTemplate.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testLessonTemplate.getStartHour()).isEqualTo(UPDATED_START_HOUR);
        assertThat(testLessonTemplate.getStartMinute()).isEqualTo(UPDATED_START_MINUTE);
        assertThat(testLessonTemplate.getEndHour()).isEqualTo(UPDATED_END_HOUR);
        assertThat(testLessonTemplate.getEndMinute()).isEqualTo(UPDATED_END_MINUTE);
        assertThat(testLessonTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLessonTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLessonTemplate.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testLessonTemplate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testLessonTemplate.getAvailableSpaces()).isEqualTo(UPDATED_AVAILABLE_SPACES);
        assertThat(testLessonTemplate.getRepeatStartDate()).isEqualTo(UPDATED_REPEAT_START_DATE);
        assertThat(testLessonTemplate.getRepeatUntilDate()).isEqualTo(UPDATED_REPEAT_UNTIL_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingLessonTemplate() throws Exception {
        int databaseSizeBeforeUpdate = lessonTemplateRepository.findAll().size();

        // Create the LessonTemplate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLessonTemplateMockMvc.perform(put("/api/lesson-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the LessonTemplate in the database
        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLessonTemplate() throws Exception {
        // Initialize the database
        lessonTemplateRepository.saveAndFlush(lessonTemplate);

        int databaseSizeBeforeDelete = lessonTemplateRepository.findAll().size();

        // Delete the lessonTemplate
        restLessonTemplateMockMvc.perform(delete("/api/lesson-templates/{id}", lessonTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LessonTemplate> lessonTemplateList = lessonTemplateRepository.findAll();
        assertThat(lessonTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LessonTemplate.class);
        LessonTemplate lessonTemplate1 = new LessonTemplate();
        lessonTemplate1.setId(1L);
        LessonTemplate lessonTemplate2 = new LessonTemplate();
        lessonTemplate2.setId(lessonTemplate1.getId());
        assertThat(lessonTemplate1).isEqualTo(lessonTemplate2);
        lessonTemplate2.setId(2L);
        assertThat(lessonTemplate1).isNotEqualTo(lessonTemplate2);
        lessonTemplate1.setId(null);
        assertThat(lessonTemplate1).isNotEqualTo(lessonTemplate2);
    }
}
