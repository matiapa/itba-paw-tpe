package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    List<Announcement> findGeneral();

    List<Announcement> findByCourse(String courseId);

    List<Announcement> findByCareer(String careerCode);

    Optional<Announcement> findById(int id);

    void markSeen(int announcementId, User user);

    Announcement create(String title, String summary, String content, String careerCode,
       String courseId, Date expiryDate, User author);

}
