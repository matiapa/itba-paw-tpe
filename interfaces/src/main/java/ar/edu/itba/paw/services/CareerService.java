package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CareerService {

    List<Career> findAll();

    Optional<Career> findByCode(String code);

}