package ee.urbanzen.backoffice.service;

import ee.urbanzen.backoffice.domain.Booking;
import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.User;
import ee.urbanzen.backoffice.repository.BookingRepository;
import ee.urbanzen.backoffice.repository.LessonRepository;
import ee.urbanzen.backoffice.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing bookings.
 */

@Service
@Transactional
public class BookingService {

    private static final String ENTITY_NAME = "booking";

    private final BookingRepository bookingRepository;
    private final LessonRepository lessonRepository;

    public BookingService(BookingRepository bookingRepository, LessonRepository lessonRepository) {
        this.bookingRepository = bookingRepository;
        this.lessonRepository = lessonRepository;

    }

    public Booking createAndSaveBookingByLessonAndUser(Long lessonId, User user) {

        Lesson lesson = lessonRepository
            .findById(lessonId)
            .orElseThrow(() -> new BadRequestAlertException("Lesson not found", ENTITY_NAME, "lessonnotfound"));

        Booking booking = createBooking(user, lesson);
        return bookingRepository.save(booking);

    }

    public Booking saveCancelDateIntoBooking (Long id) {

        Booking booking = bookingRepository
            .findById(id)
            .orElseThrow(()-> new BadRequestAlertException("Booking not found", ENTITY_NAME, "bookingnotfound"));

        booking.setCancelDate(Instant.now());
        return booking;
    }

    private Booking createBooking(User user, Lesson lesson) {
        Booking booking = new Booking();
        booking.setLesson(lesson);
        booking.setUser(user);
        booking.setReservationDate(Instant.now());
        return booking;
    }

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> findAllByUserId(Long id) {
        return bookingRepository.findAllByUserId(id);
    }

    public List<Booking> findAllByUserIdWithoutCancelDate(Long id) {
        return bookingRepository.findAllByUserIdWithoutCancelDate(id);
    }

    public List<Booking> findAllByUserIdWithoutCancelDateAndLessonStartTimeIsAfterNow (Long id, Instant now){
        return bookingRepository.findAllByUserIdWithoutCancelDateAndLessonStartTimeIsAfterNow(id, now);
    }
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> findAllByLessonId(Long lessonId) {
        return bookingRepository.findAllByLessonId(lessonId);
    }

    public List<Booking> findAllByLessonIdWithoutCancelDate(Long lessonId) {
        return bookingRepository.findAllByLessonIdWithoutCancelDate(lessonId);
    }

    public List<Booking> findAllByLessonIdWithCancelDate(Long lessonId) {
        return bookingRepository.findAllByLessonIdWithCancelDate(lessonId);
    }

    public List<Booking> findBookingByLessonIdAndUserId (Long lessonId, Long userId) {
        return bookingRepository.findBookingsByLessonIdAndUserId(lessonId, userId);
    }


    public Booking findBookingByLessonIdUserIdAndWithoutCancelDate(Long lessonId, Long userId) {
        return bookingRepository.findBookingByLessonIdUserIdAndWithoutCancelDate(lessonId, userId);
    }
}
