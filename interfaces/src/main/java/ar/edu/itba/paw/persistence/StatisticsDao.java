package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.Map;

public interface StatisticsDao {

    Map<Entity, Integer> newContributions(User loggedUser);

    Map<Career, Integer> contributionsByCareer();

    Map<Date, Integer> contributionsByDate();

    Map<User, Integer> topUsersByContributions();

    Map<Course, Integer> topCoursesByContributions();

}