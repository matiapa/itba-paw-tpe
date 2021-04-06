package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    List<Course> findFavourites(int userId);

    List<Course> findFavourites(int userId, int limit);

    List<Course> findByCareer(int careerId);

    List<Course> findByCareer(int careerId, int limit);

    Optional<Course> findById(String id);

}
