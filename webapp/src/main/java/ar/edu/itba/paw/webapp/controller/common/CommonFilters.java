package ar.edu.itba.paw.webapp.controller.common;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommonFilters {

    /* The reason this class was not designed to by a ControllerAdvice with ModelAttribute methods is because theses
    methods require parameters, and beause the retrieval of all careers/courses is expensive and only needed in
    some specific controller methods, not in a whole controller class or classes. */

    @Autowired private CareerService careerService;
    @Autowired private CourseService courseService;

    public Career addCareers(ModelAndView mav, String selectedCareerCode){
        List<Career> careers = careerService.findAll();
        mav.addObject("careers", careers);

        Career selectedCareer = selectedCareerCode != null ?
            careers.stream().filter(c -> c.getCode().equals(selectedCareerCode)).findFirst()
                .orElseThrow(RuntimeException::new)
            : null;
        mav.addObject("selectedCareer", selectedCareer);

        return selectedCareer;
    }

    public Course addCourses(ModelAndView mav, String selectedCourseId){
        List<Course> courses = courseService.findAll();
        mav.addObject("courses", courses);

        Course selectedCourse = selectedCourseId != null ?
            courses.stream().filter(c -> c.getId().equals(selectedCourseId)).findFirst()
                .orElseThrow(RuntimeException::new)
            : null;
        mav.addObject("selectedCourse", selectedCourse);

        return selectedCourse;
    }

    public EntityTarget getTarget(String careerCode, String courseId){
        if(careerCode == null && courseId == null)
            return EntityTarget.general;
        else if(careerCode == null && courseId != null)
            return EntityTarget.course;
        else if(courseId == null)
            return EntityTarget.career;

        return EntityTarget.general;
    }

}