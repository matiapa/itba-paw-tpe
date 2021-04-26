package ar.edu.itba.paw.webapp.controller;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.validation.Valid;

import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.common.FiltersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.services.ChatGroupService;
import ar.edu.itba.paw.webapp.form.ChatGroupForm;

@Controller
public class ChatGroupController {

    @Autowired private ChatGroupService chatGroupService;

    @Autowired private UserService userService;

    @Autowired private FiltersController commonFilters;


    @RequestMapping(value = "/chats", method = GET)
    public ModelAndView get(
        @RequestParam(name = "careerId", required = false) Integer careerId,
        @RequestParam(name = "platform", required = false) String platform,
        @RequestParam(name = "year", required = false) Integer year,
        @RequestParam(name = "quarter", required = false) Integer quarter,
        @RequestParam(name = "showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final ChatGroupForm chatGroupForm
    ){
        final ModelAndView mav = new ModelAndView("chats/chats_list");

        mav.addObject("user", userService.getLoggedUser());

        // Add filters options

        commonFilters.addCareers(mav, careerId);

        // -- By platform

        mav.addObject("platforms", ChatGroup.ChatPlatform.values());
        mav.addObject("platformsTranslate", new HashMap<ChatGroup.ChatPlatform, String>()
        {{
            put(ChatGroup.ChatPlatform.discord, "Discord");
            put(ChatGroup.ChatPlatform.whatsapp, "WhatsApp");
        }});

        ChatGroup.ChatPlatform selectedPlatform = platform != null && !platform.equals("")
                ? ChatGroup.ChatPlatform.valueOf(platform) : null;
        mav.addObject("selectedPlatform", selectedPlatform);

        // -- By year

        mav.addObject("years", IntStream.range(1960, 2022).boxed()
                .sorted(Comparator.reverseOrder()).collect(toList()));

        Integer selectedYear = year != null && year > 0 ? year : null;
        mav.addObject("selectedYear", selectedYear);

        // -- By quarter

        mav.addObject("quarters", new Integer[]{1, 2});
        mav.addObject("quartersTranslate", new HashMap<Integer, String>()
        {{
            put(1, "Primero");
            put(2, "Segundo");
        }});

        Integer selectedQuarter = quarter != null && quarter > 0 ? quarter : null;
        mav.addObject("selectedQuarter", selectedQuarter);

        // Add filtered chats

        List<ChatGroup> chatGroupList = new ArrayList<>();
        if(careerId != null){
            chatGroupList = chatGroupService.findByCareer(careerId, selectedPlatform, selectedYear, selectedQuarter);
        }

        mav.addObject("chatgroups", chatGroupList);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("user", userService.getLoggedUser());

        return mav;
    }


    @RequestMapping(value = "/chats/create", method = POST)
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final ChatGroupForm chatGroupForm,
        final BindingResult errors
    ) {
        if(errors.hasErrors()){
            return get(null, null, null, null, true, chatGroupForm);
        }

        chatGroupService.addGroup(
            chatGroupForm.getName(),
            chatGroupForm.getCareerId(),
            chatGroupForm.getLink(),
            userService.getLoggedUser().getId(),
            chatGroupForm.getCreationDate(),
            chatGroupForm.getPlatform()
        );

        return get(chatGroupForm.getCareerId(), null, null, null, false, chatGroupForm);
    }

}