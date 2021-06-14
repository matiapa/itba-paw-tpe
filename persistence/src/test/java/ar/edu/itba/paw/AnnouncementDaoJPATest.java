package ar.edu.itba.paw;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.AnnouncementDaoJPA;
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
public class AnnouncementDaoJPATest {

    @Autowired private AnnouncementDaoJPA announcementDao;

    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

        @Test
    public void testFindGeneral() {
        Career career = new Career();
        career.setCode("A");
        User user = new User(0, "John", "Doe", "jd@gmail.com", "12345678", career);

        List<Announcement> announcements = announcementDao.findGeneral(user,1, 10);

        Assert.assertEquals(3, announcements.size());

        Assert.assertEquals(1, Optional.ofNullable(announcements.get(0).getId()));
    }



    @Test
    public void testFindByCarrer() {
        Career career = new Career();
        career.setCode("A");
        User user = new User(0, "John", "Doe", "jd@gmail.com", "12345678", career);

        List<Announcement> announcements = announcementDao.findByCareer(career, user, 1, 0);

        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(2, Optional.ofNullable(announcements.get(0).getId()));
    }

    @Test
    public void testFindByCourse() {
        Course course = new Course();
        course.setId("1.1");
        Career career = new Career();
        career.setCode("A");
        User user = new User(0, "John", "Doe", "jd@gmail.com", "12345678", career);

        List<Announcement> announcements = announcementDao.findByCourse(course, user, 0, 0);

        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(3, Optional.ofNullable(announcements.get(0).getId()));
    }

    @Test
    public void testPagination() {
        Career career = new Career();
        career.setCode("A");
        User user = new User(0, "John", "Doe", "jd@gmail.com", "12345678", career);

        int totalElements = announcementDao.getSize(EntityTarget.general, null, user);
        Assert.assertEquals(3, totalElements);

        List<Announcement> announcements = announcementDao.findGeneral(user,1, 0);
        Assert.assertEquals(2, announcements.size());
        Assert.assertEquals(4, Optional.ofNullable(announcements.get(1).getId()));

        announcements = announcementDao.findGeneral(user,1, 2);
        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(5, Optional.ofNullable(announcements.get(0).getId()));
    }

    @Test
    public void testShowSeen() {            //TODO
        Career career = new Career();
        career.setCode("A");
        User user = new User(0, "John", "Doe", "jd@gmail.com", "12345678", career);

        List<Announcement> announcements = announcementDao.findGeneral(user,1, 0);
        Assert.assertEquals(3, announcements.size());
        Assert.assertEquals(1, Optional.ofNullable(announcements.get(0).getId()));

        announcements = announcementDao.findGeneral(user,1, 0);
        Assert.assertEquals(4, announcements.size());
        Assert.assertEquals(6, Optional.ofNullable(announcements.get(3).getId()));
    }

    @Test
    public void testFindById() {
        Optional<Announcement> announcementOptional = announcementDao.findById(1);

        Assert.assertTrue(announcementOptional.isPresent());
        Announcement announcement = announcementOptional.get();

        Assert.assertEquals(1, Optional.ofNullable(announcement.getId()));
        Assert.assertEquals("Test content",announcement.getContent());
    }

    @Test
    public void testCreate() {
        Career career = new Career();
        career.setCode("A");
        User user = new User(0, "John", "Doe", "jd@gmail.com", "12345678", career);

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

        Announcement announcement = new Announcement();
        announcement.setId(0);
        announcementDao.delete(announcement);

        int finalCount = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "announcement",
            String.format("id=%d", 1));

        Assert.assertEquals(initialCount-1, finalCount);
    }

}