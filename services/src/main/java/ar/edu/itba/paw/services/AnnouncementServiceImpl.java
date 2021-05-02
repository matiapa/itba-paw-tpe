package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.HolderEntity;
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
    public List<Announcement> findGeneral(int offset, int limit) {
        return announcementDao.findGeneral(offset, limit);
    }

    @Override
    public List<Announcement> findByCourse(String courseId, int offset, int limit) {
        return announcementDao.findByCourse(courseId, offset, limit);
    }

    @Override
    public List<Announcement> findByCareer(String careerCode, int offset, int limit) {
        return announcementDao.findByCareer(careerCode, offset, limit);
    }

    @Override
    public int getSize(HolderEntity holderEntity, String code){
        return announcementDao.getSize(holderEntity, code);
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
    public Announcement create(String title, String summary, String content, String careerCode,
       String courseId, Date expiryDate, User author) {
        return announcementDao.create(title, summary, content, careerCode, courseId, expiryDate,
            author.getId());
    }

}