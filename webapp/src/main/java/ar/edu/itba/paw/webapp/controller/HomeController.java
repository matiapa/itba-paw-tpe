package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.ui.BreadcrumbItem;
import ar.edu.itba.paw.models.ui.Panel;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.models.ui.MainParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class HomeController {

    @Autowired
    AnnouncementService announcementService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("main");

        mav.addObject("params", new MainParams(
            "Home",
            Arrays.asList(new BreadcrumbItem("Home", "/")),
            new Panel("Todas las carreras", null, "career_full_list.jsp"),
            new Panel("Tus cursos favoritos", "/courses/fav", "course_short_list.jsp"),
            new Panel("Encuestas generales", "/polls/general", "poll_short_list.jsp"),
            new Panel("Anuncios generales", "/announcements/general", "announcement_short_list.jsp")
        ));

        mav.addObject("announcements", announcementService.findAll());

        return mav;
    }

}