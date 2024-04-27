package com.kaif.gilmanbackend.controllers.adminControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.services.adminservices.AdminBookingService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminBookingContoller {

    @Autowired
    private AdminBookingService adminBookingService;

    @GetMapping("/bookings")
    public ResponseEntity<?> getBookingsUserAndTransaction(
            @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
            @RequestParam(required = false, defaultValue = "5", value = "size") Integer size) {
        try {
            var res = adminBookingService.fetchBookingsUserAndTransaction(page, size);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

}