package ee.urbanzen.backoffice.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.urbanzen.backoffice.domain.Booking;
import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.User;
import ee.urbanzen.backoffice.repository.LessonRepository;
import ee.urbanzen.backoffice.security.AuthoritiesConstants;
import ee.urbanzen.backoffice.security.SecurityUtils;
import ee.urbanzen.backoffice.service.BookingService;
import ee.urbanzen.backoffice.service.UserService;
import ee.urbanzen.backoffice.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link ee.urbanzen.backoffice.domain.Booking}.
 */
@RestController
@RequestMapping("/api")
public class BookingResource {

    private final Logger log = LoggerFactory.getLogger(BookingResource.class);

    private static final String ENTITY_NAME = "booking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;

    private final BookingService bookingService;
    private final LessonRepository lessonRepository;

    public BookingResource(UserService userService, BookingService bookingService, LessonRepository lessonRepository) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.lessonRepository = lessonRepository;
    }

    /**
     * {@code POST  /bookings} : Create a new booking.
     *
     * @param booking the booking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new booking, or with status {@code 400 (Bad Request)} if the booking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bookings")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) throws URISyntaxException {
        log.debug("REST request to save Booking : {}", booking);
        if (booking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            if (booking.getUser() == null) {
                throw new BadRequestAlertException("User is empty", ENTITY_NAME, "userempty");
            }
            if (userService.isUserAdmin(booking.getUser())) {
                throw new BadRequestAlertException("Admin cannot be a visitor", ENTITY_NAME, "adminbookedlesson");
            }
            Booking result = bookingService.save(booking);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, booking.getId().toString()))
                .body(result);
        }
        User user = userService
            .getUserWithAuthorities()
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));
        booking.setUser(user);
        Booking result = bookingService.save(booking);
        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/bookings/new")
    public ResponseEntity<Booking> createBooking(@RequestBody Long lessonId) throws URISyntaxException {
        log.debug("here the lesson id is " + lessonId);
        if (lessonId == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        User user = userService
            .getUserWithAuthorities()
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));

        Long userId = user.getId();
//        Booking booking = (bookingService.findBookingByLessonIdAndUserId(lessonId, userId));

        Booking booking = (bookingService.findBookingByLessonIdUserIdAndWithoutCancelDate(lessonId, userId));

//        if ((booking != null) && (booking.getCancelDate() == null)) {

         if (booking != null) {
            throw new BadRequestAlertException("Booking for this lesson is already exists", ENTITY_NAME, "bookingforthesamelesson");
        }

        Lesson lesson = lessonRepository
            .findById(lessonId)
            .orElseThrow(() -> new BadRequestAlertException("Lesson not found", ENTITY_NAME, "lessonnotfound"));

        Integer lessonAvailableSpaces = lesson.getAvailableSpaces();
        if ((bookingService.findAllByLessonIdWithoutCancelDate(lessonId).size()) >= lessonAvailableSpaces) {
            throw new BadRequestAlertException("There are no more available spaces for this lesson", ENTITY_NAME, "nomoreavailablespaces");
        }

        Booking result = bookingService.createAndSaveBookingByLessonAndUser(lessonId, user);
        return ResponseEntity.created(new URI("/api/bookings/new" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bookings} : Updates an existing booking.
     *
     * @param booking the booking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated booking,
     * or with status {@code 400 (Bad Request)} if the booking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the booking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bookings")
    public ResponseEntity<Booking> updateBooking(@Valid @RequestBody Booking booking) throws URISyntaxException {
        log.debug("REST request to update Booking : {}", booking);
        if (booking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Booking result = bookingService.save(booking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, booking.getId().toString()))
            .body(result);
    }

    // for admin:
    @PutMapping("/bookings/new")
    public ResponseEntity<Booking> updateBooking(@RequestBody String data) throws URISyntaxException, IOException {
        log.debug("here the lesson id is " + data);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Long> jsonMap = objectMapper.readValue(data,
            new TypeReference<Map<String, Long>>() {
            });
        Long lessonId = jsonMap.get("lessonId");
        if (lessonId == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        User user = userService
            .getUserWithAuthorities()
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));
        Booking result = bookingService.createAndSaveBookingByLessonAndUser(lessonId, user);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) throws URISyntaxException {
        log.debug("here the booking id is " + bookingId);

        User user = userService
            .getUserWithAuthorities()
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));

        Booking booking = bookingService
            .findById(bookingId)
            .orElseThrow(() -> new BadRequestAlertException("Booking not found", ENTITY_NAME, "bookingnotfound"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new BadRequestAlertException("You are not authorized to make changes to this booking", ENTITY_NAME, "notAuthorized");
        }

        Booking result = bookingService.saveCancelDateIntoBooking(bookingId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bookings} : get all the bookings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookings in body.
     */
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        log.debug("REST request to get all Bookings for admin or for user by userId");
        User user = userService
            .getUserWithAuthorities()
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));
        if (userService.isUserAdmin(user)) {
            return bookingService.findAll();
        }
        return bookingService.findAllByUserIdWithoutCancelDate(user.getId());
    }

    @GetMapping("/userBookings")
    public List<Booking> getUserBookings() {
        log.debug("REST request to get all Bookings for admin or for user by userId");
        User user = userService
            .getUserWithAuthorities()
            .orElseThrow(() -> new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound"));
        List<Booking> bookings = bookingService.findAllByUserId(user.getId());
        return bookings;
    }

    /**
     * {@code GET  /bookings/:id} : get the "id" booking.
     *
     * @param id the id of the booking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the booking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        log.debug("REST request to get Booking : {}", id);
        Optional<Booking> booking = bookingService.findById(id);
        return ResponseUtil.wrapOrNotFound(booking);
    }

    /**
     * {@code DELETE  /bookings/:id} : delete the "id" booking.
     *
     * @param id the id of the booking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        log.debug("REST request to delete Booking : {}", id);
        bookingService.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
