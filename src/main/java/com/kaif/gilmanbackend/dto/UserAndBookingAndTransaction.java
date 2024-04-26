package com.kaif.gilmanbackend.dto;

import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.entities.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndBookingAndTransaction {
    private Booking booking;
    private String name;
    private Long mobile;
}
