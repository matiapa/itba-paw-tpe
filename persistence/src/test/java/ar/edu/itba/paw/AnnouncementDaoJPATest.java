package ar.edu.itba.paw;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.AnnouncementDaoJPA;
import ar.edu.itba.paw.persistence.jpa.CareerDaoJPA;
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
import java.util.List;
import java.util.Optional;

import static ar.edu.itba.paw.TestUtils.set;

@Transactional
@Rollback
@Sql("classpath:populators/announcement_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AnnouncementDaoJPATest {

    @Autowired private AnnouncementDaoJPA announcementDao;

    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private User user;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);

        user = new User();
        set(user, "id",1);
    }

    @Test
    public void testFindGeneral() {
        List<Announcement> announcements = announcementDao.findGeneral(null,0, 10);

        Assert.assertEquals(4, announcements.size());
        Assert.assertEquals(Integer.valueOf(1), announcements.get(0).getId());
    }

    @Test
    public void testFindByCareer() {
        Career career = new Career();
        set(career, "code","A");

        List<Announcement> announcements = announcementDao.findByCareer(career, null, 0, 10);

        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(Integer.valueOf(2), announcements.get(0).getId());
    }

    @Test
    public void testFindByCourse() {
        Course course = new Course();
        set(course, "id","1.1");

        List<Announcement> announcements = announcementDao.findByCourse(course, null, 0, 10);

        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(Integer.valueOf(3), announcements.get(0).getId());
    }

    @Test
    public void testPagination() {
        int totalElements = announcementDao.getSize(EntityTarget.general, null, null);
        Assert.assertEquals(4, totalElements);

        List<Announcement> announcements = announcementDao.findGeneral(null,0, 2);
        Assert.assertEquals(2, announcements.size());
        Assert.assertEquals(Integer.valueOf(4), announcements.get(1).getId());

        announcements = announcementDao.findGeneral(null, 2, 2);
        Assert.assertEquals(0, announcements.size());
    }

    @Test
    public void testShowSeen() {
        List<Announcement> announcements = announcementDao.findGeneral(user,0, 10);
        Assert.assertEquals(3, announcements.size());
        Assert.assertEquals(Integer.valueOf(1), announcements.get(0).getId());
    }

    @Test
    public void testFindById() {
        Optional<Announcement> announcementOptional = announcementDao.findById(1);

        Assert.assertTrue(announcementOptional.isPresent());
        Announcement announcement = announcementOptional.get();

        Assert.assertEquals(Integer.valueOf(1), announcement.getId());
        Assert.assertEquals("Test content",announcement.getContent());
    }

    @Test
    public void testCreate() {
        Announcement announcement = announcementDao.create("Test", "Test", "Test",
                null, null, null, user);

        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "announcement",
                String.format("id=%d", announcement.getId()));

        Assert.assertEquals(1, count);
    }

    @Test
    public void testDelete() {
        int initialCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "announcement",
                String.format("id=%d", 1));

        announcementDao.delete(announcementDao.findById(1).orElseThrow(RuntimeException::new));

        int finalCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "announcement",
                String.format("id=%d", 1));

        Assert.assertEquals(initialCount-1, finalCount);
    }

}
