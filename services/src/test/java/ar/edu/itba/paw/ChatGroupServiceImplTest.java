package ar.edu.itba.paw;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import ar.edu.itba.paw.services.ChatGroupServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ChatGroupServiceImplTest {

    private static final String CAREER_ID = "1";
    private static final String GROUP_NAME = "Discord";
    private static final String LINK = "https://discord.com";
    private static final Integer USER = 1;
    private static final Date DATE = new Date();

    @InjectMocks
    private ChatGroupServiceImpl chatGroupService = new ChatGroupServiceImpl();

    @Mock
    private ChatGroupDao mockDao;

    @Test
    public void testAddGroup(){
        Mockito.when(
                mockDao.addGroup(Mockito.eq(GROUP_NAME),
                        Mockito.eq(CAREER_ID),
                        Mockito.eq(LINK),
                        Mockito.eq(USER),
                        Mockito.eq(DATE)))
        .thenReturn(new ChatGroup(1, CAREER_ID, GROUP_NAME, LINK, USER, DATE));

        ChatGroup chatGroup = chatGroupService.addGroup(
                GROUP_NAME, CAREER_ID, LINK, USER, DATE);

        Assert.assertNotNull(chatGroup);
        Assert.assertEquals(GROUP_NAME, chatGroup.getName());
        Assert.assertEquals(CAREER_ID, chatGroup.getCareerId());
        Assert.assertEquals(LINK, chatGroup.getLink());
        Assert.assertEquals(USER, chatGroup.getSubmitted_by());
        Assert.assertEquals(DATE, chatGroup.getCreationDate());

    }
}
