package ar.edu.itba.paw.webapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView getLogin() {
        final ModelAndView mav = new ModelAndView("login");
            return mav;
    }

    @RequestMapping("/loginSuccess")
    public String getLoginInfo(@AuthenticationPrincipal OAuth2User user) {
        System.out.println(user.getAttributes());
        return "loginSuccess";
    }
}
