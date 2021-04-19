package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.User;

import java.util.Date;
import java.util.List;

public interface ChatGroupDao {

    boolean addGroup(String groupName, String careerId, String link, User user, Date date);

    List<ChatGroup> getChats();

    List<ChatGroup> findByCareer(int careerId);

    List<ChatGroup> findByCareer(int careerId, int limit);

}
