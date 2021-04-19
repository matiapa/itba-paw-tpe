package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatGroupServiceImpl implements ChatGroupService{

    @Autowired
    private ChatGroupDao chatGroupDao;

    @Override
    public ChatGroup addGroup(String groupName, String careerId, String link, User user, Date date) {
        return chatGroupDao.addGroup(groupName, careerId, link, user, date);
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
    public List<ChatGroup> findByCareer(int careerId, int limit) {
        return chatGroupDao.findByCareer(careerId, limit);
    }

}
