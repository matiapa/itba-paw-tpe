package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;

import java.util.List;
import java.util.Optional;

public interface CareerService {

    Optional<Career> findById(int id);
    List<Career> getCareers();
}
