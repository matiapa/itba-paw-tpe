package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserWorkRate;

import java.util.List;

public interface UserWorkRateService {

    List<UserWorkRate> getRates(User ofUser, int page, int pageSize);

    UserWorkRate rate(User rater, User rated, Course course, int behaviour, int skills, String comment);

}
