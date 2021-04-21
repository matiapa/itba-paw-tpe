package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ChatGroupDaoJdbc implements ChatGroupDao{

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<ChatGroup> CHAT_GROUP_ROW_MAPPER = (rs, rowNum) ->
        new ChatGroup(
            rs.getInt("id"),
            rs.getInt("career_id"),
            rs.getString("name"),
            rs.getString("link"),
            rs.getInt("submitted_by"),
            rs.getDate("creation_date"),
            ChatPlatform.valueOf(rs.getString("platform"))
        );

    @Autowired
    public ChatGroupDaoJdbc(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public ChatGroup addGroup(String groupName, Integer careerId, String link, Integer createdBy, Date creationDate, ChatPlatform platform) {
        return jdbcTemplate.queryForObject(
            "INSERT INTO " +
                    "chat_group(career_id, creation_date, name, link, submitted_by, platform)" +
                " VALUES " +
                    "(?,?,?,?,?,CAST(? AS chat_platform)) RETURNING *",
            new Object[]{
                careerId, creationDate, groupName, link, createdBy,
                platform.toString()
            },
            CHAT_GROUP_ROW_MAPPER
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

    @Override
    public List<ChatGroup> findByCareer(int careerId, ChatPlatform platform, Integer year, Integer quarter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("SELECT * FROM chat_group WHERE career_id='%d'", careerId));

        if (platform != null)
            stringBuilder.append(String.format(" AND platform='%s'", platform.toString().split("\\.")[0]));

        String minDate, maxDate;
        if(year != null && quarter != null){
            minDate = String.format("%d-%d-01", year, quarter == 1 ? 1 : 7);
            maxDate = String.format("%d-%d-%d", year, quarter == 1 ? 6 : 12, quarter == 1 ? 30 : 31);
            stringBuilder.append(
                String.format(" AND creation_date>='%s' AND creation_date<='%s'", minDate, maxDate)
            );
        }else if(year != null){
            minDate = String.format("%d-01-01", year);
            maxDate = String.format("%d-12-31", year);
            stringBuilder.append(
                String.format(" AND creation_date>='%s' AND creation_date<='%s'", minDate, maxDate)
            );
        }else if(quarter != null){
            stringBuilder.append(
                String.format(" AND EXTRACT(month FROM creation_date)>='%d' AND EXTRACT(month FROM creation_date)<='%d'",
                    quarter == 1 ? 1 : 7, quarter == 1 ? 6 : 12)
            );
        }

        return jdbcTemplate.query(stringBuilder.toString(), CHAT_GROUP_ROW_MAPPER);
    }

    @Override
    public Optional<ChatGroup> findById(String id) {
        return jdbcTemplate.query(
                String.format("SELECT * FROM chat_group WHERE id='%s'", id),
                CHAT_GROUP_ROW_MAPPER
        ).stream().findFirst();
    }

}