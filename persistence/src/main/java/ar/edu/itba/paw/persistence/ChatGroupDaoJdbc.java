package ar.edu.itba.paw.persistence;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.models.ChatGroup;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;

@Repository
public class ChatGroupDaoJdbc implements ChatGroupDao{

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<ChatGroup> CHAT_GROUP_ROW_MAPPER = (rs, rowNum) ->
        new ChatGroup(
            rs.getInt("id"),
            rs.getString("career_code"),
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


    private String queryBuilder(String baseQuery, ChatPlatform platform, Integer year, Integer quarter, Integer offset, Integer limit){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(baseQuery);

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

       if(offset != null && limit != null)
           stringBuilder.append(String.format(" OFFSET %d LIMIT %d", offset, limit));

       return stringBuilder.toString();
    }

    @Override
    public List<ChatGroup> findByCareer(String careerCode, ChatPlatform platform, Integer year, Integer quarter, int offset, int limit) {
        String baseQuery = String.format("SELECT * FROM chat_group WHERE career_code='%s'", careerCode);

        return jdbcTemplate.query(
            queryBuilder(baseQuery, platform, year, quarter, offset, limit),
            CHAT_GROUP_ROW_MAPPER
        );
    }

    @Override
    public int getSize(String careerCode, ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter) {
        String baseQuery = String.format("SELECT COUNT(*) FROM chat_group WHERE career_code='%s'", careerCode);

        return jdbcTemplate.queryForObject(
            queryBuilder(baseQuery, selectedPlatform, selectedYear, selectedQuarter, null, null),
            Integer.class
        );
    }

    @Override
    public Optional<ChatGroup> findById(String id) {
        return jdbcTemplate.query(
                String.format("SELECT * FROM chat_group WHERE id='%s'", id),
                CHAT_GROUP_ROW_MAPPER
        ).stream().findFirst();
    }

    @Override
    public ChatGroup addGroup(String groupName, String careerCode, String link, int createdBy, Date creationDate, ChatPlatform platform) {
        return jdbcTemplate.queryForObject(
            "INSERT INTO " +
                    "chat_group(career_code, creation_date, name, link, submitted_by, platform)" +
                    " VALUES " +
                    "(?,?,?,?,?,CAST(? AS chat_platform)) RETURNING *",
            new Object[]{
                    careerCode, creationDate, groupName, link, createdBy,
                    platform.toString()
            },
            CHAT_GROUP_ROW_MAPPER
        );
    }

    @Override
    public void delete(int id){
        jdbcTemplate.execute(
                String.format("DELETE FROM chat_group WHERE id=%d", id)
        );
    }

}