package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.AnnouncementDao;
import ar.edu.itba.paw.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired private AnnouncementDao announcementDao;


    @Override
    public List<Announcement> findRelevant(User forUser, int page, int pageSize) {
        return announcementDao.findRelevant(forUser, page, pageSize);
    }

    @Override
    public List<Announcement> findGeneral(User hideSeenBy, int page, int pageSize) {
        return announcementDao.findGeneral(hideSeenBy, page, pageSize);
    }

    @Override
    public List<Announcement> findByCourse(Course course, User hideSeenBy, int page, int pageSize) {
        return announcementDao.findByCourse(course, hideSeenBy, page, pageSize);
    }

    @Override
    public List<Announcement> findByCareer(Career career, User hideSeenBy, int page, int pageSize) {
        return announcementDao.findByCareer(career, hideSeenBy, page, pageSize);
    }

    @Override
    public int getSize(EntityTarget target, String targetCode, User hideSeenBy){
        return announcementDao.getSize(target, targetCode, hideSeenBy);
    }

    @Override
    public Optional<Announcement> findById(int id) {
        return announcementDao.findById(id);
    }

    @Override
    public Announcement create(String title, String summary, String content, Career career,
       Course course, Date expiryDate, User uploader) {
        return announcementDao.create(title, summary, content, career, course, expiryDate, uploader);
    }

    @Override
    public void markSeen(Announcement announcement, User seenBy) {
        announcementDao.markSeen(announcement, seenBy);
    }

    @Override
    public void markAllSeen(User seenBy) {
        announcementDao.markAllSeen(seenBy);
    }

    @Override
    public void delete(Announcement announcement, User loggedUser) {
        Permission reqPerm = new Permission(Permission.Action.delete, Entity.announcement);
        if(! loggedUser.getPermissions().contains(reqPerm))
            throw new UnauthorizedException();

        announcementDao.delete(announcement);
    }

}