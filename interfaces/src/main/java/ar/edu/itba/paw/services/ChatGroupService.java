package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;

public interface ChatGroupService {

    ChatGroup addGroup(String groupName, String careerCode, String link, int createdBy, Date creationDate, ChatPlatform platform);

    int getSize(String careerCode, ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter);

    List<ChatGroup> findByCareer(String careerCode, int offset, int limit);

    List<ChatGroup> findByCareer(String careerCode, ChatPlatform platform, Integer year, Integer quarter, int offset, int limit);

    void delete(int id);

}
