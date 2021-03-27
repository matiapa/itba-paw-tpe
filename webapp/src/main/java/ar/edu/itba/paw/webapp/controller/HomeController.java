package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Announcement;
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
    UserService userService;

    @RequestMapping("/")
    public ModelAndView helloWorld(
//            @RequestParam(name = "email") String email,
//            @RequestParam(name = "pass") String pass
    ) {
        final ModelAndView mav = new ModelAndView("main");

//        mav.addObject("user", userService.register(email, pass));

        mav.addObject("params", new MainParams(
                "Home", "Todas las carreras", "Tus cursos favoritos",
                "Encuestas generales", "Anuncios generales"
        ));

        List<Announcement> announcements = Arrays.asList(
                new Announcement("Anuncio 1"),
                new Announcement("Anuncio 2")
        );

        mav.addObject("announcements", announcements);

        return mav;
    }

}