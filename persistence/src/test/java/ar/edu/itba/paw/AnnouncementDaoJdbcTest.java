package ar.edu.itba.paw;


import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.persistence.AnnouncementDaoJdbc;
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
@Sql("classpath:populators/announcement_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AnnouncementDaoJdbcTest {

    @Autowired
    private AnnouncementDaoJdbc announcementDaoJdbc;



    @Test
    public void testFindGeneral() {
        List<Announcement> announcements = announcementDaoJdbc.findGeneral(0,0);

        Assert.assertEquals(1, announcements.size());

        Announcement announcement= announcements.get(0);
        Assert.assertEquals(2, announcement.getId());
        Assert.assertEquals("Test content2",announcement.getContent());
        Assert.assertEquals("Test summary2",announcement.getSummary());
        Assert.assertEquals("Test Title2",announcement.getTitle());
        Assert.assertEquals(1,announcement.getUploader().getId());

        // TODO Assert.assertEquals( DATE HERE,announcement.getExpiryDate());
        //TODO Assert.assertEquals(DATE HERE,announcement.getUploadDate());


    }


    @Test
    public void testFindByCarrer() {
        List<Announcement> announcements = announcementDaoJdbc.findByCareer("S");

        Assert.assertEquals(1, announcements.size());

        Announcement announcement= announcements.get(0);
        Assert.assertEquals(1, announcement.getId());
        Assert.assertEquals("Test content",announcement.getContent());
        Assert.assertEquals("Test summary",announcement.getSummary());
        Assert.assertEquals("Test Title",announcement.getTitle());
        Assert.assertEquals(1,announcement.getUploader().getId());

        // TODO Assert.assertEquals( DATE HERE,announcement.getExpiryDate());
        //TODO Assert.assertEquals(DATE HERE,announcement.getUploadDate());


    }

    @Test
    public void testFindByCourse() {
        List<Announcement> announcements = announcementDaoJdbc.findByCourse("1.1");

        Assert.assertEquals(1, announcements.size());

        Announcement announcement= announcements.get(0);
        Assert.assertEquals(1, announcement.getId());
        Assert.assertEquals("Test content",announcement.getContent());
        Assert.assertEquals("Test summary",announcement.getSummary());
        Assert.assertEquals("Test Title",announcement.getTitle());
        Assert.assertEquals(1,announcement.getUploader().getId());

        // TODO Assert.assertEquals( DATE HERE,announcement.getExpiryDate());
        //TODO Assert.assertEquals(DATE HERE,announcement.getUploadDate());


    }

    @Test
    public void testFindById() {
        Optional<Announcement> announcementOptional = announcementDaoJdbc.findById(1);

        Assert.assertTrue(announcementOptional.isPresent());
        Announcement announcement = announcementOptional.get();

        Assert.assertEquals(1, announcement.getId());
        Assert.assertEquals("Test content",announcement.getContent());
        Assert.assertEquals("Test summary",announcement.getSummary());
        Assert.assertEquals("Test Title",announcement.getTitle());
        Assert.assertEquals(1,announcement.getUploader().getId());

        // TODO Assert.assertEquals( DATE HERE,announcement.getExpiryDate());
        //TODO Assert.assertEquals(DATE HERE,announcement.getUploadDate());


    }
}
