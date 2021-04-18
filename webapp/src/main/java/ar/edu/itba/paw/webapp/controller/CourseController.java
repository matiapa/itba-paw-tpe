package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;



@Controller
public class CourseController {

    @Autowired private CareerService careerService;

    @Autowired private UserService userService;


    @RequestMapping("courses")
    public ModelAndView getCourses(
            @RequestParam(name="careerId", required = false) Integer careerId
    ){
        final ModelAndView mav = new ModelAndView("courses/courses_list");



        Map<Integer,List<CareerCourse>> courses;
        List<Career> careers = careerService.findAll();
        mav.addObject("careers", careers);
        if(careerId != null) {
            courses = careerService.findByCareer(careerId);
            mav.addObject("careerCourses",courses);
            Career selectedCareer = careers.stream().filter(c -> c.getId() == careerId).findFirst()
                    .orElseThrow(RuntimeException::new);
            mav.addObject("selectedCareer", selectedCareer);
        }

        mav.addObject("user", userService.getUser());

        return mav;
    }

}