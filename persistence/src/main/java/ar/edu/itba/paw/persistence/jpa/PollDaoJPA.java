package ar.edu.itba.paw.persistence.jpa;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.EntityTarget;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;
import ar.edu.itba.paw.models.Poll.PollState;
import ar.edu.itba.paw.models.Poll.PollVote;
import ar.edu.itba.paw.models.Poll.PollVoteCount;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.PollDao;

@Repository
public class PollDaoJPA implements PollDao {
    @PersistenceContext
    private EntityManager em;

    private <T> TypedQuery<T> queryBuilder(String baseQuery, PollFormat format, PollState state, Integer page, Integer pageSize, Class<T> _class) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseQuery);

        if (format != null)
            stringBuilder.append(" AND format = :format");

        if (state != null) {
            if(state == PollState.open)
                stringBuilder.append(" AND (expiryDate >= current_date OR expiryDate IS NULL)");
            else
                stringBuilder.append(" AND expiryDate < current_date");
        }

        TypedQuery<T> query = em.createQuery(stringBuilder.toString(), _class);
        if (format != null)
            query.setParameter("format", format);
        if(page != null && pageSize != null)
            query.setFirstResult(pageSize * page);
        if(pageSize != null)
            query.setMaxResults(pageSize);
        return query;
    }

	@Override
	public List<Poll> findRelevant(User user, int topN) {
		String qryStr = "SELECT p FROM Poll p \n" +
            "JOIN p.options as opt \n" +
            "WHERE p.expiryDate IS NULL OR p.expiryDate > current_date \n" +
            "AND (p.course IS NULL OR :forUserId IN (SELECT c.id FROM p.course.favedBy c)) \n" +
            "AND (p.career IS NULL OR :forUserCareerCode = p.career.code) \n" +
            "GROUP BY p \n" +
            "ORDER BY sum(opt.votes.votes) desc \n";

        TypedQuery<Poll> query = em.createQuery(qryStr, Poll.class);

        query.setParameter("forUserId", user.getId());
        query.setParameter("forUserCareerCode", user.getCareer().getCode());

        query.setMaxResults(topN);

        return query.getResultList();
	}

	@Override
	public List<Poll> findControversial(User user, int topN) {
		String qryStr = "SELECT p FROM PollControversy c \n" +
            "JOIN c.poll as p \n" +
            "WHERE p.expiryDate IS NULL OR p.expiryDate > now() \n" +
            "AND (p.course IS NULL OR :forUserId IN (SELECT c.id FROM p.course.favedBy c)) \n" +
            "AND (p.career IS NULL OR :forUserCareerCode = p.career.code) \n" +
            "ORDER BY c.points desc";

        TypedQuery<Poll> query = em.createQuery(qryStr, Poll.class);

        query.setParameter("forUserId", user.getId());
        query.setParameter("forUserCareerCode", user.getCareer().getCode());

        query.setMaxResults(topN);

        return query.getResultList();
	}

	@Override
	public List<Poll> findGeneral(PollFormat format, PollState pollState, Integer page, Integer pageSize) {
        TypedQuery<Poll> query = queryBuilder("FROM Poll where career IS NULL AND course IS NULL", 
            format, pollState, page, pageSize, Poll.class);
		return query.getResultList();
	}

	@Override
	public List<Poll> findByCareer(Career career, PollFormat format, PollState pollState, Integer page,
            Integer pageSize) {
        TypedQuery<Poll> query = queryBuilder("FROM Poll where career.code = :code AND course IS NULL", 
            format, pollState, page, pageSize, Poll.class);
        query.setParameter("code", career.getCode());

        return query.getResultList();
	}

	@Override
	public List<Poll> findByCourse(Course course, PollFormat format, PollState pollState, Integer page,
            Integer pageSize) {
        TypedQuery<Poll> query = queryBuilder("FROM Poll where course.id = :id AND career IS NULL", 
        format, pollState, page, pageSize, Poll.class);
        query.setParameter("id", course.getId());

        return query.getResultList();
	}

	@Override
	public int getCount(EntityTarget filterBy, Career career, PollFormat format, PollState pollState) {
		TypedQuery<Long> query = queryBuilder("SELECT count(*) FROM Poll where career.code = :code AND course IS NULL", 
            format, pollState, null, null, Long.class);
        query.setParameter("code", career.getCode());

        return query.getSingleResult().intValue();
	}

	@Override
	public int getCount(EntityTarget filterBy, Course course, PollFormat format, PollState pollState) {
		TypedQuery<Long> query = queryBuilder("SELECT count(*) FROM Poll where course.id = :id AND career IS NULL", 
        format, pollState, null, null, Long.class);
        query.setParameter("id", course.getId());

        return query.getSingleResult().intValue();
	}

	@Override
	public int getCount(EntityTarget filterBy, PollFormat format, PollState pollState) {
		TypedQuery<Long> query = queryBuilder("SELECT count(*) FROM Poll where career IS NULL AND course IS NULL", 
            format, pollState, null, null, Long.class);
		return query.getSingleResult().intValue();
	}

	@Override
	public Optional<Poll> findById(int id) {
        return Optional.ofNullable(
            em.find(Poll.class, id)
        );
	}

	@Override
	public Map<PollOption, Integer> getVotes(Poll poll) {
		return poll.getOptions()
            .stream()
            .collect(Collectors.toMap(Function.identity(), o -> {
                PollVoteCount voteCount = o.getVotes();
                return voteCount != null ? voteCount.getVotes() : 0;
            }));
	}

    @Transactional
	@Override
	public void addPoll(String name, String description, PollFormat format, Career career,
			Date expiryDate, User user, List<String> pollOptions) {
        Poll poll = new Poll(name, description, format, career, expiryDate, user);
        em.persist(poll);
        for(String value : pollOptions) {
            PollOption option = new PollOption(poll, value);
            em.persist(option);
        }
	}

    @Transactional
	@Override
	public void addPoll(String name, String description, PollFormat format, Course course,
			Date expiryDate, User user, List<String> pollOptions) {
        Poll poll = new Poll(name, description, format, course, expiryDate, user);
        em.persist(poll);
        for(String value : pollOptions) {
            PollOption option = new PollOption(poll, value);
            em.persist(option);
        }
	}

    @Transactional
	@Override
	public void addPoll(String name, String description, PollFormat format,
			Date expiryDate, User user, List<String> pollOptions) {
        Poll poll = new Poll(name, description, format, expiryDate, user);
        em.persist(poll);
        for(String value : pollOptions) {
            PollOption option = new PollOption(poll, value);
            em.persist(option);
        }
	}

	@Override
	public void voteChoicePoll(Poll poll, PollOption option, User user) {
		PollVote vote = new PollVote(option);
        em.persist(vote);
        poll.getVotedBy().add(user);
        em.merge(poll);
	}

	@Override
	public boolean hasVoted(Poll poll, User user) {
		return poll.getVotedBy().contains(user);
	}

	@Override
	public void delete(Poll poll) {
		em.remove(poll);
        em.flush();
        em.clear();
	}
    
}
