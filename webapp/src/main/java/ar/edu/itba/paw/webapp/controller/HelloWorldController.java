package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public ModelAndView helloWorld(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "pass") String pass
    ) {
        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("user", userService.register(email, pass));

        return mav;
    }

}