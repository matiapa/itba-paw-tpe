package ar.edu.itba.paw.webapp.controller;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ar.edu.itba.paw.models.ui.Pager;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.Permission;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.common.CommonFilters;
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

    @Autowired private ChatGroupService chatGroupService;

    @Autowired private CommonFilters commonFilters;


    @RequestMapping(value = "/chats", method = GET)
    public ModelAndView list(
        @RequestParam(name = "careerCode", required = false) String careerCode,
        @RequestParam(name = "platform", required = false) String platform,
        @RequestParam(name = "year", required = false) Integer year,
        @RequestParam(name = "quarter", required = false) Integer quarter,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final ChatGroupForm chatGroupForm,
        @ModelAttribute("user") final User loggedUser
    ){
        final ModelAndView mav = new ModelAndView("chats/chats_list");

        // Add filters options

        commonFilters.addCareers(mav, careerCode);

        // -- By platform

        mav.addObject("platforms", ChatGroup.ChatPlatform.values());

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

        Integer selectedQuarter = quarter != null && quarter > 0 ? quarter : null;
        mav.addObject("selectedQuarter", selectedQuarter);

        // Add filtered chats

        List<ChatGroup> chatGroupList = null;
        Pager pager = null;

        if(careerCode != null){
            mav.addObject("careerCode", careerCode);
            pager = new Pager(chatGroupService.getSize(careerCode, selectedPlatform, selectedYear, selectedQuarter), page);
            chatGroupList = chatGroupService.findByCareer(careerCode, selectedPlatform, selectedYear, selectedQuarter, pager.getOffset(), pager.getLimit());
        }

        mav.addObject("chatgroups", chatGroupList);
        mav.addObject("pager", pager);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("canDelete", loggedUser.getPermissions().contains(
            new Permission(Permission.Action.DELETE, Entity.CHAT_GROUP)
        ));

        return mav;
    }


    @RequestMapping(value = "/chats/create", method = POST)
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final ChatGroupForm chatGroupForm,
        @ModelAttribute("user") final User loggedUser,
        final BindingResult errors
    ) {
        if(errors.hasErrors()){
            return list(null, null, null, null, 0, true,
                chatGroupForm, loggedUser);
        }

        chatGroupService.addGroup(
            chatGroupForm.getName(),
            chatGroupForm.getCareerCode(),
            chatGroupForm.getLink(),
            loggedUser.getId(),
            chatGroupForm.getCreationDate(),
            chatGroupForm.getPlatform()
        );

        return list(chatGroupForm.getCareerCode(), null, null, null, 0, false,
            chatGroupForm, loggedUser);
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