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
    public ChatGroup addGroup(String groupName, String careerCode, String link, int createdBy, Date creationDate, ChatPlatform platform) {
        return chatGroupDao.addGroup(groupName, careerCode, link, createdBy, creationDate, platform);
    }

    @Override
    public int getSize(String careerCode, ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter) {
        return chatGroupDao.getSize(careerCode, selectedPlatform, selectedYear, selectedQuarter);
    }

    @Override
    public List<ChatGroup> findByCareer(String careerCode) {
        return chatGroupDao.findByCareer(careerCode);
    }

    @Override
    public List<ChatGroup> findByCareer(String careerCode, int limit) {
        return chatGroupDao.findByCareer(careerCode, limit);
    }

    @Override
    public List<ChatGroup> findByCareer(String careerCode, ChatGroup.ChatPlatform platform, Integer year, Integer quarter) {
        return chatGroupDao.findByCareer(careerCode, platform, year, quarter);
    }

}
