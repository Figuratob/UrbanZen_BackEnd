package ee.urbanzen.backoffice.repository;
import ee.urbanzen.backoffice.domain.Booking;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data  repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select booking from Booking booking where booking.user.login = ?#{principal.username}")
    List<Booking> findByUserIsCurrentUser();

    @Query("select booking from Booking booking where booking.user.id = :id")
    List<Booking> findAllByUserId(@Param("id")Long id);

    @Query("select booking from Booking booking where booking.user.id = :id and booking.cancelDate is null")
    List<Booking> findAllByUserIdWithoutCancelDate(@Param ("id")Long id);

    @Query("select booking from Booking booking where booking.user.id = :id and booking.cancelDate is null " +
        "and booking.lesson.startDate > :now")
    List<Booking> findAllByUserIdWithoutCancelDateAndLessonStartTimeIsAfterNow(@Param ("id") Long id,
                                                                                @Param ("now") Instant now);

    @Query("select booking from Booking booking where booking.lesson.id = :lessonId")
    List<Booking> findAllByLessonId(@Param("lessonId")Long lessonId);

    @Query("select booking from Booking booking where (booking.lesson.id = :lessonId and booking.cancelDate = null)")
    List<Booking> findAllByLessonIdWithoutCancelDate(@Param("lessonId")Long lessonId);

    @Query("select booking from Booking booking where (booking.lesson.id = :lessonId and booking.cancelDate is not null)")
    List<Booking> findAllByLessonIdWithCancelDate(@Param("lessonId")Long lessonId);

    @Query("select booking from Booking booking where ((booking.lesson.id = :lessonId) and (booking.user.id = :userId))")
    List<Booking> findBookingsByLessonIdAndUserId(@Param("lessonId")Long lessonId,
                                           @Param("userId")Long userId);

    @Query("select booking from Booking booking where (booking.lesson.id = :lessonId and booking.user.id = :userId and " +
        "booking.cancelDate is null)")
    Booking findBookingByLessonIdUserIdAndWithoutCancelDate(@Param("lessonId")Long lessonId,
                                                            @Param("userId")Long userId);
    @Query("delete from Booking booking where booking.lesson.id = :lessonId")
    void deleteAllByLessonId(@Param("lessonId")Long lessonId);
}
