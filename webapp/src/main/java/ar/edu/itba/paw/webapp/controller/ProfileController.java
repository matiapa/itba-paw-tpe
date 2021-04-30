package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ProfileController {

    @Autowired UserService userService;
    @Autowired CareerService careerService;

    @RequestMapping(value = "/profile/own", method = GET)
    public ModelAndView getOwnProfile() {
        final ModelAndView mav = new ModelAndView("profile/profile_own");

        mav.addObject("user", userService.getLoggedUser());

        return mav;
    }

    @RequestMapping(value = "/profile/own/image", method = POST)
    public String setProfileImage(
        @RequestParam("newPicture") MultipartFile newPicture
    ) throws IOException {
        String type = newPicture.getContentType();
        String base64 = Base64.getEncoder().encodeToString(newPicture.getBytes());
        String dataURI = String.format("data:%s;base64,%s", type, base64);

        userService.setProfilePicture(dataURI);

        return "redirect:/profile/own";
    }

    @RequestMapping(value = "/profile", method = GET)
    public ModelAndView getProfileById(
        @RequestParam(name="id") int id
    ) {
        final ModelAndView mav = new ModelAndView("profile/profile");

        Optional<User> optUser = userService.findById(id);
        if(! optUser.isPresent())
            // TODO: Replace this exception
            throw new RuntimeException();

        mav.addObject("user", optUser.get());

        Optional<Career> optCareer = careerService.findByCode(optUser.get().getCareerCode());
        if(! optCareer.isPresent())
            // TODO: Replace this exception
            throw new RuntimeException();

        mav.addObject("userCareer", optCareer.get());

        return mav;
    }

}