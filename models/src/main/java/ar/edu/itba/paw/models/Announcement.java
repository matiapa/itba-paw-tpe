package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcement_id_seq")
    @SequenceGenerator(sequenceName = "announcement_id_seq", name = "announcement_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Career career;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @ManyToMany()
    @JoinTable(
        name = "announcement_seen",
        joinColumns = @JoinColumn(name = "announcement_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> seenBy;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User uploader;

    @Column(name = "creation_date", columnDefinition = "date default now() not null")
    private Date uploadDate;

    @Column(name = "expiry_date")
    private Date expiryDate;

    Announcement(){}

    public Announcement(Integer id, String title, String summary, String content, Career career, Course course,
                        User uploader, Date uploadDate, Date expiryDate) {
        this.id = id;
        this.uploader = uploader;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.uploadDate = uploadDate;
        this.career = career;
        this.course = course;
        this.expiryDate = expiryDate;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getUploader() {
        return uploader;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public Career getCareer() {
        return career;
    }

    public Course getCourse() {
        return course;
    }

    public List<User> getSeenBy() {
        return seenBy;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setSeenBy(List<User> seenBy) {
        this.seenBy = seenBy;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}