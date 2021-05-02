package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    List<Announcement> findRelevant();

    List<Announcement> findGeneral(boolean showSeen);

    List<Announcement> findByCourse(String courseId, boolean showSeen);

    List<Announcement> findByCareer(String careerCode, boolean showSeen);

    Optional<Announcement> findById(int id);

    Announcement create(String title, String summary, String content, String careerCode,
       String courseId, Date expiryDate, User author);

    void markSeen(int announcementId);

    void delete(int id);

}