package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;


@Repository
public class UserDaoJdbc implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertFavCourses;

    private final RowMapper<User> USER_ROW_MAPPER;

    @Autowired
    public UserDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert= new SimpleJdbcInsert(ds)
                .withTableName("users");
        this.jdbcInsertFavCourses= new SimpleJdbcInsert(ds).withTableName("fav_course");

        USER_ROW_MAPPER = (rs, rowNum) -> {
            List<String> permissions = jdbcTemplate.query(
                String.format("SELECT * FROM permission WHERE user_id=%d", rs.getInt("id")),
                (rs2, rowNum2) -> rs.getString("entity")+"."+rs.getString("action")
            );

            return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("profile_picture"),
                rs.getDate("signup_date"),
                permissions,
                rs.getString("career_code")
            );
        };
    }

    @Override
    public Optional<User> findById(int id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM users WHERE id=%d", id),
                USER_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM users WHERE email='%s'", email),
                USER_ROW_MAPPER
        ).stream().findFirst();
    }

    private void addFavouriteCourse(int id,String courseId){
        final Map<String,Object> args = new HashMap<>();
        args.put("course_id",courseId);
        args.put("user_id",id);
        jdbcInsertFavCourses.execute(args);

    }

    @Override
    public User registerUser(int id, String name, String surname, String email, String career_code, List<String> courses) {
        final Map<String,Object> args = new HashMap<>();

        args.put("id",id);
        args.put("name",name);
        args.put("surname",surname);
        args.put("email",email);
        args.put("career_code",career_code);
        args.put("signup_date",new Date());

        final Number userID = jdbcInsert.execute(args);

        for (String course:courses ) {
            addFavouriteCourse(id,course);
        }

        return findById(userID.intValue())
            .orElseThrow(() -> new RuntimeException("Could not register user"));
    }

    @Override
    public void setProfilePicture(String pictureDataURI, int userId) {
        jdbcTemplate.update(
        "UPDATE users SET profile_picture=? WHERE id=?",
            pictureDataURI, userId
        );
    }

}
