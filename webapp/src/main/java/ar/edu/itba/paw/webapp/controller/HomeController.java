package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



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