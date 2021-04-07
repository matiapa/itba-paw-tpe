package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.persistence.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
