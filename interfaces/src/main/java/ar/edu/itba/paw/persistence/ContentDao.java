package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;

public interface ContentDao {

    List<Content> findByCourse(String courseId);

    List<Content> findByCourse(String courseId, int offset, int limit);

    List<Content> findByCourseAndType(String courseId, String contentType);

    List<Content> findContent(String courseId, Content.ContentType contentType, Date minDate, Date maxDate);

    boolean createContent(String name, String link, String courseId, String description, String contentType, Date contentDate,User user);

    int getSize(String courseId);
}
