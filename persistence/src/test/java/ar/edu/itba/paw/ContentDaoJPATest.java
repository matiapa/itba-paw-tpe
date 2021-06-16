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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;
import java.util.ArrayList;
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

    private User user;
    private Course course;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);

        user = new User();
        course = new Course();
        set(user, "id",1);
        set(course, "id", "1.1");

    }

    @Test
    public void findByCourseTest() {
        List<Content> contentList = contentDao.findByCourse(course, Content.ContentType.miscellaneous,null,null,0,10);

        Assert.assertEquals(2,contentList.size());
    }
    @Test
    public void deleteTest(){
        int initialCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "course_content",
                String.format("id=%d", 1));

        contentDao.delete(contentDao.findById(1).orElseThrow(RuntimeException::new));

        int finalCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "course_content",
                String.format("id=%d", 1));

        Assert.assertEquals(initialCount-1, finalCount);
    }

    @Test
    public void createContentTest(){
        Content content = contentDao.createContent("Test", "test.com", course, "test",
                Content.ContentType.miscellaneous, null, user, "mail.com");

        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "course_content",
                String.format("id=%d", content.getId()));

        Assert.assertEquals(1, count);

    }

    @Test
    public void testFindById(){
        Optional<Content> content = contentDao.findById(1);

        Assert.assertTrue(content.isPresent());
        Assert.assertEquals(1, Optional.ofNullable(content.get().getId()));

    }
    @Test
    public void testGetSize(){
        int totalElements = contentDao.getSize(course, null, null, null);
        Assert.assertEquals(2, totalElements);
        List<Content> contents = contentDao.findByCourse(course, null, null, null, 0, 10);
        Assert.assertEquals(2, contents.size());
        Assert.assertEquals(Integer.valueOf(2), contents.get(1).getId());

        contents = contentDao.findByCourse(course, null, null, null,3, 10);
        Assert.assertEquals(0, contents.size());
    }

    @Test
    public void testGetReviews(){
        Content content = new Content();
        set(content, "id", 1);

        List<ContentReview> reviewList = contentDao.getReviews(content, 0, 10);
        Assert.assertEquals(3, reviewList.size());
    }

    @Test
    public void testCreateContentReview(){

    }

    @Test
    public void testGetReviewsSize(){

    }

    @Test
    public void testGetSubscribedUsers(){

    }


}