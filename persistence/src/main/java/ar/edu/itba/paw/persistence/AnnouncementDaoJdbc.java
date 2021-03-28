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
            new Announcement(rs.getString("title"));


    @Override
    public List<Announcement> findAll() {
        return jdbcTemplate.query("SELECT * FROM announcement", ANNOUNCEMENT_ROW_MAPPER);
    }

}
