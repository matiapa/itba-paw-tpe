package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Repository
public class AnnouncementDaoJdbc implements AnnouncementDao {

    @Autowired private UserDao userDao;

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Announcement> announcementRowMapper = (rs, rowNum) -> {
        final Optional<User> userOpt = userDao.findById(rs.getInt("submitted_by"));
        if(!userOpt.isPresent())
            throw new NoSuchElementException();

        Boolean seen = jdbcTemplate.queryForObject(
            "SELECT count(*) FROM announcement_seen WHERE announcement_id=?",
            new Object[] { rs.getInt("id") },
            (rs2, rowNum2) -> rs2.getInt("count") > 0
        );

        return new Announcement(
            rs.getInt("id"),
            userOpt.get(),
            rs.getString("title"),
            rs.getString("summary"),
            rs.getString("content"),
            rs.getDate("creation_date"),
            rs.getDate("expiry_date"),
            seen
        );
    };

    @Autowired
    public AnnouncementDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Announcement> findGeneral(int offset, int limit) {
        return jdbcTemplate.query(
                String.format("SELECT * FROM announcement WHERE career_code IS NULL AND course_id IS NULL " +
                        "OFFSET %d LIMIT %d ", offset, limit),
                announcementRowMapper
        );
    }

    @Override
    public List<Announcement> findByCourse(String courseId, int offset, int limit) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM announcement WHERE course_id='%s' OFFSET %d LIMIT %d", courseId, offset, limit),
                announcementRowMapper
        );
    }

    @Override
    public List<Announcement> findByCareer(String careerCode, int offset, int limit) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM announcement WHERE career_code='%s' OFFSET %d LIMIT %d", careerCode, offset, limit),
                announcementRowMapper
        );
    }

    @Override
    public int getSize(HolderEntity holderEntity, String code){
        switch (holderEntity){
            case career:
                return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM announcement WHERE career_code= ?",
                        new Object[] {code}, Integer.class);
            case course:
                return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM announcement WHERE course_id= ?",
                        new Object[] {code}, Integer.class);
            case general:
            default:
                return jdbcTemplate.queryForObject("SELECT COUNT(*) " +
                                "FROM announcement WHERE career_code IS NULL AND course_id IS NULL ",
                        new Object[] {}, Integer.class);
        }

    }

    @Override
    public Optional<Announcement> findById(int id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM announcement WHERE id='%d'", id),
                announcementRowMapper
        ).stream().findFirst();
    }

    @Override
    public void markSeen(int announcementId, int userId){
        jdbcTemplate.update(
        "INSERT INTO announcement_seen(announcement_id, user_id) VALUES (?,?)" +
            "ON CONFLICT (announcement_id, user_id) DO NOTHING",
            announcementId, userId
        );
    }

    @Override
    public Announcement create(String title, String summary, String content, String careerCode,
                   String courseId, Date expiryDate, Integer submittedBy) {
        return jdbcTemplate.queryForObject(
        "INSERT INTO announcement(title, summary, content, career_code, course_id, expiry_date, " +
                "submitted_by) VALUES (?,?,?,?,?,?,?) RETURNING *",
            new Object[]{title, summary, content, careerCode, courseId, expiryDate, submittedBy},
            announcementRowMapper
        );
    }

}