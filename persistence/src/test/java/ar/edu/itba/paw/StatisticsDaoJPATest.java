package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jpa.CareerDaoJPA;
import ar.edu.itba.paw.persistence.jpa.CourseDaoJPA;
import ar.edu.itba.paw.persistence.jpa.StatisticsDaoJPA;
import ar.edu.itba.paw.persistence.jpa.UserDaoJPA;
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

@Rollback
@Sql("classpath:populators/statistics_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StatisticsDaoJPATest {



    @Autowired
    private StatisticsDaoJPA statisticsDao;

    @Autowired private CareerDaoJPA careerDao;
    @Autowired private CourseDaoJPA courseDao;
    @Autowired private UserDaoJPA userDao;
    @Test
    public void testNewContributions(){

        Optional<User> user = userDao.findById(0);
        Map<Entity, Integer> newContribs = statisticsDao.newContributions(user.get());

        Map<Entity, Integer> expectedNewContribs = new HashMap<Entity, Integer>(){{
            put(Entity.announcement, 2); put(Entity.chat_group, 2);
            put(Entity.course_content, 2); put(Entity.poll, 3);
        }};

        newContribs.forEach((k,v) -> Assert.assertEquals(expectedNewContribs.get(k), v));
    }

    @Test
    public void testContributionsByCareer(){
        Map<Career, Integer> careerContribs = statisticsDao.contributionsByCareer();

        Map<String, Integer> expectedCareerContribs = new HashMap<String, Integer>(){{
            put("A", 1); put("B", 2); put("C", 3);
        }};

        careerContribs.forEach((k,v) -> Assert.assertEquals(expectedCareerContribs.get(k.getCode()), v));
    }

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