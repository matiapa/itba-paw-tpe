package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.StatisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired StatisticsDao statisticsDao;
    @Autowired UserService userService;

    @Override
    public Map<Entity, Integer> newContributions(User loggedUser) {
        return statisticsDao.newContributions(loggedUser);
    }

    @Override
    public Map<Career, Integer> contributionsByCareer() {
        return statisticsDao.contributionsByCareer();
    }

    @Override
    public Map<Date, Integer> contributionsByDate() {
        return statisticsDao.contributionsByDate();
    }

    @Override
    public Map<User, Integer> topUsersByContributions() {
        return statisticsDao.topUsersByContributions();
    }

    @Override
    public Map<Course, Integer> topCoursesByContributions() {
        return statisticsDao.topCoursesByContributions();
    }

}