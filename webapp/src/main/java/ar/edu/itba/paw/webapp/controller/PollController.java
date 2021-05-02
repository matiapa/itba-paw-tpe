package ar.edu.itba.paw.webapp.controller;

import java.text.DateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ar.edu.itba.paw.models.ui.Pager;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.controller.common.CommonFilters;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;

import ar.edu.itba.paw.services.PollService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.form.PollForm;
import ar.edu.itba.paw.models.Poll.PollState;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@Controller
public class PollController {

    @Autowired private UserService userService;

    @Autowired private PollService pollService;

    @Autowired private CommonFilters commonFilters;


    @RequestMapping(value = "/polls", method = GET)
    public ModelAndView list(
        @RequestParam(name = "filterBy", required = false, defaultValue = "general") EntityTarget filterBy,
        @RequestParam(name = "careerCode", required = false) String careerCode,
        @RequestParam(name = "courseId", required = false) String courseId,
        @RequestParam(name = "type", required = false) String type,
        @RequestParam(name = "state", required = false) String state,
        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
        @RequestParam(name = "showCreateForm", required = false, defaultValue="false") boolean showCreateForm,
        @ModelAttribute("createForm") final PollForm pollForm,
        @ModelAttribute("user") final User loggedUser
    ) {
        final ModelAndView mav = new ModelAndView("polls/poll_list");

        // Add filters options

        mav.addObject("filterBy", filterBy);

        commonFilters.addCareers(mav, careerCode);
        commonFilters.addCourses(mav, courseId);

        // -- By Type

        mav.addObject("types", PollFormat.values());

        PollFormat selectedType = type != null ? PollFormat.valueOf(type) : null;
        mav.addObject("selectedType", selectedType);

        // -- By State

        mav.addObject("states", PollState.values());

        PollState selectedState = state != null ? PollState.valueOf(state) : null;
        mav.addObject("selectedState", selectedState);


        // Add filtered polls

        List<Poll> pollList = new ArrayList<>();
        Pager pager = null;

        switch (filterBy) {
            case course:
                if (courseId != null){
                    pager = new Pager(pollService.getSize(filterBy, courseId, selectedType, selectedState), page);
                    pollList = pollService.findByCourse(courseId, selectedType, selectedState,
                            pager.getOffset(), pager.getLimit());
                }
                break;
            case career:
                if (careerCode != null){
                    pager = new Pager(pollService.getSize(filterBy, careerCode, selectedType, selectedState), page);
                    pollList = pollService.findByCareer(careerCode, selectedType, selectedState,
                            pager.getOffset(), pager.getLimit());
                }
                break;
            case general:
            default:
                pager = new Pager(pollService.getSize(filterBy, null, selectedType, selectedState), page);
                pollList = pollService.findGeneral(selectedType, selectedState, pager.getOffset(), pager.getLimit());
                break;
        }

        mav.addObject("pager", pager);
        mav.addObject("polls", pollList);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("canDelete", loggedUser.getPermissions().contains(
                new Permission(Permission.Action.DELETE, Entity.POLL)
        ));
        
        return mav;
    }


    @RequestMapping(value = "/polls/create", method = POST)
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final PollForm pollForm,
        @ModelAttribute("user") final User loggedUser,
        final BindingResult errors
    ) {
        if (errors.hasErrors()) {
            return list(EntityTarget.general, pollForm.getCareerCode(), pollForm.getCourseId(),
                null, null,0, true, pollForm, loggedUser);
        }

        pollService.addPoll(
            pollForm.getTitle(),
            pollForm.getDescription(),
            PollFormat.text,
            pollForm.getCareerCode(),
            pollForm.getCourseId(),
            pollForm.getExpiryDate(),
            loggedUser,
            pollForm.getOptions()
        );

        EntityTarget filterBy = commonFilters.getTarget(pollForm.getCareerCode(), pollForm.getCourseId());

        return list(filterBy, pollForm.getCareerCode(), pollForm.getCourseId(), null, null,
            0, false, pollForm, loggedUser);
    }


    @RequestMapping(value = "/polls/{id}", method = GET)
    public ModelAndView get(
        @PathVariable(value="id") int pollId,
        @ModelAttribute("user") User loggedUser
    ){
        final ModelAndView mav = new ModelAndView("polls/poll_detail");

        Optional<Poll> selectedPoll= pollService.findById(pollId);

        if (!selectedPoll.isPresent())
            throw new ResourceNotFoundException();

        mav.addObject("poll",selectedPoll.get());

        Map<PollOption,Integer> votes = pollService.getVotes(pollId);
        mav.addObject("votes",votes);
        mav.addObject("hasVoted", pollService.hasVoted(pollId, loggedUser));

        Locale loc = new Locale("es", "AR");
        DateFormat expiryFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, loc);
        DateFormat creationFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, loc);

        mav.addObject("expiryFormat", expiryFormat);
        mav.addObject("creationFormat", creationFormat);

        return mav;
    }


    @RequestMapping(value = "/polls/{id}", method = DELETE)
    public String delete(
        @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        pollService.delete(id);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping(value = "/polls/{id}/delete", method = POST)
    public String deleteWithPost(
        @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        return delete(id, request);
    }


    @RequestMapping(value = "/polls/{id}/vote", method = POST)
    public String votePoll(
            @PathVariable(value="id") int id,
            @RequestParam int option,
            @ModelAttribute("user") User loggedUser
    ) {
        pollService.voteChoicePoll(id, option, loggedUser);
        return "redirect:/polls/"+id;
    }

}