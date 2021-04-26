package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.webapp.form.AnnouncementForm;

@Controller
public class AnnounceController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private CareerService careerService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;


    @RequestMapping(value = "announcements", method = GET)
    public ModelAndView getAnnouncements(
        @RequestParam(name="filterBy", required = false, defaultValue="general") HolderEntity filterBy,
        @RequestParam(name="careerId", required = false) Integer careerId,
        @RequestParam(name="courseId", required = false) String courseId,
        @RequestParam(name="courseId", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final AnnouncementForm form
    ){
        final ModelAndView mav = new ModelAndView("announcements/announcements_list");

        // Filters

        mav.addObject("filterBy", filterBy);

            // -- Career

        List<Career> careers = careerService.findAll();
        mav.addObject("careers", careers);

        Career selectedCareer = careerId != null ? careers.stream().filter(c -> c.getId() == careerId).findFirst()
                .orElseThrow(RuntimeException::new) : null;
        mav.addObject("selectedCareer", selectedCareer);

            // -- Course

        List<Course> courses = courseService.findAll();
        mav.addObject("courses", courses);

        Course selectedCourse = courseId != null ? courses.stream().filter(c -> c.getId().equals(courseId)).findFirst()
                .orElseThrow(RuntimeException::new) : null;
        mav.addObject("selectedCourse", selectedCourse);

        // Announcements

        List<Announcement> announcements = new ArrayList<>();
        switch(filterBy){
            case career:
                if(careerId != null)
                    announcements = announcementService.findByCareer(careerId);
                break;
            case course:
                if(courseId != null)
                    announcements = announcementService.findByCourse(courseId);
                break;
            case general:
            default:
                announcements = announcementService.findGeneral();
        }

        mav.addObject("showCreateForm", showCreateForm);

        mav.addObject("announcements", announcements);

        mav.addObject("user", userService.getLoggedUser());

        return mav;
    }


    @RequestMapping(value = "announcements", method = POST)
    public ModelAndView create(
            @Valid @ModelAttribute("createForm") final AnnouncementForm form,
            final BindingResult errors
    ){
        // TODO: Form validation is not working
        if (errors.hasErrors()) {
            return getAnnouncements(
                HolderEntity.general, null, null, true, form
            );
        }

        announcementService.create(
            form.getTitle(), form.getSummary(), form.getContent(),
            form.getCareerId(), form.getCourseId(), form.getExpiryDate(),
            userService.getLoggedUser()
        );

        HolderEntity filterBy;
        if(form.getCareerId() == null && form.getCourseId() == null)
            filterBy = HolderEntity.general;
        else if(form.getCareerId() == null && form.getCourseId() != null)
            filterBy = HolderEntity.course;
        else if(form.getCareerId() != null && form.getCourseId() == null)
            filterBy = HolderEntity.career;
        else
            filterBy = HolderEntity.general;

        return getAnnouncements(filterBy, form.getCareerId(), form.getCourseId(), false, form);
    }


    @RequestMapping(value = "announcements/detail", method = GET)
    public ModelAndView getAnnouncementDetail(
        @RequestParam(name="id", required = false) Integer id
    ){
        final ModelAndView mav = new ModelAndView("announcements/announcements_detail");

        Optional<Announcement> optionalAnnouncement = announcementService.findById(id);
        if (! optionalAnnouncement.isPresent()){
            throw new RuntimeException("Anuncio no encontrado");
        }

        mav.addObject("announcement", optionalAnnouncement.get());

        mav.addObject("user", userService.getLoggedUser());

        return mav;
    }


    @RequestMapping(value = "announcements/markSeen", method = POST)
    public ModelAndView markSeen(
        @RequestParam(name = "id") int id
    ) {
        final ModelAndView mav = new ModelAndView("simple");
        mav.addObject("text", "OK");

        announcementService.markSeen(id, userService.getLoggedUser());

        return mav;
    }

}