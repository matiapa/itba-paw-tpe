package ar.edu.itba.paw.persistence.jpa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.StatisticsDao;

@Repository
@SuppressWarnings("unchecked")
public class StatisticsDaoJPA implements StatisticsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Map<Entity, Integer> newContributions(User loggedUser) {
        List<Object[]> res = em.createNativeQuery("SELECT * FROM new_contribs(:forUser)")
            .setParameter("forUser", loggedUser.getId())
            .getResultList();

        Map<Entity, Integer> map = new HashMap<>();
        for(Object[] row : res)
            map.put(Entity.valueOf((String) row[0]), ((BigDecimal) row[1]).intValue());

        return map;
    }

    @Override
    public Map<Career, Integer> contributionsByCareer() {
        List<Object[]> res = em.createNativeQuery("SELECT code, contribs FROM career_contribs")
            .getResultList();

        Map<Career, Integer> map = new HashMap<>();
        for(Object[] row : res)
            map.put(em.find(Career.class, row[0]), ((BigDecimal) row[1]).intValue());

        return map;
    }

    @Override
    public Map<Date, Integer> contributionsByDate() {
        List<Object[]> res = em.createNativeQuery("SELECT date, contribs FROM daily_contribs")
                .getResultList();

        Map<Date, Integer> map = new HashMap<>();
        for(Object[] row : res){
            map.put((Date) row[0], ((BigInteger) row[1]).intValue());
        }

        return map;
    }

    @Override
    public Map<User, Integer> topUsersByContributions() {
        List<Object[]> res = em.createNativeQuery("SELECT id, contribs FROM top_users_contribs")
            .getResultList();

        Map<User, Integer> map = new HashMap<>();
        for(Object[] row : res)
            map.put(em.find(User.class, row[0]), ((BigDecimal) row[1]).intValue());

        return map;
    }

    @Override
    public Map<Course, Integer> topCoursesByContributions() {
        List<Object[]> res = em.createNativeQuery("SELECT id, contribs FROM top_courses_contribs")
            .getResultList();

        Map<Course, Integer> map = new HashMap<>();
        for(Object[] row : res)
            map.put(em.find(Course.class, row[0]), ((BigDecimal) row[1]).intValue());

        return map;
    }

}