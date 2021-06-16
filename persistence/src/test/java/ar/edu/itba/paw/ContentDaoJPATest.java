package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jpa.ContentDaoJPA;
import ar.edu.itba.paw.persistence.jpa.CourseDaoJPA;
import ar.edu.itba.paw.persistence.jpa.UserDaoJPA;
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
@Sql("classpath:populators/content_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ContentDaoJPATest {

    @Autowired private ContentDaoJPA contentDao;

    @Autowired private CourseDaoJPA courseDao;
    @Autowired private UserDaoJPA userDao;

    @Test
    public void findByCourseTest() {
        Optional<Course> course = courseDao.findById("1.1");

        List<Content> contentList = contentDao.findByCourse(course.isPresent()? course.get() : null, Content.ContentType.miscellaneous,null,null,0,10);
        Assert.assertEquals(2,contentList.size());
        Assert.assertEquals(1, Optional.ofNullable(contentList.get(0).getId()));
        Assert.assertEquals(2, Optional.ofNullable(contentList.get(1).getId()));
    }
    @Test
    public void deleteTest(){
        Optional<Course> course = courseDao.findById("1.1");

        List<Content> contentList = contentDao.findByCourse(course.isPresent()? course.get() : null, Content.ContentType.miscellaneous,null,null,0,10);
        Assert.assertEquals(2,contentList.size());
        Assert.assertEquals(1, Optional.ofNullable(contentList.get(0).getId()));
        Assert.assertEquals(2, Optional.ofNullable(contentList.get(1).getId()));

        contentDao.delete(contentList.get(0));

        contentList = contentDao.findByCourse(course.isPresent()? course.get() : null, Content.ContentType.miscellaneous,null,null,0,10);
        Assert.assertEquals(1,contentList.size());
        Assert.assertEquals(2, Optional.ofNullable(contentList.get(0).getId()));
    }

    @Test
    public void createContentTest(){
        Optional<Course> course = courseDao.findById("1.1");
        Optional<User> user = userDao.findById(0);
        List<Content> contentList = contentDao.findByCourse(course.isPresent()? course.get() : null, Content.ContentType.miscellaneous,null,null,0,10);
        Assert.assertEquals(2,contentList.size());
        Content content = new Content();
        content.setName("name");
        content.setLink("www.test.com");
        Assert.assertEquals(content , contentDao.createContent("name", "www.test.com", course.isPresent()? course.get() : null, "test description", Content.ContentType.miscellaneous, null,
                user.isPresent()? user.get() : null, user.isPresent()? user.get().getEmail() : null));

        contentList = contentDao.findByCourse(course.isPresent()? course.get() : null, Content.ContentType.miscellaneous,null,null,0,10);
        Assert.assertEquals(3,contentList.size());

    }

}