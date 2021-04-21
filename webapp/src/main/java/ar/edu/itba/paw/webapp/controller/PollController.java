package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.*;

import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.PollService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.form.PollForm;
import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollState;

import javax.validation.Valid;

@Controller
public class PollController {

    @Autowired
    private UserService userService;

    @Autowired
    private PollService pollService;

    @Autowired
    private CareerService careerService;

    @Autowired
    private CourseService courseService;

    @RequestMapping("/polls")
    public ModelAndView getPolls(
            @RequestParam(name = "filterBy", required = false, defaultValue = "general") HolderEntity filterBy,
            @RequestParam(name = "careerId", required = false) Integer careerId,
            @RequestParam(name = "courseId", required = false) String courseId,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "state", required = false) String state,
            @ModelAttribute("pollForm") final PollForm pollForm
    ) {
        final ModelAndView modelAndView = new ModelAndView("polls/polls_list");
        modelAndView.addObject("filterBy", filterBy);

        // Filters

        List<Poll> pollList = new ArrayList<>();

            // -- Career

        List<Career> careers = careerService.findAll();
        modelAndView.addObject("careers", careers);

        Career selectedCareer = careerId != null ? careers.stream().filter(c -> c.getId() == careerId).findFirst()
                .orElseThrow(RuntimeException::new) : null;
        modelAndView.addObject("selectedCareer", selectedCareer);

            // -- Course

        List<Course> courses = courseService.findAll();
        modelAndView.addObject("courses", courses);

        Course selectedCourse = courseId != null ? courses.stream().filter(c -> c.getId().equals(courseId)).findFirst()
                .orElseThrow(RuntimeException::new) : null;
        modelAndView.addObject("selectedCourse", selectedCourse);

            // -- Type

        modelAndView.addObject("types", PollFormat.values());
        modelAndView.addObject("typeTranslate", new HashMap<PollFormat, String>() {{
            put(PollFormat.text, "Texto libre");
            put(PollFormat.multiple_choice, "Opción múltiple");
        }});

        PollFormat selectedType = type != null ? PollFormat.valueOf(type) : null;
        modelAndView.addObject("selectedType", selectedType);

            // -- State

        modelAndView.addObject("states", PollState.values());
        modelAndView.addObject("stateTranslate", new HashMap<PollState, String>() {{
            put(PollState.open, "Abiertas");
            put(PollState.closed, "Cerradas");
        }});

        PollState selectedState = state != null ? PollState.valueOf(state) : null;
        modelAndView.addObject("selectedState", selectedState);

        // Polls

        switch (filterBy) {
            case course:
                if (courseId != null)
                    pollList = pollService.findByCourse(courseId, selectedType, selectedState);
                break;
            case career:
                if (careerId != null)
                    pollList = pollService.findByCareer(careerId, selectedType, selectedState);
                break;
            case general:
            default:
                pollList = pollService.findGeneral(selectedType, selectedState);
                break;
        }

        modelAndView.addObject("polls", pollList);

        modelAndView.addObject("user", userService.getUser());

        return modelAndView;
    }


    @RequestMapping(value = "/polls", method = {RequestMethod.POST})
    public ModelAndView addPoll(
            @Valid @ModelAttribute("pollForm") final PollForm pollForm,
            final BindingResult errors
    ) throws ParseException {
        if (errors.hasErrors()) {
            throw new RuntimeException("Error al agregar encuesta: " + errors);
        }
/*
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
*/
        System.out.println(pollForm.getOptions().get(0));
        pollService.addPoll(pollForm.getName(),
                pollForm.getDescription(),
                PollFormat.text,
                pollForm.getCareerId(),
                pollForm.getCourseId(),
                null,
                //formatter.parse(formatter.format(date)),
                pollForm.getExpiryDate(),
                userService.getUser().getId(),
                pollForm.getOptions());

        return getPolls(HolderEntity.general, null, null, null, null, pollForm);
    }

}