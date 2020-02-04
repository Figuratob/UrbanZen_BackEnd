package ee.urbanzen.backoffice.service;

import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing bookings.
 */

@Service
@Transactional
public class LessonTemplateService {

    private static final String ENTITY_NAME = "lessonTemplate";

    private final LessonTemplateRepository lessonTemplateRepository;


    public LessonTemplateService(LessonTemplateRepository lessonTemplateRepository) {
        this.lessonTemplateRepository = lessonTemplateRepository;
    }

    public LessonTemplate save(LessonTemplate lessonTemplate) {
        return lessonTemplateRepository.save(lessonTemplate);
    }

    public List<LessonTemplate> findAllByDatesWithoutLessons(LocalDate timetableStartDateTimeLocalDate,
                                                             LocalDate timetableEndDateTimeLocalDate) {
        return lessonTemplateRepository.findAllByDatesWithoutLessons(timetableStartDateTimeLocalDate,
            timetableEndDateTimeLocalDate);
    }

    public List<LessonTemplate> findAll() {
        return lessonTemplateRepository.findAll();
    }

    public Optional<LessonTemplate> findById(Long id) {
        return lessonTemplateRepository.findById(id);
    }

    public void deleteById(Long id) {
        lessonTemplateRepository.deleteById(id);
    }

    /**
     * load all templates that match criteria from database
     * for every template generate lessons
     * save lessons to database
     */
    public static List<Lesson> generateLessonsFromTemplatesByDates(LocalDate timetableStartDateTimeLocalDate,
                                                                   LocalDate timetableEndDateTimeLocalDate,
                                                                   List<LessonTemplate> lessonTemplatesByDatesWithoutLessons) {

        List<Lesson> lessons = new ArrayList<>();

        List<LocalDate> dates = getDatesBetween(timetableStartDateTimeLocalDate, timetableEndDateTimeLocalDate);

        for (LocalDate date : dates) {

            for (LessonTemplate lessonTemplate : lessonTemplatesByDatesWithoutLessons) {

                boolean templateIsActive = lessonTemplate.isActiveOnGivenDate(date);
                if (templateIsActive) {

                    if (date.getDayOfWeek().getValue() == lessonTemplate.getDayOfWeek().getValue()) {

                        Instant startDate = date.atTime(lessonTemplate.getStartHour(),
                            lessonTemplate.getStartMinute()).toInstant(ZoneOffset.UTC);
                        Instant endDate = date.atTime(lessonTemplate.getEndHour(),
                            lessonTemplate.getEndMinute()).toInstant(ZoneOffset.UTC);

                        Lesson lesson = new Lesson()
                            .startDate(startDate)
                            .endDate(endDate)
                            .name(lessonTemplate.getName())
                            .description(lessonTemplate.getDescription())
                            .street(lessonTemplate.getStreet())
                            .city(lessonTemplate.getCity())
                            .availableSpaces(lessonTemplate.getAvailableSpaces())
                            .teacher(lessonTemplate.getTeacher())
                            .lessonTemplate(lessonTemplate);
                        lessons.add(lesson);
                    }
                }
            }
        }
        return lessons;
    }

    public static List<LocalDate> getDatesBetween(
        LocalDate timetableStartDateTimeLocalDate, LocalDate timetableEndDateTimeLocalDate) {

        return timetableStartDateTimeLocalDate.datesUntil(timetableEndDateTimeLocalDate)
            .collect(Collectors.toList());
    }

    public static List<Lesson> generateLessonsFromTemplatesForDay(LocalDate startDateTimeLocalDate,
                                                                  List<LessonTemplate> lessonTemplatesByDatesWithoutLessons) {

        List<Lesson> lessons = new ArrayList<>();

        for (LessonTemplate lessonTemplate : lessonTemplatesByDatesWithoutLessons) {

            if (startDateTimeLocalDate.getDayOfWeek().getValue() == lessonTemplate.getDayOfWeek().getValue() &&
                startDateTimeLocalDate.isAfter(lessonTemplate.getRepeatStartDate()) &&
                startDateTimeLocalDate.isBefore(lessonTemplate.getRepeatUntilDate())) {

                Instant startDate = startDateTimeLocalDate.atTime(lessonTemplate.getStartHour(),
                    lessonTemplate.getStartMinute()).toInstant(ZoneOffset.UTC);
                Instant endDate = startDateTimeLocalDate.atTime(lessonTemplate.getEndHour(),
                    lessonTemplate.getEndMinute()).toInstant(ZoneOffset.UTC);

                Lesson lesson = new Lesson()
                    .startDate(startDate)
                    .endDate(endDate)
                    .name(lessonTemplate.getName())
                    .description(lessonTemplate.getDescription())
                    .street(lessonTemplate.getStreet())
                    .city(lessonTemplate.getCity())
                    .availableSpaces(lessonTemplate.getAvailableSpaces())
                    .teacher(lessonTemplate.getTeacher())
                    .lessonTemplate(lessonTemplate);
                lessons.add(lesson);
            }
        }
        return lessons;
    }

    public static List<Lesson> generateLessonsFromTemplates(LocalDate startDateTimeLocalDate,
                                                            LocalDate untilDateTimeLocalDate,
                                                            List<LessonTemplate> lessonTemplatesByWeekOfDay) {

        List<Lesson> lessons = new ArrayList<>();
        for (LessonTemplate lessonTemplate : lessonTemplatesByWeekOfDay) {

            // MONDAY = 1
            int numberOfDayOfWeek = lessonTemplate.getDayOfWeek().ordinal();
            // MONDAY = 2
            int numberOfStartDateTime = startDateTimeLocalDate.getDayOfWeek().ordinal();
            int days = numberOfDayOfWeek - numberOfStartDateTime;

            LocalDate lessonStartDateTimeLocalDate = startDateTimeLocalDate.plusDays(days);

            Instant startDate = lessonStartDateTimeLocalDate.atTime(lessonTemplate.getStartHour(),
                lessonTemplate.getStartMinute()).toInstant(ZoneOffset.UTC);
            Instant endDate = lessonStartDateTimeLocalDate.atTime(lessonTemplate.getEndHour(),
                lessonTemplate.getEndMinute()).toInstant(ZoneOffset.UTC);

            Lesson lesson = new Lesson()
                .startDate(startDate)
                .endDate(endDate)
                .name(lessonTemplate.getName())
                .description(lessonTemplate.getDescription())
                .street(lessonTemplate.getStreet())
                .city(lessonTemplate.getCity())
                .availableSpaces(lessonTemplate.getAvailableSpaces())
                .teacher(lessonTemplate.getTeacher());
            lessons.add(lesson);
        }
        return lessons;
    }

}
