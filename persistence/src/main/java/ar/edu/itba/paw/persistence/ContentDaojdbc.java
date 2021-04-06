package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ContentDaojdbc implements ContentDao{

    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ContentDaojdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    private static final RowMapper<Content> CONTENT_ROW_MAPPER = (rs, rowNum) ->
        new Content(
            rs.getInt("id"),
            rs.getString("course_id"),
            rs.getString("name"),
            rs.getString("submitted_by"), // OJO aca TODO hacer la busqueda del nombre del usuario en vez del id
            rs.getString("link"),
            rs.getString("description")
        );

    @Override
    public  List<Content> findByCourse(String courseId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM content_source WHERE course_id='%s'", courseId),
            CONTENT_ROW_MAPPER
        );
    }

    @Override
    public  List<Content> findByCourse(String courseId, int limit){
        return jdbcTemplate.query(
            String.format("SELECT * FROM content_source WHERE course_id='%s' "+
                "ORDER BY id LIMIT %d", courseId, limit),
            CONTENT_ROW_MAPPER
        );
    }

    @Override
    public Optional<Content> findById(String id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM content_source WHERE id='%s'", id),
            CONTENT_ROW_MAPPER
        ).stream().findFirst();
    }

}