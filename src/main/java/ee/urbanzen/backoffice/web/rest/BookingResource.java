package ee.urbanzen.backoffice.web.rest;

import ee.urbanzen.backoffice.domain.Authority;
import ee.urbanzen.backoffice.domain.Booking;
import ee.urbanzen.backoffice.domain.Lesson;
import ee.urbanzen.backoffice.domain.User;
import ee.urbanzen.backoffice.repository.BookingRepository;
import ee.urbanzen.backoffice.repository.LessonRepository;
import ee.urbanzen.backoffice.security.AuthoritiesConstants;
import ee.urbanzen.backoffice.security.SecurityUtils;
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
import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    private final BookingRepository bookingRepository;
    private final LessonRepository lessonRepository;
    private final UserService userService;

    public BookingResource(BookingRepository bookingRepository, LessonRepository lessonRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.lessonRepository = lessonRepository;
        this.userService = userService;
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

        Lesson lesson = lessonRepository
            .findById(lessonId)
            .orElseThrow(() -> new BadRequestAlertException("Lesson not found", ENTITY_NAME, "lessonnotfound"));

        Booking booking = new Booking();
        booking.setLesson(lesson);
        booking.setUser(user);
        booking.setReservationDate(Instant.now());
        Booking result = bookingRepository.save(booking);
        return ResponseEntity.created(new URI("/api/bookings/new" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
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
        if (booking.getId() != null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {

            if (booking.getUser() == null) {
                throw new BadRequestAlertException("User is empty", ENTITY_NAME, "userempty");
            }
            Optional<User> user = userService.getUserWithAuthoritiesByLogin(booking.getUser().getLogin());
            if (user.isPresent()) {
                Set<Authority> authorities = user.get().getAuthorities();
                boolean isAdmin = false;
                for (Authority authority : authorities) {
                    if (authority.getName().equals(AuthoritiesConstants.ADMIN)) {
                        isAdmin = true;
                        break;
                    }
                }
                if (isAdmin) {
                    throw new BadRequestAlertException("Admin cannot be a visitor", ENTITY_NAME, "adminbookedlesson");
                }
            }
            Booking result = bookingRepository.save(booking);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, booking.getId().toString()))
                .body(result);
        }
        Optional<User> isUser = userService.getUserWithAuthorities();
        if (isUser.isEmpty()) {
            throw new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound");
        }
        User user = isUser.get();
        booking.setUser(user);
        Booking result = bookingRepository.save(booking);
        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
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
        Booking result = bookingRepository.save(booking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, booking.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bookings} : get all the bookings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookings in body.
     */
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        log.debug("REST request to get all Bookings");
        return bookingRepository.findAll();
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
        Optional<Booking> booking = bookingRepository.findById(id);
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
        bookingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
