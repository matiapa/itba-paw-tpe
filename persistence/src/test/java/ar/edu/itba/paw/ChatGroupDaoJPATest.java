package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Announcement;
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
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static ar.edu.itba.paw.TestUtils.set;

@Transactional
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
        ChatGroup chatGroup = chatGroupDao.addGroup("ChatGroup test", career,
                "https://test.com", user, null, ChatGroup.ChatPlatform.whatsapp);


        Assert.assertEquals("ChatGroup test", chatGroup.getName());
        Assert.assertEquals("https://test.com", chatGroup.getLink());
        Assert.assertEquals(ChatGroup.ChatPlatform.whatsapp, chatGroup.getPlatform());
    }

    @Test
    public void testDelete() throws ParseException {
        Assert.assertEquals(1, chatGroupDao.getSize(career, ChatGroup.ChatPlatform.whatsapp, null, null));
        chatGroupDao.delete(chatGroupDao.findById(1).get());
        Assert.assertEquals(0, chatGroupDao.getSize(career, ChatGroup.ChatPlatform.whatsapp, null, null));

    }

    @Test
    public void testGetSize() throws ParseException {

        Assert.assertEquals(1, chatGroupDao.getSize(career, ChatGroup.ChatPlatform.whatsapp, null, null));
    }

    @Test
    public void testFindByCareer() {

        List<ChatGroup> chatGroups = chatGroupDao.findByCareer(career, ChatGroup.ChatPlatform.whatsapp, null, null, 0, 5);


        Assert.assertEquals(1, chatGroups.size());
        Assert.assertEquals(Integer.valueOf(1), chatGroups.get(0).getId());
        Assert.assertEquals("testName", chatGroups.get(0).getName());
        Assert.assertEquals("testLink", chatGroups.get(0).getLink());
    }

    @Test
    public void testFindById() {

        Optional<ChatGroup> chatGroupOptional = chatGroupDao.findById(1);

        Assert.assertTrue(chatGroupOptional.isPresent());
        Assert.assertEquals(Integer.valueOf(1), chatGroupOptional.get().getId());
        Assert.assertEquals("S", chatGroupOptional.get().getCareerCode().getCode());
        Assert.assertEquals("testName", chatGroupOptional.get().getName());
        Assert.assertEquals("testLink", chatGroupOptional.get().getLink());
    }

}