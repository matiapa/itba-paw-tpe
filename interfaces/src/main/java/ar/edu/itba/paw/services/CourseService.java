package ar.edu.itba.paw.services;
import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findAll();

    List<Course> findFavourites();

    List<Course> findFavourites(int limit);

    List<Course> findByCareer(int careerId);

    List<Course> findByCareer(int careerId, int limit);

    Optional<Course> findById(String id);

}
