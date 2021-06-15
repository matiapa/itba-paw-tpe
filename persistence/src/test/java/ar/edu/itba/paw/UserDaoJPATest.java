package ar.edu.itba.paw;

import java.util.Arrays;
import java.util.Optional;

import javax.sql.DataSource;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
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

@Rollback
@Sql("classpath:populators/course_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoJPATest {

    @Autowired private UserDaoJPA userDao;
    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private final String blob = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAAACXBIWXMAAAsSAAALEgCl2k5qAAACwklEQVR4nO3TQQ3AQAzAsKs0/pQ7GH3ERpBP3oOwmZnrBjjzGYAyA5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSJvdvW6AMz980AnubVk09AAAAABJRU5ErkJggg==";


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

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

    @Test
    public void testRegisterUser()
    {
        Career career = new Career();
        career.setCode("S");
        Course course = new Course();
        course.setId("1.1");
        User user = new User(0, "John", "Doe", "jd@gmail.com", "12345678", career);

        userDao.registerUser(3, "User 3", "Surname", "usr3@test.com", "password", career, Arrays.asList(course));
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "id=3");
        Assert.assertEquals(1, count);

        Optional<Integer> code = userDao.getVerificationCode(user);

        Assert.assertTrue(userDao.verifyEmail(user, code.isPresent()? code.get() : 0));
    }
/*          Ya no existe el metodo
    @Test
    public void testProfilePicture()
    {

        Optional<User> user = userDao.findById(1);

        userDao.setPicture(user.isPresent()? user.get() : null, blob);
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(blob, user.get().getProfileImgB64());
    }*/
}