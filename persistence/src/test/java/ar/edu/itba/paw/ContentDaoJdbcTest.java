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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Rollback
@Sql("classpath:populators/content_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ContentDaoJdbcTest {

    @Autowired private ContentDaoJdbc contentDao;



    @Test
    public void findByCourseTest() {
        List<Content> contentList = contentDao.findByCourse("93.45");
        Assert.assertEquals(2,contentList.size());
        Assert.assertEquals(1,contentList.get(0).getId());
        Assert.assertEquals(2,contentList.get(1).getId());

    }

    @Test
    public void findByCoursePaginationTest(){
        List<Content> contentList= contentDao.findByCourse("93.45",1,1);

        Assert.assertEquals(1,contentList.size());
        Assert.assertEquals(2,contentList.get(0).getId());


    }

    @Test
    public void findByCourseAndType(){
        List<Content> contentList = contentDao.findByCourseAndType("93.45","other");
        Assert.assertEquals(2,contentList.size());
        Assert.assertEquals(1,contentList.get(0).getId());
        Assert.assertEquals(2,contentList.get(1).getId());

        contentList = contentDao.findByCourseAndType("93.45","resume");
        Assert.assertEquals(0,contentList.size());

    }

//    @Test
//    public void findContentTest(){
//        List<Content> contentList= contentDao.findContent("93.45", Content.ContentType.other,new Date(0),new Date());
//
//
//
//        Assert.assertEquals(2,contentList.size());
//        Assert.assertEquals(1,contentList.get(0).getId());
//        Assert.assertEquals(2,contentList.get(1).getId());
//
//
//        contentList= contentDao.findContent("93.45", Content.ContentType.other,new Date(0),new Date());
//
//    }

    @Test
    public void deleteTest(){
        List<Content> contentList = contentDao.findByCourse("93.45");
        Assert.assertEquals(2,contentList.size());
        Assert.assertEquals(1,contentList.get(0).getId());
        Assert.assertEquals(2,contentList.get(1).getId());

        contentDao.delete(1);

        contentList = contentDao.findByCourse("93.45");
        Assert.assertEquals(1,contentList.size());
        Assert.assertEquals(2,contentList.get(0).getId());
    }

    @Test
    public void createContentTest(){

        List<Content> contentList = contentDao.findByCourse("93.45");
        Assert.assertEquals(2,contentList.size());

        Assert.assertTrue(contentDao.createContent("name", "www.test.com", "93.45", "test description", "other", null,
                new User(1, "test", "test surname", "testEmail@gmail.com", "password", null, new Date(), null, "S")));

        contentList = contentDao.findByCourse("93.45");
        Assert.assertEquals(3,contentList.size());


    }
}