package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.models.ui.Panel;
import ar.edu.itba.paw.services.*;
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
public class CourseController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private CourseService courseService;

    @Autowired private CareerService careerService;

    @Autowired private ContentService contentService;

    @Autowired private PollService pollService;

    @RequestMapping("/courses/byId")
    public ModelAndView getCourseById(
        @RequestParam(name = "id") String id
    ) {
        Optional<Course> courseOpt = courseService.findById(id);
        if(! courseOpt.isPresent()){
            throw new ResponseStatusException(NOT_FOUND, "Curso no encontrado");
        }
        Course course = courseOpt.get();

        final ModelAndView mav = new BaseMav(
            course.getName(),
            "panels.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem(course.getName(), "/courses/byId?id="+course.getId())
            )
        );

        mav.addObject("panels", Arrays.asList(
            null,

            new Panel("Contenido", "/contents/byCourse?&courseId="+course.getId(),
                    "content_source/content_short_list.jsp"),

            new Panel("Encuestas del curso", "/polls/byCourse?courseId="+course.getId(),
                    "poll/poll_short_list.jsp"),

            new Panel("Anuncios del curso", "",
                    "announcement/announcement_list.jsp")
        ));

        mav.addObject("announcements", announcementService.findByCourse(course.getId()));

        mav.addObject("contents", contentService.findByCourse(course.getId(), 4));

        mav.addObject("polls", pollService.findByCourse(course.getId()));

        return mav;
    }


    @RequestMapping("/courses/byCareer")
    public ModelAndView getCoursesByCareer(
        @RequestParam(name = "careerId") int careerId
    ) {
        Optional<Career> careerOpt = careerService.findById(careerId);
        if(! careerOpt.isPresent()){
            throw new ResponseStatusException(NOT_FOUND, "Carrera no encontrada");
        }
        Career career = careerOpt.get();

        final ModelAndView mav = new BaseMav(
            String.format("Cursos de %s", career.getName()),
            "course/course_full_list.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem(career.getName(), "/careers/byId?id="+career.getId()),
                new NavigationItem("Cursos", "/courses/byCareer?careerId="+career.getId())
            )
        );

        mav.addObject("courses", courseService.findByCareer(careerId));

        return mav;
    }


    @RequestMapping("/courses/favourites")
    public ModelAndView getFavouriteCourses() {
        final ModelAndView mav = new BaseMav(
            "Tus cursos favoritos",
            "course/course_full_list.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem("Cursos favoritos", "/courses/favourites")
            )
        );

        mav.addObject("title", "Tus cursos favoritos");

        mav.addObject("navigationHistory", Arrays.asList(
            new NavigationItem("Home", "/"),
            new NavigationItem("Cursos favoritos","/courses/favourites")
        ));

        mav.addObject("courses", courseService.findFavourites());

        return mav;
    }

}