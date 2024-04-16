package com.kaif.gilmanbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.repos.UserRepo;
import com.kaif.gilmanbackend.utilities.Utils;


@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Utils utils;

    // @PostMapping("/create-user")
    // public ResponseEntity<?> createUser(@RequestBody User payload) {
    // try {
    // userRepo.save(payload);
    // return ResponseEntity.status(HttpStatus.OK).body("User Saved sucessfully");
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    // }
    // }

    // @PostMapping("/signup")
    // public ResponseEntity<?> signup(@RequestBody User payload) {
    //     try {
    //         // utils.isValidName(payload.getName());
    //         // utils.isValidMobile(payload.getMobile());
    //         var res = userRepo.save(payload);
    //         return ResponseEntity.status(HttpStatus.CREATED).body(res);
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    //     }
    // }

    // @PostMapping("/signin")
    // public ResponseEntity<?> signin(@RequestBody User payload) {
    //     // var res = userRepo.findByMobile(payload.getMobile());
    //     return ResponseEntity.status(HttpStatus.OK).body("");
    // }

    // @PostMapping("/signin")
    // public ResponseEntity<?> createUser(@RequestBody User user) {
    // // if (utils.isStringNullOrEmpty(user.getFullname()) ||
    // utils.isStringNullOrEmpty(user.getEmail()) ||
    // // utils.isLongNullOrEmpty(user.getMobile()) ||
    // utils.isStringNullOrEmpty(user.getPassword()) ||
    // // utils.isStringNullOrEmpty(user.getRole()) ||
    // utils.isBoolNullOrEmpty(user.getIsAuthorized())) {
    // // throw new ResourceNotFound(
    // // "Fullname, Email, Mobile, Password, Role and isAuthorized are mandatory
    // required fields");
    // // }
    // // user.setPassword(passwordEncoder.encode(user.getPassword()));
    // // User res = userRepo.save(user);
    // return ResponseEntity.status(HttpStatus.CREATED).body("res");

    // }

}
