package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.ContentDaoJdbc;
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


@Rollback
@Sql("classpath:populators/content_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ContentDaoJdbcTest {

    @Autowired private ContentDaoJdbc contentDao;

    @Test
    public void findContentTest() {
        List<Content> contentList = contentDao.findContent("1.1", Content.ContentType.other,null,null,0,1000);
        Assert.assertEquals(2,contentList.size());
        Assert.assertEquals(1,contentList.get(0).getId());
        Assert.assertEquals(2,contentList.get(1).getId());
    }

    @Test
    public void deleteTest(){
        List<Content> contentList = contentDao.findContent("1.1", Content.ContentType.other,null,null,0,10);
        Assert.assertEquals(2,contentList.size());
        Assert.assertEquals(1,contentList.get(0).getId());
        Assert.assertEquals(2,contentList.get(1).getId());

        contentDao.delete(1);

        contentList = contentDao.findContent("1.1",Content.ContentType.other,null,null,0,10);
        Assert.assertEquals(1,contentList.size());
        Assert.assertEquals(2,contentList.get(0).getId());
    }

    @Test
    public void createContentTest(){

        List<Content> contentList = contentDao.findContent("1.1",Content.ContentType.other,null,null,0,10);
        Assert.assertEquals(2,contentList.size());

        Assert.assertTrue(contentDao.createContent("name", "www.test.com", "1.1", "test description", "other", null,
                new User(1, "test", "test surname", "testEmail@gmail.com", "password", null, new Date(), null, "S")));

        contentList = contentDao.findContent("1.1", Content.ContentType.other,null,null,0,10);
        Assert.assertEquals(3,contentList.size());

    }

}