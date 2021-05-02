package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Repository
public class StatisticsDaoJdbc implements StatisticsDao {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public StatisticsDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Map<Entity, Integer> newContributions(User loggedUser) {
        String query = String.format("WITH last_log AS( SELECT max(date) date FROM login_activity WHERE user_id='%d' )\n" +
            "SELECT 'announcement' entity, count(*) FROM announcement,last_log WHERE creation_date > last_log.date\n" +
            "UNION SELECT 'poll' entity, count(*) FROM poll,last_log WHERE creation_date > last_log.date\n" +
            "UNION SELECT 'chat_group' entity, count(*) FROM chat_group,last_log WHERE creation_date > last_log.date\n" +
            "UNION SELECT 'course_content' entity, count(*) FROM course_content,last_log WHERE creation_date > last_log.date",
            loggedUser.getId()
        );

        Map<Entity, Integer> map = new HashMap<>();
        jdbcTemplate.query(query, (rs, rn) -> map.put(
            Entity.valueOf(rs.getString("entity").toUpperCase(Locale.ROOT)),
            rs.getInt("count")
        ));

        return map;
    }

    @Override
    public Map<Career, Integer> contributionsByCareer() {
        String query = "SELECT *, (\n" +
        "   SELECT SUM(count) FROM (\n" +
        "       SELECT count(*) count FROM announcement WHERE career_code=career.code\n" +
        "       UNION SELECT count(*) FROM poll WHERE career_code=career.code\n" +
        "       UNION SELECT count(*) FROM chat_group WHERE career_code=career.code\n" +
        "   ) AS counts\n" +
        ") count FROM career ORDER BY count DESC LIMIT 5";

        Map<Career, Integer> map = new HashMap<>();
        jdbcTemplate.query(query, (rs, rn) -> map.put(
            CareerDaoJdbc.CAREER_ROW_MAPPER.mapRow(rs, rn),
            rs.getInt("count")
        ));

        return map;
    }

    @Override
    public Map<Date, Integer> contributionsByDate() {
        String query = "SELECT creation_date date, count(*) FROM (\n" +
        " SELECT 'a', id, creation_date FROM announcement\n" +
        " UNION SELECT 'p', id, creation_date FROM poll\n" +
        " UNION SELECT 'g', id, creation_date FROM chat_group\n" +
        " UNION SELECT 'c', id, creation_date FROM course_content\n" +
        ") contributions GROUP BY creation_date";

        Map<Date, Integer> map = new HashMap<>();
        jdbcTemplate.query(query, (rs, rn) -> map.put(
            rs.getDate("date"),
            rs.getInt("count")
        ));

        return map;
    }

    @Override
    public Map<User, Integer> topUsersByContributions() {
        String query = "SELECT *, (\n" +
        "    SELECT SUM(count) FROM (\n" +
        "        SELECT count(*) count FROM announcement WHERE submitted_by=users.id\n" +
        "        UNION SELECT count(*) FROM poll WHERE submitted_by=users.id\n" +
        "        UNION SELECT count(*) FROM chat_group WHERE submitted_by=users.id\n" +
        "        UNION SELECT count(*) FROM course_content WHERE submitted_by=users.id\n" +
        "    ) AS counts\n" +
        ") count FROM users ORDER BY count DESC LIMIT 10";

        Map<User, Integer> map = new HashMap<>();
        jdbcTemplate.query(query, (rs, rn) -> map.put(
            UserDaoJdbc.USER_ROW_MAPPER_ST.mapRow(rs, rn),
            rs.getInt("count")
        ));

        return map;
    }

    @Override
    public Map<Course, Integer> topCoursesByContributions() {
        String query = "SELECT *, (\n" +
        "    SELECT SUM(count) FROM (\n" +
        "       SELECT count(*) count FROM announcement WHERE course_id=course.id\n" +
        "       UNION SELECT count(*) FROM poll WHERE course_id=course.id\n" +
        "       UNION SELECT count(*) FROM course_content WHERE course_id=course.id\n" +
        "   ) AS counts\n" +
        ") count FROM course ORDER BY count DESC LIMIT 10";

        Map<Course, Integer> map = new HashMap<>();
        jdbcTemplate.query(query, (rs, rn) -> map.put(
            CourseDaoJdbc.COURSE_ROW_MAPPER.mapRow(rs, rn),
            rs.getInt("count")
        ));

        return map;
    }

}