package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Announcement;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {

    List<Announcement> findGeneral(boolean showSeen, int userId);

    List<Announcement> findByCourse(String courseId, boolean showSeen, int userId);

    List<Announcement> findByCareer(String careerCode, boolean showSeen, int userId);

    Optional<Announcement> findById(int id);

    void markSeen(int announcementId, int userId);

    Announcement create(String title, String summary, String content, String careerCode,
        String courseId, Date expiryDate, Integer submittedBy);

    void delete(int id);

}
