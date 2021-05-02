package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.HolderEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementDao {

    List<Announcement> findGeneral(int offset, int limit);

    List<Announcement> findByCourse(String courseId, int offset, int limit);

    List<Announcement> findByCareer(String careerCode, int offset, int limit);

    int getSize(HolderEntity holderEntity, String code);

    Optional<Announcement> findById(int id);

    void markSeen(int announcementId, int userId);

    Announcement create(String title, String summary, String content, String careerCode,
        String courseId, Date expiryDate, Integer submittedBy);

}
