package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.AnnouncementDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AnnouncementDaoJPA implements AnnouncementDao {

    @PersistenceContext
    private EntityManager em;


    String buildQuery(String baseSelect, EntityTarget target, Boolean hideSeen){

        // Build query string

        String qryStr = baseSelect;

        switch(target){
            case general:
                qryStr += " WHERE a.course IS NULL AND a.career IS NULL"; break;
            case career:
                qryStr += " WHERE a.career.code = :targetCode"; break;
            case course:
                qryStr += " WHERE a.course.id = :targetCode"; break;
        }

        if(hideSeen)
            qryStr += " AND :seenBy NOT IN (SELECT user.id FROM a.seenBy AS user)";

        return qryStr;
    }

    List<Announcement> findAnnouncements(EntityTarget target, String targetCode,
             User hideSeenBy, Integer limit, Integer offset){

        String qryStr = buildQuery("SELECT a FROM Announcement a", target, hideSeenBy != null);

        TypedQuery<Announcement> query = em.createQuery(qryStr, Announcement.class);

        query.setParameter("targetCode", targetCode);

        if(hideSeenBy != null) query.setParameter("seenBy", hideSeenBy);

        query.setFirstResult(limit * offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    int countAnnouncements(EntityTarget target, String targetCode, User hideSeenBy){

        String qryStr = buildQuery("SELECT count(a.id) FROM Announcement a", target, hideSeenBy != null);

        TypedQuery<Integer> query = em.createQuery(qryStr, Integer.class);

        query.setParameter("targetCode", targetCode);

        if(hideSeenBy != null) query.setParameter("seenBy", hideSeenBy);

        return query.getSingleResult();
    }


    @Override
    public List<Announcement> findRelevant(User forUser, int page, int pageSize) {
        // "SELECT * FROM announcement\n" +
        // "WHERE (expiry_date IS NULL OR expiry_date>now())\n" +
        // "AND (course_id IS NULL OR course_id IN (SELECT course_id FROM fav_course WHERE user_id='%d'))\n" +
        // "AND (career_code IS NULL OR career_code = (SELECT u.career_code FROM users u WHERE u.id='%d'))\n" +
        // "ORDER BY creation_date DESC LIMIT %d";

        String qryStr = "SELECT a FROM Announcement a WHERE a.expiryDate IS NULL OR a.expiryDate > current_date " +
            "AND (a.course IS NULL OR :forUserId IN (SELECT c.id FROM a.course.favedBy c)) " +
            "AND (a.career IS NULL OR a.career.id = :forUserCareer)";

        TypedQuery<Announcement> query = em.createQuery(qryStr, Announcement.class);

        query.setFirstResult(pageSize * page);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public List<Announcement> findGeneral(User hideSeenBy, int page, int pageSize) {
        return findAnnouncements(EntityTarget.general, null, hideSeenBy, pageSize, page);
    }

    @Override
    public List<Announcement> findByCourse(Course course, User hideSeenBy, int page, int pageSize) {
        return findAnnouncements(EntityTarget.course, course.getId(), hideSeenBy, pageSize, page);
    }

    @Override
    public List<Announcement> findByCareer(Career career, User hideSeenBy, int page, int pageSize) {
        return findAnnouncements(EntityTarget.career, career.getCode(), hideSeenBy, pageSize, page);
    }

    @Override
    public Optional<Announcement> findById(int id) {
        return Optional.ofNullable(
            em.find(Announcement.class, id)
        );
    }

    @Override
    public int getSize(EntityTarget target, String targetCode, User hideSeenBy) {
        return countAnnouncements(target, targetCode, hideSeenBy);
    }

    @Override
    public Announcement create(String title, String summary, String content, Career career, Course course,
           Date expiryDate, User submitter) {
        Announcement announcement = new Announcement(null, title, summary, content, career, course, submitter,
         null, expiryDate);
        em.persist(announcement);

        return announcement;
    }

    @Override
    public void markSeen(Announcement announcement, User seenBy) {
        announcement.getSeenBy().add(seenBy);
        em.merge(announcement);
    }

    @Override
    public void markAllSeen(User seenBy) {
        // TODO: Implement this method
    }

    @Override
    public void delete(Announcement announcement) {
        em.remove(announcement);
        em.flush();
        em.clear();
    }
}