package ar.edu.itba.paw.models;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Table(name = "course_review")
public class CourseReview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_review_id_seq")
    @SequenceGenerator(sequenceName = "course_review_id_seq", name = "course_review_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "review")
    private String review;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User uploader;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "creation_date", nullable = false)
    private Date uploadDate = new Date();


    public CourseReview(Integer id, String review, User uploader, Course course) {
        this.id = id;
        this.review = review;
        this.uploader = uploader;
        this.course = course;
    }

    public CourseReview() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
