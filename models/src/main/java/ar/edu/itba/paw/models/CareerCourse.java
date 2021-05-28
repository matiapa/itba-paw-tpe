package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "career_course")
@IdClass(CareerCourse.CareerCourseId.class)
public class CareerCourse {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "career_code", nullable = false)
    private Career career;

    @Column(name = "semester", nullable = false)
    private int semester;

    @Id
    @Column(name = "course_id", insertable = false, updatable = false)
    private String courseId;

    @Id
    @Column(name = "career_code", insertable = false, updatable = false)
    private String careerCode;


    public CareerCourse(){ }


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


    public static class CareerCourseId implements Serializable {

        private String careerCode;
        private String courseId;

        public CareerCourseId(){}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CareerCourseId that = (CareerCourseId) o;
            return Objects.equals(careerCode, that.careerCode) && Objects.equals(courseId, that.courseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(careerCode, courseId);
        }

    }

}