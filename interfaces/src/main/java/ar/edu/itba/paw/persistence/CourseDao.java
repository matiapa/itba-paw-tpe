package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    List<Course> findFavourites(int userId);

    List<Course> findByCareer(int careerId);

    Optional<Course> findById(String id);

}
