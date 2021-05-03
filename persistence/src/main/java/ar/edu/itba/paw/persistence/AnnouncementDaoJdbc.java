package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;


@Repository
public class AnnouncementDaoJdbc implements AnnouncementDao {

    @Autowired private UserDao userDao;

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Announcement> announcementRowMapper = (rs, rowNum) -> {
        final Optional<User> userOpt = userDao.findById(rs.getInt("submitted_by"));
        if(!userOpt.isPresent())
            throw new NoSuchElementException();

        Boolean seen = jdbcTemplate.queryForObject(
            "SELECT count(*)>0 AS seen FROM announcement_seen WHERE announcement_id=?",
            new Object[] { rs.getInt("id") },
            (rs2, rowNum2) -> rs2.getBoolean("seen")
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


    private String queryBuilder(
        String baseQuery, boolean showSeen, int userId, Integer offset, Integer limit
    ){
        StringBuilder query = new StringBuilder();
        query.append(baseQuery);

        if(!showSeen) {
            query.append(String.format(
                " AND NOT EXISTS (SELECT * FROM announcement_seen WHERE announcement_id=id"
                + " AND user_id=%d)", userId
            ));
        }

        if(offset != null && limit != null){
            query.append(String.format(" OFFSET %d LIMIT %d", offset, limit));
        }

        return query.toString();
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
        String query = queryBuilder(
            "SELECT * FROM announcement WHERE career_code IS NULL AND course_id IS NULL",
            showSeen, userId, offset, limit
        );

        return jdbcTemplate.query(query, announcementRowMapper);
    }

    @Override
    public List<Announcement> findByCourse(String courseId, boolean showSeen, int userId, int offset, int limit) {
        String query = queryBuilder(
            String.format("SELECT * FROM announcement WHERE course_id='%s'", courseId),
            showSeen, userId, offset, limit
        );

        return jdbcTemplate.query(query, announcementRowMapper);
    }

    @Override
    public List<Announcement> findByCareer(String careerCode, boolean showSeen, int userId, int offset, int limit) {
        String query = queryBuilder(
            String.format("SELECT * FROM announcement WHERE career_code='%s'", careerCode),
            showSeen, userId, offset, limit
        );

        return jdbcTemplate.query(query, announcementRowMapper);
    }

    @Override
    public int getSize(EntityTarget target, String code, boolean showSeen, int userId){
        String baseQuery;

        switch (target){
            case CAREER:
                baseQuery = String.format("SELECT COUNT(*) FROM announcement WHERE career_code='%s'", code);
                break;
            case COURSE:
                baseQuery = String.format("SELECT COUNT(*) FROM announcement WHERE course_id='%s'", code);
                break;
            case GENERAL:
            default:
                baseQuery = "SELECT COUNT(*) FROM announcement WHERE career_code IS NULL AND course_id IS NULL";
        }

        return jdbcTemplate.queryForObject(
            queryBuilder(baseQuery, showSeen, userId, null, null),
            Integer.class
        );
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
        int id = (new SimpleJdbcInsert(jdbcTemplate))
            .withTableName("announcement").usingGeneratedKeyColumns("id")
            .executeAndReturnKey(new HashMap<String, Object>(){{
                put("title", title); put("summary", summary); put("content", content); put("career_code", careerCode);
                put("course_id", courseId); put("expiry_date", expiryDate); put("submitted_by", submittedBy);
                put("creation_date", null);
            }}).intValue();

        return jdbcTemplate.queryForObject(
            "SELECT * FROM announcement WHERE id=?",
            new Object[]{id}, announcementRowMapper
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
        jdbcTemplate.update("DELETE FROM announcement WHERE id=?", id);
    }

}