package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.persistence.AnnouncementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    AnnouncementDao announcementDao;

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

}
