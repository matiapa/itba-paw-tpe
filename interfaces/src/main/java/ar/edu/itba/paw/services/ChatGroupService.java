package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.ChatGroup;

import java.util.List;
import java.util.Optional;

public interface ChatGroupService {
    List<ChatGroup> findByCareer(int careerId);

    Optional<ChatGroup> findById(String id);
}
