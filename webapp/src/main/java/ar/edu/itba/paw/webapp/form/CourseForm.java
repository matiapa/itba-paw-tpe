package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;

public class CourseForm {
    @NotNull
    private String course;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
