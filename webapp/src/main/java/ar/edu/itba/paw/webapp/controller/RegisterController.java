package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.BadRequestException;
import ar.edu.itba.paw.webapp.form.UserForm;


@Controller
public class RegisterController {

    @Autowired private CareerService careerService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;

    private static final Logger LOGGER= LoggerFactory.getLogger(RegisterController.class);


    @RequestMapping(value ="/register", method = GET)
    public ModelAndView getRegister(
        @ModelAttribute("UserForm") final UserForm form,
        @RequestParam(name="emailTaken" ,required = false) boolean emailTaken,
        @RequestParam(name="idTaken" ,required = false) boolean idTaken
    ) {
        final ModelAndView mav = new ModelAndView("register/register");

        List <Career> careers=careerService.findAll();
        //filtrar la entrada seleccionada para no tener duplicados
        if (form.getCareerCode()!=null){
            careers.removeIf(x -> (x.getCode().equals(form.getCareerCode())));
        }

        mav.addObject("careerList",careers );
        mav.addObject("selectedCareer",careerService.findByCode(form.getCareerCode()));


        List<Course> selectedCourses=new ArrayList<>();
        if (form.getCourses()!=null&&form.getCourses().size()>0){
            Set<String> codes=new HashSet<>(form.getCourses());
            codes.removeIf(Objects::isNull);
            for (String code: codes) {
                Optional<Course>optionalCourse=courseService.findById(code);
                optionalCourse.ifPresent(selectedCourses::add);
            }
        }

        mav.addObject("courseList", courseService.findAll());
        mav.addObject("selectedCourses",selectedCourses);

        mav.addObject("emailTaken",emailTaken);
        mav.addObject("idTaken",idTaken);



        return mav;
    }


    @RequestMapping(value ="/register", method = POST)
    public ModelAndView RegisterUser( HttpServletRequest request,
        @Valid @ModelAttribute("UserForm") final UserForm form, final BindingResult errors
    ) throws IOException {
        boolean emailTaken=userService.findByEmail(form.getEmail()).isPresent();
        boolean idTaken=false;
        if (form.getId()!=null)
            idTaken=userService.findById(form.getId()).isPresent();

        if (errors.hasErrors()||emailTaken||idTaken){
            LOGGER.debug("register Userform {} is invalid with errors {}",form,errors);
            return getRegister(form,emailTaken,idTaken);
        }

        String websiteUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build()
                .toString() + request.getContextPath();

        LOGGER.debug("user is attempting to register a new user with form {}",form);
        
        Stream<Optional<Course>> coursesOpt = form
            .getCourses()
            .stream()
            .filter(Objects::nonNull)
            .map(code -> courseService.findById(code));
        if(!coursesOpt.allMatch(Optional<Course>::isPresent))
            throw new BadRequestException();

        List<Course> courses = form
                .getCourses()
                .stream()
                .filter(Objects::nonNull)
                .map(code -> courseService.findById(code)).map(Optional::get)
                .collect(Collectors.toList());

        Optional<Career> optCareer = careerService.findByCode(form.getCareerCode());
        if(!optCareer.isPresent())
            throw new BadRequestException();
        
        userService.registerUser(
            form.getId(), form.getName(), form.getSurname(), form.getEmail(),
            new BCryptPasswordEncoder().encode(form.getPassword()), optCareer.get(),
            courses, websiteUrl, LocaleContextHolder.getLocale());

        LOGGER.debug("user registered a new user with form {}",form);

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
        boolean worked = userService.verifyEmail(userOptional.get(),verification_code);
        LOGGER.debug("verified user with email {} and code {}",email,verification_code);

        mav.addObject("worked", worked);

        return mav;
    }

}