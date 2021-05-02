package ar.edu.itba.paw.webapp.controller;

import java.text.DateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.controller.common.FiltersController;
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

    @Autowired private FiltersController commonFilters;


    @RequestMapping(value = "/polls", method = GET)
    public ModelAndView getPolls(
        @RequestParam(name = "filterBy", required = false, defaultValue = "general") HolderEntity filterBy,
        @RequestParam(name = "careerCode", required = false) String careerCode,
        @RequestParam(name = "courseId", required = false) String courseId,
        @RequestParam(name = "type", required = false) String type,
        @RequestParam(name = "state", required = false) String state,
        @RequestParam(name = "showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final PollForm pollForm
    ) {
        final ModelAndView mav = new ModelAndView("polls/poll_list");
        mav.addObject("filterBy", filterBy);

        // Add filters options

        List<Poll> pollList = new ArrayList<>();

        commonFilters.addCareers(mav, careerCode);

        commonFilters.addCourses(mav, courseId);

        // -- Type

        mav.addObject("types", PollFormat.values());
        mav.addObject("typeTranslate", new HashMap<PollFormat, String>() {{
            put(PollFormat.text, "Texto libre");
            put(PollFormat.multiple_choice, "Opción múltiple");
        }});

        PollFormat selectedType = type != null ? PollFormat.valueOf(type) : null;
        mav.addObject("selectedType", selectedType);

        // -- State

        mav.addObject("states", PollState.values());
        mav.addObject("stateTranslate", new HashMap<PollState, String>() {{
            put(PollState.open, "Abiertas");
            put(PollState.closed, "Cerradas");
        }});

        PollState selectedState = state != null ? PollState.valueOf(state) : null;
        mav.addObject("selectedState", selectedState);

        // Add filtered polls

        switch (filterBy) {
            case course:
                if (courseId != null)
                    pollList = pollService.findByCourse(courseId, selectedType, selectedState);
                break;
            case career:
                if (careerCode != null)
                    pollList = pollService.findByCareer(careerCode, selectedType, selectedState);
                break;
            case general:
            default:
                pollList = pollService.findGeneral(selectedType, selectedState);
                break;
        }

        mav.addObject("polls", pollList);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);

        User loggedUser=userService.getLoggedUser();
        mav.addObject("user", loggedUser);
        mav.addObject("canDelete", loggedUser.getPermissions().contains(
                new Permission(Permission.Action.DELETE, Entity.POLL)
        ));
        
        return mav;
    }


    @RequestMapping(value = "/polls/create", method = POST)
    public ModelAndView addPoll(
        @Valid @ModelAttribute("createForm") final PollForm pollForm,
        final BindingResult errors
    ) {
        if (errors.hasErrors()) {
            return getPolls(
                HolderEntity.general, pollForm.getCareerCode(), pollForm.getCourseId(), null, null,
                true, pollForm
            );
        }

        pollService.addPoll(
            pollForm.getTitle(),
            pollForm.getDescription(),
            PollFormat.text,
            pollForm.getCareerCode(),
            pollForm.getCourseId(),
            pollForm.getExpiryDate(),
            userService.getLoggedUser(),
            pollForm.getOptions()
        );

        HolderEntity filterBy;
        if(pollForm.getCareerCode() == null && pollForm.getCourseId() == null)
            filterBy = HolderEntity.general;
        else if(pollForm.getCareerCode() == null && pollForm.getCourseId() != null)
            filterBy = HolderEntity.course;
        else if(pollForm.getCareerCode() != null && pollForm.getCourseId() == null)
            filterBy = HolderEntity.career;
        else
            filterBy = HolderEntity.general;

        return getPolls(filterBy, pollForm.getCareerCode(), pollForm.getCourseId(), null, null, false, pollForm);
    }


    @RequestMapping(value = "/polls/detail", method = GET)
    public ModelAndView getPoll(
        @RequestParam(name="id") int pollId
    ){
        final ModelAndView mav = new ModelAndView("polls/poll_detail");

        Optional<Poll> selectedPoll= pollService.findById(pollId);

        if (!selectedPoll.isPresent())
            // TODO: Replace this exception
            throw new RuntimeException();
        mav.addObject("poll",selectedPoll.get());

        Map<PollOption,Integer> votes = pollService.getVotes(pollId);
        mav.addObject("votes",votes);
        mav.addObject("user", userService.getLoggedUser());
        mav.addObject("hasVoted", pollService.hasVoted(pollId, userService.getLoggedUser()));

        Locale loc = new Locale("es", "AR");
        DateFormat expiryFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, loc);
        DateFormat creationFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, loc);

        mav.addObject("expiryFormat", expiryFormat);
        mav.addObject("creationFormat", creationFormat);

        return mav;
    }


    @RequestMapping(value = "/polls/vote", method = POST)
    public String votePoll(
        @RequestParam int id,
        @RequestParam int option
    ) {
        pollService.voteChoicePoll(id, option, userService.getLoggedUser());
        return "redirect:/polls/detail?id="+id;
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

}