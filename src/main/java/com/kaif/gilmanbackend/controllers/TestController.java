package com.kaif.gilmanbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.function.FailableRunnable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;

import com.kaif.gilmanbackend.entities.Slots;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.SlotsRepo;
import com.kaif.gilmanbackend.repos.TransactionRepo;
import com.kaif.gilmanbackend.repos.UserRepo;
import com.kaif.gilmanbackend.services.SlotService;

@RestController
public class TestController {

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

    @PostMapping("/addScheduleBookings")
    public void tempInsertRecords() {
        List<Slots> payloads = new ArrayList<>();
        var tomorrowDate = LocalDate.now().plusDays(1);
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("06:00"), LocalTime.parse("06:30")));
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("06:30"), LocalTime.parse("07:00")));
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("07:00"), LocalTime.parse("07:30")));
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("07:30"), LocalTime.parse("08:00")));
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("08:00"), LocalTime.parse("08:30")));
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("08:30"), LocalTime.parse("09:00")));
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("09:00"), LocalTime.parse("09:30")));
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("09:30"), LocalTime.parse("10:00")));
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("10:00"), LocalTime.parse("10:30")));
        payloads.add(new Slots(LocalDate.now(), LocalTime.parse("10:30"), LocalTime.parse("11:00")));

        payloads.add(new Slots(tomorrowDate, LocalTime.parse("06:00"), LocalTime.parse("06:30")));
        payloads.add(new Slots(tomorrowDate, LocalTime.parse("06:30"), LocalTime.parse("07:00")));
        payloads.add(new Slots(tomorrowDate, LocalTime.parse("07:00"), LocalTime.parse("07:30")));
        payloads.add(new Slots(tomorrowDate, LocalTime.parse("07:30"), LocalTime.parse("08:00")));
        payloads.add(new Slots(tomorrowDate, LocalTime.parse("08:00"), LocalTime.parse("08:30")));
        payloads.add(new Slots(tomorrowDate, LocalTime.parse("08:30"), LocalTime.parse("09:00")));
        payloads.add(new Slots(tomorrowDate, LocalTime.parse("09:00"), LocalTime.parse("09:30")));
        payloads.add(new Slots(tomorrowDate, LocalTime.parse("09:30"), LocalTime.parse("10:00")));
        payloads.add(new Slots(tomorrowDate, LocalTime.parse("10:00"), LocalTime.parse("10:30")));
        payloads.add(new Slots(tomorrowDate, LocalTime.parse("10:30"), LocalTime.parse("11:00")));
        slotsRepo.saveAll(payloads);
    }

    public void tempResetBookings() {
        var records = slotsRepo.findAll();
        for (Slots ele : records) {
            ele.setIsBooked(false);
            slotsRepo.save(ele);
        }
    }

    // @PostMapping("/book-slot")
    // public ResponseEntity<?> test(@RequestBody Slots payload) {
    // try {
    // // utils.isValidMobileAndName(payload);
    // utils.isDateAndTimeValid(payload);
    // testService.createTestBooking(payload);
    // return ResponseEntity.status(HttpStatus.CREATED).body("Slot booked
    // sucessfully");
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    // }
    // }

    @GetMapping("/fetchBookings")
    public ResponseEntity<?> test() {
        try {
            System.out.println("*****************************************************");
            System.out.println();
            System.out.println();
            System.out.println("User: "+userRepo.findById(2L).get().getBooking());
            System.out.println("Boooking: "+bookingRepo.findById(1L).get());
            System.out.println("Transaction: "+transactionRepo.findById(1L).get().getBooking().getUser());
            System.out.println();
            System.out.println();
            System.out.println("*****************************************************");
            return ResponseEntity.status(HttpStatus.CREATED).body("Slot booked sucessfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("/test-slot")
    public ResponseEntity<?> bookTicket() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.execute(run(slotService::createBooking1));
            executor.execute(run(slotService::createBooking2));
            executor.shutdown();
            // tempResetBookings();
            return ResponseEntity.status(HttpStatus.CREATED).body("booked sucessfulluy");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    private Runnable run(FailableRunnable<Exception> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}