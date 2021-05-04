package ar.edu.itba.paw.webapp.controller.common;

import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.Permission;
import ar.edu.itba.paw.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.paw.webapp.auth.UserPrincipal;


@ControllerAdvice
public class CommonAttributes {

    @ModelAttribute("user")
    UserPrincipal loggedUser(){
        if(SecurityContextHolder.getContext().getAuthentication() == null)
            return null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof UserPrincipal ? (UserPrincipal)principal : null;
    }

    @ModelAttribute("canReadStats")
    boolean canReadStats(){
        User loggedUser = loggedUser();
        return loggedUser != null && loggedUser().getPermissions().contains(
                new Permission(Permission.Action.read, Entity.statistic));
    }

}