package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
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
    public  List<Content> findByCourse(String courseId){
        return contentDao.findByCourse(courseId);
    }

    @Override
    public List<Content> findByCourse(String courseId, int offset, int limit) {
        return contentDao.findByCourse(courseId, offset, limit);
    }

    @Override
    public int getSize(String courseId) {
        return contentDao.getSize(courseId);
    }

    @Override
    public List<Content> findByCourse(String courseId, Content.ContentType contentType, Date minDate, Date maxDate) {
        return contentDao.findContent(courseId, contentType, minDate, maxDate);
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
