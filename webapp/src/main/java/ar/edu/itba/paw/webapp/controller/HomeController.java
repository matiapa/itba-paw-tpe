package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.viewparams.MainParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    AnnouncementService announcementService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("main");

        mav.addObject("params", new MainParams(
                "Home", "Todas las carreras", "Tus cursos favoritos",
                "Encuestas generales", "Anuncios generales"
        ));

        mav.addObject("announcements", announcementService.findAll());

        return mav;
    }

}