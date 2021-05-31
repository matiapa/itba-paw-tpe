package ar.edu.itba.paw.persistence.jpa;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.LoginActivity;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserVerification;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoJPA implements UserDao {

    @PersistenceContext
    private EntityManager em;
    private final Random random;

    public UserDaoJPA() {
        random = new Random();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(
            em.find(User.class, id)
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);

        try{
            return Optional.of(query.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Transactional
    void createVerificationCode(User user) {
        int code=random.nextInt(1000000);
        UserVerification verification = new UserVerification(user, code);
        user.setVerification(verification);
        em.persist(verification);
    }

    @Override
    @Transactional
    public User registerUser(int id, String name, String surname, String email, String passwordHash, Career career,
            List<Course> courses) {
        User user = new User(id, name, surname, email, passwordHash, career);
        user.setFavoriteCourses(new TreeSet<>(courses));
        em.persist(user);
        createVerificationCode(user);
        return user;
    }

    @Override
    @Transactional
    public boolean verifyEmail(User user, int verificationCode) {
        if(user.getVerification().getCode() != verificationCode)
            return false;

        user.setVerified(true);
        em.remove(user.getVerification());
        user.setVerification(null);
        em.flush();
        em.clear();
        em.merge(user);
        return true;
    }

    @Override
    public Optional<Integer> getVerificationCode(User user) {
        UserVerification verification = user.getVerification();
        return verification != null ? Optional.of(verification.getCode()) : Optional.empty();
    }

    @Override
    @Transactional
    public void setProfilePicture(String pictureDataURI, User user) {
        user.setProfilePicture(pictureDataURI);
        em.merge(user);
    }

    @Override
    @Transactional
    public void registerLogin(User user) {
        LoginActivity activity = new LoginActivity(user);
        em.persist(activity);
    }
    
}
