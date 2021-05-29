package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChatGroupDao {

    List<ChatGroup> findByCareer(Career career, ChatPlatform platform, Integer year, Integer quarter,
                                 Integer page, Integer pageSize);

    Optional<ChatGroup> findById(int id);

    int getSize(Career career, ChatPlatform platform, Integer year, Integer quarter);

    ChatGroup addGroup(String groupName, Career career, String link, User createdBy, Date creationDate, ChatPlatform platform);

    void delete(ChatGroup chatGroup);

}