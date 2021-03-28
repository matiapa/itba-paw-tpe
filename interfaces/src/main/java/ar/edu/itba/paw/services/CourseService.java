package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findFavourites(int userId);

    Optional<Course> findById(String id);

}
