package ar.edu.itba.paw.webapp.controller;


import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.auth.UserPrincipal;
import ar.edu.itba.paw.webapp.form.UserForm;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public ModelAndView createForm(@ModelAttribute("UserForm") final UserForm form, @AuthenticationPrincipal User fetchedUser) {
        ModelAndView mav = new ModelAndView("register/register");
        mav.addObject("user", fetchedUser);
        return mav;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute("UserForm") final UserForm form, @AuthenticationPrincipal UserPrincipal fetchedUser){
        final User user = userService.registerUser(fetchedUser.getId(), fetchedUser.getName(), fetchedUser.getSurname(), fetchedUser.getEmail(), fetchedUser.getCareer_id());
        if(user != null)
        {
            OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            OAuth2User principal = new UserPrincipal(user, authorities, fetchedUser.getIdToken(), fetchedUser.getUserInfo());
            Authentication newAuth = new OAuth2AuthenticationToken(principal, authorities, auth.getAuthorizedClientRegistrationId());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        return new ModelAndView("redirect:/");
    }
}
