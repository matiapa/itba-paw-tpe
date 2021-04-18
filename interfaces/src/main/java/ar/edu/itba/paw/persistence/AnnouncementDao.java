package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {

    List<Announcement> findGeneral();

    List<Announcement> findByCourse(String courseId);

    List<Announcement> findByCareer(int careerId);

    Optional<Announcement> findById(int id);

    void markSeen(int announcementId, int userId);

}
