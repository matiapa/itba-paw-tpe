package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CourseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class CourseDaoJPA implements CourseDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Course> findAll() {
        final TypedQuery<Course> query = entityManager.createQuery(
                "from course", Course.class
        );
        final List<Course> list = query.getResultList();
        return list;
    }

    @Override
    public List<Course> findFavourites(int userId) {
        final TypedQuery<Course> query = entityManager.createQuery(
                "FROM fav_course AS fc INNER JOIN course AS c ON fc.course_id = c.id" +
                        "WHERE user_id = :userId ORDER BY c.id", Course.class
        );
        final List<Course> list = query.getResultList();
        return list;
    }

    @Override
    public List<Course> findFavourites(int userId, int limit) {
        final TypedQuery<Course> query = entityManager.createQuery(
          "FROM fav_course AS fc INNER JOIN course AS c ON fc.course_id = c.id" +
                  "WHERE user_id = :userId ORDER BY c.id LIMIT :limit", Course.class
        );
        final List<Course> list = query.getResultList();
        return list;
    }

    @Override
    public List<Course> findByCareer(String careerCode, int limit) {
        final TypedQuery<Course> query = entityManager.createQuery(
                "FROM course AS c INNER JOIN career_course AS cc ON cc.course_id = c.id" +
                        "WHERE cc.code = :careerCode" +
                        "ORDER BY c.id LIMIT :limit", Course.class
        );
        final List<Course> list = query.getResultList();
        return list;

    }

    @Override
    public Course findById(String id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    public Map<Integer, List<CareerCourse>> findByCareerSemester(String careerCode) {
        final TypedQuery<CareerCourse> query = entityManager.createQuery(
            "FROM career_course AS cc INNER JOIN course AS c ON cc.course_id = c.id" +
                    "WHERE cc.career_code = :careerCode", CareerCourse.class
        );
        List<CareerCourse> list = query.getResultList();
        Map<Integer, List<CareerCourse>> result = new HashMap<>();
        for (CareerCourse careerCourse : list) {
            if (!result.containsKey(careerCourse.getYear())) {
                result.put(careerCourse.getYear(), new ArrayList<>());
            }
            result.get((careerCourse.getYear())).add(careerCourse);
        }
        return result;
    }
}
