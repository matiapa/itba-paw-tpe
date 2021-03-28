package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Announcement;

import java.util.List;

public interface AnnouncementDao {

    List<Announcement> findGeneral();

    List<Announcement> findByCourse(String courseId);

}
