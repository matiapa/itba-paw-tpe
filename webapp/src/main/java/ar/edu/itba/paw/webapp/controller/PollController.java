package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;

import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.services.PollService;
import ar.edu.itba.paw.webapp.mav.BaseMav;

@Controller
public class PollController {
    @Autowired
    PollService pollService;

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
        mav.addObject("poll", poll);

        return mav;
    }
}
