package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jpa.CareerDaoJPA;
import ar.edu.itba.paw.persistence.jpa.CourseDaoJPA;
import ar.edu.itba.paw.persistence.jpa.StatisticsDaoJPA;
import ar.edu.itba.paw.persistence.jpa.UserDaoJPA;
import org.hsqldb.jdbc.JDBCUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.AssociationOverride;
import java.util.*;

import java.text.SimpleDateFormat;

import static ar.edu.itba.paw.TestUtils.set;

@Rollback
@Sql("classpath:populators/statistics_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StatisticsDaoJPATest {

    @Autowired
    private StatisticsDaoJPA statisticsDao;


    @Test
    public void testContributionsByDate(){
        Map<Date, Integer> dateContribs = statisticsDao.contributionsByDate();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, Integer> expectedDateContribs = new HashMap<String, Integer>(){{
            put("2021-05-03", 9); put("2021-05-01", 3);
        }};

        dateContribs.forEach((k,v) -> Assert.assertEquals(expectedDateContribs.get(df.format(k)), v));
    }

    @Test
    public void topUsersByContributions(){
        Map<User, Integer> userContribs = statisticsDao.topUsersByContributions();

        Map<Integer, Integer> expectedUserContribs = new HashMap<Integer, Integer>(){{
            put(1, 2); put(2, 4); put(3, 6);
        }};

        userContribs.forEach((k,v) -> Assert.assertEquals(expectedUserContribs.get(k.getId()), v));
    }

    @Test
    public void topCoursesByContributions(){
        Map<Course, Integer> courseContribs = statisticsDao.topCoursesByContributions();

        Map<String, Integer> expectedCourseContribs = new HashMap<String, Integer>(){{
            put("1.1", 1); put("1.2", 2); put("1.3", 3);
        }};

        courseContribs.forEach((k,v) -> Assert.assertEquals(expectedCourseContribs.get(k.getId()), v));
    }

}