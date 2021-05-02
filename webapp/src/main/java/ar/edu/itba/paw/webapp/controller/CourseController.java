package ar.edu.itba.paw.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.ui.Pager;
import ar.edu.itba.paw.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CourseController {
    static final int LIMIT = 5;

    @Autowired private CareerService careerService;

    @Autowired private AnnouncementService announcementService;

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @Autowired private PollService pollService;

    @Autowired private UserService userService;


    @RequestMapping(value = "/courses", method = GET)
    public ModelAndView getCourses(
            @RequestParam(name="careerCode", required = false) String careerCode
    ){
        final ModelAndView mav = new ModelAndView("courses/course_list");

        Map<Integer,List<CareerCourse>> courses;

        List<Career> careers = careerService.findAll();
        mav.addObject("careers", careers);

        if(careerCode != null) {
            courses = careerService.findByCareer(careerCode);
            mav.addObject("careerCourses",courses);

            Career selectedCareer = careers.stream().filter(c -> c.getCode().equals(careerCode)).findFirst()
                    .orElseThrow(RuntimeException::new);
            mav.addObject("selectedCareer", selectedCareer);
        }

        mav.addObject("user", userService.getLoggedUser());

        return mav;
    }


    @RequestMapping(value = "/courses/detail", method = GET)
    public ModelAndView getCourse(
        @RequestParam(name="id") String courseId,
        @RequestParam(name="page", required = false, defaultValue = "0") Integer page
    ){
        final ModelAndView mav = new ModelAndView("courses/course_detail");

        // Course announcements

        Pager pager = new Pager(announcementService.getSize(HolderEntity.course, courseId), page);
        List<Announcement> announcements;
        announcements = announcementService.findByCourse(courseId, pager.getOffset(), pager.getLimit());
        mav.addObject("announcements",announcements);
        mav.addObject("pager", pager);
        // Course content

        List<Content> contents;
        contents = contentService.findByCourse(courseId);
        mav.addObject("contents",contents);

        mav.addObject("contentTypeEnumMap", new HashMap<Content.ContentType, String>()
        {{
            put(Content.ContentType.exam, "Exámen");
            put(Content.ContentType.guide, "Guía");
            put(Content.ContentType.note, "Apunte");
            put(Content.ContentType.resume, "Resúmen");
            put(Content.ContentType.other, "Otro");
        }});

        // Course polls

        List<Poll> polls;
        polls = pollService.findByCourse(courseId);
        mav.addObject("polls",polls);

        // Course details

        Optional<Course> selectedCourse = courseService.findById(courseId);
        if (!selectedCourse.isPresent())
            throw new RuntimeException();
        mav.addObject("course", selectedCourse.get());

        mav.addObject("user", userService.getLoggedUser());

        return mav;
    }

}