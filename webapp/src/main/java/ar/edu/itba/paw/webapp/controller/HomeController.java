package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ModelAndView getDashboard(
        @ModelAttribute("user") User loggedUser
    ) {

        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("courses", courseService.findFavourites(loggedUser, 5));

        mav.addObject("announcements", announcementService.findRelevant(loggedUser,5));

        mav.addObject("careers", careerService.findAll());

        mav.addObject("polls", pollService.findRelevant(loggedUser.getId()));

        return mav;
    }

}