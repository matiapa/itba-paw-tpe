package ar.edu.itba.paw.services;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;

public interface CourseService {

    List<Course> findAll();

    List<Course> findFavourites(User user);

    List<CareerCourse> findByCareer(Career career);

    Optional<Course> findById(String id);

    void markFavorite(Course course, User ofUser, boolean favorite);

}