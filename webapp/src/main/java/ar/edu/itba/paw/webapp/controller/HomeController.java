package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.ui.BreadcrumbItem;
import ar.edu.itba.paw.models.ui.Panel;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class HomeController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    CourseService courseService;


    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("main");

        mav.addObject("title", "Home");

        mav.addObject("breadcrumbItems", Arrays.asList(
            new BreadcrumbItem("Home", "/")
        ));

        mav.addObject("contentViewName", "panels.jsp");

        mav.addObject("panels", Arrays.asList(
            new Panel("Todas las carreras", null,
                    "career/career_full_list.jsp"),

            new Panel("Tus cursos favoritos", "/courses/favourites",
                    "course/course_short_list.jsp"),

            new Panel("Encuestas generales", "",
                    "poll/poll_short_list.jsp"),

            new Panel("Anuncios generales", "",
                    "announcement/announcement_list.jsp")
        ));

        mav.addObject("announcements", announcementService.findGeneral());

        mav.addObject("courses", courseService.findFavourites(1));

        return mav;
    }

}