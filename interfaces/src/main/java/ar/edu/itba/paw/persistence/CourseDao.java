package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseDao {

    List<Course> findAll();

    List<Course> findFavourites(int userId);

    List<Course> findFavourites(int userId, int limit);

    List<Course> findByCareer(String careerCode, int limit);

    Course findById(String id);

    Map<Integer, List<CareerCourse>> findByCareerSemester(String careerCode);

}
