package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ui.BreadcrumbItem;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.ChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class GroupController {

    @Autowired
    ChatGroupService chatGroupService;

    @Autowired
    CareerService careerService;

    @RequestMapping("/groups/byCareer")
    public ModelAndView getGroupsByCareer(
            @RequestParam(name = "careerId") int careerId
    ) {
        final ModelAndView modelAndView = new ModelAndView("main");

        Optional<Career> careerOptional = careerService.findById(careerId);
        if (! careerOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrera no encontrada");
        }
        Career career = careerOptional.get();

        modelAndView.addObject("title", String.format("Grupos de %s", career.getName()));
        modelAndView.addObject("breadcrumbItems", Arrays.asList(
                new BreadcrumbItem("Home", "/"),
                new BreadcrumbItem(career.getName(), "/careers/byId?id="+ career.getId()),
                new BreadcrumbItem("Groups of " + career.getName(),
                        "/groups/byCareer?careerId="+ career.getId())
        ));

        modelAndView.addObject("contentViewName", "chat_group/chat_group_full_list.jsp");
        modelAndView.addObject("chat_groups", chatGroupService.findByCareer(careerId));

        return modelAndView;
    }
}
