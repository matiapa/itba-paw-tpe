package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollState;
import ar.edu.itba.paw.models.Poll.PollOption;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PollService {

    List<Poll> findGeneral();

    List<Poll> findGeneral(PollFormat format, PollState pollState);

    List<Poll> findByCareer(int careerId);

    List<Poll> findByCareer(int careerId, PollFormat format, PollState pollState);

    List<Poll> findByCourse(String courseId);

    List<Poll> findByCourse(String courseId, PollFormat format, PollState pollState);

    Optional<Poll> findById(int id);

    Map<PollOption,Integer> getVotes(int id);

    void addPoll(String name, String description, Poll.PollFormat format, Integer careerId, String courseId, Date creationDate, Date expiryDate, Integer user, List<String> pollOptions);

}