package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Content;
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
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("course_content").usingGeneratedKeyColumns("id","creation_date");
    }

    @Override
    public List<Content> findByCourse(String courseId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course_content WHERE course_id='%s'", courseId),
                contentRowMapper
        );
    }

    @Override
    public List<Content> findByCourse(String courseId, int offset, int limit){
        return jdbcTemplate.query(
            String.format("SELECT * FROM course_content WHERE course_id='%s' "+
                "ORDER BY id OFFSET %d LIMIT %d", courseId, offset, limit),
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
    public List<Content> findContent(String courseId, Content.ContentType contentType, Date minDate, Date maxDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("SELECT * FROM course_content WHERE course_id='%s'",courseId));
        if (contentType!= null)
            stringBuilder.append(String.format(" AND content_type='%s'", contentType.toString().replace("_", "-")));
        if (minDate!= null)
            stringBuilder.append(String.format(" AND creation_date>='%s'", minDate));
        if (maxDate!= null)
            stringBuilder.append(String.format(" AND creation_date<='%s'", maxDate));


        return jdbcTemplate.query(stringBuilder.toString(),contentRowMapper);
    }

    @Override
    public boolean createContent(String name, String link, String courseId, String description, String contentType, Date contentDate, User user) {
        final Map<String,Object> args= new HashMap<>();
        args.put("name",name);
        args.put("link",link);
        args.put("submitted_by",user.getId());
        args.put("course_id",courseId);
        args.put("description",description);
        args.put("content_type",contentType);
        args.put("content_date",contentDate);

        return jdbcInsert.execute(args)==1;




    }

    @Override
    public int getSize(String courseId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM course_content WHERE course_id= ?",
                new Object[]{courseId}, Integer.class);

    }

    public Optional<Content> findById(String id) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM course_content WHERE id='%s'", id),
                contentRowMapper
        ).stream().findFirst();
    }

}