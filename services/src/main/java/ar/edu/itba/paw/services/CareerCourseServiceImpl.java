package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CareerCourseDao;
import ar.edu.itba.paw.persistence.CareerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class CareerCourseServiceImpl implements CareerCourseService{
    @Autowired
    private CareerCourseDao careerCourseDao;

    @Override
    public Map<Integer, List<CareerCourse>> findByCareer(int careerId) {
        return careerCourseDao.findByCareer(careerId);
    }
}
