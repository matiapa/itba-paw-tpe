package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;

public interface UserDao {

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);
    
    User registerUser(int id, String name, String surname, String email,String password_hash,
          Career career, List<Course> courses);

    boolean verifyEmail(User user, int verificationCode);

    Optional<Integer> getVerificationCode(User user);

    void setProfilePicture(String pictureDataURI, User user);

	void registerLogin(User user);

	void addFavouriteCourse(User user, Course course);

	void removeFavouriteCourse(User user, Course course);

}