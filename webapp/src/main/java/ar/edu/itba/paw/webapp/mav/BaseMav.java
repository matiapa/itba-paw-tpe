package ar.edu.itba.paw.webapp.mav;

import ar.edu.itba.paw.models.ui.NavigationItem;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class BaseMav extends ModelAndView {

    public BaseMav(String title, String contentViewName, List<NavigationItem> hirearchy){
        super("main");

        this.addObject("title", title);

        this.addObject("navigationHistory", hirearchy);

        this.addObject("contentViewName", contentViewName);
    }

}
