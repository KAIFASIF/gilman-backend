package com.kaif.gilmanbackend.controllers.adminControllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.entities.Slots;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.SlotsRepo;
import com.kaif.gilmanbackend.repos.TransactionRepo;

@RestController
@RequestMapping("/api/v1/admin")
public class SlotController {

    @Autowired
    private SlotsRepo slotsRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/deleteAll")
    public ResponseEntity<?> delAll() {
        try {
            slotsRepo.deleteAll();
            bookingRepo.deleteAll();
            transactionRepo.deleteAll();

            return ResponseEntity.status(HttpStatus.OK).body("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/deleteSlots")
    public ResponseEntity<?> rersetSlots() {
        try {
            slotsRepo.deleteAll();
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

    @GetMapping("/addScheduleBookings")
    public void addSlots() {
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

}