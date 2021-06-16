package ar.edu.itba.paw.persistence.jpa;


import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.ChatGroup.ChatPlatform;
import ar.edu.itba.paw.persistence.ChatGroupDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Repository
public class ChatGroupDaoJPA implements ChatGroupDao {

    @PersistenceContext
    private EntityManager em;

    String buildQuery(String baseSelect, ChatPlatform selectedPlatform, Integer selectedYear, Integer selectedQuarter){
        String qryStr = baseSelect;

        qryStr += " WHERE cg.career.code = :careerCode";

        if (selectedPlatform != null)
            qryStr += " AND cg.platform = :platform";

        if(selectedYear != null && selectedQuarter != null){
            qryStr += " AND cg.creationDate >= :minDate AND cg.creationDate <= :maxDate";

        }else if(selectedYear != null){
            qryStr += " AND cg.creationDate >= :minDate AND cg.creationDate <= :maxDate";

        }else if(selectedQuarter != null){
            qryStr += " AND month(cg.creationDate) >= :minQuarter AND month(cg.creationDate) <= :maxQuarter";
        }

        return qryStr;
    }

    @Override
    public List<ChatGroup> findByCareer(Career career, ChatPlatform platform, Integer year, Integer quarter,
                                        Integer page, Integer pageSize) {
        String qryStr = buildQuery("SELECT cg FROM ChatGroup cg", platform, year, quarter);

        TypedQuery<ChatGroup> query = em.createQuery(qryStr, ChatGroup.class);

        query.setParameter("careerCode", career.getCode());

        if (platform != null)
            query.setParameter("platform",platform);

        if(year != null && quarter != null){
            query.setParameter("minDate", new GregorianCalendar(year, quarter == 1
                    ? Calendar.JANUARY : Calendar.AUGUST, 1).getTime());
            query.setParameter("maxDate", new GregorianCalendar(year, quarter == 1
                    ? Calendar.JULY : Calendar.DECEMBER, quarter == 1 ? 30 : 31).getTime());
        }else if(year != null){
            query.setParameter("minDate", new GregorianCalendar(year, Calendar.JANUARY, 1).getTime());
            query.setParameter("maxDate", new GregorianCalendar(year, Calendar.DECEMBER, 31).getTime());
        }else if(quarter != null){
            query.setParameter("minQuarter", quarter == 1 ? 1 : 7);
            query.setParameter("maxQuarter", quarter == 1 ? 6 : 12);
        }

        query.setFirstResult(pageSize * page);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }


    @Override
    public int getSize(Career career, ChatPlatform platform, Integer year, Integer quarter) {
        String qryStr = buildQuery("SELECT count(cg.id) FROM ChatGroup cg", platform, year, quarter);

        TypedQuery<Long> query = em.createQuery(qryStr, Long.class);

        query.setParameter("careerCode", career.getCode());

        if (platform != null)
            query.setParameter("platform", platform);

        if(year != null && quarter != null){
            query.setParameter("minDate", new GregorianCalendar(year, quarter == 1
                    ? Calendar.JANUARY : Calendar.AUGUST, 1).getTime());
            query.setParameter("maxDate", new GregorianCalendar(year, quarter == 1
                    ? Calendar.JULY : Calendar.DECEMBER, quarter == 1 ? 30 : 31).getTime());
        }else if(year != null){
            query.setParameter("minDate", new GregorianCalendar(year, Calendar.JANUARY, 1).getTime());
            query.setParameter("maxDate", new GregorianCalendar(year, Calendar.DECEMBER, 31).getTime());
        }else if(quarter != null){
            query.setParameter("minQuarter", quarter == 1 ? 1 : 7);
            query.setParameter("maxQuarter", quarter == 1 ? 6 : 12);
        }

        return query.getSingleResult().intValue();
    }


    @Override
    public Optional<ChatGroup> findById(int id) {
       return Optional.ofNullable(
           em.find(ChatGroup.class, id)
       );
    }


    @Override
    @Transactional
    public ChatGroup addGroup(String groupName, Career career, String link, User createdBy, Date creationDate, ChatPlatform platform){
        ChatGroup chatGroup = new ChatGroup(null, career, groupName, link, createdBy, creationDate, platform);
        em.persist(chatGroup);
        return chatGroup;
    }


    @Override
    @Transactional
    public void delete(ChatGroup chatGroup) {
        em.remove(chatGroup);
        em.flush();
        em.clear();
    }

}