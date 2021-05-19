package ar.edu.itba.paw.services;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.Poll.PollState;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PollService {

    List<Poll> findRelevant(int userId);

    List<Poll> findControversial(int userId);

    List<Poll> findGeneral(PollFormat format, PollState pollState, int offset, int limit);

    List<Poll> findByCareer(String careerCode, PollFormat format, PollState pollState, int offset, int limit);

    List<Poll> findByCourse(String courseId, PollFormat format, PollState pollState, int offset, int limit);

    int getSize(EntityTarget filterBy, String code, PollFormat format, PollState pollState);

    Optional<Poll> findById(int id);


    Map<PollOption,Integer> getVotes(int id);

    void addPoll(String name, String description, PollFormat format, String careerCode, String courseId, Date expiryDate, User user, List<String> pollOptions);
    
    void voteChoicePoll(int pollId, int optionId, User user);
    
    boolean hasVoted(int pollId, User user);

    void delete(int id);
}