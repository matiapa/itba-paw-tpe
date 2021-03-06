package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.Permission;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;
import ar.edu.itba.paw.models.Poll.PollState;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.PollService;
import ar.edu.itba.paw.webapp.controller.common.CommonFilters;
import ar.edu.itba.paw.webapp.controller.common.Pager;
import ar.edu.itba.paw.webapp.exceptions.BadRequestException;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
import ar.edu.itba.paw.webapp.form.PollForm;


@Controller
public class PollController {

    @Autowired private PollService pollService;
    @Autowired private CourseService courseService;
    @Autowired private CareerService careerService;

    @Autowired private CommonFilters commonFilters;


    private static final Logger LOGGER= LoggerFactory.getLogger(PollController.class);



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
        @ModelAttribute("user") User loggedUser
    ) {
        final ModelAndView mav = new ModelAndView("polls/poll_list");

        // Add filters options

        mav.addObject("filterBy", filterBy);

        Career career = commonFilters.addCareers(mav, careerCode);
        Course course = commonFilters.addCourses(mav, courseId);

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
                    pager = new Pager(pollService.getCount(filterBy, course, selectedType, selectedState), page);
                    pollList = pollService.findByCourse(course, selectedType, selectedState,
                            pager.getCurrPage(), pager.getLimit());
                }
                break;
            case career:
                if (careerCode != null){
                    pager = new Pager(pollService.getCount(filterBy, career, selectedType, selectedState), page);
                    pollList = pollService.findByCareer(career, selectedType, selectedState,
                            pager.getCurrPage(), pager.getLimit());
                }
                break;
            case general:
            default:
                pager = new Pager(pollService.getCount(filterBy, selectedType, selectedState), page);
                pollList = pollService.findGeneral(selectedType, selectedState, pager.getCurrPage(), pager.getLimit());
                break;
        }

        mav.addObject("pager", pager);
        mav.addObject("polls", pollList);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);

        mav.addObject("canDeletePoll", loggedUser.can(Permission.Action.delete, Entity.poll));

        return mav;
    }


    @RequestMapping(value = "/polls", method = POST)
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final PollForm pollForm,final BindingResult errors,
        @ModelAttribute("user") User loggedUser
    ) {


        if (errors.hasErrors()) {
            LOGGER.debug("user {} tried to create a poll but form {} had problems errors:{}",loggedUser,pollForm,errors);
            return list(EntityTarget.general, pollForm.getCareerCode(), pollForm.getCourseId(),
                null, null,0, true, pollForm, loggedUser);
        }

        if(pollForm.getCareerCode() != null && pollForm.getCourseId() != null)
            throw new BadRequestException();

        Career career = pollForm.getCareerCode() != null ? careerService.findByCode(pollForm.getCareerCode()).orElseThrow(BadRequestException::new) : null;
        Course course = pollForm.getCourseId() != null ? courseService.findById(pollForm.getCourseId()).orElseThrow(BadRequestException::new) : null;

        LOGGER.debug("user {} is tryng to create a poll with  form {} ",loggedUser,pollForm);
        if(career != null)
            pollService.addPoll(
                pollForm.getTitle(),
                pollForm.getDescription(),
                PollFormat.multiple_choice,
                career,
                pollForm.getExpiryDate(),
                loggedUser,
                pollForm.getOptions()
            );
        else if(course != null)
            pollService.addPoll(
                pollForm.getTitle(),
                pollForm.getDescription(),
                PollFormat.multiple_choice,
                course,
                pollForm.getExpiryDate(),
                loggedUser,
                pollForm.getOptions()
            );
        else
            pollService.addPoll(
                pollForm.getTitle(),
                pollForm.getDescription(),
                PollFormat.multiple_choice,
                pollForm.getExpiryDate(),
                loggedUser,
                pollForm.getOptions()
            );

        LOGGER.debug("user {} created a poll with  form {} ",loggedUser,pollForm);

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

        if (!selectedPoll.isPresent()){
            LOGGER.debug("user {} tried to find a poll with id  {} that wasnt found",loggedUser,pollId);
            throw new ResourceNotFoundException();
        }


        mav.addObject("poll",selectedPoll.get());

        Map<PollOption,Integer> votes = pollService.getVotes(selectedPoll.get());
        mav.addObject("votes",votes);
        Map<String,Integer> votesMap = new HashMap<>();
        for (PollOption p: votes.keySet()
             ) {
            votesMap.put("'"+p.getValue()+"'",votes.get(p));
        }
        mav.addObject("votesMap",votesMap);

        mav.addObject("hasVoted", pollService.hasVoted(selectedPoll.get(), loggedUser));

        Locale loc = new Locale("es", "AR");
        DateFormat expiryFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, loc);
        DateFormat creationFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, loc);

        mav.addObject("expiryFormat", expiryFormat);
        mav.addObject("creationFormat", creationFormat);

        return mav;
    }


    @Secured("ROLE_POLL.DELETE")
    @RequestMapping(value = "/polls/{id}", method = DELETE)
    public String delete(
        @PathVariable(value="id") int id, HttpServletRequest request,
        @ModelAttribute("user") User loggedUser
    ) {
        Poll poll = pollService.findById(id).orElseThrow(ResourceNotFoundException::new);

        LOGGER.debug("user {} is attempting to delete in poll with id {}",loggedUser,id);
        pollService.delete(poll);
        LOGGER.debug("user {} deleted poll with id {}",loggedUser,id);

        return "redirect:"+ request.getHeader("Referer");
    }

    @Secured("ROLE_POLL.DELETE")
    @RequestMapping(value = "/polls/{id}/delete", method = POST)
    public String deleteWithPost(
        @PathVariable(value="id") int id, HttpServletRequest request,
        @ModelAttribute("user") User loggedUser
    ) {
        return delete(id, request, loggedUser);
    }


    @RequestMapping(value = "/polls/{id}/vote", method = POST)
    public String votePoll(
            @PathVariable(value="id") int id,
            @RequestParam int option,
            @ModelAttribute("user") User loggedUser
    ) {
        Poll poll = pollService.findById(id).orElseThrow(ResourceNotFoundException::new);

        PollOption pollOption = poll.getOptions()
            .stream()
            .filter(o -> o.getId() == option)
            .findAny()
            .orElseThrow(BadRequestException::new);

        LOGGER.debug("user {} is attempting to vote in poll with id {} with option {}",loggedUser,id,option);
        pollService.voteChoicePoll(poll, pollOption, loggedUser);

        LOGGER.debug("user {} voted in poll with id {} with option {}",loggedUser,id,option);
        return "redirect:/polls/"+id;
    }

}