package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CareerDaoJdbc implements CareerDao{

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Career> CAREER_ROW_MAPPER = (rs, rowNum) ->
        new Career(
            rs.getInt("id"),
            rs.getString("name")
        );


    @Autowired
    public CareerDaoJdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

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
    public List<Career> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM career", CAREER_ROW_MAPPER
        );
    }

}