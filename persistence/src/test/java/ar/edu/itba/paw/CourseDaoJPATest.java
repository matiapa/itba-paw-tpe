package ar.edu.itba.paw;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.jpa.CourseDaoJPA;
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
public class CourseDaoJPATest {

    @Autowired private CourseDaoJPA courseDao;

    @Test
    public void findAllTest(){
        List<Course> courses = courseDao.findAll();

        Assert.assertEquals(1,courses.size());
        Assert.assertEquals("1.1",courses.get(0).getId());
    }

    @Test
    public void findFavouritesTest() {
        Career career = new Career();
        career.setCode("S");
        User user = new User(0, "John", "Doe", "jd@gmail.com", "12345678", career);

        List<Course> courses = courseDao.findFavourites(user);

        Assert.assertEquals(1, courses.size());
        Assert.assertEquals("1.1", courses.get(0).getId());
    }

    @Test
    public void findByCareerTest() {
        Career career = new Career();
        career.setCode("A");
        List<CareerCourse> career_courses = courseDao.findByCareer(career);

        Assert.assertEquals(1, career_courses.size());
        Assert.assertEquals("1.1", career_courses.get(0).getCourse().getId());
    }

    @Test
    public void findByIdTest() {

        Optional<Course> course = courseDao.findById("1.1");

        Assert.assertTrue(course.isPresent());
        Assert.assertEquals("Course 1", course.get().getName());
    }

    /*      Ya no existe findByCareerSemester
    @Test
    public void findByCareerSemesterTest(){
        Map<Integer, List<CareerCourse>> result= courseDao.findByCareerSemester("A");
        Assert.assertEquals(1,result.size());
        Assert.assertEquals(1,result.get(1).size());
        Assert.assertEquals("1.1",result.get(1).get(0).getId());

    }
*/
}