package com.kaif.gilmanbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kaif.gilmanbackend.dto.AuthenticationResponse;
import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.services.AuthenticationService;

@RestController
public class TempControlllers {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("feressdddddde");
    }

    @GetMapping("/user/one")
    public ResponseEntity<String> userone() {
        return ResponseEntity.ok("userone");
    }

    @GetMapping("/user/two")
    public ResponseEntity<String> usertwo() {
        return ResponseEntity.ok("usertwo");
    }

    @GetMapping("/admin/one")
    public ResponseEntity<String> adminone() {
        return ResponseEntity.ok("adminone");
    }

    @GetMapping("/admin/two")
    public ResponseEntity<String> admintwo() {
        return ResponseEntity.ok("admintwo");
    }

}
