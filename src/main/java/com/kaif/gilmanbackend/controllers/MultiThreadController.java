package com.kaif.gilmanbackend.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.services.MulthreadService;

@RestController
public class MultiThreadController {

    @Autowired
    private MulthreadService multhreadService;

    @GetMapping("/api/v1/public/multis")
    public ResponseEntity<?> testConcurrentMultiThread() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(5);
            executor.execute(run(multhreadService::testMultiThread1));
            executor.execute(run(multhreadService::testMultiThread2));
            executor.execute(run(multhreadService::testMultiThread3));
            executor.execute(run(multhreadService::testMultiThread4));
            executor.execute(run(multhreadService::testMultiThread5));
            executor.shutdown();
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
