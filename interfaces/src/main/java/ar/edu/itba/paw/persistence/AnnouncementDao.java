package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.HolderEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {

    List<Announcement> findRelevant(int userId, int limit);

    List<Announcement> findGeneral(boolean showSeen, int userId, int offset, int limit);

    List<Announcement> findByCourse(String courseId, boolean showSeen, int userId, int offset, int limit);

    List<Announcement> findByCareer(String careerCode, boolean showSeen, int userId, int offset, int limit);

    Optional<Announcement> findById(int id);

    int getSize(HolderEntity holderEntity, String code);

    Announcement create(String title, String summary, String content, String careerCode,
        String courseId, Date expiryDate, Integer submittedBy);

    void markSeen(int announcementId, int userId);

    void delete(int id);

}
