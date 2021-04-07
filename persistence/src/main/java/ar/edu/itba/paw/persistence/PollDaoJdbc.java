package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.Poll.PollOption;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class PollDaoJdbc implements PollDao {
    @Autowired
    private UserDao userDao;

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Poll> rowMapper = (rs, rowNum) -> {
        int id = rs.getInt("id");
        Optional<User> optUser = userDao.findById(rs.getInt("submitted_by"));
        return new Poll(
            id,
            rs.getString("name"),
            rs.getString("description"),
            rs.getTimestamp("creation_date"),
            rs.getTimestamp("expiry_date"),
            optUser.orElse(null),
            getOptions(id)
        );
    };

    private final RowMapper<PollOption> optionMapper = (rs, rowNum) ->
        new PollOption(
            rs.getInt("option_id"),
            rs.getString("option_value")
        );


    @Autowired
    public PollDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

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

    private List<PollOption> getOptions(int pollId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM poll_option WHERE poll_id='%d'", pollId),
            optionMapper
        );
    }
}
