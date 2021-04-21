package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class CareerDaoJdbc implements CareerDao{

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Career> CAREER_ROW_MAPPER = (rs, rowNum) ->
        new Career(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("code")
        );


    private static final RowMapper<CareerCourse> CAREER_COURSE_ROW_MAPPER = (rs, rowNum) ->
            new CareerCourse(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getInt("credits"),
                    rs.getInt("semester")

            );


    @Autowired
    public CareerDaoJdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Map<Integer, List<CareerCourse>> findByCareer(int careerId) {
        List<CareerCourse> list = jdbcTemplate.query(
                String.format("SELECT * FROM career_course  JOIN course  on career_course.course_id = course.id WHERE career_id='%s'", careerId),
                CAREER_COURSE_ROW_MAPPER
        );
        Map<Integer, List<CareerCourse>> result = new HashMap<>();
        for (CareerCourse careerCourse : list) {
            if (!result.containsKey(careerCourse.getYear())) {
                result.put(careerCourse.getYear(), new ArrayList<>());
            }
            result.get((careerCourse.getYear())).add(careerCourse);
        }
        return result;
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