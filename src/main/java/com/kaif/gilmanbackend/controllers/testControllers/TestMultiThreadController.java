package com.kaif.gilmanbackend.controllers.testControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.repos.TestMultiThreadingrepo;
import com.kaif.gilmanbackend.services.testServices.TestMultiThreadService;

@RestController
public class TestMultiThreadController {

    @Autowired
    private TestMultiThreadService service;

    @Autowired
    private TestMultiThreadingrepo repo;

    @Autowired
    private BookingRepo bookingRepo;

    @DeleteMapping("/api/v1/public/del")
    public void deleteBooking() {
        repo.deleteAll();
    }
   
    
    @GetMapping("/api/v1/public/t")
    public void traverse() {
        // bookingRepo.deleteAll();
        service.traverse();
    }
    @GetMapping("/api/v1/public/testmulti")
    public void test() {
        service.testMethod();
    }

    
}
