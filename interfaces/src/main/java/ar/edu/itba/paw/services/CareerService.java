package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CareerService {

    List<Career> findAll();

    List<Career> findByCourse(Course course);

    Optional<Career> findById(int id);

}
