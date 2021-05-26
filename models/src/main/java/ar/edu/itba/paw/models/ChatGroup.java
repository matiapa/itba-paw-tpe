package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Table(name = "chat_group")
public class ChatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_group_id_seq")
    @SequenceGenerator(sequenceName = "chat_group_id_seq", name = "chat_group_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

//    @Column(name = "career_code")
//    private String careerCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "career_code")
    private Career career;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="link",nullable = false)
    private String link;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User submittedBy;

    @Enumerated(EnumType.STRING)
    private ChatPlatform platform;

    public ChatGroup(Integer id, Career career, String name, String link, User user, Date creationDate, ChatPlatform platform){
        this.id = id;
        this.career = career;
        this.name = name;
        this.link = link;
        this.creationDate = creationDate;
        this.submittedBy = user;
        this.platform = platform;
    }

    public ChatGroup(){

    }

    public Integer getId() {return id;}

    public Career getCareerCode() {
        return career;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Integer getCreationYear() {
        return creationDate.getYear() + 1900;
    }

    public Integer getCreationQuarter() {
        return creationDate.getMonth() < 7 ? 1 : 2;
    }

    public ChatPlatform getPlatform() {
        return platform;
    }

    public enum ChatPlatform {
        whatsapp, discord
    }

}

