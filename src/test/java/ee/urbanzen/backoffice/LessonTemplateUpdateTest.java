package ee.urbanzen.backoffice;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.domain.Teacher;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
import ee.urbanzen.backoffice.repository.TeacherRepository;
import ee.urbanzen.backoffice.service.LessonService;
import ee.urbanzen.backoffice.service.LessonTemplateService;
import ee.urbanzen.backoffice.web.rest.LessonTemplateResource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = UrbanZenApp.class)
@Transactional
public class LessonTemplateUpdateTest {

    @Autowired
    private LessonTemplateRepository lessonTemplateRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    LessonTemplateResource lessonTemplateResource;
    @Autowired
        private LessonService lessonService;

    @Autowired
        private LessonTemplateService lessonTemplateService;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setupTest() {
//        saveOldLessonTemplate();
    }

    public LessonTemplate saveOldLessonTemplate() {

        byte[] photo1 = new byte[]{};
        Teacher teacher1 = new Teacher()
            .firstName("Anastassia")
            .firstNameEng("age")
            .firstNameRus("aeg")
            .lastName("Kundalini")
            .lastNameEng("age")
            .lastNameRus("afeh")
            .email("kundalini2019@gmail.com")
            .phone("+37253446498")
            .photo(photo1)
            .photoContentType("hfila")
            .about("hafhvg");

        byte[] photo2 = new byte[]{};
        Teacher teacher2 = new Teacher()
            .firstName("Yelena")
            .firstNameRus("agr")
            .firstNameEng("fehg")
            .lastName("Kundalini")
            .lastNameRus("aege")
            .lastNameEng("fwle")
            .email("kundalini2019@gmail.com")
            .phone("+37253446327")
            .photo(photo2)
            .photoContentType("hfila")
            .about("hafhvg");

        teacherRepository.saveAndFlush(teacher1);
        teacherRepository.saveAndFlush(teacher2);

        LessonTemplate lessonTemplate = new LessonTemplate()
            .dayOfWeek(DayOfWeek.MONDAY)
            .startHour(18)
            .startMinute(30)
            .endHour(20)
            .endMinute(0)
            .name("Kundalini Jooga")
            .nameRus("asd")
            .nameEng("asd")
            .description("ajvh")
            .descriptionRus("asd")
            .descriptionEng("asd")
            .street("asd")
            .streetRus("asd")
            .streetEng("asd")
            .city("asd")
            .cityRus("asd")
            .cityEng("asd")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019, 10, 3))
            .repeatUntilDate(LocalDate.of(2019, 12, 31))
            .teacher(teacher1);

        return lessonTemplateService.save(lessonTemplate);
//        return returnlessonTemplateRepository.saveAndFlush(lessonTemplate);

    }
    /** LessonTemplate Repeat Start Date and Repeat Until Date are on Lesson Start Date */
    public LessonTemplate saveOldLessonTemplate2() {

        byte[] photo1 = new byte[]{};
        Teacher teacher1 = new Teacher()
            .firstName("Anastassia")
            .firstNameEng("age")
            .firstNameRus("aeg")
            .lastName("Kundalini")
            .lastNameEng("age")
            .lastNameRus("afeh")
            .email("kundalini2019@gmail.com")
            .phone("+37253446498")
            .photo(photo1)
            .photoContentType("hfila")
            .about("hafhvg");

        byte[] photo2 = new byte[]{};
        Teacher teacher2 = new Teacher()
            .firstName("Yelena")
            .firstNameRus("agr")
            .firstNameEng("fehg")
            .lastName("Kundalini")
            .lastNameRus("aege")
            .lastNameEng("fwle")
            .email("kundalini2019@gmail.com")
            .phone("+37253446327")
            .photo(photo2)
            .photoContentType("hfila")
            .about("hafhvg");

        teacherRepository.saveAndFlush(teacher1);
        teacherRepository.saveAndFlush(teacher2);

        LessonTemplate lessonTemplate = new LessonTemplate()
            .dayOfWeek(DayOfWeek.MONDAY)
            .startHour(18)
            .startMinute(30)
            .endHour(20)
            .endMinute(0)
            .name("Kundalini Jooga")
            .nameRus("asd")
            .nameEng("asd")
            .description("ajvh")
            .descriptionRus("asd")
            .descriptionEng("asd")
            .street("asd")
            .streetRus("asd")
            .streetEng("asd")
            .city("asd")
            .cityRus("asd")
            .cityEng("asd")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019, 10, 7))
            .repeatUntilDate(LocalDate.of(2019, 10, 14))
            .teacher(teacher1);

        return lessonTemplateRepository.saveAndFlush(lessonTemplate);
    }
    public LessonTemplate saveOldLessonTemplate3() {

        byte[] photo1 = new byte[]{};
        Teacher teacher1 = new Teacher()
            .firstName("Anastassia")
            .firstNameEng("age")
            .firstNameRus("aeg")
            .lastName("Kundalini")
            .lastNameEng("age")
            .lastNameRus("afeh")
            .email("kundalini2019@gmail.com")
            .phone("+37253446498")
            .photo(photo1)
            .photoContentType("hfila")
            .about("hafhvg");

        byte[] photo2 = new byte[]{};
        Teacher teacher2 = new Teacher()
            .firstName("Yelena")
            .firstNameRus("agr")
            .firstNameEng("fehg")
            .lastName("Kundalini")
            .lastNameRus("aege")
            .lastNameEng("fwle")
            .email("kundalini2019@gmail.com")
            .phone("+37253446327")
            .photo(photo2)
            .photoContentType("hfila")
            .about("hafhvg");

        teacherRepository.saveAndFlush(teacher1);
        teacherRepository.saveAndFlush(teacher2);

        LessonTemplate lessonTemplate = new LessonTemplate()
            .dayOfWeek(DayOfWeek.MONDAY)
            .startHour(18)
            .startMinute(30)
            .endHour(20)
            .endMinute(0)
            .name("Kundalini Jooga")
            .nameRus("asd")
            .nameEng("asd")
            .description("ajvh")
            .descriptionRus("asd")
            .descriptionEng("asd")
            .street("asd")
            .streetRus("asd")
            .streetEng("asd")
            .city("asd")
            .cityRus("asd")
            .cityEng("asd")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019, 10, 7))
            .repeatUntilDate(LocalDate.of(2019, 10, 21))
            .teacher(teacher1);

        return lessonTemplateRepository.saveAndFlush(lessonTemplate);
    }
    @Test
    void testUpdateLessonTemplate1 () throws IOException{

        LessonTemplate oldLessonTemplate = saveOldLessonTemplate();
        List<LessonTemplate> oldLessonTemplates = new ArrayList<>();
        oldLessonTemplates.add(oldLessonTemplate);
        lessonService.generateLessonsFromTemplatesByDates(oldLessonTemplate.getRepeatStartDate(),
            oldLessonTemplate.getRepeatUntilDate(), oldLessonTemplates);

        String src = objectMapper.writer().writeValueAsString(oldLessonTemplate);
        LessonTemplate newLessonTemplate = objectMapper.readValue(src, LessonTemplate.class);

        newLessonTemplate.setStartHour(15);
        newLessonTemplate.setAvailableSpaces(12);
        lessonTemplateService.updateLessonTemplate(newLessonTemplate);

        List<Lesson> updatedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 10, 3),
            LocalDate.of(2019, 12, 31));

        assertNotNull(updatedLessons);
        assertEquals(13, updatedLessons.size());

        Lesson updatedLesson1 = updatedLessons.get(0);

        assertThat(updatedLesson1.getStartDate()).isEqualTo(Instant.parse("2019-10-07T15:30:00.00Z"));
        assertThat(updatedLesson1.getEndDate()).isEqualTo(Instant.parse("2019-10-07T20:00:00.00Z"));
        assertThat(updatedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(updatedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(updatedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(updatedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(updatedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(updatedLesson1.getAvailableSpaces()).isEqualTo(12);
    }
    @Test
    void testUpdateLessonTemplate1_2 () throws IOException {

        LessonTemplate oldLessonTemplate = saveOldLessonTemplate2();
        List<LessonTemplate> oldLessonTemplates = new ArrayList<>();
        oldLessonTemplates.add(oldLessonTemplate);

        // !!! generateLessonsFromTemplatesByDates doesn't include last date
        // generateLessonsFromTemplatesByDatesIncludingEndDate does

//        lessonService.generateLessonsFromTemplatesByDates(oldLessonTemplate.getRepeatStartDate(),
//            oldLessonTemplate.getRepeatUntilDate(), oldLessonTemplates);
        /// !!!
        lessonService.generateLessonsFromTemplatesByDatesIncludingEndDate(oldLessonTemplate.getRepeatStartDate(),
            oldLessonTemplate.getRepeatUntilDate(), oldLessonTemplates);

        String src = objectMapper.writer().writeValueAsString(oldLessonTemplate);
        LessonTemplate newLessonTemplate = objectMapper.readValue(src, LessonTemplate.class);

        newLessonTemplate.setStartHour(15);
        newLessonTemplate.setAvailableSpaces(12);

        lessonTemplateService.updateLessonTemplate(newLessonTemplate);

        // !!! lessonService.findLessonsByDates doesn't include last date!
        List<Lesson> updatedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 10, 7),
            LocalDate.of(2019, 10, 14));
        List<Lesson> updatedLessons1 = lessonService.findLessonsByDates(LocalDate.of(2019, 10, 7),
            LocalDate.of(2019, 10, 15));
        List<Lesson> updatedLessonsIncludingLast = lessonService.findLessonsByDatesIncludingLast(LocalDate.of(2019, 10, 7),
            LocalDate.of(2019, 10, 14));

        assertNotNull(updatedLessons);
        assertEquals(1, updatedLessons.size());
        assertEquals(2, updatedLessons1.size());
        assertEquals(2, updatedLessonsIncludingLast.size());

        Lesson updatedLesson1 = updatedLessons.get(0);

        assertThat(updatedLesson1.getStartDate()).isEqualTo(Instant.parse("2019-10-07T15:30:00.00Z"));
        assertThat(updatedLesson1.getEndDate()).isEqualTo(Instant.parse("2019-10-07T20:00:00.00Z"));
        assertThat(updatedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(updatedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(updatedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(updatedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(updatedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(updatedLesson1.getAvailableSpaces()).isEqualTo(12);

        Lesson updatedLesson2 = updatedLessonsIncludingLast.get(1);

        assertThat(updatedLesson2.getStartDate()).isEqualTo(Instant.parse("2019-10-14T15:30:00.00Z"));
        assertThat(updatedLesson2.getEndDate()).isEqualTo(Instant.parse("2019-10-14T20:00:00.00Z"));
        assertThat(updatedLesson2.getName()).isEqualTo("Kundalini Jooga");
        assertThat(updatedLesson2.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(updatedLesson2.getDescription()).isEqualTo("ajvh");
        assertThat(updatedLesson2.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(updatedLesson2.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(updatedLesson2.getAvailableSpaces()).isEqualTo(12);
    }
    @Test
    void testUpdateLessonTemplate2a () throws IOException {

        LessonTemplate oldLessonTemplate = saveOldLessonTemplate();
        List<LessonTemplate> oldLessonTemplates = new ArrayList<>();
        oldLessonTemplates.add(oldLessonTemplate);
        lessonService.generateLessonsFromTemplatesByDates(oldLessonTemplate.getRepeatStartDate(),
            oldLessonTemplate.getRepeatUntilDate(), oldLessonTemplates);
        entityManager.detach(oldLessonTemplate);
        entityManager.clear();

        String src = objectMapper.writer().writeValueAsString(oldLessonTemplate);
//        LessonTemplate newLessonTemplate = objectMapper.reader().<LessonTemplate>readValue(src);
        LessonTemplate newLessonTemplate = objectMapper.readValue(src, LessonTemplate.class);

//        LessonTemplate newLessonTemplate = lessonTemplateRepository.getOne(oldLessonTemplate.getId());

        newLessonTemplate.setRepeatStartDate(LocalDate.of(2019, 9, 23));
        newLessonTemplate.setRepeatUntilDate(LocalDate.of(2019, 10, 10));
        newLessonTemplate.setStartHour(15);
        newLessonTemplate.setAvailableSpaces(12);
//        entityManager.detach(newLessonTemplate);

        lessonTemplateService.updateLessonTemplate(newLessonTemplate);

        entityManager.clear();

        List<Lesson> addedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 9,23),
            LocalDate.of(2019, 10, 3));
        List<Lesson> updatedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 10, 3),
            LocalDate.of(2019, 10, 10));
        List<Lesson> deletedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 10,10),
            LocalDate.of(2019,12,31));

        assertNotNull(updatedLessons);
        assertNotNull(addedLessons);
        assertEquals(2, addedLessons.size());
        assertEquals(1, updatedLessons.size());
        assertEquals(0, deletedLessons.size());

        Lesson updatedLesson1 = updatedLessons.get(0);

        assertThat(updatedLesson1.getStartDate()).isEqualTo(Instant.parse("2019-10-07T15:30:00.00Z"));
        assertThat(updatedLesson1.getEndDate()).isEqualTo(Instant.parse("2019-10-07T20:00:00.00Z"));
        assertThat(updatedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(updatedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(updatedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(updatedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(updatedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(updatedLesson1.getAvailableSpaces()).isEqualTo(12);

        Lesson addedLesson1 = addedLessons.get(0);

        assertThat(addedLesson1.getStartDate()).isEqualTo(Instant.parse("2019-09-23T15:30:00.00Z"));
        assertThat(addedLesson1.getEndDate()).isEqualTo(Instant.parse("2019-09-23T20:00:00.00Z"));
        assertThat(addedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(addedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(addedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(addedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(addedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(addedLesson1.getAvailableSpaces()).isEqualTo(12);
    }
    @Test
    void testUpdateLessonTemplate2a_3 () throws IOException {

        LessonTemplate oldLessonTemplate = saveOldLessonTemplate3();
        List<LessonTemplate> oldLessonTemplates = new ArrayList<>();
        oldLessonTemplates.add(oldLessonTemplate);
        lessonService.generateLessonsFromTemplatesByDates(oldLessonTemplate.getRepeatStartDate(),
            oldLessonTemplate.getRepeatUntilDate(), oldLessonTemplates);

        String src = objectMapper.writer().writeValueAsString(oldLessonTemplate);
        LessonTemplate newLessonTemplate = objectMapper.readValue(src, LessonTemplate.class);

        newLessonTemplate.setRepeatStartDate(LocalDate.of(2019, 9, 30));
        newLessonTemplate.setRepeatUntilDate(LocalDate.of(2019, 10, 14));
        newLessonTemplate.setStartHour(15);
        newLessonTemplate.setAvailableSpaces(12);

        lessonTemplateService.updateLessonTemplate(newLessonTemplate);

        List<Lesson> addedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 9,30),
            LocalDate.of(2019, 10, 7));
        List<Lesson> updatedLessons = lessonService.findLessonsByDatesIncludingLast(LocalDate.of(2019, 10, 7),
            LocalDate.of(2019, 10, 14));
        //  findLessonsByDatesIncludingLast includes firstDate!
        List<Lesson> deletedLessons = lessonService.findLessonsByDatesIncludingLast(LocalDate.of(2019, 10,15),
            LocalDate.of(2019,10,21));

        assertNotNull(updatedLessons);
        assertNotNull(addedLessons);

        System.out.println("addedLessons.size: " + addedLessons.size());
        System.out.println("addedLesson1: " + addedLessons.get(0).toString());
//        System.out.println("addedLesson2: " + addedLessons.get(1).toString());
//        System.out.println("addedLesson3: " + addedLessons.get(2).toString());

        System.out.println("updateLessons.size: " + updatedLessons.size());
        System.out.println("updatedLesson1: " + updatedLessons.get(0).toString());
        System.out.println("updatedLesson2: " + updatedLessons.get(1).toString());
//        System.out.println("updatedLesson3: " + updatedLessons.get(2).toString());

        assertEquals(1, addedLessons.size());
        assertEquals(2, updatedLessons.size());
        assertEquals(0, deletedLessons.size());

        Lesson addedLesson1 = addedLessons.get(0);

        assertThat(addedLesson1.getStartDate()).isEqualTo(Instant.parse("2019-09-30T15:30:00.00Z"));
        assertThat(addedLesson1.getEndDate()).isEqualTo(Instant.parse("2019-09-30T20:00:00.00Z"));
        assertThat(addedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(addedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(addedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(addedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(addedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(addedLesson1.getAvailableSpaces()).isEqualTo(12);

        Lesson updatedLesson1 = updatedLessons.get(0);

        assertThat(updatedLesson1.getStartDate()).isEqualTo(Instant.parse("2019-10-07T15:30:00.00Z"));
        assertThat(updatedLesson1.getEndDate()).isEqualTo(Instant.parse("2019-10-07T20:00:00.00Z"));
        assertThat(updatedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(updatedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(updatedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(updatedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(updatedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(updatedLesson1.getAvailableSpaces()).isEqualTo(12);

        Lesson updatedLesson2 = updatedLessons.get(1);
        System.out.println("updatedLesson2: " + updatedLesson2.toString());

        assertThat(updatedLesson2.getStartDate()).isEqualTo(Instant.parse("2019-10-14T15:30:00.00Z"));
        assertThat(updatedLesson2.getEndDate()).isEqualTo(Instant.parse("2019-10-14T20:00:00.00Z"));
        assertThat(updatedLesson2.getName()).isEqualTo("Kundalini Jooga");
        assertThat(updatedLesson2.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(updatedLesson2.getDescription()).isEqualTo("ajvh");
        assertThat(updatedLesson2.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(updatedLesson2.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(updatedLesson2.getAvailableSpaces()).isEqualTo(12);
    }
    @Test
    void testUpdateLessonTemplate2b () throws IOException {

        LessonTemplate oldLessonTemplate = saveOldLessonTemplate();
        List<LessonTemplate> oldLessonTemplates = new ArrayList<>();
        oldLessonTemplates.add(oldLessonTemplate);
        lessonService.generateLessonsFromTemplatesByDates(oldLessonTemplate.getRepeatStartDate(),
            oldLessonTemplate.getRepeatUntilDate(), oldLessonTemplates);

        String src = objectMapper.writer().writeValueAsString(oldLessonTemplate);
        LessonTemplate newLessonTemplate = objectMapper.readValue(src, LessonTemplate.class);

        newLessonTemplate.setRepeatStartDate(LocalDate.of(2019, 12, 30));
        newLessonTemplate.setRepeatUntilDate(LocalDate.of(2020, 1, 7));
        newLessonTemplate.setStartHour(15);
        newLessonTemplate.setAvailableSpaces(12);

        lessonTemplateService.updateLessonTemplate(newLessonTemplate);

        // --- lessonService.findLessonsByDates doesn't include second date!
        List<Lesson> deletedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 10,3),
            LocalDate.of(2019,12,30));
        List<Lesson> updatedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 12, 30),
            LocalDate.of(2019, 12, 31));
        List<Lesson> addedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 12,31),
            LocalDate.of(2020, 1, 7));

        assertNotNull(updatedLessons);
        assertNotNull(addedLessons);

        assertEquals(0, deletedLessons.size());
        assertEquals(1, updatedLessons.size());
        assertEquals(1, addedLessons.size());

        Lesson updatedLesson1 = updatedLessons.get(0);

        assertThat(updatedLesson1.getStartDate()).isEqualTo(Instant.parse("2019-12-30T15:30:00.00Z"));
        assertThat(updatedLesson1.getEndDate()).isEqualTo(Instant.parse("2019-12-30T20:00:00.00Z"));
        assertThat(updatedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(updatedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(updatedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(updatedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(updatedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(updatedLesson1.getAvailableSpaces()).isEqualTo(12);

        Lesson addedLesson1 = addedLessons.get(0);

        assertThat(addedLesson1.getStartDate()).isEqualTo(Instant.parse("2020-01-06T15:30:00.00Z"));
        assertThat(addedLesson1.getEndDate()).isEqualTo(Instant.parse("2020-01-06T20:00:00.00Z"));
        assertThat(addedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(addedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(addedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(addedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(addedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(addedLesson1.getAvailableSpaces()).isEqualTo(12);

    }
    @Test
    void testUpdateLessonTemplate3a () throws IOException {

        LessonTemplate oldLessonTemplate = saveOldLessonTemplate();
        List<LessonTemplate> oldLessonTemplates = new ArrayList<>();
        oldLessonTemplates.add(oldLessonTemplate);
        lessonService.generateLessonsFromTemplatesByDates(oldLessonTemplate.getRepeatStartDate(),
            oldLessonTemplate.getRepeatUntilDate(), oldLessonTemplates);

        String src = objectMapper.writer().writeValueAsString(oldLessonTemplate);
        LessonTemplate newLessonTemplate = objectMapper.readValue(src, LessonTemplate.class);

        newLessonTemplate.setRepeatStartDate(LocalDate.of(2019, 9, 30));
        newLessonTemplate.setRepeatUntilDate(LocalDate.of(2019, 10, 2));
        newLessonTemplate.setStartHour(15);
        newLessonTemplate.setAvailableSpaces(12);

        lessonTemplateService.updateLessonTemplate(newLessonTemplate);

        List<Lesson> addedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 9,30),
            LocalDate.of(2019, 10, 2));
        List<Lesson> deletedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 10,3),
            LocalDate.of(2019,12,31));

        assertNotNull(addedLessons);
        assertEquals(0, deletedLessons.size());
        assertEquals(1, addedLessons.size());

        Lesson addedLesson1 = addedLessons.get(0);

        assertThat(addedLesson1.getStartDate()).isEqualTo(Instant.parse("2019-09-30T15:30:00.00Z"));
        assertThat(addedLesson1.getEndDate()).isEqualTo(Instant.parse("2019-09-30T20:00:00.00Z"));
        assertThat(addedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(addedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(addedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(addedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(addedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(addedLesson1.getAvailableSpaces()).isEqualTo(12);
    }
    @Test
    void testUpdateLessonTemplate3b() throws IOException {

        LessonTemplate oldLessonTemplate = saveOldLessonTemplate();
        List<LessonTemplate> oldLessonTemplates = new ArrayList<>();
        oldLessonTemplates.add(oldLessonTemplate);
        lessonService.generateLessonsFromTemplatesByDates(oldLessonTemplate.getRepeatStartDate(),
            oldLessonTemplate.getRepeatUntilDate(), oldLessonTemplates);

        String src = objectMapper.writer().writeValueAsString(oldLessonTemplate);
        LessonTemplate newLessonTemplate = objectMapper.readValue(src, LessonTemplate.class);

        newLessonTemplate.setRepeatStartDate(LocalDate.of(2020, 1, 1));
        newLessonTemplate.setRepeatUntilDate(LocalDate.of(2020, 1, 7));
        newLessonTemplate.setStartHour(15);
        newLessonTemplate.setAvailableSpaces(12);

        lessonTemplateService.updateLessonTemplate(newLessonTemplate);

        List<Lesson> addedLessons = lessonService.findLessonsByDates(LocalDate.of(2020, 1,1),
            LocalDate.of(2020, 1, 7));
        List<Lesson> deletedLessons = lessonService.findLessonsByDates(LocalDate.of(2019, 10,3),
            LocalDate.of(2019,12,31));

        assertNotNull(addedLessons);
        assertEquals(0, deletedLessons.size());
        assertEquals(1, addedLessons.size());

        Lesson addedLesson1 = addedLessons.get(0);

        assertThat(addedLesson1.getStartDate()).isEqualTo(Instant.parse("2020-01-06T15:30:00.00Z"));
        assertThat(addedLesson1.getEndDate()).isEqualTo(Instant.parse("2020-01-06T20:00:00.00Z"));
        assertThat(addedLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(addedLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(addedLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(addedLesson1.getStreet()).isEqualTo(oldLessonTemplate.getStreet());
        assertThat(addedLesson1.getCity()).isEqualTo(oldLessonTemplate.getCity());
        assertThat(addedLesson1.getAvailableSpaces()).isEqualTo(12);
    }

}
