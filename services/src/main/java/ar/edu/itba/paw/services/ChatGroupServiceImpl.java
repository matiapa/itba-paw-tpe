package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatGroupServiceImpl implements ChatGroupService{

    @Autowired
    ChatGroupDao chatGruopDao;

    @Override
    public List<ChatGroup> findByCareer(int careerId) {
        return chatGruopDao.findByCareer(careerId);
    }

    @Override
    public Optional<ChatGroup> findById(String id) {
        return chatGruopDao.findById(id);
    }

}
