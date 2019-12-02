package ee.urbanzen.backoffice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Lesson.
 */
@Entity
@Table(name = "lesson")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lesson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(max = 1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @NotNull
    @Min(value = 0)
    @Column(name = "available_spaces", nullable = false)
    private Integer availableSpaces;

    @OneToMany(mappedBy = "lesson", fetch =  FetchType.EAGER)
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Booking> bookings = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lessons")
    private Teacher teacher;

    @ManyToOne
    @JsonIgnoreProperties("lessons")
    private LessonTemplate lessonTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Lesson startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Lesson endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public Lesson name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Lesson description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public Lesson street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public Lesson city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAvailableSpaces() {
        return availableSpaces;
    }

    public Lesson availableSpaces(Integer availableSpaces) {
        this.availableSpaces = availableSpaces;
        return this;
    }

    public void setAvailableSpaces(Integer availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    @JsonProperty("remainSpaces")
    public long getRemainSpaces() {

        return Math.max(
            this.availableSpaces - this.getBookings()
                    .stream()
                    .filter(booking -> booking.getCancelDate() == null)
                    .count(),
            0);

//        int remainSpaces = 0;
//        for (Booking booking : this.getBookings()) {
//            if (booking.getCancelDate() != null) {
//                remainSpaces++;
//            }
//        }
//        return remainSpaces;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public Lesson bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public Lesson addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setLesson(this);
        return this;
    }

    public Lesson removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setLesson(null);
        return this;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Lesson teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @JsonIgnoreProperties("lessons")
    public LessonTemplate getLessonTemplate() {
        return lessonTemplate;
    }

    public Lesson lessonTemplate(LessonTemplate lessonTemplate) {
        this.lessonTemplate = lessonTemplate;
        return this;
    }

    @JsonIgnoreProperties("lessons")
    public void setLessonTemplate(LessonTemplate lessonTemplate) {
        this.lessonTemplate = lessonTemplate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lesson)) {
            return false;
        }
        return id != null && id.equals(((Lesson) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", street='" + getStreet() + "'" +
            ", city='" + getCity() + "'" +
            ", availableSpaces=" + getAvailableSpaces() +
            "}";
    }
}
