package ar.edu.itba.paw;

import java.util.*;

import javax.sql.DataSource;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.CareerDaoJPA;
import ar.edu.itba.paw.persistence.jpa.CourseDaoJPA;
import ar.edu.itba.paw.persistence.jpa.PollDaoJPA;
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

import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;
import ar.edu.itba.paw.models.Poll.PollState;
import org.springframework.transaction.annotation.Transactional;

import static ar.edu.itba.paw.TestUtils.set;

@Transactional
@Rollback
@Sql("classpath:populators/poll_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PollDaoJPATest {
    @Autowired
    private PollDaoJPA pollDao;

    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testFindGeneral()
    {
        List<Poll> general = pollDao.findGeneral(null, null, 0, 7);
        Assert.assertEquals(4, general.size());
    }

    @Test
    public void testfindByCareer()
    {
        Career career = new Career(); set(career, "code", "S");

        List<Poll> polls = pollDao.findByCareer(career, null, null, 0, 7);
        Assert.assertEquals(1, polls.size());
    }

    @Test
    public void testFindByCourse()
    {
        Course course = new Course(); set(course, "id", "1.1");

        List<Poll> polls = pollDao.findByCourse(course, null, null, 0, 7);
        Assert.assertEquals(2, polls.size());
    }

    @Test
    public void testGetSize()
    {
        Career career = new Career(); set(career, "code", "S");
        Course course = new Course(); set(course, "id", "1.1");

        Assert.assertEquals(3, pollDao.getCount(EntityTarget.general, PollFormat.multiple_choice, PollState.open));
        Assert.assertEquals(1, pollDao.getCount(EntityTarget.career, career, PollFormat.multiple_choice, PollState.open));
        Assert.assertEquals(2, pollDao.getCount(EntityTarget.course, course, PollFormat.multiple_choice, PollState.open));
        Assert.assertEquals(1, pollDao.getCount(EntityTarget.general, PollFormat.multiple_choice, PollState.closed));
    }

    @Test
    public void testFindById()
    {
        Optional<Poll> opt = pollDao.findById(7);
        Assert.assertTrue(opt.isPresent());

        Poll poll = opt.get();
        Assert.assertEquals(7, poll.getId());
        Assert.assertEquals("Name 7", poll.getName());
        Assert.assertEquals("Description 7", poll.getDescription());
        Assert.assertNotNull(poll.getCreationDate());
        Assert.assertNotNull(poll.getExpiryDate());
        // TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse("2021-05-08T00:00:00Z");
        // Instant i = Instant.from(ta);
        // Date d = Date.from(i);
        // Assert.assertEquals(i, poll.getExpiryDate().toInstant());
        Assert.assertNotNull(poll.getSubmittedBy());
        Assert.assertEquals(1, poll.getSubmittedBy().getId());

        Assert.assertEquals(3, poll.getOptions().size());
        String[] optionValues = poll.getOptions().stream().map(o -> o.getValue()).toArray(String[]::new);
        String [] expectedOptions = new String[]
                {
                        "Option 1", "Option 2", "Option 3"
                };
        Assert.assertArrayEquals(expectedOptions, optionValues);
    }

    @Test
    public void testDelete()
    {
        Poll poll = pollDao.findById(1).orElseThrow(RuntimeException::new);

        Assert.assertTrue(pollDao.findById(1).isPresent());
        pollDao.delete(poll);
        Assert.assertFalse(pollDao.findById(1).isPresent());
    }

}
