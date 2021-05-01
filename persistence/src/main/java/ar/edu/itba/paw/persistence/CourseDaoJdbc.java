package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Repository
public class CourseDaoJdbc implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

     static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, rowNum) ->
        new Course(
            rs.getString("id"),
            rs.getString("name"),
            rs.getInt("credits")
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
    public List<Course> findByCareer(String careerCode) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course JOIN career_course ON id=course_id " +
                "WHERE career_code='%s'", careerCode),
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

}