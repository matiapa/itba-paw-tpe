package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Poll;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class PollDaoJdbc implements PollDao {
    @Autowired
    private UserDao userDao;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PollDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    private final RowMapper<Poll> rowMapper = (rs, rowNum) ->
        new Poll(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDate("creation_date"),
            rs.getDate("expiry_date"),
            userDao.findById(rs.getInt("submitted_by")).get()
        );

    @Override
    public List<Poll> findByCareer(int careerId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM poll WHERE career_id='%d'", careerId),
            rowMapper
        );
    }

    @Override
    public List<Poll> findByCourse(String courseId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM poll WHERE course_id='%s'", courseId),
            rowMapper
        );
    }

    @Override
    public Optional<Poll> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            String.format("SELECT * FROM poll WHERE id='%d'", id),
            rowMapper
        ));
    }

    @Override
    public List<Poll> findGeneral() {
        return jdbcTemplate.query(
            "SELECT * FROM poll WHERE career_id IS NULL AND course_id IS NULL",
            rowMapper
        );
    }
}
