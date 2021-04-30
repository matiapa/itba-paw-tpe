package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CareerDao {

    List<Career> findAll();

    Optional<Career> findByCode(String code);

    Map<Integer, List<CareerCourse>> findByCareer(String careerCode);

}
