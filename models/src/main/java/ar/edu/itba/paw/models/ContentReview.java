package ar.edu.itba.paw.models;


import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Table(name = "content_review")
public class ContentReview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_review_id_seq")
    @SequenceGenerator(sequenceName = "content_review_id_seq", name = "content_review_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "review")
    private String review;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submitted_by", nullable = false)
    private User uploader;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "creation_date", nullable = false)
    private Date uploadDate = new Date();


    public ContentReview(Integer id, String review, User uploader, Content content) {
        this.id = id;
        this.review = review;
        this.uploader = uploader;
        this.content = content;
    }

    public ContentReview() {}

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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
