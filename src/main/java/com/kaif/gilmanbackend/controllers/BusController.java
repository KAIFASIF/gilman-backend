package com.kaif.gilmanbackend.controllers;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.entities.BusDetails;
import com.kaif.gilmanbackend.repos.BusRepo;
import com.kaif.gilmanbackend.services.BusService;



@RestController
@RequestMapping("/api")
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private BusRepo busRepo;

    @PostMapping("/addBus")
    public void addBus(@RequestBody BusDetails payload) {
        payload.setDeparDateTime(LocalDateTime.now());
        // busRepo.save(payload);
    }

    @GetMapping("/bookTicket")
    public ResponseEntity<?> bookTicket() {
        try {
            busService.bookTicket();
            return ResponseEntity.ok("Booked ticket sucessfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/bookTickets")
    public ResponseEntity<?> bookTickets() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.execute(run(busService::bookTicket1));
            executor.execute(run(busService::bookTicket2));
            executor.shutdown();
            return ResponseEntity.ok("Booked ticket sucessfully");

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