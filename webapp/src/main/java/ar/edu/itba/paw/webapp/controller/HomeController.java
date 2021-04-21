package ar.edu.itba.paw.webapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.PollService;


@Controller
public class HomeController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private CourseService courseService;

    @Autowired private CareerService careerService;

    @Autowired private PollService pollService;

    @RequestMapping("/")
    public ModelAndView getDashboard(@AuthenticationPrincipal User user) {

        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("user", user);

        mav.addObject("courses", courseService.findFavourites(user, 4));

        mav.addObject("announcements", announcementService.findGeneral());

        mav.addObject("careers", careerService.findAll());

        mav.addObject("polls", pollService.findGeneral());

        return mav;
    }

}