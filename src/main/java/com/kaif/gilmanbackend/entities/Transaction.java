package com.kaif.gilmanbackend.entities;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razorPayPaymemntId;
    private String razorPayOrdertId;
    private String razorPaySignature;
    private Long amountPaid;
    private Long bookingAmount;
    private String status ="Paid";

    
    private LocalTime time = LocalTime.now();

    
    private LocalDate date = LocalDate.now();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Booking booking;

    @Version
    private Long version;

    public Transaction(User user, Booking booking, Long bookingAmount, Long amountPaid, String razorPayPaymemntId,
            String razorPayOrdertId, String razorPaySignature) {
        this.user = user;
        this.booking = booking;
        this.bookingAmount = bookingAmount;
        this.amountPaid = amountPaid;
        this.razorPayPaymemntId = razorPayPaymemntId;
        this.razorPayOrdertId = razorPayOrdertId;
        this.razorPaySignature = razorPaySignature;
    }

}