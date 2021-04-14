package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.mav.BaseMav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @RequestMapping("announcements/byId")
    public ModelAndView getAnnouncementDetails(
        @RequestParam(name = "id") int id
    ){
        Optional<Announcement> optionalAnnounce = announcementService.findById(id);
        if (!optionalAnnounce.isPresent()){
            throw new ResponseStatusException(NOT_FOUND, "Anuncio no encontrado");
        }
        Announcement announce = optionalAnnounce.get();

        final ModelAndView mav = new BaseMav(
            announce.getTitle(),
            "announcement/announcement_detail.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem(announce.getTitle(), "/announcement/byId?id=" + id)
            )
        );

        mav.addObject("announcement", announce);

        return mav;
    }

}