package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @SuppressWarnings("SameReturnValue")
    private boolean isLogged() {
        return true;
    }

    @Override
    public User getUser() {
        if(isLogged())
            return new User(1, "Test User");
//            throw new UnsupportedOperationException();
        else
            return null;
    }

}
