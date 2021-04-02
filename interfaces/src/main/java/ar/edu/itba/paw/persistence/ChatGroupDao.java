package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;

import java.util.List;
import java.util.Optional;

public interface ChatGroupDao {

    List<ChatGroup> findByCareer(int careerId);

    Optional<ChatGroup> findById(String id);
}
