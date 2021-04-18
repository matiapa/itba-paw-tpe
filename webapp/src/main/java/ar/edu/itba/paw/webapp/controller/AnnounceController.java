package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AnnounceController {

    @Autowired private UserService userService;

    @Autowired private AnnouncementService announcementService;

    @Autowired private CareerService careerService;

    @Autowired private CourseService courseService;


    @RequestMapping("announcements")
    public ModelAndView getAnnouncements(
        @RequestParam(name="filterBy", required = false, defaultValue="general") HolderEntity filterBy,
        @RequestParam(name="careerId", required = false) Integer careerId,
        @RequestParam(name="courseId", required = false) String courseId
    ){
        final ModelAndView mav = new ModelAndView("announcements/announcements_list");

        mav.addObject("filterBy", filterBy);

        List<Announcement> announcements = new ArrayList<>();
        switch(filterBy){
            case career:
                List<Career> careers = careerService.findAll();
                mav.addObject("careers", careers);
                if(careerId != null){
                    announcements = announcementService.findByCareer(careerId);
                    Career selectedCareer = careers.stream().filter(c -> c.getId() == careerId).findFirst()
                            .orElseThrow(RuntimeException::new);
                    mav.addObject("selectedCareer", selectedCareer);
                }
                break;

            case course:
                List<Course> courses = courseService.findAll();
                mav.addObject("courses", courses);
                if(courseId != null){
                    announcements = announcementService.findByCourse(courseId);
                    Course selectedCourse = courses.stream().filter(c -> c.getId().equals(courseId)).findFirst()
                            .orElseThrow(RuntimeException::new);
                    mav.addObject("selectedCourse", selectedCourse);
                }
                break;

            case general:
            default:
                announcements = announcementService.findGeneral();
        }

        mav.addObject("announcements", announcements);

        mav.addObject("user", userService.getUser());

        return mav;
    }


    @RequestMapping(value = "announcements/markSeen", method = POST)
    public ModelAndView getDashboard(
        @RequestParam(name = "id") int id
    ) {
        final ModelAndView mav = new ModelAndView("simple");
        mav.addObject("text", "OK");

        announcementService.markSeen(id);

        return mav;
    }

}