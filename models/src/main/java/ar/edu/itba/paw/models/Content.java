package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;


@Entity
@Table(name = "course_content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_content_id_seq")
    @SequenceGenerator(sequenceName = "course_content_id_seq", name = "course_content_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_email")
    private String ownerMail;

    @Column(name = "creation_date", nullable = false)
    private Date uploadDate = new Date();

    @Column(name = "content_date")
    private Date contentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User uploader;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


    public Content(Integer id, String name, String link, String description, User submitter, String ownerMail,
           Date contentDate, ContentType contenttype, Course course) {
        this.id = id;
        this.name = name;
        this.link=link;
        this.description=description;
        this.ownerMail = ownerMail;
        this.uploader = submitter;
        this.contentDate = contentDate;
        this.contentType = contenttype;
        this.course = course;
    }

    public Content(){}


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerMail() {
        return ownerMail;
    }

    public User getUploader() {
        return uploader;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public Date getContentDate() {
        return contentDate;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public Course getCourse() {
        return course;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwnerMail(String ownerMail) {
        this.ownerMail = ownerMail;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setContentDate(Date contentDate) {
        this.contentDate = contentDate;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public enum ContentType{
        exam, guide, exercise, project, theory, summary, bibliography, solution, code, miscellaneous
    }

}
