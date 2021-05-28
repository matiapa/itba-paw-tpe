package ar.edu.itba.paw.webapp.controller;



import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.ui.Pager;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.webapp.auth.UserPrincipal;
import ar.edu.itba.paw.webapp.controller.common.CommonFilters;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
import ar.edu.itba.paw.webapp.form.AnnounceForm;

@Controller
public class AnnounceController {

    @Autowired private AnnouncementService announcementService;

    @Autowired private CareerService careerService;

    @Autowired private CourseService courseService;

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
        @ModelAttribute("user") UserPrincipal principal
    ){
        final ModelAndView mav = new ModelAndView("announcements/announcements_list");
        final User loggedUser = principal.getUser();

        // Add filters options

        mav.addObject("filterBy", filterBy);
        Career selectedCareer = commonFilters.addCareers(mav, careerCode);
        Course selectedCourse = commonFilters.addCourses(mav, courseId);

        // Add filtered announcements

        List<Announcement> announcements = new ArrayList<>();
        Pager pager = null;

        switch(filterBy){
            case career:
                if(careerCode != null){
                    pager = new Pager(announcementService.getSize(filterBy, careerCode, loggedUser), page);
                    announcements = announcementService.findByCareer(selectedCareer, loggedUser, pager.getOffset(), pager.getLimit());
                }
                break;
            case course:
                if(courseId != null){
                    pager = new Pager(announcementService.getSize(filterBy, courseId, loggedUser), page);
                    announcements = announcementService.findByCourse(selectedCourse, loggedUser, pager.getOffset(), pager.getLimit());
                }
                break;
            case general:
            default:
                pager = new Pager(announcementService.getSize(filterBy, courseId, loggedUser), page);
                announcements = announcementService.findGeneral(loggedUser, pager.getOffset(), pager.getLimit());
        }

        mav.addObject("pager", pager);
        mav.addObject("announcements", announcements);

        // Add other parameters

        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("showSeen", showSeen);

        return mav;
    }


    @RequestMapping(value = "/announcements", method = POST)
    public ModelAndView create(
        @Valid @ModelAttribute("createForm") final AnnounceForm form,
        @ModelAttribute("user") UserPrincipal principal,
        final BindingResult errors
    ){
        final User loggedUser = principal.getUser();

        if (errors.hasErrors()) {
            return list(EntityTarget.general, null, null, true, false,
                0, form, principal);
        }

        Career career = careerService.findByCode(form.getCareerCode()).orElseThrow(ResourceNotFoundException::new);
        Course course = courseService.findById(form.getCourseId()).orElseThrow(ResourceNotFoundException::new);

        announcementService.create(
            form.getTitle(), form.getSummary(), form.getContent(),
            career, course, form.getExpiryDate(),
            loggedUser
        );

        EntityTarget filterBy = commonFilters.getTarget(form.getCareerCode(), form.getCourseId());

        return list(filterBy, form.getCareerCode(), form.getCourseId(), false, false,
                0, form, principal);
    }


    @RequestMapping(value = "/announcements/{id}", method = GET)
    public ModelAndView get(
        @PathVariable(value = "id") Integer id,
        @ModelAttribute("user") UserPrincipal principal
    ){
        final User loggedUser = principal.getUser();
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
        @ModelAttribute("user") UserPrincipal principal
    ) {
        final User loggedUser = principal.getUser();
        Announcement announcement = announcementService.findById(id).orElseThrow(ResourceNotFoundException::new);
        
        LOGGER.debug("user {} is attempting to delete announcement with id {}",loggedUser,id);
        announcementService.delete(announcement, loggedUser);
        LOGGER.debug("user {} deleted announcement with id {}",loggedUser,id);

        return "redirect:"+ request.getHeader("Referer");
    }

    @RequestMapping(value = "/announcements/{id}/delete", method = POST)
    public String deleteWithPost(
        @PathVariable(value="id") int id, HttpServletRequest request,
        @ModelAttribute("user") UserPrincipal principal
    ) {
        return delete(id, request, principal);
    }


    @RequestMapping(value = "/announcements/{id}/seen", method = POST)
    public String markSeen(
        @PathVariable(value="id") int id, HttpServletRequest request,
        @ModelAttribute("user") UserPrincipal principal
    ) {
        final User loggedUser = principal.getUser();

        LOGGER.debug("user {} is trying to mark announcement with id {} as seen",loggedUser,id);

        announcementService.markSeen(announcementService.findById(id)
                .orElseThrow(ResourceNotFoundException::new), loggedUser);

        LOGGER.debug("user {} marked announcement with id {} as seen",loggedUser,id);

        return "redirect:"+ request.getHeader("Referer");
    }

    @RequestMapping(value = "/announcements/seen", method = POST)
    public String markAllSeen(
        HttpServletRequest request,
        @ModelAttribute("user") UserPrincipal principal
    ) {
        final User loggedUser = principal.getUser();
        announcementService.markAllSeen(loggedUser);

        return "redirect:"+ request.getHeader("Referer");
    }

}