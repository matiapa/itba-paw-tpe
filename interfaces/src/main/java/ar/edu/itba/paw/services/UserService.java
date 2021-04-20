package ar.edu.itba.paw.services;
import java.util.Optional;

import ar.edu.itba.paw.models.User;

public interface UserService {

    User getUser();

    Optional<User> findByEmail(String email);
}
