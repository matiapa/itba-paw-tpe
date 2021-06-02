package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserWorkRate;
import ar.edu.itba.paw.persistence.UserWorkRateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWorkRateServiceImpl implements UserWorkRateService {

    @Autowired private UserWorkRateDao rateDao;

    @Override
    public List<UserWorkRate> getRates(User ofUser, int page, int pageSize) {
        return rateDao.getRates(ofUser, page, pageSize);
    }

    @Override
    public UserWorkRate rate(User rater, User rated, Course course, int behaviour, int skills, String comment) {
        if(rater.equals(rated) || behaviour<0 || behaviour>5 || skills<0 || skills>5)
            return null;
        return rateDao.rate(rater, rated, course, behaviour, skills, comment);
    }

}
