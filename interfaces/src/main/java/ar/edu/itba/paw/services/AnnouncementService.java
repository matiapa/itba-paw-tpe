package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Announcement;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    List<Announcement> findGeneral();

    List<Announcement> findByCourse(String courseId);

    List<Announcement> findByCareer(int careerId);

    Optional<Announcement> findById(int id);

    void markSeen(int id);

    Announcement create(String title, String summary, String content, Integer careerId,
        String courseId, Date expiryDate);

}
