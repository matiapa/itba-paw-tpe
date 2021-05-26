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
public class AnnouncementDaoJpa implements AnnouncementDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Announcement> findRelevant(int userId, int limit) {
        return null;
    }

    @Override
    public List<Announcement> findGeneral(boolean showSeen, int userId, int offset, int limit) {
        final TypedQuery<Announcement> query = em.createQuery(
                "select a from Announcement as a where a.career is null and a.course is null", Announcement.class);

        return query.getResultList();
    }

    @Override
    public List<Announcement> findByCourse(String courseId, boolean showSeen, int userId, int offset, int limit) {
        final TypedQuery<Announcement> query = em.createQuery(
            "select a from Announcement as a where a.course.id = :courseId", Announcement.class);
        query.setParameter("courseId", courseId);

        return query.getResultList();
    }

    @Override
    public List<Announcement> findByCareer(String careerCode, boolean showSeen, int userId, int offset, int limit) {
        final TypedQuery<Announcement> query = em.createQuery(
                "select a from Announcement as a where a.career.code = :careerCode", Announcement.class);
        query.setParameter("careerCode", careerCode);

        return query.getResultList();
    }

    @Override
    public Optional<Announcement> findById(int id) {
        return Optional.ofNullable(em.find(Announcement.class, id));
    }

    @Override
    public int getSize(EntityTarget target, String code, boolean showSeen, int userId) {
        return 0;
    }

    @Override
    public Announcement create(String title, String summary, String content, String careerCode, String courseId,
           Date expiryDate, Integer submittedBy) {
        User uploader = em.find(User.class, submittedBy);
        Career career = careerCode != null ? em.find(Career.class, careerCode) : null;
        Course course = courseId != null ? em.find(Course.class, courseId) : null;

        Announcement announcement = new Announcement(null, title, summary, content, career, course, uploader,
         null, expiryDate, false);
        em.persist(announcement);

        return announcement;
    }

    @Override
    public void markSeen(int announcementId, int userId) {

    }

    @Override
    public void markAllSeen(User loggedUser) {

    }

    @Override
    public void delete(int id) {

    }
}
