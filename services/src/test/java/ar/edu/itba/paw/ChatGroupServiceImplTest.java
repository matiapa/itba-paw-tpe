package ar.edu.itba.paw;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import ar.edu.itba.paw.services.ChatGroupServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class ChatGroupServiceImplTest {

    private static final String CAREER_CODE = "S";
    private static final String GROUP_NAME = "Discord";
    private static final String LINK = "https://discord.com";
    private static final int USER = 1;
    private static final Date DATE = new Date();

    @InjectMocks
    private ChatGroupServiceImpl chatGroupService = new ChatGroupServiceImpl();

    @Mock
    private ChatGroupDao mockDao;

    @Test
    public void testAddGroup(){
        Mockito.when(
                mockDao.addGroup(Mockito.eq(GROUP_NAME),
                        Mockito.eq(CAREER_CODE),
                        Mockito.eq(LINK),
                        Mockito.eq(USER),
                        Mockito.eq(DATE),
                        ChatGroup.ChatPlatform.WHATSAPP))
        .thenReturn(new ChatGroup(1, CAREER_CODE, GROUP_NAME, LINK, USER, DATE, ChatGroup.ChatPlatform.WHATSAPP));

        ChatGroup chatGroup = chatGroupService.addGroup(
                GROUP_NAME, CAREER_CODE, LINK, USER, DATE, ChatGroup.ChatPlatform.WHATSAPP);

        Assert.assertNotNull(chatGroup);
        Assert.assertEquals(GROUP_NAME, chatGroup.getName());
        Assert.assertEquals(CAREER_CODE, chatGroup.getCareerCode());
        Assert.assertEquals(LINK, chatGroup.getLink());
        Assert.assertEquals(USER, chatGroup.getSubmittedBy());
        Assert.assertEquals(DATE, chatGroup.getCreationDate());

    }
}
