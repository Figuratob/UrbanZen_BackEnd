package ee.urbanzen.backoffice.domain;

public class Entry {
    private String lessonName;
    private String lessonDate;

    public Entry(String lessonName, String lessonDate) {
        this.lessonName = lessonName;
        this.lessonDate = lessonDate;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
}
