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

    @OneToMany(mappedBy = "career")
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