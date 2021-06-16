package ar.edu.itba.paw;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.sql.DataSource;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.jpa.CareerDaoJPA;
import ar.edu.itba.paw.persistence.jpa.CourseDaoJPA;
import ar.edu.itba.paw.persistence.jpa.UserDaoJPA;
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
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.models.User;
import org.springframework.transaction.annotation.Transactional;

import static ar.edu.itba.paw.TestUtils.set;

@Transactional
@Rollback
@Sql("classpath:populators/user_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoJPATest {

    @Autowired private UserDaoJPA userDao;

    @Test
    public void testFindById(){
        Optional<User> userOptional = userDao.findById(1);

        Assert.assertTrue(userOptional.isPresent());
        User user = userOptional.get();

        Assert.assertEquals("User 1", user.getName());
        Assert.assertEquals("Surname", user.getSurname());
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testFindByEmail()
    {
        Optional<User> userOptional = userDao.findByEmail("usr1@test.com");

        Assert.assertTrue(userOptional.isPresent());
        User user = userOptional.get();

        Assert.assertEquals("User 1", user.getName());
        Assert.assertEquals("Surname", user.getSurname());
        Assert.assertEquals(1, user.getId());

        userOptional = userDao.findByEmail("non-existant@test.com");
        Assert.assertFalse(userOptional.isPresent());
    }

}
