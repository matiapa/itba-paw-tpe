package ar.edu.itba.paw.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDao;

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
    public User registerUser(int id, String name, String surname, String email,String passwordHash, Career career,
          List<Course> courses, String websiteUrl) throws IOException {
        User user = userDao.registerUser(id, name, surname, email, passwordHash, career, courses);

        emailService.sendVerificationEmail(user, websiteUrl);

        return user;
    }

    @Override
    public boolean verifyEmail(User user, int verificationCode) {
        return userDao.verifyEmail(user, verificationCode);
    }

    @Override
    public void setPicture(User user, byte picture[]) {
        userDao.setPicture(user, picture);
    }

    @Override
    public void registerLogin(User user) {
        userDao.registerLogin(user);
    }

}
