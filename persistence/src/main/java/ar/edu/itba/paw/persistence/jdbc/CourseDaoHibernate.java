package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CourseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CourseDaoHibernate implements CourseDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Course> findAll() {
        final TypedQuery<Course> query = entityManager.createQuery(
                "from Course", Course.class
        );
        final List<Course> list = query.getResultList();
        return list;
    }

    @Override
    public List<Course> findFavourites(int userId) {
        return null;
    }

    @Override
    public List<Course> findFavourites(int userId, int limit) {
        return null;
    }

    @Override
    public List<Course> findByCareer(String careerCode, int limit) {
        return null;
    }

    @Override
    public Optional<Course> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Map<Integer, List<CareerCourse>> findByCareerSemester(String careerCode) {
        return null;
    }
}
