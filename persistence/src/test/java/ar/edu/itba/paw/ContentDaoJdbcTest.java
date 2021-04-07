package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.persistence.ContentDaoJdbc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@Rollback
@Sql("classpath:populators/content_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ContentDaoJdbcTest {

    @Autowired private ContentDaoJdbc contentDao;

    @Test
    public void testFindByCourse() {
        List<Content> contents = contentDao.findByCourse("1.1");

        Assert.assertEquals(1, contents.size());
        Assert.assertEquals(1, contents.get(0).getId());
    }

    @Test
    public void testFindById() {
        Optional<Content> content = contentDao.findById("1.1");

        Assert.assertTrue(content.isPresent());
        Assert.assertEquals("Content 1", content.get().getName());
    }

}