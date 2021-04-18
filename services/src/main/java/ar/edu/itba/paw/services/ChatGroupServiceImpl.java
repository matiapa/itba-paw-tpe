package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatGroupServiceImpl implements ChatGroupService{

    @Autowired
    private ChatGroupDao chatGruopDao;

    @Override
    public boolean addGroup(String groupName, String careerId, String link) {
        //TODO implementar agregado de grupo
        return false;
    }

    @Override
    public List<ChatGroup> getChatGroups() {
        return chatGruopDao.getChats();
    }

    @Override
    public List<ChatGroup> findByCareer(int careerId) {
        return chatGruopDao.findByCareer(careerId);
    }

    @Override
    public List<ChatGroup> findByCareer(int careerId, int limit) {
        return chatGruopDao.findByCareer(careerId, limit);
    }

}
