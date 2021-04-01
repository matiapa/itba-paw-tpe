package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CareerDaoJdbc implements CareerDao{
    @Autowired
    private DataSource ds;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CareerDaoJdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    private static final RowMapper<Career> CAREER_ROW_MAPPER = (rs, rowNum) ->
        new Career(
            rs.getInt("id"),
            rs.getString("name")
        );

    @Override
    public Optional<Career> findById(int id) {
        return jdbcTemplate.query(
            String.format(
                "SELECT * FROM career WHERE id='%d'",
                id
            ),
            CAREER_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public Career findByCourse(Course course) {
        return jdbcTemplate.query(
            String.format(
                "SELECT * FROM career JOIN course c ON career.id = c.career_id " + "WHERE c.id = '%s'",
                course.getId()
            ),
            CAREER_ROW_MAPPER
        ).stream().findFirst()
        .orElseThrow(IllegalStateException::new);

        // TODO: Is this the appropiate exception?
    }

    @Override
    public List<Career> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM career", CAREER_ROW_MAPPER
        );
    }
}
