package com.kaif.gilmanbackend.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kaif.gilmanbackend.services.TestService;

@RestController
@RequestMapping("/api/v1/user")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public String test() {
        return "Welcome Burak";
    }
    
    @PostMapping("/test-slot")
    public ResponseEntity<?> testConcurrentBoooking() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.execute(run(testService::method1));
            executor.execute(run(testService::method2));
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