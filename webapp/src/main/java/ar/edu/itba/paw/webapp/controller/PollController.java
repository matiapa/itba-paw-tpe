package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;


import java.text.DateFormat;
import java.util.*;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.PollService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.mav.BaseMav;

@Controller
public class PollController {

    @Autowired private PollService pollService;

    @Autowired private CareerService careerService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;

    @RequestMapping("polls/byId")
    public ModelAndView getPollDetails(@RequestParam int id)
    {
        Optional<Poll> optionalPoll = pollService.findById(id);
        if(!optionalPoll.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Encuesta no encontrada");
        Poll poll = optionalPoll.get();
        
        ModelAndView mav = new BaseMav(
            poll.getName(),
            "poll/poll_detail.jsp", 
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem(poll.getName(), "polls/byId?id=" + id)
            )
        );

        Locale loc = new Locale("es", "AR");
        DateFormat expiryFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, loc);
        DateFormat creationFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, loc);

        mav.addObject("expiryFormat", expiryFormat);
        mav.addObject("creationFormat", creationFormat);
        mav.addObject("poll", poll);

        return mav;
    }

    @RequestMapping("polls/byCareer")
    public ModelAndView getPollsbyCareer(@RequestParam int careerId)
    {
        Optional<Career> careerOpt = careerService.findById(careerId);
        if(!careerOpt.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrera no encontrada");
        }
        Career career = careerOpt.get();

        ModelAndView mav = new BaseMav(
            String.format("Encuestas de %s", career.getName()),
            "poll/poll_full_list.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem(career.getName(), "/careers/byId?id="+career.getId()),
                new NavigationItem("Encuestas", "/polls/byCareer?careerId="+career.getId())
            )
        );

        mav.addObject("polls", pollService.findByCareer(careerId));
        return mav;
    }

    @RequestMapping("polls/byCourse")
    public ModelAndView getPollsbyCourse(@RequestParam String courseId)
    {
        Optional<Course> courseOpt = courseService.findById(courseId);
        if(!courseOpt.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado");
        }
        Course course = courseOpt.get();

        ModelAndView mav = new BaseMav(
            String.format("Encuestas de %s", course.getName()),
            "poll/poll_full_list.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem(course.getName(), "/courses/byId?id="+course.getId()),
                new NavigationItem("Encuestas", "/polls/byCourse?careerId="+course.getId())
            )
        );

        mav.addObject("polls", pollService.findByCourse(courseId));
        return mav;
    }

    @RequestMapping("polls/general")
    public ModelAndView getGeneralPolls()
    {
        ModelAndView mav = new BaseMav(
            "Encuestas generales",
            "poll/poll_full_list.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem("Encuestas", "/polls/general")
            )
        );

        mav.addObject("polls", pollService.findGeneral());
        return mav;
    }

    @RequestMapping("polls/detail")
    public ModelAndView getPoll(
            @RequestParam(name="id") int pollId
    ){
        final ModelAndView mav = new ModelAndView("polls/poll_detail");


        Optional<Poll>  selectedPoll= pollService.findById(pollId);


        if (!selectedPoll.isPresent())
            throw new RuntimeException();
        mav.addObject("poll",selectedPoll.get());


        mav.addObject("user", userService.getUser());

        return mav;
    }
}
