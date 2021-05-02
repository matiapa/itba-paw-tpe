package ar.edu.itba.paw.services;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Autowired private UserService userService;


    @Override
    public List<Poll> findRelevant(int userId){
        return pollDao.findRelevant(userId);
    }

    @Override
    public List<Poll> findGeneral(PollFormat format, PollState pollState, int offset, int limit){
        return pollDao.findGeneral(format, pollState, offset, limit);
    }

    @Override
    public List<Poll> findByCareer(String careerCode, PollFormat format, PollState pollState, int offset, int limit){
        return pollDao.findByCareer(careerCode, format, pollState, offset, limit);
    }

    @Override
    public List<Poll> findByCourse(String courseId, PollFormat format, Poll.PollState pollState, int offset, int limit){
        return pollDao.findByCourse(courseId, format, pollState, offset, limit);
    }

    @Override
    public int getSize(EntityTarget filterBy, String code, PollFormat format, PollState pollState){
        return pollDao.getSize(filterBy, code, format, pollState);
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

    @Override
    public void delete(int id) {
        pollDao.delete(id);
    }

}