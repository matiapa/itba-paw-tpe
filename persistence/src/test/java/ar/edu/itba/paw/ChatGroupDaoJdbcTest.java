package ar.edu.itba.paw;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.persistence.ChatGroupDaoJdbc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Rollback
@Sql("classpath:populators/chat_group_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ChatGroupDaoJdbcTest {

    @Autowired private ChatGroupDaoJdbc chatGroupDaoJdbc;

    @Test
    public void testAddGroup() throws ParseException {

        Date creationDate = new SimpleDateFormat("dd-MM-yyyy").parse("11-05-2018");
        ChatGroup chatGroup = chatGroupDaoJdbc.addGroup("ChatGroup test", "S",
                "https://test.com", 0, creationDate, ChatGroup.ChatPlatform.whatsapp);

        Assert.assertEquals("ChatGroup test", chatGroup.getName());
        Assert.assertEquals("S", chatGroup.getCareerCode());
        Assert.assertEquals("https://test.com", chatGroup.getLink());

        Assert.assertEquals("ChatGroup test", chatGroupDaoJdbc.findByCareer("S", ChatGroup.ChatPlatform.whatsapp, null, null, 0, 10).get(0).getName());
        Assert.assertEquals("S", chatGroupDaoJdbc.findByCareer("S", ChatGroup.ChatPlatform.whatsapp, null, null, 0, 10).get(0).getCareerCode());
        Assert.assertEquals("https://test.com", chatGroupDaoJdbc.findByCareer("S", ChatGroup.ChatPlatform.whatsapp, null, null, 0, 10).get(0).getLink());

    }

    @Test
    public void testGetSize() throws ParseException {

        Date creationDate = new SimpleDateFormat("dd-MM-yyyy").parse("11-05-2018");
        for (int i = 0; i < 10; i++){
            ChatGroup chatGroup = chatGroupDaoJdbc.addGroup("ChatGroup test", "S",
                    "https://test.com", 0, creationDate, ChatGroup.ChatPlatform.whatsapp);
        }

        int size = chatGroupDaoJdbc.getSize("S", ChatGroup.ChatPlatform.whatsapp, 2018, 1);
        Assert.assertEquals(10, size);
    }

    @Test
    public void testFindByCareer() throws ParseException {

        Date creationDate = new SimpleDateFormat("dd-MM-yyyy").parse("11-05-2018");
        for (int i = 0; i < 10; i++){
            ChatGroup chatGroup = chatGroupDaoJdbc.addGroup("ChatGroup test", "S",
                    "https://test.com", 0, creationDate, ChatGroup.ChatPlatform.whatsapp);
        }

        List<ChatGroup> chatGroups = chatGroupDaoJdbc.findByCareer("S", ChatGroup.ChatPlatform.whatsapp, 2018, 1, 0, 5);

        Assert.assertEquals(5, chatGroups.size());
        Assert.assertEquals(1, chatGroups.get(0).getId());
        Assert.assertEquals("ChatGroup test", chatGroups.get(0).getName());
        Assert.assertEquals(2, chatGroups.get(1).getId());
    }

    @Test
    public void testFindById() throws ParseException {

        Date creationDate = new SimpleDateFormat("dd-MM-yyyy").parse("11-05-2018");
        ChatGroup chatGroup = chatGroupDaoJdbc.addGroup("ChatGroup test", "S",
                "https://test.com", 0, creationDate, ChatGroup.ChatPlatform.whatsapp);

        Optional<ChatGroup> chatGroupOptional = chatGroupDaoJdbc.findById("1");

        Assert.assertTrue(chatGroupOptional.isPresent());
        Assert.assertEquals(1, chatGroupOptional.get().getId());
        Assert.assertEquals("S", chatGroupOptional.get().getCareerCode());
        Assert.assertEquals("ChatGroup test", chatGroupOptional.get().getName());
        Assert.assertEquals("https://test.com", chatGroupOptional.get().getLink());
    }

}