package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ui.NavigationItem;
import ar.edu.itba.paw.models.ui.Panel;
import ar.edu.itba.paw.services.AnnouncementService;
import ar.edu.itba.paw.services.CareerService;
import ar.edu.itba.paw.services.ChatGroupService;
import ar.edu.itba.paw.services.CourseService;
import ar.edu.itba.paw.webapp.mav.BaseMav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class AnnounceController {

    @Autowired AnnouncementService announcementService;

    @RequestMapping("announcements/byId")
    public ModelAndView getCareerDetail(
        @RequestParam(name = "id") int id
    ){
        Optional<Announcement> optionalAnnounce = announcementService.findById(id);
        if (!optionalAnnounce.isPresent()){
            throw new ResponseStatusException(NOT_FOUND, "Anuncio no encontrado");
        }
        Announcement announce = optionalAnnounce.get();

        final ModelAndView mav = new BaseMav(
            ""+announce.getTitle(),
            "announcement/announcement_detail.jsp",
            Arrays.asList(
                new NavigationItem("Home", "/"),
                new NavigationItem(announce.getTitle(), "/announcement/byId?id=" + id)
            )
        );

        mav.addObject("announcement", announce);

        return mav;
    }

}