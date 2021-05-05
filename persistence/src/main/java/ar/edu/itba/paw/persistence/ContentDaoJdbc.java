package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Content.ContentType;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ContentDaoJdbc implements ContentDao{

    @Autowired private UserDao userDao;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<Content> contentRowMapper = (rs, rowNum) -> {
        final Optional<User> submitterOpt = userDao.findById(rs.getInt("submitted_by"));
        if(!submitterOpt.isPresent())
            throw new NoSuchElementException();

        return new Content(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("link"),
            rs.getString("description"),
            submitterOpt.get(),
            rs.getString("owner_email"),
            rs.getDate("creation_date"),
            rs.getDate("content_date"),
            ContentType.valueOf(rs.getString("content_type").trim())
        );
    };

    @Autowired
    public ContentDaoJdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("course_content")
            .usingColumns("name", "link", "submitted_by", "owner_email", "course_id", "description", "content_type", "content_date")
            .usingGeneratedKeyColumns("id");
    }

    private String queryBuilder(String courseId, ContentType contentType, Date minDate, Date maxDate, Integer offset, Integer limit){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(
            "SELECT %s FROM course_content WHERE course_id='%s'",
            offset!=null ? "*" : "count(*)", courseId
        ));

        if (contentType!= null)
            stringBuilder.append(String.format(" AND content_type='%s'", contentType.toString().replace("_", "-")));
        if(minDate!=null || maxDate!=null)
            stringBuilder.append(" AND content_date IS NOT NULL");
        if (minDate!= null)
            stringBuilder.append(String.format(" AND content_date>='%s'", minDate));
        if (maxDate!= null)
            stringBuilder.append(String.format(" AND content_date<='%s'", maxDate));

        if(offset != null && limit != null)
            stringBuilder.append(String.format(" ORDER BY creation_date DESC OFFSET %d LIMIT %d", offset, limit));

        return stringBuilder.toString();
    }

    @Override
    public List<Content> findContent(String courseId, ContentType contentType, Date minDate, Date maxDate, int offset, int limit){
        return jdbcTemplate.query(
            queryBuilder(courseId, contentType, minDate, maxDate, offset, limit),
            contentRowMapper
        );
    }

    @Override
    public boolean createContent(String name, String link, String courseId, String description, String contentType, Date contentDate, User user) {
        final Map<String,Object> args= new HashMap<>();

        args.put("name",name);
        args.put("link",link);
        args.put("submitted_by",user.getId());
        args.put("owner_email", user.getEmail());
        args.put("course_id",courseId);
        args.put("description",description);
        args.put("content_type",contentType);
        args.put("content_date",contentDate);

        return jdbcInsert.execute(args)==1;
    }

    @Override
    public int getSize(String courseId, ContentType contentType, Date minDate, Date maxDate) {
        return jdbcTemplate.queryForObject(
            queryBuilder(courseId, contentType, minDate, maxDate, null, null),
            Integer.class
        );
    }

    public Optional<Content> findById(String id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course_content WHERE id='%s'", id),
                contentRowMapper
        ).stream().findFirst();
    }

    @Override
    public void delete(int id){
        jdbcTemplate.execute(
                String.format("DELETE FROM course_content WHERE id=%d", id)
        );
    }

}