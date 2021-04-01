package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
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
public class CourseController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    CourseService courseService;

    @Autowired
    CareerService careerService;


    @RequestMapping("/courses/byId")
    public ModelAndView getCourseById(
        @RequestParam(name = "id") String id
    ) {
        final ModelAndView mav = new ModelAndView("main");

        Optional<Course> courseOpt = courseService.findById(id);
        if(! courseOpt.isPresent()){
            throw new ResponseStatusException(NOT_FOUND, "Curso no encontrado");
        }
        Course course = courseOpt.get();

        Career career = careerService.findByCourse(course);

        mav.addObject("title", course.getName());

        mav.addObject("breadcrumbItems", Arrays.asList(
            new BreadcrumbItem("Home", "/"),

            new BreadcrumbItem(
                ""+career.getName(),
                "/careers/byId?id="+career.getId()
            ),

            new BreadcrumbItem(
                course.getName(),
                "/course/detail?id="+course.getId()
            )
        ));

        mav.addObject("contentViewName", "panels.jsp");

        mav.addObject("panels", Arrays.asList(
            new Panel("Grupos de chat", "",
                    "chat_group/chat_group_short_list.jsp"),

            new Panel("Contenido", "",
                    "content_source/content_short_list.jsp"),

            new Panel("Encuestas del curso", "",
                    "poll/poll_short_list.jsp"),

            new Panel("Anuncios del curso", "",
                    "announcement/announcement_list.jsp")
        ));

        mav.addObject("announcements", announcementService.findByCourse(course.getId()));

        return mav;
    }


    @RequestMapping("/courses/byCareer")
    public ModelAndView getCoursesByCareer(
        @RequestParam(name = "careerId") int careerId
    ) {
        final ModelAndView mav = new ModelAndView("main");

        Optional<Career> careerOpt = careerService.findById(careerId);
        if(! careerOpt.isPresent()){
            throw new ResponseStatusException(NOT_FOUND, "Carrera no encontrada");
        }
        Career career = careerOpt.get();

        mav.addObject("title", String.format("Cursos de %s", career.getName()));

        mav.addObject("breadcrumbItems", Arrays.asList(
                new BreadcrumbItem("Home", "/"),

                new BreadcrumbItem(
                    ""+career.getName(),
                    "/careers/byId?id="+career.getId()
                )
        ));

        mav.addObject("contentViewName", "course/course_full_list.jsp");

        mav.addObject("courses", courseService.findFavourites(1));

        return mav;
    }


    @RequestMapping("/courses/favourites")
    public ModelAndView getFavouriteCourses() {
        final ModelAndView mav = new ModelAndView("main");

        mav.addObject("title", "Tus cursos favoritos");

        mav.addObject("breadcrumbItems", Arrays.asList(
            new BreadcrumbItem("Home", "/"),
            new BreadcrumbItem("Cursos favoritos","/courses/favourites")
        ));

        mav.addObject("contentViewName", "course/course_full_list.jsp");

        mav.addObject("courses", courseService.findFavourites(1));

        return mav;
    }

}