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

@Rollback
@Sql("classpath:populators/poll_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PollDaoJPATest {
    @Autowired
    private PollDaoJPA pollDao;

    @Autowired private CareerDaoJPA careerDao;
    @Autowired private UserDaoJPA userDao;
    @Autowired private CourseDaoJPA courseDao;

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
        Optional<Career> career = careerDao.findByCode("S");

        List<Poll> polls = pollDao.findByCareer(career.isPresent()? career.get() : null, null, null, 0, 7);
        Assert.assertEquals(1, polls.size());
    }

    @Test
    public void testFindByCourse()
    {
        Optional<Course> course = courseDao.findById("1.1");

        List<Poll> polls = pollDao.findByCourse(course.isPresent()? course.get() : null, null, null, 0, 7);
        Assert.assertEquals(2, polls.size());
    }

    @Test
    public void testGetSize()
    {
        Optional<Career> career = careerDao.findByCode("S");
        Optional<Course> course = courseDao.findById("1.1");

        Assert.assertEquals(3, pollDao.getCount(EntityTarget.general, PollFormat.multiple_choice, PollState.open));
        Assert.assertEquals(1, pollDao.getCount(EntityTarget.career, career.isPresent()? career.get() : null, PollFormat.multiple_choice, PollState.open));
        Assert.assertEquals(2, pollDao.getCount(EntityTarget.course, course.isPresent()? course.get() : null, PollFormat.multiple_choice, PollState.open));
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
    public void testGetVotes()
    {
        Optional<Career> career = careerDao.findByCode("S");
        Optional<Course> course = courseDao.findById("1.1");
        Optional<User> user = userDao.findById(0);
        Poll poll = new Poll("poll", "description", PollFormat.multiple_choice, career.get(), course.get(), null, user.get());

        Map<PollOption,Integer> votes = pollDao.getVotes(poll);
        for(PollOption option : votes.keySet()) {
            switch(option.getId()) {
                case 1:
                    Assert.assertEquals(2, (int)votes.get(option));
                    break;
                case 2:
                    Assert.assertEquals(1, (int)votes.get(option));
                    break;
                case 3:
                    Assert.assertEquals(0, (int)votes.get(option));
                    break;
                default:
                    Assert.fail("Not expecting option id " + option.getId());
            }
        }
    }

    @Test
    public void testAddPoll()
    {
        Optional<Career> career = careerDao.findByCode("S");
        Optional<Course> course = courseDao.findById("1.1");
        Optional<User> user = userDao.findById(0);

        pollDao.addPoll("Name 8", "Description 8", PollFormat.multiple_choice, career.get(), null, user.get(), Arrays.asList("Option 1", "Option 2"));

        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "poll", "name='Name 8'");

        Assert.assertEquals(1, count);
    }

    @Test
    public void testVoteChoicePoll()
    {
        Optional<Career> career = careerDao.findByCode("S");
        Optional<Course> course = courseDao.findById("1.1");
        Optional<User> user = userDao.findById(0);

        Poll poll = new Poll("poll", "description", PollFormat.multiple_choice, career.get(), course.get(), null, user.get());
        PollOption option = new PollOption(poll, "option");

        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "poll_submission", "poll_id=7 AND option_id=3");
        Assert.assertEquals(0, count);
        
        pollDao.voteChoicePoll(poll, option, user.get());
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "poll_submission", "poll_id=7 AND option_id=3");
        Assert.assertEquals(1, count);
    }

    @Test
    public void testHasVoted()
    {
        Optional<Career> career = careerDao.findByCode("S");
        Optional<Course> course = courseDao.findById("1.1");
        Optional<User> user1 = userDao.findById(0);
        Optional<User> user2 = userDao.findById(1);
        Optional<User> user3 = userDao.findById(2);

        Poll poll = new Poll("poll", "description", PollFormat.multiple_choice, career.get(), course.get(), null, user1.get());

        Assert.assertTrue(pollDao.hasVoted(poll, user1.get()));

        Assert.assertTrue(pollDao.hasVoted(poll, user2.get()));
        Assert.assertFalse(pollDao.hasVoted(poll, user3.get()));
    }

    @Test
    public void testDelete()
    {
        Optional<Career> career = careerDao.findByCode("S");
        Optional<Course> course = courseDao.findById("1.1");
        Optional<User> user = userDao.findById(0);

        Poll poll = new Poll("poll", "description", PollFormat.multiple_choice, career.get(), course.get(), null, user.get());

        Assert.assertTrue(pollDao.findById(1).isPresent());
        pollDao.delete(poll);
        Assert.assertFalse(pollDao.findById(1).isPresent());
    }

    @Test
    public void testFindRelevant(User user, int topN){

    }

    @Test
    public void testFindControversial(User user, int topN){

    }

}
