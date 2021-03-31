package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> findGeneral();

    List<Announcement> findByCourse(String courseId);

    List<Announcement> findByCareer(int careerId);

}
