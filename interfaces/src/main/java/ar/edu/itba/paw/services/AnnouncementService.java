package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> findAll();

}
