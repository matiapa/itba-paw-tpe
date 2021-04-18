package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ContentDaoJdbc implements ContentDao{

    @Autowired private UserDao userDao;

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Content> contentRowMapper = (rs, rowNum) -> {
        final Optional<User> userOpt = userDao.findById(rs.getInt("submitted_by"));
        if(!userOpt.isPresent())
            throw new NoSuchElementException();

        String contentType = rs.getString("content_type");

        return new Content(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("link"),
            rs.getString("description"),
            userOpt.get(),
            rs.getDate("creation_date"),
            rs.getDate("content_date"),
            Arrays.stream(Content.ContentType.values()).filter( t -> t.name().equals(contentType))
                    .findFirst().orElseThrow(() -> new IllegalStateException("Invalid content type found"))
        );
    };

    @Autowired
    public ContentDaoJdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Content> findByCourse(String courseId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course_content WHERE course_id='%s'", courseId),
                contentRowMapper
        );
    }

    @Override
    public List<Content> findByCourse(String courseId, int limit){
        return jdbcTemplate.query(
            String.format("SELECT * FROM course_content WHERE course_id='%s' "+
                "ORDER BY id LIMIT %d", courseId, limit),
                contentRowMapper
        );
    }

    @Override
    public List<Content> findByCourseAndType(String courseId, String contentType) {
        return jdbcTemplate.query(
                String.format("SELECT * FROM course_content WHERE course_id='%s' AND content_type='%s", courseId,contentType),
                contentRowMapper
        );
    }

    @Override
    public List<Content> findContent(String courseId, String contentType, Date minDate, Date maxDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("SELECT * FROM course_content WHERE course_id='%s'",courseId));
        if (contentType!= null)
            stringBuilder.append(String.format(" AND content_type='%s'",contentType));
        if (minDate!= null)
            stringBuilder.append(String.format(" AND creation_date>='%s'", minDate));
        if (maxDate!= null)
            stringBuilder.append(String.format(" AND creation_date<='%s'", maxDate));


        return jdbcTemplate.query(stringBuilder.toString(),contentRowMapper);
    }

    public Optional<Content> findById(String id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course_content WHERE id='%s'", id),
                contentRowMapper
        ).stream().findFirst();
    }

}