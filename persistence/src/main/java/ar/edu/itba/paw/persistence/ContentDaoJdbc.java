package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Content;
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
public class ContentDaoJdbc implements ContentDao{

    @Autowired private UserDao userDao;

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Content> contentRowMapper = (rs, rowNum) -> {
        final Optional<User> userOpt = userDao.findById(rs.getInt("submitted_by"));
        if(!userOpt.isPresent())
            throw new NoSuchElementException();

        return new Content(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("link"),
            rs.getString("description"),
            userOpt.get(),
            rs.getDate("creation_date")
        );
    };

    @Autowired
    public ContentDaoJdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Content> findByCourse(String courseId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM content_source WHERE course_id='%s'", courseId),
                contentRowMapper
        );
    }

    @Override
    public List<Content> findByCourse(String courseId, int limit){
        return jdbcTemplate.query(
            String.format("SELECT * FROM content_source WHERE course_id='%s' "+
                "ORDER BY id LIMIT %d", courseId, limit),
                contentRowMapper
        );
    }

    public Optional<Content> findById(String id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM content_source WHERE id='%s'", id),
                contentRowMapper
        ).stream().findFirst();
    }

}