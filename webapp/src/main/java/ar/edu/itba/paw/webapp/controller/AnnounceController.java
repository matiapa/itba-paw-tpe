package ar.edu.itba.paw.webapp.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.common.FiltersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.webapp.form.AnnouncementForm;

@Controller
public class AnnounceController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private UserService userService;

    @Autowired private FiltersController commonFilters;


    @RequestMapping(value = "/announcements", method = GET)
    public ModelAndView getAnnouncements(
        @RequestParam(name="filterBy", required = false, defaultValue="general") HolderEntity filterBy,
        @RequestParam(name="careerCode", required = false) String careerCode,
        @RequestParam(name="courseId", required = false) String courseId,
        @RequestParam(name="showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final AnnouncementForm form
    ){
        final ModelAndView mav = new ModelAndView("announcements/announcements_list");

        // Add filters options

        mav.addObject("filterBy", filterBy);
        commonFilters.addCareers(mav, careerCode);
        commonFilters.addCourses(mav, careerCode);

        // Add filtered announcements

        List<Announcement> announcements = new ArrayList<>();
        switch(filterBy){
            case career:
                if(careerCode != null)
                    announcements = announcementService.findByCareer(careerCode);
                break;
            case course:
                if(courseId != null)
                    announcements = announcementService.findByCourse(courseId);
                break;
            case general:
            default:
                announcements = announcementService.findGeneral();
        }

        mav.addObject("announcements", announcements);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("user", userService.getLoggedUser());

        return mav;
    }


    @RequestMapping(value = "/announcements/create", method = POST)
    public ModelAndView create(
            @Valid @ModelAttribute("createForm") final AnnouncementForm form,
            final BindingResult errors
    ){
        if (errors.hasErrors()) {
            return getAnnouncements(
                HolderEntity.general, null, null, true, form
            );
        }

        announcementService.create(
            form.getTitle(), form.getSummary(), form.getContent(),
            form.getCareerCode(), form.getCourseId(), form.getExpiryDate(),
            userService.getLoggedUser()
        );

        HolderEntity filterBy;
        if(form.getCareerCode() == null && form.getCourseId() == null)
            filterBy = HolderEntity.general;
        else if(form.getCareerCode() == null && form.getCourseId() != null)
            filterBy = HolderEntity.course;
        else if(form.getCareerCode() != null && form.getCourseId() == null)
            filterBy = HolderEntity.career;
        else
            filterBy = HolderEntity.general;

        return getAnnouncements(filterBy, form.getCareerCode(), form.getCourseId(), false, form);
    }


    @RequestMapping(value = "/announcements/detail", method = GET)
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


    @RequestMapping(value = "/announcements/markSeen", method = POST)
    public ModelAndView markSeen(
        @RequestParam(name = "id") int id
    ) {
        final ModelAndView mav = new ModelAndView("simple");
        mav.addObject("text", "OK");

        announcementService.markSeen(id, userService.getLoggedUser());

        return mav;
    }

}