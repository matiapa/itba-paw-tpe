package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;

import java.util.List;
import java.util.Optional;

public interface CareerDao {

    List<Career> findAll();

    Optional<Career> findByCode(String code);

}
