package ar.edu.itba.paw;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.ContentDaoJPA;
import ar.edu.itba.paw.persistence.jpa.CourseDaoJPA;
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
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static ar.edu.itba.paw.TestUtils.set;


@Transactional
@Sql("classpath:populators/content_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ContentDaoJPATest {

    @Autowired private ContentDaoJPA contentDao;

    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private User user;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);

        user = new User();
        set(user, "id",1);
        set(user, "email","test@email.com");
    }


    @Test
    public void findByIdTest(){
        Optional<Content> content = contentDao.findById(1);
        Assert.assertTrue(content.isPresent());
        Assert.assertEquals(Integer.valueOf(1),content.get().getId());
        Assert.assertEquals("Content 2", content.get().getName());
        Assert.assertEquals("desc", content.get().getDescription());
        Assert.assertEquals("http://test.com", content.get().getLink());

    }


    @Test
    public void findByCourseTest() {
        Course course = new Course();
        set(course, "id","1.1");

        List<Content> contentList = contentDao.findByCourse(course, Content.ContentType.miscellaneous,null,null,0,10);
        Assert.assertEquals(2,contentList.size());
        Assert.assertEquals(Integer.valueOf(0), contentList.get(0).getId());
        Assert.assertEquals(Integer.valueOf(1), contentList.get(1).getId());
    }
    @Test
    public void deleteTest(){
        Course course = new Course();
        set(course, "id","1.1");

        List<Content> contentList = contentDao.findByCourse(course, Content.ContentType.miscellaneous,null,null,0,10);
        Assert.assertEquals(2,contentList.size());
        Assert.assertEquals(Integer.valueOf(0), contentList.get(0).getId());
        Assert.assertEquals(Integer.valueOf(1), contentList.get(1).getId());

        contentDao.delete(contentList.get(0));

        contentList = contentDao.findByCourse(course, Content.ContentType.miscellaneous,null,null,0,10);
        Assert.assertEquals(1,contentList.size());
        Assert.assertEquals(Integer.valueOf(1), contentList.get(0).getId());
    }


    @Test
    public void getReviewsTest() {

        Content content = new Content();
        set(content, "id",0);

        List<ContentReview>list =contentDao.getReviews(content,0,10);
        Assert.assertEquals(1,list.size());
        Assert.assertEquals(Integer.valueOf(0), list.get(0).getId());

    }

    @Test
    public void getReviewsSizeTest(){
        Content content = new Content();
        set(content, "id",0);


        Assert.assertEquals(1,contentDao.getReviewsSize(content));
        set(content, "id",1);
        Assert.assertEquals(0, contentDao.getReviewsSize(content));
    }


    @Test
    public void createContentReview(){
        Content content = new Content();
        set(content, "id",0);

        ContentReview contentReview = contentDao.createContentReview(content,"test rev",user);

        Assert.assertEquals("test rev",contentReview.getReview());
        Assert.assertEquals(user,contentReview.getUploader());
        Assert.assertEquals(content,contentReview.getContent());
    }

    @Test
    public void getSubscribedUsersTest(){

        Course course = new Course();
        set(course, "id","1.1");
        Content content = new Content();
        set(content, "id",0);
        content.setCourse(course);
        List<User> users= contentDao.getSubscribedUsers(content);
        Assert.assertEquals(1,users.size());
        Assert.assertEquals(1,users.get(0).getId());
    }
}