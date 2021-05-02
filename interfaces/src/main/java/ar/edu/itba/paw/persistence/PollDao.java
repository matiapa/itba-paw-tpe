package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PollDao {

    List<Poll> findGeneral();

    List<Poll> findGeneral(Poll.PollFormat format, Poll.PollState pollState);

    List<Poll> findByCareer(String careerCode);

    List<Poll> findByCareer(String careerCode, Poll.PollFormat format, Poll.PollState pollState);

    List<Poll> findByCourse(String courseId);

    List<Poll> findByCourse(String courseId, Poll.PollFormat format, Poll.PollState pollState);

    Optional<Poll> findById(int id);

    Map<PollOption,Integer> getVotes(int id);

    void addPoll(String name, String description, PollFormat format, String careerCode, String courseId, Date expiryDate, int userId, List<String> pollOptions);

    void voteChoicePoll(int pollId, int optionId, int userId);
    
    boolean hasVoted(int pollId, int userId);

    void delete(int id);
}