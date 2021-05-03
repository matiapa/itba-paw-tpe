package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class RegisterController {

    @Autowired private CareerService careerService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;

    @Autowired private EmailService emailService;

    private static final Logger LOGGER= LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value ="/register", method = GET)
    public ModelAndView getRegister(
        @ModelAttribute("UserForm") final UserForm form
    ) {

        final ModelAndView mav = new ModelAndView("register/register");

        mav.addObject("careerList", careerService.findAll());

        mav.addObject("courseList", courseService.findAll());

        return mav;
    }


    @RequestMapping(value ="/register", method = POST)
    public ModelAndView RegisterUser( HttpServletRequest request,
        @Valid @ModelAttribute("UserForm") final UserForm form, final BindingResult errors
    ) {
        if (errors.hasErrors()){
            LOGGER.debug("register Userform {} is invalid with errors {}",form,errors);
            return getRegister(form);
        }
        LOGGER.debug("user is attempting to register a new user with form {}",form);
        userService.registerUser(
            form.getId(), form.getName(), form.getSurname(), form.getEmail(),
            new BCryptPasswordEncoder().encode(form.getPassword()), form.getCareerCode(),
            form.getCourses()
        );
        LOGGER.debug("user registered a new user with form {}",form);
        LOGGER.debug("attempting to send verification email for user with id {}",form.getId());
        emailService.sendVerificationEmail(
            form.getEmail(), ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build()
                .toString() + request.getContextPath()
        );
        LOGGER.debug("verification email sent for user with id {}",form.getId());

        return new ModelAndView("register/pending_email_verification");
    }

    @RequestMapping(value ="/register/verification")
    public ModelAndView VerifyUser(
        @RequestParam(name="verificationCode") Integer verification_code,
        @RequestParam(name="email") String email
    ) {
        final ModelAndView mav = new ModelAndView("register/validate_email");

        Optional<User> userOptional= userService.findByEmail(email);

        if (!userOptional.isPresent()){
            LOGGER.debug("verification, no user with email {} could be found",email);
           throw new RuntimeException( "email not found");
        }
        LOGGER.debug("attempting to verify user with email {} and code {}",email,verification_code);
        boolean worked = userService.verifyEmail(userOptional.get().getId(),verification_code);
        LOGGER.debug("verified user with email {} and code {}",email,verification_code);

        mav.addObject("worked", worked);

        return mav;
    }

}