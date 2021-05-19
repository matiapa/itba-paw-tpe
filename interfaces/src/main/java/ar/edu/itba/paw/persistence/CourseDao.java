package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseDao {

    List<Course> findAll();

    List<Course> findFavourites(int userId);

    List<Course> findByCareer(String careerCode, int limit);

    Optional<Course> findById(String id);

    Map<Integer, List<CareerCourse>> findByCareerSemester(String careerCode);

    void addFavourite(int id, String course);

    void removeFavourite(int id, String course);

    boolean isFaved(String courseId, Integer userId);

}
