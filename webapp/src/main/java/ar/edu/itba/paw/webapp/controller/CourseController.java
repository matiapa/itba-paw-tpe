package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.*;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.webapp.auth.UserPrincipal;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;

@Controller
public class CourseController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @Autowired private CareerService careerService;

    @Autowired private PollService pollService;

    @Autowired private SgaService sgaService;


    private static final Logger LOGGER= LoggerFactory.getLogger(CourseController.class);

    @RequestMapping(value = "/courses", method = GET)
    public ModelAndView list(
        @RequestParam(name="careerCode", required = false) String careerCode
    ){
        final ModelAndView mav = new ModelAndView("courses/course_list");

        List<Career> careers = careerService.findAll();
        mav.addObject("careers", careers);

        if(careerCode != null) {
            Career selectedCareer = careers.stream().filter(c -> c.getCode().equals(careerCode)).findFirst()
                    .orElseThrow(NoSuchElementException::new);

            List<CareerCourse> careerCourses = courseService.findByCareer(selectedCareer);

            Map<Integer, List<Course>> coursesBySemester = new HashMap<>();
            List<Course> optionalCourses = new ArrayList<>();

            careerCourses.forEach(cc -> {
                if(cc.getSemester() == 0)
                    optionalCourses.add(cc.getCourse());
                else if(coursesBySemester.get(cc.getSemester()) == null) {
                    LinkedList<Course> ll = new LinkedList<>();
                    ll.add(cc.getCourse());
                    coursesBySemester.put(cc.getSemester(), ll);
                }else
                    coursesBySemester.get(cc.getSemester()).add(cc.getCourse());
            });

            mav.addObject("optionalCourses", optionalCourses);
            mav.addObject("coursesBySemester", coursesBySemester);
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

//        List<Poll> polls = pollService.findByCourse(courseId, null, null, 0, 10);
//        mav.addObject("polls",polls);

        return mav;
    }

    @RequestMapping(value = "courses/{id:.+}/fav", method = POST)
    public ModelAndView addFav(
            @PathVariable(value="id") String id,
            @ModelAttribute("user") User loggedUser
    ){
        courseService.markFavorite(
            courseService.findById(id).orElseThrow(NoSuchElementException::new),
            loggedUser, true
        );
        return get(id, loggedUser);
    }

    @RequestMapping(value = "courses/{id:.+}/unfav", method = POST)
    public ModelAndView removeFav(
            @PathVariable(value="id") String id,
            @ModelAttribute("user") User loggedUser
    ){
        courseService.markFavorite(
            courseService.findById(id).orElseThrow(NoSuchElementException::new),
            loggedUser, false
        );
        return get(id, loggedUser);
    }

}