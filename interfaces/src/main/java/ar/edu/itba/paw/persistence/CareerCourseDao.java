package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Map;

public interface CareerCourseDao {

    Map<Integer, List<CareerCourse>> findByCareer(int careerId);
}
