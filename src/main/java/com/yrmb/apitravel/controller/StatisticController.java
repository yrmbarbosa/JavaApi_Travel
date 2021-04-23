package com.yrmb.apitravel.controller;

import com.yrmb.apitravel.model.Statistic;
import com.yrmb.apitravel.model.Travel;
import com.yrmb.apitravel.service.StatisticService;
import com.yrmb.apitravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticServices;
    @Autowired
    private TravelService travelService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Statistic> getStatistic(){

        List<Travel> travels = travelService.find();
        Statistic statistic = statisticServices.create(travels);

        return ResponseEntity.ok(statistic);
    }

}
