package ar.edu.itba.paw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean isLogged() {
        return false;
    }

    @Override
    public int getID() {
        throw new NotImplementedException();
    }

}
