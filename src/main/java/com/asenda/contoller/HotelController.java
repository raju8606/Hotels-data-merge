package com.asenda.contoller;


import com.asenda.service.HotelService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * The HotelController class handles HTTP requests related to loading data into the hotel service and retrieving data by hotel or destination IDs.
 */
@RestController
public class HotelController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HotelService hotelService;

    @GetMapping("/ascenda/loaddata")
    public ResponseEntity<Object> loaddata()
            throws JsonProcessingException, ParseException {

        String status = "SUCCESS";
        try {
            hotelService.loaddData();
        } catch (Exception e) {
            status = "FAILED with error " + e.getMessage();
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/ascenda/hotelid/{hotelid}")
    public ResponseEntity<Object> getByhotelid(@PathVariable("hotelid") String hotelId) {
        return new ResponseEntity<>(hotelService.getDatabyHotelId(hotelId), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/ascenda/destid/{destId}")
    public ResponseEntity<Object> getBydestid(@PathVariable("destId") String destId) {
        return new ResponseEntity<>(hotelService.getDatabyDestId(destId), HttpStatus.BAD_REQUEST);
    }

}