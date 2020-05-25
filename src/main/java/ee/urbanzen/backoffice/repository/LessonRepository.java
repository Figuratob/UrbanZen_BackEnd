package ee.urbanzen.backoffice.repository;

import ee.urbanzen.backoffice.domain.Lesson;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the Lesson entity.
 */

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select l from Lesson l " +
        "where l.startDate " +
        "between :firstDayOfWeek and :lastDayOfWeek")
    List<Lesson> findAllByDates(@Param("firstDayOfWeek") Instant firstDayOfWeek,
                                @Param("lastDayOfWeek") Instant lastDayOfWeek);

    @Query("delete from Lesson lesson where lesson.lessonTemplate.id = :lessonTemplateId")
    void deleteAllByLessonTemplateId(@Param("lessonTemplateId")Long lessonTemplateId);
}
