package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "career")
public class Career {

    @Id
    @Column(name = "code")
    private String code;

    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "career", fetch = FetchType.LAZY)
    private List<CareerCourse> careerCourses;

    @OneToMany(mappedBy = "career", fetch = FetchType.LAZY)
    private List<Announcement> announcements;

    @OneToMany(mappedBy = "career", fetch = FetchType.LAZY)
    private List<ChatGroup> chatGroups;


    public Career(){}


    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public List<CareerCourse> getCareerCourses() {
        return careerCourses;
    }

    public List<ChatGroup> getChatGroups() {
        return chatGroups;
    }

    public void setChatGroups(List<ChatGroup> chatGroups) {
        this.chatGroups = chatGroups;
    }

    public void setCareerCourses(List<CareerCourse> careerCourses) {
        this.careerCourses = careerCourses;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

}