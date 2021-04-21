package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;

import java.util.Date;
import java.util.List;

public interface ChatGroupDao {

    ChatGroup addGroup(String groupName, String careerId, String link, Integer user, Date date);

    List<ChatGroup> getChats();

    List<ChatGroup> findByCareer(int careerId);

    List<ChatGroup> findByCareer(int careerId, ChatPlatform platform, Integer year, Integer quarter);

    List<ChatGroup> findByCareer(int careerId, int limit);

}
