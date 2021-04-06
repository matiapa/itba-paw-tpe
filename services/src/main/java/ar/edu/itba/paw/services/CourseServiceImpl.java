package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LoginRequiredException;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    UserService userService;


    @Override
    public List<Course> findFavourites() throws LoginRequiredException {
        return courseDao.findFavourites(userService.getLoggedID());
    }

    @Override
    public List<Course> findFavourites(int limit) throws LoginRequiredException {
        return courseDao.findFavourites(userService.getLoggedID(), limit);
    }

    @Override
    public List<Course> findByCareer(int careerId) {
        return courseDao.findByCareer(careerId);
    }

    @Override
    public List<Course> findByCareer(int careerId, int limit) {
        return courseDao.findByCareer(careerId, limit);
    }

    @Override
    public Optional<Course> findById(String id) {
        return courseDao.findById(id);
    }

}