package ar.edu.itba.paw.services;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;
import ar.edu.itba.paw.models.Poll.PollState;
import ar.edu.itba.paw.models.User;

public interface PollService {

    List<Poll> findRelevant(User user, int topN);

    List<Poll> findControversial(User user, int topN);

    List<Poll> findGeneral(PollFormat format, PollState pollState, Integer page, Integer pageSize);

    List<Poll> findByCareer(Career career, PollFormat format, PollState pollState, Integer page, Integer pageSize);

    List<Poll> findByCourse(Course course, PollFormat format, PollState pollState, Integer page, Integer pageSize);

    int getCount(EntityTarget filterBy, Career career, PollFormat format, PollState pollState);

    int getCount(EntityTarget filterBy, Course course, PollFormat format, PollState pollState);
    
    int getCount(EntityTarget filterBy, PollFormat format, PollState pollState);

    Optional<Poll> findById(int id);

    Map<PollOption,Integer> getVotes(Poll poll);

    void addPoll(String name, String description, PollFormat format, Course course,
        Date expiryDate, User user, List<String> pollOptions);

    void addPoll(String name, String description, PollFormat format, Career career,
        Date expiryDate, User user, List<String> pollOptions);

    void addPoll(String name, String description, PollFormat format,
        Date expiryDate, User user, List<String> pollOptions);

    void voteChoicePoll(Poll poll, PollOption option, User user);
    
    boolean hasVoted(Poll poll, User user);

    void delete(Poll poll);
}