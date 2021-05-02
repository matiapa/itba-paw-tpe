package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Announcement;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {

    List<Announcement> findRelevant(int userId);

    List<Announcement> findGeneral(boolean showSeen, int userId);

    List<Announcement> findByCourse(String courseId, boolean showSeen, int userId);

    List<Announcement> findByCareer(String careerCode, boolean showSeen, int userId);

    Optional<Announcement> findById(int id);

    Announcement create(String title, String summary, String content, String careerCode,
        String courseId, Date expiryDate, Integer submittedBy);

    void markSeen(int announcementId, int userId);

    void delete(int id);

}
