package ee.urbanzen.backoffice.service.dto;
import ee.urbanzen.backoffice.domain.Lesson;
import java.time.Instant;
import java.util.List;

public class TimetableDTO {

    private Instant timetableDay;
    private List<Lesson> lessons;

    public TimetableDTO()
    {
        // Empty constructor needed for Jackson.
    }

    public Instant getTimetableDay() {
        return timetableDay;
    }

    public void setTimetableDay(Instant timetableDay) {
        this.timetableDay = timetableDay;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
