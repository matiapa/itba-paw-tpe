package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoJdbc implements UserDao {

    @Override
    public User register(String email, String password) {
        System.out.println(String.format("Registered %s", email));
        return new User(email, password);
    }

}
