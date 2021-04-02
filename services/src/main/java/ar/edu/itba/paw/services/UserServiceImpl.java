package ar.edu.itba.paw.services;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean isLogged() {
        return false;
    }

    @Override
    public int getID() {
        throw new RuntimeException("Unimplemented method");
    }

}
