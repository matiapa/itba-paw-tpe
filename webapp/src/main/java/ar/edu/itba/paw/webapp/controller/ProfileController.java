package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserData;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.SgaService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.UserPrincipal;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ProfileController {

    @Autowired UserService userService;
    @Autowired CareerService careerService;
    @Autowired SgaService sgaService;

    private static final Logger LOGGER= LoggerFactory.getLogger(ProfileController.class);

    @RequestMapping(value = "/profile/own", method = GET)
    public ModelAndView getOwnProfile() {
        return new ModelAndView("profile/profile_own");
    }

    @RequestMapping(value = "/profile/own/image", method = POST)
    public String setProfileImage(
        @RequestParam("newPicture") MultipartFile newPicture,
        @ModelAttribute("user") User loggedUser
    ) throws IOException {

//        String type = newPicture.getContentType();
//        String base64 = Base64.getEncoder().encodeToString(newPicture.getBytes());
//        String dataURI = String.format("data:%s;base64,%s", type, base64);

        userService.setPicture(loggedUser, newPicture.getBytes());

        return "redirect:/profile/own";
    }

    @RequestMapping(value = "/profile/{id:[0-9]+}", method = GET)
    public ModelAndView getProfileById(
        @PathVariable(value="id") int id,
        @ModelAttribute("user") User loggedUser
    ) {
        final ModelAndView mav = new ModelAndView("profile/profile");


        Optional<User> optUser = userService.findById(id);
        if(! optUser.isPresent()){
            LOGGER.debug("user {} in profile with id {} was not found",loggedUser,id);
            throw new ResourceNotFoundException();
        }

        Career career = optUser.get().getCareer();

        mav.addObject("profile", optUser.get());
        mav.addObject("userCareer", career);

        return mav;
    }


    @RequestMapping(value = "/profile/{id:[0-9]+}/picture", method = GET)
    public void getProfilePicture(
        @PathVariable(value="id") int id,
        @ModelAttribute("user") User loggedUser,
        HttpServletResponse response
    ) throws IOException{
        Optional<User> optUser = userService.findById(id);
        if(! optUser.isPresent()){
            LOGGER.debug("user {} in profile with id {} was not found",loggedUser,id);
            throw new ResourceNotFoundException();
        }

        byte[] image = optUser.get().getPicture();
        if (image.length == 0) {
            response.sendRedirect("/assets/img/avatars/avatar.png");
        } else {
            response.setContentType("image/png");
            response.getOutputStream().write(image);
        }

    }


    /* The purpose of this endpoint is to provide a way of showing information about not registered users
        by using the SGAService, since this one only provides and endpoint to fetch by email, not id. This
        is used for example for displaying the name of owners of contents that have been scrapped. */

    @RequestMapping(value = "/profile/{email:[a-zA-Z]+@[a-zA-Z.]+}", method = GET)
    public ModelAndView getProfileByEmail(
        @PathVariable(value="email") String email,
        @ModelAttribute("user") User loggedUser
    ) {
        final ModelAndView mav = new ModelAndView("profile/profile");


        UserData user;
        Optional<User> optUser = userService.findByEmail(email);
        user = optUser.isPresent() ? optUser.get() : sgaService.fetchFromEmail(email);

        if(user == null){
            LOGGER.debug("user {} in profile with email {} was not found",loggedUser,email);
            throw new ResourceNotFoundException();
        }

        Career career = user.getCareer();

        mav.addObject("profile", user);
        mav.addObject("userCareer", career);

        return mav;
    }


}