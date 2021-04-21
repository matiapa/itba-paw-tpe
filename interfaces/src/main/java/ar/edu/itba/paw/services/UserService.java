package ar.edu.itba.paw.services;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.models.User;

public interface UserService {

    Optional<User> findByEmail(String email);
    
    User registerUser(int id, String name, String surname, String email, int career_id, List<String> courses);

    User getLoggedUser();

}
