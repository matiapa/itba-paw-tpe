package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ui.BreadcrumbItem;
import ar.edu.itba.paw.models.ui.Panel;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
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

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    CareerService careerService;

    @Autowired
    CourseService courseService;


    @RequestMapping("careers/byId")
    public ModelAndView getCareerDetail(
            @RequestParam(name = "id") int id
    ){
        final ModelAndView modelAndView = new ModelAndView("main");
        Optional<Career> optionalCareer = careerService.findById(id);
        if (!optionalCareer.isPresent()){
            throw new ResponseStatusException(NOT_FOUND, "Carrera no encontrada");
        }

        Career career = optionalCareer.get();
        modelAndView.addObject("title", career.getName());

        modelAndView.addObject("breadcrumbItems", Arrays.asList(
                new BreadcrumbItem("Home", "/"),
                new BreadcrumbItem(career.getName(), "/careers/byId?id="+career.getId())
        ));

        modelAndView.addObject("contentViewName", "panels.jsp");
        modelAndView.addObject("panels", Arrays.asList(
            new Panel("Lista de cursos", "/courses/byCareer?careerId="+career.getId(),
                    "course/course_short_list.jsp"),

            new Panel("Grupos de chat", "",
                    "chat_group/chat_group_short_list.jsp"),

            new Panel("Encuestas de la carrera", "",
                    "poll/poll_short_list.jsp"),

            new Panel("Anuncios de la carrera", "",
                    "announcement/announcement_list.jsp")
        ));

        modelAndView.addObject("courses",
                courseService.findByCareer(career.getId()));

        modelAndView.addObject("announcements",
                announcementService.findByCareer(career.getId()));

        return modelAndView;
    }

}
