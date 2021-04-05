package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(int id);

}
