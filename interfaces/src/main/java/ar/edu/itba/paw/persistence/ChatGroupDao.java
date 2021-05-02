package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ChatGroupDao {

    ChatGroup addGroup(String groupName, String careerCode, String link, int createdBy, Date creationDate, ChatPlatform platform);

    int getSize(String careerCode, ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter);

    List<ChatGroup> findByCareer(String careerCode, int offset, int limit);

    List<ChatGroup> findByCareer(String careerCode, ChatPlatform platform, Integer year, Integer quarter, int offset, int limit);

    Optional<ChatGroup> findById(String id);

    void delete(int id);

}