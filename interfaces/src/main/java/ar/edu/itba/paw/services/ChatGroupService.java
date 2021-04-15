package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;

import java.util.List;

public interface ChatGroupService {

    List<ChatGroup> getChatGroups();

    List<ChatGroup> findByCareer(int careerId);

    List<ChatGroup> findByCareer(int careerId, int limit);

}
