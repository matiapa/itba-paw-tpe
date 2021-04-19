package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.PollService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class CourseController {

    @Autowired private CareerService careerService;

    @Autowired private AnnouncementService announcementService;

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @Autowired private PollService pollService;

    @Autowired private UserService userService;


    @RequestMapping("courses")
    public ModelAndView getCourses(
            @RequestParam(name="careerId", required = false) Integer careerId
    ){
        final ModelAndView mav = new ModelAndView("courses/courses_list");



        Map<Integer,List<CareerCourse>> courses;
        List<Career> careers = careerService.findAll();
        mav.addObject("careers", careers);
        if(careerId != null) {
            courses = careerService.findByCareer(careerId);
            mav.addObject("careerCourses",courses);
            Career selectedCareer = careers.stream().filter(c -> c.getId() == careerId).findFirst()
                    .orElseThrow(RuntimeException::new);
            mav.addObject("selectedCareer", selectedCareer);
        }

        mav.addObject("user", userService.getUser());

        return mav;
    }
    @RequestMapping("courseByCourseId")
    public ModelAndView getCourse(
            @RequestParam(name="courseId") String courseId
    ){
        final ModelAndView mav = new ModelAndView("courses/course_detail");


        List<Announcement> announcements;
        announcements = announcementService.findByCourse(courseId);
        mav.addObject("announcements",announcements);


        List<Content> contents;
        contents = contentService.findByCourse(courseId);
        mav.addObject("contents",contents);

        List<Poll> polls;
        polls = pollService.findByCourse(courseId);
        mav.addObject("polls",polls);

        Optional<Course> selectedCourse = courseService.findById(courseId);
        mav.addObject("selectedCourse", selectedCourse);


        mav.addObject("user", userService.getUser());

        return mav;
    }

}