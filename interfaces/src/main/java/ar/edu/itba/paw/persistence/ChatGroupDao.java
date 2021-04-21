package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;

import java.util.Date;
import java.util.List;

public interface ChatGroupDao {

    ChatGroup addGroup(String groupName, Integer careerId, String link, Integer createdBy, Date creationDate, ChatPlatform platform);

    List<ChatGroup> getChats();

    List<ChatGroup> findByCareer(int careerId);

    List<ChatGroup> findByCareer(int careerId, ChatPlatform platform, Integer year, Integer quarter);

    List<ChatGroup> findByCareer(int careerId, int limit);

}
