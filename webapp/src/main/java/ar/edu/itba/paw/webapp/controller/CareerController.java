package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.models.ui.Panel;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.ChatGroupService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.webapp.mav.BaseMav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class CareerController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private CareerService careerService;

    @Autowired private CourseService courseService;

    @Autowired private ChatGroupService chatGroupService;

    @Autowired PollService pollService;

    @RequestMapping("careers/byId")
    public ModelAndView getCareerDetail(
        @RequestParam(name = "id") int id
    ){
        Optional<Career> optionalCareer = careerService.findById(id);
        if (!optionalCareer.isPresent()){
            throw new ResponseStatusException(NOT_FOUND, "Carrera no encontrada");
        }
        Career career = optionalCareer.get();

        final ModelAndView mav = new BaseMav(
            career.getName(),
            "panels.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem(career.getName(), "/careers/byId?id="+career.getId())
            )
        );

        mav.addObject("panels", Arrays.asList(
            new Panel("Lista de cursos", "/courses/byCareer?careerId="+career.getId(),
                    "course/course_short_list.jsp"),

            new Panel("Grupos de chat", "/groups/byCareer?careerId="+career.getId(),
                    "chat_group/chat_group_short_list.jsp"),

            new Panel("Encuestas de la carrera", "/polls/byCareer?careerId="+career.getId(),
                    "poll/poll_short_list.jsp"),

            new Panel("Anuncios de la carrera", "",
                    "announcement/announcement_list.jsp")
        ));

        mav.addObject("courses",
                courseService.findByCareer(career.getId(), 4));

        mav.addObject("chat_groups",
                chatGroupService.findByCareer(career.getId(), 4));

        mav.addObject("announcements",
                announcementService.findByCareer(career.getId()));

        mav.addObject("polls", 
                pollService.findByCareer(career.getId()));

        return mav;
    }

}