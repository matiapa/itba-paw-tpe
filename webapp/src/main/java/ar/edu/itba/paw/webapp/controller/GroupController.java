package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.ChatGroupService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.form.ChatGroupForm;
import ar.edu.itba.paw.webapp.mav.BaseMav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class GroupController {

    @Autowired private ChatGroupService chatGroupService;

    @Autowired private CareerService careerService;

    @Autowired private UserService userService;

    @RequestMapping("/chats")
    public ModelAndView getChats(
            @RequestParam(name="filterBy", required = false, defaultValue = "general")HolderEntity filterBy,
            @RequestParam(name = "careerId", required = false) Integer careerId,
            @ModelAttribute("chatGroupForm") final ChatGroupForm chatGroupForm
    ){
        final ModelAndView modelAndView = new ModelAndView("chats/chats_list");
        modelAndView.addObject("filterBy", filterBy);

        List<ChatGroup> chatGroupList = new ArrayList<>();
        List<Career> careers = careerService.findAll();
        modelAndView.addObject("careers", careers);
        switch (filterBy){
            case career:

                if (careerId != null){
                    chatGroupList = chatGroupService.findByCareer(careerId);
                    Career selected = careers.stream().filter(c -> c.getId() == careerId).findFirst()
                            .orElseThrow(RuntimeException::new);
                    modelAndView.addObject("selectedCareer", selected);
                }
                break;
            case general:
            default:
                chatGroupList = chatGroupService.getChatGroups();
                break;
        }
        modelAndView.addObject("chats", chatGroupList);
        modelAndView.addObject("user", userService.getUser());
        return modelAndView;
    }


    @RequestMapping(value = "/chats", method = {RequestMethod.POST})
    public ModelAndView addGroup(
            @Valid @ModelAttribute("chatGroupForm") final ChatGroupForm chatGroupForm,
            final BindingResult errors
    ) {

        System.out.println(chatGroupForm.getGroupName());
        System.out.println(chatGroupForm.getGroupCareer());
        System.out.println(chatGroupForm.getLink());
        System.out.println(chatGroupForm.getGroupDate());
/*
        if (errors.hasErrors()) {
            throw new RuntimeException("Error");
            //return getChats(HolderEntity.general, null, chatGroupForm);
        }


*/
        chatGroupService.addGroup(chatGroupForm.getGroupName(),
                chatGroupForm.getGroupCareer(),
                chatGroupForm.getLink(),
                userService.getUser().getId(),
                chatGroupForm.getGroupDate());


        return new ModelAndView("redirect:chats/chats_list");
    }



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