package com.kaif.gilmanbackend.controllers;

import java.time.LocalDate;

// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.TimeUnit;

// import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.entities.Bookings;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.services.TestService;
import com.kaif.gilmanbackend.utilities.Utils;



@RestController
@RequestMapping("/api/v1")
public class TestController {

    @Autowired
    private Utils utils;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TestService testService;

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody Bookings payload) {
        try {
            utils.isValidMobileAndName(payload);
            utils.isDateAndTimeValid(payload);
            // testService.createTestBooking(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body("Slot booked sucessfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/ground")
    public ResponseEntity<?> fetchGrunddetails(@RequestParam(required = false) LocalDate date) {
        try {
            // var res= bookingRepo.findAll();
            // var a= bookingRepo.findByDate(LocalDate.parse("2024-04-14"));
            // var b = bookingRepo.findByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body("b");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    // @PostMapping("/test1")
    // public ResponseEntity<?> test1(@RequestBody Bookings payload) {
    // try {
    // utils.isDateAndTimeValid(payload);
    // ExecutorService executor = Executors.newFixedThreadPool(2);
    // executor.execute(run(testService::createTestBooking1));
    // executor.execute(run(testService::createTestBooking2));
    // executor.shutdown();
    // return ResponseEntity.ok("Slot booked sucessfully");

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    // }
    // }

    // @PostMapping("/test1")
    // public ResponseEntity<?> test1(@RequestBody Bookings payload) {
    // try {
    // utils.isDateAndTimeValid(payload);
    // ExecutorService executor = Executors.newFixedThreadPool(2);
    // executor.execute(() -> {
    // try {
    // testService.createTestBooking1();
    // } catch (Exception e) {
    // throw new RuntimeException(e); // Wrap the exception and rethrow
    // }
    // });
    // executor.execute(() -> {
    // try {
    // testService.createTestBooking2();
    // } catch (Exception e) {
    // throw new RuntimeException(e); // Wrap the exception and rethrow
    // }
    // });
    // executor.shutdown();
    // return ResponseEntity.ok("Slot booked successfully");
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
