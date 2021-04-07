package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Poll;

import java.util.List;
import java.util.Optional;

public interface PollService {
    List<Poll> findGeneral();
    List<Poll> findByCareer(int careerId);
    List<Poll> findByCourse(String courseId);
    Optional<Poll> findById(int id);
}
