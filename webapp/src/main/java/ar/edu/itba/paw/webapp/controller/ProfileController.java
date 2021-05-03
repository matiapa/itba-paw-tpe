package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
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

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ProfileController {

    @Autowired UserService userService;
    @Autowired CareerService careerService;
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
        String type = newPicture.getContentType();
        String base64 = Base64.getEncoder().encodeToString(newPicture.getBytes());
        String dataURI = String.format("data:%s;base64,%s", type, base64);

        userService.setProfilePicture(loggedUser, dataURI);

        return "redirect:/profile/own";
    }

    @RequestMapping(value = "/profile/{id}", method = GET)
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


        Optional<Career> optCareer = careerService.findByCode(optUser.get().getCareerCode());
        if(! optCareer.isPresent()){
            LOGGER.debug("user {} in profile cant find career with code {}",loggedUser,optUser.get().getCareerCode());
            throw new ResourceNotFoundException();
        }


        mav.addObject("userCareer", optCareer.get());

        return mav;
    }

}