package ar.edu.itba.paw.webapp.controller.common;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;


@ControllerAdvice
public class CommonAttributes {

    @Autowired private UserService userService;
    @Autowired private CareerService careerService;

    @ModelAttribute("user")
    User loggedUser(){
        String email = "mapablaza@itba.edu.ar";
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByEmail(email).orElse(null);
    }

}