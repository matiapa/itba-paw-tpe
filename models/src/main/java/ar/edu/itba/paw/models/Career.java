package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "career")
public class Career {

    @Column(length = 100, nullable = false)
    private String name;

    @Id
    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "career_id")
    private List<Announcement> announcements;

    Career(){}

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

}