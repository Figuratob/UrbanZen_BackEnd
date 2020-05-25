package ee.urbanzen.backoffice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "name_eng", nullable = false)
    private String nameEng;

    @NotNull
    @Column(name = "name_rus", nullable = false)
    private String nameRus;

    @NotNull
    @Size(max = 1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @NotNull
    @Size(max = 1000)
    @Column(name = "description_eng", length = 1000, nullable = false)
    private String descriptionEng;

    @NotNull
    @Size(max = 1000)
    @Column(name = "description_rus", length = 1000, nullable = false)
    private String descriptionRus;

    @NotNull
    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "street_eng")
    private String streetEng;

    @NotNull
    @Column(name = "street_rus")
    private String streetRus;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "city_eng")
    private String cityEng;

    @NotNull
    @Column(name = "city_rus")
    private String cityRus;

    @NotNull
    @Min(value = 0)
    @Column(name = "available_spaces", nullable = false)
    private Integer availableSpaces;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lessons")
    private Teacher teacher;

    @ManyToOne    @JsonIgnoreProperties("lessons")
    private LessonTemplate lessonTemplate;

    @OneToMany(mappedBy = "lesson", fetch =  FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Lesson name(String name) {
        this.name = name;
        return this;
    }
    public Lesson nameEng(String nameEng) {
        this.nameEng = nameEng;
        return this;
    }
    public Lesson nameRus(String nameRus) {
        this.nameRus = nameRus;
        return this;
    }
    public Lesson startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }
    public Lesson endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }
    public Lesson description(String description) {
        this.description = description;
        return this;
    }
    public Lesson descriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
        return this;
    }
    public Lesson descriptionRus(String descriptionRus) {
        this.descriptionRus = descriptionRus;
        return this;
    }
    public Lesson street(String street) {
        this.street = street;
        return this;
    }
    public Lesson streetEng(String streetEng) {
        this.streetEng = streetEng;
        return this;
    }
    public Lesson streetRus(String streetRus) {
        this.streetRus = streetRus;
        return this;
    }
    public Lesson city(String city) {
        this.city = city;
        return this;
    }
    public Lesson cityEng(String cityEng) {
        this.cityEng = cityEng;
        return this;
    }
    public Lesson cityRus(String cityRus) {
        this.cityRus = cityRus;
        return this;
    }
    public Lesson availableSpaces(Integer availableSpaces) {
        this.availableSpaces = availableSpaces;
        return this;
    }
    public Lesson teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }
    public Lesson lessonTemplate(LessonTemplate lessonTemplate) {
        this.lessonTemplate = lessonTemplate;
        return this;
    }
    public Lesson bookings(Set<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameRus() {
        return nameRus;
    }

    public void setNameRus(String nameRus) {
        this.nameRus = nameRus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionRus() {
        return descriptionRus;
    }

    public void setDescriptionRus(String descriptionRus) {
        this.descriptionRus = descriptionRus;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetEng() {
        return streetEng;
    }

    public void setStreetEng(String streetEng) {
        this.streetEng = streetEng;
    }

    public String getStreetRus() {
        return streetRus;
    }

    public void setStreetRus(String streetRus) {
        this.streetRus = streetRus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEng() {
        return cityEng;
    }

    public void setCityEng(String cityEng) {
        this.cityEng = cityEng;
    }

    public String getCityRus() {
        return cityRus;
    }

    public void setCityRus(String cityRus) {
        this.cityRus = cityRus;
    }

    public Integer getAvailableSpaces() {
        return availableSpaces;
    }

    public void setAvailableSpaces(Integer availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    public Set<Booking> getBookings() {return bookings;}

    public void setBookings(Set<Booking> bookings) {this.bookings = bookings;}

    public Teacher getTeacher() {return teacher;}

    public void setTeacher(Teacher teacher) {this.teacher = teacher;}

    @JsonIgnoreProperties("lessons")
    public LessonTemplate getLessonTemplate() {
        return lessonTemplate;
    }

    @JsonIgnoreProperties("lessons")
    public void setLessonTemplate(LessonTemplate lessonTemplate) {
        this.lessonTemplate = lessonTemplate;
    }

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
