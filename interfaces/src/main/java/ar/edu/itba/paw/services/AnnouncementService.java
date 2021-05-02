package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    List<Announcement> findRelevant(int limit);

    List<Announcement> findGeneral(boolean showSeen, int offset, int limit);

    List<Announcement> findByCourse(String courseId, boolean showSeen, int offset, int limit);

    List<Announcement> findByCareer(String careerCode, boolean showSeen, int offset, int limit);

    Optional<Announcement> findById(int id);

    int getSize(HolderEntity holderEntity, String code);

    Announcement create(String title, String summary, String content, String careerCode,
       String courseId, Date expiryDate, User author);

    void markSeen(int announcementId);

    void delete(int id);

}