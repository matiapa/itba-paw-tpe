package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Repository
public class UserDaoJdbc implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) ->
        new User(
            rs.getInt("id"),
            rs.getString("name"),
                rs.getString("surname"),
                rs.getString("email"),
                rs.getInt("career_id")
        );



    @Autowired
    public UserDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert= new SimpleJdbcInsert(ds)
                .withTableName("users");
    }

    @Override
    public Optional<User> findById(int id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM users WHERE id=%d", id),
                USER_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public User registerUser(int id, String name, String surname, String email,int career_id) {
        final Map<String,Object> args = new HashMap<>();
        args.put("id",id);
        args.put("name",name);
        args.put("surname",surname);
        args.put("email",email);
        args.put("career_id",career_id);
        args.put("signup_date",new Date());

        final Number userID = jdbcInsert.execute(args);

        return new User(id,name,surname,email,career_id);

    }
}
