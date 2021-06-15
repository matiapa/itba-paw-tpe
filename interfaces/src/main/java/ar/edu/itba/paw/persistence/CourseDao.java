package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseDao {

    List<Course> findAll();

    List<Course> findFavourites(User ofUser);

    List<CareerCourse> findByCareer(Career career);

    Optional<Course> findById(String id);

    void markFavorite(Course course, User ofUser, boolean favorite);

    CourseReview createCourseReview(Course course, String review, User uploader);

    List<CourseReview> getReviews(Course course, Integer page, Integer pageSize);

    int getReviewsSize(Course course);
}
