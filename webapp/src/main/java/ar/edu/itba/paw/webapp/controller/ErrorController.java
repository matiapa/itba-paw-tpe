package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController{

    @RequestMapping("/error/404")
    public ModelAndView error404(){
        final ModelAndView mav = new ModelAndView("simple");
        mav.addObject("text", "Página no encontrada");
        return mav;
    }

}