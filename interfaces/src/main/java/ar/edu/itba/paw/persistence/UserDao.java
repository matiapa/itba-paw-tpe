package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);
    
    User registerUser(int id, String name, String surname, String email,String password_hash,
          String careerCode, List<String>courses);

    boolean verifyEmail(int userId, int verificationCode);

    int getVerificationCode(String email);

    void setProfilePicture(String pictureDataURI, int userId);

	void registerLogin(int id);

}