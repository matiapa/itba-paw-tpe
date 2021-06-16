package ar.edu.itba.paw;


import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jpa.CareerDaoJPA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static ar.edu.itba.paw.TestUtils.set;

@Sql("classpath:populators/career_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class CareerDaoJPATest {

    @Autowired
    private CareerDaoJPA careerDao;

    @Test
    public void testFindById() {
        Optional<Career> careerOptional = careerDao.findByCode("S");

        Assert.assertTrue(careerOptional.isPresent());
        Assert.assertEquals("Career 1", careerOptional.get().getName());
    }

    @Test
    public void testFindAll() {
        List<Career> careers = careerDao.findAll();

        Assert.assertEquals(4, careers.size());
        Assert.assertEquals("Career 1", careers.get(0).getName());
    }

}