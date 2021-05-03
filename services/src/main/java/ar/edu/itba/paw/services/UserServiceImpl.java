package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserDao userDao;

    @Autowired private EmailService emailService;


    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findById(int id) {
        return userDao.findById(id);
    }

    @Transactional
    @Override
    public User registerUser(int id, String name, String surname, String email, String passwordHash,
             String careerCode, List<String> courses, String websiteUrl) throws IOException {
        User user = userDao.registerUser(id, name, surname, email, passwordHash, careerCode, courses);

        emailService.sendVerificationEmail(email, websiteUrl);

        return user;
    }

    @Override
    public boolean verifyEmail(int userId, int verificationCode) {
        return userDao.verifyEmail(userId, verificationCode);
    }

    @Override
    public void setProfilePicture(User loggedUser, String pictureDataURI) {
        userDao.setProfilePicture(pictureDataURI, loggedUser.getId());
    }

}
