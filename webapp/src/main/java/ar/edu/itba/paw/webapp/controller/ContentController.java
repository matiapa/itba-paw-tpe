package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.webapp.form.ContentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class ContentController {

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @RequestMapping("contents")
    public ModelAndView getContents(
        @RequestParam(name="courseId", required = false) String courseId,
        @RequestParam(name="contentType", required = false) String contentType,
        @RequestParam(name="minDate",required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date minDate,
        @RequestParam(name="maxDate",required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date maxDate,
        @AuthenticationPrincipal User user
    ) {
        final ModelAndView mav = new ModelAndView("contents/content_list");

        List<Course> courses = courseService.findAll();
        mav.addObject("courses", courses);

        List<Content> contents = new ArrayList<>();

        if (courseId != null) {
            contentType = contentType.equals("") ? null : contentType;
            contents = contentService.findContent(courseId, contentType, minDate, maxDate);

            Course selectedCourse = courses.stream().filter(c -> c.getId().equals(courseId)).findFirst()
                    .orElseThrow(RuntimeException::new);
            mav.addObject("selectedCourse", selectedCourse);
        }

        mav.addObject("contentTypeEnumMap", new HashMap<Content.ContentType, String>()
        {{
            put(Content.ContentType.exam, "Exámen");
            put(Content.ContentType.guide, "Guía");
            put(Content.ContentType.note, "Apunte");
            put(Content.ContentType.resume, "Resúmen");
            put(Content.ContentType.other, "Otro");
        }});

        mav.addObject("contents", contents);


        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping("contents/create")
    public ModelAndView create(
            @ModelAttribute("createForm") final ContentForm form,
            @AuthenticationPrincipal User user
    ) {
        ModelAndView mav = new ModelAndView("contents/create_content");

        mav.addObject("courses", courseService.findAll());
        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping(value = "contents/create", method = POST)
    public ModelAndView create(
            @Valid @ModelAttribute("createForm") final ContentForm form,
            final BindingResult errors,
            @AuthenticationPrincipal User user
    ){

//        if (errors.hasErrors())
//            throw new RuntimeException("Tiene errores");

         contentService.createContent(form.getName(),form.getLink(), form.getCourseId(), form.getDescription(), form.getContentType(),form.getContentDate(), user);



        return new ModelAndView("redirect:/contents");
    }



}