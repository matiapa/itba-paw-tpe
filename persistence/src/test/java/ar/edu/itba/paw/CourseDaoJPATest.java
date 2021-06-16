package ar.edu.itba.paw;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.jpa.CourseDaoJPA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static ar.edu.itba.paw.TestUtils.set;

@Transactional
@Sql("classpath:populators/course_populate.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CourseDaoJPATest {

    @Autowired private CourseDaoJPA courseDao;

    @Autowired private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private User user;
    private Career career;
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);

        user = new User();
        career = new Career();
        set(user, "id",1);
        set(career, "code", "A");
    }


    @Test
    public void findAllTest(){
        List<Course> courses = courseDao.findAll();

        Assert.assertEquals(1,courses.size());
        Assert.assertEquals("1.1",courses.get(0).getId());
    }

    @Test
    public void findFavouritesTest() {
        List<Course> courses = courseDao.findFavourites(user);

        Assert.assertEquals(1, courses.size());
        Assert.assertEquals("1.1", courses.get(0).getId());
    }

    @Test
    public void findByCareerTest() {
        List<CareerCourse> career_courses = courseDao.findByCareer(career);

        Assert.assertEquals(1, career_courses.size());
        Assert.assertEquals("1.1", career_courses.get(0).getCourse().getId());
    }

    @Test
    public void testFindById() {
        Optional<Course> course = courseDao.findById("1.1");

        Assert.assertTrue(course.isPresent());
        Assert.assertEquals("Course 1", course.get().getName());
    }

    @Test
    public void testCreateCourseReview(){
        Course course = new Course();
        set(course, "id", "1.1");
        CourseReview courseReview = courseDao.createCourseReview(course, "Test", user);


        Assert.assertEquals("Test", courseReview.getReview());
        Assert.assertEquals(user, courseReview.getUploader());
        Assert.assertEquals(course, courseReview.getCourse());
    }


    @Test
    public void testGetReviews(){
        Course course = new Course();
        set(course, "id", "1.1");

        List<CourseReview> courseReviews = courseDao.getReviews(course, 0, 10);

        Assert.assertEquals("1.1", courseReviews.get(0).getCourse().getId());
    }

    @Test
    public void testGetReviewsSize(){
        Course course = new Course();
        set(course, "id", "1.1");

        List<CourseReview> courseReviews = courseDao.getReviews(course, 0, 10);

        Assert.assertEquals(1, courseReviews.size());
    }

}