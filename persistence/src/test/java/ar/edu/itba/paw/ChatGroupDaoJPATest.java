package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jpa.CareerDaoJPA;
import ar.edu.itba.paw.persistence.jpa.ChatGroupDaoJPA;
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

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static ar.edu.itba.paw.TestUtils.set;

@Rollback
@Sql("classpath:populators/chat_group_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ChatGroupDaoJPATest {

    @Autowired private ChatGroupDaoJPA chatGroupDao;

    @Autowired private CareerDaoJPA careerDao;
    @Autowired private UserDaoJPA userDao;

    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private User user;
    private Career career;
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);

        user = new User();
        career = new Career();

        set(user, "id",1);
        set(career, "code", "S");
    }


    @Test
    public void testAddGroup() throws ParseException {
        Date creationDate = new SimpleDateFormat("dd-MM-yyyy").parse("11-05-2018");
        ChatGroup chatGroup = chatGroupDao.addGroup("ChatGroup test", career,
                "https://test.com", user, creationDate, ChatGroup.ChatPlatform.whatsapp);


        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "announcement",
                String.format("id=%d", chatGroup.getId()));

        Assert.assertEquals(1, count);
    }

    @Test
    public void testGetSize() throws ParseException {
  /*
        Date creationDate = new SimpleDateFormat("dd-MM-yyyy").parse("11-05-2018");
        for (int i = 0; i < 10; i++){
            ChatGroup chatGroup = chatGroupDao.addGroup("ChatGroup test", career.isPresent()? career.get() : null,
                    "https://test.com", user.isPresent()? user.get() : null, creationDate, ChatGroup.ChatPlatform.whatsapp);
        }
*/
        int size = chatGroupDao.getSize(career, ChatGroup.ChatPlatform.whatsapp, 2018, 1);
        Assert.assertEquals(1, size);
    }

    @Test
    public void testFindByCareer() throws ParseException {
        /*
        Date creationDate = new SimpleDateFormat("dd-MM-yyyy").parse("11-05-2018");
        for (int i = 0; i < 10; i++){
            ChatGroup chatGroup = chatGroupDao.addGroup("ChatGroup test", career,
                    "https://test.com", user, creationDate, ChatGroup.ChatPlatform.whatsapp);
        }
*/
        List<ChatGroup> chatGroups = chatGroupDao.findByCareer(career, ChatGroup.ChatPlatform.whatsapp, 2018, 1, 0, 5);

        Assert.assertEquals(5, chatGroups.size());
        Assert.assertEquals(1, Optional.ofNullable(chatGroups.get(0).getId()));
        Assert.assertEquals("ChatGroup test", chatGroups.get(0).getName());
        Assert.assertEquals(2, Optional.ofNullable(chatGroups.get(1).getId()));
    }

    @Test
    public void testFindById() throws ParseException {
        Optional<Career> career = careerDao.findByCode("S");
        Optional<User> user = userDao.findById(0);

        Date creationDate = new SimpleDateFormat("dd-MM-yyyy").parse("11-05-2018");
        ChatGroup chatGroup = chatGroupDao.addGroup("ChatGroup test", career.isPresent()? career.get() : null,
                "https://test.com", user.isPresent()? user.get() : null, creationDate, ChatGroup.ChatPlatform.whatsapp);


        Optional<ChatGroup> chatGroupOptional = chatGroupDao.findById(1);

        Assert.assertTrue(chatGroupOptional.isPresent());
        Assert.assertEquals(1, Optional.ofNullable(chatGroupOptional.get().getId()));
        Assert.assertEquals("S", chatGroupOptional.get().getCareerCode());
        Assert.assertEquals("ChatGroup test", chatGroupOptional.get().getName());
        Assert.assertEquals("https://test.com", chatGroupOptional.get().getLink());
    }

}