package com.kaif.gilmanbackend.dto;

import com.kaif.gilmanbackend.entities.Booking;
import com.kaif.gilmanbackend.entities.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingAndTransactionRequest {
    private Booking booking;
    private Transaction transaction;
}
