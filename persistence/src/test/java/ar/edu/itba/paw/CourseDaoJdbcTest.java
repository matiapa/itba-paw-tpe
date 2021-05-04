package ar.edu.itba.paw;

import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CourseDaoJdbc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Rollback
@Sql("classpath:populators/course_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CourseDaoJdbcTest {

    @Autowired private CourseDaoJdbc courseDao;

    @Test
    public void findAllTest(){
        List<Course> courses = courseDao.findAll();

        Assert.assertEquals(1,courses.size());
        Assert.assertEquals("1.1",courses.get(0).getId());
    }

    @Test
    public void findFavouritesTest() {
        List<Course> courses = courseDao.findFavourites(1);

        Assert.assertEquals(1, courses.size());
        Assert.assertEquals("1.1", courses.get(0).getId());
    }

    @Test
    public void findByCareerTest() {
        List<Course> courses = courseDao.findByCareer("A",10);

        Assert.assertEquals(1, courses.size());
        Assert.assertEquals("1.1", courses.get(0).getId());
    }

    @Test
    public void findByIdTest() {
        Optional<Course> course = courseDao.findById("1.1");

        Assert.assertTrue(course.isPresent());
        Assert.assertEquals("Course 1", course.get().getName());
    }

    @Test
    public void findByCareerSemesterTest(){
        Map<Integer, List<CareerCourse>> result= courseDao.findByCareerSemester("A");
        Assert.assertEquals(1,result.size());
        Assert.assertEquals(1,result.get(1).size());
        Assert.assertEquals("1.1",result.get(1).get(0).getId());

    }

}