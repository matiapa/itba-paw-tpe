package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Content.ContentType;
import ar.edu.itba.paw.models.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

public interface ContentService {

    List<Content> findByCourse(String courseId, ContentType contentType, Date minDate, Date maxDate, Integer offset, Integer limit);

    int getSize(String courseId, ContentType contentType, Date minDate, Date maxDate);

    boolean createContent(String name, String link, String courseId, String description, String contentType, Date contentDate,User user);

    void delete(int id);

}
