package ar.edu.itba.paw;


import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CareerDaoJdbc;
import ar.edu.itba.paw.persistence.CourseDaoJdbc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@Rollback
@Sql("classpath:populators/career_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CareerDaoJdbcTest {

    @Autowired
    private CareerDaoJdbc careerDao;

    @Test
    public void testFindById() {
        Optional<Career> careerOptional = careerDao.findByCode("S");

        Assert.assertTrue(careerOptional.isPresent());
        Assert.assertEquals("Career 1", careerOptional.get().getName());
    }
    @Test
    public void testFindAll() {
        List<Career> careers = careerDao.findAll();

        Assert.assertEquals(1,careers.size());
        Assert.assertEquals("Career 1", careers.get(0).getName());
        //TODO: assert code
    }
}
