package ar.edu.itba.paw.webapp.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.ui.Pager;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.common.CommonFilters;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.webapp.form.AnnounceForm;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AnnounceController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private UserService userService;

    @Autowired private CommonFilters commonFilters;


    private static final Logger LOGGER= LoggerFactory.getLogger(AnnounceController.class);


    @RequestMapping(value = "/announcements", method = GET)
    public ModelAndView list(
        @RequestParam(name="filterBy", required=false, defaultValue="general") EntityTarget filterBy,
        @RequestParam(name="careerCode", required=false) String careerCode,
        @RequestParam(name="courseId", required=false) String courseId,
        @RequestParam(name="showCreateForm", required=false, defaultValue="false") boolean showCreateForm,
        @RequestParam(name="showSeen", required = false, defaultValue="false") boolean showSeen,
        @RequestParam(name="page", required = false, defaultValue = "0") int page,
        @ModelAttribute("createForm") final AnnounceForm form,
        @ModelAttribute("user") final User loggedUser
    ){
        final ModelAndView mav = new ModelAndView("announcements/announcements_list");

        // Add filters options

        mav.addObject("filterBy", filterBy);
        commonFilters.addCareers(mav, careerCode);
        commonFilters.addCourses(mav, courseId);

        // Add filtered announcements

        List<Announcement> announcements = new ArrayList<>();
        Pager pager = null;

        int userId = loggedUser.getId();

        switch(filterBy){
            case career:
                if(careerCode != null){
                    pager = new Pager(announcementService.getSize(filterBy, careerCode, showSeen, userId), page);
                    announcements = announcementService.findByCareer(loggedUser, careerCode, showSeen, pager.getOffset(), pager.getLimit());
                }
                break;
            case course:
                if(courseId != null){
                    pager = new Pager(announcementService.getSize(filterBy, courseId, showSeen, userId), page);
                    announcements = announcementService.findByCourse(loggedUser, courseId, showSeen, pager.getOffset(), pager.getLimit());
                }
                break;
            case general:
            default:
                pager = new Pager(announcementService.getSize(filterBy, courseId, showSeen, userId), page);
                announcements = announcementService.findGeneral(loggedUser, showSeen, pager.getOffset(), pager.getLimit());
        }

        mav.addObject("pager", pager);
        mav.addObject("announcements", announcements);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("showSeen", showSeen);

        mav.addObject("canCreate", loggedUser.getPermissions().contains(
            new Permission(Permission.Action.create, Entity.announcement)
        ));

        mav.addObject("canDelete", loggedUser.getPermissions().contains(
            new Permission(Permission.Action.delete, Entity.announcement)
        ));

        return mav;
    }


    @RequestMapping(value = "/announcements", method = POST)
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final AnnounceForm form,
        @ModelAttribute("user") User loggedUser,
        final BindingResult errors
    ){
        if (errors.hasErrors()) {
            return list(EntityTarget.general, null, null, true, false,
            0, form, loggedUser);
        }

        announcementService.create(
            form.getTitle(), form.getSummary(), form.getContent(),
            form.getCareerCode(), form.getCourseId(), form.getExpiryDate(),
            loggedUser
        );

        EntityTarget filterBy = commonFilters.getTarget(form.getCareerCode(), form.getCourseId());

        return list(filterBy, form.getCareerCode(), form.getCourseId(), false, false,
            0, form, loggedUser);
    }


    @RequestMapping(value = "/announcements/{id}", method = GET)
    public ModelAndView get(
        @PathVariable(value = "id") Integer id,
        @ModelAttribute("user") User loggedUser
    ){
        final ModelAndView mav = new ModelAndView("announcements/announcements_detail");

        Optional<Announcement> optionalAnnouncement = announcementService.findById(id);
        if (! optionalAnnouncement.isPresent()){
            LOGGER.debug("user {} tried accessing announcement with id {} but didnt exist",loggedUser,id);
            throw new ResourceNotFoundException();
        }

        mav.addObject("announcement", optionalAnnouncement.get());

        return mav;
    }


    @RequestMapping(value = "/announcements/{id}", method = DELETE)
    public String delete(
        @PathVariable(value="id") int id, HttpServletRequest request,
        @ModelAttribute("user") User loggedUser
    ) {

        LOGGER.debug("user {} is attempting to delete announcement with id {}",loggedUser,id);
        announcementService.delete(loggedUser, id);
        LOGGER.debug("user {} deleted announcement with id {}",loggedUser,id);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping(value = "/announcements/{id}/delete", method = POST)
    public String deleteWithPost(
        @PathVariable(value="id") int id, HttpServletRequest request,
        @ModelAttribute("user") User loggedUser
    ) {
        return delete(id, request, loggedUser);
    }


    @RequestMapping(value = "/announcements/{id}/seen", method = POST)
    public String markSeen(
        @PathVariable(value="id") int id, HttpServletRequest request,
        @ModelAttribute("user") User loggedUser
    ) {

        LOGGER.debug("user {} is trying to mark announcement with id {} as seen",loggedUser,id);

        announcementService.markSeen(loggedUser, id);

        LOGGER.debug("user {} marked announcement with id {} as seen",loggedUser,id);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

}