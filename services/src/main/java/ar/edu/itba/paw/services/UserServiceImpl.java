package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public boolean isLogged() {
        return false;
    }

    @Override
    public int getLoggedID() {
        throw new RuntimeException("Unimplemented method");
    }

    @Override
    public Optional<User> getById(int id) {
        return userDao.findById(id);
    }

}
