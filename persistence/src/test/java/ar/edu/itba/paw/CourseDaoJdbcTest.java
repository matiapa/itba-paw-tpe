package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CourseDaoJdbc;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:course_populate.sql")
public class CourseDaoJdbcTest {


    @Autowired
    private DataSource ds;

    @Autowired
    private CourseDaoJdbc courseDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testFindFavourites() {
        List<Course> favCourses = courseDao.findFavourites(1);

        Assert.assertEquals(1, favCourses.size());
    }
}
