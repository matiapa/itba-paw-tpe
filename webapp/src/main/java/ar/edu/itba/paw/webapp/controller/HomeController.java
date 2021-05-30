package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.PollService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.UserPrincipal;
import ar.edu.itba.paw.webapp.exceptions.BadRequestException;
import ar.edu.itba.paw.webapp.form.CourseForm;


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

        mav.addObject("announcements", announcementService.findRelevant(loggedUser,0, 5));

        mav.addObject("careers", careerService.findAll());

        // mav.addObject("relevantPolls", pollService.findRelevant(loggedUser.getId()));
        // mav.addObject("controversialPolls", pollService.findControversial(loggedUser.getId()));

        return mav;
    }

}