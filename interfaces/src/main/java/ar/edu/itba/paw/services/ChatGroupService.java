package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;

public interface ChatGroupService {

    ChatGroup addGroup(String groupName, String careerId, String link, Integer user, Date date);

    List<ChatGroup> getChatGroups();

    List<ChatGroup> findByCareer(int careerId);

    List<ChatGroup> findByCareer(int careerId, int limit);

}
