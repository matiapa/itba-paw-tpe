package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseDao {

    List<Course> findAll();

    List<Course> findFavourites(User ofUser);

    List<CareerCourse> findByCareer(Career career);

    Optional<Course> findById(String id);

    void markFavorite(Course course, User ofUser, boolean favorite);

}
