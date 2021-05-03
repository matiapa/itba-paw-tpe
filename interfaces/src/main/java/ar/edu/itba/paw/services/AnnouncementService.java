package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    List<Announcement> findRelevant(User loggedUser, int limit);

    List<Announcement> findGeneral(User loggedUser, boolean showSeen, int offset, int limit);

    List<Announcement> findByCourse(User loggedUser, String courseId, boolean showSeen, int offset, int limit);

    List<Announcement> findByCareer(User loggedUser, String careerCode, boolean showSeen, int offset, int limit);

    Optional<Announcement> findById(int id);
    
    int getSize(EntityTarget target, String code, boolean showSeen, int userId);

    Announcement create(String title, String summary, String content, String careerCode,
       String courseId, Date expiryDate, User author);

    void markSeen(User loggedUser, int announcementId);

    void delete(User loggedUser, int id);

}