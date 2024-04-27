package com.kaif.gilmanbackend.controllers.publicControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.services.publicServices.PublicBookedSlotService;
import com.kaif.gilmanbackend.services.publicServices.PublicUserService;

@RequestMapping("/api/v1/public")
@RestController
public class PublicController {

    @Autowired
    private PublicUserService publicUserService;

    @Autowired
    private PublicBookedSlotService publicBookedSlotService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User payload) {
        try {
            publicUserService.saveUser(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucsess");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody User payload) {
        try {
            var res = publicUserService.signinUser(payload);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @GetMapping("/slots")
    public ResponseEntity<?> fetchBookedSlots(@RequestParam(required = false) LocalDate date) {

        try {
            var res = publicBookedSlotService.fetchBookedSlots(date);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

}
