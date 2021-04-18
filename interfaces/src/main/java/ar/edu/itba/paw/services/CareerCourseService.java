package ar.edu.itba.paw.services;



import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Map;

public interface CareerCourseService {
    Map<Integer,List<CareerCourse>> findByCareer(int careerId);
}
