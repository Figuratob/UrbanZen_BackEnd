package ee.urbanzen.backoffice;

import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.domain.Teacher;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
import ee.urbanzen.backoffice.repository.TeacherRepository;
import ee.urbanzen.backoffice.service.LessonService;
import ee.urbanzen.backoffice.web.rest.LessonTemplateResource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = UrbanZenApp.class)
@Transactional
public class LessonTemplateTest {

    @Autowired
    private LessonTemplateRepository lessonTemplateRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    LessonTemplateResource lessonTemplateResource;

    @Autowired
    private LessonService lessonService;


    LocalDate timetableStartDate = LocalDate.of(2019,10,07);
    LocalDate timetableEndDate = LocalDate.of(2019,10,13);
    LocalDate timetableEndDateTimetableForTwoWeeks=LocalDate.of(2019,10,20);


    LocalDate timetableStartDateIsOnDateOfLesson = LocalDate.of(2019,10,07);
    LocalDate timetableStartDateIsBeforeDateOfLesson = LocalDate.of(2019,10,06);
    LocalDate timetableStartDateIsAfterDateOfLesson = LocalDate.of(2019,10,8);

    @Before
    public void setupTest() {
        getTestData();
    }

    public List<LessonTemplate> getTestData() {

        byte[] photo1 = new byte[]{};
        Teacher teacher1 = new Teacher()
            .firstName("Anastassia")
            .firstNameEng("")
            .firstNameRus("")
            .lastName("Kundalini")
            .lastNameEng("")
            .lastNameRus("")
            .email("kundalini2019@gmail.com")
            .phone("+37253446498")
            .photo(photo1)
            .photoContentType("hfila")
            .about("hafhvg");

        byte[] photo2 = new byte[]{};
        Teacher teacher2 = new Teacher()
            .firstName("Yelena")
            .firstNameRus("")
            .firstNameEng("")
            .lastName("Kundalini")
            .lastNameRus("")
            .lastNameEng("")
            .email("kundalini2019@gmail.com")
            .phone("+37253446327")
            .photo(photo2)
            .photoContentType("hfila")
            .about("hafhvg");

        teacherRepository.saveAndFlush(teacher1);
        teacherRepository.saveAndFlush(teacher2);

        Lesson lesson1 = new Lesson()
            .startDate((LocalDateTime.of(LocalDate.of(2019, 10, 7), LocalTime.of(18,30))).toInstant(ZoneOffset.UTC))
            .endDate((LocalDateTime.of(LocalDate.of(2019, 10, 7), LocalTime.of(20,00))).toInstant(ZoneOffset.UTC))
            .name("Kundalini Jooga")
            .nameEng("asg")
            .nameRus("asg")
            .description("ajvh")
            .descriptionEng("asg")
            .descriptionRus("age")
            .street("")
            .streetEng("aswe")
            .streetRus("aef")
            .city("")
            .cityEng("as")
            .cityRus("ag")
            .availableSpaces(10)
            .teacher(teacher1);

        LessonTemplate lessonTemplate1 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.MONDAY)
            .startHour(18)
            .startMinute(30)
            .endHour(20)
            .endMinute(0)
            .name("Kundalini Jooga")
            .nameEng("")
            .nameRus("")
            .description("ajvh")
            .descriptionEng("")
            .descriptionRus("")
            .street("")
            .streetEng("")
            .streetRus("")
            .city("")
            .cityEng("")
            .cityRus("")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019, 10, 3))
            .repeatUntilDate(LocalDate.of(2019, 12, 31))
            .teacher(teacher1)
            .addLesson(lesson1);

        Lesson lesson2 = new Lesson()
            .startDate((LocalDateTime.of(LocalDate.of(2019, 10, 8), LocalTime.of(9,30))).toInstant(ZoneOffset.UTC))
            .endDate((LocalDateTime.of(LocalDate.of(2019, 10, 8), LocalTime.of(12,00))).toInstant(ZoneOffset.UTC))
            .name("Kundalini Jooga")
            .nameEng("")
            .nameRus("")
            .description("ajvh")
            .descriptionEng("")
            .descriptionRus("")
            .street("")
            .streetEng("")
            .streetRus("")
            .city("")
            .cityEng("")
            .cityRus(" ")
            .availableSpaces(10)
            .teacher(teacher1);

        LessonTemplate lessonTemplate2 = new LessonTemplate()

            .dayOfWeek(DayOfWeek.TUESDAY)
            .startHour(9)
            .startMinute(30)
            .endHour(12)
            .endMinute(0)
            .name("Kundalini Jooga")
            .nameEng("")
            .nameRus("")
            .description("ajvh")
            .descriptionEng("Afe")
            .descriptionRus("fae")
            .street("")
            .streetEng("")
            .streetRus("")
            .city("")
            .cityEng("")
            .cityRus("")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019, 10, 3))
            .repeatUntilDate(LocalDate.of(2019, 12, 31))
            .teacher(teacher1)
            .addLesson(lesson2);

        LessonTemplate lessonTemplate3 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.TUESDAY)
            .startHour(18)
            .startMinute(00)
            .endHour(19)
            .endMinute(30)
            .name("Kundalini Jooga")
            .nameEng("sdfa")
            .nameRus("afe")
            .description("ajvh")
            .descriptionEng("ae")
            .descriptionRus("fe")
            .street("")
            .streetEng("fe")
            .streetRus("daf")
            .city("")
            .cityEng("")
            .cityRus("")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019, 10, 3))
            .repeatUntilDate(LocalDate.of(2019, 12, 31))
            .teacher(teacher2);

        LessonTemplate lessonTemplate4 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.WEDNESDAY)
            .startHour(18)
            .startMinute(30)
            .endHour(20)
            .endMinute(0)
            .name("Kundalini Jooga")
            .nameEng("fe")
            .nameRus("fea")
            .description("ajvh")
            .descriptionEng("ae")
            .descriptionRus("sfge")
            .street("")
            .streetEng("")
            .streetRus("gr")
            .city("")
            .cityEng("")
            .cityRus("rga")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019, 10, 3))
            .repeatUntilDate(null)
            .teacher(teacher1);

        LessonTemplate lessonTemplate5 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.THURSDAY)
            .startHour(9)
            .startMinute(00)
            .endHour(11)
            .endMinute(00)
            .name("SRE")
            .nameEng("sg")
            .nameRus("sager")
            .description("zzzzz")
            .descriptionEng("aeg")
            .descriptionRus("shge")
            .street("")
            .streetEng("sag")
            .streetRus("aeg")
            .city("")
            .cityEng("sag")
            .cityRus("ag")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019,10,3))
            .repeatUntilDate(LocalDate.of(2019,12,31))
            .teacher(teacher2);

        LessonTemplate lessonTemplate6 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.THURSDAY)
            .startHour(18)
            .startMinute(00)
            .endHour(19)
            .endMinute(30)
            .name("Kundalini Jooga")
            .nameEng("age")
            .nameRus("aegf")
            .description("zzzzz")
            .descriptionEng("aeg")
            .descriptionRus("sag")
            .street("")
            .streetEng("asef")
            .streetRus("age")
            .city("")
            .cityEng("ag")
            .cityRus("age")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019,10,3))
            .repeatUntilDate(LocalDate.of(2019,12,31))
            .teacher(teacher2);

        LessonTemplate lessonTemplate7 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.FRIDAY)
            .startHour(18)
            .startMinute(30)
            .endHour(20)
            .endMinute(00)
            .name("Kundalini Jooga")
            .nameEng("age")
            .nameRus("age")
            .description("zzzzz")
            .descriptionEng("age")
            .descriptionRus("age")
            .street("")
            .streetEng("aeg")
            .streetRus("age")
            .city("")
            .cityEng("aeg")
            .cityRus("agr")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019,10,3))
            .repeatUntilDate(LocalDate.of(2019,12,31))
            .teacher(teacher1);

        LessonTemplate lessonTemplate8 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.SATURDAY)
            .startHour(9)
            .startMinute(00)
            .endHour(10)
            .endMinute(30)
            .name("Zen Meditation")
            .nameEng("Age")
            .nameRus("age")
            .description("zzzzz")
            .descriptionEng("age")
            .descriptionRus("age")
            .street("")
            .streetEng("age")
            .streetRus("sg")
            .city("")
            .cityEng("")
            .cityRus("")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019,10,3))
            .repeatUntilDate(LocalDate.of(2019,12,31))
            .teacher(teacher2);

        lessonTemplateRepository.saveAndFlush(lessonTemplate1);
        lessonTemplateRepository.saveAndFlush(lessonTemplate2);
        lessonTemplateRepository.saveAndFlush(lessonTemplate3);
        lessonTemplateRepository.saveAndFlush(lessonTemplate4);
        lessonTemplateRepository.saveAndFlush(lessonTemplate5);
        lessonTemplateRepository.saveAndFlush(lessonTemplate6);
        lessonTemplateRepository.saveAndFlush(lessonTemplate7);
        lessonTemplateRepository.saveAndFlush(lessonTemplate8);

        List<LessonTemplate> lessonTemplateList = new ArrayList<>();
        lessonTemplateList.add(lessonTemplate1);
        lessonTemplateList.add(lessonTemplate2);
        lessonTemplateList.add(lessonTemplate3);
        lessonTemplateList.add(lessonTemplate4);
        lessonTemplateList.add(lessonTemplate5);
        lessonTemplateList.add(lessonTemplate6);
        lessonTemplateList.add(lessonTemplate7);
        lessonTemplateList.add(lessonTemplate8);

        return lessonTemplateList;
    }

    public List<LessonTemplate> getTestDataWithoutLessons() {
        List<LessonTemplate> testDataWithoutLessons = new ArrayList<>();
        List<LessonTemplate> allLessonsTemplates = getTestData();
        for (LessonTemplate template: allLessonsTemplates) {
            if (template.getLessons().isEmpty()) {
                testDataWithoutLessons.add(template);
            }
        }
        return testDataWithoutLessons;
    }

    @Test
    void testGenerateLessonsFromTemplates() {

        System.out.println(timetableStartDate);
        System.out.println(timetableStartDate.getDayOfWeek());

        List<Lesson> newLessons = lessonService.generateLessonsFromTemplates(timetableStartDate, timetableEndDate, getTestData());
        assertNotNull(newLessons);
        System.out.println(newLessons.size());

        LessonTemplate lessonTemplate1 = getTestData().get(0);
        Lesson newLesson1 = newLessons.get(0);

        assertThat(newLesson1.getStartDate()).isEqualTo(Instant.parse("2019-10-07T18:30:00.00Z"));
        assertThat(newLesson1.getEndDate()).isEqualTo(Instant.parse("2019-10-07T20:00:00.00Z"));
        assertThat(newLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson1.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(newLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(newLesson1.getStreet()).isEqualTo(lessonTemplate1.getStreet());
        assertThat(newLesson1.getCity()).isEqualTo(lessonTemplate1.getCity());
        assertThat(newLesson1.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson1.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson1.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson1.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.MONDAY-1);


        LessonTemplate lessonTemplate2 = getTestData().get(1);
        Lesson newLesson2 = newLessons.get(1);

        assertThat(newLesson2.getStartDate()).isEqualTo(Instant.parse("2019-10-08T09:30:00.00Z"));
        assertThat(newLesson2.getEndDate()).isEqualTo(Instant.parse("2019-10-08T12:00:00.00Z"));
        assertThat(newLesson2.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson2.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(newLesson2.getDescription()).isEqualTo("ajvh");
        assertThat(newLesson2.getStreet()).isEqualTo(lessonTemplate2.getStreet());
        assertThat(newLesson2.getCity()).isEqualTo(lessonTemplate2.getCity());
        assertThat(newLesson2.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson2.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson2.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson2.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.TUESDAY-1);

        LessonTemplate lessonTemplate3 = getTestData().get(2);
        Lesson newLesson3 = newLessons.get(2);

        assertThat(newLesson3.getStartDate()).isEqualTo(Instant.parse("2019-10-08T18:00:00.00Z"));
        assertThat(newLesson3.getEndDate()).isEqualTo(Instant.parse("2019-10-08T19:30:00.00Z"));
        assertThat(newLesson3.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson3.getTeacher().getFirstName()).isEqualTo("Yelena");
        assertThat(newLesson3.getDescription()).isEqualTo("ajvh");
        assertThat(newLesson3.getStreet()).isEqualTo(lessonTemplate3.getStreet());
        assertThat(newLesson3.getCity()).isEqualTo(lessonTemplate3.getCity());
        assertThat(newLesson3.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson3.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson3.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson3.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.TUESDAY-1);

        LessonTemplate lessonTemplate4 = getTestData().get(3);
        Lesson newLesson4 = newLessons.get(3);

        assertThat(newLesson4.getStartDate()).isEqualTo(Instant.parse("2019-10-09T18:30:00.00Z"));
        assertThat(newLesson4.getEndDate()).isEqualTo(Instant.parse("2019-10-09T20:00:00.00Z"));
        assertThat(newLesson4.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson2.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(newLesson4.getDescription()).isEqualTo("ajvh");
        assertThat(newLesson4.getStreet()).isEqualTo(lessonTemplate4.getStreet());
        assertThat(newLesson4.getCity()).isEqualTo(lessonTemplate4.getCity());
        assertThat(newLesson4.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson4.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson4.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson4.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.WEDNESDAY-1);

        LessonTemplate lessonTemplate5 = getTestData().get(4);
        Lesson newLesson5 = newLessons.get(4);

        assertThat(newLesson5.getStartDate()).isEqualTo(Instant.parse("2019-10-10T09:00:00.00Z"));
        assertThat(newLesson5.getEndDate()).isEqualTo(Instant.parse("2019-10-10T11:00:00.00Z"));
        assertThat(newLesson5.getName()).isEqualTo("SRE");
        assertThat(newLesson5.getTeacher().getFirstName()).isEqualTo("Yelena");
        assertThat(newLesson5.getDescription()).isEqualTo("zzzzz");
        assertThat(newLesson5.getStreet()).isEqualTo(lessonTemplate5.getStreet());
        assertThat(newLesson5.getCity()).isEqualTo(lessonTemplate5.getCity());
        assertThat(newLesson5.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson5.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson5.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson5.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.THURSDAY-1);

        LessonTemplate lessonTemplate6 = getTestData().get(5);
        Lesson newLesson6 = newLessons.get(5);

        assertThat(newLesson6.getStartDate()).isEqualTo(Instant.parse("2019-10-10T18:00:00.00Z"));
        assertThat(newLesson6.getEndDate()).isEqualTo(Instant.parse("2019-10-10T19:30:00.00Z"));
        assertThat(newLesson6.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson6.getTeacher().getFirstName()).isEqualTo("Yelena");
        assertThat(newLesson6.getDescription()).isEqualTo("zzzzz");
        assertThat(newLesson6.getStreet()).isEqualTo(lessonTemplate6.getStreet());
        assertThat(newLesson6.getCity()).isEqualTo(lessonTemplate6.getCity());
        assertThat(newLesson6.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson6.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson6.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson6.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.THURSDAY-1);

        LessonTemplate lessonTemplate7 = getTestData().get(6);
        Lesson newLesson7 = newLessons.get(6);

        assertThat(newLesson7.getStartDate()).isEqualTo(Instant.parse("2019-10-11T18:30:00.00Z"));
        assertThat(newLesson7.getEndDate()).isEqualTo(Instant.parse("2019-10-11T20:00:00.00Z"));
        assertThat(newLesson7.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson7.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(newLesson7.getDescription()).isEqualTo("zzzzz");
        assertThat(newLesson7.getStreet()).isEqualTo(lessonTemplate7.getStreet());
        assertThat(newLesson7.getCity()).isEqualTo(lessonTemplate7.getCity());
        assertThat(newLesson7.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson7.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson7.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson7.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.FRIDAY-1);


        LessonTemplate lessonTemplate8 = getTestData().get(7);
        Lesson newLesson8 = newLessons.get(7);

        assertThat(newLesson8.getStartDate()).isEqualTo(Instant.parse("2019-10-12T09:00:00.00Z"));
        assertThat(newLesson8.getEndDate()).isEqualTo(Instant.parse("2019-10-12T10:30:00.00Z"));
        assertThat(newLesson8.getName()).isEqualTo("Zen Meditation");
        assertThat(newLesson8.getTeacher().getFirstName()).isEqualTo("Yelena");
        assertThat(newLesson8.getDescription()).isEqualTo("zzzzz");
        assertThat(newLesson8.getStreet()).isEqualTo(lessonTemplate8.getStreet());
        assertThat(newLesson8.getCity()).isEqualTo(lessonTemplate8.getCity());
        assertThat(newLesson8.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson8.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson8.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson8.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.SATURDAY-1);

    }

    @Test
    void testGenerateLessonsFromTemplatesByDates() {

        System.out.println(timetableStartDate);
        System.out.println(timetableStartDate.getDayOfWeek());

        List<Lesson> newLessons = lessonService.generateLessonsFromTemplatesByDates(timetableStartDate, timetableEndDate, getTestDataWithoutLessons());
        assertNotNull(newLessons);
        assertFalse(newLessons.isEmpty());
        System.out.println(newLessons.size());


        LessonTemplate lessonTemplate1 = getTestDataWithoutLessons().get(0);
        Lesson newLesson1 = newLessons.get(0);

        assertThat(newLesson1.getStartDate()).isEqualTo(Instant.parse("2019-10-08T18:00:00.00Z"));
        assertThat(newLesson1.getEndDate()).isEqualTo(Instant.parse("2019-10-08T19:30:00.00Z"));
        assertThat(newLesson1.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson1.getTeacher().getFirstName()).isEqualTo("Yelena");
        assertThat(newLesson1.getDescription()).isEqualTo("ajvh");
        assertThat(newLesson1.getStreet()).isEqualTo(lessonTemplate1.getStreet());
        assertThat(newLesson1.getCity()).isEqualTo(lessonTemplate1.getCity());
        assertThat(newLesson1.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson1.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson1.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson1.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.TUESDAY-1);

        LessonTemplate lessonTemplate2 = getTestDataWithoutLessons().get(1);
        Lesson newLesson2 = newLessons.get(1);

        assertThat(newLesson2.getStartDate()).isEqualTo(Instant.parse("2019-10-09T18:30:00.00Z"));
        assertThat(newLesson2.getEndDate()).isEqualTo(Instant.parse("2019-10-09T20:00:00.00Z"));
        assertThat(newLesson2.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson2.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(newLesson2.getDescription()).isEqualTo("ajvh");
        assertThat(newLesson2.getStreet()).isEqualTo(lessonTemplate2.getStreet());
        assertThat(newLesson2.getCity()).isEqualTo(lessonTemplate2.getCity());
        assertThat(newLesson2.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson2.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson2.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson2.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.WEDNESDAY-1);

        LessonTemplate lessonTemplate3 = getTestDataWithoutLessons().get(2);
        Lesson newLesson3 = newLessons.get(2);

        assertThat(newLesson3.getStartDate()).isEqualTo(Instant.parse("2019-10-10T09:00:00.00Z"));
        assertThat(newLesson3.getEndDate()).isEqualTo(Instant.parse("2019-10-10T11:00:00.00Z"));
        assertThat(newLesson3.getName()).isEqualTo("SRE");
        assertThat(newLesson3.getTeacher().getFirstName()).isEqualTo("Yelena");
        assertThat(newLesson3.getDescription()).isEqualTo("zzzzz");
        assertThat(newLesson3.getStreet()).isEqualTo(lessonTemplate3.getStreet());
        assertThat(newLesson3.getCity()).isEqualTo(lessonTemplate3.getCity());
        assertThat(newLesson3.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson3.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson3.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson3.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.THURSDAY-1);

        LessonTemplate lessonTemplate4 = getTestDataWithoutLessons().get(3);
        Lesson newLesson4 = newLessons.get(3);

        assertThat(newLesson4.getStartDate()).isEqualTo(Instant.parse("2019-10-10T18:00:00.00Z"));
        assertThat(newLesson4.getEndDate()).isEqualTo(Instant.parse("2019-10-10T19:30:00.00Z"));
        assertThat(newLesson4.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson4.getTeacher().getFirstName()).isEqualTo("Yelena");
        assertThat(newLesson4.getDescription()).isEqualTo("zzzzz");
        assertThat(newLesson4.getStreet()).isEqualTo(lessonTemplate4.getStreet());
        assertThat(newLesson4.getCity()).isEqualTo(lessonTemplate4.getCity());
        assertThat(newLesson4.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson4.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson4.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson4.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.THURSDAY-1);

        LessonTemplate lessonTemplate5 = getTestDataWithoutLessons().get(4);
        Lesson newLesson5 = newLessons.get(4);

        assertThat(newLesson5.getStartDate()).isEqualTo(Instant.parse("2019-10-11T18:30:00.00Z"));
        assertThat(newLesson5.getEndDate()).isEqualTo(Instant.parse("2019-10-11T20:00:00.00Z"));
        assertThat(newLesson5.getName()).isEqualTo("Kundalini Jooga");
        assertThat(newLesson5.getTeacher().getFirstName()).isEqualTo("Anastassia");
        assertThat(newLesson5.getDescription()).isEqualTo("zzzzz");
        assertThat(newLesson5.getStreet()).isEqualTo(lessonTemplate5.getStreet());
        assertThat(newLesson5.getCity()).isEqualTo(lessonTemplate5.getCity());
        assertThat(newLesson5.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson5.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson5.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson5.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.FRIDAY-1);

        LessonTemplate lessonTemplate6 = getTestDataWithoutLessons().get(5);
        Lesson newLesson6 = newLessons.get(5);

        assertThat(newLesson6.getStartDate()).isEqualTo(Instant.parse("2019-10-12T09:00:00.00Z"));
        assertThat(newLesson6.getEndDate()).isEqualTo(Instant.parse("2019-10-12T10:30:00.00Z"));
        assertThat(newLesson6.getName()).isEqualTo("Zen Meditation");
        assertThat(newLesson6.getTeacher().getFirstName()).isEqualTo("Yelena");
        assertThat(newLesson6.getDescription()).isEqualTo("zzzzz");
        assertThat(newLesson6.getStreet()).isEqualTo(lessonTemplate6.getStreet());
        assertThat(newLesson6.getCity()).isEqualTo(lessonTemplate6.getCity());
        assertThat(newLesson6.getAvailableSpaces()).isEqualTo(10);

        System.out.println(newLesson6.getStartDate());
        System.out.println(LocalDateTime.ofInstant(newLesson6.getStartDate(), ZoneOffset.UTC).getDayOfWeek());
        assertThat(LocalDateTime.ofInstant(newLesson6.getStartDate(), ZoneOffset.UTC).getDayOfWeek().getValue()).isEqualTo(Calendar.SATURDAY-1);

    }

    @Test
    void test1GenerateLessonsFromTemplatesByDates() {
        List<Lesson> newLessons = lessonService.generateLessonsFromTemplatesByDates(timetableStartDate, timetableEndDate, getTestDataWithoutLessons());
        assertNotNull(newLessons);
        System.out.println(newLessons.size());
        System.out.println(newLessons.get(3));

    }

    @Test
    void test2GenerateLessonsFromTemplatesByDatesForTwoWeeks() {
        List<Lesson> newLessons = lessonService.generateLessonsFromTemplatesByDates(timetableStartDate, timetableEndDateTimetableForTwoWeeks, getTestDataWithoutLessons());
        assertNotNull(newLessons);
        System.out.println(newLessons.size());
        assertThat(newLessons.size()).isEqualTo(12);
    }

    @Test
    void test1GenerateLessonsFromTemplatesForDay() {

        List<Lesson> newLessons = lessonService.generateLessonsFromTemplatesForDay(timetableStartDateIsOnDateOfLesson, getTestData());
        assertNotNull(newLessons);
        assertFalse(newLessons.isEmpty());
        System.out.println(newLessons.size());
        System.out.println(newLessons.get(0));
    }

    @Test
    void test3GenerateLessonsFromTemplatesForDay() {

        List<Lesson> newLessons = lessonService.generateLessonsFromTemplatesForDay(timetableStartDateIsAfterDateOfLesson, getTestData());
        assertNotNull(newLessons);
        assertFalse(newLessons.isEmpty());
    }
}
