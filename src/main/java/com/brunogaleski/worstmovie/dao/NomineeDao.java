package com.brunogaleski.worstmovie.dao;

import com.brunogaleski.worstmovie.model.Nominee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class NomineeDao implements NomineeDaoInterface<Nominee, Integer> {

    private final EntityManagerFactory entityManagerFactory;

    private Session currentSession;

    private Transaction currentTransaction;

    public NomineeDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Session openCurrentSession() {
        return getSessionFactory().openSession();
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private SessionFactory getSessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(Nominee entity) {
        getCurrentSession().save(entity);
    }

    public void update(Nominee entity) {
        getCurrentSession().update(entity);
    }

    public Nominee findById(Integer id) {
        return getCurrentSession().get(Nominee.class, id);
    }

    public void delete(Nominee entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Nominee> findAll() {
        return (List<Nominee>) getCurrentSession().createQuery("from Nominee").list();
    }

    public void deleteAll() {
        List<Nominee> entityList = findAll();
        for (Nominee entity : entityList) {
            delete(entity);
        }
    }
}
