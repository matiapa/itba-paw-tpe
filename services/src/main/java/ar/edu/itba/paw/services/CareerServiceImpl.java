package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.persistence.CareerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CareerServiceImpl implements CareerService{

    @Autowired
    CareerDao careerDao;

    @Override
    public Optional<Career> findById(int id) {
        return careerDao.findById(id);
    }

    @Override
    public List<Career> findByCourse(Course course) {
        return careerDao.findByCourse(course);
    }

    @Override
    public List<Career> findAll() {
        return careerDao.findAll();
    }
}
