package com.kaif.gilmanbackend.entities;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionsDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private Long amountPaid;
    private Long mobile;


    @CreatedDate
    private LocalTime time;

    @CreatedDate
    private LocalDate date;

    // @JsonFormat(pattern = "dd-MM-yyyy")
    // @DateTimeFormat(pattern = "dd-MM-yyyy")
    // private LocalDate date;


    @Version
    private Long version;
}