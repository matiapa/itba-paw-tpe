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

    private static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, rowNum) ->
            new Course(
                rs.getString("id"),
                rs.getString("name")
            );

    @Autowired
    public CourseDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
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
    public List<Course> findByCareer(int careerId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course JOIN career_course ON id=course_id " +
                "WHERE career_id='%d'", careerId),
            COURSE_ROW_MAPPER
        );
    }

    @Override
    public List<Course> findByCareer(int careerId, int limit) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course JOIN career_course ON id=course_id " +
                "WHERE career_id='%d' ORDER BY id LIMIT %d", careerId, limit),
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