package com.kaif.gilmanbackend.controllers;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.kaif.gilmanbackend.entities.Bookings;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.services.BookingService;
import com.kaif.gilmanbackend.utilities.Utils;

@RestController
@RequestMapping("/api/v1/user")
public class BookingController {

    @Autowired
    private Utils utils;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book-slot")
    public ResponseEntity<?> test(@RequestBody Bookings payload) {
        try {
            utils.isValidMobileAndName(payload);
            utils.isDateAndTimeValid(payload);
            bookingService.createTestBooking(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body("Slot booked sucessfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/slots")
    public ResponseEntity<?> fetchGrunddetails(@RequestParam(required = false) LocalDate date) {
        try {
            var res = bookingRepo.findByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

}
