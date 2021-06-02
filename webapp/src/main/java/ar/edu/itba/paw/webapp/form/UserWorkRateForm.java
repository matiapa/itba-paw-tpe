package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserWorkRateForm {

    @NotNull
    private String courseId;

    @NotNull
    @Size(min = 10, max = 500)
    private String comment;

    @NotNull
    @Min(1)
    @Max(5)
    private int behaviour;

    @NotNull
    @Min(1)
    @Max(5)
    private int skill;

    public String getCourseId() {
        return courseId;
    }

    public String getComment() {
        return comment;
    }

    public int getBehaviour() {
        return behaviour;
    }

    public int getSkill() {
        return skill;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setBehaviour(int behaviour) {
        this.behaviour = behaviour;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }
}