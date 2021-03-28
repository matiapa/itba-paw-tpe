package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.AnnouncementDao;
import ar.edu.itba.paw.persistence.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseDao courseDao;

    @Override
    public List<Course> findFavourites(int userId) {
        return courseDao.findFavourites(userId);
    }

    @Override
    public Optional<Course> findById(String id) {
        return courseDao.findById(id);
    }

}
