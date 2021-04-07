package ar.edu.itba.paw;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.persistence.PollDaoJdbc;

@Rollback
@Sql("classpath:populators/poll_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PollDaoJdbcTest {
    @Autowired
    private PollDaoJdbc pollDaoJdbc;

    @Test
    public void testFindGeneral()
    {
        List<Poll> general = pollDaoJdbc.findGeneral();
        Assert.assertEquals(4, general.size());
    }

    @Test
    public void testfindByCareer()
    {
        List<Poll> polls = pollDaoJdbc.findByCareer(1);
        Assert.assertEquals(3, polls.size());
    }

    @Test
    public void testFindByCourse()
    {
        List<Poll> polls = pollDaoJdbc.findByCourse("1.1");
        Assert.assertEquals(2, polls.size());
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
}
