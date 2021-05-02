package ar.edu.itba.paw.persistence;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import ar.edu.itba.paw.models.EntityTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.Poll.PollState;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PollDaoJdbc implements PollDao {
    @Autowired
    private UserDao userDao;

    private final SimpleJdbcInsert simpleJdbcInsertPollOption;
    private final SimpleJdbcInsert simpleJdbcInsertPollChoiceVote;
    private final SimpleJdbcInsert simpleJdbcInsertPollRegisterVote;

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Poll> rowMapper = (rs, rowNum) -> {
        int id = rs.getInt("id");
        Optional<User> optUser = userDao.findById(rs.getInt("submitted_by"));
        return new Poll(
            id,
            rs.getString("name"),
            rs.getString("description"),
            PollFormat.valueOf(rs.getString("format").replace("-", "_")),
            rs.getString("career_code"),
            rs.getString("course_id"),
            rs.getTimestamp("creation_date"),
            rs.getTimestamp("expiry_date"),
            optUser.orElse(null),
            getOptions(id)
        );
    };


    private final RowMapper<PollOption> optionMapper = (rs, rowNum) ->
        new PollOption(
            rs.getInt("option_id"),
            rs.getString("option_value")
        );

    private final RowMapper<PollVoteOption> voteMapper = (rs, rowNum) -> new PollVoteOption(
            rs.getInt("option_id"),
            rs.getString("option_value"),
            rs.getInt("votes"));


    @Autowired
    public PollDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);

        SimpleJdbcInsert simpleJdbcInsertPoll = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("poll")
                .usingGeneratedKeyColumns("id");
                
        this.simpleJdbcInsertPollOption = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("poll_option")
                .usingGeneratedKeyColumns("option_id");

         this.simpleJdbcInsertPollChoiceVote = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("poll_submission")
                .usingGeneratedKeyColumns("value")
                .usingGeneratedKeyColumns("date");

        this.simpleJdbcInsertPollRegisterVote = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("poll_vote_registry");
    }

    
    private String queryBuilder(String baseQuery, PollFormat format, PollState state, Integer offset, Integer limit){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseQuery);

        if (format != null) {
            stringBuilder.append(
                String.format(" AND format='%s'", format.toString().replace("_", "-"))
            );
        }

        if (state != null){
            if(state == PollState.open)
                stringBuilder.append(" AND expiry_date>=now() OR expiry_date IS NULL");
            else
                stringBuilder.append(" AND expiry_date<now()");
        }

       if(offset != null && limit != null){
           stringBuilder.append(String.format(" OFFSET %d LIMIT %d", offset, limit));
       }

        return stringBuilder.toString();
    }


    @Override
    public List<Poll> findRelevant(int userId) {
        String query = "SELECT *, (SELECT count(*) FROM poll_vote_registry WHERE poll_id=id) votes FROM poll\n" +
        "WHERE (expiry_date IS NULL OR expiry_date>now())\n" +
        "  AND (course_id IS NULL OR course_id IN (SELECT course_id FROM fav_course WHERE user_id='%d'))\n" +
        "  AND (career_code IS NULL OR career_code = (SELECT u.career_code FROM users u WHERE u.id='%d'))\n" +
        "ORDER BY votes DESC LIMIT 5";

        return jdbcTemplate.query(String.format(query, userId, userId), rowMapper);
    }

    @Override
    public List<Poll> findGeneral(PollFormat format, PollState state, int offset, int limit) {
        String query = queryBuilder(
            "SELECT * FROM poll WHERE career_code IS NULL AND course_id IS NULL",
            format, state, offset, limit
        );

        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<Poll> findByCareer(String careerCode, PollFormat format, PollState state, int offset, int limit) {
        String query = queryBuilder(
            String.format("SELECT * FROM poll WHERE career_code='%s' AND course_id IS NULL", careerCode),
            format, state, offset, limit
        );

        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<Poll> findByCourse(String courseId, PollFormat format, PollState state, int offset, int limit) {
        String query = queryBuilder(
            String.format("SELECT * FROM poll WHERE course_id='%s' AND career_code IS NULL", courseId),
            format, state, offset, limit
        );

        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public int getSize(EntityTarget filterBy, String code, PollFormat format, PollState pollState) {
        String baseQuery;

        switch (filterBy){
            case career:
                baseQuery = String.format("SELECT COUNT(*) FROM poll WHERE career_code='%s' AND course_id IS NULL", code);
                break;
            case course:
                baseQuery = String.format("SELECT COUNT(*) FROM poll WHERE course_id='%s' AND career_code IS NULL", code);
                break;
            case general:
            default:
                baseQuery = "SELECT COUNT(*) FROM poll WHERE career_code IS NULL AND course_id IS NULL";
        }

        return jdbcTemplate.queryForObject(
            queryBuilder(baseQuery, format, pollState, null, null),
            Integer.class
        );
    }

    @Override
    public Optional<Poll> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            String.format("SELECT * FROM poll WHERE id='%d'", id),
            rowMapper
        ));
    }

    // -------------------- CREATE / DELETE --------------------

    @Transactional
    @Override
    public void addPoll(String name, String description, PollFormat format, String careerCode, String courseId, Date expiryDate, int userId, List<String> pollOptions) {

        Poll poll = jdbcTemplate.queryForObject(
        "INSERT INTO " +
                "poll(name, description, format, career_code, course_id, creation_date, expiry_date, submitted_by)" +
            " VALUES " +
                "(?, ?, CAST(? AS poll_format_type), ?, ?, DEFAULT, ?, ?) " +
            "RETURNING *;",
            new Object[]{
                name, description, format.toString().replace("_","-"),
                careerCode, courseId, expiryDate, userId
            },
            rowMapper
        );

        final Number id = poll.getId();
        for(String pollOption : pollOptions){
            addPollOption(id.intValue(), pollOption);
        }
    }

    @Override
    public void delete(int id){
        jdbcTemplate.execute(
            String.format("DELETE FROM poll WHERE id=%d", id)
        );
    }

    // -------------------- VOTING --------------------

    @Override
    public Map<PollOption,Integer> getVotes(int id) {

        List<PollVoteOption> list = jdbcTemplate.query(
            String.format(
                "SELECT count(poll_option.option_id) AS votes , poll_option.option_value, poll_option.option_id FROM\n" +
                "              poll  JOIN poll_option  on poll.id = poll_option.poll_id\n" +
                "                  JOIN poll_submission on poll.id = poll_submission.poll_id and poll_option.option_id= poll_submission.option_id\n" +
                "WHERE poll_option.poll_id='%d'\n" +
                "GROUP BY poll_option.option_id,poll_option.option_value",id
            ),
            voteMapper
        );

        Map<PollOption,Integer> map= new HashMap<>();
        for (PollVoteOption pair:list) {
            map.put(new PollOption(pair.getId(), pair.getValue()), pair.getVote());
        }

        return map;
    }

    private void addPollOption(Integer id, String value) {
        final Map<String, Object> args = new HashMap<>();
        args.put("poll_id", id);
        args.put("option_value", value);
        final Number id_po = simpleJdbcInsertPollOption.executeAndReturnKey(args);
    }

    private List<PollOption> getOptions(int pollId) {
        return jdbcTemplate.query(
                String.format("SELECT * FROM poll_option WHERE poll_id='%d'", pollId),
                optionMapper
        );
    }

    @Transactional
    @Override
    public void voteChoicePoll(int pollId, int optionId, int userId) {
        boolean validOption = jdbcTemplate.queryForList(
            "SELECT * FROM poll_option WHERE poll_id=? AND option_id=?", pollId, optionId)
            .size() > 0;
        if(!validOption || hasVoted(pollId, userId))
            return;

        Map<String, Object> args = new HashMap<>();
        args.put("poll_id", pollId);
        args.put("option_id", optionId);
        simpleJdbcInsertPollChoiceVote.execute(args);

        args = new HashMap<>();
        args.put("poll_id", pollId);
        args.put("user_id", userId);
        simpleJdbcInsertPollRegisterVote.execute(args);
    }
    
    @Override
    public boolean hasVoted(int pollId, int userId) {
        return jdbcTemplate.queryForList(
            "SELECT * FROM poll_vote_registry WHERE poll_id=? AND user_id=?", pollId, userId
        ).size() > 0;
    }

    private static class PollVoteOption {
        private final int id;
        private final String value;
        private final int vote;

        public PollVoteOption(int id, String value,int vote) {
            this.id = id;
            this.value = value;
            this.vote=vote;
        }

        public int getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public int getVote(){return vote;}
    }

}