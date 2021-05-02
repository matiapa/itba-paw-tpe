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


    private List<Announcement> findAnnouncement(
        StringBuilder baseQuery, boolean showSeen, int userId, int offset, int limit
    ){
        if(!showSeen) {
            baseQuery.append(String.format(
                " AND NOT EXISTS (SELECT * FROM announcement_seen WHERE announcement_id=id"
                + " AND user_id=%d)", userId
            ));
        }

        baseQuery.append(String.format(" OFFSET %d LIMIT %d", offset, limit));

        System.out.println(baseQuery.toString());

        return jdbcTemplate.query(baseQuery.toString(), announcementRowMapper);
    }

    @Override
    public List<Announcement> findRelevant(int userId, int limit) {
        String query = "SELECT * FROM announcement\n" +
        "WHERE (expiry_date IS NULL OR expiry_date>now())\n" +
        "AND (course_id IS NULL OR course_id IN (SELECT course_id FROM fav_course WHERE user_id='%d'))\n" +
        "AND (career_code IS NULL OR career_code = (SELECT u.career_code FROM users u WHERE u.id='%d'))\n" +
        "ORDER BY creation_date DESC LIMIT %d";

        return jdbcTemplate.query(
            String.format(query, userId, userId, limit),
            announcementRowMapper
        );
    }

    @Override
    public List<Announcement> findGeneral(boolean showSeen, int userId, int offset, int limit) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM announcement WHERE career_code IS NULL AND course_id IS NULL");
        return findAnnouncement(query, showSeen, userId, offset, limit);
    }

    @Override
    public List<Announcement> findByCourse(String courseId, boolean showSeen, int userId, int offset, int limit) {
        StringBuilder query = new StringBuilder();
        query.append(String.format("SELECT * FROM announcement WHERE course_id='%s'", courseId));
        return findAnnouncement(query, showSeen, userId, offset, limit);
    }

    @Override
    public List<Announcement> findByCareer(String careerCode, boolean showSeen, int userId, int offset, int limit) {
        StringBuilder query = new StringBuilder();
        query.append(String.format("SELECT * FROM announcement WHERE career_code='%s'", careerCode));
        return findAnnouncement(query, showSeen, userId, offset, limit);
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
    public Announcement create(String title, String summary, String content, String careerCode,
                               String courseId, Date expiryDate, Integer submittedBy) {
        return jdbcTemplate.queryForObject(
                "INSERT INTO announcement(title, summary, content, career_code, course_id, expiry_date, " +
                        "submitted_by) VALUES (?,?,?,?,?,?,?) RETURNING *",
                new Object[]{title, summary, content, careerCode, courseId, expiryDate, submittedBy},
                announcementRowMapper
        );
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
    public void delete(int id){
        jdbcTemplate.execute(
            String.format("DELETE FROM announcement WHERE id=%d", id)
        );
    }

}