package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ui.NavigationItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class NavigationServiceImpl implements NavigationService{

    private List<NavigationItem> navigationHistory = new ArrayList<>();

    public List<NavigationItem> getHistory() {
        return navigationHistory;
    }

    public void registerPath(NavigationItem pathItem) {

        int pathItemPos = navigationHistory.indexOf(pathItem);
        if(pathItemPos > -1){
            // If we are going back remove all subsequent path items
            navigationHistory = navigationHistory.subList(0, pathItemPos+1);
        }else{
            // Else just add the item to history
            navigationHistory.add(pathItem);
        }

    }

}