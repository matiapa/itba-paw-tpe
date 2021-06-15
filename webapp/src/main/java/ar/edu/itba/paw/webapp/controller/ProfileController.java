package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.Optional;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserWorkRateService;
import ar.edu.itba.paw.webapp.form.UserWorkRateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserData;
import ar.edu.itba.paw.services.SgaService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@Controller
public class ProfileController {

    @Autowired UserService userService;
    @Autowired SgaService sgaService;
    @Autowired UserWorkRateService rateService;
    @Autowired CourseService courseService;

    private static final Logger LOGGER= LoggerFactory.getLogger(ProfileController.class);


    /* -------------------- OWN PROFILE METHODS -------------------- */

    @RequestMapping(value = "/profile/own", method = GET)
    public ModelAndView getOwnProfile() {
        return new ModelAndView("profile/profile_own");
    }


    @RequestMapping(value = "/profile/own/image", method = POST)
    public String setProfileImage(
        @RequestParam("newPicture") MultipartFile newPicture,
        @ModelAttribute("user") User loggedUser
    ) throws IOException {

        userService.setPicture(loggedUser, newPicture.getBytes());

        return "redirect:/profile/own";
    }


    /* -------------------- OTHER'S PROFILE METHODS -------------------- */

    @RequestMapping(value = "/profile", method = GET)
    public ModelAndView getProfiles(
        @RequestParam(name="personId", required=false) Integer personId,
        @ModelAttribute("user") User loggedUser
    ) {
        if (personId != null) {
            return new ModelAndView("redirect:/profile/"+personId);
        }

        final ModelAndView mav = new ModelAndView("profile/profile_list");

        List<User> users = userService.findAll();

        users.forEach(System.out::println);

        mav.addObject("profiles", users);

        return mav;
    }


    @RequestMapping(value = "/profile/{id:[0-9]+}", method = GET)
    public ModelAndView getProfileById(
        @PathVariable(value="id") int id,
        @ModelAttribute("createForm") final UserWorkRateForm form,
        @RequestParam(name="showCreateForm", required=false, defaultValue="false") boolean showCreateForm,
        @ModelAttribute("user") User loggedUser
    ) {
        final ModelAndView mav = new ModelAndView("profile/profile_registered");

        Optional<User> optUser = userService.findById(id);
        if(! optUser.isPresent()){
            LOGGER.debug("user {} in profile with id {} was not found",loggedUser,id);
            throw new ResourceNotFoundException();
        }

        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("profile", optUser.get());
        mav.addObject("rates", rateService.getRates(optUser.get(), 0, 5));
        mav.addObject("courses", courseService.findAll());

        return mav;
    }


    @RequestMapping(value = "/profile/{id:[0-9]+}/picture", method = GET)
    public void getProfilePicture(
        @PathVariable(value="id") int id,
        @ModelAttribute("user") User loggedUser,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException{
        Optional<User> optUser = userService.findById(id);
        if(! optUser.isPresent()){
            LOGGER.debug("user {} in profile with id {} was not found",loggedUser,id);
            throw new ResourceNotFoundException();
        }

        byte[] image = optUser.get().getPicture();
        if (image == null) {
            response.sendRedirect(request.getContextPath() + "/assets/img/avatars/avatar.png");
        } else {
            response.setContentType("image/png");
            response.getOutputStream().write(image);
        }

    }


    @RequestMapping(value = "/profile/{id:[0-9]+}/rate", method = POST)
    public ModelAndView rateProfile(
            @PathVariable(value="id") int id,
            @ModelAttribute("user") User loggedUser,
            @Valid @ModelAttribute("createForm") final UserWorkRateForm form,
            final BindingResult errors
    ) {
        if (errors.hasErrors()) {
            System.out.println(errors);
            return getProfileById(id, form, true, loggedUser);
        }

        User ratedUser = userService.findById(id).orElseThrow(ResourceNotFoundException::new);

        Course course = courseService.findById(form.getCourseId())
                .orElseThrow(ResourceNotFoundException::new);

        rateService.rate(loggedUser, ratedUser, course, form.getBehaviour(), form.getSkill(), form.getComment());

        return getProfileById(id, form, false, loggedUser);
    }


    /* The purpose of this endpoint is to provide a way of showing information about not registered users
        by using the SGAService, since this one only provides and endpoint to fetch by email, not id. This
        is used for example for displaying the name of owners of contents that have been scrapped. */

    @RequestMapping(value = "/profile/{email:[a-zA-Z]+@[a-zA-Z.]+}", method = GET)
    public ModelAndView getProfileByEmail(
            @PathVariable(value="email") String email,
            @ModelAttribute("user") User loggedUser
    ) {

        // Try to find it on database, if found, redirect to ID endpoint (for registered users)

        UserData user;
        Optional<User> optUser = userService.findByEmail(email);

        if (optUser.isPresent()) {
            return new ModelAndView("redirect:/profile/" + optUser.get().getId());
        }

        // If not found, try to find it from SGA service and show unregistered profile page

        user = sgaService.fetchFromEmail(email);

        if(user == null){
            LOGGER.debug("user {} in profile with email {} was not found",loggedUser,email);
            throw new ResourceNotFoundException();
        }

        ModelAndView mav = new ModelAndView("profile/profile_unregistered");

        mav.addObject("profile", user);

        return mav;

    }

}