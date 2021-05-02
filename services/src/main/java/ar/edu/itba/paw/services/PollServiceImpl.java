package ar.edu.itba.paw.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ar.edu.itba.paw.models.HolderEntity;
import ar.edu.itba.paw.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;
import ar.edu.itba.paw.persistence.PollDao;


@Service
public class PollServiceImpl implements PollService {

    @Autowired
    private PollDao pollDao;

    @Override
    public List<Poll> findByCareer(String careerCode) {
        return pollDao.findByCareer(careerCode);
    }

    @Override
    public List<Poll> findByCareer(String careerCode, Poll.PollFormat format, Poll.PollState pollState, int offset, int limit) {
        return pollDao.findByCareer(careerCode, format, pollState, offset, limit);
    }

    @Override
    public List<Poll> findByCourse(String courseId) {
        return pollDao.findByCourse(courseId);
    }

    @Override
    public List<Poll> findByCourse(String courseId, Poll.PollFormat format, Poll.PollState pollState, int offset, int limit) {
        return pollDao.findByCourse(courseId, format, pollState, offset, limit);
    }

    @Override
    public int getSize(HolderEntity filterBy, String code) {
        return pollDao.getSize(filterBy, code);
    }

    @Override
    public List<Poll> findGeneral(int offset, int limit) {
        return pollDao.findGeneral(offset, limit);
    }

    @Override
    public List<Poll> findGeneral(Poll.PollFormat format, Poll.PollState pollState) {
        return pollDao.findGeneral(format, pollState);
    }

    @Override
    public Optional<Poll> findById(int id) {
        return pollDao.findById(id);
    }

    @Override
    public Map<PollOption, Integer> getVotes(int id) {
        return pollDao.getVotes(id);
    }

    @Override
    public void addPoll(String name, String description, PollFormat format, String careerCode, String courseId, Date expiryDate, User user, List<String> pollOptions) {
        pollDao.addPoll(name, description, format, careerCode, courseId, expiryDate, user.getId(), pollOptions);
    }

    @Override
    public void voteChoicePoll(int pollId, int optionId, User user) {
        pollDao.voteChoicePoll(pollId, optionId, user.getId());
    }

    @Override
    public boolean hasVoted(int pollId, User user) {
        return pollDao.hasVoted(pollId, user.getId());
    }

}