package com.kaif.gilmanbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.dto.BookingAndTransactionRequest;
import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.services.BookingService;
import com.kaif.gilmanbackend.utilities.Utils;

@RestController
@RequestMapping("/api/v1/user")
public class BookingContoller {

    @Autowired
    private BookingService bookService;

    @Autowired
    private Utils utils;

    @PostMapping("/validate-booking-and-create-order/{amount}")
    public ResponseEntity<?> validateBoookingAndcreateOrder(@PathVariable("amount") Long amount,
            @RequestBody Booking payload) {
        try {
            utils.isDateAndTimeValid(payload);
            var jsonResponse = bookService.validateBoookingAndcreateOrder(amount, payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(jsonResponse.toString());
        } catch (Exception e) {
            bookService.resetLockedRows(payload);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("/creating-boooking-and-save-transaction/{userId}")
    public ResponseEntity<?> saveBookingAndTransaction(@PathVariable("userId") Long userId,
            @RequestBody BookingAndTransactionRequest payload) {
        try {
            bookService.createBookingAndTransaction(userId, payload);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ground booked sucessfully");
        } catch (Exception e) {
            bookService.resetLockedRows(payload.getBooking());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/bookings/{userId}")
    public ResponseEntity<?> fetchBookings(@PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "5", value = "size") Integer size) {
        try {
            var res = bookService.fetchBookings(userId, page, size);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

}