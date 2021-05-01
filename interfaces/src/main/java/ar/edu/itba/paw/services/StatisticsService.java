package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.Map;

public interface StatisticsService {

    Map<Entity, Integer> newContributions();

    Map<Career, Integer> contributionsByCareer();

    Map<Date, Integer> contributionsByDate();

    Map<User, Integer> topUsersByContributions();

    Map<Course, Integer> topCoursesByContributions();

}