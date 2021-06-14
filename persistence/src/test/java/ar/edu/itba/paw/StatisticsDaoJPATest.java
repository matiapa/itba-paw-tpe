package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jpa.StatisticsDaoJPA;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import java.text.SimpleDateFormat;

@Rollback
@Sql("classpath:populators/statistics_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class StatisticsDaoJPATest {



    @Autowired
    private StatisticsDaoJPA statisticsDao;

    @Test
    public void testNewContributions(){
        Career career = new Career();
        career.setCode("S");
        Course course = new Course();
        course.setId("1.1");
        User user = new User(0, "John", "Doe", "jd@gmail.com", "12345678", career);

        Map<Entity, Integer> newContribs = statisticsDao.newContributions(user);

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