package ar.edu.itba.paw;

import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.services.NavigationServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class NavigationServiceImplTest {

    @InjectMocks
    private final NavigationServiceImpl navigationService = new NavigationServiceImpl();

    @Test
    public void testForwardNav(){

        List<NavigationItem> expectedHistory = Arrays.asList(
            new NavigationItem("A", "/A"), new NavigationItem("B", "/B")
        );

        navigationService.registerPath(new NavigationItem("A", "/A"));
        navigationService.registerPath(new NavigationItem("B", "/B"));

        Assert.assertEquals(expectedHistory, navigationService.getHistory());
    }

    @Test
    public void testBackwardNav(){

        List<NavigationItem> expectedHistory = Collections.singletonList(
                new NavigationItem("A", "/A")
        );

        navigationService.registerPath(new NavigationItem("A", "/A"));
        navigationService.registerPath(new NavigationItem("B", "/B"));
        navigationService.registerPath(new NavigationItem("A", "/A"));

        Assert.assertEquals(expectedHistory, navigationService.getHistory());
    }

    @Test
    public void testMixedNav(){

        List<NavigationItem> expectedHistory = Arrays.asList(
                new NavigationItem("A", "/A"),
                new NavigationItem("B", "/B"),
                new NavigationItem("C", "/C")
        );

        navigationService.registerPath(new NavigationItem("A", "/A"));
        navigationService.registerPath(new NavigationItem("B", "/B"));
        navigationService.registerPath(new NavigationItem("D", "/D"));
        navigationService.registerPath(new NavigationItem("B", "/B"));
        navigationService.registerPath(new NavigationItem("A", "/A"));
        navigationService.registerPath(new NavigationItem("B", "/B"));
        navigationService.registerPath(new NavigationItem("C", "/C"));

        Assert.assertEquals(expectedHistory, navigationService.getHistory());
    }

}