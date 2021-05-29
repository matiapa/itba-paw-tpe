package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChatGroupServiceImpl implements ChatGroupService{

    @Autowired
    private ChatGroupDao chatGroupDao;


    @Override
    public ChatGroup addGroup(String groupName, Career career, String link, User createdBy, Date creationDate, ChatPlatform platform) {
        return chatGroupDao.addGroup(groupName, career, link, createdBy, creationDate, platform);
    }

    @Override
    public List<ChatGroup> findByCareer(Career career, ChatPlatform platform, Integer year, Integer quarter, Integer offset, Integer limit) {
        return chatGroupDao.findByCareer(career, platform, year, quarter, offset, limit);
    }

    @Override
    public Optional<ChatGroup> findById(int id) {
        return chatGroupDao.findById(id);
    }

    @Override
    public int getSize(Career career, ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter) {
        return chatGroupDao.getSize(career, selectedPlatform, selectedYear, selectedQuarter);
    }

    @Override
    public void delete(ChatGroup chatGroup) {
            chatGroupDao.delete(chatGroup);
    }

}
