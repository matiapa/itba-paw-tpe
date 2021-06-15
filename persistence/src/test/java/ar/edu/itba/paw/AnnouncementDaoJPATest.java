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

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Rollback
@Sql("classpath:populators/announcement_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AnnouncementDaoJPATest {

    @Autowired private AnnouncementDaoJPA announcementDao;

    @Autowired private UserDaoJPA userDao;
    @Autowired private CareerDaoJPA careerDao;
    @Autowired private CourseDaoJPA courseDao;

    @Autowired private DataSource ds;


    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

        @Test
    public void testFindGeneral() {
        List<Announcement> announcements = announcementDao.findGeneral(null,0, 10);

        Assert.assertEquals(3, announcements.size());
        Assert.assertEquals(1, Optional.ofNullable(announcements.get(0).getId()));
    }



    @Test
    public void testFindByCareer() {
        Optional<Career> career = careerDao.findByCode("S");
        List<Announcement> announcements = announcementDao.findByCareer(career.isPresent()? career.get() : null, null, 1, 0);

        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(2, Optional.ofNullable(announcements.get(0).getId()));
    }

    @Test
    public void testFindByCourse() {
        Optional<Course> course = courseDao.findById("1.1");
        List<Announcement> announcements = announcementDao.findByCourse(course.isPresent()? course.get() : null, null, 0, 0);

        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(3, Optional.ofNullable(announcements.get(0).getId()));
    }

    @Test
    public void testFindRelevant() {
        Optional<User> user = userDao.findById(0);
        List<Announcement> announcements = announcementDao.findRelevant(user.isPresent()? user.get() : null, 1, 10);
        //TODO AGREGAR RELEVANT AL POPULATOR
        Assert.assertEquals(1, announcements.size());
    }
    @Test
    public void testPagination() {

        int totalElements = announcementDao.getSize(EntityTarget.general, null, null);
        Assert.assertEquals(3, totalElements);

        List<Announcement> announcements = announcementDao.findGeneral(null,1, 0);
        Assert.assertEquals(2, announcements.size());
        Assert.assertEquals(4, Optional.ofNullable(announcements.get(1).getId()));

        announcements = announcementDao.findGeneral(null,1, 2);
        Assert.assertEquals(1, announcements.size());
        Assert.assertEquals(5, Optional.ofNullable(announcements.get(0).getId()));
    }

    @Test
    public void testShowSeen() {
        Optional<User> opUser = userDao.findById(0);
        User user = null;
        if (opUser.isPresent()){
            user = opUser.get();
        }
        List<Announcement> announcements = announcementDao.findGeneral(user,1, 0);
        Assert.assertEquals(3, announcements.size());
        Assert.assertEquals(1, Optional.ofNullable(announcements.get(0).getId()));

        announcementDao.markSeen(announcements.get(0), user);
        Assert.assertTrue(announcements.get(0).getSeenBy().get(0).getName().equals(user.getName()));
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
        Optional<User> user = userDao.findById(0);


        Announcement announcement = announcementDao.create("Test", "Test", "Test",
        null, null, null, user.isPresent()? user.get() : null);

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