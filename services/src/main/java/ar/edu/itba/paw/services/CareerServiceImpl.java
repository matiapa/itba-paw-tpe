package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.CareerCourse;
import ar.edu.itba.paw.persistence.CareerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CareerServiceImpl implements CareerService{

    @Autowired
    private CareerDao careerDao;

    @Override
    public Optional<Career> findById(int id) {
        return careerDao.findById(id);
    }

    @Override
    public Map<Integer, List<CareerCourse>> findByCareer(int careerId) {
        return careerDao.findByCareer(careerId);
    }


    @Override
    public List<Career> findAll() {
        return careerDao.findAll();
    }
}
