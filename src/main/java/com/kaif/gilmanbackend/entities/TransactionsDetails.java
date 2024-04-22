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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TransactionsDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String razorPayPaymemntId;
    private String razorPayOrdertId;
    private String razorPaySignature;
    private Long amountPaid;
    private String status;

    @CreatedDate
    private LocalTime time;

    @CreatedDate
    private LocalDate date;


    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    private Slot slot;



    // @JsonFormat(pattern = "dd-MM-yyyy")
    // @DateTimeFormat(pattern = "dd-MM-yyyy")
    // private LocalDate date;

    @Version
    private Long version;
}