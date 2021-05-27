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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by")
    private User submitter;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_email")
    private String ownerMail;

    @Column(name = "creation_date", columnDefinition = "date default now() not null")
    private Date uploadDate;

    @Column(name = "content_date")
    private Date contentDate;

    @Column(name = "content_type")
    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    public Content(Integer id, String name, String link, String description, User submitter, String ownerMail,
           Date uploadDate, Date contentDate, ContentType contenttype, Course course) {
        this.id = id;
        this.name = name;
        this.link=link;
        this.description=description;
        this.ownerMail = ownerMail;
        this.submitter = submitter;
        this.uploadDate = uploadDate;
        this.contentDate = contentDate;
        this.contentType = contenttype;
        this.course = course;
    }

    public Content(){

    }

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

    public User getSubmitter() {
        return submitter;
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


    public enum ContentType{
        exam, guide, exercise, project, theory, summary, bibliography, solution, code, miscellaneous
    }

}
