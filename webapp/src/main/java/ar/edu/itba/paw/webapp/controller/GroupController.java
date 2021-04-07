package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.ChatGroupService;
import ar.edu.itba.paw.webapp.mav.BaseMav;
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
    private ChatGroupService chatGroupService;

    @Autowired
    private CareerService careerService;

    @RequestMapping("/groups/byCareer")
    public ModelAndView getGroupsByCareer(
            @RequestParam(name = "careerId") int careerId
    ) {
        Optional<Career> careerOptional = careerService.findById(careerId);
        if (! careerOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrera no encontrada");
        }
        Career career = careerOptional.get();

        final ModelAndView modelAndView = new BaseMav(
            String.format("Grupos de %s", career.getName()),
            "chat_group/chat_group_full_list.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem(career.getName(), "/careers/byId?id=" + career.getId()),
                new NavigationItem("Grupos","/groups/byCareer?careerId=" + career.getId())
            )
        );

        modelAndView.addObject("chat_groups", chatGroupService.findByCareer(careerId));

        return modelAndView;
    }

}