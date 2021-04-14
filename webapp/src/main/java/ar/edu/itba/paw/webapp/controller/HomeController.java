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

    @Autowired private UserService userService;

    @Autowired private AnnouncementService announcementService;

    @Autowired private CourseService courseService;

    @Autowired private CareerService careerService;

    @Autowired private PollService pollService;

    @RequestMapping("/")
    public ModelAndView getDashboard() {

        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("user", userService.getUser());

        mav.addObject("courses", courseService.findFavourites(4));

        mav.addObject("announcements", announcementService.findGeneral());

        mav.addObject("careers", careerService.findAll());

        mav.addObject("polls", pollService.findGeneral());

        return mav;
    }

}