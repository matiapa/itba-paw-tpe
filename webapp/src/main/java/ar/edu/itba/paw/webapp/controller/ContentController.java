package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.webapp.mav.BaseMav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class ContentController {

    @Autowired ContentService contentService;

    @Autowired CourseService courseService;


    @RequestMapping("/contents/byCourse")
    public ModelAndView getContentsByCourse(
            @RequestParam(name = "courseId") String courseId
    ) {
        Optional<Course> courseOptional = courseService.findById(courseId);
        if(! courseOptional.isPresent()){
            throw new ResponseStatusException(NOT_FOUND, "Curso no encontrado");
        }
        Course course = courseOptional.get();

        final ModelAndView mav = new BaseMav(
            ""+String.format("Contenidos de %s", course.getName()),
            "content_source/content_full_list.jsp",
            Arrays.asList(
                    new NavigationItem("Home", "/"),
                    new NavigationItem(course.getName(), "/courses/byId?id="+ course.getId()),
                    new NavigationItem("Contenido de " + course.getName(),
                            "/contents/byCourse?courseId="+course.getId())
            )
        );

        mav.addObject("contents", contentService.findByCourse(course.getId()));

        return mav;
    }

}