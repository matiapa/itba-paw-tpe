package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface ChatGroupService {

    boolean addGroup(String groupName, String careerId, String link, User user);

    List<ChatGroup> getChatGroups();

    List<ChatGroup> findByCareer(int careerId);

    List<ChatGroup> findByCareer(int careerId, int limit);

}
