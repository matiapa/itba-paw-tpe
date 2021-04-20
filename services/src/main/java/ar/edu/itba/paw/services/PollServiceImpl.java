package ar.edu.itba.paw.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollOption;
import ar.edu.itba.paw.persistence.PollDao;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    private PollDao pollDao;

    @Override
    public List<Poll> findByCareer(int careerId) {
        return pollDao.findByCareer(careerId);
    }

    @Override
    public List<Poll> findByCourse(String courseId) {
        return pollDao.findByCourse(courseId);
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
    public List<Poll> findGeneral() {
        return pollDao.findGeneral();
    }

}
