package ar.edu.itba.paw.persistence.jdbc;

import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;


@Repository
public class CourseDaoJdbc implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

     static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, rowNum) ->
        new Course(
            rs.getString("id"),
            rs.getString("name"),
            rs.getInt("credits")
        );

    private static final RowMapper<CareerCourse> CAREER_COURSE_ROW_MAPPER = (rs, rowNum) ->
        new CareerCourse(
                rs.getString("id"),
                rs.getString("name"),
                rs.getInt("credits"),
                rs.getInt("semester")
        );


    @Autowired
    public CourseDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM course",
                COURSE_ROW_MAPPER
        );
    }

    @Override
    public List<Course> findFavourites(int userId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM fav_course JOIN course ON course_id=id " +
                "WHERE user_id=%d", userId),
            COURSE_ROW_MAPPER
        );
    }

    @Override
    public List<Course> findFavourites(int userId, int limit) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM fav_course JOIN course ON course_id=id " +
                "WHERE user_id=%d ORDER BY id LIMIT %d", userId, limit),
            COURSE_ROW_MAPPER
        );
    }


    @Override
    public List<Course> findByCareer(String careerCode, int limit) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course JOIN career_course ON id=course_id " +
                "WHERE career_code='%s' ORDER BY id LIMIT %d", careerCode, limit),
            COURSE_ROW_MAPPER
        );
    }

    @Override
    public Optional<Course> findById(String id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course WHERE id='%s'", id),
            COURSE_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public Map<Integer, List<CareerCourse>> findByCareerSemester(String careerCode) {
        List<CareerCourse> list = jdbcTemplate.query(
                String.format("SELECT * FROM career_course  JOIN course  on career_course.course_id = course.id WHERE career_code='%s'", careerCode),
                CAREER_COURSE_ROW_MAPPER
        );
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