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
import org.apache.commons.lang3.function.FailableRunnable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;

import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.entities.Slots;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.SlotsRepo;
import com.kaif.gilmanbackend.repos.TransactionRepo;
import com.kaif.gilmanbackend.repos.UserRepo;
import com.kaif.gilmanbackend.services.SlotService;
import com.kaif.gilmanbackend.utilities.Utils;

@RestController
@RequestMapping("/api/v1/user")
public class BookingController {

    @Autowired
    private SlotsRepo slotsRepo;

    @Autowired
    private SlotService slotService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private Utils utils;

    @PostMapping("/book-slot/{userId}")
    public ResponseEntity<?> test(@PathVariable("userId") Long userId, @RequestBody Booking payload) {
        try {
            System.out.println(userId);
            System.out.println(payload);
            utils.isDateAndTimeValid(payload);
            slotService.createBooking(userId, payload);
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
    // executor.execute(run(slotService::createBooking1));
    // executor.execute(run(slotService::createBooking2));
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