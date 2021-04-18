package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    List<Announcement> findGeneral();

    List<Announcement> findByCourse(String courseId);

    List<Announcement> findByCareer(int careerId);

    Optional<Announcement> findById(int id);

    void markSeen(int id);

}
