package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserWorkRate;
import ar.edu.itba.paw.persistence.UserWorkRateDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserWorkRateJPA implements UserWorkRateDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserWorkRate> getRates(User ofUser, int page, int pageSize) {
        TypedQuery<UserWorkRate> query = em.createQuery(
    "SELECT r FROM UserWorkRate r WHERE r.rated.id = :ratedId",
            UserWorkRate.class
        );

        query.setParameter("ratedId", ofUser.getId());
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    @Transactional
    public UserWorkRate rate(User rater, User rated, Course course, int behaviour, int skills, String comment) {
        UserWorkRate rate = new UserWorkRate(rater, rated, course, behaviour, skills, comment);

        em.persist(rate);

        return rate;
    }

}
