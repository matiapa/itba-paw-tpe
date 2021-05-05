package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@Repository
public class CareerDaoJdbc implements CareerDao{

    private final JdbcTemplate jdbcTemplate;

    static final RowMapper<Career> CAREER_ROW_MAPPER = (rs, rowNum) ->
        new Career(
            rs.getString("name"),
            rs.getString("code")
        );

    @Autowired
    public CareerDaoJdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    @Override
    public Optional<Career> findByCode(String code) {
        return jdbcTemplate.query(
            String.format(
                "SELECT * FROM career WHERE code='%s'",
                code
            ),
            CAREER_ROW_MAPPER
        ).stream().findFirst();
    }


    @Override
    public List<Career> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM career", CAREER_ROW_MAPPER
        );
    }

}