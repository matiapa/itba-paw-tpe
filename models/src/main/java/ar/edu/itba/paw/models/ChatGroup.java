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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "career_code", nullable = false)
    private Career career;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="link", nullable = false)
    private String link;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User uploader;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    private ChatPlatform platform;


    public ChatGroup(Integer id, Career career, String name, String link, User user, Date creationDate, ChatPlatform platform){
        this.id = id;
        this.career = career;
        this.name = name;
        this.link = link;
        this.creationDate = creationDate;
        this.uploader = user;
        this.platform = platform;
    }

    public ChatGroup(){}


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

    public User getUploader() {
        return uploader;
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

    public Career getCareer() {
        return career;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public void setPlatform(ChatPlatform platform) {
        this.platform = platform;
    }

    public enum ChatPlatform {
        whatsapp, discord
    }

}