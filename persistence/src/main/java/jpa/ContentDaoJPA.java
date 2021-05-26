package jpa;

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
        stringBuilder.append("FROM course_content WHERE course_id= :courseId");

        if (contentType!= null)
            stringBuilder.append(" AND content_type= :content_type");
        if(minDate!=null || maxDate!=null)
            stringBuilder.append(" AND content_date IS NOT NULL");
        if (minDate!= null)
            stringBuilder.append(" AND content_date>= :minDate");
        if (maxDate!= null)
            stringBuilder.append(" AND content_date<= :maxDate");

        if(offset != null && limit != null)
            stringBuilder.append(" ORDER BY creation_date DESC OFFSET :offset LIMIT :limit");



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
    public boolean createContent(String name, String link, String courseId, String description, String contentType, Date contentDate, User user) {


        Course course = courseId != null ? em.find(Course.class, courseId) : null;
        Content content = new Content(null,name,link,description,user,user.getEmail(),new Date(),contentDate, Content.ContentType.valueOf(contentType.trim()));
        em.persist(content);
        return true;


    }

    @Override
    public int getSize(String courseId, Content.ContentType contentType, Date minDate, Date maxDate) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FROM course_content WHERE course_id= :courseId");

        if (contentType!= null)
            stringBuilder.append(" AND content_type= :content_type");
        if(minDate!=null || maxDate!=null)
            stringBuilder.append(" AND content_date IS NOT NULL");
        if (minDate!= null)
            stringBuilder.append(" AND content_date>= :minDate");
        if (maxDate!= null)
            stringBuilder.append(" AND content_date<= :maxDate");




        final TypedQuery<Content> query = em.createQuery(stringBuilder.toString(), Content.class);
        query.setParameter("courseId",courseId);
        query.setParameter("content_type",contentType);
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
