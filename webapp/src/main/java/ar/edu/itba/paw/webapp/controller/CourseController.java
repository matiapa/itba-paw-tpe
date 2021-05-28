package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserData;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.PollService;
import ar.edu.itba.paw.services.SgaService;
import ar.edu.itba.paw.webapp.auth.UserPrincipal;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;

@Controller
public class CourseController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @Autowired private PollService pollService;

    @Autowired private SgaService sgaService;

    private static final Logger LOGGER= LoggerFactory.getLogger(CourseController.class);

//    TODO: Fix this method
//    @RequestMapping(value = "/courses", method = GET)
//    public ModelAndView list(
//        @RequestParam(name="careerCode", required = false) String careerCode
//    ){
//        final ModelAndView mav = new ModelAndView("courses/course_list");
//
//        Map<Integer,List<CareerCourse>> courses;
//
//        List<Career> careers = careerService.findAll();
//        mav.addObject("careers", careers);
//
//        if(careerCode != null) {
//            courses = courseService.findByCareerSemester(careerCode);
//            if (courses.containsKey(0)){
//                List<CareerCourse> optionalCourses=courses.get(0);
//                courses.remove(0);
//                mav.addObject("careerOptionalCourses",optionalCourses);
//            }
//            else {
//                mav.addObject("careerOptionalCourses",null);
//            }
//
//            mav.addObject("careerCourses",courses);
//
//
//            Career selectedCareer = careers.stream().filter(c -> c.getCode().equals(careerCode)).findFirst()
//                    .orElseThrow(RuntimeException::new);
//            mav.addObject("selectedCareer", selectedCareer);
//        }
//
//        return mav;
//    }

    @RequestMapping(value = "/courses/{id:.+}", method = GET)
    public ModelAndView get(
        @PathVariable(value="id") String courseId,
        @ModelAttribute("user") UserPrincipal principal
    ){
        final User loggedUser = principal.getUser();
        final ModelAndView mav = new ModelAndView("courses/course_detail");

        // Course details

        Course selectedCourse = courseService.findById(courseId).orElseThrow(NoSuchElementException::new);
        if (selectedCourse == null){
            LOGGER.debug("user {} cound not find course with id {}",loggedUser,courseId);
            throw new ResourceNotFoundException();
        }

        mav.addObject("course", selectedCourse);

        // Course announcements

        List<Announcement> announcements = announcementService.findByCourse(selectedCourse, loggedUser, 0, 10);
        mav.addObject("announcements",announcements);

        // Course content

        List<Content> contents = contentService.findByCourse(selectedCourse, null, null, null, 0, 10);
        mav.addObject("contents", contents);

        Map<Integer, UserData> contentOwners = new HashMap<>();
        contents.forEach(c -> {
            UserData user = sgaService.fetchFromEmail(c.getOwnerMail());
            if(user != null) contentOwners.put(c.getId(), user);
        });

        mav.addObject("contentOwners", contentOwners);

        // Course polls

        List<Poll> polls = pollService.findByCourse(courseId, null, null, 0, 10);
        mav.addObject("polls",polls);

        return mav;
    }

}