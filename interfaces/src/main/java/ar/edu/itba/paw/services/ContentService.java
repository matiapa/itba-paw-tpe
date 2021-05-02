package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Content.ContentType;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;

public interface ContentService {

    List<Content> findByCourse(String courseId, int offset, int limit);

    List<Content> findByCourse(String courseId, ContentType contentType, Date minDate, Date maxDate, int offset, int limit);

    int getSize(String courseId, ContentType contentType, Date minDate, Date maxDate);

    boolean createContent(String name, String link, String courseId, String description, String contentType, Date contentDate,User user);

    void delete(int id);

}
