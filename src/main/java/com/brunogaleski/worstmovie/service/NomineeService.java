package com.brunogaleski.worstmovie.service;

import com.brunogaleski.worstmovie.model.Nominee;
import com.brunogaleski.worstmovie.model.Producer;
import com.brunogaleski.worstmovie.dao.NomineeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NomineeService {
    
    private final NomineeDao nomineeDao;

    @Autowired
    public NomineeService(NomineeDao nomineeDao) {
        this.nomineeDao = nomineeDao;
    }

    public void persist(Nominee entity) {
        nomineeDao.openCurrentSessionWithTransaction();
        nomineeDao.persist(entity);
        nomineeDao.closeCurrentSessionWithTransaction();
    }

    public void update(Nominee entity) {
        nomineeDao.openCurrentSessionWithTransaction();
        nomineeDao.update(entity);
        nomineeDao.closeCurrentSessionWithTransaction();
    }

    public Nominee findById(Integer id) {
        nomineeDao.openCurrentSession();
        Nominee nominee = nomineeDao.findById(id);
        nomineeDao.closeCurrentSession();
        return nominee;
    }

    public void delete(Integer id) {
        nomineeDao.openCurrentSessionWithTransaction();
        Nominee nominee = nomineeDao.findById(id);
        nomineeDao.delete(nominee);
        nomineeDao.closeCurrentSessionWithTransaction();
    }

    public List<Nominee> findAll() {
        nomineeDao.openCurrentSessionWithTransaction();
        List<Nominee> nominees = nomineeDao.findAll();
        nomineeDao.closeCurrentSessionWithTransaction();
        return nominees;
    }

    public void deleteAll() {
        nomineeDao.openCurrentSessionWithTransaction();
        nomineeDao.deleteAll();
        nomineeDao.closeCurrentSessionWithTransaction();
    }

    public void addProducerToNominee(Nominee nominee, Producer producer) {
        if (nominee.getProducers().isEmpty())
            nominee.setProducers(new ArrayList<>());

        nominee.getProducers().add(producer);
        update(nominee);
    }

}
