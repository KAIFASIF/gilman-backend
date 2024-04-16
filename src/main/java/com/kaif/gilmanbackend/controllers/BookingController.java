package com.kaif.gilmanbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.entities.Bookings;
import com.kaif.gilmanbackend.services.BookService;
import com.kaif.gilmanbackend.utilities.Utils;



@RestController
public class BookingController {

    @Autowired
    private Utils utils;

    @Autowired
    private BookService bookService;

    @PostMapping("/bookGround")
    public ResponseEntity<?> bookGround(@RequestBody Bookings payload) {
        try {
            utils.isDateAndTimeValid(payload);
            bookService.createBooking(payload);
            return ResponseEntity.status(HttpStatus.OK).body("Ground booked sucessfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

}
