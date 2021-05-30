package ar.edu.itba.paw.webapp.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.services.UserService;

@Component
public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
            
        UserPrincipal loggedUser = (UserPrincipal) authentication.getPrincipal();
        userService.registerLogin(loggedUser.getUser());
    }
    
}
