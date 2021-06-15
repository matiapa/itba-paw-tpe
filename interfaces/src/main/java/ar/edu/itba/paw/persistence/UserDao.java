package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserWorkRate;

public interface UserDao {

    List<User> findAll();

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);
    
    User registerUser(int id, String name, String surname, String email,String password_hash,
          Career career, List<Course> courses);

    boolean verifyEmail(User user, int verificationCode);

    Optional<Integer> getVerificationCode(User user);

    void setPicture(User loggedUser, byte picture[]);

	void registerLogin(User user);

}