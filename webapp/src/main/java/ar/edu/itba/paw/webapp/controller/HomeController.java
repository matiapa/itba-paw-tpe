package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.form.ChatGroupForm;
import ar.edu.itba.paw.webapp.form.CourseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class HomeController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private CourseService courseService;

    @Autowired private CareerService careerService;

    @Autowired private PollService pollService;

    @RequestMapping("/")
    public ModelAndView getDashboard(
        @ModelAttribute("user") User loggedUser,
        @ModelAttribute("courseForm") final CourseForm courseForm
        ) {

        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("courses", courseService.findFavourites(loggedUser));

        mav.addObject("allCourses", courseService.findAll());

        mav.addObject("announcements", announcementService.findRelevant(loggedUser,5));

        mav.addObject("careers", careerService.findAll());

        mav.addObject("polls", pollService.findRelevant(loggedUser.getId()));

        return mav;
    }

}