package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.ui.NavigationItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Controller
public class ErrorController{

    @RequestMapping(value="/error/404")
    public ModelAndView error404(){
        final ModelAndView mav = new ModelAndView("main");

        mav.addObject("navigationHistory", Collections.singletonList(
                new NavigationItem("Home", "/")));
        mav.addObject("title", "Página no encontrada");
        mav.addObject("contentViewName", "not_found.jsp");

        return mav;
    }

}