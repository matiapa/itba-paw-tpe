package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ui.NavigationItem;
import java.util.List;

public interface NavigationService {

    List<NavigationItem> getHistory();

    void registerPath(NavigationItem pathItem);

}
