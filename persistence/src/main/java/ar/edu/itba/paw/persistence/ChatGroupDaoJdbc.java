package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatGroupDaoJdbc implements ChatGroupDao{

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<ChatGroup> CHAT_GROUP_ROW_MAPPER = (rs, rowNum) ->
        new ChatGroup(
            rs.getString("id"),
            rs.getString("career_id"),
            rs.getString("name"),
            rs.getString("link"),
            rs.getDate("creation_date")
        );

    @Autowired
    public ChatGroupDaoJdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    @Override
    public boolean addGroup(String groupName, String careerId, String link, User user, Date date) {
        jdbcTemplate.query(String.format("INSERT INTO chat_group" +
                        "(id, career_id, creation_date, name, link, submitted_by) " +
                        "VALUES (2, %s, %s, %s, %s, %d)",
                careerId, date.toString(), groupName, link, user.getId()), CHAT_GROUP_ROW_MAPPER);
        return false;
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