package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "id")
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "credits")
    private Integer credits;

    @OneToMany(mappedBy = "course")
    private List<CareerCourse> courseCareers;

    @OneToMany(mappedBy = "course")
    private List<Announcement> announcements;

    @ManyToMany()
    @JoinTable(
        name = "fav_course",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> favedBy;

    Course(){}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public List<User> getFavedBy() {
        return favedBy;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public List<CareerCourse> getCourseCareers() {
        return courseCareers;
    }

    public void setCourseCareers(List<CareerCourse> courseCareers) {
        this.courseCareers = courseCareers;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public void setFavedBy(List<User> favedBy) {
        this.favedBy = favedBy;
    }

}