package ar.edu.itba.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.common.FiltersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.webapp.form.AnnouncementForm;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class AnnounceController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private UserService userService;

    @Autowired private FiltersController commonFilters;


    @RequestMapping(value = "/announcements", method = GET)
    public ModelAndView list(
        @RequestParam(name="filterBy", required = false, defaultValue="general") HolderEntity filterBy,
        @RequestParam(name="careerCode", required = false) String careerCode,
        @RequestParam(name="courseId", required = false) String courseId,
        @RequestParam(name="showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final AnnouncementForm form,
        @RequestParam(name="showSeen", required = false, defaultValue="false") Boolean showSeen
    ){
        final ModelAndView mav = new ModelAndView("announcements/announcements_list");

        // Add filters options

        mav.addObject("filterBy", filterBy);
        commonFilters.addCareers(mav, careerCode);
        commonFilters.addCourses(mav, courseId);

        // Add filtered announcements

        List<Announcement> announcements = new ArrayList<>();
        switch(filterBy){
            case career:
                if(careerCode != null)
                    announcements = announcementService.findByCareer(careerCode, showSeen);
                break;
            case course:
                if(courseId != null)
                    announcements = announcementService.findByCourse(courseId, showSeen);
                break;
            case general:
            default:
                announcements = announcementService.findGeneral(showSeen);
        }

        mav.addObject("announcements", announcements);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("showSeen", showSeen);

        User loggedUser = userService.getLoggedUser();
        mav.addObject("user", loggedUser);
        mav.addObject("canDelete", loggedUser.getPermissions().contains(
            new Permission(Permission.Action.DELETE, Permission.Entity.ANNOUNCEMENT)
        ));

        return mav;
    }


    @RequestMapping(value = "/announcements", method = POST)
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final AnnouncementForm form,
        final BindingResult errors
    ){
        if (errors.hasErrors()) {
            return list(HolderEntity.general, null, null, true, form, false);
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

        return list(filterBy, form.getCareerCode(), form.getCourseId(), false, form, false);
    }


    @RequestMapping(value = "/announcements/{id}", method = GET)
    public ModelAndView read(
        @PathVariable(value = "id") Integer id
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


    @RequestMapping(value = "/announcements/{id}", method = DELETE)
    public String delete(
        @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        announcementService.delete(id);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping(value = "/announcements/{id}/delete", method = POST)
    public String deleteWithPost(
        @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        return delete(id, request);
    }


    @RequestMapping(value = "/announcements/{id}/seen", method = POST)
    public String markSeen(
        @PathVariable(value="id") int id,
        HttpServletRequest request
    ) {
        announcementService.markSeen(id);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

}