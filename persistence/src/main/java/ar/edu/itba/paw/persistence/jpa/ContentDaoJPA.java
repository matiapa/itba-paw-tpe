package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.ContentDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;


@Repository
public class ContentDaoJPA implements ContentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Content> findContent(String courseId, Content.ContentType contentType, Date minDate, Date maxDate, Integer offset, Integer limit){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT c from Content AS c WHERE c.course.id = :courseId");

        if (contentType!= null)
            stringBuilder.append(" AND c.contentType = :content_type");
        if (minDate!=null || maxDate!=null)
            stringBuilder.append(" AND c.contentDate IS NOT NULL");
        if (minDate!= null)
            stringBuilder.append(" AND c.contentDate >= :minDate");
        if (maxDate!= null)
            stringBuilder.append(" AND c.contentDate <= :maxDate");

        if(offset != null && limit != null)
            stringBuilder.append(" ORDER BY c.uploadDate DESC OFFSET :offset LIMIT :limit");

        final TypedQuery<Content> query = em.createQuery(stringBuilder.toString(), Content.class);
        query.setParameter("courseId",courseId);
        query.setParameter("content_type",contentType);
        query.setParameter("minDate",minDate);
        query.setParameter("maxDate",maxDate);
        query.setParameter("offset",offset);
        query.setParameter("limit",limit);

        return query.getResultList();
    }

    @Override
    public boolean createContent(String name, String link, Course course, String description, String contentType,
            Date contentDate, User user) {
        Content content = new Content(null,name,link,description,user,user.getEmail(),new Date(),contentDate,
                Content.ContentType.valueOf(contentType.trim()), course);
        em.persist(content);
        return true;
    }

    @Override
    public int getSize(String courseId, Content.ContentType contentType, Date minDate, Date maxDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT c from Content AS c WHERE c.course.id = :courseId");

        if (contentType!= null)
            stringBuilder.append(" AND c.contentType = :contentType");
        if (minDate!=null || maxDate!=null)
            stringBuilder.append(" AND c.contentDate IS NOT NULL");
        if (minDate!= null)
            stringBuilder.append(" AND c.contentDate >= :minDate");
        if (maxDate!= null)
            stringBuilder.append(" AND c.contentDate <= :maxDate");

        final TypedQuery<Content> query = em.createQuery(stringBuilder.toString(), Content.class);
        query.setParameter("courseId",courseId);
        query.setParameter("contentType",contentType);
        query.setParameter("minDate",minDate);
        query.setParameter("maxDate",maxDate);

        return query.getResultList().size();
    }

    @Override
    public void delete(int id) {
        Content content= em.find(Content.class,id);
        em.remove(content);
        em.flush();
        em.clear();
    }

}