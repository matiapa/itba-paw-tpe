package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollOption;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PollDao {
    List<Poll> findGeneral();
    List<Poll> findByCareer(int careerId);
    List<Poll> findByCourse(String courseId);
    Optional<Poll> findById(int id);
    Map<PollOption,Integer> getVotes(int id);

    void addPoll(String name, String description, Integer careerId, String courseId, Date creationDate, Date expiryDate, Integer user, List<String> pollOptions);
}
