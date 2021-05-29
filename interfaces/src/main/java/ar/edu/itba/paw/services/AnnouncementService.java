package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    List<Announcement> findRelevant(User forUser, int page, int pageSize);

    List<Announcement> findGeneral(User hideSeenBy, int page, int pageSize);

    List<Announcement> findByCourse(Course course, User hideSeenBy, int page, int pageSize);

    List<Announcement> findByCareer(Career career, User hideSeenBy, int page, int pageSize);

    Optional<Announcement> findById(int id);
    
    int getSize(EntityTarget target, String targetCode, User hideSeenBy);

    Announcement create(String title, String summary, String content, Career career,
        Course course, Date expiryDate, User uploader);

    void markSeen(Announcement announcement, User seenBy);

    void markAllSeen(User seenBy);

    void delete(Announcement announcement, User loggedUser);

}