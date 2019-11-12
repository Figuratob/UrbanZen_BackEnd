package ee.urbanzen.backoffice.repository;

import ee.urbanzen.backoffice.domain.LessonTemplate;
import ee.urbanzen.backoffice.domain.enumeration.DayOfWeek;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
    public List<LessonTemplate> findAllByDates(@Param("timetableStartDate") LocalDate timetableStartDate,
                                               @Param("timetableEndDate") LocalDate timetableEndDate);

    @Query("select lt from LessonTemplate lt " +
        "where " +
        "lt.id not in (select lt1.id from LessonTemplate lt1 " +
        "where (lt1.repeatStartDate > :timetableEndDate) or (lt1.repeatUntilDate < :timetableStartDate)) " +
        "and lt.lessons is empty ")
    public List<LessonTemplate> findAllByDatesWithoutLessons(@Param("timetableStartDate") LocalDate timetableStartDate,
                                                             @Param("timetableEndDate") LocalDate timetableEndDate);


//    @Query("select distinct lt from LessonTemplate lt " +
//        "where " +
//        "lt.id in (select lt1.id from LessonTemplate lt1 " +
//        "where lt1.repeatStartDate <= :timetableStartDate and lt1.repeatUntilDate >= :timetableStartDate) " +
//        "or " +
//        "lt.id in (select lt2.id from LessonTemplate lt2 " +
//        "where lt2.repeatUntilDate >= :timetableEndDate and lt2.repeatStartDate < :timetableEndDate)" +
//        "or " +
//
//        "lt.id in (select lt3.id from LessonTemplate lt3 " +
//        "where lt3.repeatStartDate >= :timetableStartDate and lt3.repeatUntilDate <= :timetableEndDate)" +
//        "or " +
//        "lt.id in (select lt4.id from LessonTemplate lt4 " +
//        "where lt4.repeatStartDate < :timetableEndDate and lt4.repeatUntilDate is null)" )
//    public List<LessonTemplate> findAllByDates1(@Param("timetableStartDate") LocalDate timetableStartDate,
//                                               @Param("timetableEndDate") LocalDate timetableEndDate);
//
//
//    @Query("select distinct lt from LessonTemplate lt " +
//        "where " +
//        "lt.id in (select lt1.id from LessonTemplate lt1 " +
//        "where lt1.repeatStartDate <= :timetableStartDate and lt1.repeatStartDate <= :timetableEndDate and lt1.repeatUntilDate >= :timetableStartDate and lt1.repeatUntilDate <= :timetableEndDate) " +
//        "or " +
//        "lt.id in (select lt2.id from LessonTemplate lt2 " +
//        "where lt2.repeatStartDate >= :timetableStartDate and lt2.repeatStartDate <= :timetableEndDate and lt2.repeatUntilDate >= :timetableStartDate and lt2.repeatUntilDate >= :timetableEndDate)" +
//        "or " +
//        "lt.id in (select lt3.id from LessonTemplate lt3 " +
//        "where lt3.repeatStartDate <= :timetableStartDate and lt3.repeatStartDate <= :timetableEndDate and lt3.repeatUntilDate >= :timetableStartDate and lt3.repeatUntilDate >= :timetableEndDate)" +
//        "or " +
//        "lt.id in (select lt4.id from LessonTemplate lt4 " +
//        "where lt4.repeatStartDate >= :timetableStartDate and lt4.repeatStartDate <= :timetableEndDate and  lt4.repeatUntilDate >= :timetableStartDate and lt4.repeatUntilDate <= :timetableEndDate)" +
//        "or " +
//        "lt.id in (select lt5.id from LessonTemplate lt5 " +
//        "where lt5.repeatStartDate >= :timetableStartDate and lt5.repeatStartDate <= :timetableEndDate and  lt5.repeatUntilDate is null)" +
//        "or " +
//        "lt.id in (select lt6.id from LessonTemplate lt6 " +
//        "where lt6.repeatStartDate <= :timetableStartDate and lt6.repeatStartDate <= :timetableEndDate and  lt6.repeatUntilDate is null)")
//    public List<LessonTemplate> findAllByDates2(@Param("timetableStartDate") LocalDate timetableStartDate,
//                                               @Param("timetableEndDate") LocalDate timetableEndDate);

}
