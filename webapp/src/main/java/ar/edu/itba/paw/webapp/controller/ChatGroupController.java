package ar.edu.itba.paw.webapp.controller;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.webapp.controller.common.Pager;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.ChatGroupService;
import ar.edu.itba.paw.webapp.controller.common.CommonFilters;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
import ar.edu.itba.paw.webapp.form.ChatGroupForm;

@Controller
public class ChatGroupController {

    @Autowired private ChatGroupService chatGroupService;

    @Autowired private CareerService careerService;

    @Autowired private CommonFilters commonFilters;


    private static final Logger LOGGER= LoggerFactory.getLogger(ChatGroupController.class);


    @RequestMapping(value = "/chats", method = GET)
    public ModelAndView list(
        @RequestParam(name = "careerCode", required = false) String careerCode,
        @RequestParam(name = "platform", required = false) String platform,
        @RequestParam(name = "year", required = false) Integer year,
        @RequestParam(name = "quarter", required = false) Integer quarter,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final ChatGroupForm chatGroupForm,
        @ModelAttribute("user") User loggedUser
    ){
        final ModelAndView mav = new ModelAndView("chats/chats_list");

        // Add filters options
        Career selectedCareer = commonFilters.addCareers(mav, careerCode);

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
            pager = new Pager(chatGroupService.getSize(selectedCareer, selectedPlatform, selectedYear, selectedQuarter), page);
            chatGroupList = chatGroupService.findByCareer(selectedCareer, selectedPlatform, selectedYear, selectedQuarter, pager.getCurrPage(), pager.getLimit());
        }

        mav.addObject("chatgroups", chatGroupList);
        mav.addObject("pager", pager);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);

        mav.addObject("canDeleteChat", loggedUser.can(Permission.Action.delete, Entity.chat_group));

        return mav;
    }


    @RequestMapping(value = "/chats", method = POST)
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final ChatGroupForm chatGroupForm,final BindingResult errors,
        @ModelAttribute("user") User loggedUser

    ) {
        if(errors.hasErrors()){
            LOGGER.debug("User {} tried and failed to create a chatgroup with form {} and error {}",loggedUser,chatGroupForm,errors);
            return list(null, null, null, null, 0, true,
                    chatGroupForm, loggedUser);
        }

        ChatGroup chatGroup=chatGroupService.addGroup(
            chatGroupForm.getName(),
            careerService.findByCode(chatGroupForm.getCareerCode()).orElseThrow(ResourceNotFoundException::new),
            chatGroupForm.getLink(),
            loggedUser,
            chatGroupForm.getCreationDate(),
            chatGroupForm.getPlatform()
        );
        LOGGER.debug("user {} created chatgroup {} with form {}",loggedUser,chatGroup,chatGroupForm);

        return list(chatGroupForm.getCareerCode(), null, null, null, 0, false,
                chatGroupForm, loggedUser);
    }


    @Secured("ROLE_CHAT_GROUP.DELETE")
    @RequestMapping(value = "/chats/{id}", method = DELETE)
    public String delete(
        @PathVariable(value="id") Integer id, HttpServletRequest request,
        @ModelAttribute("user") User loggedUser
    ) {

        chatGroupService.delete(chatGroupService.findById(id).orElseThrow(ResourceNotFoundException::new));

        LOGGER.debug("user {} deleted chatgroup with id {}",loggedUser,id);

        return "redirect:"+request.getHeader("Referer");
    }


    @Secured("ROLE_CHAT_GROUP.DELETE")
    @RequestMapping(value = "/chats/{id}/delete", method = POST)
    public String deleteWithPost(
            @PathVariable(value="id") Integer id, HttpServletRequest request,
            @ModelAttribute("user") User loggedUser
    ) {
        return delete(id, request, loggedUser);
    }

}