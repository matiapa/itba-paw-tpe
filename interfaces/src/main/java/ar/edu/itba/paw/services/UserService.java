package ar.edu.itba.paw.services;
import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.User;

public interface UserService {

    User getUser();

    User registerUser(int id, String name, String surname, String email,int career_id);

}
