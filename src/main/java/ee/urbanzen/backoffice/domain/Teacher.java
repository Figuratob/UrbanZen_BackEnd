package ee.urbanzen.backoffice.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "first_name_eng", nullable = false)
    private String firstNameEng;

    @NotNull
    @Column(name = "first_name_rus", nullable = false)
    private String firstNameRus;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "last_name_eng", nullable = false)
    private String lastNameEng;

    @NotNull
    @Column(name = "last_name_rus", nullable = false)
    private String lastNameRus;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "about")
    private String about;

    @Column(name = "about_eng")
    private String aboutEng;

    @Column(name = "about_rus")
    private String aboutRus;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LessonTemplate> lessonTemplates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Teacher firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    public Teacher firstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
        return this;
    }
    public Teacher firstNameRus(String firstNameRus) {
        this.firstNameRus = firstNameRus;
        return this;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Teacher lastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
        return this;
    }
    public Teacher lastNameRus(String lastNameRus) {
        this.lastNameRus = lastNameRus;
        return this;
    }
    public Teacher lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAboutRus() {
        return aboutRus;
    }

    public void setAboutRus(String aboutRus) {
        this.aboutRus = aboutRus;
    }

    public String getAboutEng() {
        return aboutEng;
    }

    public void setAboutEng(String aboutEng) {
        this.aboutEng = aboutEng;
    }

    public String getLastNameRus() {
        return lastNameRus;
    }

    public void setLastNameRus(String lastNameRus) {
        this.lastNameRus = lastNameRus;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public String getFirstNameRus() {
        return firstNameRus;
    }

    public void setFirstNameRus(String firstNameRus) {
        this.firstNameRus = firstNameRus;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public String getEmail() {
        return email;
    }

    public Teacher email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Teacher phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Teacher photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Teacher photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getAbout() {
        return about;
    }

    public Teacher about(String about) {
        this.about = about;
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public Teacher lessons(Set<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }

    public Teacher addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setTeacher(this);
        return this;
    }

    public Teacher removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setTeacher(null);
        return this;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<LessonTemplate> getLessonTemplates() {
        return lessonTemplates;
    }

    public Teacher lessonTemplates(Set<LessonTemplate> lessonTemplates) {
        this.lessonTemplates = lessonTemplates;
        return this;
    }

    public Teacher addLessonTemplate(LessonTemplate lessonTemplate) {
        this.lessonTemplates.add(lessonTemplate);
        lessonTemplate.setTeacher(this);
        return this;
    }

    public Teacher removeLessonTemplate(LessonTemplate lessonTemplate) {
        this.lessonTemplates.remove(lessonTemplate);
        lessonTemplate.setTeacher(null);
        return this;
    }

    public void setLessonTemplates(Set<LessonTemplate> lessonTemplates) {
        this.lessonTemplates = lessonTemplates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teacher)) {
            return false;
        }
        return id != null && id.equals(((Teacher) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", about='" + getAbout() + "'" +
            "}";
    }
}
