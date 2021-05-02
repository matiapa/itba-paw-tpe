package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.HolderEntity;
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

    @Autowired private UserService userService;

    @Override
    public List<Announcement> findRelevant(int limit) {
        return announcementDao.findRelevant(userService.getLoggedUser().getId(), limit);
    }

    @Override
    public List<Announcement> findGeneral(boolean showSeen, int offset, int limit) {
        return announcementDao.findGeneral(showSeen, userService.getLoggedUser().getId(), limit, offset);
    }

    @Override
    public List<Announcement> findByCourse(String courseId, boolean showSeen, int offset, int limit) {
        return announcementDao.findByCourse(courseId, showSeen, userService.getLoggedUser().getId(), offset, limit);
    }

    @Override
    public List<Announcement> findByCareer(String careerCode, boolean showSeen, int offset, int limit) {
        return announcementDao.findByCareer(careerCode, showSeen, userService.getLoggedUser().getId(), offset, limit);
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
    public Announcement create(String title, String summary, String content, String careerCode,
       String courseId, Date expiryDate, User author) {
        return announcementDao.create(title, summary, content, careerCode, courseId, expiryDate,
            author.getId());
    }

    @Override
    public void markSeen(int announcementId) {
        announcementDao.markSeen(announcementId, userService.getLoggedUser().getId());
    }

    @Override
    public void delete(int id) {
        Permission reqPerm = new Permission(Permission.Action.DELETE, Entity.ANNOUNCEMENT);
        if(! userService.getLoggedUser().getPermissions().contains(reqPerm))
            throw new UnauthorizedException();

        announcementDao.delete(id);
    }

}