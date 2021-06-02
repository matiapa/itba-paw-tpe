package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_work_rate")
public class UserWorkRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_work_rate_id_seq")
    @SequenceGenerator(sequenceName = "user_work_rate_id_seq", name = "user_work_rate_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rater", nullable = false)
    private User rater;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rated", nullable = false)
    private User rated;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course", nullable = false)
    private Course course;

    @Column(name = "behaviour", columnDefinition = "int not null check(behaviour >= 1 and behaviour <= 5)")
    private int behaviour;

    @Column(name = "skills", columnDefinition = "int not null  check(skills >= 1 and skills <= 5)")
    private int skills;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "rate_date", nullable = false)
    private final Date rateDate = new Date();

    UserWorkRate(){}

    public UserWorkRate(User rater, User rated, Course course, int behaviour, int skills, String comment) {
        this.rater = rater;
        this.rated = rated;
        this.course = course;
        this.behaviour = behaviour;
        this.skills = skills;
        this.comment = comment;
    }

    public Course getCourse() {
        return course;
    }

    public int getBehaviour() {
        return behaviour;
    }

    public int getSkills() {
        return skills;
    }

    public String getComment() {
        return comment;
    }

    public Date getRateDate() {
        return rateDate;
    }

}