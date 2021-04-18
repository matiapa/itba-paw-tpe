package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
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
    public List<Content> findByCourse(String courseId, int limit) {
        return contentDao.findByCourse(courseId, limit);
    }

    @Override
    public List<Content> findByCourseAndType(String courseId, String contentType) {
        return contentDao.findByCourseAndType(courseId, contentType);
    }

    @Override
    public List<Content> findContent(String courseId, String contentType, Date minDate, Date maxDate) {
        return contentDao.findContent(courseId,contentType,minDate,maxDate);
    }

}
