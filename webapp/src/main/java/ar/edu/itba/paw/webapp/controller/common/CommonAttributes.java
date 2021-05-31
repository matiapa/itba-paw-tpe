package ar.edu.itba.paw.webapp.controller.common;

import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.Permission;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.edu.itba.paw.webapp.auth.UserPrincipal;


@ControllerAdvice
public class CommonAttributes {

    @Autowired private UserService userService;

    @ModelAttribute("user")
    User loggedUser(){
        if(SecurityContextHolder.getContext().getAuthentication() == null)
            return null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!(principal instanceof UserPrincipal))
            return null;

        // TODO: Add an invalid state exception showing error 500

        return userService.findByEmail(((UserPrincipal) principal).getUsername())
                .orElseThrow(RuntimeException::new);
    }

    @ModelAttribute("canReadStats")
    boolean canReadStats() {
        return loggedUser().can(Permission.Action.read, Entity.statistic);
    }

}