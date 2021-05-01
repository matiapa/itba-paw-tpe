package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User registerUser(int id, String name, String surname, String email, String careerCode, List<String> courses) {
        return userDao.registerUser(id,name,surname,email,careerCode,courses);
    }

    @Override
    public User getLoggedUser() {
        return userDao.findById(59714).orElseThrow(RuntimeException::new);
    }

    @Override
    public void setProfilePicture(String pictureDataURI) {
        userDao.setProfilePicture(pictureDataURI, getLoggedUser().getId());
    }
}
