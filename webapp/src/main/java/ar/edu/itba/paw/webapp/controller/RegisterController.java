package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.controller.common.FiltersController;
import ar.edu.itba.paw.webapp.form.UserForm;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class RegisterController {




    @Autowired private CareerService careerService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;

    @Autowired private EmailService emailService;

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
        new BCryptPasswordEncoder().encode(form.getPassword());
        userService.registerUser(form.getId(),form.getName(),form.getSurname(),form.getEmail(),new BCryptPasswordEncoder().encode(form.getPassword()),form.getCareerCode(),form.getCourses());

        emailService.sendVerificationEmail(form.getEmail());

        mav.addObject("careerList", careerService.findAll());

        mav.addObject("courseList",courseService.findAll());

        return mav;
    }

    @RequestMapping(value ="/register/verification")
    public ModelAndView VerifyUser(
            @RequestParam(name="verificationCode", required = true) String verification_code,
            @RequestParam(name="email",required = true)String email
    ) {
        Integer.getInteger(verification_code);

        Optional<User> userOptional= userService.findByEmail(email);


       if (!userOptional.isPresent()){
           throw new RuntimeException( "email not found");
       }

        userService.verifyEmail(userOptional.get().getId(),Integer.getInteger(verification_code));

        return new ModelAndView("redirect:/");
    }
}
