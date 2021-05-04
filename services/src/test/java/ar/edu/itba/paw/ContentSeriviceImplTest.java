package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import ar.edu.itba.paw.persistence.ContentDao;
import ar.edu.itba.paw.services.ChatGroupServiceImpl;
import ar.edu.itba.paw.services.ContentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ContentSeriviceImplTest {

    private static final String NAME = "Drive";
    private static final String LINK = "https://test.com";
    private static final int SUBMITTED_BY = 1;
    private static final String COURSE_ID="72.38";
    private static final String DESCRIPTION="drive de informatica";
    private static final Date CREATION_DATE= new Date();
    private static final Content.ContentType CONTENT_TYPE= Content.ContentType.other;
    private static final Date CONTENT_DATE = null;
    private static final User USER = new User(SUBMITTED_BY,"testUserName","testuserSurname","testEmail@gmail.com","password",null,new Date(),null,"S");



    @InjectMocks
    private ContentServiceImpl contentService = new ContentServiceImpl();

    @Mock
    private ContentDao mockDao;

    @Test
    public void testfindByCourse(String courseId){

        List<Content> result = new ArrayList<>();
        result.add(new Content(1,NAME,LINK,DESCRIPTION,USER,CREATION_DATE,CONTENT_DATE,CONTENT_TYPE));
        Mockito.when(
                mockDao.findByCourse(

                        Mockito.eq(COURSE_ID)))

                .thenReturn(result);

        List<Content> contentList=contentService.findByCourse(courseId);

        Assert.assertEquals(1,contentList.size());
        Content content = contentList.get(0);
        Assert.assertEquals(NAME,content.getName());
        Assert.assertEquals(LINK,content.getLink());
        Assert.assertEquals(SUBMITTED_BY,content.getSubmitter().getId());
        Assert.assertEquals(DESCRIPTION,content.getDescription());
        Assert.assertEquals(CREATION_DATE,content.getUploadDate());
        Assert.assertEquals(CONTENT_TYPE,content.getContentType());
        Assert.assertEquals(CONTENT_DATE,content.getContentDate());



    }
}

