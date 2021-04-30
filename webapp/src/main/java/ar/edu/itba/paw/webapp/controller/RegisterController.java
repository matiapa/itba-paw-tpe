package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.common.FiltersController;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class RegisterController {




    @Autowired private CareerService careerService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;

    @RequestMapping(value ="/register", method = GET)
    public ModelAndView getRegister(
            @ModelAttribute("UserForm") final UserForm form
    ) {

        final ModelAndView mav = new ModelAndView("register/register");

        mav.addObject("careerList", careerService.findAll());

        mav.addObject("courseList",courseService.findAll());

        return mav;
    }


    @RequestMapping(value ="/register", method = POST)
    public ModelAndView RegisterUser(
            @ModelAttribute("UserForm") final UserForm form,final BindingResult errors
    ) {

        final ModelAndView mav = new ModelAndView("register/register");


        if (errors.hasErrors()){
            return new ModelAndView("register/register");
        }

        userService.registerUser(form.getId(),form.getName(),form.getSurname(),form.getEmail(),form.getCareerCode(),form.getCourses());

        mav.addObject("careerList", careerService.findAll());

        mav.addObject("courseList",courseService.findAll());

        return mav;
    }
}
