package com.brunogaleski.worstmovie.service;

import com.brunogaleski.worstmovie.dao.AwardIntervalDao;
import com.brunogaleski.worstmovie.model.AwardIntervalsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AwardIntervalService {

    private final AwardIntervalDao dao;

    @Autowired
    public AwardIntervalService(AwardIntervalDao dao) {
        this.dao = dao;
    }

    public AwardIntervalsDTO getAwardIntervals() {
        return dao.getAwardIntervals();
    }
}
