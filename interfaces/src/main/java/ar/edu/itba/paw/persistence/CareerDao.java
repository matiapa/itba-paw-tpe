package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;

import java.util.List;
import java.util.Optional;

public interface CareerDao {

    Optional<Career> findById(int id);

    List<Career> getCareers();
}
