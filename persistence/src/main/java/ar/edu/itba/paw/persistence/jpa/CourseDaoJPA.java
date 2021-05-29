package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.CourseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class CourseDaoJPA implements CourseDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Course> findAll() {
        final TypedQuery<Course> query = em.createQuery(
        "SELECT c FROM Course c", Course.class
        );
        return query.getResultList();
    }


    @Override
    public List<Course> findFavourites(User ofUser) {
        final TypedQuery<Course> query = em.createQuery(
    "SELECT c FROM Course c WHERE :userId IN (SELECT u.id FROM c.favedBy u)", Course.class
        );

        query.setParameter("userId", ofUser.getId());

        return query.getResultList();
    }


    @Override
    public List<CareerCourse> findByCareer(Career career) {
        final TypedQuery<CareerCourse> query = em.createQuery(
            "SELECT c FROM CareerCourse c WHERE c.career.code = :careerCode",
                CareerCourse.class
        );

        query.setParameter("careerCode", career.getCode());

        return query.getResultList();
    }


    @Override
    public Optional<Course> findById(String id) {
        return Optional.ofNullable(
            em.find(Course.class, id)
        );
    }

    @Override
    @Transactional
    public void markFavorite(Course course, User ofUser, boolean favorite) {
        if(favorite)
            course.getFavedBy().add(ofUser);
        else
            course.getFavedBy().remove(ofUser);
        em.merge(course);
    }

}