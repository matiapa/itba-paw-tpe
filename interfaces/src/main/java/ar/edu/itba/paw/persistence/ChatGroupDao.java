package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;

import java.util.List;

public interface ChatGroupDao {

    List<ChatGroup> getChats();

    List<ChatGroup> findByCareer(int careerId);

    List<ChatGroup> findByCareer(int careerId, int limit);

}
