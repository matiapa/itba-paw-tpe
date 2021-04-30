package ar.edu.itba.paw.services;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;

public interface CourseService {

    List<Course> findAll();

    List<Course> findFavourites(User user);

    List<Course> findFavourites(User user, int limit);

    List<Course> findByCareer(String careerCode);

    List<Course> findByCareer(String careerCode, int limit);

    Optional<Course> findById(String id);

}
