package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id_seq")
    @SequenceGenerator(sequenceName = "course_id_seq", name = "course_id_seq", allocationSize = 1)
    @Column(name = "id")
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(name = "credits")
    private int credits;

    @OneToMany(mappedBy = "course_id")
    private List<Announcement> announcements;

    @ManyToMany()
    @JoinTable(
        name = "fav_course",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> favedBy;

    Course(){}

    public Course(String id, String name,int credits) {
        this.id = id;
        this.name = name;
        this.credits=credits;
    }

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
}