package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Content.ContentType;
import ar.edu.itba.paw.models.ContentReview;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.ContentDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public class ContentDaoJPA implements ContentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Content> findByCourse(Course course, Content.ContentType contentType, Date minDate, Date maxDate,
                                      Integer page, Integer pageSize){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT c from Content AS c WHERE c.course.id = :courseId");

        if (contentType != null)
            stringBuilder.append(" AND c.contentType = :contentType");
        if (minDate !=null || maxDate != null)
            stringBuilder.append(" AND c.contentDate IS NOT NULL");
        if (minDate != null)
            stringBuilder.append(" AND c.contentDate >= :minDate");
        if (maxDate != null)
            stringBuilder.append(" AND c.contentDate <= :maxDate");

        final TypedQuery<Content> query = em.createQuery(stringBuilder.toString(), Content.class);

        query.setParameter("courseId", course.getId());

        if (contentType != null)
            query.setParameter("contentType", contentType);
        if (minDate != null)
            query.setParameter("minDate", minDate);
        if (maxDate != null)
            query.setParameter("maxDate", maxDate);

        query.setFirstResult(pageSize * page);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public Optional<Content> findById(int id) {
        return Optional.ofNullable(
            em.find(Content.class, id)
        );
    }

    @Override
    public int getSize(Course course, Content.ContentType contentType, Date minDate, Date maxDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT count(c) from Content AS c WHERE c.course.id = :courseId");

        if (contentType!= null)
            stringBuilder.append(" AND c.contentType = :contentType");
        if (minDate!=null || maxDate!=null)
            stringBuilder.append(" AND c.contentDate IS NOT NULL");
        if (minDate!= null)
            stringBuilder.append(" AND c.contentDate >= :minDate");
        if (maxDate!= null)
            stringBuilder.append(" AND c.contentDate <= :maxDate");

        final TypedQuery<Long> query = em.createQuery(stringBuilder.toString(), Long.class);

        query.setParameter("courseId", course.getId());

        if (contentType != null)
            query.setParameter("contentType", contentType);
        if (minDate != null)
            query.setParameter("minDate", minDate);
        if (maxDate != null)
            query.setParameter("maxDate", maxDate);

        return query.getSingleResult().intValue();
    }

    @Override
    @Transactional
    public boolean createContent(String name, String link, Course course, String description, ContentType contentType,
            Date contentDate, User uploader, String ownerMail) {
        Content content = new Content(null, name, link, description, uploader, ownerMail,
                contentDate, contentType, course);
        em.persist(content);
        em.flush();
        em.clear();
        return true;
    }

    @Override
    @Transactional
    public void delete(Content content) {
        em.remove(content);
        em.flush();
        em.clear();
    }

    @Override
    public List<ContentReview> getReviews(Content content, Integer page, Integer pageSize) {

        final TypedQuery<ContentReview> query = em.createQuery(
                "SELECT c FROM ContentReview c WHERE c.content= :content ", ContentReview.class
        );

        query.setParameter("content", content);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();

    }

    @Override
    @Transactional
    public ContentReview createContentReview(Content content, String review, User uploader) {
        ContentReview contentReview = new ContentReview(null,review,uploader,content);
        em.persist(contentReview);
        em.flush();
        em.clear();
        return contentReview;
    }

    @Override
    public int getReviewsSize(Content content) {
        final TypedQuery<ContentReview> query = em.createQuery(
                "SELECT c FROM ContentReview c WHERE c.content= :content ", ContentReview.class
        );

        query.setParameter("content", content);

        return query.getResultList().size();
    }

}