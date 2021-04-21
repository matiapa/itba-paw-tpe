package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.AnnouncementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementDao announcementDao;

    @Override
    public List<Announcement> findGeneral() {
        return announcementDao.findGeneral();
    }

    @Override
    public List<Announcement> findByCourse(String courseId) {
        return announcementDao.findByCourse(courseId);
    }

    @Override
    public List<Announcement> findByCareer(int careerId) {
        return announcementDao.findByCareer(careerId);
    }

    @Override
    public Optional<Announcement> findById(int id) {
        return announcementDao.findById(id);
    }

    @Override
    public void markSeen(int announcementId, User user) {
        announcementDao.markSeen(announcementId, user.getId());
    }

    @Override
    public Announcement create(String title, String summary, String content, Integer careerId,
       String courseId, Date expiryDate, User author) {
        return announcementDao.create(title, summary, content, careerId, courseId, expiryDate,
            author.getId());
    }

}