package ar.edu.itba.paw.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.CourseDao;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public List<Course> findFavourites(User user) {
        return courseDao.findFavourites(user);
    }

    @Override
    public List<CareerCourse> findByCareer(Career career) {
        return courseDao.findByCareer(career);
    }

    @Override
    public Optional<Course> findById(String id) {
        return courseDao.findById(id);
    }

}