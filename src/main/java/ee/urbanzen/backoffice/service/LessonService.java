package ee.urbanzen.backoffice.service;

import ee.urbanzen.backoffice.domain.Booking;
import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.domain.User;
import ee.urbanzen.backoffice.repository.LessonRepository;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
import ee.urbanzen.backoffice.domain.Entry;
import ee.urbanzen.backoffice.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing lessons.
 */


@Service
@Transactional
public class LessonService {

    private static final String ENTITY_NAME = "lesson";
    private final LessonRepository lessonRepository;
    private final LessonTemplateRepository lessonTemplateRepository;
    private final MailService mailService;
    private final EntityManager entityManager;

    public LessonService(LessonRepository lessonRepository,
                         MailService mailService,
                         LessonTemplateRepository lessonTemplateRepository, EntityManager entityManager) {
        this.lessonRepository = lessonRepository;
        this.mailService = mailService;
        this.lessonTemplateRepository = lessonTemplateRepository;
        this.entityManager = entityManager;
    }

    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public List<Lesson> saveAll(Collection<Lesson> lessons) {
        return lessonRepository.saveAll(lessons);
    }

    public void flush() {
        lessonRepository.flush();
    }

    public Optional<Lesson> findById(Long id) {
        return lessonRepository.findById(id);
    }

    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    public List<Lesson> findLessonsByDates(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        Instant firstDayOfWeekInstant = firstDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant lastDayOfWeekInstant = lastDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return lessonRepository.findAllByDates(firstDayOfWeekInstant, lastDayOfWeekInstant);
    }

    public List<Lesson> findLessonsByDatesIncludingLast(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        Instant firstDayOfWeekInstant = firstDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant lastDayOfWeekInstant = (lastDayOfWeek.plusDays(1)).atStartOfDay(ZoneId.systemDefault()).toInstant();
        return lessonRepository.findAllByDates(firstDayOfWeekInstant, lastDayOfWeekInstant);
    }


    public List<LocalDate> getDatesBetweenIncludingLast(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        return firstDayOfWeek.datesUntil(lastDayOfWeek.plusDays(1))
            .collect(Collectors.toList());
    }

    public List<LocalDate> getDatesBetween(
        LocalDate timetableStartDateTimeLocalDate, LocalDate timetableEndDateTimeLocalDate) {

        return timetableStartDateTimeLocalDate.datesUntil(timetableEndDateTimeLocalDate)
            .collect(Collectors.toList());
    }

    /**
     * load all templates that match criteria (not including EndDate) from database
     * for every template generate lessons
     * save lessons to database
     */
    public List<Lesson> generateLessonsFromTemplatesByDates(LocalDate timetableStartDateTimeLocalDate,
                                                            LocalDate timetableEndDateTimeLocalDate,
                                                            List<LessonTemplate> lessonTemplatesByDatesWithoutLessons) {

        List<Lesson> lessons = new ArrayList<>();

        // !!! getDatesBetween doesn't include last date

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
                            .nameEng(lessonTemplate.getNameEng())
                            .nameRus(lessonTemplate.getNameRus())
                            .description(lessonTemplate.getDescription())
                            .descriptionEng(lessonTemplate.getDescriptionEng())
                            .descriptionRus(lessonTemplate.getDescriptionRus())
                            .street(lessonTemplate.getStreet())
                            .streetEng(lessonTemplate.getStreetEng())
                            .streetRus(lessonTemplate.getStreetRus())
                            .city(lessonTemplate.getCity())
                            .cityEng(lessonTemplate.getCityEng())
                            .cityRus(lessonTemplate.getCityRus())
                            .availableSpaces(lessonTemplate.getAvailableSpaces())
                            .teacher(lessonTemplate.getTeacher())
                            .lessonTemplate(lessonTemplate);
                        lessonTemplate.addLesson(lesson);
                        lessons.add(lesson);
                    }
                }
            }
        }
        saveAll(lessons);
        flush();
        return lessons;
    }
    /**
     * load all templates that match criteria (including both: StartDate and EndDate) from database
     * for every template generate lessons
     * save lessons to database
     */
    public List<Lesson> generateLessonsFromTemplatesByDatesIncludingEndDate (LocalDate timetableStartDateTimeLocalDate,
                                                            LocalDate timetableEndDateTimeLocalDate,
                                                            List<LessonTemplate> lessonTemplatesByDatesWithoutLessons) {

        List<Lesson> lessons = new ArrayList<>();

        List<LocalDate> dates = getDatesBetweenIncludingLast(timetableStartDateTimeLocalDate, timetableEndDateTimeLocalDate);

//        List<LocalDate> dates = getDatesBetween(timetableStartDateTimeLocalDate, timetableEndDateTimeLocalDate);

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
                            .nameEng(lessonTemplate.getNameEng())
                            .nameRus(lessonTemplate.getNameRus())
                            .description(lessonTemplate.getDescription())
                            .descriptionEng(lessonTemplate.getDescriptionEng())
                            .descriptionRus(lessonTemplate.getDescriptionRus())
                            .street(lessonTemplate.getStreet())
                            .streetEng(lessonTemplate.getStreetEng())
                            .streetRus(lessonTemplate.getStreetRus())
                            .city(lessonTemplate.getCity())
                            .cityEng(lessonTemplate.getCityEng())
                            .cityRus(lessonTemplate.getCityRus())
                            .availableSpaces(lessonTemplate.getAvailableSpaces())
                            .teacher(lessonTemplate.getTeacher())
                            .lessonTemplate(lessonTemplate);
                        lessonTemplate.addLesson(lesson);
                        lessons.add(lesson);
                    }
                }
            }
        }
        saveAll(lessons);
        flush();
        return lessons;
    }

    public List<Lesson> generateLessonsFromTemplates(LocalDate startDateTimeLocalDate,
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

    public List<Lesson> generateLessonsFromTemplatesForDay(LocalDate startDateTimeLocalDate,
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
                    .nameEng(lessonTemplate.getNameEng())
                    .nameRus(lessonTemplate.getNameRus())
                    .description(lessonTemplate.getDescription())
                    .descriptionEng(lessonTemplate.getDescriptionEng())
                    .descriptionRus(lessonTemplate.getDescriptionRus())
                    .street(lessonTemplate.getStreet())
                    .streetEng(lessonTemplate.getStreetEng())
                    .streetRus(lessonTemplate.getStreetRus())
                    .city(lessonTemplate.getCity())
                    .cityEng(lessonTemplate.getCityEng())
                    .cityRus(lessonTemplate.getCityRus())
                    .availableSpaces(lessonTemplate.getAvailableSpaces())
                    .teacher(lessonTemplate.getTeacher())
                    .lessonTemplate(lessonTemplate);
                lessons.add(lesson);
            }
        }
        return lessons;
    }

//    public List<Lesson> generateLessonsFromTemplate(LessonTemplate lessonTemplate) {
//
//        List<Lesson> lessons = new ArrayList<>();
//
//        Lesson lesson = new Lesson()
//            .startDate(lessonTemplate.getRepeatStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant())
//            .endDate(lessonTemplate.getRepeatUntilDate().atStartOfDay(ZoneId.systemDefault()).toInstant())
//            .name(lessonTemplate.getName())
//            .nameEng(lessonTemplate.getNameEng())
//            .nameRus(lessonTemplate.getNameRus())
//            .description(lessonTemplate.getDescription())
//            .descriptionEng(lessonTemplate.getDescriptionEng())
//            .descriptionRus(lessonTemplate.getDescriptionRus())
//            .street(lessonTemplate.getStreet())
//            .street(lessonTemplate.getStreetEng())
//            .street(lessonTemplate.getStreetRus())
//            .city(lessonTemplate.getCity())
//            .cityEng(lessonTemplate.getCityEng())
//            .cityRus(lessonTemplate.getCityRus())
//            .availableSpaces(lessonTemplate.getAvailableSpaces())
//            .teacher(lessonTemplate.getTeacher())
//            .lessonTemplate(lessonTemplate);
//        lessons.add(lesson);
//
//        return lessons;
//    }

    public Set<Lesson> findLessonsByLessonTemplateAndDates(LocalDate firstDate, LocalDate secondDate,
                                                           LessonTemplate updatedLessonTemplate) {
        Set<Lesson> lessons = new HashSet<>();

        LessonTemplate oldLessonTemplate = lessonTemplateRepository
            .findById(updatedLessonTemplate.getId())
            .orElseThrow(() -> new BadRequestAlertException("LessonTemplate not found", ENTITY_NAME, "lessonTemplatenotfound"));

        for (Lesson lesson : oldLessonTemplate.getLessons()) {

            if ( ((((lesson.getStartDate()).atZone(ZoneId.systemDefault()).toLocalDate()).isAfter(firstDate)) ||
                (((lesson.getStartDate()).atZone(ZoneId.systemDefault()).toLocalDate()).isEqual(firstDate)))
            &&
                ((((lesson.getStartDate()).atZone(ZoneId.systemDefault()).toLocalDate()).isBefore(secondDate)) ||
                (((lesson.getStartDate()).atZone(ZoneId.systemDefault()).toLocalDate()).isEqual(secondDate))) )
            {
                lessons.add(lesson);
            }
        }
        return lessons;
    }

    public void updateLessonsOfLessonTemplateByDates(LocalDate firstDate, LocalDate secondDate, LessonTemplate updatedLessonTemplate) {

        Set<Lesson> lessons = findLessonsByLessonTemplateAndDates(firstDate, secondDate, updatedLessonTemplate);
        updateLessonsByLessonTemplate(lessons, updatedLessonTemplate);
    }

//    public void updateLessonsOfLessonTemplate(LessonTemplate updatedLessonTemplate) {
//
////      Set<Lesson> lessons = updatedLessonTemplate.getLessons();
////        Set<Lesson> lessons = findOldLessonsByLessonTemplateAndDates(firstDate, secondDate, updatedLessonTemplate);
//      updateLessonsByLessonTemplate(lessons, updatedLessonTemplate);
//
//    }

    public void updateLessonsByLessonTemplate (Set<Lesson> lessons, LessonTemplate updatedLessonTemplate) {

        if (lessons != null) {

            for (Lesson lesson : lessons) {
                lesson
                    .startDate((lesson.getStartDate().atZone(ZoneId.systemDefault()).toLocalDate()).atTime(updatedLessonTemplate.getStartHour(),
                        updatedLessonTemplate.getStartMinute()).toInstant(ZoneOffset.UTC))
                    .endDate((lesson.getEndDate().atZone(ZoneId.systemDefault()).toLocalDate()).atTime(updatedLessonTemplate.getEndHour(),
                        updatedLessonTemplate.getEndMinute()).toInstant(ZoneOffset.UTC))
                    .name(updatedLessonTemplate.getName())
                    .nameEng(updatedLessonTemplate.getNameEng())
                    .nameRus(updatedLessonTemplate.getNameRus())
                    .description(updatedLessonTemplate.getDescription())
                    .descriptionEng(updatedLessonTemplate.getDescriptionEng())
                    .descriptionRus(updatedLessonTemplate.getDescriptionRus())
                    .street(updatedLessonTemplate.getStreet())
                    .streetEng(updatedLessonTemplate.getStreetEng())
                    .streetRus(updatedLessonTemplate.getStreetRus())
                    .city(updatedLessonTemplate.getCity())
                    .cityEng(updatedLessonTemplate.getCityEng())
                    .cityRus(updatedLessonTemplate.getCityRus())
                    .availableSpaces(updatedLessonTemplate.getAvailableSpaces())
                    .teacher(updatedLessonTemplate.getTeacher())
                    .lessonTemplate(updatedLessonTemplate);
            }

//            List<Lesson> lessonArrayList = new ArrayList<Lesson>(lessons);
            saveAll(lessons);
            flush();
        }
    }

    public void deleteLessonsByDates(LocalDate firstDate, LocalDate secondDate,
                                     LessonTemplate updatedLessonTemplate) {

        Set<Lesson> lessons = findLessonsByLessonTemplateAndDates(firstDate, secondDate, updatedLessonTemplate);
        deleteLessons(lessons);
//        List<Lesson> deletedLessons = findLessonsByDates(firstDate,secondDate);
//        updatedLessonTemplate.getLessons().removeAll(lessons);

    }

    public void deleteLessons(Set<Lesson>lessons) {

        List<User> users = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();

        if (lessons != null) {
            for (Lesson lesson : lessons) {
                if (lesson.getBookings() != null) {
                    for (Booking booking : lesson.getBookings()) {

                        User user = booking.getUser();
                        users.add(user);

                        String lessonName = booking.getLesson().getName();
                        String lessonDate = booking.getLesson().getStartDate().toString();

                        Entry entry = new Entry(lessonName, lessonDate);
                        entries.add(entry);
                    }
                }
               deleteLesson(lesson);
            }

        }
        for (User user : users) {
            mailService.sendLessonTemplateCancellationEmail(user, entries);
        }
    }

    public void deleteLessonsByLessonTemplate(LessonTemplate lessonTemplate) {

        Set<Lesson> lessons = lessonTemplate.getLessons();
        deleteLessons(lessons);
    }

    public void deleteLesson(Lesson lesson) {
        lessonRepository.delete(lesson);
        lessonRepository.flush();
//        entityManager.clear();
    }
}
