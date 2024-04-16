package com.kaif.gilmanbackend.services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaif.gilmanbackend.entities.Bookings;
import com.kaif.gilmanbackend.exceptions.ResourceNotFound;
import com.kaif.gilmanbackend.exceptions.SeatNotAvailable;
import com.kaif.gilmanbackend.repos.BookingRepo;
import com.kaif.gilmanbackend.utilities.Utils;

import jakarta.transaction.Transactional;

@Service
public class TestService {

    @Autowired
    private Utils utils;

    @Autowired
    private BookingRepo bookingRepo;

    @Transactional
    public void createTestBooking(Bookings payload) {
        LocalTime payloadStartTime = payload.getStartTime();
        LocalTime payloadEndTime = payload.getEndTime();
        var records = bookingRepo.findByDateWithLock(payload.getDate());
        for (Bookings ele : records) {
            if (utils.isStartTimeEqualOrInRange(payloadStartTime, ele.getStartTime(), ele.getEndTime())
                    || utils.isEndTimeEqualOrInRange(payloadEndTime, ele.getStartTime(), ele.getEndTime())) {
                throw new ResourceNotFound(
                        "Please check the available slots  on the slot page for the provided date");
            }
        }

        bookingRepo.save(payload);
    }

    @Transactional
    public void createTestBooking1() throws SeatNotAvailable, InterruptedException {
        System.out.println("******************");
        LocalTime payloadStartTime = LocalTime.parse("11:00");
        LocalTime payloadEndTime = LocalTime.parse("12:00");
        var records = bookingRepo.findByDateWithLock(LocalDate.parse("2024-04-16"));

        for (Bookings ele : records) {
            if (utils.isStartTimeEqualOrInRange(payloadStartTime, ele.getStartTime(), ele.getEndTime())
                    || utils.isEndTimeEqualOrInRange(payloadEndTime, ele.getStartTime(), ele.getEndTime())) {
                throw new ResourceNotFound("Ground is already booked for given date and time");
            }
        }
        Bookings payload = new Bookings();
        payload.setStartTime(LocalTime.parse("11:00"));
        payload.setEndTime(LocalTime.parse("12:00"));
        payload.setDate(LocalDate.parse("2024-04-16"));
        bookingRepo.save(payload);
        Thread.sleep(1000);
    }

    @Transactional
    public void createTestBooking2() throws SeatNotAvailable, InterruptedException {
        LocalTime payloadStartTime = LocalTime.parse("11:00");
        LocalTime payloadEndTime = LocalTime.parse("12:00");
        var records = bookingRepo.findByDateWithLock(LocalDate.parse("2024-04-16"));

        for (Bookings ele : records) {
            if (utils.isStartTimeEqualOrInRange(payloadStartTime, ele.getStartTime(), ele.getEndTime())
                    || utils.isEndTimeEqualOrInRange(payloadEndTime, ele.getStartTime(), ele.getEndTime())) {
                throw new ResourceNotFound("Ground is already booked for given date and time");
            }
        }
        Bookings payload = new Bookings();
        payload.setStartTime(LocalTime.parse("11:00"));
        payload.setEndTime(LocalTime.parse("12:00"));
        payload.setDate(LocalDate.parse("2024-04-16"));
        bookingRepo.save(payload);
        Thread.sleep(1000);
    }

}
