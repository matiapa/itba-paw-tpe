package ar.edu.itba.paw.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.ui.Pager;
import ar.edu.itba.paw.services.*;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CourseController {

    @Autowired private CareerService careerService;

    @Autowired private AnnouncementService announcementService;

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @Autowired private PollService pollService;

    @Autowired private UserService userService;


    @RequestMapping(value = "/courses", method = GET)
    public ModelAndView list(
        @RequestParam(name="careerCode", required = false) String careerCode
    ){
        final ModelAndView mav = new ModelAndView("courses/course_list");

        Map<Integer,List<CareerCourse>> courses;

        List<Career> careers = careerService.findAll();
        mav.addObject("careers", careers);

        if(careerCode != null) {
            courses = courseService.findByCareerSemester(careerCode);
            mav.addObject("careerCourses",courses);

            Career selectedCareer = careers.stream().filter(c -> c.getCode().equals(careerCode)).findFirst()
                    .orElseThrow(RuntimeException::new);
            mav.addObject("selectedCareer", selectedCareer);
        }

        return mav;
    }

    @RequestMapping(value = "/courses/{id:.+}", method = GET)
    public ModelAndView get(
        @PathVariable(value="id") String courseId,
        @ModelAttribute("user") User loggedUser
    ){
        final ModelAndView mav = new ModelAndView("courses/course_detail");

        // Course details

        Optional<Course> selectedCourse = courseService.findById(courseId);
        if (!selectedCourse.isPresent())
            throw new ResourceNotFoundException();

        mav.addObject("course", selectedCourse.get());

        // Course announcements

        List<Announcement> announcements = announcementService.findByCourse(loggedUser, courseId, false, 0, 10);
        mav.addObject("announcements",announcements);

        // Course content

        List<Content> contents = contentService.findByCourse(courseId, 0, 10);
        mav.addObject("contents", contents);

        // Course polls

        List<Poll> polls = pollService.findByCourse(courseId, null, null, 0, 10);
        mav.addObject("polls",polls);

        // Other parameters

        mav.addObject("canDelete", loggedUser.getPermissions().contains(
            new Permission(Permission.Action.DELETE, Entity.COURSE_CONTENT)
        ));

        return mav;
    }

}