package ar.edu.itba.paw.services;
import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {

    boolean isLogged();

    int getLoggedID() throws LoginRequiredException;

    Optional<User> getById(int id);

}
