package ar.edu.itba.paw.webapp.controller.common;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FiltersController {

    @Autowired private CareerService careerService;
    @Autowired private CourseService courseService;

    public void addCareers(ModelAndView mav, String selectedCareerCode){
        List<Career> careers = careerService.findAll();
        mav.addObject("careers", careers);

        Career selectedCareer = selectedCareerCode != null ?
            careers.stream().filter(c -> c.getCode().equals(selectedCareerCode)).findFirst()
                .orElseThrow(RuntimeException::new)
            : null;
        mav.addObject("selectedCareer", selectedCareer);
    }

    public void addCourses(ModelAndView mav, String selectedCourseId){
        List<Course> courses = courseService.findAll();
        mav.addObject("courses", courses);

        Course selectedCourse = selectedCourseId != null ?
            courses.stream().filter(c -> c.getId().equals(selectedCourseId)).findFirst()
                .orElseThrow(RuntimeException::new)
            : null;
        mav.addObject("selectedCourse", selectedCourse);
    }

}