package ar.edu.itba.paw;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

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

import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;
import ar.edu.itba.paw.models.Poll.PollState;
import ar.edu.itba.paw.persistence.PollDaoJdbc;

@Rollback
@Sql("classpath:populators/poll_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PollDaoJdbcTest {
    @Autowired
    private PollDaoJdbc pollDaoJdbc;

    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testFindGeneral()
    {
        List<Poll> general = pollDaoJdbc.findGeneral(null, null, 0, 7);
        Assert.assertEquals(4, general.size());
    }

    @Test
    public void testfindByCareer()
    {
        List<Poll> polls = pollDaoJdbc.findByCareer("S", null, null, 0, 7);
        Assert.assertEquals(1, polls.size());
    }

    @Test
    public void testFindByCourse()
    {
        List<Poll> polls = pollDaoJdbc.findByCourse("1.1", null, null, 0, 7);
        Assert.assertEquals(2, polls.size());
    }

    @Test
    public void testGetSize()
    {
        Assert.assertEquals(3, pollDaoJdbc.getSize(EntityTarget.general, null, PollFormat.multiple_choice, PollState.open));
        Assert.assertEquals(1, pollDaoJdbc.getSize(EntityTarget.career, "S", PollFormat.multiple_choice, PollState.open));
        Assert.assertEquals(2, pollDaoJdbc.getSize(EntityTarget.course, "1.1", PollFormat.multiple_choice, PollState.open));
        Assert.assertEquals(1, pollDaoJdbc.getSize(EntityTarget.general, null, PollFormat.multiple_choice, PollState.closed));
    }

    @Test
    public void testFindById()
    {
        Optional<Poll> opt = pollDaoJdbc.findById(7);
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
        Map<PollOption,Integer> votes = pollDaoJdbc.getVotes(7);
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
        pollDaoJdbc.addPoll("Name 8", "Description 8", PollFormat.multiple_choice, null, null, null, null, Arrays.asList("Option 1", "Option 2"));

        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "poll", "name='Name 8'");

        Assert.assertEquals(1, count);
    }

    @Test
    public void testVoteChoicePoll()
    {
        int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "poll_submission", "poll_id=7 AND option_id=3");
        Assert.assertEquals(0, count);
        
        pollDaoJdbc.voteChoicePoll(7, 3, 4);
        count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "poll_submission", "poll_id=7 AND option_id=3");
        Assert.assertEquals(1, count);
    }

    @Test
    public void testHasVoted()
    {
        Assert.assertTrue(pollDaoJdbc.hasVoted(7, 1));
        Assert.assertTrue(pollDaoJdbc.hasVoted(7, 2));
        Assert.assertFalse(pollDaoJdbc.hasVoted(7, 3));
    }

    @Test
    public void testDelete()
    {
        Assert.assertTrue(pollDaoJdbc.findById(1).isPresent());
        pollDaoJdbc.delete(1);
        Assert.assertFalse(pollDaoJdbc.findById(1).isPresent());
    }
}
