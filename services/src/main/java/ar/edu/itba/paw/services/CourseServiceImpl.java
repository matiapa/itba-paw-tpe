package ar.edu.itba.paw.services;

import java.util.List;
import java.util.Optional;

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
        return courseDao.findFavourites(user.getId());
    }

    @Override
    public List<Course> findFavourites(User user, int limit) {
        return courseDao.findFavourites(user.getId(), limit);
    }

    @Override
    public List<Course> findByCareer(String careerCode) {
        return courseDao.findByCareer(careerCode);
    }

    @Override
    public List<Course> findByCareer(String careerCode, int limit) {
        return courseDao.findByCareer(careerCode, limit);
    }

    @Override
    public Optional<Course> findById(String id) {
        return courseDao.findById(id);
    }

}