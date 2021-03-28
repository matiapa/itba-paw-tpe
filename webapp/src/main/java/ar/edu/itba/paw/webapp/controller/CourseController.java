package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.ui.BreadcrumbItem;
import ar.edu.itba.paw.models.ui.Panel;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class CourseController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    CourseService courseService;


    @RequestMapping("/courses/detail")
    public ModelAndView getCourseDetails(
            @RequestParam(name = "id") String id
    ) {
        final ModelAndView mav = new ModelAndView("main");

        Course course = courseService.findById(id).get();

        mav.addObject("title", course.getName());

        mav.addObject("breadcrumbItems", Arrays.asList(
            new BreadcrumbItem("Home", "/"),

            // TODO: Replace title with career name once carrer model is implemented

            new BreadcrumbItem(
                ""+course.getCareerId(),
                "/careers/detail?id="+course.getCareerId()
            ),

            new BreadcrumbItem(
                course.getName(),
                "/course/detail?id="+course.getId()
            )
        ));

        mav.addObject("contentViewName", "panels.jsp");

        mav.addObject("panels", Arrays.asList(
            new Panel("Grupos de chat", "",
                    "chat_group/chat_group_short_list.jsp"),

            new Panel("Contenido", "",
                    "content_source/content_short_list.jsp"),

            new Panel("Encuestas del curso", "",
                    "poll/poll_short_list.jsp"),

            new Panel("Anuncios del curso", "",
                    "announcement/announcement_short_list.jsp")
        ));

        mav.addObject("announcements", announcementService.findByCourse(course.getId()));

        return mav;
    }

}