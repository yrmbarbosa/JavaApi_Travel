package com.yrmb.apitravel.controller;

import com.yrmb.apitravel.model.Travel;
import com.yrmb.apitravel.service.TravelService;
import net.minidev.json.JSONObject;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/travels")
public class TravelController {

    //private static final Logger logger = Logger.getLogger(TravelController.class);

    @Autowired
    private TravelService travelService;

    @GetMapping
    public ResponseEntity<List<Travel>> find() {

        var travels = travelService.find();

        if (travels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(travels);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete() {

        //travelService.delete();
        //return ResponseEntity.noContent().build();

        try {
            travelService.delete();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            //logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Travel> create(@RequestBody JSONObject travel) {

        try {

            if (travelService.isJsonValid(travel.toString())){

                Travel travelCreated = travelService.create(travel);
                var uri = ServletUriComponentsBuilder.fromCurrentRequest().path(travelCreated.getOrderNumber()).build().toUri();

                if (travelService.isStartDateGreaterThanEndDate(travelCreated)){
                    //logger.error("The start date is greater than end date.");
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
                } else {
                    travelService.add(travelCreated);
                    return ResponseEntity.created(uri).body(null);
                }

            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping(path = "/{id}", produces = { "application/json" })
    public ResponseEntity<Travel> update(@PathVariable("id") long id, @RequestBody JSONObject travel) {

        try {

            if (travelService.isJsonValid(travel.toString())) {

                Travel travelToUpdate = travelService.findById(id);

                if(travelToUpdate == null){
                    //logger.error("Travel not found.");
                    return ResponseEntity.notFound().build();
                } else {
                    travelToUpdate = travelService.update(travel, travelToUpdate);
                    return ResponseEntity.ok(travelToUpdate);
                }

            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch(Exception e) {
            //logger.error("JSON fields are not parsable." + e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }
}
