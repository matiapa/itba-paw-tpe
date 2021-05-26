package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Content.ContentType;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;

public interface ContentDao {

    List<Content> findContent(String courseId, Content.ContentType contentType, Date minDate, Date maxDate, Integer offset, Integer limit);

    boolean createContent(String name, String link, String courseId, String description, String contentType, Date contentDate,User user);

    int getSize(String courseId, ContentType contentType, Date minDate, Date maxDate);

    void delete(int id);

}
