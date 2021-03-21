package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.User;

public interface UserDao {

    User register(String email, String password);

}
