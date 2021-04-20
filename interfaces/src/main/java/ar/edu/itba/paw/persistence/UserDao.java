package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);
}
