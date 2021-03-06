package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "creation_date", nullable = false)
    private Date uploadDate = new Date();

    @Column(name = "expiry_date")
    private Date expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_code")
    private Career career;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User uploader;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "announcement_seen",
        joinColumns = @JoinColumn(name = "announcement_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> seenBy;


    public Announcement(){}

    public Announcement(Integer id, String title, String summary, String content, Career career, Course course,
                        User uploader, Date expiryDate) {
        this.id = id;
        this.uploader = uploader;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.career = career;
        this.course = course;
        this.expiryDate = expiryDate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public List<User> getSeenBy() {
        return seenBy;
    }

    public void setSeenBy(List<User> seenBy) {
        this.seenBy = seenBy;
    }
}