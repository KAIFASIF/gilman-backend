package com.kaif.gilmanbackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempControlllers {

    @GetMapping("/user")
    public String userone() {
        return "user one";
    }

}
