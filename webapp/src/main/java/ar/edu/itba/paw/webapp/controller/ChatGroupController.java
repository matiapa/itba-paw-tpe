package ar.edu.itba.paw.webapp.controller;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ar.edu.itba.paw.models.ui.Pager;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.Permission;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.common.FiltersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.services.ChatGroupService;
import ar.edu.itba.paw.webapp.form.ChatGroupForm;

@Controller
public class ChatGroupController {

    private static final int LIMIT = 5;
    @Autowired private ChatGroupService chatGroupService;

    @Autowired private UserService userService;

    @Autowired private FiltersController commonFilters;


    @RequestMapping(value = "/chats", method = GET)
    public ModelAndView get(
        @RequestParam(name = "careerCode", required = false) String careerCode,
        @RequestParam(name = "platform", required = false) String platform,
        @RequestParam(name = "year", required = false) Integer year,
        @RequestParam(name = "quarter", required = false) Integer quarter,
        @RequestParam(name = "showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @ModelAttribute("createForm") final ChatGroupForm chatGroupForm
    ){
        final ModelAndView mav = new ModelAndView("chats/chats_list");

        mav.addObject("user", userService.getLoggedUser());

        // Add filters options

        commonFilters.addCareers(mav, careerCode);

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
        if (page == null) page = 0;
        Pager pager = new Pager(chatGroupService.getSize("",selectedPlatform,selectedYear,selectedQuarter), page);

        if(careerCode != null){
            mav.addObject("careerCode", careerCode);
            pager = new Pager(chatGroupService.getSize(careerCode,selectedPlatform,selectedYear,selectedQuarter), page);
            chatGroupList = chatGroupService.findByCareer(careerCode, pager.getOffset(), pager.getLimit());
            //chatGroupList = chatGroupService.findByCareer(careerCode, selectedPlatform, selectedYear, selectedQuarter, pager.getOffset(), pager.getLimit());
        }

        mav.addObject("chatgroups", chatGroupList);

        // Add other parameters

        mav.addObject("pager", pager);
        mav.addObject("showCreateForm", showCreateForm);


        User loggedUser = userService.getLoggedUser();
        mav.addObject("user", loggedUser);
        mav.addObject("canDelete", loggedUser.getPermissions().contains(
                new Permission(Permission.Action.DELETE, Entity.CHAT_GROUP)
        ));

        return mav;
    }


    @RequestMapping(value = "/chats/create", method = POST)
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final ChatGroupForm chatGroupForm,
        final BindingResult errors
    ) {
        if(errors.hasErrors()){
            return get(null, null, null, null, true, 0, chatGroupForm);
        }

        chatGroupService.addGroup(
            chatGroupForm.getName(),
            chatGroupForm.getCareerCode(),
            chatGroupForm.getLink(),
            userService.getLoggedUser().getId(),
            chatGroupForm.getCreationDate(),
            chatGroupForm.getPlatform()
        );

        return get(chatGroupForm.getCareerCode(), null, null, null, false, 0, chatGroupForm);
    }

    @RequestMapping(value = "/chats/{id}", method = DELETE)
    public String delete(
            @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        chatGroupService.delete(id);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping(value = "/chats/{id}/delete", method = POST)
    public String deleteWithPost(
            @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        return delete(id, request);
    }

}