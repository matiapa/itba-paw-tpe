package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatGroupServiceImpl implements ChatGroupService{

    @Autowired
    private ChatGroupDao chatGroupDao;

    @Override
    public ChatGroup addGroup(String groupName, Integer careerId, String link, Integer createdBy, Date creationDate, ChatPlatform platform) {
        return chatGroupDao.addGroup(groupName, careerId, link, createdBy, creationDate, platform);
    }

    @Override
    public List<ChatGroup> getChatGroups() {
        return chatGroupDao.getChats();
    }

    @Override
    public List<ChatGroup> findByCareer(int careerId) {
        return chatGroupDao.findByCareer(careerId);
    }

    @Override
    public List<ChatGroup> findByCareer(int careerId, ChatGroup.ChatPlatform platform, Integer year, Integer quarter) {
        return chatGroupDao.findByCareer(careerId, platform, year, quarter);
    }

    @Override
    public List<ChatGroup> findByCareer(int careerId, int limit) {
        return chatGroupDao.findByCareer(careerId, limit);
    }

}
