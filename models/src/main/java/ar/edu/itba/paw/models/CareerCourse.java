package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "career_course")
public class CareerCourse extends Course{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "career_code", nullable = false)
    private Career career;

    @Column(name = "semester", nullable = false)
    private int semester;

    CareerCourse(){ }

    public int getSemester() {
        return semester%2 +1;
    }

    public int getYear() {
        return (semester+1)/2;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

}