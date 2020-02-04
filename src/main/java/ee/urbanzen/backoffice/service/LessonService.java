package ee.urbanzen.backoffice.service;

import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing lessons.
 */


@Service
@Transactional
public class LessonService {
    private static final String ENTITY_NAME="lesson";

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository=lessonRepository;
    }

    public Lesson save(Lesson lesson) {return lessonRepository.save(lesson);}

    public List<Lesson> saveAll(List <Lesson> lessons) {return lessonRepository.saveAll(lessons);}

    public void flush() {lessonRepository.flush();}

    public List<Lesson> findAll() {return lessonRepository.findAll();}

    public Optional<Lesson> findById(Long id) {return lessonRepository.findById(id);}

    public List<Lesson> getLessonsByDates(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        Instant firstDayOfWeekInstant = firstDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant lastDayOfWeekInstant = lastDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return lessonRepository.findAllByDates(firstDayOfWeekInstant, lastDayOfWeekInstant);
    }

    public static List<LocalDate> getDatesBetween(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        return firstDayOfWeek.datesUntil(lastDayOfWeek.plusDays(1))
            .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }

}
