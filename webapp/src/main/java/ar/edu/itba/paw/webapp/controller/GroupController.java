package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.Content;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Controller
public class GroupController {

    @Autowired private ChatGroupService chatGroupService;

    @Autowired private CareerService careerService;

    @Autowired private UserService userService;

    @RequestMapping("/chats")
    public ModelAndView getChats(
        @RequestParam(name = "careerId", required = false) Integer careerId,
        @RequestParam(name = "platform", required = false) String platform,
        @RequestParam(name = "year", required = false) Integer year,
        @RequestParam(name = "quarter", required = false) Integer quarter,
        @ModelAttribute("chatGroupForm") final ChatGroupForm chatGroupForm
    ){
        final ModelAndView modelAndView = new ModelAndView("chats/chats_list");

        modelAndView.addObject("user", userService.getUser());

        // Filters

            // -- By career

        List<Career> careers = careerService.findAll();
        modelAndView.addObject("careers", careers);

        Career selected = careerId != null ? careers.stream().filter(c -> c.getId() == careerId).findFirst()
                .orElseThrow(RuntimeException::new) : null;
        modelAndView.addObject("selectedCareer", selected);

            // -- By platform

        modelAndView.addObject("platforms", ChatGroup.ChatPlatform.values());
        modelAndView.addObject("platformsTranslate", new HashMap<ChatGroup.ChatPlatform, String>()
        {{
            put(ChatGroup.ChatPlatform.discord, "Discord");
            put(ChatGroup.ChatPlatform.whatsapp, "WhatsApp");
        }});

        ChatGroup.ChatPlatform selectedPlatform = platform != null && !platform.equals("")
                ? ChatGroup.ChatPlatform.valueOf(platform) : null;
        modelAndView.addObject("selectedPlatform", selectedPlatform);

            // -- By year

        modelAndView.addObject("years", IntStream.range(1960, 2022).boxed()
                .sorted(Comparator.reverseOrder()).collect(toList()));

        Integer selectedYear = year != null && year > 0 ? year : null;
        modelAndView.addObject("selectedYear", selectedYear);

            // -- By quarter

        modelAndView.addObject("quarters", new Integer[]{1, 2});
        modelAndView.addObject("quartersTranslate", new HashMap<Integer, String>()
        {{
            put(1, "Primero");
            put(2, "Segundo");
        }});

        Integer selectedQuarter = quarter != null && quarter > 0 ? quarter : null;
        modelAndView.addObject("selectedQuarter", selectedQuarter);

        // Chats

        List<ChatGroup> chatGroupList = new ArrayList<>();
        if(careerId != null){
            chatGroupList = chatGroupService.findByCareer(careerId, selectedPlatform, selectedYear, selectedQuarter);
        }

        modelAndView.addObject("chatgroups", chatGroupList);

        return modelAndView;
    }

    @RequestMapping(value = "/chats", method = {RequestMethod.POST})
    public ModelAndView addGroup(
            @Valid @ModelAttribute("chatGroupForm") final ChatGroupForm chatGroupForm,
            final BindingResult errors
    ) {
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


        return new ModelAndView("redirect:/chats");
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