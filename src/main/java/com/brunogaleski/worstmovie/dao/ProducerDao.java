package com.brunogaleski.worstmovie.dao;

import com.brunogaleski.worstmovie.model.Producer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class ProducerDao implements ProducerDaoInterface<Producer, Integer> {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private Session currentSession;

    private Transaction currentTransaction;

    public ProducerDao() {
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

    public void persist(Producer entity) {
        getCurrentSession().save(entity);
    }

    public void update(Producer entity) {
        getCurrentSession().update(entity);
    }

    public void merge(Producer entity) {
        getCurrentSession().merge(entity);
    }

    public Producer findById(Integer id) {
        return getCurrentSession().get(Producer.class, id);
    }

    public void delete(Producer entity) {
        getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Producer> findAll() {
        return (List<Producer>) getCurrentSession().createQuery("from Producer").list();
    }

    public void deleteAll() {
        List<Producer> entityList = findAll();
        for (Producer entity : entityList) {
            delete(entity);
        }
    }

    public Producer findByName(String name) {
        Producer producer = null;

        if (name != null)
            producer = (Producer) getCurrentSession().createQuery("from Producer where name = :name").setParameter("name", name).uniqueResult();

        return producer;
    }
}
