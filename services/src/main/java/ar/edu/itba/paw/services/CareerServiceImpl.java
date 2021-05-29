package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.persistence.CareerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CareerServiceImpl implements CareerService{

    @Autowired
    private CareerDao careerDao;

    @Override
    public Optional<Career> findByCode(String code) {
        return careerDao.findByCode(code);
    }

    @Override
    public List<Career> findAll() {
        return careerDao.findAll();
    }

}