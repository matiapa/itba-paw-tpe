package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class AnnouncementDaoJdbc implements AnnouncementDao {

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AnnouncementDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    private static final RowMapper<Announcement> ANNOUNCEMENT_ROW_MAPPER = (rs, rowNum) ->
            new Announcement(
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("creation_date")
            );


    @Override
    public List<Announcement> findGeneral() {
        return jdbcTemplate.query(
            "SELECT * FROM announcement WHERE career_id IS NULL AND course_id IS NULL",
            ANNOUNCEMENT_ROW_MAPPER
        );
    }

    @Override
    public List<Announcement> findByCourse(String courseId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM announcement WHERE course_id='%s'", courseId),
            ANNOUNCEMENT_ROW_MAPPER
        );
    }

    @Override
    public List<Announcement> findByCareer(int careerId) {
        return jdbcTemplate.query(
                String.format("SELECT * FROM announcement WHERE career_id='%d'", careerId), ANNOUNCEMENT_ROW_MAPPER
        );
    }

}
