package ar.edu.itba.paw;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.CareerDaoJPA;
import ar.edu.itba.paw.persistence.jpa.CourseDaoJPA;
import ar.edu.itba.paw.persistence.jpa.UserDaoJPA;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Rollback
@Sql("classpath:populators/course_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CourseDaoJPATest {

    @Autowired private CourseDaoJPA courseDao;

    @Autowired private UserDaoJPA userDao;
    @Autowired private CareerDaoJPA careerDao;

    @Test
    public void findAllTest(){
        List<Course> courses = courseDao.findAll();

        Assert.assertEquals(1,courses.size());
        Assert.assertEquals("1.1",courses.get(0).getId());
    }

    @Test
    public void findFavouritesTest() {
        Optional<User> user = userDao.findById(0);

        List<Course> courses = courseDao.findFavourites(user.isPresent()? user.get() : null);

        Assert.assertEquals(1, courses.size());
        Assert.assertEquals("1.1", courses.get(0).getId());
    }

    @Test
    public void findByCareerTest() {
        Optional<Career> career = careerDao.findByCode("S");
        List<CareerCourse> career_courses = courseDao.findByCareer(career.isPresent()? career.get() : null);

        Assert.assertEquals(1, career_courses.size());
        Assert.assertEquals("1.1", career_courses.get(0).getCourse().getId());
    }

    @Test
    public void findByIdTest() {

        Optional<Course> course = courseDao.findById("1.1");

        Assert.assertTrue(course.isPresent());
        Assert.assertEquals("Course 1", course.get().getName());
    }

    @Test
    public void testMarkFavorite(Course course, User ofUser, boolean favorite){

    }

    @Test
    public void testCreateCourseReview(Course course, String review, User uploader){

    }

    @Test
    public void testGetReviews(Course course, Integer page, Integer pageSize){

    }

    @Test
    public void testGetReviewsSize(Course course){

    }

}