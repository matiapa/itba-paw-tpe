package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Content.ContentType;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private ContentDao contentDao;

    @Override
    public List<Content> findByCourse(String courseId, ContentType contentType, Date minDate, Date maxDate, int offset, int limit) {
        return contentDao.findContent(courseId, contentType, minDate, maxDate, offset, limit);
    }

    @Override
    public int getSize(String courseId, ContentType contentType, Date minDate, Date maxDate) {
        return contentDao.getSize(courseId, contentType, minDate, maxDate);
    }

    @Override
    public boolean createContent(String name, String link, String courseId, String description, String contentType, Date contentDate,User user ) {
        return contentDao.createContent(name, link, courseId, description, contentType, contentDate,user);
    }

    @Override
    public void delete(int id) {
            contentDao.delete(id);
    }

}
