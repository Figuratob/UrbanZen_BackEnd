package ee.urbanzen.backoffice.service;

import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.repository.LessonTemplateRepository;
import ee.urbanzen.backoffice.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing lesson templates.
 */

@Service
@Transactional
public class LessonTemplateService {

    private static final String ENTITY_NAME = "lessonTemplate";

    private final LessonTemplateRepository lessonTemplateRepository;

    public final LessonService lessonService;

    public LessonTemplateService(LessonTemplateRepository lessonTemplateRepository,
                                 LessonService lessonService) {
        this.lessonTemplateRepository = lessonTemplateRepository;
        this.lessonService = lessonService;
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

    public LessonTemplate save(LessonTemplate lessonTemplate) {
        return lessonTemplateRepository.saveAndFlush(lessonTemplate);
    }

    public void update(LessonTemplate updatedLessonTemplate) {
        save(updatedLessonTemplate);
//        lessonService.updateLessonsOfLessonTemplate(updatedLessonTemplate);
        lessonService.updateLessonsOfLessonTemplateByDates(updatedLessonTemplate.getRepeatStartDate(), updatedLessonTemplate.getRepeatUntilDate(),
            updatedLessonTemplate);
    }

    public LessonTemplate updateLessonTemplate(LessonTemplate updatedLessonTemplate) {

        if (updatedLessonTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        LessonTemplate oldLessonTemplate = lessonTemplateRepository
            .findById(updatedLessonTemplate.getId())
            .orElseThrow(() -> new BadRequestAlertException("LessonTemplate not found", ENTITY_NAME, "lessonTemplatenotfound"));

        LocalDate updatedLessonTemplateStartDate = updatedLessonTemplate.getRepeatStartDate();
        LocalDate oldLessonTemplateStartDate = oldLessonTemplate.getRepeatStartDate();
        LocalDate updatedLessonTemplateUntilDate = updatedLessonTemplate.getRepeatUntilDate();
        LocalDate oldLessonTemplateUntilDate = oldLessonTemplate.getRepeatUntilDate();

        List<LessonTemplate> updatedLessonTemplates = new ArrayList<>();
        updatedLessonTemplates.add(updatedLessonTemplate);

        // general case 1
        if (updatedLessonTemplateStartDate.isEqual(oldLessonTemplateStartDate) && (updatedLessonTemplateUntilDate.isEqual(
            oldLessonTemplateUntilDate))) {
            update(updatedLessonTemplate);

        }
        // adding lessons 3a || 3b
        if (updatedLessonTemplateUntilDate.isBefore(oldLessonTemplateStartDate) ||
            updatedLessonTemplateStartDate.isAfter(oldLessonTemplateUntilDate)) {
            lessonService.generateLessonsFromTemplatesByDates(updatedLessonTemplateStartDate, updatedLessonTemplateUntilDate,
                updatedLessonTemplates);
            lessonService.deleteLessonsByDates(oldLessonTemplateStartDate, oldLessonTemplateUntilDate, oldLessonTemplate);
            return updatedLessonTemplate;
        }
        // adding lessons 2a
        if (updatedLessonTemplateStartDate.isBefore(oldLessonTemplateStartDate)) {
            List<Lesson> addedLessons = lessonService.generateLessonsFromTemplatesByDates(updatedLessonTemplateStartDate, oldLessonTemplateStartDate,
                updatedLessonTemplates);
            System.out.println("addedLessons: " + addedLessons.size());
        }
        // adding lessons 2b
        if (updatedLessonTemplateUntilDate.isAfter(oldLessonTemplateUntilDate)) {
            lessonService.generateLessonsFromTemplatesByDates(oldLessonTemplateUntilDate, updatedLessonTemplateUntilDate,
                updatedLessonTemplates);
        }
        // delete (2a)
        if (updatedLessonTemplateUntilDate.isBefore(oldLessonTemplateUntilDate)) {
            lessonService.deleteLessonsByDates(updatedLessonTemplateUntilDate, oldLessonTemplateUntilDate, updatedLessonTemplate);
        }
        // delete (2b)
        if (oldLessonTemplateStartDate.isBefore(updatedLessonTemplateStartDate)) {
            lessonService.deleteLessonsByDates(oldLessonTemplateStartDate, updatedLessonTemplateStartDate, updatedLessonTemplate);
        }
        // update 2a
        if (updatedLessonTemplateStartDate.isBefore(oldLessonTemplateStartDate) &&
            updatedLessonTemplateUntilDate.isBefore(oldLessonTemplateUntilDate)) {
            lessonService.updateLessonsOfLessonTemplateByDates(oldLessonTemplateStartDate, updatedLessonTemplateUntilDate,
                updatedLessonTemplate);
        }
        // update 2b
        if (updatedLessonTemplateStartDate.isAfter(oldLessonTemplateStartDate) &&
            updatedLessonTemplateUntilDate.isAfter(oldLessonTemplateUntilDate)) {
            lessonService.updateLessonsOfLessonTemplateByDates(updatedLessonTemplateStartDate, oldLessonTemplateUntilDate,
                updatedLessonTemplate);
        }
        return updatedLessonTemplate;
           }

    public void deleteById(Long id) {

        LessonTemplate lessonTemplate = lessonTemplateRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("LessonTemplate not found", ENTITY_NAME, "lessonTemplatenotfound"));

        lessonService.deleteLessonsByLessonTemplate(lessonTemplate);
        lessonTemplateRepository.deleteById(id);
    }
}
