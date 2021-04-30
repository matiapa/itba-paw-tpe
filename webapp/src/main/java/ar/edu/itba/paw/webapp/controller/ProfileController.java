package ar.edu.itba.paw.webapp.controller;

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

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ProfileController {

    @Autowired UserService userService;

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

}