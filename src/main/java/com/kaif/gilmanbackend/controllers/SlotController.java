package com.kaif.gilmanbackend.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.entities.Slots;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.SlotsRepo;

@RestController
@RequestMapping("/api/v1/user")
public class SlotController {

    @Autowired
    private SlotsRepo slotsRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @GetMapping("/reset")
    public ResponseEntity<?> rersetSlots() {
        try {
            slotsRepo.deleteAll();
            // var records = slotRepo.findAll();
            // for (Slots ele : records) {
            //     ele.setIsBooked(false);
            //     slotRepo.save(ele);
            // }
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @Scheduled(cron = "0 39 14 * * *")
    public void insertSlots() {
        List<Slots> payloads = new ArrayList<>();
        var tomorrowsDate = LocalDate.now().plusDays(1);
        LocalTime startTime = LocalTime.parse("06:00");
        LocalTime endTime = LocalTime.parse("06:30");

        while (endTime.isBefore(LocalTime.parse("23:30"))) {
            payloads.add(new Slots(tomorrowsDate, startTime, endTime));
            startTime = endTime;
            endTime = endTime.plusMinutes(30);
        }
        slotsRepo.saveAll(payloads);
    }

    @GetMapping("/slots")
    public ResponseEntity<?> fetchBookedSlots(@RequestParam(required = false) LocalDate date) {

        try {
            var res = bookingRepo.findByDate(date);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

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