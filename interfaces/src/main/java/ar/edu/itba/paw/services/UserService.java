package ar.edu.itba.paw.services;
import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {

    boolean isLogged();

    /** @return ID of the currently logged user
     *  @throws LoginRequiredException  If there is no user logged in */

    int getLoggedID() throws LoginRequiredException;

    Optional<User> getById(int id);

}
