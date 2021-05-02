package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.Entity;
import ar.edu.itba.paw.models.Permission;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.AnnouncementDao;
import exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired private AnnouncementDao announcementDao;


    @Override
    public List<Announcement> findRelevant(User loggedUser, int limit) {
        return announcementDao.findRelevant(loggedUser.getId(), limit);
    }

    @Override
    public List<Announcement> findGeneral(User loggedUser, boolean showSeen, int offset, int limit) {
        return announcementDao.findGeneral(showSeen, loggedUser.getId(), offset, limit);
    }

    @Override
    public List<Announcement> findByCourse(User loggedUser, String courseId, boolean showSeen, int offset, int limit) {
        return announcementDao.findByCourse(courseId, showSeen, loggedUser.getId(), offset, limit);
    }

    @Override
    public List<Announcement> findByCareer(User loggedUser, String careerCode, boolean showSeen, int offset, int limit) {
        return announcementDao.findByCareer(careerCode, showSeen, loggedUser.getId(), offset, limit);
    }

    @Override
    public int getSize(EntityTarget target, String code, boolean showSeen, int userId){
        return announcementDao.getSize(target, code, showSeen, userId);
    }

    @Override
    public Optional<Announcement> findById(int id) {
        return announcementDao.findById(id);
    }

    @Override
    public Announcement create(String title, String summary, String content, String careerCode,
       String courseId, Date expiryDate, User author) {
        return announcementDao.create(title, summary, content, careerCode, courseId, expiryDate,
            author.getId());
    }

    @Override
    public void markSeen(User loggedUser, int announcementId) {
        announcementDao.markSeen(announcementId, loggedUser.getId());
    }

    @Override
    public void delete(User loggedUser, int id) {
        Permission reqPerm = new Permission(Permission.Action.DELETE, Entity.ANNOUNCEMENT);
        if(! loggedUser.getPermissions().contains(reqPerm))
            throw new UnauthorizedException();

        announcementDao.delete(id);
    }

}