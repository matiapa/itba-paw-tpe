package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.AnnouncementDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
             User hideSeenBy, Integer pageSize, Integer page){

        String qryStr = buildQuery("SELECT a FROM Announcement a", target, hideSeenBy != null);

        TypedQuery<Announcement> query = em.createQuery(qryStr, Announcement.class);

        if(target != EntityTarget.general)
            query.setParameter("targetCode", targetCode);

        if(hideSeenBy != null) query.setParameter("seenBy", hideSeenBy.getId());

        query.setFirstResult(pageSize * page);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    Long countAnnouncements(EntityTarget target, String targetCode, User hideSeenBy){

        String qryStr = buildQuery("SELECT count(a.id) FROM Announcement a", target, hideSeenBy != null);

        TypedQuery<Long> query = em.createQuery(qryStr, Long.class);

        if(target != EntityTarget.general)
            query.setParameter("targetCode", targetCode);

        if(hideSeenBy != null) query.setParameter("seenBy", hideSeenBy.getId());

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
            "AND (a.career IS NULL OR a.career.code = :forUserCareerCode)";

        TypedQuery<Announcement> query = em.createQuery(qryStr, Announcement.class);

        query.setParameter("forUserId", forUser.getId());
        query.setParameter("forUserCareerCode", forUser.getCareer().getCode());

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
        return countAnnouncements(target, targetCode, hideSeenBy).intValue();
    }

    @Override
    @Transactional
    public Announcement create(String title, String summary, String content, Career career, Course course,
           Date expiryDate, User submitter) {
        Announcement announcement = new Announcement(null, title, summary, content, career, course, submitter, expiryDate);
        em.persist(announcement);

        return announcement;
    }

    @Override
    @Transactional
    public void markSeen(Announcement announcement, User seenBy) {
        announcement.getSeenBy().add(seenBy);
        em.merge(announcement);
    }

    @Override
    @Transactional
    public void markAllSeen(User seenBy) {
        // Usamos una query nativa porque con Hibernate se traen toda la lista de anuncios solo para
        // marcarlos como listos, lo cual es muy ineficiente y ralentiza la experiencia de usuario.
        // Se intent?? usar la cl??usula ON CONFLICT de insert para hacer la query m??s eficiente pero
        // generaba problemas de compatibilidad con HSQL.
        em.createNativeQuery("INSERT INTO announcement_seen SELECT a.id, :forUser FROM announcement a"
            + " WHERE NOT EXISTS(SELECT * FROM announcement_seen s WHERE s.announcement_id=a.id AND s.user_id=:forUser)")
            .setParameter("forUser", seenBy.getId())
            .executeUpdate();
    }

    @Override
    @Transactional
    public void delete(Announcement announcement) {
        em.remove(announcement);
        em.flush();
        em.clear();
    }
}