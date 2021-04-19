package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.services.ContentService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.services.UserService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class ContentController {

    @Autowired private ContentService contentService;

    @Autowired private CourseService courseService;

    @Autowired private UserService userService;

    @RequestMapping("contents")
    public ModelAndView getContents(
        @RequestParam(name="courseId", required = false) String courseId,
        @RequestParam(name="contentType", required = false) String contentType,
        @RequestParam(name="minDate",required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date minDate,
        @RequestParam(name="maxDate",required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date maxDate
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

        mav.addObject("contents", contents);


        mav.addObject("user", userService.getUser());

        return mav;
    }

    @RequestMapping("contents/create")
    public ModelAndView create(
            @ModelAttribute("createForm") final ContentForm form
    ) {
        ModelAndView mav = new ModelAndView("contents/create_content");

        mav.addObject("courses", courseService.findAll());
        mav.addObject("user", userService.getUser());

        return mav;
    }

    @RequestMapping(value = "contents/create", method = POST)
    public ModelAndView create(
            @Valid @ModelAttribute("createForm") final ContentForm form,
            final BindingResult errors
    ){
        System.out.println(form.getContentType());
        System.out.println(form.getContentDate());
        System.out.println(form.getCourseId());
        System.out.println(form.getDescription());
        System.out.println(form.getLink());
        System.out.println(form.getName());
//        if (errors.hasErrors())
//            throw new RuntimeException("Tieme errores");

         contentService.createContent(form.getName(),form.getLink(), form.getCourseId(), form.getDescription(), form.getContentType(),form.getContentDate(), userService.getUser());



        return new ModelAndView("redirect:/contents");
    }



}