package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Content;

import java.util.List;
import java.util.Optional;

public interface ContentDao {
    //    List<Content> findByCareer(int careerId);

    List<Content> findByCourse(String courseId);

    Optional<Content> findById(String id);
}
