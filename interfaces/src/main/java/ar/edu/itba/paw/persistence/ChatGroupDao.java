package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChatGroupDao {

    ChatGroup addGroup(String groupName, String careerCode, String link, User createdBy, Date creationDate, ChatGroup.ChatPlatform platform);

    int getSize(String careerCode, ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter);

    List<ChatGroup> findByCareer(String careerCode, ChatGroup.ChatPlatform platform, Integer year, Integer quarter, Integer offset, Integer limit);

    Optional<ChatGroup> findById(String id);

    void delete(int id);

}