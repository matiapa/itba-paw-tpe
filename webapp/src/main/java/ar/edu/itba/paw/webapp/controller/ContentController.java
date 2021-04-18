package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.mav.BaseMav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class ContentController {

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;

    @RequestMapping("contents")
    public ModelAndView getAnnouncements(
            //@RequestParam(name="filterBy", required = false, defaultValue="courseId") HolderEntity filterBy,

            @RequestParam(name="courseId", required = false) String courseId
    ) {
        final ModelAndView mav = new ModelAndView("contents/content_list");

        //mav.addObject("filterBy", filterBy);

        List<Content> contents = new ArrayList<>();
        //if (filterBy == HolderEntity.course) {
            List<Course> courses = courseService.findAll();
            mav.addObject("courses", courses);
            if (courseId != null) {
                contents = contentService.findByCourse(courseId);
                Course selectedCourse = courses.stream().filter(c -> c.getId().equals(courseId)).findFirst()
                        .orElseThrow(RuntimeException::new);
                mav.addObject("selectedCourse", selectedCourse);
            }
        //}

        mav.addObject("contents", contents);

        mav.addObject("user", userService.getUser());

        return mav;
    }

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
            String.format("Contenidos de %s", course.getName()),
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