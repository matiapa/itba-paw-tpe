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
    private final SimpleJdbcInsert jdbcInsertVerificationCode;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) ->
        new User(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("surname"),
            rs.getString("email"),
            rs.getString("career_code")
        );

    private static final RowMapper<Integer> VERIFICATION_CODE_MAPPER = (rs, rowNum) ->
            rs.getInt("verification_code");


    @Autowired
    public UserDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert= new SimpleJdbcInsert(ds)
                .withTableName("users");
        this.jdbcInsertFavCourses= new SimpleJdbcInsert(ds).withTableName("fav_course");
        this.jdbcInsertVerificationCode= new SimpleJdbcInsert(ds).withTableName("user_verification");
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
    private void createVerificationCode(int id) {

        final Map<String, Object> args = new HashMap<>();
        Random random = new Random();
        int code=random.nextInt(1000000);
        args.put("verification_code",code );
        args.put("user_id", id);
        jdbcInsertVerificationCode.execute(args);
    }
    @Override
    public User registerUser(int id, String name, String surname, String email,String password_hash, String career_code, List<String> courses) {


        final Map<String,Object> args = new HashMap<>();
        args.put("id",id);
        args.put("name",name);
        args.put("surname",surname);
        args.put("email",email);
        args.put("password_hash",password_hash);
        args.put("career_code",career_code);
        args.put("signup_date",new Date());
        args.put("verified",false);

        final Number userID = jdbcInsert.execute(args);

        for (String course:courses ) {
            addFavouriteCourse(id,course);
        }

        createVerificationCode(id);

        return new User(id,name,surname,email,career_code);
    }

    @Override
    public boolean verifyEmail(int user_id,int verification_code) {

        Optional<Integer> db_verification_code=
                jdbcTemplate.query(
                String.format("Select * FROM user_verification WHERE user_id=%d",user_id),
                VERIFICATION_CODE_MAPPER
        ).stream().findFirst();

        if (!db_verification_code.isPresent())
            return false;
        if (db_verification_code.get()!=verification_code)
            return false;

        jdbcTemplate.execute(String.format("DELETE FROM user_verification WHERE user_id=%d",user_id));
        jdbcTemplate.execute(String.format("UPDATE users SET verified='true' WHERE user_id=%d",user_id));

        return true;
    }

    @Override
    public int getVerificationCode(String email) {
        Optional<User>userOptional=findByEmail(email);
        if (!userOptional.isPresent())
            return -1;

        Optional<Integer> db_verification_code=
                jdbcTemplate.query(
                        String.format("Select * FROM user_verification WHERE user_id=%s",userOptional.get().getId()),
                        VERIFICATION_CODE_MAPPER
                ).stream().findFirst();

        return db_verification_code.orElse(-1);

    }



}
