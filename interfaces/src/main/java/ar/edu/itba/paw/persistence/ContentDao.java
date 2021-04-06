package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Content;

import java.util.List;
import java.util.Optional;

public interface ContentDao {

    List<Content> findByCourse(String courseId);

    List<Content> findByCourse(String courseId, int limit);

    Optional<Content> findById(String id);

}
