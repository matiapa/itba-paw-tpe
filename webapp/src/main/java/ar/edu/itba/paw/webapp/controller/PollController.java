package ar.edu.itba.paw.webapp.controller;

import java.text.DateFormat;
import java.util.*;

import javax.validation.Valid;

import ar.edu.itba.paw.models.ui.Pager;
import ar.edu.itba.paw.webapp.controller.common.FiltersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;

import ar.edu.itba.paw.services.PollService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.form.PollForm;
import ar.edu.itba.paw.models.Poll.PollState;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


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
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
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

        if (page == null) page = 0;
        Pager pager = new Pager(pollService.getSize(filterBy, ""), page);
        // Add filtered polls
        switch (filterBy) {
            case course:
                if (courseId != null){
                    pager = new Pager(pollService.getSize(filterBy, courseId), page);
                    pollList = pollService.findByCourse(courseId, selectedType, selectedState,
                            pager.getOffset(), pager.getLimit());
                }
                break;
            case career:
                if (careerCode != null){
                    pager = new Pager(pollService.getSize(filterBy, careerCode), page);
                    pollList = pollService.findByCareer(careerCode, selectedType, selectedState,
                            pager.getOffset(), pager.getLimit());
                }
                break;
            case general:
            default:
                pollList = pollService.findGeneral(pager.getOffset(), pager.getLimit());
                //pollList = pollService.findGeneral(selectedType, selectedState);
                break;
        }

        mav.addObject("pager", pager);
        mav.addObject("polls", pollList);

        // Add other parameters
        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("user", userService.getLoggedUser());
        
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
                true, 0, pollForm
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

        return getPolls(filterBy, pollForm.getCareerCode(), pollForm.getCourseId(), null, null, false, 0, pollForm);
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

}