package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ChatGroupDaoJdbc implements ChatGroupDao{

    static int id = 2;
    private final SimpleJdbcInsert simpleJdbcInsert;


    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<ChatGroup> CHAT_GROUP_ROW_MAPPER = (rs, rowNum) ->
        new ChatGroup(
            rs.getInt("id"),
            rs.getString("career_id"),
            rs.getString("name"),
            rs.getString("link"),
            rs.getInt("submitted_by"),
            rs.getDate("creation_date")
        );

    @Autowired
    public ChatGroupDaoJdbc(DataSource ds){

        this.jdbcTemplate = new JdbcTemplate(ds);
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("chat_group")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS chat_group(" +
                "id             SERIAL," +
                "career_id      INT NOT NULL," +
                "creation_date  DATE NOT NULL," +
                "name           VARCHAR(100) NOT NULL," +
                "link           VARCHAR (100) NOT NULL," +
                "submitted_by   INT NOT NULL," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(career_id) REFERENCES career ON DELETE CASCADE," +
                "FOREIGN KEY(submitted_by) REFERENCES users ON DELETE RESTRICT );");
    }

    @Override
    public ChatGroup addGroup(String groupName, String careerId, String link, Integer user, Date date) {
        final Map<String, Object> args = new HashMap<>();
        args.put("career_id", careerId);
        args.put("creation_date", date);
        args.put("name", groupName);
        args.put("link", link);
        args.put("submitted_by", user);

        final Number id = simpleJdbcInsert.executeAndReturnKey(args);

        return new ChatGroup(id.intValue(), careerId, groupName, link, user, date);
    }

    @Override
    public List<ChatGroup> getChats() {
        return jdbcTemplate.query(
                String.format("SELECT * FROM chat_group"), CHAT_GROUP_ROW_MAPPER
        );
    }

    @Override
    public List<ChatGroup> findByCareer(int careerId) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM chat_group WHERE career_id='%d'", careerId),
            CHAT_GROUP_ROW_MAPPER
        );
    }

    @Override
    public List<ChatGroup> findByCareer(int careerId, int limit) {
        return jdbcTemplate.query(
            String.format("SELECT * FROM chat_group WHERE career_id='%d' "+
                "ORDER BY id LIMIT %d", careerId, limit),
            CHAT_GROUP_ROW_MAPPER
        );
    }

    public Optional<ChatGroup> findById(String id) {
        return jdbcTemplate.query(
                String.format("SELECT * FROM chat_group WHERE id='%s'", id),
                CHAT_GROUP_ROW_MAPPER
        ).stream().findFirst();
    }

}