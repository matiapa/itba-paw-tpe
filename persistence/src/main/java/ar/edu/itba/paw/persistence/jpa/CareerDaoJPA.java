package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.persistence.CareerDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class CareerDaoJPA implements CareerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Career> findAll() {
        final TypedQuery<Career> query = entityManager.createQuery(
        "SELECT c FROM Career c", Career.class
        );
        return query.getResultList();
    }

    @Override
    public Optional<Career> findByCode(String code) {
        final TypedQuery<Career> query = entityManager.createQuery(
        "SELECT c FROM Career c WHERE c.code = :code", Career.class);
        query.setParameter("code", code);
        return query.getResultList().stream().findFirst();
    }

}