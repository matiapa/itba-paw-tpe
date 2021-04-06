package ar.edu.itba.paw.services;
import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findFavourites() throws LoginRequiredException;

    List<Course> findFavourites(int limit) throws LoginRequiredException;

    List<Course> findByCareer(int careerId);

    List<Course> findByCareer(int careerId, int limit);

    Optional<Course> findById(String id);

}
