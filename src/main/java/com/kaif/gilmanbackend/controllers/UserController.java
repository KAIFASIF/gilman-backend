package com.kaif.gilmanbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.services.UserService;
import com.kaif.gilmanbackend.utilities.Utils;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Utils utils;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User payload) {
        try {
            // utils.isValidName(payload.getName());
            utils.isValidMobile(payload.getMobile());
            userService.saveUser(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sucsess");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody User payload) {
        try {
            return ResponseEntity.ok(userService.signinUser(payload));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

}
