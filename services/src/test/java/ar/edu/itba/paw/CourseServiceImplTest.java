package ar.edu.itba.paw;

import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CourseDao;
import ar.edu.itba.paw.services.CourseServiceImpl;
import ar.edu.itba.paw.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService = new CourseServiceImpl();

    @Mock private CourseDao courseDao;
    @Mock private UserService userService;


    @Test(expected = LoginRequiredException.class)
    public void testFavCoursesNotLoggedIn() throws LoginRequiredException {
        Mockito.when(userService.isLogged()).thenReturn(false);

        courseService.findFavourites();
    }

    @Test
    public void testFavCoursesLoggedIn() throws LoginRequiredException {
        Mockito.when(userService.isLogged()).thenReturn(true);
        Mockito.when(userService.getLoggedID()).thenReturn(1);

        List<Course> expectedFavs = Collections.singletonList(
            new Course("1", "test")
        );
        Mockito.when(courseDao.findFavourites(1)).thenReturn(expectedFavs);

        List<Course> favs = courseService.findFavourites();

        Assert.assertEquals(expectedFavs, favs);
    }

}