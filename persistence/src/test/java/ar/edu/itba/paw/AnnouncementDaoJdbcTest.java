package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.persistence.AnnouncementDaoJdbc;
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

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Rollback
@Sql("classpath:populators/announcement_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AnnouncementDaoJdbcTest {

    @Autowired private AnnouncementDaoJdbc announcementDaoJdbc;

    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testFindGeneral() {
        List<Announcement> announcements = announcementDaoJdbc.findGeneral(false,1, 0, 10);

        Assert.assertEquals(3, announcements.size());
        Assert.assertEquals(1, announcements.get(0).getId());
    }

    @Test
    public void testFindByCarrer() {
        List<Announcement> announcements = announcementDaoJdbc.findByCareer("A", false, 1, 0, 10);

        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(2, announcements.get(0).getId());
    }

    @Test
    public void testFindByCourse() {
        List<Announcement> announcements = announcementDaoJdbc.findByCourse("1.1", false, 0, 0, 10);

        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(3, announcements.get(0).getId());
    }

    @Test
    public void testPagination() {
        int totalElements = announcementDaoJdbc.getSize(EntityTarget.general, null, false, 1);
        Assert.assertEquals(3, totalElements);

        List<Announcement> announcements = announcementDaoJdbc.findGeneral(false,1, 0, 2);
        Assert.assertEquals(2, announcements.size());
        Assert.assertEquals(4, announcements.get(1).getId());

        announcements = announcementDaoJdbc.findGeneral(false,1, 2, 2);
        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(5, announcements.get(0).getId());
    }

    @Test
    public void testShowSeen() {
        List<Announcement> announcements = announcementDaoJdbc.findGeneral(false,1, 0, 10);
        Assert.assertEquals(3, announcements.size());
        Assert.assertEquals(1, announcements.get(0).getId());

        announcements = announcementDaoJdbc.findGeneral(true,1, 0, 10);
        Assert.assertEquals(4, announcements.size());
        Assert.assertEquals(6, announcements.get(3).getId());
    }

    @Test
    public void testFindById() {
        Optional<Announcement> announcementOptional = announcementDaoJdbc.findById(1);

        Assert.assertTrue(announcementOptional.isPresent());
        Announcement announcement = announcementOptional.get();

        Assert.assertEquals(1, announcement.getId());
        Assert.assertEquals("Test content",announcement.getContent());
    }

    @Test
    public void testCreate() {
        Announcement announcement = announcementDaoJdbc.create("Test", "Test", "Test",
        null, null, null, 1);

        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "announcement",
            String.format("id=%d", announcement.getId()));

        Assert.assertEquals(1, count);
    }

    @Test
    public void testDelete() {
        int initialCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "announcement",
            String.format("id=%d", 1));

        announcementDaoJdbc.delete(1);

        int finalCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "announcement",
            String.format("id=%d", 1));

        Assert.assertEquals(initialCount-1, finalCount);
    }

}