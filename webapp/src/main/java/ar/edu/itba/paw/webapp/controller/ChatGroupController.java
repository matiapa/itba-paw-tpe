package ar.edu.itba.paw.webapp.controller;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.ChatGroupService;
import ar.edu.itba.paw.webapp.form.ChatGroupForm;

@Controller
public class ChatGroupController {

    @Autowired private ChatGroupService chatGroupService;

    @Autowired private CareerService careerService;

    @RequestMapping("/chats")
    public ModelAndView get(
        @RequestParam(name = "careerId", required = false) Integer careerId,
        @RequestParam(name = "platform", required = false) String platform,
        @RequestParam(name = "year", required = false) Integer year,
        @RequestParam(name = "quarter", required = false) Integer quarter,
        @ModelAttribute("chatGroupForm") final ChatGroupForm chatGroupForm,
        @AuthenticationPrincipal User user
    ){
        final ModelAndView modelAndView = new ModelAndView("chats/chats_list");

        modelAndView.addObject("user", user);

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


    @RequestMapping("chats/create")
    public ModelAndView create(
        @ModelAttribute("createForm") final ChatGroupForm form,
        @AuthenticationPrincipal User user
    ) {
        ModelAndView mav = new ModelAndView("chats/create_chat");

        mav.addObject("careers", careerService.findAll());

        mav.addObject("platforms", ChatGroup.ChatPlatform.values());
        mav.addObject("platformsTranslate", new HashMap<ChatGroup.ChatPlatform, String>()
        {{
            put(ChatGroup.ChatPlatform.discord, "Discord");
            put(ChatGroup.ChatPlatform.whatsapp, "WhatsApp");
        }});

        mav.addObject("user", user);

        return mav;
    }


    @RequestMapping(value = "chats/create", method = {RequestMethod.POST})
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final ChatGroupForm chatGroupForm,
        final BindingResult errors,
        @AuthenticationPrincipal User user
    ) {
        if(errors.hasErrors()){
            throw new RuntimeException();
        }

        chatGroupService.addGroup(
            chatGroupForm.getName(),
            chatGroupForm.getCareerId(),
            chatGroupForm.getLink(),
            user.getId(),
            chatGroupForm.getCreationDate(),
            chatGroupForm.getPlatform()
        );

        return new ModelAndView("redirect:/chats");
    }

}