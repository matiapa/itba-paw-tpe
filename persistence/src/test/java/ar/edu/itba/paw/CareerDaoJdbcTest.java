package ar.edu.itba.paw;


import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.persistence.jpa.CareerDaoJPA;
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
    private CareerDaoJPA careerDao;

    @Test
    public void testFindById() {
        Optional<Career> career = careerDao.findByCode("S");

        Assert.assertTrue(career != null);
        Assert.assertEquals("Career 1", career.get().getName());
    }

    @Test
    public void testFindAll() {
        List<Career> careers = careerDao.findAll();

        Assert.assertEquals(4, careers.size());
        Assert.assertEquals("Career 1", careers.get(0).getName());
    }

}