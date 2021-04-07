package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.models.ui.Panel;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.mav.BaseMav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;

@Controller
public class HomeController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private CourseService courseService;

    @Autowired private CareerService careerService;

    @Autowired private PollService pollService;

    @RequestMapping("/")
    public ModelAndView getDashboard() {

        final ModelAndView mav = new BaseMav(
            "Home",
            "panels.jsp",
                Collections.singletonList(
                        new NavigationItem("Home", "/")
                )
        );

        String favCoursesView;
        try{
            favCoursesView = "course/course_short_list.jsp";
            mav.addObject("courses", courseService.findFavourites(4));
        }catch (LoginRequiredException e){
            favCoursesView = "user/login_required.jsp";
            mav.addObject("redirectTo", "/home");
        }

        mav.addObject("panels", Arrays.asList(
            new Panel("Todas las carreras", null,
                    "career/career_full_list.jsp"),

            new Panel("Tus cursos favoritos", "/courses/favourites",
                    favCoursesView),

            new Panel("Encuestas generales", "/polls/general",
                    "poll/poll_short_list.jsp"),

            new Panel("Anuncios generales", "",
                    "announcement/announcement_list.jsp")
        ));

        mav.addObject("announcements", announcementService.findGeneral());

        mav.addObject("careers", careerService.findAll());

        mav.addObject("polls", pollService.findGeneral());
        return mav;
    }

}