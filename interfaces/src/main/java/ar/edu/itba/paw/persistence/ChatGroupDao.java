package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChatGroupDao {

    ChatGroup addGroup(String groupName, int careerId, String link, int createdBy, Date creationDate, ChatPlatform platform);

    List<ChatGroup> findByCareer(int careerId);

    List<ChatGroup> findByCareer(int careerId, int limit);

    List<ChatGroup> findByCareer(int careerId, ChatPlatform platform, Integer year, Integer quarter);

    Optional<ChatGroup> findById(String id);

}