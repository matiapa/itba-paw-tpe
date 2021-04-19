package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;

public interface ContentService {

    List<Content> findByCourse(String courseId);

    List<Content> findByCourse(String courseId, int limit);

    List<Content> findByCourseAndType(String courseId, String contentType);

    List<Content> findContent(String courseId, String contentType, Date minDate,Date maxDate);

    boolean createContent(String name, String link, String courseId, String description, Content.ContentType contentType, Date contentDate,User user);

}
