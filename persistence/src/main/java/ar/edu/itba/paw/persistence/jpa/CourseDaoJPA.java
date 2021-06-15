package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.*;
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

    @Override
    @Transactional
    public CourseReview createCourseReview(Course course, String review, User uploader) {
        CourseReview courseReview = new CourseReview(null,review,uploader,course);
        em.persist(courseReview);
        em.flush();
        em.clear();
        return courseReview;
    }

    @Override
    public List<CourseReview> getReviews(Course course, Integer page, Integer pageSize) {

        final TypedQuery<CourseReview> query = em.createQuery(
                "SELECT c FROM CourseReview c WHERE c.course= :course ", CourseReview.class
        );

        query.setParameter("course", course);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();

    }

    @Override
    public int getReviewsSize(Course course) {
        final TypedQuery<CourseReview> query = em.createQuery(
                "SELECT c FROM CourseReview c WHERE c.course= :course ", CourseReview.class
        );

        query.setParameter("course", course);

        return query.getResultList().size();
    }

}