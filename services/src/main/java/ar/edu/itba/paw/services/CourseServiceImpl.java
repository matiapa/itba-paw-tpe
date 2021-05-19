package ar.edu.itba.paw.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return courseDao.findFavourites(user.getId());
    }

    @Override
    public List<Course> findByCareer(String careerCode, int limit) {
        return courseDao.findByCareer(careerCode, limit);
    }

    @Override
    public Optional<Course> findById(String id) {
        return courseDao.findById(id);
    }

    @Override
    public Map<Integer, List<CareerCourse>> findByCareerSemester(String careerCode) {
        return courseDao.findByCareerSemester(careerCode);
    }

    @Override
    public void addFavourite(int id, String course) {
        courseDao.addFavourite(id, course);
    }

    @Override
    public void removeFavourite(int id, String course){
        courseDao.removeFavourite(id, course);
    }

    @Override
    public boolean isFaved(String courseId, Integer userId) {
        return courseDao.isFaved(courseId, userId);
    }

}