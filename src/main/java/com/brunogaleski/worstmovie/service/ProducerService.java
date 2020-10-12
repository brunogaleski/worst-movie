package com.brunogaleski.worstmovie.service;

import com.brunogaleski.worstmovie.model.Producer;
import com.brunogaleski.worstmovie.dao.ProducerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {

    private final ProducerDao producerDao;

    @Autowired
    public ProducerService(ProducerDao producerDao) {
        this.producerDao = producerDao;
    }

    public void persist(Producer entity) {
        producerDao.openCurrentSessionWithTransaction();
        producerDao.persist(entity);
        producerDao.closeCurrentSessionWithTransaction();
    }

    public void update(Producer entity) {
        producerDao.openCurrentSessionWithTransaction();
        producerDao.update(entity);
        producerDao.closeCurrentSessionWithTransaction();
    }

    public Producer findById(Integer id) {
        producerDao.openCurrentSessionWithTransaction();
        Producer producer = producerDao.findById(id);
        producerDao.closeCurrentSessionWithTransaction();
        return producer;
    }

    public void delete(Integer id) {
        producerDao.openCurrentSessionWithTransaction();
        Producer producer = producerDao.findById(id);
        producerDao.delete(producer);
        producerDao.closeCurrentSessionWithTransaction();
    }

    public List<Producer> findAll() {
        producerDao.openCurrentSession();
        List<Producer> producers = producerDao.findAll();
        producerDao.closeCurrentSession();
        return producers;
    }

    public void deleteAll() {
        producerDao.openCurrentSessionWithTransaction();
        producerDao.deleteAll();
        producerDao.closeCurrentSessionWithTransaction();
    }

    public Producer createIfItDoestExist(String producerName) {
        Producer producer = findByName(producerName);

        if (producer == null) {
            producer = new Producer();
            producer.setName(producerName);
            persist(producer);
        }

        return producer;
    }

    public Producer findByName(String producerName) {
        producerDao.openCurrentSessionWithTransaction();
        Producer producer = producerDao.findByName(producerName);
        producerDao.closeCurrentSessionWithTransaction();

        return producer;
    }


}
