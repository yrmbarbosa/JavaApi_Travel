package com.yrmb.apitravel.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yrmb.apitravel.enumeration.TravelTypeEnum;
import com.yrmb.apitravel.factory.TravelFactory;
import com.yrmb.apitravel.model.Travel;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelService {

    private List<Travel> travels;

    public void createTravelList() {
        if (travels == null) {
            travels = new ArrayList<>();
        }
    }

    public boolean isJsonValid(String jsonString) {
        try {
            return new ObjectMapper().readTree(jsonString) != null;
        } catch (IOException ex) {
            return false;
        }
    }

    public long parseId(JSONObject jsonTravel){
        return Long.valueOf((int) jsonTravel.get("id"));
    }

    private BigDecimal parseAmount(JSONObject jsonTravel){
        return new BigDecimal((String) jsonTravel.get("amount"));
    }

    private LocalDateTime parseStartDate(JSONObject jsonTravel){
        var startDate = (String) jsonTravel.get("startDate");
        return ZonedDateTime.parse(startDate).toLocalDateTime();
    }

    private LocalDateTime parseEndDate(JSONObject jsonTravel){
        var endDate = (String) jsonTravel.get("endDate");
        return ZonedDateTime.parse(endDate).toLocalDateTime();
    }

    public boolean isStartDateGreaterThanEndDate(Travel travel) {
        if (travel.getEndDate() == null) return false;
        return travel.getStartDate().isAfter(travel.getEndDate());
    }

    public void setTravelValues(JSONObject jsonTravel, Travel travel){

        String orderNumber = (String) jsonTravel.get("orderNumber");
        String type = (String) jsonTravel.get("type");

        travel.setOrderNumber(orderNumber != null ? orderNumber : travel.getOrderNumber());
        travel.setAmount(jsonTravel.get("amount") != null ? parseAmount(jsonTravel) : travel.getAmount());
        travel.setStartDate(jsonTravel.get("initialDate") != null ? parseStartDate(jsonTravel) : travel.getStartDate());
        travel.setEndDate(jsonTravel.get("finalDate") != null ? parseEndDate(jsonTravel) : travel.getEndDate());
        travel.setType(type != null ? TravelTypeEnum.getEnum(type) : travel.getType());
    }

    public Travel create(JSONObject jsonTravel) {

        TravelFactory factory = new TravelFactory();
        Travel travel = factory.createTravel((String) jsonTravel.get("type"));
        travel.setId(parseId(jsonTravel));
        setTravelValues(jsonTravel, travel);

        return travel;
    }

    public Travel update(JSONObject jsonTravel, Travel travel) {

        setTravelValues(jsonTravel, travel);
        return travel;
    }

    public void add(Travel travel) {
        createTravelList();
        travels.add(travel);
    }

    public List<Travel> find() {
        createTravelList();
        return travels;
    }

    public Travel findById(long id){
        return travels.stream().filter(t -> id == t.getId()).collect(Collectors.toList()).get(0);
    }

    public void delete(){
        travels.clear();
    }

}
