package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.StatisticsService;
import ar.edu.itba.paw.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class AdminController {

    @Autowired UserService userService;

    @Autowired StatisticsService statisticsService;

    private static final Logger LOGGER= LoggerFactory.getLogger(AdminController.class);


    @RequestMapping(value = "/admin/statistics", method = GET)
    public ModelAndView statistics(
        @ModelAttribute("user") User loggedUser
    ){
        LOGGER.debug("user {}  accessed admin controller",loggedUser);

        ModelAndView mav = new ModelAndView("statistics/statistics");

        mav.addObject("newContributions", statisticsService.newContributions(loggedUser));

        Map<String, Integer> map = statisticsService.contributionsByCareer().entrySet().stream()
            .collect(Collectors.toMap(e -> "'"+e.getKey().getName()+"'", Map.Entry::getValue));

        mav.addObject("contributionsByCareer", map);

        SimpleDateFormat df = new SimpleDateFormat("MM-dd");

        List<Map.Entry<Date, Integer>> contributionsByDates = statisticsService.contributionsByDate().entrySet().stream()
            .sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());;

        mav.addObject("contributionsByDateDates", contributionsByDates.stream().map(Map.Entry::getKey)
                .map(df::format).map(s -> String.format("'%s'", s)).collect(Collectors.toList()));
        mav.addObject("contributionsByDateContribs", contributionsByDates.stream().map(Map.Entry::getValue)
                .collect(Collectors.toList()));

        System.out.println(contributionsByDates.stream().map(Map.Entry::getKey)
                .map(df::format).map(s -> String.format("'%s'", s)).collect(Collectors.toList()));

        List<Map.Entry<User, Integer>> topUsersList = statisticsService.topUsersByContributions().entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList());

        mav.addObject("topUsersByContributions", topUsersList);

        List<Map.Entry<Course, Integer>> topCoursesList = statisticsService.topCoursesByContributions().entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList());

        mav.addObject("topCoursesByContributions", topCoursesList);

        return mav;
    }

}