package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Content.ContentType;
import ar.edu.itba.paw.models.ContentReview;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ContentDao {

    List<Content> findByCourse(Course course, ContentType contentType, Date minDate, Date maxDate,
                               Integer page, Integer pageSize);

    Optional<Content> findById(int id);

    int getSize(Course course, ContentType contentType, Date minDate, Date maxDate);

    Content createContent(String name, String link, Course course, String description, ContentType contentType,
                          Date contentDate, User uploader, String ownerMail);

    void delete(Content content);

    List<ContentReview> getReviews(Content content, Integer page, Integer pageSize);

    ContentReview createContentReview(Content content,String review,User uploader);

    int getReviewsSize(Content content);

    List<User> getSubscribedUsers(Content content);

}
