package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @SuppressWarnings("SameReturnValue")
    private boolean isLogged() {
        return true;
    }

    @Override
    public User getUser() {
        if(isLogged())
            return new User(1, "Test User","user surname","email",1);
//            throw new UnsupportedOperationException();
        else
            return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public User registerUser(int id, String name, String surname, String email, int career_id) {
        return userDao.registerUser(id,name,surname,email,career_id);
    }
}
