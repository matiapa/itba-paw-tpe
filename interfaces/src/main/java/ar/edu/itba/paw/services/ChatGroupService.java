package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;

public interface ChatGroupService {

    ChatGroup addGroup(String groupName, Integer careerId, String link, Integer createdBy, Date creationDate, ChatPlatform platform);

    List<ChatGroup> getChatGroups();

    List<ChatGroup> findByCareer(int careerId);

    List<ChatGroup> findByCareer(int careerId, ChatPlatform platform, Integer year, Integer quarter);

    List<ChatGroup> findByCareer(int careerId, int limit);

}
