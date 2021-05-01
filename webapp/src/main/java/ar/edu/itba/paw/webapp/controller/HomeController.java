package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;


@Controller
public class HomeController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private CourseService courseService;

    @Autowired private CareerService careerService;

    @Autowired private PollService pollService;

    @Autowired private UserService userService;

    @RequestMapping("/")
    public ModelAndView getDashboard() {

        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("user", userService.getLoggedUser());

        mav.addObject("courses", courseService.findFavourites(userService.getLoggedUser(), 4));

        mav.addObject("announcements", announcementService.findGeneral(0, 5));

        mav.addObject("careers", careerService.findAll());

        mav.addObject("polls", pollService.findGeneral());

        return mav;
    }

}