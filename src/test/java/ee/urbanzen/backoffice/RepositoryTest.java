package ee.urbanzen.backoffice;
import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.domain.Teacher;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
import ee.urbanzen.backoffice.repository.TeacherRepository;
import ee.urbanzen.backoffice.web.rest.LessonTemplateResource;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = UrbanZenApp.class)
@Transactional
public class RepositoryTest {

    @Autowired
    private LessonTemplateRepository lessonTemplateRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    LessonTemplateResource lessonTemplateResource;

    LocalDate timetableStartDate = LocalDate.of(2019,10,07);
    LocalDate timetableEndDate = LocalDate.of(2019,10,13);
    LocalDate timetableEndDateTimetableForTwoWeeks=LocalDate.of(2019,10,20);

    LocalDate timetableStartDateIsBeforeRepeatStartDate = LocalDate.of(2019,10,01);
    LocalDate timetableStartDateIsOnRepeatStartDate = LocalDate.of(2019,10,03);
    LocalDate timetableStartDateIsAfterRepeatStartDate = LocalDate.of(2019,10,04);

    LocalDate timetableStartDateIsBeforeRepeatUntilDate = LocalDate.of(2019,12,30);
    LocalDate timetableStartDateIsOnRepeatUntilDate = LocalDate.of(2013,12,31);
    LocalDate timetableStartDateIsAfterRepeatUntilDate = LocalDate.of(2020,01,01);

    LocalDate timetableEndDateIsBeforeRepeatStartDate = LocalDate.of(2019,10,02);
    LocalDate timetableEndDateIsOnRepeatStartDate = LocalDate.of(2019,10,03);
    LocalDate timetableEndDateIsAfterRepeatStartDate = LocalDate.of(2019,10,04);

    LocalDate timetableEndDateIsBeforeRepeatUntilDate = LocalDate.of(2019,12,30);
    LocalDate timetableEndDateIsOnRepeatUntilDate = LocalDate.of(2019,12,31);
    LocalDate timetableEndDateIsAfterRepeatUntilDate = LocalDate.of(2020,01,02);

    @Before
    public void setupTest() {
        getTestData();
    }

    public List<LessonTemplate> getTestData() {

        byte[] photo1 = new byte[]{};
        Teacher teacher1 = new Teacher()
            .firstName("Anastassia")
            .lastName("Kundalini")
            .email("kundalini2019@gmail.com")
            .phone("+37253446498")
            .photo(photo1)
            .photoContentType("hfila")
            .about("hafhvg");

        byte[] photo2 = new byte[]{};
        Teacher teacher2 = new Teacher()
            .firstName("Yelena")
            .lastName("Kundalini")
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
            .description("ajvh")
            .street("")
            .city("")
            .availableSpaces(10)
            .teacher(teacher1);

        LessonTemplate lessonTemplate1 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.MONDAY)
            .startHour(18)
            .startMinute(30)
            .endHour(20)
            .endMinute(0)
            .name("Kundalini Jooga")
            .description("ajvh")
            .street("")
            .city("")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019, 10, 3))
            .repeatUntilDate(LocalDate.of(2019, 12, 31))
            .teacher(teacher1)
            .addLesson(lesson1);

        Lesson lesson2 = new Lesson()
            .startDate((LocalDateTime.of(LocalDate.of(2019, 10, 8), LocalTime.of(9,30))).toInstant(ZoneOffset.UTC))
            .endDate((LocalDateTime.of(LocalDate.of(2019, 10, 8), LocalTime.of(12,00))).toInstant(ZoneOffset.UTC))
            .name("Kundalini Jooga")
            .description("ajvh")
            .street("")
            .city("")
            .availableSpaces(10)
            .teacher(teacher1);

        LessonTemplate lessonTemplate2 = new LessonTemplate()

            .dayOfWeek(DayOfWeek.TUESDAY)
            .startHour(9)
            .startMinute(30)
            .endHour(12)
            .endMinute(0)
            .name("Kundalini Jooga")
            .description("ajvh")
            .street("")
            .city("")
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
            .description("ajvh")
            .street("")
            .city("")
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
            .description("ajvh")
            .street("")
            .city("")
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
            .description("zzzzz")
            .street("")
            .city("")
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
            .description("zzzzz")
            .street("")
            .city("")
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
            .description("zzzzz")
            .street("")
            .city("")
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
            .description("zzzzz")
            .street("")
            .city("")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019,10,3))
            .repeatUntilDate(LocalDate.of(2019,12,31))
            .teacher(teacher2);

        lessonTemplateRepository.saveAndFlush(lessonTemplate1);
        lessonTemplateRepository.saveAndFlush(lessonTemplate2);
        lessonTemplateRepository.saveAndFlush(lessonTemplate3);
        lessonTemplateRepository.saveAndFlush(lessonTemplate4);

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

    @Test
    void test1() {

    }

    @Test
    void testFindAll() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> all = lessonTemplateRepository.findAll();
        System.out.println(all);
        System.out.println("!!!!!");
        assertNotNull(all);
        assertEquals(8, all.size());
    }

    private void printToConsole(List<LessonTemplate> testData, List<LessonTemplate> tested, LocalDate timetableStartDate, LocalDate timetableEndDate) {
        System.out.println();
        System.out.println("testData size:" + testData.size());
        System.out.println();
        for (int i = 0; i < testData.size(); i++) {

            System.out.println("template" + i + " repeatStartDate: " + testData.get(i).getRepeatStartDate() +
                " repeatUntilDate: " + testData.get(i).getRepeatUntilDate());
            if (testData.get(i).getLessons().isEmpty()) {
                System.out.println("*");
            }
        }
        System.out.println();
        System.out.println("timetableStartDate: " + timetableStartDate +
            " timetableEndDate: " + timetableEndDate);
        System.out.println();
        System.out.println("tested size: " + tested.size());
        System.out.println();
    }

    @Test
    void test1FindAllByDatesIfTimetableStartDateIsBeforeRepeatStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsOnRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsOnRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test2FindAllByDatesIfTimetableStartDateIsOnRepeatStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsOnRepeatStartDate,
            timetableEndDateIsOnRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsOnRepeatStartDate, timetableEndDateIsOnRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test3FindAllByDatesIfTimetableStartDateIsAfterRepeatStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsAfterRepeatStartDate,
            timetableEndDateIsOnRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsAfterRepeatStartDate, timetableEndDateIsOnRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test4FindAllByDatesIfTimetableEndDateIsBeforeRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsOnRepeatStartDate,
            timetableEndDateIsBeforeRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsOnRepeatStartDate, timetableEndDateIsBeforeRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test5FindAllByDatesIfTimetableEndDateIsOnRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsOnRepeatStartDate,
            timetableEndDateIsOnRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsOnRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test6FindAllByDatesIfTimetableEndDateIsAfterRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsOnRepeatStartDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsOnRepeatStartDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test7FindAllByDatesIfTimetableStartDateIsBeforeRepeatStartDateAndTimetableEndDateIsBeforeRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsBeforeRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsBeforeRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test8FindAllByDatesIfTimetableStartDateIsAfterRepeatStartDateAndTimetableEndDateIsAfterRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsAfterRepeatStartDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsAfterRepeatStartDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test9FindAllByDatesIfTimetableStartDateIsBeforeRepeatStartDateAndTimetableEndDateIsAfterRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsAfterRepeatUntilDate);

        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test10FindAllByDatesIfTimetableStartDateIsAfterRepeatStartDateAndTimetableEndDateIsBeforeRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsAfterRepeatStartDate,
            timetableEndDateIsBeforeRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsAfterRepeatStartDate, timetableEndDateIsBeforeRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test11FindAllByDatesIfTimetableEndDateIsBeforeTemplateStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsBeforeRepeatStartDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsBeforeRepeatStartDate);
        assertNotNull(tested);
        assertEquals(0, tested.size());
    }

    @Test
    void test12FindAllByDatesIfTimetableEndDateIsOnTemplateStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsOnRepeatStartDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsOnRepeatStartDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test13FindAllByDatesIfTimetableEndDateIsAfterTemplateStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsAfterRepeatStartDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsAfterRepeatStartDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test14FindAllByDatesIfTimetableStartDateIsBeforeTemplateUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsBeforeRepeatUntilDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatUntilDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test15FindAllByDatesIfTimetableStartDateIsOnTemplateUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsOnRepeatUntilDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsOnRepeatUntilDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test16FindAllByDatesIfTimetableStartDateIsAfterTemplateUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsAfterRepeatUntilDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsAfterRepeatUntilDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(1, tested.size());
    }

    @Test
    void testFindAllByDatesWithoutLessons() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDate,
            timetableEndDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDate, timetableEndDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void testFindAllByDatesWithoutLessonsForTwoWeeks() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDate,
            timetableEndDateTimetableForTwoWeeks);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDate, timetableEndDateTimetableForTwoWeeks);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test1FindAllByDatesWithoutLessonsIfTimetableStartDateIsBeforeRepeatStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsOnRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsOnRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test2FindAllByDatesWithoutLessonsIfTimetableStartDateIsOnRepeatStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsOnRepeatStartDate,
            timetableEndDateIsOnRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsOnRepeatStartDate, timetableEndDateIsOnRepeatUntilDate);

        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test3FindAllByDatesWithoutLessonsIfTimetableStartDateIsAfterRepeatStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsAfterRepeatStartDate,
            timetableEndDateIsOnRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsAfterRepeatStartDate, timetableEndDateIsOnRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test4FindAllByDatesWithoutLessonsIfTimetableEndDateIsBeforeRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsOnRepeatStartDate,
            timetableEndDateIsBeforeRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsOnRepeatStartDate, timetableEndDateIsBeforeRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test5FindAllByDatesWithoutLessonsIfTimetableEndDateIsOnRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(timetableStartDateIsOnRepeatStartDate,
            timetableEndDateIsOnRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsOnRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(8, tested.size());
    }

    @Test
    void test6FindAllByDatesWithoutLessonsIfTimetableEndDateIsAfterRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsOnRepeatStartDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsOnRepeatStartDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test7FindAllByDatesWithoutLessonsIfTimetableStartDateIsBeforeRepeatStartDateAndTimetableEndDateIsBeforeRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsBeforeRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsBeforeRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test8FindAllByDatesWithoutLessonsIfTimetableStartDateIsAfterRepeatStartDateAndTimetableEndDateIsAfterRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsAfterRepeatStartDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsAfterRepeatStartDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test9FindAllByDatesWithoutLessonsIfTimetableStartDateIsBeforeRepeatStartDateAndTimetableEndDateIsAfterRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test10FindAllByDatesWithoutLessonsIfTimetableStartDateIsAfterRepeatStartDateAndTimetableEndDateIsBeforeRepeatUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsAfterRepeatStartDate,
            timetableEndDateIsBeforeRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsAfterRepeatStartDate, timetableEndDateIsBeforeRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test11FindAllByDatesWithoutLessonsIfTimetableEndDateIsBeforeTemplateStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsBeforeRepeatStartDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsBeforeRepeatStartDate);
        assertNotNull(tested);
        assertEquals(0, tested.size());
    }

    @Test
    void test12FindAllByDatesWithoutLessonsIfTimetableEndDateIsOnTemplateStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsOnRepeatStartDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsOnRepeatStartDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test13FindAllByDatesWithoutLessonsIfTimetableEndDateIsAfterTemplateStartDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsBeforeRepeatStartDate,
            timetableEndDateIsAfterRepeatStartDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatStartDate, timetableEndDateIsAfterRepeatStartDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test14FindAllByDatesWithoutLessonsIfTimetableStartDateIsBeforeTemplateUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsBeforeRepeatUntilDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsBeforeRepeatUntilDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test15FindAllByDatesWithoutLessonsIfTimetableStartDateIsOnTemplateUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsOnRepeatUntilDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsOnRepeatUntilDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(6, tested.size());
    }

    @Test
    void test16FindAllByDatesWithoutLessonsIfTimetableStartDateIsAfterTemplateUntilDate() {
        lessonTemplateRepository.saveAll(getTestData());
        lessonTemplateRepository.flush();
        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateIsAfterRepeatUntilDate,
            timetableEndDateIsAfterRepeatUntilDate);
        printToConsole(lessonTemplateResource.getAllLessonTemplates(), tested, timetableStartDateIsAfterRepeatUntilDate, timetableEndDateIsAfterRepeatUntilDate);
        assertNotNull(tested);
        assertEquals(1, tested.size());
    }

}
