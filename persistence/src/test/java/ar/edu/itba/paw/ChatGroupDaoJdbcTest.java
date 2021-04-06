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
    public void testFindByCareer(){
        List<ChatGroup> chatGroups = chatGroupDaoJdbc.findByCareer(1);
        Assert.assertEquals(3, chatGroups.size());
        Assert.assertEquals("1", chatGroups.get(0).getId());
        chatGroups = chatGroupDaoJdbc.findByCareer(1, 2);
        Assert.assertTrue(chatGroups.size() <= 2);

    }

    @Test
    public void testFindById(){
        Optional<ChatGroup> chatGroupOptional = chatGroupDaoJdbc.findById("1");
        Assert.assertTrue(chatGroupOptional.isPresent());
        Assert.assertEquals("1", chatGroupOptional.get().getId());
        Assert.assertEquals("1", chatGroupOptional.get().getCareerId());
        Assert.assertEquals("whatsapp", chatGroupOptional.get().getName().toLowerCase());
        Assert.assertEquals("linkwhatsapp", chatGroupOptional.get().getLink().toLowerCase());
    }

}
