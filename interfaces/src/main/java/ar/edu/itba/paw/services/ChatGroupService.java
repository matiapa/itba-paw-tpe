package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;

import java.util.Date;
import java.util.List;

public interface ChatGroupService {

    ChatGroup addGroup(String groupName, String careerCode, String link, int createdBy, Date creationDate, ChatPlatform platform);

    List<ChatGroup> findByCareer(String careerCode, ChatPlatform platform, Integer year, Integer quarter, int offset, int limit);

    int getSize(String careerCode, ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter);

    void delete(int id);

}
