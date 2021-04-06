package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;

import java.util.List;
import java.util.Optional;

public interface ContentService {

    List<Content> findByCourse(String courseId);

    List<Content> findByCourse(String courseId, int limit);

    Optional<Content> findById(String id);

}
