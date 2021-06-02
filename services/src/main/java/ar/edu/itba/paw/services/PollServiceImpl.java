package ar.edu.itba.paw.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;
import ar.edu.itba.paw.models.Poll.PollState;
import ar.edu.itba.paw.persistence.PollDao;


@Service
public class PollServiceImpl implements PollService {

    @Autowired private PollDao pollDao;

    @Override
    public List<Poll> findRelevant(User user, int topN) {
        return pollDao.findRelevant(user, topN);
    }

    @Override
    public List<Poll> findControversial(User user, int topN) {
        return pollDao.findControversial(user, topN);
    }

    @Override
    public List<Poll> findGeneral(PollFormat format, PollState pollState, Integer page, Integer pageSize) {
        return pollDao.findGeneral(format, pollState, page, pageSize);
    }

    @Override
    public List<Poll> findByCareer(Career career, PollFormat format, PollState pollState, Integer page,
            Integer pageSize) {
        return pollDao.findByCareer(career, format, pollState, page, pageSize);
    }

    @Override
    public List<Poll> findByCourse(Course course, PollFormat format, PollState pollState, Integer page,
            Integer pageSize) {
        return pollDao.findByCourse(course, format, pollState, page, pageSize);
    }

    @Override
    public int getCount(EntityTarget filterBy, Career career, PollFormat format, PollState pollState) {
        return pollDao.getCount(filterBy, career, format, pollState);
    }

    @Override
    public int getCount(EntityTarget filterBy, Course course, PollFormat format, PollState pollState) {
        return pollDao.getCount(filterBy, course, format, pollState);
    }

    @Override
    public int getCount(EntityTarget filterBy, PollFormat format, PollState pollState) {
        return pollDao.getCount(filterBy, format, pollState);
    }

    @Override
    public Optional<Poll> findById(int id) {
        return pollDao.findById(id);
    }

    @Override
    public Map<PollOption, Integer> getVotes(Poll poll) {
        return pollDao.getVotes(poll);
    }

    @Override
    public void addPoll(String name, String description, PollFormat format, Course course,
            Date expiryDate, User user, List<String> pollOptions) {
        pollDao.addPoll(name, description, format, course, expiryDate, user, pollOptions);
    }

    @Override
    public void addPoll(String name, String description, PollFormat format, Career career,
            Date expiryDate, User user, List<String> pollOptions) {
        pollDao.addPoll(name, description, format, career, expiryDate, user, pollOptions);
    }

    @Override
    public void addPoll(String name, String description, PollFormat format,
            Date expiryDate, User user, List<String> pollOptions) {
        pollDao.addPoll(name, description, format, expiryDate, user, pollOptions);
    }

    @Override
    public void voteChoicePoll(Poll poll, PollOption option, User user) {
        pollDao.voteChoicePoll(poll, option, user);
    }

    @Override
    public boolean hasVoted(Poll poll, User user) {
        return pollDao.hasVoted(poll, user);
    }

    @Override
    public void delete(Poll poll) {
        pollDao.delete(poll);
    }

}