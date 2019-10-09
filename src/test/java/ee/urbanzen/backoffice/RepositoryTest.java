package ee.urbanzen.backoffice;

import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.domain.Teacher;
import ee.urbanzen.backoffice.domain.enumeration.DayOfWeek;
import ee.urbanzen.backoffice.repository.LessonRepository;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
import ee.urbanzen.backoffice.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


@SpringBootTest(classes = UrbanZenApp.class)
@Transactional
public class RepositoryTest {

    @Autowired
    private LessonTemplateRepository lessonTemplateRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void testRepository() {

        byte[] photo = new byte[] {};
        Teacher teacher = new Teacher()
            .firstName("Anastassia")
            .lastName("Kundalini")
            .email("kundalini2019@gmail.com")
            .phone("+37253446498")
            .photo(photo)
            .photoContentType("hfila")
            .about("hafhvg");

        teacherRepository.saveAndFlush(teacher);

        LessonTemplate lessonTemplate1 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.Monday)
            .startHour(18)
            .startMinute(30)
            .endHour(20)
            .endMinute(0)
            .name("Kundalini Jooga")
            .description("ajvh")
            .street("")
            .city("")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019,10,3))
            .repeatUntilDate(LocalDate.of(2019, 12, 31))
            .teacher(teacher);
        LessonTemplate lessonTemplate2 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.Tuesday)
            .startHour(9)
            .startMinute(30)
            .endHour(12)
            .endMinute(0)
            .name("Kundalini Jooga")
            .description("ajvh")
            .street("")
            .city("")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019,10,3))
            .repeatUntilDate(LocalDate.of(2019, 12, 31))
            .teacher(teacher);
        LessonTemplate lessonTemplate3 = new LessonTemplate()
            .dayOfWeek(DayOfWeek.Wednesday)
            .startHour(18)
            .startMinute(30)
            .endHour(20)
            .endMinute(0)
            .name("Kundalini Jooga")
            .description("ajvh")
            .street("")
            .city("")
            .availableSpaces(10)
            .repeatStartDate(LocalDate.of(2019,10,3))
            .repeatUntilDate(LocalDate.of(2019, 12, 31))
            .teacher(teacher);

        List<LessonTemplate> lessonTemplateList = new ArrayList<>();
        lessonTemplateList.add(lessonTemplate1);
        lessonTemplateList.add(lessonTemplate2);
        lessonTemplateList.add(lessonTemplate3);

        lessonTemplateRepository.saveAll(lessonTemplateList);
        lessonTemplateRepository.flush();
        List<LessonTemplate> all = lessonTemplateRepository.findAll();
        System.out.println(all);
        System.out.println("!!!!!");
        assertNotNull(all);
        assertEquals(3, all.size());

        List<LessonTemplate> tested = lessonTemplateRepository.findAllByDates(
            LocalDate.now(),
            null);





    }
}
