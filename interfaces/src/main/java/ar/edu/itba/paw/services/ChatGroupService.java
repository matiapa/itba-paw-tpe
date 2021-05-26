package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;

public interface ChatGroupService {

    ChatGroup addGroup(String groupName, String careerCode, String link, User createdBy, Date creationDate, ChatGroup.ChatPlatform platform);

    List<ChatGroup> findByCareer(String careerCode, ChatGroup.ChatPlatform platform, Integer year, Integer quarter, Integer offset, Integer limit);

    int getSize(String careerCode, ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter);

    void delete(int id);

}
