package ar.edu.itba.paw.services;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.models.User;

public interface UserService {

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    User registerUser(int id, String name, String surname, String email,String password_hash, String careerCode,
          List<String> courses, String websiteUrl) throws IOException;

    void setProfilePicture(User loggedUser, String pictureDataURI);

    boolean verifyEmail(int userId, int verificationCode);

}