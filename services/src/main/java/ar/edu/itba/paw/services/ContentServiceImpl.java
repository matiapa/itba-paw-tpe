package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Content.ContentType;
import ar.edu.itba.paw.models.ContentReview;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private ContentDao contentDao;

    @Override
    public List<Content> findByCourse(Course course, ContentType contentType, Date minDate, Date maxDate,
                                      Integer page, Integer pageSize) {
        return contentDao.findByCourse(course, contentType, minDate, maxDate, page, pageSize);
    }

    @Override
    public Optional<Content> findById(int id) {
        return contentDao.findById(id);
    }

    @Override
    public int getSize(Course course, ContentType contentType, Date minDate, Date maxDate) {
        return contentDao.getSize(course, contentType, minDate, maxDate);
    }

    @Override
    public boolean createContent(String name, String link, Course course, String description, ContentType contentType,
                                 Date contentDate, User uploader, String ownerMail) {
        return contentDao.createContent(name, link, course, description, contentType, contentDate, uploader,ownerMail);
    }

    @Override
    public void delete(Content content) {
            contentDao.delete(content);
    }

    @Override
    public List<ContentReview> getReviews(Content content, Integer page, Integer pageSize) {
        return contentDao.getReviews(content,page,pageSize);
    }

    @Override
    public ContentReview createContentReview(Content content, String review, User uploader) {
        return contentDao.createContentReview(content,review,uploader);
    }

    @Override
    public int getReviewsSize(Content content) {
        return contentDao.getReviewsSize(content);
    }

}
