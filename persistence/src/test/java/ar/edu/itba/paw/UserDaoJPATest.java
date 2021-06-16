package ar.edu.itba.paw;

import java.util.Optional;
import ar.edu.itba.paw.persistence.jpa.UserDaoJPA;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.paw.models.User;
import org.springframework.transaction.annotation.Transactional;


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