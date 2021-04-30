package ar.edu.itba.paw.services;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.models.User;

public interface UserService {

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);
    
    User registerUser(int id, String name, String surname, String email, String careerCode, List<String> courses);

    User getLoggedUser();

    void setProfilePicture(String pictureDataURI);

}
