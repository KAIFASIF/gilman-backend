package com.kaif.gilmanbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempControlllers {

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
