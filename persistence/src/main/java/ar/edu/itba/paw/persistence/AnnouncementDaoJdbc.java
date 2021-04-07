package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Repository
public class AnnouncementDaoJdbc implements AnnouncementDao {

    @Autowired private UserDao userDao;

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Announcement> announcementRowMapper = (rs, rowNum) -> {
        final Optional<User> userOpt = userDao.findById(rs.getInt("submitted_by"));
        if(!userOpt.isPresent())
            throw new NoSuchElementException();

        return new Announcement(
            rs.getInt("id"),
            userOpt.get(),
            rs.getString("title"),
            rs.getString("summary"),
            rs.getString("content"),
            rs.getDate("creation_date"),
            rs.getDate("expiry_date")
        );
    };

    @Autowired
    public AnnouncementDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Announcement> findGeneral() {
        return jdbcTemplate.query(
            "SELECT * FROM announcement WHERE career_id IS NULL AND course_id IS NULL",
                announcementRowMapper
        );
    }

    @Override
    public List<Announcement> findByCourse(String courseId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM announcement WHERE course_id='%s'", courseId),
                announcementRowMapper
        );
    }

    @Override
    public List<Announcement> findByCareer(int careerId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM announcement WHERE career_id='%d'", careerId),
                announcementRowMapper
        );
    }

    @Override
    public Optional<Announcement> findById(int id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM announcement WHERE id='%d'", id),
                announcementRowMapper
        ).stream().findFirst();
    }

}