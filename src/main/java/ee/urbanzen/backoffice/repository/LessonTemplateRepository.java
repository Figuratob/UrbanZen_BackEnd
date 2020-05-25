package ee.urbanzen.backoffice.repository;
import ee.urbanzen.backoffice.domain.LessonTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the LessonTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LessonTemplateRepository extends JpaRepository<LessonTemplate, Long> {


    @Query("select lt from LessonTemplate lt " +
        "where " +
        "lt.id not in (select lt1.id from LessonTemplate lt1 " +
        "where (lt1.repeatStartDate > :timetableEndDate) or (lt1.repeatUntilDate < :timetableStartDate)) ")
    List<LessonTemplate> findAllByDates(@Param("timetableStartDate") LocalDate timetableStartDate,
                                        @Param("timetableEndDate") LocalDate timetableEndDate);

    @Query("select lt from LessonTemplate lt " +
        "where " +
        "lt.id not in (select lt1.id from LessonTemplate lt1 " +
        "where (lt1.repeatStartDate > :timetableEndDate) or (lt1.repeatUntilDate < :timetableStartDate)) " +
        "and lt.lessons is empty ")
    List<LessonTemplate> findAllByDatesWithoutLessons(@Param("timetableStartDate") LocalDate timetableStartDate,
                                                      @Param("timetableEndDate") LocalDate timetableEndDate);

    @Query("select lessonTemplate from LessonTemplate lessonTemplate where lessonTemplate.id = :id")
    List<LessonTemplate> findAllById(@Param("id")Long id);




}
