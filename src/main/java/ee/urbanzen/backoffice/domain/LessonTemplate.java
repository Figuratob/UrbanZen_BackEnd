package ee.urbanzen.backoffice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A LessonTemplate.
 */
@Entity
@Table(name = "lesson_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LessonTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @NotNull
    @Min(value = 0)
    @Max(value = 23)
    @Column(name = "start_hour", nullable = false)
    private Integer startHour;

    @NotNull
    @Min(value = 0)
    @Max(value = 59)
    @Column(name = "start_minute", nullable = false)
    private Integer startMinute;

    @NotNull
    @Min(value = 0)
    @Max(value = 23)
    @Column(name = "end_hour", nullable = false)
    private Integer endHour;

    @NotNull
    @Min(value = 0)
    @Max(value = 59)
    @Column(name = "end_minute", nullable = false)
    private Integer endMinute;

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

    @NotNull
    @Column(name = "repeat_start_date", nullable = false)
    private LocalDate repeatStartDate;

    @Column(name = "repeat_until_date")
    private LocalDate repeatUntilDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lessonTemplates")
    private Teacher teacher;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lessonTemplate")
    private Set<Lesson> lessons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public boolean isActiveOnGivenDate (LocalDate date) {

        if (date.isBefore(this.getRepeatStartDate()) && date.isAfter(this.getRepeatUntilDate())) {
           return false;
        }
        return true;
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public LessonTemplate dayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }
    public LessonTemplate startHour(Integer startHour) {
        this.startHour = startHour;
        return this;
    }
    public LessonTemplate startMinute(Integer startMinute) {
        this.startMinute = startMinute;
        return this;
    }
    public LessonTemplate endHour(Integer endHour) {
        this.endHour = endHour;
        return this;
    }
    public LessonTemplate endMinute(Integer endMinute) {
        this.endMinute = endMinute;
        return this;
    }
    public LessonTemplate name(String name) {
        this.name = name;
        return this;
    }
    public LessonTemplate nameEng(String nameEng) {
        this.nameEng = nameEng;
        return this;
    }
    public LessonTemplate nameRus(String nameRus) {
        this.nameRus = nameRus;
        return this;
    }
    public LessonTemplate description(String description) {
        this.description = description;
        return this;
    }
    public LessonTemplate descriptionEng (String descriptionEng) {
        this.descriptionEng = descriptionEng;
        return this;
    }
    public LessonTemplate descriptionRus (String descriptionRus) {
        this.descriptionRus = descriptionRus;
        return this;
    }
    public LessonTemplate street(String street) {
        this.street = street;
        return this;
    }
    public LessonTemplate streetEng(String streetEng) {
        this.streetEng = streetEng;
        return this;
    }
    public LessonTemplate streetRus(String streetRus) {
        this.streetRus = streetRus;
        return this;
    }
    public LessonTemplate city(String city) {
        this.city = city;
        return this;
    }
    public LessonTemplate cityEng(String cityEng) {
        this.cityEng = cityEng;
        return this;
    }
    public LessonTemplate cityRus(String cityRus) {
        this.cityRus = cityRus;
        return this;
    }
    public LessonTemplate availableSpaces(Integer availableSpaces) {
        this.availableSpaces = availableSpaces;
        return this;
    }
    public LessonTemplate repeatStartDate(LocalDate repeatStartDate) {
        this.repeatStartDate = repeatStartDate;
        return this;
    }
    public LessonTemplate repeatUntilDate(LocalDate repeatUntilDate) {
        this.repeatUntilDate = repeatUntilDate;
        return this;
    }
    public LessonTemplate lessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }
    public LessonTemplate addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setLessonTemplate(this);
        return this;
    }
    public LessonTemplate removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setLessonTemplate(null);
        return this;
    }
    public LessonTemplate teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityRus() {
        return cityRus;
    }

    public void setCityRus(String cityRus) {
        this.cityRus = cityRus;
    }

    public String getCityEng() {
        return cityEng;
    }

    public void setCityEng(String cityEng) {
        this.cityEng = cityEng;
    }

    public Integer getAvailableSpaces() {
        return availableSpaces;
    }

    public void setAvailableSpaces(Integer availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    public LocalDate getRepeatStartDate() {
        return repeatStartDate;
    }

    public void setRepeatStartDate(LocalDate repeatStartDate) {
        this.repeatStartDate = repeatStartDate;
    }

    public LocalDate getRepeatUntilDate() {
        return repeatUntilDate;
    }

    public void setRepeatUntilDate(LocalDate repeatUntilDate) {
        this.repeatUntilDate = repeatUntilDate;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }

    public String getDescriptionRus() {
        return descriptionRus;
    }

    public void setDescriptionRus(String descriptionRus) {
        this.descriptionRus = descriptionRus;
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

    public Set<Lesson> getLessons() { return lessons; }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LessonTemplate)) {
            return false;
        }
        return id != null && id.equals(((LessonTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LessonTemplate{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", startHour=" + getStartHour() +
            ", startMinute=" + getStartMinute() +
            ", endHour=" + getEndHour() +
            ", endMinute=" + getEndMinute() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", street='" + getStreet() + "'" +
            ", city='" + getCity() + "'" +
            ", availableSpaces=" + getAvailableSpaces() +
            ", repeatStartDate='" + getRepeatStartDate() + "'" +
            ", repeatUntilDate='" + getRepeatUntilDate() + "'" +
            "}";
    }
}
