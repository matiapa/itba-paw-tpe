package ar.edu.itba.paw.persistence;

import java.util.*;

import javax.sql.DataSource;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.Poll;
import ar.edu.itba.paw.models.Poll.PollState;
import ar.edu.itba.paw.models.Poll.PollFormat;
import ar.edu.itba.paw.models.Poll.PollOption;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class PollDaoJdbc implements PollDao {
    @Autowired
    private UserDao userDao;

    private final SimpleJdbcInsert simpleJdbcInsertPoll;
    private final SimpleJdbcInsert simpleJdbcInsertPollOption;

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Poll> rowMapper = (rs, rowNum) -> {
        int id = rs.getInt("id");
        Optional<User> optUser = userDao.findById(rs.getInt("submitted_by"));
        return new Poll(
            id,
            rs.getString("name"),
            rs.getString("description"),
            PollFormat.valueOf(rs.getString("format").replace("-", "_")),
            rs.getInt("career_id"),
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

    private final RowMapper<Poll.PollVoteOption> voteMapper = (rs, rowNum) -> new Poll.PollVoteOption(
            rs.getInt("option_id"),
            rs.getString("option_value")
            ,rs.getInt("votes"));


    @Autowired
    public PollDaoJdbc(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);

        this.simpleJdbcInsertPoll = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("poll")
                .usingGeneratedKeyColumns("id");
        this.simpleJdbcInsertPollOption = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("poll_option")
                .usingGeneratedKeyColumns("option_id");
    }

    
    private List<Poll> find(String baseQuery, PollFormat format, PollState state){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseQuery);

        if (format != null)
            stringBuilder.append(
                String.format(" AND format='%s'", format.toString().replace("_", "-"))
            );

        if (state != null){
            if(state == PollState.open)
                stringBuilder.append(" AND expiry_date>=now() OR expiry_date IS NULL");
            else
                stringBuilder.append(" AND expiry_date<now()");
        }

        return jdbcTemplate.query(stringBuilder.toString(), rowMapper);
    }
    

    @Override
    public List<Poll> findGeneral() {
        return jdbcTemplate.query(
            "SELECT * FROM poll WHERE career_id IS NULL AND course_id IS NULL",
            rowMapper
        );
    }

    @Override
    public List<Poll> findGeneral(PollFormat format, PollState state) {
        return find(
            "SELECT * FROM poll WHERE career_id IS NULL AND course_id IS NULL",
            format, state
        );
    }
    

    @Override
    public List<Poll> findByCareer(int careerId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM poll WHERE career_id='%d' AND course_id IS NULL", careerId),
            rowMapper
        );
    }

    @Override
    public List<Poll> findByCareer(int careerId, PollFormat format, PollState state) {
        return find(
            String.format("SELECT * FROM poll WHERE career_id='%d' AND course_id IS NULL", careerId),
            format, state
        );
    }

    
    @Override
    public List<Poll> findByCourse(String courseId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM poll WHERE course_id='%s' AND career_id IS NULL", courseId),
            rowMapper
        );
    }

    @Override
    public List<Poll> findByCourse(String courseId, PollFormat format, PollState state) {
        return find(
            String.format("SELECT * FROM poll WHERE course_id='%s' AND career_id IS NULL", courseId),
            format, state
        );
    }

    @Override
    public Optional<Poll> findById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            String.format("SELECT * FROM poll WHERE id='%d'", id),
            rowMapper
        ));
    }

    @Override
    public Map<PollOption,Integer> getVotes(int id) {

        List<Poll.PollVoteOption> list =

        jdbcTemplate.query(
                String.format(
                "SELECT count(poll_option.option_id) AS votes , poll_option.option_value, poll_option.option_id FROM\n" +
                "              poll  JOIN poll_option  on poll.id = poll_option.poll_id\n" +
                "                  JOIN poll_submission on poll.id = poll_submission.poll_id and poll_option.option_id= poll_submission.option_id\n" +
                "WHERE poll_option.poll_id='%d'\n" +
                "GROUP BY poll_option.option_id,poll_option.option_value",id),voteMapper);
        Map<PollOption,Integer> map= new HashMap<>();
        for (Poll.PollVoteOption pair:list
             ) {

                map.put(new PollOption(pair.getId(), pair.getValue()), pair.getVote());
            }
        return map;

        }

    @Override
    public void addPoll(String name, String description, PollFormat format, Integer careerId, String courseId, Date creationDate, Date expiryDate, Integer user, List<String> pollOptions) {
        Poll poll = jdbcTemplate.queryForObject("INSERT INTO " +
                "poll(name, description, format, career_id, course_id, creation_date, expiry_date, submitted_by) VALUES " +
                "(?, ?, CAST(? AS poll_format_type), ?, ?, DEFAULT, ?, ?) " +
                "RETURNING *;", new Object[]{name, description, format.toString().replace("_","-"), careerId, courseId, expiryDate, user}, rowMapper);
        /*
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("description", description);
        args.put("career_id", careerId);
        args.put("course_id", courseId);
        args.put("format", "multiple-choice");      //TODO: Implementar otros format
        args.put("creation_date", null);
        args.put("expiry_date", expiryDate);
        args.put("submitted_by", user);

        final Number id = simpleJdbcInsertPoll.executeAndReturnKey(args);

 */
        final Number id = poll.getId();
        for(String pollOption : pollOptions){
            addPollOption(id.intValue(), pollOption);
        }
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
}
