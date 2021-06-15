package ar.edu.itba.paw.services;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserWorkRate;

public interface UserService {

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    User registerUser(int id, String name, String surname, String email, String passwordHash, Career career,
                      List<Course> courses, String websiteUrl, Locale locale) throws IOException;

    void setPicture(User user, byte picture[]);

    boolean verifyEmail(User user, int verificationCode);

    void registerLogin(User user);

}