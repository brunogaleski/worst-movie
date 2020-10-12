package com.brunogaleski.worstmovie.controller;

import com.brunogaleski.worstmovie.model.AwardIntervalsDTO;
import com.brunogaleski.worstmovie.service.AwardIntervalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwardIntervalController {

    private final AwardIntervalService awardIntervalService;

    public AwardIntervalController(AwardIntervalService awardIntervalService) {
        this.awardIntervalService = awardIntervalService;
    }

    @GetMapping("awardintervals")
    public ResponseEntity<AwardIntervalsDTO> awardIntervals() {
        AwardIntervalsDTO awardIntervalsDTO = null;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            awardIntervalsDTO = awardIntervalService.getAwardIntervals();
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(awardIntervalsDTO, httpStatus);
    }
}
