package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;


@Repository
public class UserDaoJdbc implements UserDao {

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) ->
        new User(
            rs.getInt("id"),
            rs.getString("name")
        );

    @Override
    public Optional<User> findById(int id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM users WHERE id=%d", id),
                USER_ROW_MAPPER
        ).stream().findFirst();
    }
}
