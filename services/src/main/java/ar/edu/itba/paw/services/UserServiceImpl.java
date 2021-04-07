package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LoginRequiredException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @SuppressWarnings("SameReturnValue")
    private boolean isLogged() {
        return false;
    }

    @Override
    public int getLoggedID() throws LoginRequiredException {
        if(isLogged())
            throw new UnsupportedOperationException();
        else
            throw new LoginRequiredException();
    }

}
