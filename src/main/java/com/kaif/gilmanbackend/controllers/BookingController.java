package com.kaif.gilmanbackend.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.services.BookingService;
import com.kaif.gilmanbackend.utilities.Utils;

@RestController
@RequestMapping("/api/v1/user")
public class BookingController {

    @Autowired
    private BookingService bookService;

    @Autowired
    private Utils utils;

    @PostMapping("/book-slot/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable("userId") Long userId,
            @RequestBody Booking payload) {
        try {
            utils.isDateAndTimeValid(payload);
            bookService.createOrder(userId, payload);
            return ResponseEntity.status(HttpStatus.CREATED).body("Slot booked sucessfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    // @GetMapping("/fetchBookings")
    // public ResponseEntity<?> test() {
    // try {
    // System.out.println("*****************************************************");
    // System.out.println();
    // System.out.println();
    // System.out.println("User: " + userRepo.findById(2L).get().getBooking());
    // System.out.println("Boooking: " + bookingRepo.findById(1L).get());
    // System.out.println("Transaction: " +
    // transactionRepo.findById(1L).get().getBooking().getUser());
    // System.out.println();
    // System.out.println();
    // System.out.println("*****************************************************");
    // return ResponseEntity.status(HttpStatus.CREATED).body("Slot booked
    // sucessfully");
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    // }
    // }

    // @PostMapping("/test-slot")
    // public ResponseEntity<?> bookTicket() {
    // try {
    // ExecutorService executor = Executors.newFixedThreadPool(2);
    // executor.execute(run(bookService::method1));
    // executor.execute(run(bookService::method2));
    // executor.shutdown();
    // // tempResetBookings();
    // return ResponseEntity.status(HttpStatus.CREATED).body("booked sucessfulluy");
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    // }
    // }

    // private Runnable run(FailableRunnable<Exception> runnable) {
    // return () -> {
    // try {
    // runnable.run();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // };
    // }

}