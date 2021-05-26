package ar.edu.itba.paw.persistence.jpa;


import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import org.springframework.stereotype.Repository;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatGroupDaoJPA implements ChatGroupDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public ChatGroup addGroup(String groupName, String careerCode, String link, User createdBy, Date creationDate, ChatGroup.ChatPlatform platform) {
        Career career = careerCode != null ? em.find(Career.class, careerCode) : null;
        ChatGroup chatGroup = new ChatGroup(null,career,groupName,link,createdBy,creationDate,platform);
        em.persist(chatGroup);
        return chatGroup;
    }

    @Override
    public int getSize(String careerCode, ChatGroup.ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FROM chat_group WHERE career_code= :careerCode");

        if (selectedPlatform != null)
            stringBuilder.append(" AND platform= :platform");

        int caso=0;
        String minDate, maxDate;
        if(selectedYear != null && selectedQuarter != null){
            caso=1;
            stringBuilder.append(" AND creation_date>= :minDate AND creation_date<= :maxDate");

        }else if(selectedYear != null){
            caso=2;
            stringBuilder.append(" AND creation_date>= :minDate AND creation_date<= :maxDate");

        }else if(selectedQuarter != null){
            caso=3;
            stringBuilder.append(" AND EXTRACT(month FROM creation_date)>= :minQuarter AND EXTRACT(month FROM creation_date)<= :maxQuarter");
        }

        final TypedQuery<ChatGroup> query = em.createQuery(stringBuilder.toString(), ChatGroup.class);
        query.setParameter("careerCode",careerCode);
        query.setParameter("platform",selectedPlatform);
        switch (caso){
            case 1:
                minDate = String.format("%d-%d-01", selectedYear, selectedQuarter == 1 ? 1 : 7);
                maxDate = String.format("%d-%d-%d", selectedYear, selectedQuarter == 1 ? 6 : 12, selectedQuarter == 1 ? 30 : 31);
                query.setParameter("minDate",minDate);
                query.setParameter("maxDate",maxDate);
                break;
            case 2:
                minDate = String.format("%d-01-01", selectedYear);
                maxDate = String.format("%d-12-31", selectedYear);
                query.setParameter("minDate",minDate);
                query.setParameter("maxDate",maxDate);
                break;
            case 3:
                query.setParameter("minQuarter",selectedQuarter == 1 ? 1 : 7);
                query.setParameter("maxQuarter",selectedQuarter == 1 ? 6 : 12);
                break;
            default:break;

        }
        return query.getResultList().size();


    }

    @Override
    public List<ChatGroup> findByCareer(String careerCode, ChatGroup.ChatPlatform platform, Integer year, Integer quarter, Integer offset, Integer limit) {


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FROM chat_group WHERE career_code= :careerCode");

        if (platform != null)
            stringBuilder.append(" AND platform= :platform");

        int caso=0;
        String minDate, maxDate;
        if(year != null && quarter != null){
            caso=1;
            stringBuilder.append(" AND creation_date>= :minDate AND creation_date<= :maxDate");

        }else if(year != null){
            caso=2;
            stringBuilder.append(" AND creation_date>= :minDate AND creation_date<= :maxDate");

        }else if(quarter != null){
            caso=3;
            stringBuilder.append(" AND EXTRACT(month FROM creation_date)>= :minQuarter AND EXTRACT(month FROM creation_date)<= :maxQuarter");
        }

        if(offset != null && limit != null)
            stringBuilder.append(" ORDER BY creation_date DESC OFFSET :offset LIMIT :limit");

        final TypedQuery<ChatGroup> query = em.createQuery(stringBuilder.toString(), ChatGroup.class);
        query.setParameter("careerCode",careerCode);
        query.setParameter("platform",platform);
        switch (caso){
            case 1:
                minDate = String.format("%d-%d-01", year, quarter == 1 ? 1 : 7);
                maxDate = String.format("%d-%d-%d", year, quarter == 1 ? 6 : 12, quarter == 1 ? 30 : 31);
                query.setParameter("minDate",minDate);
                query.setParameter("maxDate",maxDate);
                break;
            case 2:
                minDate = String.format("%d-01-01", year);
                maxDate = String.format("%d-12-31", year);
                query.setParameter("minDate",minDate);
                query.setParameter("maxDate",maxDate);
                break;
            case 3:
                query.setParameter("minQuarter",quarter == 1 ? 1 : 7);
                query.setParameter("maxQuarter",quarter == 1 ? 6 : 12);
                break;
            default:break;

        }
        query.setParameter("offset",offset);
        query.setParameter("limit",limit);


        return query.getResultList();
    }

    @Override
    public Optional<ChatGroup> findById(String id) {
        ChatGroup optionalChatGroup=em.find(ChatGroup.class,id);
        if (optionalChatGroup == null){
            return Optional.empty();
        }
        else {
            return Optional.of(optionalChatGroup);
        }
    }

    @Override
    public void delete(int id) {
        ChatGroup chatGroup= em.find(ChatGroup.class,id);
        em.remove(chatGroup);
        em.flush();
        em.clear();
    }
}
