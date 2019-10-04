package ee.urbanzen.backoffice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import ee.urbanzen.backoffice.domain.enumeration.DayOfWeek;

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

    @NotNull
    @Column(name = "repeat_start_date", nullable = false)
    private LocalDate repeatStartDate;

    @Column(name = "repeat_until_date")
    private LocalDate repeatUntilDate;

    @OneToMany(mappedBy = "lessonTemplate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lessonTemplates")
    private Teacher teacher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LessonTemplate dayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public LessonTemplate startHour(Integer startHour) {
        this.startHour = startHour;
        return this;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public LessonTemplate startMinute(Integer startMinute) {
        this.startMinute = startMinute;
        return this;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public LessonTemplate endHour(Integer endHour) {
        this.endHour = endHour;
        return this;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public LessonTemplate endMinute(Integer endMinute) {
        this.endMinute = endMinute;
        return this;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
    }

    public String getName() {
        return name;
    }

    public LessonTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public LessonTemplate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public LessonTemplate street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public LessonTemplate city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getAvailableSpaces() {
        return availableSpaces;
    }

    public LessonTemplate availableSpaces(Integer availableSpaces) {
        this.availableSpaces = availableSpaces;
        return this;
    }

    public void setAvailableSpaces(Integer availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    public LocalDate getRepeatStartDate() {
        return repeatStartDate;
    }

    public LessonTemplate repeatStartDate(LocalDate repeatStartDate) {
        this.repeatStartDate = repeatStartDate;
        return this;
    }

    public void setRepeatStartDate(LocalDate repeatStartDate) {
        this.repeatStartDate = repeatStartDate;
    }

    public LocalDate getRepeatUntilDate() {
        return repeatUntilDate;
    }

    public LessonTemplate repeatUntilDate(LocalDate repeatUntilDate) {
        this.repeatUntilDate = repeatUntilDate;
        return this;
    }

    public void setRepeatUntilDate(LocalDate repeatUntilDate) {
        this.repeatUntilDate = repeatUntilDate;
    }

    public Set<Lesson> getLessons() {
        return lessons;
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

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public LessonTemplate teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
