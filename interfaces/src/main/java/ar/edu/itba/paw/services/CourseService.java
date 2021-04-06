package ar.edu.itba.paw.services;
import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    /** @return List of the favourite courses of the user
     *  @throws LoginRequiredException  If there is no user logged in */

    List<Course> findFavourites() throws LoginRequiredException;

    List<Course> findFavourites(int limit) throws LoginRequiredException;

    List<Course> findByCareer(int careerId);

    List<Course> findByCareer(int careerId, int limit);

    Optional<Course> findById(String id);

}
