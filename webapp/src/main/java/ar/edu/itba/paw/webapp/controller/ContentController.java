package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.mav.BaseMav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class ContentController {

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;

    @RequestMapping("contents")
    public ModelAndView getContents(
        @RequestParam(name="courseId", required = false) String courseId,
        @RequestParam(name="contentType", required = false) String contentType,
        @RequestParam(name="minDate",required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date minDate,
        @RequestParam(name="maxDate",required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date maxDate
    ) {
        final ModelAndView mav = new ModelAndView("contents/content_list");

        List<Course> courses = courseService.findAll();
        mav.addObject("courses", courses);

        List<Content> contents = new ArrayList<>();

        if (courseId != null) {
            contentType = contentType.equals("") ? null : contentType;
            contents = contentService.findContent(courseId, contentType, minDate, maxDate);

            Course selectedCourse = courses.stream().filter(c -> c.getId().equals(courseId)).findFirst()
                    .orElseThrow(RuntimeException::new);
            mav.addObject("selectedCourse", selectedCourse);
        }

        mav.addObject("contents", contents);

        mav.addObject("user", userService.getUser());

        return mav;
    }



}