package com.kaif.gilmanbackend.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;    
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isPlayed = false;

    @Version
    private Long version;
    
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;    
    
    public Booking(LocalDate date, LocalTime startTime, LocalTime endTime, User user) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
    }

}