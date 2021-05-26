package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;


@Entity
@Table(name = "course_content")
public class Content {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_content_id_seq")
    @SequenceGenerator(sequenceName = "coourse_content_id_seq", name = "course_content_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String link;

    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User submitter;


    @Column(name = "owner_email")
    private String ownerMail;

    @Column(name = "creation_date", columnDefinition = "date default now() not null")
    private Date uploadDate;

    private Date contentDate;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    public Content(Integer id, String name, String link, String description, User submitter, String ownerMail,
           Date uploadDate, Date contentDate, ContentType contenttype) {
        this.id = id;
        this.name = name;
        this.link=link;
        this.description=description;
        this.ownerMail = ownerMail;
        this.submitter = submitter;
        this.uploadDate = uploadDate;
        this.contentDate = contentDate;
        this.contentType = contenttype;
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

//@Entity
//@Table(name = "course")
//public class Course {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id_seq")
//    @SequenceGenerator(sequenceName = "course_id_seq", name = "course_id_seq", allocationSize = 1)
//    @Column(name = "id")
//    private String id;
//
//    @Column(length = 100, nullable = false)
//    private String name;
//
//    @Column(name = "credits")
//    private int credits;
//
//    Course(){
//
//    }
//
//    public Course(String id, String name,int credits) {
//        this.id = id;
//        this.name = name;
//        this.credits=credits;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getCredits() {
//        return credits;
//    }
//}