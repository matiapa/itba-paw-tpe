package ar.edu.itba.paw.services;
import ar.edu.itba.paw.exceptions.LoginRequiredException;

public interface UserService {

    /** @return ID of the currently logged user
     *  @throws LoginRequiredException  If there is no user logged in */

    int getLoggedID() throws LoginRequiredException;

}
