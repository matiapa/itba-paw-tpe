package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;

import java.util.List;
import java.util.Optional;

public interface CareerService {

    List<Career> findAll();

    Optional<Career> findById(int id);



}
