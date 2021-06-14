package ar.edu.itba.paw;

import java.util.Arrays;
import java.util.Optional;

import javax.sql.DataSource;

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
import ar.edu.itba.paw.persistence.UserDaoJdbc;

@Rollback
@Sql("classpath:populators/course_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoJPATest {

    @Autowired private UserDaoJdbc userDaoJdbc;
    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    private final String blob = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAAACXBIWXMAAAsSAAALEgCl2k5qAAACwklEQVR4nO3TQQ3AQAzAsKs0/pQ7GH3ERpBP3oOwmZnrBjjzGYAyA5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSDMAaQYgzQCkGYA0A5BmANIMQJoBSJvdvW6AMz980AnubVk09AAAAABJRU5ErkJggg==";

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testFindById(){
        Optional<User> userOptional = userDaoJdbc.findById(1);

        Assert.assertTrue(userOptional.isPresent());
        User user = userOptional.get();

        Assert.assertEquals("User 1", user.getName());
        Assert.assertEquals("Surname", user.getSurname());
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testFindByEmail()
    {
        Optional<User> userOptional = userDaoJdbc.findByEmail("usr1@test.com");

        Assert.assertTrue(userOptional.isPresent());
        User user = userOptional.get();

        Assert.assertEquals("User 1", user.getName());
        Assert.assertEquals("Surname", user.getSurname());
        Assert.assertEquals(1, user.getId());

        userOptional = userDaoJdbc.findByEmail("non-existant@test.com");
        Assert.assertFalse(userOptional.isPresent());
    }

    @Test
    public void testRegisterUser()
    {
        userDaoJdbc.registerUser(3, "User 3", "Surname", "usr3@test.com", "password", "A", Arrays.asList("1.1"));
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "id=3");
        Assert.assertEquals(1, count);

        int code = userDaoJdbc.getVerificationCode("usr3@test.com");
        Assert.assertTrue(userDaoJdbc.verifyEmail(3, code));
    }

    @Test
    public void testProfilePicture()
    {
        userDaoJdbc.setProfilePicture(blob, 1);
        Optional<User> user = userDaoJdbc.findById(1);
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(blob, user.get().getProfileImgB64());
    }
}