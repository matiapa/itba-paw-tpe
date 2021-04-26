package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserService;
import ar.edu.itba.paw.webapp.form.ChatGroupForm;
import ar.edu.itba.paw.webapp.form.ContentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class ContentController {

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;


    @RequestMapping("/contents")
    public ModelAndView getContents(
        @RequestParam(name = "courseId", required = false) String courseId,
        @RequestParam(name = "contentType", required = false) String contentType,
        @RequestParam(name = "minDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date minDate,
        @RequestParam(name = "maxDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date maxDate,
        @RequestParam(name = "showCreateForm", required = false, defaultValue="false") Boolean showCreateForm,
        @ModelAttribute("createForm") final ContentForm contentForm
    ) {
        final ModelAndView mav = new ModelAndView("contents/content_list");

        // Filters

        List<Content> contents;

            // -- Course

        List<Course> courses = courseService.findAll();
        mav.addObject("courses", courses);

        Course selectedCourse = courseId != null ? courses.stream().filter(c -> c.getId().equals(courseId)).findFirst()
                .orElseThrow(RuntimeException::new) : null;
        mav.addObject("selectedCourse", selectedCourse);

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

        contents = contentService.findByCourse(courseId, selectedType, minDate, maxDate);

        mav.addObject("contents", contents);
        mav.addObject("showCreateForm", showCreateForm);
        mav.addObject("user", userService.getLoggedUser());

        return mav;
    }


    @RequestMapping(value = "/contents", method = POST)
    public ModelAndView create(
            @Valid @ModelAttribute("createForm") final ContentForm form,
            final BindingResult errors
    ){
        if(errors.hasErrors()){
            System.out.println("ERRORS");
            return getContents(null, null, null, null, true, form);
        }

        System.out.println("NO ERRORS");

         contentService.createContent(
             form.getName(),form.getLink(), form.getCourseId(), form.getDescription(), form.getContentType(),
             form.getContentDate(), userService.getLoggedUser()
         );

        return getContents(form.getCourseId(), null, null, null, false, form);
    }

}