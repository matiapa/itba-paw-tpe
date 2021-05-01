package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Permission;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.controller.common.FiltersController;
import ar.edu.itba.paw.webapp.form.ContentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@Controller
public class ContentController {

    @Autowired private ContentService contentService;

    @Autowired private UserService userService;

    @Autowired private FiltersController commonFilters;


    @RequestMapping(value = "/contents", method = GET)
    public ModelAndView getContents(
        @RequestParam(name = "courseId", required = false) String courseId,
        @RequestParam(name = "contentType", required = false) String contentType,
        @RequestParam(name = "minDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date minDate,
        @RequestParam(name = "maxDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date maxDate,
        @RequestParam(name = "showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final ContentForm contentForm
    ) {
        final ModelAndView mav = new ModelAndView("contents/content_list");

        // Add filters options

        List<Content> contents;

        commonFilters.addCourses(mav, courseId);

        // -- Type

        mav.addObject("contentTypeEnumMap", new HashMap<Content.ContentType, String>()
        {{
            put(Content.ContentType.exam, "Exámen");
            put(Content.ContentType.guide, "Guía");
            put(Content.ContentType.note, "Apunte");
            put(Content.ContentType.resume, "Resúmen");
            put(Content.ContentType.other, "Otro");
        }});

        Content.ContentType selectedType = contentType != null ? Content.ContentType.valueOf(contentType) : null;
        mav.addObject("selectedType", selectedType);

        // Add filtered content

        contents = contentService.findByCourse(courseId, selectedType, minDate, maxDate);
        mav.addObject("contents", contents);

        // Add other parameters
        User loggedUser = userService.getLoggedUser();
        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("user", loggedUser);

        mav.addObject("canDelete", loggedUser.getPermissions().contains(
                new Permission(Permission.Action.DELETE, Permission.Entity.COURSE_CONTENT)
        ));

        return mav;
    }


    @RequestMapping(value = "/contents/create", method = POST)
    public ModelAndView create(
            @Valid @ModelAttribute("createForm") final ContentForm form,
            final BindingResult errors
    ){
        if(errors.hasErrors()){
            return getContents(null, null, null, null, true, form);
        }

         contentService.createContent(
             form.getName(),form.getLink(), form.getCourseId(), form.getDescription(), form.getContentType(),
             form.getContentDate(), userService.getLoggedUser()
         );

        return getContents(form.getCourseId(), null, null, null, false, form);
    }

    @RequestMapping(value = "/contents/{id}", method = DELETE)
    public String delete(
            @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        contentService.delete(id);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @RequestMapping(value = "/contents/{id}/delete", method = POST)
    public String deleteWithPost(
            @PathVariable(value="id") int id, HttpServletRequest request
    ) {
        return delete(id, request);
    }

}